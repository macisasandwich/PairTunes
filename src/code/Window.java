package code;

import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	Map<String, Object> eventSources;
	JButton importButton;
	JTextField displaySong, friendIPField;
	JLabel myIPLabel, friendIPLabel;
	JList<String> songList;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import Folder");
		displaySong = new JTextField();
		songList = new JList<String>( new String[] {"<Add some songs!>"} );
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
		
		add(songList, BorderLayout.CENTER);
		
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
		
		songList.setListData(new String[] {"oogla boogla"});
		
		//Set event Listener
		eventListener = listener;
		
		//Register event listeners, add components to list of event sources to be passed to GUIEventListener
		importButton.addActionListener(eventListener);
		displaySong.addActionListener(eventListener);
		eventSources = new HashMap<String, Object>();
		eventSources.put("loadButton", importButton);
		eventSources.put("displaySong", displaySong);
	}
	
	public Map<String, Object> getSources() {
		return eventSources;
	}
}
