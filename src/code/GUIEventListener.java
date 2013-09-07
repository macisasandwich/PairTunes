package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Map;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import java.io.File;
import java.io.FilenameFilter;

public class GUIEventListener implements ActionListener, ControllerListener {

	Map<String, Object> eventSources;
	Window window;
	Player player;
	
	public void setWindow(Window w) {
		this.window = w;
		this.eventSources = w.getSources();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == eventSources.get("loadButton")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int returnVal = fileChooser.showOpenDialog(fileChooser);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {							//Actually gives us the current directory
	        	String folderDir = fileChooser.getSelectedFile().toString();
	            ((JTextField) (eventSources.get("displaySong"))).setText(folderDir);
	            
	            File[] files = new File(folderDir).listFiles(new FilenameFilter() {
	            	public boolean accept(File dir, String filename) 
	            	    { return filename.endsWith(".wav") || filename.endsWith(".mp3"); }
	            } );
	            
//	            for (File file: files) {
//	            	window.songList.setListData(new String[] { file.getName() } );
//	            	System.out.println(file.getName());
//	            }
	            
	            //TODO if songList is empty, clear the empty message

//	            RTPServer.entry("172.16.138.68", "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav");
//	            RTPClient.entry("172.16.150.122");
	            
//	            InitiaterClient.startComm();
	        }
		}
	}

	@Override
	public void controllerUpdate(ControllerEvent arg0) {
		
	}
}
