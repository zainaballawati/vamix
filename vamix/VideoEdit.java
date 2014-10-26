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

public class VideoEdit extends JPanel implements ItemListener,
ActionListener {

	// Initializing the text for the buttons
	private final String TEXT_SAVE = "Save";
	private final String TEXT_CHOOSE = "Choose Audio";
	private final String TEXT_PLAY_AUDIO = "Play Audio";

	// // Initializing the labels
	private JLabel videoEditLabel = new JLabel("Video Edit");
	private JLabel widthLabel = new JLabel("Width:");
	private JLabel heightLabel = new JLabel("Height:");

	// Initializing the textFields
	private JTextField widthTxt = new JTextField("");
	private JTextField heightTxt = new JTextField("");
	private JButton saveButton = new JButton(TEXT_SAVE);

	// Initializing the check boxes
	private JCheckBox mirrorCheck = new JCheckBox("Create vertical mirror image");
	private JCheckBox resizeCheck = new JCheckBox("Resize video");
	private JCheckBox flipCheck = new JCheckBox("Flip Video");
	private JRadioButton vertical = new JRadioButton(
			"Vertically");

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
	private boolean mirrorEnable = false;
	private boolean verticalEnable = false;
	private boolean horizontalEnable = false;
	private boolean flipEnable = false;
	private boolean resizeEnable = false;

	// Initializing the components for JFileChooser()
	private JFileChooser chooser = new JFileChooser();
	private String inputFile;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"mp3 files", "mp3");

	// Initializing the Strings
	private String mirrorCmd = "";
	private String flipCmd = "";
	private String resizeCmd = "";
	private String projectPath;
	private String hiddenDir;
	private String videoPath;
	private String videoLength;
	private String workingDir;
	private String videoEditFields;

	private String width = "";
	private String height = "";


	// Initializing the swing worker BackgroundTask
	private BackgroundTask longTask;
	private final JRadioButton horizontal = new JRadioButton("Horizontally");
	private final JLabel label = new JLabel("");

	/**
	 * Constructor for AudioManipulator() -Sets up the GUI for audio
	 * manipulation tab -Sets up the default layout
	 */

	public VideoEdit() {

		// change the font of the title, subTitles and starLabels
		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		resizeCheck.setFont(new Font("Dialog", Font.BOLD, 15));
		flipCheck.setFont(new Font("Dialog", Font.BOLD, 15));

		// set the columns of the textFields
		widthTxt.setColumns(6);
		heightTxt.setColumns(6);
		saveButton.setPreferredSize(new Dimension(150, 25));
		resizeCheck.addItemListener(this);
		flipCheck.addItemListener(this);
		horizontal.addItemListener(this);
		vertical.addItemListener(this);
		saveButton.addActionListener(this);

		// adding all components to the Panel
		add(videoEditLabel);
		add(separator);
		add(separator2);
		add(separator3);
		add(separator4);
		add(resizeCheck);
		add(separator5);
		add(separator6);
		add(widthLabel);
		add(widthTxt);
		add(heightLabel);
		add(heightTxt);
		add(separator7);
		add(flipCheck);
		add(separator8);
		add(separator9);
		add(vertical);
		horizontal.setEnabled(false);

		add(horizontal);
		add(separator10);
		mirrorCheck.setFont(new Font("Dialog", Font.BOLD, 15));

		// add item to the implemented listeners
		mirrorCheck.addItemListener(this);
		add(mirrorCheck);
		label.setPreferredSize(new Dimension(525, 10));

		add(label);
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
		separator10.setPreferredSize(new Dimension(525, 15));
		widthLabel.setEnabled(false);
		widthTxt.setEnabled(false);
		heightLabel.setEnabled(false);
		heightTxt.setEnabled(false);
		vertical.setEnabled(false);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		// Reference for JCheckBox:
		// http://www.java2s.com/Code/Java/Swing-JFC/SwingCheckBoxDemo.htm

		// if the removeCheck is clicked
		if (e.getItemSelectable() == mirrorCheck) {

			// if the removeCheck is enabled, set removeEnable to true and if
			// add is enabled as well, disable duration check for add
			if (e.getStateChange() == 1) {
				mirrorEnable = true;

			}

			// if the removeCheck is disabled, set removeEnable to false and if
			// add is enabled as well, enable duration check for add
			else {
				mirrorEnable = false;
			}
		}

		// if the extractCheck is clicked
		else if (e.getItemSelectable() == resizeCheck) {

			// if the extractCheck is enabled, set extractEnable to true and
			// enable the user to add an output file name and set duration
			if (e.getStateChange() == 1) {

				resizeEnable = true;
				widthLabel.setEnabled(true);
				heightLabel.setEnabled(true);
				widthTxt.setEnabled(true);
				heightTxt.setEnabled(true);

			}
			// if the extractCheck is disabled, set extractEnable and
			// extractDurationEnable to false and set the extract options to
			// default
			else {

				resizeEnable = false;

				widthTxt.setEnabled(false);
				widthTxt.setText(width);
				heightTxt.setEnabled(false);
				heightTxt.setText(height);
				widthLabel.setEnabled(false);
				heightLabel.setEnabled(false);

			}
		}

		// if the addCheck is clicked
		else if (e.getItemSelectable() == flipCheck) {

			// if the addCheck is enabled, set addEnable to true and
			// enable the user to choose an input .mp3 file
			if (e.getStateChange() == 1) {

				vertical.setEnabled(true);
				horizontal.setEnabled(true);

			}

			// if the addCheck is disabled, set addEnable and
			// addDurationEnable to false and set the add options to
			// default
			else {

				vertical.setSelected(false);
				horizontal.setSelected(false);
				vertical.setEnabled(false);
				horizontal.setEnabled(false);
				
			}
		}


		// if the addDurationCheck is clicked
		else if (e.getItemSelectable() == vertical) {

			// if the addDurationCheck is enabled, enable duration options
			if (e.getStateChange() == 1) {
				verticalEnable = true;
				flipEnable = true;
				vertical.setEnabled(true);
				horizontal.setSelected(false);
				horizontal.setEnabled(false);
			}

			// if the addDurationCheck is enabled, enable duration options
			else {
				verticalEnable = false;
				flipEnable = false;
				vertical.setEnabled(true);
				horizontal.setEnabled(true);
			}

		} else if (e.getItemSelectable() == horizontal) {

			// if the addDurationCheck is enabled, enable duration options
			if (e.getStateChange() == 1) {
				horizontalEnable = true;
				flipEnable = true;
				horizontal.setEnabled(true);
				vertical.setSelected(false);
				vertical.setEnabled(false);		
			}

			// if the addDurationCheck is enabled, enable duration options
			else {
				horizontalEnable = false;
				flipEnable = false;
				vertical.setEnabled(true);
				horizontal.setEnabled(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == saveButton) {

			if (allChecksVideoEdit()) {
				// Get the video path and length
				setVideoInfo();

				// Reference for JOptionPane():
				// http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html

				// check if the file audioFields exists in the working directory
				File f = new File(workingDir + "/.videoEditFields");
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
						setAllFields(workingDir + "/.videoEditFields");
						return;
					}
				}
				// Save all user inputs to the hidden audioFields text
				try {
					FileWriter fw = new FileWriter(f);
					BufferedWriter bw = new BufferedWriter(fw);
					// Add resize video fields
					bw.write(resizeEnable + "\n");
					width = widthTxt.getText();
					bw.write(width+ "\n");
					height =heightTxt.getText();
					bw.write(height+ "\n");

					// Add flip video fields
					bw.write(flipEnable + "\n");
					bw.write(verticalEnable + "\n");
					bw.write(horizontalEnable + "\n");

					// Add mirror fields
					bw.write(mirrorEnable + "\n");

					bw.close();

				} catch (IOException e1) {
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
		mirrorCheck.setEnabled(state);
		resizeCheck.setEnabled(state);
		flipCheck.setEnabled(state);
		saveButton.setEnabled(state);
	}

	/**
	 * enableExtractOnly() Method enables only extract option (is called when
	 * the input file is an audio file)
	 */

	protected void enableExtractOnly() {
		mirrorCheck.setEnabled(false);
		resizeCheck.setEnabled(true);
		flipCheck.setEnabled(false);
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

		videoEditFields = audioFieldsPath;
		File f = new File(videoEditFields);
		try {

			// read the audioFields file and sets the fields for audio
			// manipulation depending on the last save

			BufferedReader reader;
			reader = new BufferedReader(new FileReader(f));
			resizeCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			width = reader.readLine();
			widthTxt.setText(width);
			height = reader.readLine();
			heightTxt.setText(height);

			flipCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
			vertical.setSelected(Boolean.parseBoolean(reader.readLine()));
			horizontal.setSelected(Boolean.parseBoolean(reader.readLine()));

			mirrorCheck.setSelected(Boolean.parseBoolean(reader.readLine()));

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

	}



	/**
	 * allChecksAudio Method does all the checks for Audio Manipulator
	 */
	private boolean allChecksVideoEdit() {
		boolean passedAllChecks = true;

		// do all the checks for extract
		if (resizeEnable) {
			if (widthTxt.getText().isEmpty() || heightTxt.getText().isEmpty()) {
				JOptionPane
				.showMessageDialog(
						null,
						"ERROR: dimensions cannot be empty");
				passedAllChecks = false;
			}
		}

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
				
				String tempOut1 = hiddenDir + "/tempOut1.mp4";

				String output ="";
				String input ="";

				// if flip is enabled, constructs the command for extract
				if (flipEnable) {
					if (mirrorEnable || resizeEnable) {
						output = tempOut1;
					} else {
						output = lastOutput;
					}
					String direction ="";

					if (verticalEnable) {
						direction = "vflip";
					} else if (horizontalEnable) {
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
				if (mirrorEnable) {
					if (flipEnable) {
						input = output;
					} else {
						input = firstInput;
					}

					if (resizeEnable) {
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
				if (resizeEnable) {

					if (flipEnable || mirrorEnable) {
						input = output;
					} else {
						input = firstInput;
					}

					StringBuilder bigResizeCmd = new StringBuilder();

					bigResizeCmd.append("avconv -i " + input);
					bigResizeCmd.append(" -vf \"scale=" + width +":"  +height + "\" -strict experimental -y ");
					bigResizeCmd.append(lastOutput);
					resizeCmd = bigResizeCmd.toString();

				}


				//construct the final command for audio
				StringBuilder finalCommand = new StringBuilder();

				// if both remove and add are not enabled together
				if (flipEnable) {
					finalCommand.append(flipCmd);
					finalCommand.append(";");
				}
				if (mirrorEnable) {
					finalCommand.append(mirrorCmd);
					finalCommand.append(";");
				}
				if (resizeEnable) {
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