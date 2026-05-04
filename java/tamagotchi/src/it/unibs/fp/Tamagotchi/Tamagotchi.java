package it.unibs.fp.Tamagotchi;

public class Tamagotchi {
	
	private String name;
	private int feed_status;
	private int mood_status;
	private static final int MIN_LIMIT = 30;
	private static final int MAX_LIMIT = 70;
	private static final double UPGRADE = 10 / 100;
	private static final double DOWNGRADE = 1 / 4;
	
	public Tamagotchi (String _name, int _feed_status, int _mood_status) {
		this.name = _name;
		this.feed_status =_feed_status;
		this.mood_status = _mood_status;
	}
	/**
	 * riceviBiscotti: metodo implementato usando la libreria Math, 
	 * con la quale c'è un incremento sulla sazietà pari al 10% dei biscotti presi in input.
	 * Per quanto riguarda il grado di affetto, sempre usando la libreria Math, si applica un decremento pari 
	 * ad un quarto dei biscotti ricevuti in input.
	 * Operazione analoga per il metodo riceviCarezze.
	 * @param cookies
	 */
	
	public void riceviBiscotti (int cookies) {
		
		this.feed_status = Math.min(100, feed_status *(int)(cookies * UPGRADE));
		this.mood_status = Math.max(0, mood_status - (int)(cookies * DOWNGRADE));
	}
	
	public void riceviCarezze (int cuddles) {
		this.mood_status = Math.min(100, mood_status *(int)(cuddles* UPGRADE));
		this.feed_status = Math.max(0, feed_status - (int)(cuddles * DOWNGRADE)) ;
	}
	
	/**
	 * Con l'utilizzo di un enum, questa porzione di codice calcola l'umore del tamagotchi,analizzandone i diversi casi,
	 * mentre porta alla morte dell'animale domestico nel caso il tamagotchi sia:
	 * - nutrito continuamente fino ad andare oltre al limite massimo di sazietà;
	 * - abbia grado di affetto oppure fame pari a 0 
	 * @return
	 */
	
	public Mood decideMood() {
		
		if (mood_status < MIN_LIMIT || feed_status < MIN_LIMIT)
			return Mood.SAD;
				
		else if (mood_status > MAX_LIMIT && feed_status > MAX_LIMIT) 
			return Mood.HAPPY;
		
		else if (mood_status > MAX_LIMIT && feed_status < MIN_LIMIT)
			return Mood.NORMAL;
		
		else if(mood_status == 0 || feed_status == 0 || feed_status >= 100) 
			return Mood.DEAD;
		
		else return Mood.NORMAL;	
	}
	
	public boolean sonoMorto() {
		if (decideMood() == Mood.DEAD)
			return true;
		
		return false;
	}
	
	public boolean sonoTriste() {
		if(decideMood() == Mood.SAD)
			return true;
		
		return false;
	}

	/**
	 * @return Getters and setters of private variables
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFeed_status() {
		return feed_status;
	}

	public void setFeed_status(int feed_status) {
		this.feed_status = feed_status;
	}

	public int getMood_status() {
		return mood_status;
	}

	public void setMood_status(int mood_status) {
		this.mood_status = mood_status;
	}

	@Override
	public String toString() {
		return "\n FEED STATUS = " + feed_status + "\n MOOD STATUS = " + mood_status;
	}
}
