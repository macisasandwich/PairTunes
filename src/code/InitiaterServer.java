package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class InitiaterServer {
	public static void startComm() {
		ServerSocket server;
		Socket client;
		try {
			server = new ServerSocket(42050);
			client = server.accept();
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.print("go");
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
