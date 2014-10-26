package mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import vamix.Download;

public class PlayerGui extends JPanel implements ActionListener, ChangeListener /*MouseListener*/ {

	private static final long serialVersionUID = 1L;
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	protected static EmbeddedMediaPlayer video;

	private JButton fastFwdBtn = new JButton();
	private JButton backFwdBtn = new JButton();
	public static JButton playBtn = new JButton();
	private JButton stopBtn = new JButton();
	public static JButton muteBtn = new JButton();
	public static JLabel timeDisplay = new JLabel("00:00:00");
	private JSlider volume = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
	public static JSlider videoTimeline;
	public final static int maxTime = 10000;

	public ImageIcon forwardIcon;
	public ImageIcon backwardIcon;
	public static ImageIcon playIcon;
	public static ImageIcon pauseIcon;
	public ImageIcon stopIcon;
	public static ImageIcon muteIcon;
	public static ImageIcon unmuteIcon;

	private Download download;
	
	private PlayBackFunctionality playBack = new PlayBackFunctionality();
	
	/*
	 * The class constructor
	 */
	public PlayerGui() {

		try {
			
			//Get the icons for buttons
			forwardIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/forward.png")));
			backwardIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/backward.png")));
			playIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/play_big.png")));
			pauseIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/pause_big.png")));
			stopIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/stop.png")));
			muteIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/mute.png")));
			unmuteIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/unmute.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Set the button icons
		fastFwdBtn.setIcon(forwardIcon);
		fastFwdBtn.setBorderPainted(false); 
		fastFwdBtn.setContentAreaFilled(false); 
		fastFwdBtn.setFocusPainted(false); 
		fastFwdBtn.setOpaque(false);

		backFwdBtn.setIcon(backwardIcon);
		backFwdBtn.setBorderPainted(false); 
		backFwdBtn.setContentAreaFilled(false); 
		backFwdBtn.setFocusPainted(false); 
		backFwdBtn.setOpaque(false);

		playBtn.setIcon(playIcon);
		playBtn.setBorderPainted(false); 
		playBtn.setContentAreaFilled(false); 
		playBtn.setFocusPainted(false); 
		playBtn.setOpaque(false);

		stopBtn.setIcon(stopIcon);
		stopBtn.setBorderPainted(false); 
		stopBtn.setContentAreaFilled(false); 
		stopBtn.setFocusPainted(false); 
		stopBtn.setOpaque(false);

		muteBtn.setIcon(unmuteIcon);
		muteBtn.setBorderPainted(false); 
		muteBtn.setContentAreaFilled(false); 
		muteBtn.setFocusPainted(false); 
		muteBtn.setOpaque(false);

		//Set the media player
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.setPreferredSize(new Dimension(525, 400));
		video = mediaPlayerComponent.getMediaPlayer();

		//Set the time line
		videoTimeline.setValue(0);
		videoTimeline.setMinimum(0);
		videoTimeline.setMaximum(maxTime);
		videoTimeline.setEnabled(false);
		
		videoTimeline.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				//setSliderBasedPosition();
//				if (isPlaying){
//					play();
//					isPlaying = false;
//				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
//				if (mediaPlayer.isPlaying()){
//					pause();
//					isPlaying = true;
//				}
				//setSliderBasedPosition();
			}
		});
		
		//Add all the components to panels
		JPanel timeBar = new JPanel();
		//TODO add a time line class and add it to this panel
		timeBar.add(timeDisplay);
		
		JPanel toolBar = new JPanel();
		toolBar.add(stopBtn);
		toolBar.add(backFwdBtn);
		toolBar.add(playBtn);
		toolBar.add(fastFwdBtn);
		toolBar.add(muteBtn);
		toolBar.add(volume);

		//Add all the panels to the main VideoPlayer panel
		setLayout(new BorderLayout());
		add(mediaPlayerComponent, BorderLayout.NORTH);
		add(timeBar, BorderLayout.CENTER);
		add(toolBar, BorderLayout.SOUTH);

		// adding action listeners to all the buttons so when a user clicks a
		// button, the corresponding actions are done.
		backFwdBtn.addActionListener(this);
		stopBtn.addActionListener(this);
		playBtn.addActionListener(this);
		fastFwdBtn.addActionListener(this);
		muteBtn.addActionListener(this);
		volume.addChangeListener(this);

		//Setting preferred sizes for some components
		timeDisplay.setPreferredSize(new Dimension(100,20));
		volume.setPreferredSize(new Dimension(120,40));

		//Set the volume values back to default when the user closes the Vamix player
		vamix.VamixFrame.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				video.mute(false);
				video.setVolume(100);
			}
		});
		

	}

	/**
	 * Activate the volume bar
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (video.isPlayable()) {
			if (e.getSource() == volume) {
				video.setVolume(volume.getValue());

			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == muteBtn) {
			playBack.mute(video);
			
		} else if (e.getSource() == stopBtn) {
			playBack.stop(video);
			
		} else if (e.getSource() == playBtn) {
			playBack.play(video, download);
			
		} else if (e.getSource() == fastFwdBtn) {
			playBack.forward(video);
			
		} else if (e.getSource() == backFwdBtn) {
			playBack.rewind(video);
				
		}

	}

}
