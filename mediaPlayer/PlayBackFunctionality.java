package mediaPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import vamix.Download;


public class PlayBackFunctionality {

	private SkipTask longTask;
	private String inputVideo;

	public PlayBackFunctionality() {
		//Set the video playing and the video imported by user to null
		//to start the window with fresh inputs next time.
		vamix.VamixFrame.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				inputVideo = null;
			}
		});
	}

	/**
	 * Mutes the video if it is un-muted and vice versa
	 * @param video
	 */
	public void mute(EmbeddedMediaPlayer video) {
		if (video.isMute()) { // Un-mute the video
			video.mute(false);
			PlayerGui.muteBtn.setIcon(PlayerGui.unmuteIcon);
		} else { // mute the video
			video.mute(true);
			PlayerGui.muteBtn.setIcon(PlayerGui.muteIcon);
		}
	}

	/**
	 * Stops the current playing video and refreshes the time display
	 * @param video
	 */
	public void stop(EmbeddedMediaPlayer video) {
		if (video.isPlayable()) {
			video.stop();
			PlayerGui.timeDisplay.setText("00:00:00");
			PlayerGui.playBtn.setIcon(PlayerGui.playIcon);
		}
	}

	/**
	 * Fast forwards the current playing video by creating a forwarding task in the worker thread
	 * @param video
	 */
	public void forward(EmbeddedMediaPlayer video) {
		//If the video is already fast forwarding, cancel it and start a new forwarding task
		if (longTask != null && !longTask.isDone()) {
			longTask.cancel(false);
		}
		//Start the forwarding task if video is playing
		if (video.isPlaying()) {
			longTask = new SkipTask(10, video);
			longTask.execute();
			PlayerGui.playBtn.setIcon(PlayerGui.playIcon);
		}
	}

	/**
	 * Fast rewinds the current playing video by creating a rewinding task in the worker thread
	 * @param video
	 */
	public void rewind(EmbeddedMediaPlayer video) {
		//If the video is already is rewinding, cancel it and start a new rewind task
		if (longTask != null && !longTask.isDone()) {
			longTask.cancel(false);
		}
		//Start the rewind task if the video is playing
		if (video.isPlaying()) {
			longTask = new SkipTask(-10, video);
			longTask.execute();
			PlayerGui.playBtn.setIcon(PlayerGui.playIcon);
		}
	}

	/**
	 * Plays the imported or the downloaded video
	 * It also runs the ticker
	 * @param video
	 */
	public void play(final EmbeddedMediaPlayer video, Download download) {
		if (longTask != null && !longTask.isDone()) {
			//if the video is currently skipping, stop the skipping task and play the video
			longTask.cancel(false);
			video.play();
			PlayerGui.playBtn.setIcon(PlayerGui.pauseIcon);
		} else if (video.isPlaying()) {
			//If the video is playing, pause it.
			video.pause();
			PlayerGui.playBtn.setIcon(PlayerGui.playIcon);
		} else {
			if (!video.isPlayable()) {
				//If the mediaPlayerComponent doesn't have a video yet
				if (download != null || readVideoPath() != null) {
					if (download != null) { //Store the inputVideo that has been downloaded
						String chosenFile = download.getChosenFile();
						if (!chosenFile.equals("No file yet")) {
							inputVideo = chosenFile;
							video.playMedia(inputVideo);
							PlayerGui.playBtn.setIcon(PlayerGui.pauseIcon);
						}
					}
					if (readVideoPath() != null) { //Play the video that was imported from folder
						inputVideo = readVideoPath();
						video.playMedia(inputVideo);
						PlayerGui.playBtn.setIcon(PlayerGui.pauseIcon);
					}
					//Setting the timer
					Timer ticker = new Timer(200, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							/*
							 * Display the time in the format "hh:mm:ss"
							 * @reference: http://stackoverflow.com/questions/9214786/how-to-convert-the-seconds-in-this-format-hhmmss
							 */
							int time = (int) (video.getTime());
							SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
							TimeZone tz = TimeZone.getTimeZone("UTC");
							df.setTimeZone(tz);
							String formatedTime = df.format(new Date(time));	
							if (time < video.getLength()) {
								PlayerGui.timeDisplay.setText(formatedTime);

							} else {
								PlayerGui.playBtn.setIcon(PlayerGui.playIcon);
							}
						}
					});
					ticker.start();
				}
			} else { //If video is paused
				video.play();
				PlayerGui.playBtn.setIcon(PlayerGui.pauseIcon);
			}
		}

	}

	/**
	 * Reads the project file and store the full path of the imported video
	 * @return input video path
	 */
	private String readVideoPath() {
		//Get the main project file
		String projectPath = vamix.Menu.getProjectPath();
		File f = new File(projectPath);
		try {
			//Read the file and save the necessary variables
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(f));
			reader.readLine(); //project path
			reader.readLine(); //Hidden Directory
			reader.readLine(); //Working Directory
			String videoPath = reader.readLine();
			reader.close();
			return videoPath;

		} catch (IOException e1) {
			//Do nothing
		}

		return null;
	}

	/**
	 * SkipTask class rewinds or forwards the video in the
	 * worker thread.
	 */
	class SkipTask extends SwingWorker<Void, Void> {
		private int skipSpeed;
		EmbeddedMediaPlayer video;

		//Set the skipping duration
		public SkipTask(int time, EmbeddedMediaPlayer video) {
			this.skipSpeed = time;
			this.video = video;
		}
		//Skip the video until play is pressed
		@Override
		protected Void doInBackground() throws Exception {
			try {
				while (!isCancelled()) {
					video.skip(skipSpeed);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}
	}


}

