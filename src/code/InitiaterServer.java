package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class InitiaterServer {
	public static void startComm() {
		ServerSocket server;
		Socket client;
		try {
			server = new ServerSocket(42050);
			client = server.accept();
//			/sdlfjaksdf
			//PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			in.readLine();
			System.out.println("WON");
			RTPClient.entry("172.16.138.68");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}