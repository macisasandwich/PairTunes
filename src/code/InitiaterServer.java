package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// This is the song receiver
// THIS GOES SECOND

public class InitiaterServer {
	public static void startComm() {
		ServerSocket server;
		Socket client;
		try {
			server = new ServerSocket(42050);
			client = server.accept();
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			in.readLine();
			System.out.println("WON");
			RTPClient.entry("172.16.150.122");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
