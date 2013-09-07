package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;

// This is the song receiver
// THIS GOES SECOND

public class InitiaterServer {
	public static void startComm(RTPServer r) {
		ServerSocket server;
		Socket client;
		long offsetTotal = 0;
		try {
			server = new ServerSocket(42050);
			client = server.accept();
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
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
	}
}
