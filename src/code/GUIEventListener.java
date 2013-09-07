package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class GUIEventListener implements ActionListener {

	Map<String, Object> eventSources;
	
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
	            ((JTextField) (eventSources.get("displaySong"))).setText(fileChooser.getSelectedFile().toString());
	        }
		}
	}
}
