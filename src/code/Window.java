package code;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	Map<String, Object> eventSources;
	JButton importButton;
	JTextField displaySong;
	GUIEventListener eventListener;
	
	public Window(GUIEventListener listener) {
		
		//Set up GUI components
		importButton = new JButton("Import...");
		displaySong = new JTextField();
		setLayout(new BorderLayout());
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(displaySong, BorderLayout.CENTER);
		bottomPanel.add(importButton, BorderLayout.EAST);
		setSize(500,500);
		setVisible(true);
		setTitle("PairTunes");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
