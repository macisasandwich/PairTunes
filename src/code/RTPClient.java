package code;

import javax.media.*;
import javax.media.format.AudioFormat;

public class RTPClient implements Runnable {

	Player p;
	MediaLocator src;
	ControllerListener mama;
	int port;
	
	/*public static void entry(String srcIP, long offset, RTPServer r) {
		RTPClient rtp = new RTPClient(srcIP);
		Thread t = new Thread(rtp);
		t.start();
	}*/

	public RTPClient(String srcIP, ControllerListener mom, int port) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
		        "com.sun.media.codec.audio.mp3.JavaDecoder",
		        new Format[]{input1, input2},
		        new Format[]{output},
		        PlugInManager.CODEC);
		this.mama = mom;
		this.port = port;
		
		//TODO not the port!
		String srcUrl = "rtp://" + srcIP + ":" + Integer.toString(port) + "/audio/1";
		DataSink sink;
		src = new MediaLocator(srcUrl);
	}

	public void run() {
		try {
			p = Manager.createPlayer(src);
			p.addControllerListener(mama);
			p.prefetch();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
