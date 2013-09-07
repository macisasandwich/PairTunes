package code;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	            
	            for (File file: files) {
	            	if (window.songListModel.getElementAt(0).equals("<Add some songs!>")) 
	            		window.songListModel.remove(0);
	            	window.songListModel.addElement(file.getName());
	            }
	            
	            //Set mouse listener for songList
	    		MouseListener mouseListener = new MouseAdapter() {
	    		    public void mouseClicked(MouseEvent e) {
	    		    	Rectangle r = window.songList.getCellBounds(0, window.songList.getLastVisibleIndex());
	    		    	if (r != null && r.contains(e.getPoint()) && e.getClickCount() == 2) {
	    		    		int index = window.songList.locationToIndex(e.getPoint());
	    		    		//edge case: we only add to queue if it's not the default non-song message
	    		    		if (!window.songListModel.getElementAt(index).equals("<Add some songs!>")) {
	    		    			window.queueModel.addElement(window.songListModel.getElementAt(index));
	    		    		}
	    		    	}
	    		    }
	    		};
	    		window.songList.addMouseListener(mouseListener);
	    		
	    		//Set mouse listener for queueList
	    		MouseListener queueMouseListener = new MouseAdapter() {
	    		    public void mouseClicked(MouseEvent e) {
	    		    	Rectangle r = window.queueList.getCellBounds(0, window.queueList.getLastVisibleIndex());
	    		    	if (r != null && r.contains(e.getPoint()) && e.getClickCount() == 2) {
	    		    		int index = window.queueList.locationToIndex(e.getPoint());
	    		    		//TODO actually start broadcasting
	    	    			System.out.println("Playing: " + window.queueModel.getElementAt(index));
	    		    	}
	    		    }
	    		};
	    		window.queueList.addMouseListener(queueMouseListener);
	    		
	    		//TODO have the top of queue be playing
	    		//TODO song list and queue are synced between comps

//	            RTPServer.entry("172.16.138.68", "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav");
//	            RTPClient.entry("172.16.150.122");

//	            InitiaterServer.startComm();
	            
	            /* Sam's IP = 172.16.200.239
	             * Jesse's IP = 172.16.150.122
	             * Stephen's IP = 172.16.138.68
	             */
	            
	            String samIP = "172.16.200.239";
	            String jesseIP = "172.16.150.122";
	            String stephenIP = "172.16.138.68";
	            int port1 = 42050;
	            int port2 = 25000;
	            
	            //Uncomment the following if you are SERVING the music
	            //temp testing to Sam's computer
//	            InitiaterClient ic = new InitiaterClient(samIP, port1);
//	            ic.initiate();
	            
	            //Uncomment the following if you are RECEVING the music
	            //temp testing to Jesse
	            InitiaterServer is = new InitiaterServer(jesseIP, port1);
	            is.initiate();

	        }
		}
	}

	@Override
	public void controllerUpdate(ControllerEvent arg0) {
		
	}
}
