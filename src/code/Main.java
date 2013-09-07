package code;

public class Main {

	public static void main(String[] args) {
		System.out.println("PairTunes Running...");
		GUIEventListener guiEventListener = new GUIEventListener(); //Create event listener
		Window mainWindow = new Window(guiEventListener); //Make window with the listener
		guiEventListener.setSources(mainWindow.getSources()); //Set the event listener's list of event sources from the window
	}
}