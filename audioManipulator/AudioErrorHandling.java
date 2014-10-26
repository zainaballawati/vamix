package audioManipulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class AudioErrorHandling {
	
	private String projectPath;
	private String hiddenDir;
	private static String videoPath;
	private static String videoLength;
	private static String workingDir;
	private String audioFields;

	
	/**
	 * timeChecks Method checks whether the time inputs given by the user were
	 * in the hh:mm:ss format or not
	 * 
	 * @param startTime
	 *            , length
	 */

	private static int timeChecks(String startTime, String length) {

		// Reference: from assignment 3 of Chahat Chawla
		// checks whether the startTime and length are in the format hh:mm:ss
		Pattern timePattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
		Matcher startMatcher = timePattern.matcher(startTime);
		Matcher lengthMatcher = timePattern.matcher(length);

		int passed = 0;

		// if the startTime is not in the right format, return 1
		if (!(startMatcher.find() && startTime.length() == 8)) {
			passed = passed + 1;
		}
		// if the length is not in the right format, return 2, if startTime is
		// wrong too, it will return 3
		if (!(lengthMatcher.find() && length.length() == 8)) {
			passed = passed + 2;
		}

		return passed;
	}

	/**
	 * audioSignalCheck Method checks whether the input media given by the user
	 * has an audio signal or not
	 */

	private static boolean audioSignalCheck() {

		//avProbe @reference: https://libav.org/avprobe.html

		int count = 0;

		//count the lines using the avprobe bash command
		String cmd = "avprobe -loglevel error -show_streams " + videoPath
				+ " | grep -i streams.stream | wc -l";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.redirectErrorStream(true);
		Process process;
		try {
			process = builder.start();
			InputStream outStr = process.getInputStream();
			// read the output of the process
			BufferedReader stdout = new BufferedReader(new InputStreamReader(outStr));
			String line;
			line = stdout.readLine();
			count = Integer.parseInt(line); // Get the number of streams
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if the video file has no audio stream
		if (count == 2) {
			JOptionPane.showMessageDialog(
							null,
							"ERROR: no audio signal in the imported video, can not perform remove or extract");
			AudioGui.removeCheck.setEnabled(false);
			AudioGui.extractCheck.setEnabled(false);
			AudioGui.outputFileNameLabel.setEnabled(false);
			AudioGui.outputFileName.setEnabled(false);
			AudioGui.mp3Label.setEnabled(false);
			AudioGui.starLabel2.setEnabled(false);
			AudioGui.extractDurationCheck.setEnabled(false);
			AudioGui.startTimeLabelExtract.setEnabled(false);
			AudioGui.startTimeExtract.setEnabled(false);
			AudioGui.lengthLabelExtract.setEnabled(false);
			AudioGui.lengthExtract.setEnabled(false);
			return false;
		}

		// if the video file has more than 2 lines, it has an audio signal and
		// if an audio file has one line, it has an audio signal
		else {
			return true;
		}
	}

	/**
	 * allChecksExtract Method does all the checks for extract
	 */

	private static boolean allChecksExtract() {
		boolean passedOrNot = true;

		// if duration is enabled
		if (AudioGui.extractDurationEnable) {

			// check whether the input startTime and lengthTime is in the right
			// format or not using timeChecks()
			int passedTimeCheckExtract = timeChecks(AudioGui.startTimeExtract.getText()
					.trim(), AudioGui.lengthExtract.getText().trim());

			// if both are in the wrong format, inform the user and allow them
			// to enter the start time and length again
			if (passedTimeCheckExtract == 3) {
				AudioGui.startTimeExtract.setText("");
				AudioGui.lengthExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(
								null,
								"ERROR: start time and length for extract command can only be in the format hh:mm:ss");
			}

			// if start time is in the wrong format, inform the user and allow
			// them to enter the start time again
			else if (passedTimeCheckExtract == 1) {
				AudioGui.startTimeExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: start time for extract command can only be in the format hh:mm:ss");

			}

			// if length is in the wrong format, inform the user and allow them
			// to enter the length again
			else if (passedTimeCheckExtract == 2) {
				AudioGui.lengthExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: length for extract command can only be in the format hh:mm:ss");
			}

			// check for whether the given duration + start time is smaller than
			// the length of the video or not
			else if (passedOrNot) {
				int convertedStartTime = AudioFunctionality.convertToSeconds(AudioGui.startTimeExtract
						.getText().trim());
				int convertedLength = AudioFunctionality.convertToSeconds(AudioGui.lengthExtract.getText()
						.trim());
				int totalTime = convertedStartTime + convertedLength;
				int lengthOfVideo = (int) (Double.parseDouble(videoLength));

				// if totalTime is more than the length of the video, inform the
				// user and allow them to enter the start time and length again
				if (totalTime > lengthOfVideo) {
					passedOrNot = false;
					JOptionPane
							.showMessageDialog(
									null,
									"ERROR: start time + length for extract can not be more than the length of the video");
					AudioGui.startTimeExtract.setText("");
					AudioGui.lengthExtract.setText("");
				}
			}
		}

		// check for whether the outputFileName field is empty
		if (AudioGui.outputFileName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null,
					"ERROR: please specify an output file name for extract");
		}

		// check whether the outputFileName specified user already exists in the
		// project directory
		String outputFile = workingDir + "/" + AudioGui.outputFileName.getText()
				+ ".mp3";
		File f = new File(outputFile);
		if (f.exists()) {

			// Reference for JOptionPane() as above.

			// Allow user to choose either overwriting the current file or
			// change the output file name
			Object[] existOptions = { "Cancel", "Overwrite" };
			int optionChosen = JOptionPane.showOptionDialog(null, "ERROR: "
					+ AudioGui.outputFileName.getText() + ".mp3 already exists. "
					+ "Do you want to overwrite the existing file?",
					"File Exists!", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, existOptions,
					existOptions[0]);
			if (optionChosen == 1) {
				f.delete(); // Delete the existing file
			} else {
				AudioGui.outputFileName.setText("");
			}
			passedOrNot = false;
		}
		return passedOrNot;
	}

	/**
	 * allChecksAdd Method does all the checks for add
	 */

	public static boolean allChecksAdd() {
		boolean passedOrNot = true;

		// if duration is enabled
		if (AudioGui.addDurationEnable) {

			// check whether the input startTime and lengthTime is in the right
			// format or not using timeChecks()
			int passedTimeCheckAdd = timeChecks(AudioGui.startTimeAdd.getText(),
					AudioGui.lengthAdd.getText());

			// if both are in the wrong format, inform the user and allow them
			// to enter the start time and length again
			if (passedTimeCheckAdd == 3) {
				AudioGui.startTimeAdd.setText("");
				AudioGui.lengthAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(
								null,
								"ERROR: start time and length for add command can only be in the format hh:mm:ss");
			}

			// if start time is in the wrong format, inform the user and allow
			// them to enter the start time again
			else if (passedTimeCheckAdd == 1) {
				AudioGui.startTimeAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: start time for add command can only be in the format hh:mm:ss");
			}

			// if length is in the wrong format, inform the user and allow
			// them to enter the length again
			else if (passedTimeCheckAdd == 2) {
				AudioGui.lengthAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: length for add can only be in the format hh:mm:ss");
			}

			// check for whether the given duration + start time is smaller than
			// the length of the video or not
			else if (passedOrNot) {
				int convertedStartTime = AudioFunctionality.convertToSeconds(AudioGui.startTimeAdd
						.getText().trim());
				int convertedLength = AudioFunctionality.convertToSeconds(AudioGui.lengthAdd.getText()
						.trim());
				int totalTime = convertedStartTime + convertedLength;
				int lengthOfVideo = (int) (Double.parseDouble(videoLength));

				// if totalTime is more than the length of the video, inform the
				// user and allow them to enter the start time and length again
				if (totalTime > lengthOfVideo) {
					passedOrNot = false;
					JOptionPane
							.showMessageDialog(
									null,
									"ERROR: start time + length for add can not be more than the length of the video");
					AudioGui.startTimeAdd.setText("");
					AudioGui.lengthAdd.setText("");
				}
			}
		}

		// Reference to File.probeContentType
		// http://docs.oracle.com/javase/7/docs/api/java/nio/file/spi/FileTypeDetector.html

		// check for whether the input audio file chosen by the user is an audio
		// file or not
		File file = new File(AudioGui.inputFile);
		Path path = file.toPath();
		String type = "";
		try {
			type = Files.probeContentType(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if the file is NOT an audio file, notify the user and
		// allow them to select again
		if (!(type.equals("audio/mpeg"))) {
			JOptionPane
					.showMessageDialog(
							null,
							"ERROR: "
									+ AudioGui.inputFile
									+ " does not refer to a valid audio file. Please select a new input file!");
			passedOrNot = false;
		}
		return passedOrNot;
	}

	/**
	 * allChecksAudio Method does all the checks for Audio Manipulator
	 */
	public static boolean allChecksAudio() {
		boolean passedAllChecks = true;
		AudioFunctionality.setVideoInfo();

		// do all the checks for extract
		if (AudioGui.extractEnable) {
			passedAllChecks = allChecksExtract();
		}

		// do all the checks for add
		if (AudioGui.addEnable) {
			passedAllChecks = allChecksAdd();
		}

		// do the check for audioSignal
		passedAllChecks = audioSignalCheck();
		return passedAllChecks;
	}


	
}
