package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

// THIS GOES FIRST
// It initiates the socket AND the music
// It sends the music

public class InitiaterClient {
	public static void startComm() {
		Socket client;
		try {
            client = new Socket(InetAddress.getByName("172.16.200.239"), 42050);
            System.out.println("GGG");
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out.println("gonow");
            RTPServer.entry("172.16.200.239", "file:///C:\\Users\\JESSE\\Desktop\\Developer\\GitHub\\PairTunes\\src\\res\\17 Jeremy Soule - Secunda.wav");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
