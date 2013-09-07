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
	Player player;
	
	public void setSources(Map<String, Object> eventSources) {
		this.eventSources = eventSources;
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
	            
	            //TODO create a JTextArea of all the songs in the directory
	            
	            
	            //this is the local media player that we no longer use
//	            try {
//	            	//Creates player, starts stream from file (plays song)
//	            	//.wav format only
//					player = Manager.createPlayer(new URL("file:///" + singleFile));
//					player.addControllerListener(this);
//					player.start();
//				} catch (Exception x) {
//					System.out.println(x);
//				}
	            
	        }
		}
	}

	@Override
	public void controllerUpdate(ControllerEvent arg0) {
		
	}
}
