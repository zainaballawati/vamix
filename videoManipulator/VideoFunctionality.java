package videoManipulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class VideoFunctionality {

	private static String projectPath;
	private static String hiddenDir;
	private static String videoPath;
	private static String videoLength;
	private static String workingDir;

	private String mirrorCmd = "";
	private String flipCmd = "";
	private String resizeCmd = "";

	/**
	 * setVideoInfo Method stores the video info from the project file to the private fields
	 */
	protected static void setVideoInfo() {
		// Get the main project file
		projectPath = vamix.Menu.getProjectPath();
		File f = new File(projectPath);
		try {
			// Read the file and save the necessary variables
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(f));
			reader.readLine(); // project path
			hiddenDir = reader.readLine();
			workingDir = reader.readLine();
			videoPath = reader.readLine(); // video path
			videoLength = reader.readLine();
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * allChecksVideoEdit Method checks that all text fields are not empty
	 */
	public static boolean dimentionsNotEmpty() {
		boolean passed = true;

		// do all the checks for extract
		if (VideoGui.resizeEnable) {
			if (VideoGui.widthTxt.getText().isEmpty() || VideoGui.heightTxt.getText().isEmpty()) {
				JOptionPane
				.showMessageDialog(
						null,
						"ERROR: dimensions cannot be empty");
				passed = false;
			}
		}

		return passed;
	}

	/**
	 * Background Task class extends SwingWorker and handles all the long tasks.
	 */
	class BackgroundTask extends SwingWorker<Integer, String> {
		Process process;
		ProcessBuilder builder;
		String firstInput;
		String lastOutput;

		// Constructor for backgroundTask, takes in the name of the input and
		// the output
		protected BackgroundTask(String input, String output) {
			firstInput = input;
			lastOutput = output;
		}

		// Override doInBackgrount() to execute longTask in the background
		@Override
		protected Integer doInBackground() throws Exception {

			// Reference for all the avconv commands
			// https://libav.org/avconv.html and a combination of many searches
			// found on google, final command selected after a lot of trials and
			// testing

			try {

				String tempOut1 = hiddenDir + "/tempOut1.mp4";

				String output ="";
				String input ="";

				// if flip is enabled, constructs the command for extract
				if (VideoGui.flipEnable) {
					if (VideoGui.mirrorEnable || VideoGui.resizeEnable) {
						output = tempOut1;
					} else {
						output = lastOutput;
					}
					String direction ="";

					if (VideoGui.verticalEnable) {
						direction = "vflip";
					} else if (VideoGui.horizontalEnable) {
						direction = "hflip";
					}

					StringBuilder bigFlipCmd = new StringBuilder();

					bigFlipCmd.append("avconv -i " + firstInput);
					bigFlipCmd.append(" -vf \"");
					bigFlipCmd.append(direction);
					bigFlipCmd.append("\" -strict experimental -y ");
					bigFlipCmd.append(output);
					flipCmd = bigFlipCmd.toString();

				}

				// if remove is enabled and add is not enabled, constructs the
				// command for removing the audio from the input file
				if (VideoGui.mirrorEnable) {
					if (VideoGui.flipEnable) {
						input = output;
					} else {
						input = firstInput;
					}

					if (VideoGui.resizeEnable) {
						output = tempOut1;
					} else {
						output = lastOutput;
					}
					// remove audio
					StringBuilder bigMirrorCmd = new StringBuilder();
					bigMirrorCmd.append("avconv -i " + input
							+ " -vf \"crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2\" -strict experimental -y " + output);

					mirrorCmd = bigMirrorCmd.toString();
				}

				// if extract is enabled, constructs the command for add
				if (VideoGui.resizeEnable) {

					if (VideoGui.flipEnable || VideoGui.mirrorEnable) {
						input = output;
					} else {
						input = firstInput;
					}

					StringBuilder bigResizeCmd = new StringBuilder();

					bigResizeCmd.append("avconv -i " + input);
					bigResizeCmd.append(" -vf \"scale=" + VideoGui.width +":"  +VideoGui.height + "\" -strict experimental -y ");
					bigResizeCmd.append(lastOutput);
					resizeCmd = bigResizeCmd.toString();

				}


				//construct the final command for audio
				StringBuilder finalCommand = new StringBuilder();

				// if both remove and add are not enabled together
				if (VideoGui.flipEnable) {
					finalCommand.append(flipCmd);
					finalCommand.append(";");
				}
				if (VideoGui.mirrorEnable) {
					finalCommand.append(mirrorCmd);
					finalCommand.append(";");
				}
				if (VideoGui.resizeEnable) {
					finalCommand.append(resizeCmd);
					finalCommand.append(";");
				}

				//start the builder for the bash command so it is executed
				String cmd = finalCommand.toString();
				System.out.println(cmd);
				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				process = builder.start();
				process.waitFor();
				return process.exitValue();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return 1;
		}
	}

}
