package vamix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * SoftEng206 Project- Main Vamix Player class
 * @author Chahat Chawla and Zainab Al Lawati
 *
 */

public class VamixFrame {
	/**
	 * Main method that runs the Vamix player
	 * @param args
	 */
	public static void main(final String[] args) {
		
		//Change the look and feel of the GUI
		//@ref http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		// If Nimbus is not available, just preview the default look and feel
		}
		
		//Create Vamix frame and run it
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VamixFrame();
			}
		});
	}

	//Initialize all the fields and JComponents
	public static JFrame frame;
	
	public static JTabbedPane tabbedPane;
	public static AudioManipulator audioMan;
	public static TextEditor textEdit;
	public static VideoEdit videoEdit;


	/*
	 * The class constructor
	 */
	private VamixFrame() {

		frame = new JFrame("The awesome VAMIX");

		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(525, 440));
		audioMan = new AudioManipulator();
		tabbedPane.add("Audio Manipulator", audioMan);
		textEdit = new TextEditor();
		tabbedPane.add("Text Editor", textEdit);
		//Create the video manipulating tab pane
		videoEdit = new VideoEdit();
		tabbedPane.add("Video Manipulator", videoEdit);
		//Disable the tabs when first loading the main window
		tabbedPane.setEnabled(false);
		audioMan.enableAudioMan(false);

		//Add the panels to a main panel
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new Menu(), BorderLayout.NORTH);
		panel.add(new mediaPlayer.PlayerGui(), BorderLayout.EAST);
		panel.add(tabbedPane, BorderLayout.WEST);

		//Set the frame
		frame.setContentPane(panel);
		frame.setLocation(100, 100);
		frame.setSize(1066, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


	}


}
