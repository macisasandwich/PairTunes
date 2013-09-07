package code;

public class Main {

	public static void main(String[] args) {
		System.out.println("PairTunes Running...");
		GUIEventListener guiEventListener = new GUIEventListener(); //Create event listener
		Window mainWindow = new Window(guiEventListener); //Make window with the listener
		guiEventListener.setWindow(mainWindow); //Pass a reference of the window for guiEventListener to use
	}
}
