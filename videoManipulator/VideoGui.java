
package videoManipulator;

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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * SoftEng206 Project - video Gui class
 * Class has the gui components for video manipulating: resizing, flipping, and mirror effect
 * @author Zainab Al Lawati
 *
 */

public class VideoGui extends JPanel implements ItemListener,
ActionListener {


	private static final long serialVersionUID = 1L;

	// Initializing the text for the buttons
	private final String TEXT_SAVE = "Save";

	// // Initializing the labels
	private JLabel videoEditLabel = new JLabel("Video Edit");
	private JLabel widthLabel = new JLabel("Width:");
	private JLabel heightLabel = new JLabel("Height:");

	// Initializing the textFields
	public static JTextField widthTxt = new JTextField("");
	public static JTextField heightTxt = new JTextField("");
	private JButton saveButton = new JButton(TEXT_SAVE);

	// Initializing the check boxes
	public static JCheckBox mirrorCheck = new JCheckBox("Create vertical mirror image");
	public static JCheckBox resizeCheck = new JCheckBox("Resize video");
	public static JCheckBox flipCheck = new JCheckBox("Flip Video");
	public static JRadioButton vertical = new JRadioButton(
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
	public static boolean mirrorEnable = false;
	public static boolean verticalEnable = false;
	public static boolean horizontalEnable = false;
	public static boolean flipEnable = false;
	public static boolean resizeEnable = false;

	// Initializing the Strings
	private String projectPath;
	private String hiddenDir;
	private String videoPath;
	private String videoLength;
	private String workingDir;
	private String videoEditFields;

	public static String width = "";
	public static String height = "";


	// Initializing the swing worker BackgroundTask
	private final JRadioButton horizontal = new JRadioButton("Horizontally");
	private final JLabel label = new JLabel("");

	/**
	 * Constructor for VideoGui() -Sets up the GUI for video
	 * manipulation tab -Sets up the default layout
	 */

	public VideoGui() {

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

		if (e.getItemSelectable() == mirrorCheck) {
			//Store that the user choose mirror effect
			if (e.getStateChange() == 1) {
				mirrorEnable = true;
			} else {
				mirrorEnable = false;
			}
			
		} else if (e.getItemSelectable() == resizeCheck) {

			//Store that the user choose resize effect and enable the related fields
			if (e.getStateChange() == 1) {
				resizeEnable = true;
				widthLabel.setEnabled(true);
				heightLabel.setEnabled(true);
				widthTxt.setEnabled(true);
				heightTxt.setEnabled(true);

			} else {
				resizeEnable = false;

				widthTxt.setEnabled(false);
				widthTxt.setText(width);
				heightTxt.setEnabled(false);
				heightTxt.setText(height);
				widthLabel.setEnabled(false);
				heightLabel.setEnabled(false);

			}
			
		} else if (e.getItemSelectable() == flipCheck) {

			//Store that the user choose flip effect and enable the related fields
			if (e.getStateChange() == 1) {
				vertical.setEnabled(true);
				horizontal.setEnabled(true);

			} else {
				vertical.setSelected(false);
				horizontal.setSelected(false);
				vertical.setEnabled(false);
				horizontal.setEnabled(false);
				
			}
			
		} else if (e.getItemSelectable() == vertical) {

			//Store that vertical flip was chosen and disable horizontal flip option
			if (e.getStateChange() == 1) {
				verticalEnable = true;
				flipEnable = true;
				vertical.setEnabled(true);
				horizontal.setSelected(false);
				horizontal.setEnabled(false);
				
			} else {
				verticalEnable = false;
				flipEnable = false;
				vertical.setEnabled(true);
				horizontal.setEnabled(true);
			}

		} else if (e.getItemSelectable() == horizontal) {

			//Store that horizontal flip was chosen and disable vertical flip option
			if (e.getStateChange() == 1) {
				horizontalEnable = true;
				flipEnable = true;
				horizontal.setEnabled(true);
				vertical.setSelected(false);
				vertical.setEnabled(false);		
				
			} else {
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

			if (VideoFunctionality.dimentionsNotEmpty()) {
				// Get the video path and length
				VideoFunctionality.setVideoInfo();

				// Reference for JOptionPane():
				// http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html

				// check if the file videoEditFields exists in the working directory
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
				
				// Save all user inputs to the hidden VideoEditFields text
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
	 * setAllFields Method sets all the fields of the video class to the
	 * previously saved values
	 * 
	 * @param videoFieldsPath
	 *            : the path of the stored video fields
	 */
	protected void setAllFields(String videoFieldsPath) {
		
		File f = new File(videoFieldsPath);
		
		try {

			// read the videoEditFields file and sets the fields for video
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
			e.printStackTrace();
		}
	}

	
}