package code;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	Map<String, Object> eventSources;
	JButton importButton;
	JTextField displaySong, friendIPField;
	JLabel myIPLabel, friendIPLabel;
	DefaultListModel<String> songListModel;
	JList<String> songList;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import Folder");
		displaySong = new JTextField();
		songListModel = new DefaultListModel<String>();
		songListModel.addElement("<Add some songs!>");
		songList = new JList<String>(songListModel);
		friendIPField = new JTextField(10);
		friendIPLabel = new JLabel("Friend IP Address:");
		String ip = "Error";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		myIPLabel = new JLabel("My IP Address: " + ip);
		
		setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		JPanel topRightPanel = new JPanel();
		
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(songList), BorderLayout.CENTER);
		
		topPanel.add(myIPLabel);
		topPanel.add(topRightPanel);
		topRightPanel.add(friendIPLabel);
		topRightPanel.add(friendIPField);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(displaySong, BorderLayout.CENTER);
		bottomPanel.add(importButton, BorderLayout.EAST);
		setSize(500,500);
		setVisible(true);
		setTitle("PairTunes");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set event listener
		eventListener = listener;
		
		//Register event listeners, add components to list of event sources to be passed to GUIEventListener
		importButton.addActionListener(eventListener);
		displaySong.addActionListener(eventListener);
		eventSources = new HashMap<String, Object>();
		eventSources.put("loadButton", importButton);
		eventSources.put("displaySong", displaySong);
		
		//Set mouse listener
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	Rectangle r = songList.getCellBounds(0, songList.getLastVisibleIndex());
		    	if (r != null && r.contains(e.getPoint()) && e.getClickCount() == 2) {
		    		int index = songList.locationToIndex(e.getPoint());
		    		if (!songListModel.getElementAt(index).equals("<Add some songs!>")) {
		    			//edge case: we only do something if it's not the default non-song message
		    		}
		    		//TODO stop printing to console and actually do something with the selected song
		    		System.out.println("Double clicked on Item " + (index+1) + ", " + songListModel.getElementAt(index));
		    	}
		    }
		};
		songList.addMouseListener(mouseListener);
	}
	
	public Map<String, Object> getSources() {
		return eventSources;
	}
}
