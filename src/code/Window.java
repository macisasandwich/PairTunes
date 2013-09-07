package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	Map<String, Object> eventSources;
	JButton importButton;
	JTextField displaySong, friendIPField;
	JLabel myIPLabel, friendIPLabel, songsLabel, queueLabel;
	DefaultListModel<String> songListModel, queueModel;
	JList<String> songList, queueList;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import Folder");
		displaySong = new JTextField();
		songsLabel = new JLabel("Songs:");
		songListModel = new DefaultListModel<String>();
		songListModel.addElement("<Add some songs!>");
		songList = new JList<String>(songListModel);
		songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		queueLabel = new JLabel("Queue:");
		queueModel = new DefaultListModel<String>();
		queueList = new JList<String>(queueModel);
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
		eventSources = new HashMap<String, Object>();
		eventSources.put("loadButton", importButton);
		eventSources.put("displaySong", displaySong);
		
		//Set mouse listener for songList
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	Rectangle r = songList.getCellBounds(0, songList.getLastVisibleIndex());
		    	if (r != null && r.contains(e.getPoint()) && e.getClickCount() == 2) {
		    		int index = songList.locationToIndex(e.getPoint());
		    		//edge case: we only add to queue if it's not the default non-song message
		    		if (!songListModel.getElementAt(index).equals("<Add some songs!>")) {
		    			queueModel.addElement(songListModel.getElementAt(index));
		    		}
		    	}
		    }
		};
		songList.addMouseListener(mouseListener);
		
		//Set mouse listener for queueList
		MouseListener queueMouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	Rectangle r = queueList.getCellBounds(0, queueList.getLastVisibleIndex());
		    	if (r != null && r.contains(e.getPoint()) && e.getClickCount() == 2) {
		    		int index = queueList.locationToIndex(e.getPoint());
		    		//TODO actually start broadcasting
	    			System.out.println("Playing: " + queueModel.getElementAt(index));
		    	}
		    }
		};
		queueList.addMouseListener(queueMouseListener);
		
		//TODO have the top of queue be playing
		//TODO song list and queue are synced between comps
	}
	
	public Map<String, Object> getSources() {
		return eventSources;
	}
}
