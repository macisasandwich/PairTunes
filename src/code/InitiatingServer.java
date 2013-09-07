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
	long offsetTotal = 0, offset;
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
			
			/*if (!in.readLine().equals("Start Sync")) {
				System.out.println("Bad Server... Exiting...");
				System.exit(1);
			}*/
			
			System.out.println("Starting Sync...");
			offsetTotal = 0;
            
            //for (int i = 0; i <= 3; i++) {
            //	out.println(new GregorianCalendar().getTimeInMillis());
            //	offset = Long.parseLong(in.readLine());
            //	System.out.println(offset);
            //	offsetTotal += offset;
            //}
            
            //offsetTotal /= 4;  
            
            System.out.println("sync Value is: "+offsetTotal);
            
		} catch (Exception x) {
			x.printStackTrace();
		}
		
		System.out.println("Sync Complete.\nStarting Transmission...");
		rtps = new RTPServer(destinationIP, songAddress, offsetTotal, port);
		Thread t = new Thread(rtps);
		t.start();
		
		try {
			if (in.readLine().equals("Start Sync")) {
				rtps.play();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//rtps.play();
		System.out.println("Playing...");
	}
	
	
	//Stephen's version
/*	public static void startComm() {
		Socket client;
		try {
            client = new Socket(InetAddress.getByName("172.16.200.239"), 42050);
            System.out.println("GGG");
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("gonow");
            RTPServer.entry("172.16.200.239", "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}*/	
	
	
	/*public static void startComm() {
		Socket client;
		try {
            client = new Socket(InetAddress.getByName("172.16.200.239"), 42050);
            System.out.println("Intiating socket!");
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            long offsetTotal = 0;
            
            for (int i = 0; i <= 3; i++) {
            	out.println(new GregorianCalendar().getTimeInMillis());
            	offsetTotal += Long.parseLong(in.readLine());
            }
            
            offsetTotal /= 3;
            
            RTPServer.entry("172.16.200.239", "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav");
            wait(offsetTotal);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}*/
}
