package code;

public class SongTuple<A, B, C, D> {
	public final A songName;
	public final B filePath;
	public final C myIP;
	public final D myPort;
	
	public SongTuple(A songName, B filePath, C myIP, D myPort) {
		this.songName = songName;
		this.filePath = filePath;
		this.myIP = myIP;
		this.myPort = myPort;
	}
	
	public String toString() {
		return (String) this.songName;
	}
}