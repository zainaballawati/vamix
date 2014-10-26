package textEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import videoManipulator.VideoGui;

public class PreviewText {
	
	private static String projectPath;
	private static String hiddenDir;
	private static String videoLength;
	private static String videoPath;
	private static String workingDir;
	
	private static String previewCommand;

	public static void generatePreview() {
		
		//Get the project info fields
		TextFunctionality.setVideoInfo();

		// set the the FontSettings
		TextFunctionality.setTitleFontSettings();
		StringBuilder cmd = new StringBuilder();
		String inputFrameTime;
		boolean passedOrNot = true;

		// create a file for preview text
		File filePreview = new File(hiddenDir + "/PreviewText.txt");

		// Reference for writing to file:
		// http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java

		FileWriter fw;
		try {
			fw = new FileWriter(filePreview, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter x = new PrintWriter(bw);
			x.println(TextEditorGui.addTextArea.getText());
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// check if one of the background image options are selected
		if (!TextEditorGui.defaultCheck.isSelected() && !TextEditorGui.overlayCheck.isSelected()
				&& !TextEditorGui.frameCheck.isSelected()) {

			// if none of the three are selected, show an error message to
			// the user and allow them to choose one
			passedOrNot = false;
			JOptionPane.showMessageDialog(null,
					"ERROR: please select one of the background options");
			filePreview.delete();
		}
		// if the user wants to preview the title screen
		if (TextEditorGui.screenType.equals("Title Screen")) {
			// if they select a frame from the video
			if (TextEditorGui.backgroundImageOption == 2) {
				// check for whether the frame time specified by the user is
				// longer than the length of the video
				if (passedOrNot) {
					int convertedFrameTime = TextFunctionality.convertToSeconds(TextEditorGui.addTimeFrame
							.getText().trim());
					int lengthOfVideo = (int) (Double
							.parseDouble(videoLength));
					// if it is longer, show an error message to the user
					// and allow them to change the frame time
					if (convertedFrameTime > lengthOfVideo) {
						passedOrNot = false;
						JOptionPane
						.showMessageDialog(null,
								"ERROR: frame time can not be more than the length of the video");
						TextEditorGui.addTimeFrame.setText("");
						filePreview.delete();
					}
				}
				// if the checks are passed, the inputFrameTime is set so
				// the screenshot is taken at the time that the user
				// specified
				inputFrameTime = TextEditorGui.addTimeFrame.getText().trim();
			} else {
				// if the checks are passed, the inputFrameTime is set so
				// the screenshot is taken at 00:00:00.001 for both the
				// overlay option and the default option
				inputFrameTime = "00:00:00.001";
			}
		}
		// if the user wants to preview the credit screen
		else {
			// if they select a frame from the video
			if (TextEditorGui.backgroundImageOption == 2) {
				// check for whether the frame time specified by the user is
				// in the right format
				int passedTimeCheck = TextErrorHandling.timeChecks(TextEditorGui.addTimeFrame.getText()
						.trim());
				// if it is not in the right format, show an error message
				// to the user
				// and allow them to change the frame time
				if (passedTimeCheck == 1) {
					TextEditorGui.addTimeFrame.setText("");
					passedOrNot = false;
					JOptionPane
					.showMessageDialog(null,
							"ERROR: please specify the frame time in hh:mm:ss");
					filePreview.delete();
				}
				// check for whether the frame time specified by the user is
				// longer than the length of the video
				if (passedOrNot) {
					int convertedFrameTime = TextFunctionality.convertToSeconds(TextEditorGui.addTimeFrame
							.getText().trim());
					int lengthOfVideo = (int) (Double
							.parseDouble(videoLength));
					// if it is longer, show an error message to the user
					// and allow them to change the frame time
					if (convertedFrameTime > lengthOfVideo) {
						passedOrNot = false;
						JOptionPane
						.showMessageDialog(null,
								"ERROR: frame time can not be more than the length of the video");
						TextEditorGui.addTimeFrame.setText("");
						filePreview.delete();
					}
				}
				// if the checks are passed, the inputFrameTime is set so
				// the screenshot is taken at the time that the user
				// specified
				inputFrameTime = TextEditorGui.addTimeFrame.getText().trim();
			} else if (TextEditorGui.backgroundImageOption == 0) {
				// if the checks are passed, the inputFrameTime is set so
				// the screenshot is taken at the last frame of the video
				// for the overlay option
				int time = (int) (Double.parseDouble(videoLength));
				String videoLength = "" + time;
				inputFrameTime = videoLength;
			} else {
				// if the checks are passed, the inputFrameTime is set so
				// the screenshot is taken at 00:00:00.001 for both the
				// default option
				inputFrameTime = "00:00:00.001";
			}
		}
		// if all checks are passed generate the preview screen
		if (passedOrNot) {
			// command to take a screenshot from the video at the given
			// inputFrametime
			cmd.append("avconv -i " + videoPath + " -ss " + inputFrameTime
					+ " -f image2 -vframes 1 " + hiddenDir + "/out.png");
			cmd.append(";");
			// command to add the text to the screenshot
			cmd.append("avplay -i "
					+ hiddenDir
					+ "/out.png -strict experimental -vf \"drawtext=fontfile='"
					+ TextEditorGui.fontDir + TextEditorGui.fontName + "':textfile='" + filePreview
					+ "':x=(main_w-text_w)/2:y=(main_h-text_h)/2:fontsize="
					+ TextEditorGui.fontSize + ":fontcolor=" + TextEditorGui.fontColour
					+ "\" -window_title previewScreen -x 500 -y 350 ");
			cmd.append(";");
			previewCommand = cmd.toString();
			// run the preview Command
			Process process;
			ProcessBuilder builder;
			builder = new ProcessBuilder("/bin/bash", "-c", previewCommand);
			try {
				process = builder.start();
				process.waitFor();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// delete the preview file so the user can generate another
			// preview
			filePreview.delete();
			
		}	
		

	}
	
//	/**
//	 * Background Task class extends SwingWorker and handles all the long tasks.
//	 */
//	class PreviewWorker extends SwingWorker<Void, String> {
//		Process process;
//		ProcessBuilder builder;
//		String cmd;
//		File prevFile;
//
//		// Constructor for backgroundTask, takes in the name of the input and
//		// the output
//		protected PreviewWorker(String cmd) {
//			this.cmd = cmd;
//		}
//
//		// Override doInBackgrount() to execute longTask in the background
//		@Override
//		protected Void doInBackground() throws Exception {
//
//				builder = new ProcessBuilder("/bin/bash", "-c", cmd);
//				try {
//					process = builder.start();
//					process.waitFor();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//				// delete the preview file so the user can generate another preview
//				prevFile.delete();
//				return null;
//		}
//	}

}
