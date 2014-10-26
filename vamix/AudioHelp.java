package vamix;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 * SoftEng206 Assignment3 - audioHelp class
 * @author Chahat Chawla and Zainab Al Lawati
 *
 */

public class AudioHelp extends JPanel {

	//main header label
	private JLabel helpLabel = new JLabel("Help & Tips - Audio Manipulation");

	
	//remove labels
	private JLabel removelabel = new JLabel("Remove : ");
	private JLabel removeInfoLabel = new JLabel(
			"The remove operation, enables the settings to remove the audio of the imported video.");

	//extract labels
	private JLabel extractlabel = new JLabel("Extract : ");
	private JLabel extractInfoLabel = new JLabel(
			"The extract operation, enables the settings to extract the whole or a subset audio of");
	private JLabel extractInfo2Label = new JLabel(
			"the imported media (can be an audio or a video file). To enable the optional setting of");
	private JLabel extractInfo3Label = new JLabel(
			"extracting a subset audio of the media file, enable the check for set duration and ");
	private JLabel extractInfo4Label = new JLabel(
			"specify the start time and length in the format hh:mm:ss. However, please ensure that");
	private JLabel extractInfo5Label = new JLabel(
			"the start time + the length does not exceed the duration of the media file. The output");
	private JLabel extractInfo6Label = new JLabel(
			"file name you choose must not already exist in the chosen project.");

	
	//add labels
	private JLabel addlabel = new JLabel("Add: ");
	private JLabel addInfoLabel = new JLabel(
			"The add operation, enables the settings to add an audio track to the whole or a subset ");
	private JLabel addInfo2Label = new JLabel(
			"of the imported video. To enable the optional setting of adding an audio track to a ");
	private JLabel addInfo3Label = new JLabel(
			"subset of the media file, enable the check for set duration and specify the start time");
	private JLabel addInfo4Label = new JLabel(
			"and length in the format hh:mm:ss. However, please ensure that the start time + the ");
	private JLabel addInfo5Label = new JLabel(
			"length does not exceed the duration of the media file. Also, the option to select ");
	private JLabel addInfo6Label = new JLabel(
			"duration is not allowed when the remove operation is enabled, and hence the add ");
	private JLabel addInfo7Label = new JLabel(
			"operation acts like a replace operation. The audio file chosen must be an .mp3 file, ");
	private JLabel addInfo8Label = new JLabel(
			"you can also use the play audio button to listen to the audio file you selected.");

	// Initializing the Fonts
	private Font defaultFont = new Font("TimesRoman", Font.PLAIN, 12);
	private Font headLineFont = new Font("TimesRoman", Font.BOLD, 14);


	public AudioHelp() {
		
		// Set the fonts
		helpLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		removelabel.setFont(headLineFont);
		extractlabel.setFont(headLineFont);
		addlabel.setFont(headLineFont);
		
		removeInfoLabel.setFont(defaultFont);
		extractInfoLabel.setFont(defaultFont);
		extractInfo2Label.setFont(defaultFont);
		extractInfo3Label.setFont(defaultFont);
		extractInfo4Label.setFont(defaultFont);
		extractInfo5Label.setFont(defaultFont);
		extractInfo6Label.setFont(defaultFont);
		
		addInfoLabel.setFont(defaultFont);
		addInfo2Label.setFont(defaultFont);
		addInfo3Label.setFont(defaultFont);
		addInfo4Label.setFont(defaultFont);
		addInfo5Label.setFont(defaultFont);
		addInfo6Label.setFont(defaultFont);
		addInfo7Label.setFont(defaultFont);
		addInfo8Label.setFont(defaultFont);
		
		// Set the sizes for all labels
		removelabel.setPreferredSize(new Dimension(600, 25));
		removeInfoLabel.setPreferredSize(new Dimension(600, 20));
		
		extractlabel.setPreferredSize(new Dimension(600, 25));

		extractInfoLabel.setPreferredSize(new Dimension(600, 20));
		extractInfo2Label.setPreferredSize(new Dimension(600, 20));
		extractInfo3Label.setPreferredSize(new Dimension(600, 20));
		extractInfo4Label.setPreferredSize(new Dimension(600, 20));
		extractInfo5Label.setPreferredSize(new Dimension(600, 20));
		extractInfo6Label.setPreferredSize(new Dimension(600, 20));

		addlabel.setPreferredSize(new Dimension(600, 25));
		
		addInfoLabel.setPreferredSize(new Dimension(600, 20));
		addInfo2Label.setPreferredSize(new Dimension(600, 20));
		addInfo3Label.setPreferredSize(new Dimension(600, 20));
		addInfo4Label.setPreferredSize(new Dimension(600, 20));
		addInfo5Label.setPreferredSize(new Dimension(600, 20));
		addInfo6Label.setPreferredSize(new Dimension(600, 20));
		addInfo7Label.setPreferredSize(new Dimension(600, 20));
		addInfo8Label.setPreferredSize(new Dimension(600, 20));
		
		
		//add the header 
		add(helpLabel);
		
		//add the Labels for remove information 
		add(removelabel);
		add(removeInfoLabel);
		
		//add the Labels for extract information 
		add(extractlabel);
		add(extractInfoLabel);
		add(extractInfo2Label);
		add(extractInfo3Label);
		add(extractInfo4Label);
		add(extractInfo5Label);
		add(extractInfo6Label);

		//add the Labels for add information 
		add(addlabel);

		add(addInfoLabel);
		add(addInfo2Label);
		add(addInfo3Label);
		add(addInfo4Label);
		add(addInfo5Label);
		add(addInfo6Label);
		add(addInfo7Label);
		add(addInfo8Label);
		
	

	}
}