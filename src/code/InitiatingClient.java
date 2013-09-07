package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.PrefetchCompleteEvent;

// This is the song receiver
// THIS GOES SECOND

public class InitiatingClient implements ControllerListener {
	
	PrintWriter out;
	BufferedReader in;
	String srcIP;
	int port;
	long offsetTotal;
	RTPClient rtpc;
	
	public InitiatingClient(String srcIP, int port) {
		this.srcIP = srcIP;
		this.port = port;
	}
	
	public void initiate() {
		ServerSocket server;
		Socket client;
		try {
			System.out.println("Initiating Server Socket...");
			server = new ServerSocket(port);
			client = server.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			System.out.println("Socket Established.");
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		long timeStamp = new Date().getTime();
		out.println("sync");
		try {
			in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long networkDelay = new Date().getTime() - timeStamp;
		
		rtpc = new RTPClient(srcIP, this);
		Thread t = new Thread(rtpc);
		t.start();
		try {
			t.sleep(networkDelay * 8/9);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
	public synchronized void controllerUpdate(ControllerEvent evt) {
		if (evt instanceof EndOfMediaEvent) {
			System.exit(0);
		} else if (evt instanceof PrefetchCompleteEvent) {		
			System.out.println("Sync complete. Ready to recieve transmisison.");
			out.println("gogo");
			rtpc.p.start();
		}
	}
}
