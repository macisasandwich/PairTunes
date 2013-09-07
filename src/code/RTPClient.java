package code;

import javax.media.*;
import javax.media.format.AudioFormat;

public class RTPClient implements ControllerListener, Runnable {

	Player p;
	MediaLocator src;
	long offset;
	RTPServer r;
	
	public static void entry(String srcIP, long offset, RTPServer r) {
		RTPClient rtp = new RTPClient(srcIP, offset, r);
		Thread t = new Thread(rtp);
		t.start();
	}

	public RTPClient(String srcIP, long offset, RTPServer r) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
		        "com.sun.media.codec.audio.mp3.JavaDecoder",
		        new Format[]{input1, input2},
		        new Format[]{output},
		        PlugInManager.CODEC);
		
		this.offset = offset;
		this.r = r;
		
		//TODO not the port!
		String srcUrl = "rtp://" + srcIP + ":42050/audio/1";
		DataSink sink;
		src = new MediaLocator(srcUrl);
	}

	public void run() {
		try {
			p = Manager.createPlayer(src);
			p.addControllerListener(this);
			p.prefetch();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public synchronized void controllerUpdate(ControllerEvent evt) {
		if (evt instanceof EndOfMediaEvent) {
			System.exit(0);
		} else if (evt instanceof PrefetchCompleteEvent) {
			p.start();
			r.pl.start();
		} else {
			System.out.println(evt.toString());
		}
	}
}