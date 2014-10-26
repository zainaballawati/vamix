package textEditor;

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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * SoftEng206 Assignment3 - text editor class
 * @author Chahat Chawla and Zainab Al Lawati
 * 
 */
public class TextEditorGui extends JPanel implements ActionListener, ItemListener {

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
	public static JTextField addDuration = new JTextField();
	public static JTextArea addTextArea = new JTextArea(8, 30);
	public static JTextField addTimeFrame = new JTextField();
	// Initializing the JRadioButton
	public static JRadioButton overlayCheck = new JRadioButton("Overlay on the video");
	public static JRadioButton defaultCheck = new JRadioButton(
			"Default frame from 00:00:01");
	public static JRadioButton frameCheck = new JRadioButton(
			"A frame from the video at:");
	// Initializing the ComboBox and lists of drop down menus
	public static String[] dropDownScreen = { "", "Title Screen", "Credit Screen" };
	public static JComboBox<String> screenList = new JComboBox<String>(dropDownScreen);
	static String[] dropDownFonts = { "Arial", "Courier", "Georgia", "TimesNewRoman",
	"Verdana" };
	public static JComboBox<String> fontsList = new JComboBox<String>(dropDownFonts);
	static String[] dropDownStyles = { "PLAIN", "BOLD", "ITALIC", "BOLD&ITALIC" };
	public static JComboBox<String> stylesList = new JComboBox<String>(dropDownStyles);
	static String[] dropDownSizes = { "8", "10", "14", "18", "22", "26", "30", "34",
			"38", "42", "48", "52", "56", "72" };
	public static JComboBox<String> sizesList = new JComboBox<String>(dropDownSizes);
	static String[] dropDownColors = { "black", "green", "blue", "yellow", "red",
			"white", "pink" };
	public static JComboBox<String> coloursList = new JComboBox<String>(dropDownColors);
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
	public static String screenType = "Title Screen";
	public static int backgroundImageOption = 0;

	public static int backgroundImageTitle = 3;
	public static int backgroundImageCredit = 3;

	public static String titleDuration = "";
	public static String creditDuration = "";
	public static String titleFrameTime = "";
	public static String creditFrameTime = "";
	public static int fontType = 0;
	public static int fontStyle = 0;
	public static int titleFontSize = 8;
	public static int fontSize = 8;
	public static int creditFontSize = 8;
	public static String titleFontColour = "black";
	public static String fontColour = "black";
	public static String creditFontColour = "black";
	public static String fontDir = "/usr/share/fonts/truetype/msttcorefonts/";
	public static String titleFontName = "";
	public static String fontName = "";
	public static String creditFontName = "";
	private String projectPath;
	private String hiddenDir;
	private String videoLength;
	private String videoPath;
	private String workingDir;

	private String titleFields;

	public static int titleFontStyle = 0;
	public static String titlePreviewFont = "Arial";
	public static int creditFontStyle = 0;
	public static String creditPreviewFont = "Arial";
	public static String prevFont = "Arial";
	private String creditFields;

	// TextEditor constructor - sets the GUI for textEditor tab
	public TextEditorGui() {

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
		TextFunctionality.setVideoInfo();

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

			if (screenType.equals("")) { // Abort saving if screen type was not chosen
				JOptionPane.showMessageDialog(null,
						"ERROR: please select the type of screen you want to add text on.");
				return;
			}

			boolean passsedChecks = TextErrorHandling.allChecks();

			if (passsedChecks) {

				// check the screen type to select the corresponding text file
				if (screenType.equals("Title Screen")) {
					textFile = new File(workingDir + "/.TitleText.txt");
					// get the duration for title screen
					titleDuration = addDuration.getText().trim();
					// get the inputFrameTime for title screen
					titleFrameTime = addTimeFrame.getText().trim();


					// set the font settings for title screen
					TextFunctionality.setTitleFontSettings();

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
					TextFunctionality.setCreditFontSettings();

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
			PreviewText.generatePreview();
			
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

	

}