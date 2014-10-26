package vamix;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import audioManipulator.AudioFunctionality;

public class Menu extends JMenuBar implements ActionListener {

	protected JMenu fileMenu;
	protected static JMenu submenu;
	protected static JMenuItem export;
	protected JMenu helpMenu;
	private JMenuItem newProj, openProj, fromURL, fromFolder, exit, help;

	private FileNameExtensionFilter mediaFilter = new FileNameExtensionFilter( "mp3 & mp4 Clips", "mp3", "mp4");
	private FileNameExtensionFilter projectFilter = new FileNameExtensionFilter( "Vamix projects", "vamix");
	
	private static String workingDir = "";
	private static String hiddenDir = "";
	private static String projectPath = "";
	
	private String inputVideo;
	private Download download;
	

	/*
	 * The class constructor
	 */
	public Menu() {

		//Build the file menu.
		fileMenu = new JMenu("File");
		add(fileMenu);

		//Create a group of JMenuItems
		newProj = new JMenuItem("New project");
		fileMenu.add(newProj);
		openProj = new JMenuItem("Open project", new ImageIcon("images/middle.gif"));
		fileMenu.add(openProj);

		//Create a submenu in the file menu
		fileMenu.addSeparator();
		submenu = new JMenu("Import media");
		fromURL = new JMenuItem("Download from URL");
		submenu.add(fromURL);
		fromFolder = new JMenuItem("Import from computer");
		submenu.add(fromFolder);
		submenu.setEnabled(false);
		fileMenu.add(submenu);
		export = new JMenuItem("Export video");
		export.setEnabled(false);
		fileMenu.add(export);

		//Create Exit JMenuItem
		fileMenu.addSeparator();
		exit = new JMenuItem("Exit");
		fileMenu.add(exit);
		add(fileMenu);

		//Build second menu in the menu bar.
		helpMenu = new JMenu("Help");
		help = new JMenuItem("Help & Tips");
		helpMenu.add(help);
		add(helpMenu);
		
		//Add action listeners
		newProj.addActionListener(this);
		openProj.addActionListener(this);
		fromURL.addActionListener(this);
		fromFolder.addActionListener(this);
		export.addActionListener(this);
		exit.addActionListener(this);
		help.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newProj) {

			//Refresh the project
			VamixFrame.audioMan.refreshAudioMan();
			VamixFrame.textEdit.refreshtextEdit();
			VamixFrame.textEdit.refreshTiTleScreen();
			VamixFrame.textEdit.refreshCreditScreen();
			
			//Stop the played video
			//TODO figure out how to fix it
			//VideoPlayer.stopVideo();
			inputVideo = null;

			int i = -1;
			while (i<0) {	//Loop until project is created successfully or cancelled

				//Take the project name as an input from the user
				String projectName = JOptionPane.showInputDialog(
						null,
						"Project name: ",
						"Create new project",
						JOptionPane.DEFAULT_OPTION);
				if (projectName == null) {
					//Cancel is pressed, go back to main frame
					i++;

				} else if (projectName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter the project's name!");

				} else {
					/*
					 * Create a matcher to check that the output name has no space in middle
					 * @reference: http://stackoverflow.com/questions/4067809/how-to-check-space-in-string
					 */
					Pattern pattern = Pattern.compile("\\s");
					Matcher matcher = pattern.matcher(projectName);
					boolean found = matcher.find();

					if (found) {
						JOptionPane.showMessageDialog(null, "Sorry, project name cannot contain spaces.");

					} else {
						//Allow the user to choose the project file destination and set it as his working directory
						JFileChooser dirChooser = new JFileChooser();
						dirChooser.setDialogTitle("Save to ...");
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnVal = dirChooser.showSaveDialog(null);
						//If no directory was selected, don't go back to main frame
						if(returnVal != JFileChooser.APPROVE_OPTION) {
							return;
						}

						//Get the name of the chosen directory
						workingDir = dirChooser.getSelectedFile().getPath()+"/"+projectName;
						//Create the working directory
						File workDir = new File(workingDir);
						workDir.mkdir();

						//Check that the project doesn't exists in the specified directory
						String outputFileName = workingDir+"/"+projectName+".vamix";
						File f = new File(outputFileName);
						hiddenDir = workingDir+"/."+projectName+".vamix";
						File hidden = new File(hiddenDir);

						if ( f.exists()) {
							//Allow user to choose either overwriting the current project or cancel creating new project
							Object[] existOptions = {"Cancel", "Overwrite"};
							int optionChosen = JOptionPane.showOptionDialog(null, "Project already exists. " +
									"Do you want to overwrite the existing project?",
									"Project Exists!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
									null,existOptions, existOptions[0]);

							if (optionChosen == 1) { //if overwrite, delete the existing file and hidden directory
								f.delete();
								hidden.delete();
								i++;

							} else { //Cancel creating new project
								return;
							}
						}
						i++; //to exit the while loop

						try {
							//Create the main project file
							f.createNewFile();
							//Create the hidden directory that holds all the results of the intermediate processes
							hidden.mkdir();

							//Write the hidden directory and the working directory to the main project file
							FileWriter fw = new FileWriter(f);
							BufferedWriter bw = new BufferedWriter(fw);
							projectPath = f.getPath();
							bw.write(projectPath); //Store the project path
							bw.newLine();
							bw.write(hidden.toString()); //Store the hidden folder path
							bw.newLine();
							bw.write(workingDir); //Store the working directory
							bw.newLine();
							bw.close();

							//When project is created successfully, enable importing media
							submenu.setEnabled(true);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			//-----------------------------------------------------------------------------------
		} else if (e.getSource() == openProj) {
			//Refresh the project
			VamixFrame.audioMan.refreshAudioMan();
			VamixFrame.textEdit.refreshtextEdit();
			VamixFrame.textEdit.refreshTiTleScreen();
			VamixFrame.textEdit.refreshCreditScreen();

			//Prompt the user to choose a project
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(projectFilter); //Shownly correct extension type file by default

			int returnVal = chooser.showOpenDialog(null);
			//Go back to the main frame if choosing project was cancelled
			if(returnVal != JFileChooser.APPROVE_OPTION) {
				return;
			}
			projectPath = chooser.getSelectedFile().toString();
			File f = new File(projectPath);
			try {
				//Read the project file
				BufferedReader reader;
				reader = new BufferedReader(new FileReader(f));
				if (!projectPath.equals(reader.readLine())) { //if the project file selected is not valid
					JOptionPane.showMessageDialog(null, "Project file selected is no valid, please try again.");
					reader.close();
					return;
				}

				//Store the values of the main fields
				hiddenDir = reader.readLine();
				workingDir = reader.readLine();
				inputVideo = reader.readLine();
				reader.close();

				//Check that the opened project contains a valid media
				if ( inputVideo == null ) {
					JOptionPane.showMessageDialog(null, "Error! no video was imported in this project\nPlease start a new project or open a valid one.");
					return;
				}

				//Check that the video is still in the working directory
				File f1 = new File(inputVideo);
				if (!f1.exists()) {
					JOptionPane.showMessageDialog(null, "Error! The video imported is not found.\n(check that it is in the correct directory)");
					return;
				}

				//enable editing the video and exporting
				submenu.setEnabled(false);
				VamixFrame.tabbedPane.setEnabled(true);
				VamixFrame.audioMan.enableAudioMan(true);
				export.setEnabled(true);

				//Set the main project and video info in the editing tabs
				VamixFrame.audioMan.setVideoInfo();
				VamixFrame.textEdit.setVideoInfo();

				//Check what edits were performed previously
				File f2 = new File(workingDir+"/.audioFields");
				File f3 = new File(workingDir+"/.creditFields");
				File f4 = new File(workingDir+"/.titleFields");
				File f5 = new File(workingDir+"/.videoEditFields");

				if (f2.exists()) {
					VamixFrame.audioMan.setAllFields(workingDir + "/.audioFields");
				}
				
				if (f5.exists()) {
					VamixFrame.videoEdit.setAllFields(workingDir + "/.videoEditFields");
				}
				
				if (!f3.exists() && f4.exists()) {
					VamixFrame.textEdit.setCreditFields(workingDir + "/.creditFields");
				} else if ((f3.exists() && f4.exists()) || (f3.exists() && !f4.exists())){
					VamixFrame.textEdit.setTitleFields(workingDir + "/.titleFields");
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			//-----------------------------------------------------------------------------------
			//If user chooses to import video by downloading it from web
			//Assume that the URL is for a valid mp4 video
		} else if (e.getSource() == fromURL) {

			//Create and set a new download frame
			JFrame downloadFrame = new JFrame("Download");
			download = new Download(projectPath, workingDir);
			downloadFrame.setContentPane(download);
			downloadFrame.setLocation(300, 300);
			downloadFrame.setSize(500, 200);
			downloadFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			downloadFrame.setVisible(true);

			//-----------------------------------------------------------------------------------
			//If user chooses to import video from local folder
		} else if (e.getSource() == fromFolder) {

			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(mediaFilter); //Show only correct extension type file by default
			int returnVal = chooser.showOpenDialog(null);

			//Store the chosen file as an input video
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				inputVideo = chooser.getSelectedFile().toString();

				File file = new File(inputVideo);
				Path path = file.toPath();
				String type = "";
				try {
					type = Files.probeContentType(path);
				} catch (IOException f) {
					f.printStackTrace();
				}

				// if the file is NOT an audio or a video or audio file, notify the user and
				// allow them to import again
				if (!(type.equals("audio/mpeg")) && !(type.equals("video/mp4"))){
					JOptionPane.showMessageDialog(null, "ERROR: file imported does not" +
							"refer to a valid audio or video file. Please select a new input file!");
					return;
				} else if (type.equals("audio/mpeg")) { //If the file is an audio only, enable extracting only
					VamixFrame.audioMan.enableExtractOnly();

				} else { //2> If it is a video enable all the editing options
					VamixFrame.tabbedPane.setEnabled(true);
					VamixFrame.audioMan.enableAudioMan(true);
				}

				export.setEnabled(true);
				submenu.setEnabled(false);

			} else { //No media was imported
				return;
			}

			/*
			 * Store the name, directory and length of the input video in a txt file
			 */
			try {
				//Open the video project file
				File f = new File(projectPath);
				FileWriter fw = new FileWriter(f, true);
				BufferedWriter bw = new BufferedWriter(fw);

				//Store the video info in the txt file
				File videoFile = new File(inputVideo);
				bw.write(videoFile.getPath());
				bw.newLine();

				//Get the length of the video
				String cmd = "avprobe -loglevel error -show_streams " + videoFile.getPath() + " | grep -i duration | cut -f2 -d=";
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				builder.redirectErrorStream(true);
				Process process = builder.start();

				//read the output of the process
				InputStream outStr = process.getInputStream();
				BufferedReader stdout = new BufferedReader(new InputStreamReader(outStr));

				//Write the length of the input video
				bw.write(stdout.readLine());
				bw.newLine();
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			//-----------------------------------------------------------------------------------
		} else if (e.getSource() == export) {

			int i = -1;
			while (i<0) {	//Loop until the user provides a valid output name or cancels exporting
				//Take the output file name as an input from the user
				String outputName = JOptionPane.showInputDialog(
						null, "Please choose the output name: ",
						"Export project",
						JOptionPane.DEFAULT_OPTION);

				if (outputName == null) { //Cancel is pressed, go back to main frame
					i++;

				} else if (outputName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Sorry, output file name cannot be empty!");

				} else {
					/*
					 * Create a matcher to check that the output name has no space in middle
					 * @reference: http://stackoverflow.com/questions/4067809/how-to-check-space-in-string
					 */
					Pattern pattern = Pattern.compile("\\s");
					Matcher matcher = pattern.matcher(outputName);
					boolean found = matcher.find();

					if (found) {
						JOptionPane.showMessageDialog(null, "Sorry, output file name cannot contain spaces.");
					} else {

						//Check that the file doesn't exists in the specified directory
						String outputFileName = workingDir+"/"+outputName+".mp4";
						File f = new File(outputFileName);
						if ( f.exists()) {
							//Allow user to choose either overwriting the existing file or cancel exporting
							Object[] existOptions = {"Cancel", "Overwrite"};
							int optionChosen = JOptionPane.showOptionDialog(null, "File already exists." +
									"Do you want to overwrite the existing media file?",
									"File Exists!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
									null,existOptions, existOptions[0]);

							if (optionChosen == 1) { //if overwrite, delete the existing file
								f.delete();
								i++;

							} else { //Cancel exporting project
								return;
							}
						}
						i++; //to exit the while loop


						//Declare exit status variables
						int textExitStatus = 0;
						int audioExitStatus = 0;
						int videoEditExitStatus = 0;

						//Get the video path
						String videoPath = "";
						File f1 = new File(projectPath);

						try {
							//Read the file and save the necessary variables
							BufferedReader reader;
							reader = new BufferedReader(new FileReader(f1));
							reader.readLine();
							reader.readLine();
							reader.readLine();
							videoPath = reader.readLine();
							reader.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						//Check what edits were performed previously
						File f2 = new File(workingDir+"/.audioFields");
						File f3 = new File(workingDir+"/.creditFields");
						File f4 = new File(workingDir+"/.titleFields");
						File f5 = new File(workingDir+"/.videoEditFields");
						
						boolean videoEditing = f5.exists();
						boolean audio = f2.exists();
						boolean txtEditing = f3.exists()||f4.exists();
						

						//If no edits were done in the project do not export
						if (!audio && !txtEditing && !videoEditing) {
							JOptionPane.showMessageDialog(null, "Please save the changes before exporting");
							return;

						} else if (audio && !txtEditing && !videoEditing ) { //User did audio manipulation only
							audioExitStatus = AudioFunctionality.runCommands(videoPath, outputFileName);

						} else if (!audio && !videoEditing && txtEditing) { //User did text editing only
							textExitStatus = VamixFrame.textEdit.runCommands(videoPath, outputFileName);
							
						} else if (!audio && videoEditing && !txtEditing) { //User did video editing only
							videoEditExitStatus = VamixFrame.videoEdit.runCommands(videoPath, outputFileName);

						} else if (!videoEditing && audio && txtEditing){ //User did txt and audio
							audioExitStatus = VamixFrame.audioMan.runCommands(videoPath, hiddenDir+"/temp.mp4");
							textExitStatus = VamixFrame.textEdit.runCommands(hiddenDir+"/temp.mp4", outputFileName);
							
						} else if (videoEditing && audio && !txtEditing){ //User did video and audio
							videoEditExitStatus = VamixFrame.videoEdit.runCommands(videoPath, hiddenDir+"/temp.mp4");
							audioExitStatus = VamixFrame.audioMan.runCommands(hiddenDir+"/temp.mp4", outputFileName);
							
						} else if (videoEditing && !audio && txtEditing){ //User did video and txt
							videoEditExitStatus = VamixFrame.videoEdit.runCommands(videoPath, hiddenDir+"/temp.mp4");
							textExitStatus = VamixFrame.textEdit.runCommands(hiddenDir+"/temp.mp4", outputFileName);
							
						} else if (videoEditing && audio && txtEditing){ //User did all
							videoEditExitStatus = VamixFrame.videoEdit.runCommands(videoPath, hiddenDir+"/temp.mp4");
							audioExitStatus = VamixFrame.audioMan.runCommands(hiddenDir+"/temp.mp4", hiddenDir+"/temp2.mp4");
							textExitStatus = VamixFrame.textEdit.runCommands(hiddenDir+"/temp2.mp4", outputFileName);	
						} 


						//Show error messages or completion message
						if (audioExitStatus != 0) {
							JOptionPane.showMessageDialog(null, "Error encountered in audio manipulation. Exporting is aborted!");
						} else if (textExitStatus != 0) {
							JOptionPane.showMessageDialog(null, "Error encountered in text editing. Exporting is aborted!");
						} else {
							JOptionPane.showMessageDialog(null, "Export is Successful!");
						}

						//Delete the hidden folder that holds the intermediate outputs
						File f6 = new File(hiddenDir);
					//	f6.delete();
					}
				}
			}
			//-----------------------------------------------------------------------------------

			//Close the frame
		} else if (e.getSource() == exit) {
			VamixFrame.frame.dispose();
		} else if (e.getSource() == help) {

			//Create and set the help window
			JFrame helpFrame = new JFrame("Help & Tips"); 
			JTabbedPane helpTips = new JTabbedPane(); 
			helpTips.setPreferredSize(new Dimension(525, 440)); 
			helpTips.add("Audio Manipulator", new AudioHelp()); 
			helpTips.add("Text Editor", new TextEditorHelp()); //Create a new help frame 
			helpFrame.setContentPane(helpTips); 
			helpFrame.setLocation(400, 300); 
			helpFrame.setSize(680, 580); 
			helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			helpFrame.setVisible(true);
		} 

	}
	
	/**
	 * Gets the full path of the project
	 */
	public static String getProjectPath() {
		if (!projectPath.isEmpty()) {
			return projectPath;
		} else {
			return "No file yet";
		}
	}

}
