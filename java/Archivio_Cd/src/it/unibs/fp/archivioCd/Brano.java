package it.unibs.fp.archivioCd;

public class Brano {
	
	public static final String INSERT_TRACK_TITLE = "Track title:";
	public static final String INSERT_MINUTES = "Minutes: ";
	public static final String INSERT_SECONDS = "Seconds: ";
	public static final int MIN = 0;
	public static final int MAX = 59;
	private final String TRACK = "%s [%02d:%02d] ";
	
	private String title;
	private int[] duration;
	
	public Brano(String _title, int _min, int _sec) {
		
		this.title =_title;
		this.duration = new int[2];
		this.duration[0] = _min;
		this.duration[1] = _sec;
	}
	
	public String getTitolo() {
		return title;
	}

	public String toString() {
		return String.format(TRACK, this.title, this.duration[0], this.duration[1]);
	}
}
