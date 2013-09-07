package code;

import java.net.InetAddress;
import javax.media.rtp.*;
import javax.media.rtp.rtcp.*;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.control.*;
import javax.media.format.AudioFormat;

public class RTPServer implements ControllerListener, Runnable {
	private boolean realized = false;
	private boolean configured = false;
	private String ipAddress;
	private long offset;
	private int port;
	Processor p;
	MediaLocator src;
	
	public static void entry(String destIP, String songAddress, long offset, int port) {
		RTPServer rtp = new RTPServer(destIP, songAddress, offset, port);
		Thread t = new Thread(rtp);
		t.start();
	}

	public RTPServer(String destIP, String song, long offset, int port) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
		        "com.sun.media.codec.audio.mp3.JavaDecoder",
		        new Format[]{input1, input2},
		        new Format[]{output},
		        PlugInManager.CODEC);
		ipAddress = destIP;
		this.offset = offset;
		this.port = port;
		//TODO dynamic paths
		//String srcFile = song;
		// \\\ is absolute reference ~\ is relaive reference
		src = new MediaLocator(song);

	}

	private void setTrackFormat(Processor p) {
		// Get the tracks from the processor
		TrackControl[] tracks = p.getTrackControls();
		// Do we have atleast one track?
		if (tracks == null || tracks.length < 1) {
			System.out.println("Couldn't find tracks in processor");
			System.exit(1);
		}

		// Set the output content descriptor to RAW_RTP
		// This will limit the supported formats reported from
		// Track.getSupportedFormats to only valid RTP formats.
		ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
		p.setContentDescriptor(cd);

		Format supported[];
		Format chosen;
		boolean atLeastOneTrack = false;

		// Program the tracks.
		for (int i = 0; i < tracks.length; i++) {
			Format format = tracks[i].getFormat();
			//TODO wat
			System.out.println("Trenutni format je " + format.getEncoding());
			if (tracks[i].isEnabled()) {
				supported = tracks[i].getSupportedFormats();
				for (int n = 0; n < supported.length; n++)
					System.out.println("Supported format: " + supported[n]);

				// We've set the output content to the RAW_RTP.
				// So all the supported formats should work with RTP.
				// We'll just pick the first one.

				if (supported.length > 0) {
					chosen = supported[0]; // this is where I tried changing
											// formats
					tracks[i].setFormat(chosen);
					System.err.println("Track " + i
							+ " is set to transmit as: " + chosen);
					atLeastOneTrack = true;
				} else
					tracks[i].setEnabled(false);
			} else
				tracks[i].setEnabled(false);
		}
	}

	private void transmit(Processor p) {
		try {
			DataSource output = p.getDataOutput();
			PushBufferDataSource pbds = (PushBufferDataSource) output;
			RTPManager rtpMgr = RTPManager.newInstance();
			SessionAddress localAddr, destAddr;
			SendStream sendStream;
			//int port = 42050;
			SourceDescription srcDesList[];
			//Player pl = Manager.createPlayer(src);
			
			localAddr = new SessionAddress(InetAddress.getLocalHost(), port);
			InetAddress ipAddr = InetAddress.getByName(ipAddress);
			destAddr = new SessionAddress(ipAddr, port);
			rtpMgr.initialize(localAddr);
			rtpMgr.addTarget(destAddr);
			sendStream = rtpMgr.createSendStream(output, 0);
			sendStream.start();
			System.err
					.println("Created RTP session: " + ipAddress + " " + port);
			p.start();
			wait(offset);
			this.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		try {
			Player pl = Manager.createPlayer(src);
			pl.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void controllerUpdate(ControllerEvent evt) {
		if (evt instanceof RealizeCompleteEvent) {
			realized = true;
		} else if (evt instanceof ConfigureCompleteEvent) {
			configured = true;
		} else if (evt instanceof EndOfMediaEvent) {
			System.exit(0);
		} else {
			// System.out.println(evt.toString());
		}
	}

	public void run() {

		try {
			p = Manager.createProcessor(src);
			p.addControllerListener(this);
			p.configure();
			while (!configured) {
				try {
					Thread.currentThread().sleep(100L);
					;
				} catch (InterruptedException e) {
					// ignore
				}
			}

			setTrackFormat(p);
			p.setContentDescriptor(new ContentDescriptor(
					ContentDescriptor.RAW_RTP));

			p.realize();
			while (!realized) {
				try {
					Thread.currentThread().sleep(100L);
					;
				} catch (InterruptedException e) {
					// ignore
				}
			}
			transmit(p);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}