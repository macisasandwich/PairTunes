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
	JButton importButton;
	JTextField displaySong, friendIPField;
	JLabel myIPLabel, friendIPLabel, songsLabel, queueLabel;
	DefaultListModel<SongTuple<String, String>> songListModel, queueModel;
	JList<SongTuple<String, String>> songList, queueList;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import Folder");
		displaySong = new JTextField();
		songsLabel = new JLabel("Songs:");
		songListModel = new DefaultListModel<SongTuple<String, String>>();
		songListModel.addElement(new SongTuple<String, String>("<Add some songs!>", "N/A"));
		songList = new JList<SongTuple<String, String>>(songListModel);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		queueLabel = new JLabel("Queue:");
		queueModel = new DefaultListModel<SongTuple<String, String>>();
		queueList = new JList<SongTuple<String, String>>(queueModel);
		queueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friendIPField = new JTextField(10);
		friendIPLabel = new JLabel("Stream to IP Address:");
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
		topPanel.add(topRightPanel);
		topRightPanel.add(friendIPLabel);
		topRightPanel.add(friendIPField);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(displaySong, BorderLayout.CENTER);
		bottomPanel.add(importButton, BorderLayout.EAST);
		setSize(800,500);
		setVisible(true);
		setTitle("PairTunes");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set event listener
		eventListener = listener;
		
		//Register event listeners, add components to list of event sources to be passed to GUIEventListener
		importButton.addActionListener(eventListener);
		displaySong.addActionListener(eventListener);
		songList.addMouseListener(eventListener);
		queueList.addMouseListener(eventListener);
		eventSources = new HashMap<String, Object>();
		eventSources.put("loadButton", importButton);
		eventSources.put("displaySong", displaySong);
	}
	
	public Map<String, Object> getSources() {
		return eventSources;
	}
}
