package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GUIEventListener implements ActionListener {

	Map<String, Object> eventSources;
	
	public void setSources(Map<String, Object> eventSources) {
		this.eventSources = eventSources;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == eventSources.get("loadButton")) {
			System.out.println("yeah we got dis shiz");
		}
	}

}
