package vamix;

import java.awt.Color;
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
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * SoftEng206 Assignment3 - text editor class
 * 
 * @author Chahat Chawla and Zainab Al Lawati
 * 
 */
public class TextEditor extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;

	// Initializing the text for the buttons
	private final String TEXT_SAVE = "Save";
	// Initializing the buttons
	private JButton saveButton = new JButton(TEXT_SAVE);
	private JButton prevBtn = new JButton("preview");
	// Initializing the labels
	private JLabel textEditorLabel = new JLabel("Text Editor");
	private JLabel screenLabel = new JLabel("Add text on: ");
	private JLabel durationLabel = new JLabel("Duration (in seconds): ");
	private JLabel backgroundImageLabel = new JLabel("Use for background: ");
	private JLabel addTextLabel = new JLabel("Add Text:");
	private JLabel wordLimitLabel = new JLabel("(20 lines max)");
	private JLabel chooseFontLabel = new JLabel("Font type: ");
	private JLabel chooseFontStyleLabel = new JLabel("Font style: ");
	private JLabel chooseFontSizeLabel = new JLabel("Font size: ");
	private JLabel chooseColorLabel = new JLabel("Font colour: ");
	// Initializing the textFields and textAreas
	private JTextField addDuration = new JTextField();
	private JTextArea addTextArea = new JTextArea(8, 30);
	private JTextField addTimeFrame = new JTextField();
	// Initializing the JRadioButton
	private JRadioButton overlayCheck = new JRadioButton("Overlay on the video");
	private JRadioButton defaultCheck = new JRadioButton(
			"Default frame from 00:00:01");
	private JRadioButton frameCheck = new JRadioButton(
			"A frame from the video at:");
	// Initializing the ComboBox and lists of drop down menus
	String[] dropDownScreen = { "", "Title Screen", "Credit Screen" };
	private JComboBox<String> screenList = new JComboBox<String>(dropDownScreen);
	String[] dropDownFonts = { "Arial", "Courier", "Georgia", "TimesNewRoman",
	"Verdana" };
	private JComboBox<String> fontsList = new JComboBox<String>(dropDownFonts);
	String[] dropDownStyles = { "PLAIN", "BOLD", "ITALIC", "BOLD&ITALIC" };
	private JComboBox<String> stylesList = new JComboBox<String>(dropDownStyles);
	String[] dropDownSizes = { "8", "10", "14", "18", "22", "26", "30", "34",
			"38", "42", "48", "52", "56", "72" };
	private JComboBox<String> sizesList = new JComboBox<String>(dropDownSizes);
	String[] dropDownColors = { "black", "green", "blue", "yellow", "red",
			"white", "pink" };
	private JComboBox<String> coloursList = new JComboBox<String>(dropDownColors);
	// Initializing the seperators
	private JLabel separator = new JLabel("");
	private JLabel separator2 = new JLabel("");
	private JLabel separator3 = new JLabel("");
	private JLabel separator4 = new JLabel("");
	private JLabel separator5 = new JLabel("");
	private JLabel separator6 = new JLabel("");
	private JLabel separator7 = new JLabel("");
	// Initializing all the variables for text editing
	private File textFile;
	private String screenType = "Title Screen";
	private int backgroundImageOption = 0;

	private int backgroundImageTitle = 3;
	private int backgroundImageCredit = 3;

	private String titleDuration = "";
	private String creditDuration = "";
	private String titleFrameTime = "";
	private String creditFrameTime = "";
	private int fontType = 0;
	private int fontStyle = 0;
	private int titleFontSize = 8;
	private int fontSize = 8;
	private int creditFontSize = 8;
	private String titleFontColour = "black";
	private String fontColour = "black";
	private String creditFontColour = "black";
	private String fontDir = "/usr/share/fonts/truetype/msttcorefonts/";
	private String titleFontName = "";
	private String fontName = "";
	private String creditFontName = "";
	private String projectPath;
	private String hiddenDir;
	private String videoLength;
	private String videoPath;
	private String titleCommand;
	private String creditCommand;
	private String previewCommand;
	private String bothTitleAndCreditCommand;
	private BackgroundTask longTask;
	private String titleFields;
	private String workingDir;
	private int titleFontStyle = 0;
	private String titlePreviewFont = "Arial";
	private int creditFontStyle = 0;
	private String creditPreviewFont = "Arial";
	private String prevFont = "Arial";
	private String creditFields;

	// TextEditor constructor - sets the GUI for textEditor tab
	public TextEditor() {

		// change the font of the title
		textEditorLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		// add the addTextArea textArea to the ScrollPane scroll
		JScrollPane scroll = new JScrollPane(addTextArea);

		// adding action listeners to all the buttons and lists so when a user
		// clicks a button or a list, the corresponding actions are done.
		prevBtn.addActionListener(this);
		saveButton.addActionListener(this);
		screenList.addActionListener(this);
		fontsList.addActionListener(this);
		stylesList.addActionListener(this);
		sizesList.addActionListener(this);
		coloursList.addActionListener(this);

		// add all the buttons, labels, scrolls, comboBox, radioButtons and
		// textFields to the panel
		add(textEditorLabel);
		add(separator);
		add(screenList);
		add(durationLabel);
		add(addDuration);
		add(backgroundImageLabel);
		add(separator3);
		add(overlayCheck);
		add(defaultCheck);
		add(frameCheck);
		add(addTimeFrame);
		add(separator4);
		add(screenLabel);
		add(separator2);
		add(addTextLabel);
		add(wordLimitLabel);
		add(separator5);
		add(chooseFontLabel);
		add(fontsList);
		add(chooseFontStyleLabel);
		add(stylesList);
		add(separator6);
		add(chooseFontSizeLabel);
		add(sizesList);
		add(chooseColorLabel);
		add(coloursList);
		add(scroll);
		add(separator7);
		add(prevBtn);
		add(saveButton);

		// set the preferred size for the components
		separator.setPreferredSize(new Dimension(525, 10));
		separator2.setPreferredSize(new Dimension(525, 0));
		separator3.setPreferredSize(new Dimension(525, 0));
		separator4.setPreferredSize(new Dimension(525, 0));
		separator5.setPreferredSize(new Dimension(525, 0));
		separator6.setPreferredSize(new Dimension(525, 0));
		separator7.setPreferredSize(new Dimension(525, 0));
		saveButton.setPreferredSize(new Dimension(120, 25));
		prevBtn.setPreferredSize(new Dimension(120, 25));
		scroll.setPreferredSize(new Dimension(400, 130));
		addTimeFrame.setColumns(10);
		addTimeFrame.setEnabled(false);
		addDuration.setColumns(3);

		// add ItemListner to the radio buttons, to check what happens when the
		// radioButtons are checked or unchecked
		frameCheck.addItemListener(this);
		defaultCheck.addItemListener(this);
		overlayCheck.addItemListener(this);

	}

	/**
	 * actionPerformed method responds to all the actions done by the user on
	 * the GUI
	 */
	public void actionPerformed(ActionEvent e) {
		int fileExistsResponse = -1;

		// Get the video path and length
		setVideoInfo();

		// Make sure that the credit and title fields do not interfere with each
		// other
		if (e.getSource() == screenList) {

			screenType = screenList.getSelectedItem().toString();

			// Refresh the fields and load the title fields (if nothing is
			// stored, just keep it empty)
			if (screenList.getSelectedIndex() == 1) {
				setFieldsEnabled(true);
				refreshTiTleScreen();
				setTitleFields(titleFields);

				// Refresh the fields and load the credit fields (if nothing is
				// stored, just keep it empty)
			} else if (screenList.getSelectedIndex() == 2) {
				setFieldsEnabled(true);
				refreshCreditScreen();
				setCreditFields(creditFields);

			} else { // Disable the fields if no screen is selected
				setFieldsEnabled(false);
			}
		}

		if (e.getSource() == saveButton) {

			if (screenType.equals("")) { // Abort saving if screen type was not
				// chosen
				JOptionPane
				.showMessageDialog(null,
						"ERROR: please select the type of screen you want to add text on.");
				return;
			}

			boolean passsedChecks = allChecks();

			if (passsedChecks) {

				// check the screen type to select the corresponding text file
				if (screenType.equals("Title Screen")) {
					textFile = new File(workingDir + "/.TitleText.txt");
					// get the duration for title screen
					titleDuration = addDuration.getText().trim();
					// get the inputFrameTime for title screen
					titleFrameTime = addTimeFrame.getText().trim();


					// set the font settings for title screen
					setTitleFontSettings();

					// Save all user inputs to the hidden titleFields text
					File f = new File(workingDir + "/.titleFields");
					try {
						FileWriter fw = new FileWriter(f);
						BufferedWriter bw = new BufferedWriter(fw);
						// Add title audio fields
						bw.write("Title Screen" + "\n");
						bw.write(titleDuration + "\n");
						bw.write(overlayCheck.isSelected() + "\n");
						bw.write(defaultCheck.isSelected() + "\n");
						bw.write(frameCheck.isSelected() + "\n");
						bw.write(titleFrameTime + "\n");
						bw.write(titleFontSize + "\n");
						bw.write(titleFontColour + "\n");
						bw.write(titleFontName + "\n");
						bw.write(titleFontStyle + "\n");
						bw.write(titlePreviewFont + "\n");
						bw.write(addTextArea.getText() + "\n");
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}


				} else {
					textFile = new File(workingDir + "/.CreditText.txt");
					// get the duration for credit screen
					creditDuration = addDuration.getText().trim();
					// get the inputFrameTime for credit screen
					creditFrameTime = addTimeFrame.getText().trim();



					// set the font settings for credit screen
					setCreditFontSettings();

					// Save all user inputs to the hidden creditFields text
					File f = new File(workingDir + "/.creditFields");
					try {
						FileWriter fw = new FileWriter(f);
						BufferedWriter bw = new BufferedWriter(fw);
						// Add credit audio fields
						bw.write("Credit Screen" + "\n");
						bw.write(creditDuration + "\n");
						bw.write(overlayCheck.isSelected() + "\n");
						bw.write(defaultCheck.isSelected() + "\n");
						bw.write(frameCheck.isSelected() + "\n");
						bw.write(creditFrameTime + "\n");
						bw.write(creditFontSize + "\n");
						bw.write(creditFontColour + "\n");
						bw.write(creditFontName + "\n");
						bw.write(creditFontStyle + "\n");
						bw.write(creditPreviewFont + "\n");
						bw.write(addTextArea.getText() + "\n");
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}


				}
				// check whether the text file for credit or title exists
				if (textFile.exists()) {

					// if the file does exist, inform the user that they have
					// previously created a screen
					while (fileExistsResponse == JOptionPane.CLOSED_OPTION) {
						Object[] options = { "Keep original settings",
						"Overwrite" };
						fileExistsResponse = JOptionPane
								.showOptionDialog(
										null,
										"Chosen screen already created. Are you sure you want to make changes?",
										"Screen Exists",
										JOptionPane.YES_NO_CANCEL_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										options, options[1]);

						// overwrite option
						if (fileExistsResponse == 0) {
							FileWriter fw;
							try {
								fw = new FileWriter(textFile, false);
								BufferedWriter bw = new BufferedWriter(fw);
								PrintWriter x = new PrintWriter(bw);
								x.println(addTextArea.getText());
								bw.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}

				// Reference for writing to file:
				// http://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java

				// If the file does not exist create a new file, and append the
				// addTextArea text into the file
				else {
					FileWriter fw;
					try {
						fw = new FileWriter(textFile, true);
						BufferedWriter bw = new BufferedWriter(fw);
						PrintWriter x = new PrintWriter(bw);
						x.println(addTextArea.getText());
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		// if the preview button is clicked
		else if (e.getSource() == prevBtn) {

			// set the the FontSettings
			setTitleFontSettings();
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
				x.println(addTextArea.getText());
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// check if one of the background image options are selected
			if (!defaultCheck.isSelected() && !overlayCheck.isSelected()
					&& !frameCheck.isSelected()) {

				// if none of the three are selected, show an error message to
				// the user and allow them to choose one
				passedOrNot = false;
				JOptionPane.showMessageDialog(null,
						"ERROR: please select one of the background options");
				filePreview.delete();
			}
			// if the user wants to preview the title screen
			if (screenType.equals("Title Screen")) {
				// if they select a frame from the video
				if (backgroundImageOption == 2) {
					// check for whether the frame time specified by the user is
					// longer than the length of the video
					if (passedOrNot) {
						int convertedFrameTime = convertToSeconds(addTimeFrame
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
							addTimeFrame.setText("");
							filePreview.delete();
						}
					}
					// if the checks are passed, the inputFrameTime is set so
					// the screenshot is taken at the time that the user
					// specified
					inputFrameTime = addTimeFrame.getText().trim();
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
				if (backgroundImageOption == 2) {
					// check for whether the frame time specified by the user is
					// in the right format
					int passedTimeCheck = timeChecks(addTimeFrame.getText()
							.trim());
					// if it is not in the right format, show an error message
					// to the user
					// and allow them to change the frame time
					if (passedTimeCheck == 1) {
						addTimeFrame.setText("");
						passedOrNot = false;
						JOptionPane
						.showMessageDialog(null,
								"ERROR: please specify the frame time in hh:mm:ss");
						filePreview.delete();
					}
					// check for whether the frame time specified by the user is
					// longer than the length of the video
					if (passedOrNot) {
						int convertedFrameTime = convertToSeconds(addTimeFrame
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
							addTimeFrame.setText("");
							filePreview.delete();
						}
					}
					// if the checks are passed, the inputFrameTime is set so
					// the screenshot is taken at the time that the user
					// specified
					inputFrameTime = addTimeFrame.getText().trim();
				} else if (backgroundImageOption == 0) {
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
						+ fontDir + fontName + "':textfile='" + filePreview
						+ "':x=(main_w-text_w)/2:y=(main_h-text_h)/2:fontsize="
						+ fontSize + ":fontcolor=" + fontColour
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
		} else if (e.getSource() == fontsList) {
			fontType = fontsList.getSelectedIndex();
			prevFont = fontsList.getSelectedItem().toString();
			// change the font type on the text the user writes to the font type
			// they choose
			addTextArea.setFont(new Font(prevFont, fontStyle, fontSize));
		} else if (e.getSource() == stylesList) {
			fontStyle = stylesList.getSelectedIndex();
			// change the font style on the text the user writes to the font
			// style they choose
			addTextArea.setFont(new Font(prevFont, fontStyle, fontSize));
		} else if (e.getSource() == sizesList) {
			fontSize = Integer.parseInt(sizesList.getSelectedItem().toString());
			// change the font size on the text the user writes to the font size
			// they choose
			addTextArea.setFont(new Font(prevFont, fontStyle, fontSize));
		} else if (e.getSource() == coloursList) {
			fontColour = coloursList.getSelectedItem().toString();
			// change the font color on the text the user writes to the font
			// color they choose
			switch (fontColour) {
			case "black":
				addTextArea.setForeground(Color.black);
				break;
			case "green":
				addTextArea.setForeground(Color.green);
				break;
			case "blue":
				addTextArea.setForeground(Color.blue);
				break;
			case "yellow":
				addTextArea.setForeground(Color.yellow);
				break;
			case "red":
				addTextArea.setForeground(Color.red);
				break;
			case "white":
				addTextArea.setForeground(Color.white);
				break;
			case "pink":
				addTextArea.setForeground(Color.pink);
				break;
			}
		}
	}

	/**
	 * Method stores the video info from the project file to the private fields
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
			videoPath = reader.readLine();
			videoLength = reader.readLine();
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
	 * Method concatenates the selected fontType, fontStyle to a string called
	 * fontName and initializes all the font variables for title screen
	 */
	public void setTitleFontSettings() {
		StringBuilder font = new StringBuilder();
		// fontType options
		if (fontType == 0) {
			font.append("Arial");
		} else if (fontType == 1) {
			font.append("Courier_New");
		} else if (fontType == 2) {
			font.append("Georgia");
		} else if (fontType == 2) {
			font.append("Times_New_Roman");
		} else {
			font.append("Verdana");
		}
		// fontStyle options
		if (fontStyle == 1) {
			font.append("_Bold");
		} else if (fontStyle == 2) {
			font.append("_Italic");
		} else if (fontStyle == 3) {
			font.append("_Bold_Italic");
		}
		// append .ttf as font types are ttf files
		font.append(".ttf");
		fontName = font.toString();
		// set font variables for title screen
		titleFontSize = fontSize;
		titleFontColour = fontColour;
		titleFontName = fontName;
		titleFontStyle = fontStyle;
		titlePreviewFont = prevFont;
	}

	/**
	 * Method concatenates the selected fontType, fontStyle to a string called
	 * fontName and initializes all the font variables for title screen
	 */
	public void setCreditFontSettings() {
		StringBuilder font = new StringBuilder();
		// fontType options
		if (fontType == 0) {
			font.append("Arial");
		} else if (fontType == 1) {
			font.append("Courier_New");
		} else if (fontType == 2) {
			font.append("Georgia");
		} else if (fontType == 2) {
			font.append("Times_New_Roman");
		} else {
			font.append("Verdana");
		}
		// fontStyle options
		if (fontStyle == 1) {
			font.append("_Bold");
		} else if (fontStyle == 2) {
			font.append("_Italic");
		} else if (fontStyle == 3) {
			font.append("_Bold_Italic");
		}
		// append .ttf as font types are ttf files
		font.append(".ttf");
		fontName = font.toString();
		// set font variables for credit screen
		creditFontSize = fontSize;
		creditFontColour = fontColour;
		creditFontName = fontName;
		creditFontStyle = fontStyle;
		creditPreviewFont = prevFont;
	}

	/**
	 * Method sets all the fields of related to the title screen into the
	 * previously saved values
	 * 
	 * @param textFieldsPath
	 *            : the path of the stored text fields
	 */
	protected void setTitleFields(String textFieldsPath) {
		titleFields = textFieldsPath;
		File f = new File(workingDir + "/.titleFields");
		if (f.exists()) {
			try {
				BufferedReader reader;
				reader = new BufferedReader(new FileReader(f)); // Read the file
				// that saved
				// the fields
				screenList.setSelectedItem(reader.readLine());
				titleDuration = reader.readLine();
				addDuration.setText(titleDuration);
				overlayCheck
				.setSelected(Boolean.parseBoolean(reader.readLine()));
				defaultCheck
				.setSelected(Boolean.parseBoolean(reader.readLine()));
				frameCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
				titleFrameTime = reader.readLine();
				addTimeFrame.setText(titleFrameTime);
				titleFontSize = Integer.parseInt(reader.readLine());
				sizesList.setSelectedItem("" + titleFontSize);
				titleFontColour = reader.readLine();
				coloursList.setSelectedItem(titleFontColour);
				switch (titleFontColour) {
				case "black":
					addTextArea.setForeground(Color.black);
					break;
				case "green":
					addTextArea.setForeground(Color.green);
					break;
				case "blue":
					addTextArea.setForeground(Color.blue);
					break;
				case "yellow":
					addTextArea.setForeground(Color.yellow);
					break;
				case "red":
					addTextArea.setForeground(Color.red);
					break;
				case "white":
					addTextArea.setForeground(Color.white);
					break;
				case "pink":
					addTextArea.setForeground(Color.pink);
					break;
				}
				titleFontName = reader.readLine();
				titleFontStyle = Integer.parseInt(reader.readLine());
				stylesList.setSelectedIndex(titleFontStyle);
				titlePreviewFont = reader.readLine();
				fontsList.setSelectedItem(titlePreviewFont);
				String line;
				StringBuilder allText = new StringBuilder();
				while (((line = reader.readLine()) != null)) {
					allText.append(line);
					allText.append(System.getProperty("line.separator"));
				}
				addTextArea.setText(allText.toString());
				addTextArea.setFont(new Font(titlePreviewFont, titleFontStyle,
						titleFontSize));
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method sets all the fields of related to the credit screen into the
	 * previously saved values
	 * 
	 * @param textFieldsPath
	 *            : the path of the stored text fields
	 */
	protected void setCreditFields(String textFieldsPath) {
		titleFields = textFieldsPath;
		File f = new File(workingDir + "/.creditFields");
		if (f.exists()) {
			try {
				BufferedReader reader;
				reader = new BufferedReader(new FileReader(f));
				screenList.setSelectedItem(reader.readLine()); // Credit Screen
				creditDuration = reader.readLine();
				addDuration.setText(creditDuration);
				overlayCheck
				.setSelected(Boolean.parseBoolean(reader.readLine()));
				defaultCheck
				.setSelected(Boolean.parseBoolean(reader.readLine()));
				frameCheck.setSelected(Boolean.parseBoolean(reader.readLine()));
				creditFrameTime = reader.readLine();
				addTimeFrame.setText(creditFrameTime);
				creditFontSize = Integer.parseInt(reader.readLine());
				sizesList.setSelectedItem("" + creditFontSize);
				creditFontColour = reader.readLine();
				coloursList.setSelectedItem(creditFontColour);
				switch (creditFontColour) {
				case "black":
					addTextArea.setForeground(Color.black);
					break;
				case "green":
					addTextArea.setForeground(Color.green);
					break;
				case "blue":
					addTextArea.setForeground(Color.blue);
					break;
				case "yellow":
					addTextArea.setForeground(Color.yellow);
					break;
				case "red":
					addTextArea.setForeground(Color.red);
					break;
				case "white":
					addTextArea.setForeground(Color.white);
					break;
				case "pink":
					addTextArea.setForeground(Color.pink);
					break;
				}
				creditFontName = reader.readLine();
				creditFontStyle = Integer.parseInt(reader.readLine());
				stylesList.setSelectedIndex(creditFontStyle);
				creditPreviewFont = reader.readLine();
				fontsList.setSelectedItem(creditPreviewFont);
				String line;
				StringBuilder allText = new StringBuilder();
				while (((line = reader.readLine()) != null)) {
					allText.append(line);
					allText.append(System.getProperty("line.separator"));
				}
				addTextArea.setText(allText.toString());
				addTextArea.setFont(new Font(creditPreviewFont,
						creditFontStyle, creditFontSize));
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method refreshes all the title fields in the text editing tab
	 */
	protected void refreshTiTleScreen() {
		addDuration.setText("");
		addTextArea.setText("");
		addTimeFrame.setText("hh:mm:ss");
		overlayCheck.setSelected(false);
		defaultCheck.setSelected(false);
		frameCheck.setSelected(false);
		if ((projectPath != null) && (!projectPath.isEmpty())) {
			fontsList.setSelectedIndex(0);
			stylesList.setSelectedIndex(0);
			sizesList.setSelectedIndex(0);
			coloursList.setSelectedIndex(0);
		}
		backgroundImageOption = 0;
		titleDuration = "";
		titleFrameTime = "";
		fontType = 0;
		fontStyle = 0;
		titleFontSize = 8;
		fontSize = 8;
		titleFontColour = "black";
		fontColour = "black";
		titleFontName = "";
		fontName = "";
		titleFontStyle = 0;
		titlePreviewFont = "Arial";
		prevFont = "Arial";
	}

	/**
	 * Method changes the enable state of all the field in the text edit tab
	 * except the screen list
	 */
	protected void setFieldsEnabled(boolean state) {
		saveButton.setEnabled(state);
		prevBtn.setEnabled(state);

		addDuration.setEnabled(state);
		addTextArea.setEnabled(state);
		addTimeFrame.setEnabled(state);

		overlayCheck.setEnabled(state);
		defaultCheck.setEnabled(state);
		frameCheck.setEnabled(state);

		fontsList.setEnabled(state);
		stylesList.setEnabled(state);
		sizesList.setEnabled(state);
		coloursList.setEnabled(state);

	}

	/**
	 * Method refreshes all the credits fields in the text editing tab
	 */
	protected void refreshCreditScreen() {
		addDuration.setText("");
		addTextArea.setText("");
		addTimeFrame.setText("hh:mm:ss");
		overlayCheck.setSelected(false);
		defaultCheck.setSelected(false);
		frameCheck.setSelected(false);
		if ((projectPath != null) && (!projectPath.isEmpty())) {
			fontsList.setSelectedIndex(0);
			sizesList.setSelectedIndex(0);
			coloursList.setSelectedIndex(0);
		}
		backgroundImageOption = 0;
		creditDuration = "";
		creditFrameTime = "";
		fontType = 0;
		fontStyle = 0;
		fontSize = 8;
		creditFontSize = 8;
		fontColour = "black";
		creditFontColour = "black";
		fontName = "";
		creditFontName = "";
		creditFontStyle = 0;
		creditPreviewFont = "Arial";
		prevFont = "Arial";
	}

	/**
	 * Method refreshes all the project fields in the text editing tab
	 */
	protected void refreshtextEdit() {
		if ((projectPath != null) && (!projectPath.isEmpty())) {
			screenList.setSelectedIndex(0);
		}
		screenType = "";
		projectPath = "";
		hiddenDir = "";
		videoPath = "";
		videoLength = "";
		workingDir = "";
		titleFields = "";
		creditFields = "";
	}

	private boolean backgroundImageCheck() {
		boolean passed = false;

		// If no background option was specified
		if (!defaultCheck.isSelected() && !overlayCheck.isSelected()
				&& !frameCheck.isSelected()) {
			passed = false;
			JOptionPane.showMessageDialog(null,
					"ERROR: please select of the background options");
		}

		else if (backgroundImageTitle == 3 && backgroundImageCredit != 3) {
			passed = true;
		} else if (backgroundImageCredit == 3 && backgroundImageTitle != 3) {
			passed = true;
		}

		else if (backgroundImageTitle == 3 && backgroundImageCredit == 3) {
			passed = true;
		} else if (backgroundImageTitle == backgroundImageCredit) {
			passed = true;
		} else {
			passed = false;
			
			JOptionPane
			.showMessageDialog(
					null,
					"ERROR: background image option needs to be the same if both title and credit screen are selected");

			overlayCheck.setSelected(false);
			defaultCheck.setSelected(false);
			frameCheck.setSelected(false);
		}

		return passed;
	}

	private int timeChecks(String frameTime) {

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

	/**
	 * Method returns true if the text editor tab passed all the checks (user
	 * input is valid)
	 */
	private boolean allChecks() {
		boolean passedOrNot = true;
		boolean passedBackgroundCheck;

		if (screenType.equals("Title Screen")) {

			// set the backgroundImageTitle;
			backgroundImageTitle = backgroundImageOption;

			// check for the backgroundImageOption
			passedBackgroundCheck = backgroundImageCheck();

		} else {
			// set the backgroundImageCredit;
			backgroundImageCredit = backgroundImageOption;

			// check for the backgroundImageOption
			passedBackgroundCheck = backgroundImageCheck();
		}

		if (passedBackgroundCheck) {

			// If no duration was specified
			if (addDuration.getText().trim().equals("")) {
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
			} else if (addTextArea.getLineCount() > 10) {
				JOptionPane.showMessageDialog(null,
						"ERROR: Text can only be 10 lines.");
				passedOrNot = false;

				// If a frame background was chosen without specifying the frame
				// time
			} else if (backgroundImageOption == 2) {
				int passedTimeCheck = timeChecks(addTimeFrame.getText().trim());
				if (passedTimeCheck == 1) {
					addTimeFrame.setText("");
					passedOrNot = false;
					JOptionPane.showMessageDialog(null,
							"ERROR: please specify the frame time in hh:mm:ss");
				}

				if (passedOrNot) {
					int convertedFrameTime = convertToSeconds(addTimeFrame
							.getText().trim());
					int lengthOfVideo = (int) (Double.parseDouble(videoLength));
					// Check the time of frame is within the range of the video
					if (convertedFrameTime > lengthOfVideo) {
						passedOrNot = false;
						JOptionPane
						.showMessageDialog(null,
								"ERROR: frame time can not be more than the length of the video");
						addTimeFrame.setText("");
					}
				}
			}
		}
		else {
			passedOrNot = false;
		}
		return passedOrNot;
	}

	private int convertToSeconds(String myDateString) {

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
	 * Make sure that when one radio button is enabled, the others are disabled
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == frameCheck) {
			// if frame background is chosen, disable other radio buttons
			if (e.getStateChange() == 1) {
				defaultCheck.setEnabled(false);
				overlayCheck.setEnabled(false);
				backgroundImageOption = 2;
				addTimeFrame.setEnabled(true);
			} else {
				addTimeFrame.setText("hh:mm:ss");
				addTimeFrame.setEnabled(false);
				defaultCheck.setEnabled(true);
				overlayCheck.setEnabled(true);
			}
		}
		if (e.getSource() == overlayCheck) {
			// if overlay is chosen, disable other radio buttons
			if (e.getStateChange() == 1) {
				frameCheck.setEnabled(false);
				defaultCheck.setEnabled(false);
				backgroundImageOption = 0;
			} else {
				frameCheck.setEnabled(true);
				defaultCheck.setEnabled(true);
			}
		}
		if (e.getSource() == defaultCheck) {
			// if a default frame is chosen, disable other radio buttons
			if (e.getStateChange() == 1) {
				frameCheck.setEnabled(false);
				overlayCheck.setEnabled(false);
				backgroundImageOption = 1;
			} else {
				frameCheck.setEnabled(true);
				overlayCheck.setEnabled(true);
			}
		}
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
				if (backgroundImageOption == 0) {

					// if the user has saved title screen settings but does not
					// want to implement credit screen
					if (fileTitle.exists() && !fileCredit.exists()) {
						StringBuilder finalTitleCommand = new StringBuilder();
						// add text to video
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ titleFontSize + ":fontcolor="
								+ titleFontColour + "\" -t "
								+ titleDuration + " -y " + hiddenDir
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
								+ titleDuration
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
								.parseInt(creditDuration));
						String startTime = "" + time;
						// add text to video
						finalCreditCommand
						.append("avconv -ss "
								+ startTime
								+ " -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ creditFontSize + ":fontcolor="
								+ creditFontColour + "\" -t "
								+ creditDuration + " -y " + hiddenDir
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
								.parseInt(creditDuration));
						int time1 = time - Integer.parseInt(titleDuration);
						String startTime = "" + time;
						String stopTime = "" + time1;
						// add text to title video
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ titleFontSize + ":fontcolor="
								+ titleFontColour + "\" -t "
								+ titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalBothCommand.append(";");
						// add text to credit video
						finalBothCommand
						.append("avconv -ss "
								+ startTime
								+ " -i "
								+ firstInput
								+ " -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ creditFontSize + ":fontcolor="
								+ creditFontColour + "\" -t "
								+ creditDuration + " -y " + hiddenDir
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
								+ titleDuration
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
						if (backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001
							inputFrameTime = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshot will be taken at the time specified by
							// the user
							inputFrameTime = titleFrameTime;
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
								+ titleDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalTitleCommand.append(";");
						// add text
						finalTitleCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ titleFontSize + ":fontcolor="
								+ titleFontColour + "\" -t "
								+ titleDuration + " -y " + hiddenDir
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
						if (backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001
							inputFrameTime = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshot will be taken at the time specified by
							// the user
							inputFrameTime = creditFrameTime;
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
								+ creditDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalCreditCommand.append(";");
						// add text
						finalCreditCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ creditFontSize + ":fontcolor="
								+ creditFontColour + "\" -t "
								+ creditDuration + " -y " + hiddenDir
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
						if (backgroundImageOption == 1) {
							// screenshot will be taken at 00:00:00.001 for both
							// title screen and credit screen
							inputFrameTime = "00:00:00.001";
							inputFrameTime1 = "00:00:00.001";
						}
						// if they select a frame from the video
						else {
							// screenshots will be taken at the time specified
							// by the user
							inputFrameTime = titleFrameTime;
							inputFrameTime1 = creditFrameTime;
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
								+ titleDuration + " -y " + hiddenDir
								+ "/result.mp4");
						finalBothCommand.append(";");
						// create video from image for the duration given by the
						// user for credit screen
						finalBothCommand
						.append("avconv -loop 1 -shortest -y -i "
								+ hiddenDir + "/out1.png -t "
								+ creditDuration + " -y " + hiddenDir
								+ "/result1.mp4");
						finalBothCommand.append(";");
						// add text for title screen
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ titleFontName
								+ "':textfile='"
								+ fileTitle
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ titleFontSize + ":fontcolor="
								+ titleFontColour + "\" -t "
								+ titleDuration + " -y " + hiddenDir
								+ "/text.mp4");
						finalBothCommand.append(";");
						// add text for credit screen
						finalBothCommand
						.append("avconv -ss 0 -i "
								+ hiddenDir
								+ "/result1.mp4 -strict experimental -vf \"drawtext=fontfile='"
								+ fontDir
								+ creditFontName
								+ "':textfile='"
								+ fileCredit
								+ "':x=(main_w-text_w)/3:y=(main_h-text_h)/2:fontsize="
								+ creditFontSize + ":fontcolor="
								+ creditFontColour + "\" -t "
								+ creditDuration + " -y " + hiddenDir
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