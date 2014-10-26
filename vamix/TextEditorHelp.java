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
 * SoftEng206 Assignment3 - textEditorHelp class
 * @author Chahat Chawla and Zainab Al Lawati
 *
 */
public class TextEditorHelp extends JPanel {

	//labels for text edit help
	private JLabel helpLabel = new JLabel("Help & Tips - Text Editor");

	private JLabel screenlabel = new JLabel("Title Screen or Credit Screen : ");
	private JLabel screenInfoLabel = new JLabel(
			"To enables the settings to add a title screen or a credit screen to the imported video,");
	private JLabel screenInfo2Label = new JLabel(
			"select the screen type to title screen or credit screen accordingly. To enable both title");
	private JLabel screenInfo3Label = new JLabel(
			"and credit screen, make sure settings for both the screens are saved before exporting.");
	private JLabel screenInfo4Label = new JLabel(
			"For both title and credit screen, specify a duration in seconds, choose one of the");
	private JLabel screenInfo5Label = new JLabel(
			"background image options, select the font type, style, size and colour and add the text ");
	private JLabel screenInfo6Label = new JLabel(
			"to the the text area. If you select the background image option to be a frame from the video,");
	private JLabel screenInfo7Label = new JLabel(
			"please specify the time in the format hh:mm:ss and make sure it is within the range of the ");
	private JLabel screenInfo8Label = new JLabel(
			"video. If the text is empty, the title and/or credit screen will be added to the video ");
	private JLabel screenInfo9Label = new JLabel(
			"but will show no text. Please use the preview button before saving to preview your settings.");

	
	// Initializing the Fonts
	private Font defaultFont = new Font("TimesRoman", Font.PLAIN, 12);
	private Font headLineFont = new Font("TimesRoman", Font.BOLD, 14);

	public TextEditorHelp() {

		// Set the font
		helpLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
		screenlabel.setFont(headLineFont);
		
		screenInfoLabel.setFont(defaultFont);
		screenInfo2Label.setFont(defaultFont);
		screenInfo3Label.setFont(defaultFont);
		screenInfo4Label.setFont(defaultFont);
		screenInfo5Label.setFont(defaultFont);
		screenInfo6Label.setFont(defaultFont);
		screenInfo7Label.setFont(defaultFont);
		screenInfo8Label.setFont(defaultFont);
		screenInfo9Label.setFont(defaultFont);
		
		// Set the sizes for all labels
		screenlabel.setPreferredSize(new Dimension(600, 25));

		screenInfoLabel.setPreferredSize(new Dimension(600, 20));
		screenInfo2Label.setPreferredSize(new Dimension(600, 20));
		screenInfo3Label.setPreferredSize(new Dimension(600, 20));
		screenInfo4Label.setPreferredSize(new Dimension(600, 20));
		screenInfo5Label.setPreferredSize(new Dimension(600, 20));
		screenInfo6Label.setPreferredSize(new Dimension(600, 20));
		screenInfo7Label.setPreferredSize(new Dimension(600, 20));
		screenInfo8Label.setPreferredSize(new Dimension(600, 20));
		screenInfo9Label.setPreferredSize(new Dimension(600, 20));

		//add the header 
		add(helpLabel);

		//add the Labels for screen information 
		add(screenlabel);
		add(screenInfoLabel);
		add(screenInfo2Label);
		add(screenInfo3Label);
		add(screenInfo4Label);
		add(screenInfo5Label);
		add(screenInfo6Label);
		add(screenInfo7Label);
		add(screenInfo8Label);
		add(screenInfo9Label);


	}

}