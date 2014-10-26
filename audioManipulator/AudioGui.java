package audioManipulator;

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
 */

public class AudioGui extends JPanel implements ItemListener,
ActionListener {

	private static final long serialVersionUID = 1L;

	// Initializing the text for the buttons
	private final String TEXT_SAVE = "Save";
	private final String TEXT_CHOOSE = "Choose Audio";
	private final static String TEXT_PLAY_AUDIO = "Play Audio";

	// // Initializing the labels
	private JLabel audioManipulatorLabel = new JLabel("Audio Manipulator");
	private JLabel optionalLabel = new JLabel("(* = Optional)");
	public static JLabel starLabel2 = new JLabel("*");
	private JLabel starLabel3 = new JLabel("*");
	public static JLabel startTimeLabelExtract = new JLabel("Start Time: ");
	private JLabel startTimeLabelAdd = new JLabel("Start Time: ");
	public static JLabel lengthLabelExtract = new JLabel("Length: ");
	private JLabel lengthLabelAdd = new JLabel("Length: ");
	public static JLabel outputFileNameLabel = new JLabel("Output File Name: ");
	public static JLabel mp3Label = new JLabel(".mp3");
	private JLabel inputAudioLabel = new JLabel("Add audio file to project");

	// Initializing the textFields
	public static JTextField startTimeExtract = new JTextField("");
	public static JTextField startTimeAdd = new JTextField("");
	public static JTextField lengthExtract = new JTextField("");
	public static JTextField lengthAdd = new JTextField("");
	public static JTextField outputFileName = new JTextField("");

	// Initializing the buttons
	private JButton inputAudioButton = new JButton(TEXT_CHOOSE);
	private JButton saveButton = new JButton(TEXT_SAVE);
	public static JButton playButton = new JButton(TEXT_PLAY_AUDIO);

	// Initializing the check boxes
	public static JCheckBox removeCheck = new JCheckBox("Remove audio");
	public static JCheckBox extractCheck = new JCheckBox("Extract audio");
	public static JCheckBox addCheck = new JCheckBox("Add audio");

	// Initializing the radio buttons
	public static JRadioButton extractDurationCheck = new JRadioButton(
			"Set Audio Duration");
	private static JRadioButton addDurationCheck = new JRadioButton(
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
	public static boolean removeEnable = false;
	public static boolean extractEnable = false;
	public static boolean extractDurationEnable = false;
	public static boolean addEnable = false;
	public static boolean addDurationEnable = false;

	private JFileChooser chooser = new JFileChooser();
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3 files", "mp3");
	public static String inputFile;

	// Initializing the Strings
	private String removeCmd = "";
	private String extractCmd = "";
	private String addCmd = "";
	private String projectPath;
	private String hiddenDir;
	private String videoPath;
	private String videoLength;
	private static String workingDir;
	private static String audioFields;

	public AudioFunctionality audioWork = new AudioFunctionality();




	/**
	 * Constructor for AudioManipulator() -Sets up the GUI for audio
	 * manipulation tab -Sets up the default layout
	 */

	public AudioGui() {

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

		// setting the size for the separators
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

		// @reference for JCheckBox:
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

		if (e.getSource() == inputAudioButton) {

			//Show only correct extension type file by default
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(null);

			// Store the chosen file
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				inputFile = chooser.getSelectedFile().toString();
				playButton.setEnabled(true);
			}

		} else if (e.getSource() == saveButton) {

			// Get the video path and length
			AudioFunctionality.setVideoInfo();
			//Save the audio manipulating fields to a file
			saveToFile();

		} else if (e.getSource() == playButton) {
			AudioFunctionality.playAudio();
		}
	}

	/**
	 * enableAudioMan Method enables or disable all the fields in the audio
	 * manipulating tab depending on the state
	 * 
	 * @param state
	 */

	public void enableAudioMan(boolean state) {
		removeCheck.setEnabled(state);
		extractCheck.setEnabled(state);
		addCheck.setEnabled(state);
		saveButton.setEnabled(state);
	}

	/**
	 * enableExtractOnly() Method enables only extract option (is called when
	 * the input file is an audio file)
	 */

	public void enableExtractOnly() {
		removeCheck.setEnabled(false);
		extractCheck.setEnabled(true);
		addCheck.setEnabled(false);
		saveButton.setEnabled(true);
	}


	/**
	 * Saves all the current field values into an external file
	 */
	public static void saveToFile() {

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
			boolean passedAll = AudioErrorHandling.allChecksAudio();
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

	/**
	 * Sets all the fields of the audio class to the previously saved values
	 * @param audioFieldsPath: the path of the stored audio fields
	 */
	protected static void setAllFields(String audioFieldsPath) {

		File f = new File(audioFieldsPath);
		try {

			// read the audioFields file and sets the fields for audio
			// manipulation depending on the last save

			BufferedReader reader;
			reader = new BufferedReader(new FileReader(f));
			removeCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			extractCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			outputFileName.setText(reader.readLine());
			extractDurationCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
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
			addDurationCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			startTimeAdd.setText(reader.readLine());
			lengthAdd.setText(reader.readLine());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
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

	
}