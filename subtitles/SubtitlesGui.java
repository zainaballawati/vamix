
package subtitles;

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
 * SoftEng206 Project - subtitles Gui class
 * @author Zainab Al Lawati
 *
 */

public class SubtitlesGui extends JPanel implements ItemListener,
ActionListener {


	private static final long serialVersionUID = 1L;

	// // Initializing the labels
	private JLabel videoEditLabel = new JLabel("Subtitles Generator");
	private JLabel startLabel = new JLabel("Start Time:");
	private JLabel endLabel = new JLabel("End Time:");

	// Initializing the textFields
	public static JTextField startTime = new JTextField("");
	public static JTextField endTime = new JTextField("");
	private JButton generateBtn = new JButton("Generate subtitle");
	private JButton loadBtn = new JButton("Load subtitle");

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

	public SubtitlesGui() {

		// change the font of the title, subTitles and starLabels
		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		resizeCheck.setFont(new Font("Dialog", Font.BOLD, 15));
		flipCheck.setFont(new Font("Dialog", Font.BOLD, 15));

		// set the columns of the textFields
		generateBtn.setPreferredSize(new Dimension(150, 25));
		resizeCheck.addItemListener(this);
		flipCheck.addItemListener(this);
		horizontal.addItemListener(this);
		vertical.addItemListener(this);
		generateBtn.addActionListener(this);

		// adding all components to the Panel
		add(videoEditLabel);
		add(separator);
		add(separator2);
		add(separator3);
		add(separator4);
		add(resizeCheck);
		add(separator5);
		add(separator6);
		add(startLabel);
		add(startTime);
		add(endLabel);
		add(endTime);
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
		add(generateBtn);

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

			} else {
				resizeEnable = false;

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

		if (e.getSource() == generateBtn) {

			
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

			height = reader.readLine();
		

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