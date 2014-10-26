package vamix;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * SoftEng206 Assignment3 - audio manipulator class
 * @author Chahat Chawla and Zainab Al Lawati
 *
 */

public class AudioManipulator extends JPanel implements ItemListener,
		ActionListener {

	// Initializing the text for the buttons
	private final String TEXT_SAVE = "Save";
	private final String TEXT_CHOOSE = "Choose Audio";
	private final String TEXT_PLAY_AUDIO = "Play Audio";

	// // Initializing the labels
	private JLabel audioManipulatorLabel = new JLabel("Audio Manipulator");
	private JLabel optionalLabel = new JLabel("(* = Optional)");
	private JLabel starLabel2 = new JLabel("*");
	private JLabel starLabel3 = new JLabel("*");
	private JLabel startTimeLabelExtract = new JLabel("Start Time: ");
	private JLabel startTimeLabelAdd = new JLabel("Start Time: ");
	private JLabel lengthLabelExtract = new JLabel("Length: ");
	private JLabel lengthLabelAdd = new JLabel("Length: ");
	private JLabel outputFileNameLabel = new JLabel("Output File Name: ");
	private JLabel mp3Label = new JLabel(".mp3");
	private JLabel inputAudioLabel = new JLabel("Add audio file to project");

	// Initializing the textFields
	private JTextField startTimeExtract = new JTextField("");
	private JTextField startTimeAdd = new JTextField("");
	private JTextField lengthExtract = new JTextField("");
	private JTextField lengthAdd = new JTextField("");
	private JTextField outputFileName = new JTextField("");

	// Initializing the buttons
	private JButton inputAudioButton = new JButton(TEXT_CHOOSE);
	private JButton saveButton = new JButton(TEXT_SAVE);
	private JButton playButton = new JButton(TEXT_PLAY_AUDIO);

	// Initializing the check boxes
	private JCheckBox removeCheck = new JCheckBox("Remove audio");
	private JCheckBox extractCheck = new JCheckBox("Extract audio");
	private JCheckBox addCheck = new JCheckBox("Add audio");

	// Initializing the radio buttons
	private JRadioButton extractDurationCheck = new JRadioButton(
			"Set Audio Duration");
	private JRadioButton addDurationCheck = new JRadioButton(
			"Set Video Duration");

	// Initializing the separators
	private JLabel separator = new JLabel("");
	private JLabel separator2 = new JLabel("");
	private JLabel separator3 = new JLabel("");
	private JLabel separator4 = new JLabel("");
	private JLabel separator5 = new JLabel("");
	private JLabel separator6 = new JLabel("");
	private JLabel separator7 = new JLabel("");
	private JLabel separator8 = new JLabel("");
	private JLabel separator9 = new JLabel("");
	private JLabel separator10 = new JLabel("");

	// Initializing the enable booleans
	private boolean removeEnable = false;
	private boolean extractEnable = false;
	private boolean extractDurationEnable = false;
	private boolean addEnable = false;
	private boolean addDurationEnable = false;

	// Initializing the components for JFileChooser()
	private JFileChooser chooser = new JFileChooser();
	private String inputFile;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"mp3 files", "mp3");

	// Initializing the Strings
	private String removeCmd = "";
	private String extractCmd = "";
	private String addCmd = "";
	private String projectPath;
	private String hiddenDir;
	private String videoPath;
	private String videoLength;
	private String workingDir;
	private String audioFields;
	

	// Initializing the swing worker BackgroundTask
	private BackgroundTask longTask;

	/**
	 * Constructor for AudioManipulator() -Sets up the GUI for audio
	 * manipulation tab -Sets up the default layout
	 */

	public AudioManipulator() {

		// change the font of the title, subTitles and starLabels
		audioManipulatorLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		audioManipulatorLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		starLabel2.setFont(new Font("TimesRoman", Font.BOLD, 18));
		starLabel3.setFont(new Font("TimesRoman", Font.BOLD, 18));
		removeCheck.setFont(new Font("Dialog", Font.BOLD, 15));
		extractCheck.setFont(new Font("Dialog", Font.BOLD, 15));
		addCheck.setFont(new Font("Dialog", Font.BOLD, 15));

		// set the columns of the textFields
		startTimeExtract.setColumns(6);
		startTimeAdd.setColumns(6);
		lengthExtract.setColumns(6);
		lengthAdd.setColumns(6);
		outputFileName.setColumns(15);

		// set the size of the buttons
		inputAudioButton.setPreferredSize(new Dimension(140, 20));
		saveButton.setPreferredSize(new Dimension(150, 25));
		playButton.setPreferredSize(new Dimension(130, 20));

		// add item to the implemented listeners
		removeCheck.addItemListener(this);
		extractCheck.addItemListener(this);
		extractDurationCheck.addItemListener(this);
		addCheck.addItemListener(this);
		addDurationCheck.addItemListener(this);
		inputAudioButton.addActionListener(this);
		saveButton.addActionListener(this);
		playButton.addActionListener(this);

		// adding all components to the Panel
		add(audioManipulatorLabel);
		add(separator);
		add(optionalLabel);
		add(separator2);
		add(removeCheck);
		add(separator3);
		add(separator4);
		add(extractCheck);
		add(separator5);
		add(outputFileNameLabel);
		add(outputFileName);
		add(mp3Label);
		add(separator6);
		add(starLabel2);
		add(extractDurationCheck);
		add(startTimeLabelExtract);
		add(startTimeExtract);
		add(lengthLabelExtract);
		add(lengthExtract);
		add(separator7);
		add(addCheck);
		add(separator8);
		add(inputAudioLabel);
		add(inputAudioButton);
		add(playButton);
		add(separator9);
		add(starLabel3);
		add(addDurationCheck);
		add(startTimeLabelAdd);
		add(startTimeAdd);
		add(lengthLabelAdd);
		add(lengthAdd);
		add(separator10);
		add(saveButton);

		// setting the size for the seperators
		separator.setPreferredSize(new Dimension(525, 10));
		separator2.setPreferredSize(new Dimension(525, 10));
		separator3.setPreferredSize(new Dimension(525, 0));
		separator4.setPreferredSize(new Dimension(525, 10));
		separator5.setPreferredSize(new Dimension(525, 0));
		separator6.setPreferredSize(new Dimension(525, 0));
		separator7.setPreferredSize(new Dimension(525, 10));
		separator8.setPreferredSize(new Dimension(525, 0));
		separator9.setPreferredSize(new Dimension(525, 0));
		separator10.setPreferredSize(new Dimension(525, 10));

		// disabling components that are not accessible in the default screen
		outputFileNameLabel.setEnabled(false);
		outputFileName.setEnabled(false);
		mp3Label.setEnabled(false);
		starLabel2.setEnabled(false);
		extractDurationCheck.setEnabled(false);
		startTimeLabelExtract.setEnabled(false);
		startTimeExtract.setEnabled(false);
		lengthLabelExtract.setEnabled(false);
		lengthExtract.setEnabled(false);
		inputAudioLabel.setEnabled(false);
		inputAudioButton.setEnabled(false);
		playButton.setEnabled(false);
		starLabel3.setEnabled(false);
		addDurationCheck.setEnabled(false);
		startTimeLabelAdd.setEnabled(false);
		startTimeAdd.setEnabled(false);
		lengthLabelAdd.setEnabled(false);
		lengthAdd.setEnabled(false);
		playButton.setEnabled(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		// Reference for JCheckBox:
		// http://www.java2s.com/Code/Java/Swing-JFC/SwingCheckBoxDemo.htm

		// if the removeCheck is clicked
		if (e.getItemSelectable() == removeCheck) {

			// if the removeCheck is enabled, set removeEnable to true and if
			// add is enabled as well, disable duration check for add
			if (e.getStateChange() == 1) {
				removeEnable = true;
				if (addEnable) {
					addDurationCheck.setEnabled(false);
				}

			}

			// if the removeCheck is disabled, set removeEnable to false and if
			// add is enabled as well, enable duration check for add
			else {
				if (addEnable) {
					addDurationCheck.setEnabled(true);
				}
				removeEnable = false;
			}
		}

		// if the extractCheck is clicked
		else if (e.getItemSelectable() == extractCheck) {

			// if the extractCheck is enabled, set extractEnable to true and
			// enable the user to add an output file name and set duration
			if (e.getStateChange() == 1) {
				outputFileNameLabel.setEnabled(true);
				outputFileName.setEnabled(true);
				mp3Label.setEnabled(true);
				starLabel2.setEnabled(true);
				extractDurationCheck.setEnabled(true);

				extractEnable = true;

			}
			// if the extractCheck is disabled, set extractEnable and
			// extractDurationEnable to false and set the extract options to
			// default
			else {
				outputFileNameLabel.setEnabled(false);
				outputFileName.setEnabled(false);
				outputFileName.setText("");
				mp3Label.setEnabled(false);
				starLabel2.setEnabled(false);
				extractDurationCheck.setEnabled(false);
				startTimeLabelExtract.setEnabled(false);
				startTimeExtract.setEnabled(false);
				startTimeExtract.setText("");
				lengthLabelExtract.setEnabled(false);
				lengthExtract.setEnabled(false);
				lengthExtract.setText("");

				extractEnable = false;
				extractDurationEnable = false;
			}
		}

		// if the addCheck is clicked
		else if (e.getItemSelectable() == addCheck) {

			// if the addCheck is enabled, set addEnable to true and
			// enable the user to choose an input .mp3 file
			if (e.getStateChange() == 1) {
				inputAudioLabel.setEnabled(true);
				inputAudioButton.setEnabled(true);
				starLabel3.setEnabled(true);

				addEnable = true;

				// if removeEnable is also true, disable the set duration option
				// if removeEnable is false, enable the set duration option
				if (removeEnable) {
					addDurationCheck.setEnabled(false);
				} else {
					addDurationCheck.setEnabled(true);
				}

			}

			// if the addCheck is disabled, set addEnable and
			// addDurationEnable to false and set the add options to
			// default
			else {
				inputAudioLabel.setEnabled(false);
				inputAudioButton.setEnabled(false);
				playButton.setEnabled(false);
				starLabel2.setEnabled(false);
				inputFile = "";

				addDurationCheck.setSelected(false);
				startTimeLabelAdd.setEnabled(false);
				startTimeAdd.setEnabled(false);
				startTimeAdd.setText("");
				lengthLabelAdd.setEnabled(false);
				lengthAdd.setEnabled(false);
				lengthAdd.setText("");

				addDurationEnable = false;
				addEnable = false;
			}
		}

		// Reference for JRadioButton:
		// http://www.tutorialspoint.com/swing/swing_jradiobutton.htm

		// if the extractDurationCheck is clicked
		else if (e.getItemSelectable() == extractDurationCheck) {

			// if the extractDurationCheck is enabled, enable duration options
			if (e.getStateChange() == 1) {

				startTimeLabelExtract.setEnabled(true);
				startTimeExtract.setEnabled(true);
				lengthLabelExtract.setEnabled(true);
				lengthExtract.setEnabled(true);
				extractDurationEnable = true;
			}

			// if the extractDurationCheck is disabled, disable duration options
			else {
				startTimeLabelExtract.setEnabled(false);
				startTimeExtract.setEnabled(false);
				lengthLabelExtract.setEnabled(false);
				lengthExtract.setEnabled(false);
				extractDurationEnable = false;
			}
		}

		// if the addDurationCheck is clicked
		else if (e.getItemSelectable() == addDurationCheck) {

			// if the addDurationCheck is enabled, enable duration options
			if (e.getStateChange() == 1) {
				startTimeLabelAdd.setEnabled(true);
				startTimeAdd.setEnabled(true);
				lengthLabelAdd.setEnabled(true);
				lengthAdd.setEnabled(true);
				addDurationEnable = true;
			}

			// if the addDurationCheck is enabled, enable duration options
			else {
				startTimeLabelAdd.setEnabled(false);
				startTimeAdd.setEnabled(false);
				lengthLabelAdd.setEnabled(false);
				lengthAdd.setEnabled(false);
				addDurationEnable = false;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// If the inputAudioButton is clicked
		if (e.getSource() == inputAudioButton) {

			// Reference for JFileChooser():
			// http://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html

			// Show only correct extension type file by default
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);
			// Store the chosen file
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputFile = chooser.getSelectedFile().toString();
				playButton.setEnabled(true);
			}

		}

		// If the save button is clicked
		else if (e.getSource() == saveButton) {

			// Get the video path and length
			setVideoInfo();

			// Reference for JOptionPane():
			// http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html

			// check if the file audioFields exists in the working directory
			File f = new File(workingDir + "/.audioFields");
			if (f.exists()) {

				// Allow user to choose either overwriting the existing changes
				// or keep them if the file exists
				Object[] existOptions = { "Cancel", "Overwrite" };
				int optionChosen = JOptionPane.showOptionDialog(null,
						"Do you want to overwrite the previous changes?",
						"File Exists!", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, existOptions,
						existOptions[0]);
				if (optionChosen == 0) { // If cancel, go back to main menu
					setAllFields(workingDir + "/.audioFields");
					return;
				}
			}
			// Save all user inputs to the hidden audioFields text
			try {
				boolean passedAll = allChecksAudio();
				if (passedAll) {
					FileWriter fw = new FileWriter(f);
					BufferedWriter bw = new BufferedWriter(fw);
					// Add remove audio fields
					bw.write(removeEnable + "\n");
					// Add extract audio fields
					bw.write(extractEnable + "\n");
					bw.write(outputFileName.getText() + "\n");
					bw.write(extractDurationEnable + "\n");
					bw.write(startTimeExtract.getText() + "\n");
					bw.write(lengthExtract.getText() + "\n");
					// Add overlaying audio fields
					bw.write(addEnable + "\n");
					bw.write(inputFile + "\n");
					bw.write(addDurationEnable + "\n");
					bw.write(startTimeAdd.getText() + "\n");
					bw.write(lengthAdd.getText() + "\n");
					bw.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// If the play button is clicked
		if (e.getSource() == playButton) {

			// Get the video path and length
			setVideoInfo();

			// Run the allChecksAdd() to check whether or not the user inputs
			// are valid
			boolean addChecksPassed = allChecksAdd();

			// If all checks are passed, run a bash command avplay to enable the
			// user to preview the audio file they chose
			if (addChecksPassed) {
				String cmd = "avplay -i " + inputFile
						+ " -window_title playChosenAudio -x 400 -y 100";
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",
						cmd);
				Process process;
				try {
					process = builder.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * enableAudioMan Method enables or disable all the fields in the audio
	 * manipulating tab depending on the state
	 * 
	 * @param state
	 */

	protected void enableAudioMan(boolean state) {
		removeCheck.setEnabled(state);
		extractCheck.setEnabled(state);
		addCheck.setEnabled(state);
		saveButton.setEnabled(state);
	}

	/**
	 * enableExtractOnly() Method enables only extract option (is called when
	 * the input file is an audio file)
	 */

	protected void enableExtractOnly() {
		removeCheck.setEnabled(false);
		extractCheck.setEnabled(true);
		addCheck.setEnabled(false);
		saveButton.setEnabled(true);
	}

	/**
	 * setAllFields Method sets all the fields of the audio class to the
	 * previously saved values
	 * 
	 * @param audioFieldsPath
	 *            : the path of the stored audio fields
	 */
	protected void setAllFields(String audioFieldsPath) {

		audioFields = audioFieldsPath;
		File f = new File(audioFields);
		try {

			// read the audioFields file and sets the fields for audio
			// manipulation depending on the last save

			BufferedReader reader;
			reader = new BufferedReader(new FileReader(f));
			removeCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			extractCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			outputFileName.setText(reader.readLine());
			extractDurationCheck.setSelected(Boolean.parseBoolean(reader
					.readLine()));
			startTimeExtract.setText(reader.readLine());
			lengthExtract.setText(reader.readLine());
			addCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			inputFile = reader.readLine();
			if (inputFile != null) { // If audio was imported, enable playing it
				File audio = new File(inputFile);
				if (audio.exists()) {
					playButton.setEnabled(true);
				}
			}
			addDurationCheck
					.setSelected(Boolean.parseBoolean(reader.readLine()));
			startTimeAdd.setText(reader.readLine());
			lengthAdd.setText(reader.readLine());
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * setVideoInfo Method stores the video info from the hidden file to the
	 * private fields
	 * 
	 * @param hiddenDir
	 */
	protected void setVideoInfo() {
		// Get the main project file
		projectPath = Menu.getProjectPath();
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
	 * refreshAudioMan Method refreshes all the fields in the audio tab
	 */
	protected void refreshAudioMan() {

		startTimeExtract.setText("hh:mm:ss");
		startTimeAdd.setText("hh:mm:ss");
		lengthExtract.setText("hh:mm:ss");
		lengthAdd.setText("hh:mm:ss");
		outputFileName.setText("");
		removeCheck.setSelected(false);
		extractCheck.setSelected(false);
		addCheck.setSelected(false);
		extractDurationCheck.setSelected(false);
		addDurationCheck.setSelected(false);
		inputFile = "";
		projectPath = "";
		hiddenDir = "";
		videoPath = "";
		videoLength = "";
		audioFields = "";
		workingDir = "";

	}

	/**
	 * convertToSeconds Method changes a string in the format hh:mm:ss to
	 * seconds
	 * 
	 * @param longFormat
	 */
	private int convertToSeconds(String longFormat) {

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
	 * timeChecks Method checks whether the time inputs given by the user were
	 * in the hh:mm:ss format or not
	 * 
	 * @param startTime
	 *            , length
	 */

	private int timeChecks(String startTime, String length) {

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

	private boolean audioSignalCheck() {

		// Reference for avProbe:
		// https://libav.org/avprobe.html

		int count = 0;

		// count the lines using the avprobe bash command
		String cmd = "avprobe -loglevel error -show_streams " + videoPath
				+ " | grep -i streams.stream | wc -l";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.redirectErrorStream(true);
		Process process;
		try {
			process = builder.start();
			InputStream outStr = process.getInputStream();
			// read the output of the process
			BufferedReader stdout = new BufferedReader(new InputStreamReader(
					outStr));
			String line;
			line = stdout.readLine();
			count = Integer.parseInt(line); // Get the number of streams
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if the video file has no audio stream
		if (count == 2) {
			JOptionPane
					.showMessageDialog(
							null,
							"ERROR: no audio signal in the imported video, can not perform remove or extract");
			removeCheck.setEnabled(false);
			extractCheck.setEnabled(false);
			outputFileNameLabel.setEnabled(false);
			outputFileName.setEnabled(false);
			mp3Label.setEnabled(false);
			starLabel2.setEnabled(false);
			extractDurationCheck.setEnabled(false);
			startTimeLabelExtract.setEnabled(false);
			startTimeExtract.setEnabled(false);
			lengthLabelExtract.setEnabled(false);
			lengthExtract.setEnabled(false);
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

	private boolean allChecksExtract() {
		boolean passedOrNot = true;

		// if duration is enabled
		if (extractDurationEnable) {

			// check whether the input startTime and lengthTime is in the right
			// format or not using timeChecks()
			int passedTimeCheckExtract = timeChecks(startTimeExtract.getText()
					.trim(), lengthExtract.getText().trim());

			// if both are in the wrong format, inform the user and allow them
			// to enter the start time and length again
			if (passedTimeCheckExtract == 3) {
				startTimeExtract.setText("");
				lengthExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(
								null,
								"ERROR: start time and length for extract command can only be in the format hh:mm:ss");
			}

			// if start time is in the wrong format, inform the user and allow
			// them to enter the start time again
			else if (passedTimeCheckExtract == 1) {
				startTimeExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: start time for extract command can only be in the format hh:mm:ss");

			}

			// if length is in the wrong format, inform the user and allow them
			// to enter the length again
			else if (passedTimeCheckExtract == 2) {
				lengthExtract.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: length for extract command can only be in the format hh:mm:ss");
			}

			// check for whether the given duration + start time is smaller than
			// the length of the video or not
			else if (passedOrNot) {
				int convertedStartTime = convertToSeconds(startTimeExtract
						.getText().trim());
				int convertedLength = convertToSeconds(lengthExtract.getText()
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
					startTimeExtract.setText("");
					lengthExtract.setText("");
				}
			}
		}

		// check for whether the outputFileName field is empty
		if (outputFileName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null,
					"ERROR: please specify an output file name for extract");
		}

		// check whether the outputFileName specified user already exists in the
		// project directory
		String outputFile = workingDir + "/" + outputFileName.getText()
				+ ".mp3";
		File f = new File(outputFile);
		if (f.exists()) {

			// Reference for JOptionPane() as above.

			// Allow user to choose either overwriting the current file or
			// change the output file name
			Object[] existOptions = { "Cancel", "Overwrite" };
			int optionChosen = JOptionPane.showOptionDialog(null, "ERROR: "
					+ outputFileName.getText() + ".mp3 already exists. "
					+ "Do you want to overwrite the existing file?",
					"File Exists!", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, existOptions,
					existOptions[0]);
			if (optionChosen == 1) {
				f.delete(); // Delete the existing file
			} else {
				outputFileName.setText("");
			}
			passedOrNot = false;
		}
		return passedOrNot;
	}

	/**
	 * allChecksAdd Method does all the checks for add
	 */

	private boolean allChecksAdd() {
		boolean passedOrNot = true;

		// if duration is enabled
		if (addDurationEnable) {

			// check whether the input startTime and lengthTime is in the right
			// format or not using timeChecks()
			int passedTimeCheckAdd = timeChecks(startTimeAdd.getText(),
					lengthAdd.getText());

			// if both are in the wrong format, inform the user and allow them
			// to enter the start time and length again
			if (passedTimeCheckAdd == 3) {
				startTimeAdd.setText("");
				lengthAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(
								null,
								"ERROR: start time and length for add command can only be in the format hh:mm:ss");
			}

			// if start time is in the wrong format, inform the user and allow
			// them to enter the start time again
			else if (passedTimeCheckAdd == 1) {
				startTimeAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: start time for add command can only be in the format hh:mm:ss");
			}

			// if length is in the wrong format, inform the user and allow
			// them to enter the length again
			else if (passedTimeCheckAdd == 2) {
				lengthAdd.setText("");
				passedOrNot = false;
				JOptionPane
						.showMessageDialog(null,
								"ERROR: length for add can only be in the format hh:mm:ss");
			}

			// check for whether the given duration + start time is smaller than
			// the length of the video or not
			else if (passedOrNot) {
				int convertedStartTime = convertToSeconds(startTimeAdd
						.getText().trim());
				int convertedLength = convertToSeconds(lengthAdd.getText()
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
					startTimeAdd.setText("");
					lengthAdd.setText("");
				}
			}
		}

		// Reference to File.probeContentType
		// http://docs.oracle.com/javase/7/docs/api/java/nio/file/spi/FileTypeDetector.html

		// check for whether the input audio file chosen by the user is an audio
		// file or not
		File file = new File(inputFile);
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
									+ inputFile
									+ " does not refer to a valid audio file. Please select a new input file!");
			passedOrNot = false;
		}
		return passedOrNot;
	}

	/**
	 * allChecksAudio Method does all the checks for Audio Manipulator
	 */
	private boolean allChecksAudio() {
		boolean passedAllChecks = true;

		// do all the checks for extract
		if (extractEnable) {
			passedAllChecks = allChecksExtract();
		}

		// do all the checks for add
		if (addEnable) {
			passedAllChecks = allChecksAdd();
		}

		// do the check for audioSignal
		passedAllChecks = audioSignalCheck();
		return passedAllChecks;
	}

	/**
	 * Method run all the audio manipulating commands in a background thread
	 * 
	 * @return exit status
	 */
	public int runCommands(String input, String output) {
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
				if (removeEnable) {
					if (!addEnable) {

						// remove audio
						StringBuilder bigRemoveCmd = new StringBuilder();
						bigRemoveCmd.append("avconv -i " + firstInput
								+ " -an -y " + lastOutput);

						removeCmd = bigRemoveCmd.toString();
					}

				}

				// if extract is enabled, constructs the command for extract
				if (extractEnable) {

					StringBuilder bigExtractCmd = new StringBuilder();

					// if duration is not enabled - extract the .mp3 file
					// without a start time and a length
					if (!extractDurationEnable) {
						bigExtractCmd.append("avconv -i " + firstInput
								+ " -vcodec copy -y -vn ");
						bigExtractCmd.append(workingDir + "/");
						bigExtractCmd.append(outputFileName.getText());
						bigExtractCmd.append(".mp3");
						extractCmd = bigExtractCmd.toString();
					}

					// if duration is enabled - extract the .mp3 file with a
					// start time and a length
					else {
						bigExtractCmd.append("avconv -i " + firstInput
								+ " -vcodec copy -ss ");
						bigExtractCmd.append(startTimeExtract.getText());
						bigExtractCmd.append(" -t ");
						bigExtractCmd.append(lengthExtract.getText());
						bigExtractCmd.append(" -y -vn ");
						bigExtractCmd.append(workingDir + "/");
						bigExtractCmd.append(outputFileName.getText());
						bigExtractCmd.append(".mp3");

						extractCmd = bigExtractCmd.toString();
					}
				}
				
				// if extract is enabled, constructs the command for add
				if (addEnable) {

					StringBuilder bigAddCmd = new StringBuilder();

					
					// if remove is enabled as well, just replace the audio stream
					if (removeEnable) {
						bigAddCmd.append("avconv -i " + firstInput + " -i ");
						bigAddCmd.append(inputFile);
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
						if (!addDurationEnable) {
							bigAddCmd
									.append("avconv -i " + firstInput + " -i ");
							bigAddCmd.append(inputFile);
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
							int startTimeInSeconds = convertToSeconds(startTimeAdd
									.getText());
							//convert the length from hh:mm:ss to seconds
							int lengthInSeconds = convertToSeconds(lengthAdd
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
							bigAddCmd.append(inputFile);
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
				if (!(removeEnable && addEnable)) {
					if (removeEnable) {
						finalCommand.append(removeCmd);
						finalCommand.append(";");
					}
					if (extractEnable) {
						finalCommand.append(extractCmd);
						finalCommand.append(";");
					}
					if (addEnable) {
						finalCommand.append(addCmd);
						finalCommand.append(";");
					}
				} 
				
				//if both remove and add are enabled together, don't perform remove
				else {
					finalCommand.append(addCmd);
					finalCommand.append(";");
					if (extractEnable) {
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