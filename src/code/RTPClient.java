package code;

import javax.media.*;
import javax.media.format.AudioFormat;

public class RTPClient implements ControllerListener, Runnable {

	Player p;
	MediaLocator src;
	long offset;
	
	public static void entry(String srcIP, long offset) {
		RTPClient rtp = new RTPClient(srcIP, offset);
		Thread t = new Thread(rtp);
		t.start();
	}

	public RTPClient(String srcIP, long offset) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
		        "com.sun.media.codec.audio.mp3.JavaDecoder",
		        new Format[]{input1, input2},
		        new Format[]{output},
		        PlugInManager.CODEC);
		
		this.offset = offset;
		
		//TODO not the port!
		String srcUrl = "rtp://" + srcIP + ":42050/audio/1";
		DataSink sink;
		src = new MediaLocator(srcUrl);
	}

	public void run() {
		try {
			p = Manager.createPlayer(src);
			p.addControllerListener(this);
			System.out.println(offset);
			//wait(offset);
			p.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public synchronized void controllerUpdate(ControllerEvent evt) {
		if (evt instanceof EndOfMediaEvent) {
			System.exit(0);
		} else {
			System.out.println(evt.toString());
		}
	}
}