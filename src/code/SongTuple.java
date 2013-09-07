package code;

public class SongTuple<A, B> {
	public final A songName;
	public final B filePath;
	
	public SongTuple(A songName, B filePath) {
		this.songName = songName;
		this.filePath = filePath;
	}
	
	public String toString() {
		return (String) this.songName;
	}
}