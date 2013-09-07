package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.GregorianCalendar;

// THIS GOES FIRST
// It initiates the socket AND the music
// It sends the music

public class InitiatingServer{
	
	String destinationIP;
	int port;
	long offsetTotal, offset;
	String songAddress = "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\Somewhere I Belong.wav";
	PrintWriter out;
	BufferedReader in;
	RTPServer rtps;
	
	public InitiatingServer(String destinationIP, int port) {
		this.destinationIP = destinationIP;
		this.port = port;
	}
	
	public void initiate() {
		Socket client;
		try {
			System.out.println("Initiating Client Socket...");
			client = new Socket(InetAddress.getByName(destinationIP), port);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			if (!in.readLine().equals("Start Sync")) {
				System.out.println("Bad Server... Exiting...");
				System.exit(1);
			}
			out.println("ok");
			
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		System.out.println("Sync Complete.\nStarting Transmission...");
		rtps = new RTPServer(destinationIP, songAddress, port);
		Thread t = new Thread(rtps);
		t.start();
		rtps.play();
		System.out.println("Playing...");
	}
}
