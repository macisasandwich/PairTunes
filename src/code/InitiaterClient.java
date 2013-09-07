package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class InitiaterClient {
	public static void startComm() {
		Socket client;
		try {
            client = new Socket(InetAddress.getByName("172.16.150.122"), 42050);
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println(in.readLine());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
