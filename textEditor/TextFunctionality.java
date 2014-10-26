package textEditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class TextFunctionality {

	private static String projectPath;
	private static String hiddenDir;
	private static String videoLength;
	private static String videoPath;
	private static String workingDir;
	
	private String titleCommand;
	private String creditCommand;
	private String bothTitleAndCreditCommand;

	/**
	 * Method stores the video info from the project file to the private fields
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
			videoPath = reader.readLine();
			videoLength = reader.readLine();
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Method concatenates the selected fontType, fontStyle to a string called
	 * fontName and initializes all the font variables for title screen
	 */
	public static void setTitleFontSettings() {
		StringBuilder font = new StringBuilder();
		// fontType options
		if (TextEditorGui.fontType == 0) {
			font.append("Arial");
		} else if (TextEditorGui.fontType == 1) {
			font.append("Courier_New");
		} else if (TextEditorGui.fontType == 2) {
			font.append("Georgia");
		} else if (TextEditorGui.fontType == 2) {
			font.append("Times_New_Roman");
		} else {
			font.append("Verdana");
		}
		// fontStyle options
		if (TextEditorGui.fontStyle == 1) {
			font.append("_Bold");
		} else if (TextEditorGui.fontStyle == 2) {
			font.append("_Italic");
		} else if (TextEditorGui.fontStyle == 3) {
			font.append("_Bold_Italic");
		}
		// append .ttf as font types are ttf files
		font.append(".ttf");
		TextEditorGui.fontName = font.toString();
		// set font variables for title screen
		TextEditorGui.titleFontSize = TextEditorGui.fontSize;
		TextEditorGui.titleFontColour = TextEditorGui.fontColour;
		TextEditorGui.titleFontName = TextEditorGui.fontName;
		TextEditorGui.titleFontStyle = TextEditorGui.fontStyle;
		TextEditorGui.titlePreviewFont = TextEditorGui.prevFont;
	}

	/**
	 * Method concatenates the selected fontType, fontStyle to a string called
	 * fontName and initializes all the font variables for title screen
	 */
	public static void setCreditFontSettings() {
		StringBuilder font = new StringBuilder();
		// fontType options
		if (TextEditorGui.fontType == 0) {
			font.append("Arial");
		} else if (TextEditorGui.fontType == 1) {
			font.append("Courier_New");
		} else if (TextEditorGui.fontType == 2) {
			font.append("Georgia");
		} else if (TextEditorGui.fontType == 2) {
			font.append("Times_New_Roman");
		} else {
			font.append("Verdana");
		}
		// fontStyle options
		if (TextEditorGui.fontStyle == 1) {
			font.append("_Bold");
		} else if (TextEditorGui.fontStyle == 2) {
			font.append("_Italic");
		} else if (TextEditorGui.fontStyle == 3) {
			font.append("_Bold_Italic");
		}
		// append .ttf as font types are ttf files
		font.append(".ttf");
		TextEditorGui.fontName = font.toString();
		// set font variables for credit screen
		TextEditorGui.creditFontSize = TextEditorGui.fontSize;
		TextEditorGui.creditFontColour = TextEditorGui.fontColour;
		TextEditorGui.creditFontName = TextEditorGui.fontName;
		TextEditorGui.creditFontStyle = TextEditorGui.fontStyle;
		TextEditorGui.creditPreviewFont = TextEditorGui.prevFont;
	}

	
	

	public static int convertToSeconds(String myDateString) {

		// Reference for convertingToSeconds:
		// http://stackoverflow.com/questions/11994790/parse-time-of-format-hhmmss

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = null;

		// parse the input to a Date format
		try {
			date = sdf.parse(myDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// creates a new calendar instance
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date); // assigns calendar to given date

		// do the calculation to convert hh:mm:ss to seconds
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		if (hour != 0) {
			hour = hour * 60 * 60;
		}
		if (minute != 0) {
			minute = minute * 60;
		}
		int timeInSeconds = seconds + hour + minute;
		return timeInSeconds;
	}

	/**
	 * Background Task class extends SwingWorker and handles all the long tasks.
	 */
	class BackgroundTask extends SwingWorker<Integer, String> {
		String firstInput = "";
		String lastOutput = "";
		Process process;
		ProcessBuilder builder;

		protected BackgroundTask(String input, String output) {
			firstInput = input;
			lastOutput = output;
		}

		// Override doInBackground() to execute longTask in the background
		@Override
		protected Integer doInBackground() throws Exception {
			try {

				/*
				 * Reference for all the avconv commands
				 * https://libav.org/avconv.html and a combination of many
				 * searches found on google, final command selected after a lot
				 * of trials and testing
				 */

				// create text files for title screen and credit screen for the
				// purposes of checking which screen the user wants to implement
				File fileTitle = new File(workingDir + "/.TitleText.txt");
				File fileCredit = new File(workingDir + "/.CreditText.txt");

				// if the user wants to overlay
				if (TextEditorGui.backgroundImageOption == 0) {

					// if the user has saved title screen settings but does not
					// want to implement credit screen
					if (fileTitle.exists() && !fileCredit.exists()) {
						StringBuilder finalTitleCommand = new StringBuilder();
						// add text to video
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.titleFontSize + ":fontcolor="
								+ TextEditorGui.titleFontColour + "\" -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalTitleCommand.append(";");
						// concatenate the videos together = gives the final
						// output
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/text.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file1.ts ; avconv -ss "
								+ TextEditorGui.titleDuration
								+ " -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file2.ts; avconv -i concat:\""
								+ hiddenDir
								+ "/file1.ts|"
								+ hiddenDir
								+ "/file2.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalTitleCommand.append(";");
						titleCommand = finalTitleCommand.toString();
						builder = new ProcessBuilder("/bin/bash", "-c",
								titleCommand);
					}

					// if the user has saved credit screen settings but does not
					// want to implement title screen
					else if (!fileTitle.exists() && fileCredit.exists()) {
						StringBuilder finalCreditCommand = new StringBuilder();
						// calculates the start time for credit screen
						int time = (int) (Double.parseDouble(videoLength) - Integer
								.parseInt(TextEditorGui.creditDuration));
						String startTime = "" + time;
						// add text to video
						finalCreditCommand
						.append("avconv -ss "
								+ startTime
								+ " -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.creditFontSize + ":fontcolor="
								+ TextEditorGui.creditFontColour + "\" -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/text1.mp4");
						finalCreditCommand.append(";");
						// concatenate the videos together = gives the final
						// output
						finalCreditCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "
								+ startTime
								+ " -y "
								+ hiddenDir
								+ "/file1.ts ; avconv -ss 0 -i "
								+ hiddenDir
								+ "/text1.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file2.ts; avconv -i concat:\""
								+ hiddenDir
								+ "/file1.ts|"
								+ hiddenDir
								+ "/file2.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalCreditCommand.append(";");
						creditCommand = finalCreditCommand.toString();
						builder = new ProcessBuilder("/bin/bash", "-c",
								creditCommand);
					}

					// if the user has saved settings for both title screen and
					// credit screen - wants to implement both
					else {
						StringBuilder finalBothCommand = new StringBuilder();
						// calculates the start time and the stop time
						int time = (int) (Double.parseDouble(videoLength) - Integer
								.parseInt(TextEditorGui.creditDuration));
						int time1 = time - Integer.parseInt(TextEditorGui.titleDuration);
						String startTime = "" + time;
						String stopTime = "" + time1;
						// add text to title video
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.titleFontSize + ":fontcolor="
								+ TextEditorGui.titleFontColour + "\" -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalBothCommand.append(";");
						// add text to credit video
						finalBothCommand
						.append("avconv -ss "
								+ startTime
								+ " -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.creditFontSize + ":fontcolor="
								+ TextEditorGui.creditFontColour + "\" -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/text1.mp4");
						finalBothCommand.append(";");
						// concatenate the videos together = gives the final
						// output
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/text.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file3.ts ; avconv -ss "
								+ TextEditorGui.titleDuration
								+ " -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y -t "
								+ stopTime
								+ " "
								+ hiddenDir
								+ "/file5.ts; avconv -ss 0 -i "
								+ hiddenDir
								+ "/text1.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file6.ts ; avconv -i concat:\""
								+ hiddenDir
								+ "/file3.ts|"
								+ hiddenDir
								+ "/file5.ts|"
								+ hiddenDir
								+ "/file6.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalBothCommand.append(";");
						bothTitleAndCreditCommand = finalBothCommand.toString();

						System.out.println(bothTitleAndCreditCommand);
						builder = new ProcessBuilder("/bin/bash", "-c",
								bothTitleAndCreditCommand);
					}
				}

				// if the user wants to use a default frame or select a frame
				// from the video
				else {
					String inputFrameTime;
					String inputFrameTime1;

					// if the user has saved title screen settings but does not
					// want to implement credit screen
					if (fileTitle.exists() && !fileCredit.exists()) {
						StringBuilder finalTitleCommand = new StringBuilder();
						// if they choose to use a defaultFrame
						if (TextEditorGui.backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001
							inputFrameTime = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshot will be taken at the time specified by
							// the user
							inputFrameTime = TextEditorGui.titleFrameTime;
						}
						// take a screenshot from the video at the given
						// inputFrametime
						finalTitleCommand.append("avconv -i " + firstInput
								+ " -ss " + inputFrameTime
								+ " -f image2 -vframes 1 " + hiddenDir
								+ "/out.png");
						finalTitleCommand.append(";");
						// create a video from image for the duration given by
						// the user
						finalTitleCommand
						.append("avconv -loop 1 -shortest -y -i "
								+ hiddenDir + "/out.png -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalTitleCommand.append(";");
						// add text
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.titleFontSize + ":fontcolor="
								+ TextEditorGui.titleFontColour + "\" -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalTitleCommand.append(";");

						// concatenate the videos together = gives the final
						// output
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/text.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file1.ts ; avconv -ss 0"
								+ " -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file2.ts; avconv -i concat:\""
								+ hiddenDir
								+ "/file1.ts|"
								+ hiddenDir
								+ "/file2.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalTitleCommand.append(";");

						titleCommand = finalTitleCommand.toString();
						builder = new ProcessBuilder("/bin/bash", "-c",
								titleCommand);
					}
					// if the user has saved credit screen settings but does not
					// want to implement title screen
					else if (!fileTitle.exists() && fileCredit.exists()) {
						StringBuilder finalCreditCommand = new StringBuilder();
						// if they choose to use a defaultFrame
						if (TextEditorGui.backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001
							inputFrameTime = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshot will be taken at the time specified by
							// the user
							inputFrameTime = TextEditorGui.creditFrameTime;
						}
						// take a screenshot from the video at the given
						// inputFrametime
						finalCreditCommand.append("avconv -i " + firstInput
								+ " -ss " + inputFrameTime
								+ " -f image2 -vframes 1 " + hiddenDir
								+ "/out.png");
						finalCreditCommand.append(";");
						// create a video from image for the duration given by
						// the user
						finalCreditCommand
						.append("avconv -loop 1 -shortest -y -i "
								+ hiddenDir + "/out.png -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalCreditCommand.append(";");
						// add text
						finalCreditCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.creditFontSize + ":fontcolor="
								+ TextEditorGui.creditFontColour + "\" -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/text1.mp4");
						finalCreditCommand.append(";");
						// concatenate the videos together = gives the final
						// output
						finalCreditCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file1.ts ; avconv -ss 0 -i "
								+ hiddenDir
								+ "/text1.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file2.ts; avconv -i concat:\""
								+ hiddenDir
								+ "/file1.ts|"
								+ hiddenDir
								+ "/file2.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalCreditCommand.append(";");
						creditCommand = finalCreditCommand.toString();
						builder = new ProcessBuilder("/bin/bash", "-c",
								creditCommand);
					}

					// if the user has saved settings for both title screen and
					// credit screen - wants to implement both
					else {
						StringBuilder finalBothCommand = new StringBuilder();
						// if they choose to use a defaultFrame
						if (TextEditorGui.backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001 for both
							// title screen and credit screen
							inputFrameTime = "00:00:00.001";
							inputFrameTime1 = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshots will be taken at the time specified
							// by the user
							inputFrameTime = TextEditorGui.titleFrameTime;
							inputFrameTime1 = TextEditorGui.creditFrameTime;
						}
						// take a screenshot from the video at the given
						// inputFrametime for title screen
						finalBothCommand.append("avconv -i " + firstInput
								+ " -ss " + inputFrameTime
								+ " -f image2 -vframes 1 " + hiddenDir
								+ "/out.png");
						finalBothCommand.append(";");
						// take a screenshot from the video at the given
						// inputFrametime for credit screen
						finalBothCommand.append("avconv -i " + firstInput
								+ " -ss " + inputFrameTime1
								+ " -f image2 -vframes 1 " + hiddenDir
								+ "/out1.png");
						finalBothCommand.append(";");
						// create video from image for the duration given by the
						// user for title screen
						finalBothCommand
						.append("avconv -loop 1 -shortest -y -i "
								+ hiddenDir + "/out.png -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalBothCommand.append(";");
						// create video from image for the duration given by the
						// user for credit screen
						finalBothCommand
						.append("avconv -loop 1 -shortest -y -i "
								+ hiddenDir + "/out1.png -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/result1.mp4");
						finalBothCommand.append(";");
						// add text for title screen
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.titleFontSize + ":fontcolor="
								+ TextEditorGui.titleFontColour + "\" -t "
								+ TextEditorGui.titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalBothCommand.append(";");
						// add text for credit screen
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result1.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ TextEditorGui.fontDir
								+ TextEditorGui.creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ TextEditorGui.creditFontSize + ":fontcolor="
								+ TextEditorGui.creditFontColour + "\" -t "
								+ TextEditorGui.creditDuration + " -y " + hiddenDir
								+ "/text1.mp4");
						finalBothCommand.append(";");

						// concatenate the videos together = gives the final
						// output
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/text.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file3.ts ; avconv -ss 0 -i "
								+ firstInput
								+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file5.ts; avconv -ss 0 -i "
								+ hiddenDir
								+ "/text1.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
								+ hiddenDir
								+ "/file6.ts ; avconv -i concat:\""
								+ hiddenDir
								+ "/file3.ts|"
								+ hiddenDir
								+ "/file5.ts|"
								+ hiddenDir
								+ "/file6.ts\" -c copy -bsf:a aac_adtstoasc -y "
								+ lastOutput);
						finalBothCommand.append(";");

						bothTitleAndCreditCommand = finalBothCommand.toString();

						System.out.println(bothTitleAndCreditCommand);
						builder = new ProcessBuilder("/bin/bash", "-c",
								bothTitleAndCreditCommand);
					}
				}

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
