package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	Map<String, Object> eventSources;
	JButton importButton, streamButton, rcvButton, playPauseButton, stopButton, backButton, forwardButton;
	JTextField displaySong, friendIPField;
	JLabel myIPLabel, friendIPLabel, songsLabel, queueLabel;
	DefaultListModel<SongTuple<String, String>> songListModel, queueModel;
	JList<SongTuple<String, String>> songList, queueList;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import Folder");
		displaySong = new JTextField();
		playPauseButton = new JButton("Play/Pause");
		stopButton = new JButton("Stop");
		backButton = new JButton("Back");
		forwardButton = new JButton("Forward");
		
		//Middle scrolling panel stuff
		songsLabel = new JLabel("Songs:");
		songListModel = new DefaultListModel<SongTuple<String, String>>();
		songListModel.addElement(new SongTuple<String, String>("<Add some songs!>", "N/A"));
		songList = new JList<SongTuple<String, String>>(songListModel);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		queueLabel = new JLabel("Queue:");
		queueModel = new DefaultListModel<SongTuple<String, String>>();
		queueList = new JList<SongTuple<String, String>>(queueModel);
		queueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//IP stuff
		friendIPField = new JTextField(10);
		streamButton = new JButton("Stream to IP Addresses...");
		rcvButton = new JButton("Receive from IP Addresses...");
		String ip = "Error";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		myIPLabel = new JLabel("My IP Address: " + ip);
		
		//Create JPanels
		setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		JPanel topPanel = new JPanel();
		//topPanel.setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		
		//Top Left Panel
		JPanel topLeft = new JPanel();
		topLeft.add(myIPLabel);
		topLeft.add(streamButton);
		topLeft.add(rcvButton);
		topPanel.add(topLeft);
		
		//Top Right Panel
		JPanel topRight = new JPanel();
		topPanel.add(topRight, BorderLayout.EAST);
		topRight.add(playPauseButton);
		topRight.add(stopButton);
		topRight.add(backButton);
		topRight.add(forwardButton);
		
		//Create a split pane with the two scroll panes in it. Create the column headers.
		JScrollPane songListPane = new JScrollPane(songList);
		songListPane.setColumnHeaderView(songsLabel);
		JScrollPane queueListPane = new JScrollPane(queueList);
		queueListPane.setColumnHeaderView(queueLabel);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
											  songListPane, 
											  queueListPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(380);

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		songListPane.setMinimumSize(minimumSize);
		queueListPane.setMinimumSize(minimumSize);
		add(splitPane, BorderLayout.CENTER);
		
		topPanel.add(myIPLabel);
		topPanel.add(streamButton);
		topPanel.add(rcvButton);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(displaySong, BorderLayout.CENTER);
		bottomPanel.add(importButton, BorderLayout.EAST);
		setSize(1200,500);
		setVisible(true);
		setTitle("PairTunes");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set event listener
		eventListener = listener;
		
		//Register event listeners, add components to list of event sources to be passed to GUIEventListener
		rcvButton.addActionListener(eventListener);
		streamButton.addActionListener(eventListener);
		importButton.addActionListener(eventListener);
		displaySong.addActionListener(eventListener);
		songList.addMouseListener(eventListener);
		queueList.addMouseListener(eventListener);
		eventSources = new HashMap<String, Object>();
		eventSources.put("importButton", importButton);
		eventSources.put("displaySong", displaySong);
	}
	
	public Map<String, Object> getSources() {
		return eventSources;
	}
}
