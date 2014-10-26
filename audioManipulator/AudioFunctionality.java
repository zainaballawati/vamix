package audioManipulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import mediaPlayer.PlayerGui;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class AudioFunctionality {

	private static String projectPath;
	private static String hiddenDir;
	private static String videoPath;
	private static String videoLength;
	private static String workingDir;
	private static String audioFields;
	
	private String removeCmd = "";
	private String extractCmd = "";
	private String addCmd = "";
	
	// Initializing the swing worker BackgroundTask
	private BackgroundTask longTask;

	/**
	 * Allows the user to play the selected audio
	 */
	public static void playAudio() {
		
		//Get the video path and length
		setVideoInfo();

		//Check the validity of the user input
		boolean addChecksPassed = AudioErrorHandling.allChecksAdd();

		// If all checks are passed, run a bash command avplay to enable the
		// user to preview the audio file they have chosen
		if (addChecksPassed) {
			String cmd = "avplay -i " + AudioGui.inputFile
					+ " -window_title playChosenAudio -x 400 -y 100";
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
					cmd);
			Process process;
			try {
				process = builder.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Changes a string in the format hh:mm:ss to seconds only
	 * @param longFormat: string of the format hh:mm:ss
	 */
	public static int convertToSeconds(String longFormat) {

		// Reference for convertingToSeconds:
		// http://stackoverflow.com/questions/11994790/parse-time-of-format-hhmmss

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = null;

		// parse the input to a Date format
		try {
			date = sdf.parse(longFormat);
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
	 * Stores the video info from the project file to the private fields
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
	 * Method run all the audio manipulating commands in a background thread
	 * @return exit status
	 */
	public int runCommands(String input, String output) {
		//Set the project fields
		setVideoInfo();
		// Create the swingWorker class and execute it
		longTask = new BackgroundTask(input, output);
		longTask.execute();
		try {
			return longTask.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 1;
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
				String tempOut = "";

				// if remove is enabled and add is not enabled, constructs the
				// command for removing the audio from the input file
				if (AudioGui.removeEnable) {
					if (!AudioGui.addEnable) {

						// remove audio
						StringBuilder bigRemoveCmd = new StringBuilder();
						bigRemoveCmd.append("avconv -i " + firstInput
								+ " -an -y " + lastOutput);

						removeCmd = bigRemoveCmd.toString();
					}

				}

				// if extract is enabled, constructs the command for extract
				if (AudioGui.extractEnable) {

					StringBuilder bigExtractCmd = new StringBuilder();

					// if duration is not enabled - extract the .mp3 file
					// without a start time and a length
					if (!AudioGui.extractDurationEnable) {
						bigExtractCmd.append("avconv -i " + firstInput
								+ " -vcodec copy -y -vn ");
						bigExtractCmd.append(workingDir + "/");
						bigExtractCmd.append(AudioGui.outputFileName.getText());
						bigExtractCmd.append(".mp3");
						extractCmd = bigExtractCmd.toString();
					}

					// if duration is enabled - extract the .mp3 file with a
					// start time and a length
					else {
						bigExtractCmd.append("avconv -i " + firstInput
								+ " -vcodec copy -ss ");
						bigExtractCmd.append(AudioGui.startTimeExtract.getText());
						bigExtractCmd.append(" -t ");
						bigExtractCmd.append(AudioGui.lengthExtract.getText());
						bigExtractCmd.append(" -y -vn ");
						bigExtractCmd.append(workingDir + "/");
						bigExtractCmd.append(AudioGui.outputFileName.getText());
						bigExtractCmd.append(".mp3");

						extractCmd = bigExtractCmd.toString();
					}
				}

				// if extract is enabled, constructs the command for add
				if (AudioGui.addEnable) {

					StringBuilder bigAddCmd = new StringBuilder();


					// if remove is enabled as well, just replace the audio stream
					if (AudioGui.removeEnable) {
						bigAddCmd.append("avconv -i " + firstInput + " -i ");
						bigAddCmd.append(AudioGui.inputFile);
						bigAddCmd
						.append(" -ss 0 -map 0:0 -map 1:0 -c:v copy -c:a copy -t ");
						bigAddCmd.append(videoLength);
						bigAddCmd.append(" -y " + lastOutput);
						addCmd = bigAddCmd.toString();
					}

					// if remove is not enabled
					else {

						//if duration is not enabled - merge the two audio streams using the filter amix=inputs=2
						//Reference to Nasser's slides 
						if (!AudioGui.addDurationEnable) {
							bigAddCmd
							.append("avconv -i " + firstInput + " -i ");
							bigAddCmd.append(AudioGui.inputFile);
							bigAddCmd
							.append(" -map 0:v -map 1:a -codec:v libx264 -preset medium -crf 23 -codec:a aac -strict experimental -b:a 192k -filter_complex amix=inputs=2 -t ");
							bigAddCmd.append(videoLength);
							bigAddCmd.append(" -y " + lastOutput);
							addCmd = bigAddCmd.toString();
						} 

						//if duration is enabled - merge the two audio streams using the filted amix=inputs=2
						//Reference to Nasser's slides with a specified start time and duration
						else {

							//convert the start time from hh:mm:ss to seconds
							int startTimeInSeconds = AudioFunctionality.convertToSeconds(AudioGui.startTimeAdd
									.getText());
							//convert the length from hh:mm:ss to seconds
							int lengthInSeconds = AudioFunctionality.convertToSeconds(AudioGui.lengthAdd
									.getText());

							//add the start time and length to get the stopTime
							int stopTimeInSeconds = startTimeInSeconds
									+ lengthInSeconds;

							//convert to strings
							String startTime = "" + startTimeInSeconds;
							String length = "" + lengthInSeconds;
							String stopTime = "" + stopTimeInSeconds;

							// temp file Selection.mp4 with the video selection that they want to add audio too
							// then merge the audio streams of the input audio file and selection
							// then concatenate the corresponding segments of the video together
							bigAddCmd.append("avconv -i " + firstInput
									+ " -ss ");
							bigAddCmd.append(startTime);
							bigAddCmd.append(" -t ");
							bigAddCmd.append(length);
							bigAddCmd.append(" -strict experimental -y "
									+ hiddenDir + "/Selection.mp4; avconv -i "
									+ hiddenDir + "/Selection.mp4 -i ");
							bigAddCmd.append(AudioGui.inputFile);
							bigAddCmd
							.append(" -map 0:v -map 1:a -codec:v libx264 -preset medium -crf 23 -acodec aac -strict experimental -b:a 192k -filter_complex amix=inputs=2 -t "
									+ length
									+ " -y "
									+ hiddenDir
									+ "/out.mp4; avconv -ss 0 -i "
									+ firstInput
									+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t ");
							bigAddCmd.append(startTime);
							bigAddCmd
							.append(" -y "
									+ hiddenDir
									+ "/file1.ts ; avconv -ss 0 -i "
									+ hiddenDir
									+ "/out.mp4 -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -t "
									+ length + " -y " + hiddenDir
									+ "/file2.ts; avconv -ss ");
							bigAddCmd.append(stopTime); // start+duration
							bigAddCmd
							.append(" -i "
									+ firstInput
									+ " -vcodec libx264 -acodec aac -bsf:v h264_mp4toannexb -f mpegts -strict experimental -y "
									+ hiddenDir
									+ "/file3.ts ; avconv -i concat:\""
									+ hiddenDir
									+ "/file1.ts|"
									+ hiddenDir
									+ "/file2.ts|"
									+ hiddenDir
									+ "/file3.ts\" -c copy -bsf:a aac_adtstoasc -y "
									+ lastOutput);


							addCmd = bigAddCmd.toString();
						}

					}
				}


				//construct the final command for audio
				StringBuilder finalCommand = new StringBuilder();

				// if both remove and add are not enabled together
				if (!(AudioGui.removeEnable && AudioGui.addEnable)) {
					if (AudioGui.removeEnable) {
						finalCommand.append(removeCmd);
						finalCommand.append(";");
					}
					if (AudioGui.extractEnable) {
						finalCommand.append(extractCmd);
						finalCommand.append(";");
					}
					if (AudioGui.addEnable) {
						finalCommand.append(addCmd);
						finalCommand.append(";");
					}
				} 

				//if both remove and add are enabled together, don't perform remove
				else {
					finalCommand.append(addCmd);
					finalCommand.append(";");
					if (AudioGui.extractEnable) {
						finalCommand.append(extractCmd);
						finalCommand.append(";");
					}
				}

				//start the builder for the bash command so it is executed
				String cmd = finalCommand.toString();
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
