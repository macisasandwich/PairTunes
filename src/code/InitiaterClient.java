package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.GregorianCalendar;

// THIS GOES FIRST
// It initiates the socket AND the music
// It sends the music

public class InitiaterClient implements Runnable {
	String destinationIP;
	int port;
	long offsetTotal;
	String songAddress = "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav";
	
	public static void initiate(String destinationIP, int port) {
		InitiaterClient ini = new InitiaterClient(destinationIP, port);
		Thread t = new Thread(ini);
		t.start();
	}
	
	public InitiaterClient(String destinationIP, int port) {
		this.destinationIP = destinationIP;
		this.port = port;
	}
	
	public void run() {
		this.setup();
		RTPServer.entry(destinationIP, songAddress, offsetTotal, port);
	}
	
	private void setup() {
		Socket client;
		try {
			client = new Socket(InetAddress.getByName(destinationIP), port);
			System.out.println("Initiating Socket!");
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			offsetTotal = 0;
            
            for (int i = 0; i <= 3; i++) {
            	out.println(new GregorianCalendar().getTimeInMillis());
            	offsetTotal += Long.parseLong(in.readLine());
            }
            
            offsetTotal /= 3;  
            
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	
	
	
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
