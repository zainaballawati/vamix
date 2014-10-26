
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
import javax.swing.JTextArea;
import javax.swing.JList;

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
	public static JTextField startTime = new JTextField("hh:mm:ss");
	public static JTextField endTime = new JTextField("hh:mm:ss");
	private JButton generateButton = new JButton("Generate subtitle");
	private JButton loadBtn = new JButton("Load subtitle");

	// Initializing the separators
	private JLabel separator = new JLabel("");
	private JLabel separator4 = new JLabel("");
	private JLabel separator5 = new JLabel("");
	private JLabel separator6 = new JLabel("");
	private JLabel separator8 = new JLabel("");
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
	private final JLabel label = new JLabel("");
	private final JButton loadButton = new JButton("Load subtitle");
	private final JLabel lblSubtitle = new JLabel("Subtitle:");
	private final JTextArea textArea = new JTextArea();
	private final JButton saveButton = new JButton("Save");
	private final JButton deleteButton = new JButton("Delete");
	private final JLabel label_1 = new JLabel("");
	private final JButton getButton1 = new JButton("get time");
	private final JButton getButton2 = new JButton("get time");
	private final JList list = new JList();

	/**
	 * Constructor for VideoGui() -Sets up the GUI for video
	 * manipulation tab -Sets up the default layout
	 */

	public SubtitlesGui() {

		// change the font of the title, subTitles and starLabels
		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		videoEditLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));

		// set the columns of the textFields
		generateButton.setPreferredSize(new Dimension(150, 25));
		generateButton.addActionListener(this);

		// adding all components to the Panel
		add(videoEditLabel);
		add(separator);
		add(separator4);
		add(startLabel);
		startTime.setColumns(6);
		add(startTime);
		getButton1.setPreferredSize(new Dimension(100, 25));
		
		add(getButton1);
		label_1.setPreferredSize(new Dimension(525, 0));
		
		add(label_1);
		add(endLabel);
		endTime.setColumns(6);
		add(endTime);
		getButton2.setPreferredSize(new Dimension(100, 25));
		
		add(getButton2);
		add(separator6);
		separator6.setPreferredSize(new Dimension(525, 0));
		
		add(lblSubtitle);
		textArea.setRows(3);
		textArea.setColumns(22);
		
		add(textArea);
		add(separator5);
		separator5.setPreferredSize(new Dimension(525, 0));
		
		add(list);
		saveButton.setPreferredSize(new Dimension(75, 25));
		
		add(saveButton);
		deleteButton.setPreferredSize(new Dimension(75, 25));
		
		add(deleteButton);
		add(separator8);
		add(separator10);
		label.setPreferredSize(new Dimension(525, 10));

		add(label);
		loadButton.setPreferredSize(new Dimension(150, 25));
		
		add(loadButton);
		add(generateButton);

		// setting the size for the seperators
		separator.setPreferredSize(new Dimension(525, 10));
		separator4.setPreferredSize(new Dimension(525, 10));
		separator8.setPreferredSize(new Dimension(525, 0));
		separator10.setPreferredSize(new Dimension(525, 15));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == generateButton) {

			
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