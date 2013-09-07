package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.PrefetchCompleteEvent;

import java.util.Date;

// This is the song receiver
// THIS GOES SECOND

public class InitiaterServer implements ControllerListener {
	
	PrintWriter out;
	BufferedReader in;
	String srcIP;
	int port;
	long offsetTotal;
	RTPClient rtpc;
	
	public InitiaterServer(String srcIP, int port) {
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
			
			out.println("Start Sync");
			
			System.out.println("Starting Sync...");
			offsetTotal = 0;
			//long timeDiff;			
			
			//for (int i = 0; i <= 3; i++) {
			//	timeDiff = new GregorianCalendar().getTimeInMillis() - Long.parseLong(in.readLine());
			//	out.println(timeDiff);
			//	offsetTotal += timeDiff;
			//	System.out.println(timeDiff);
			//}
			
			//offsetTotal /= 4;
			
			System.out.println("Sync value is: "+offsetTotal);
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		System.out.println("Sync complete. Ready to recieve transmisison.");
		
		rtpc = new RTPClient(srcIP, this);
		Thread t = new Thread(rtpc);
		t.start();
		rtpc.p.start();
	}
	
	public synchronized void controllerUpdate(ControllerEvent evt) {
		if (evt instanceof EndOfMediaEvent) {
			System.exit(0);
		} //else if (evt instanceof PrefetchCompleteEvent) {
			//out.println("Start Sync");
			//rtpc.p.start();
			//r.pl.start();
		//} else {
		//	System.out.println(evt.toString());
		//}
	}
	
	/*public static void startComm(RTPServer r) {
		ServerSocket server;
		Socket client;
		long offsetTotal = 0;
		try {
			server = new ServerSocket(42050);
			client = server.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			long timeDiff;			
			
			for (int i = 0; i <= 3; i++) {
				timeDiff = new GregorianCalendar().getTimeInMillis() - Long.parseLong(in.readLine());
				out.println(timeDiff);
				offsetTotal += timeDiff;
				System.out.println(timeDiff);
			}
			
			offsetTotal /= 4;

			System.out.println(offsetTotal);
			System.out.println("Handshake received from server. Socket established!");
			RTPClient.entry("172.16.150.122", offsetTotal, r);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}*/
}
