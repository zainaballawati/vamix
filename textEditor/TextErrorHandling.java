package textEditor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class TextErrorHandling {
	
	private static String projectPath;
	private static String hiddenDir;
	private static String videoLength;
	private static String videoPath;
	private static String workingDir;
	
	public static int timeChecks(String frameTime) {

		// Reference: from assignment 3 of Chahat Chawla
		// checks whether the startTime and length are in the format hh:mm:ss
		Pattern timePattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
		Matcher startMatcher = timePattern.matcher(frameTime);
		int passed = 0;
		// if the startTime is not in the right format
		if (!(startMatcher.find() && frameTime.length() == 8)) {
			passed = 1;
		}
		return passed;
	}
	
	public static boolean backgroundImageCheck() {
		boolean passed = false;

		// If no background option was specified
		if (!TextEditorGui.defaultCheck.isSelected() && !TextEditorGui.overlayCheck.isSelected()
				&& !TextEditorGui.frameCheck.isSelected()) {
			passed = false;
			JOptionPane.showMessageDialog(null,
					"ERROR: please select of the background options");
		}

		else if (TextEditorGui.backgroundImageTitle == 3 && TextEditorGui.backgroundImageCredit != 3) {
			passed = true;
		} else if (TextEditorGui.backgroundImageCredit == 3 && TextEditorGui.backgroundImageTitle != 3) {
			passed = true;
		}

		else if (TextEditorGui.backgroundImageTitle == 3 && TextEditorGui.backgroundImageCredit == 3) {
			passed = true;
		} else if (TextEditorGui.backgroundImageTitle == TextEditorGui.backgroundImageCredit) {
			passed = true;
		} else {
			passed = false;

			JOptionPane
			.showMessageDialog(
					null,
					"ERROR: background image option needs to be the same if both title and credit screen are selected");

			TextEditorGui.overlayCheck.setSelected(false);
			TextEditorGui.defaultCheck.setSelected(false);
			TextEditorGui.frameCheck.setSelected(false);
		}

		return passed;
	}

	
	/**
	 * Method returns true if the text editor tab passed all the checks (user
	 * input is valid)
	 */
	public static boolean allChecks() {
		boolean passedOrNot = true;
		boolean passedBackgroundCheck;
		
		TextFunctionality.setVideoInfo();

		if (TextEditorGui.screenType.equals("Title Screen")) {

			// set the backgroundImageTitle;
			TextEditorGui.backgroundImageTitle = TextEditorGui.backgroundImageOption;

			// check for the backgroundImageOption
			passedBackgroundCheck = backgroundImageCheck();

		} else {
			// set the backgroundImageCredit;
			TextEditorGui.backgroundImageCredit = TextEditorGui.backgroundImageOption;

			// check for the backgroundImageOption
			passedBackgroundCheck = backgroundImageCheck();
		}

		if (passedBackgroundCheck) {

			// If no duration was specified
			if (TextEditorGui.addDuration.getText().trim().equals("")) {
				passedOrNot = false;
				JOptionPane.showMessageDialog(null,
						"ERROR: please specify the duration");

				/*
				 * If text area has lines > 10 the number of lines were used to
				 * limit the title/credit length specifying number of lines can
				 * decrease the possibility of having large texts that exceeds
				 * the screen dimensions since the maximum font size is 72.
				 * Although it doesn't guarantee that the input will always be
				 * in right size, limiting line numbers is easier for the user
				 * since he can go back and count the extra lines to delete
				 * them. Which is fairly hard task if words or characters were
				 * used as a limit (they also don't guarantee right text size)
				 */
			} else if (TextEditorGui.addTextArea.getLineCount() > 10) {
				JOptionPane.showMessageDialog(null,
						"ERROR: Text can only be 10 lines.");
				passedOrNot = false;

				// If a frame background was chosen without specifying the frame
				// time
			} else if (TextEditorGui.backgroundImageOption == 2) {
				int passedTimeCheck = timeChecks(TextEditorGui.addTimeFrame.getText().trim());
				if (passedTimeCheck == 1) {
					TextEditorGui.addTimeFrame.setText("");
					passedOrNot = false;
					JOptionPane.showMessageDialog(null,
							"ERROR: please specify the frame time in hh:mm:ss");
				}

				if (passedOrNot) {
					int convertedFrameTime = TextFunctionality.convertToSeconds(TextEditorGui.addTimeFrame
							.getText().trim());
					int lengthOfVideo = (int) (Double.parseDouble(videoLength));
					// Check the time of frame is within the range of the video
					if (convertedFrameTime > lengthOfVideo) {
						passedOrNot = false;
						JOptionPane
						.showMessageDialog(null,
								"ERROR: frame time can not be more than the length of the video");
						TextEditorGui.addTimeFrame.setText("");
					}
				}
			}
		} else {
			passedOrNot = false;
		}
		return passedOrNot;
	}

}
