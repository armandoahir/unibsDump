package it.unibs.fp.TamaBase;

public class Tamagotchi {
	
	private String name;
	private int feed_status;
	private int mood_status;
	private static final int MIN_LIMIT = 30;
	private static final int MAX_LIMIT = 70;
	private static final int UPGRADE = 2;
	private static final int DOWNGRADE = 10;
	
	public Tamagotchi (String _name, int _feed_status, int _mood_status) {
		this.name = _name;
		this.feed_status =_feed_status;
		this.mood_status = _mood_status;
	}
	
	public void feedTamagotchi (int cookies) {
		this.feed_status += cookies * UPGRADE;
		this.mood_status -= DOWNGRADE;
	}
	
	public void toCuddle (int cuddles) {
		this.mood_status += cuddles * UPGRADE;
		this.feed_status -= DOWNGRADE;
	}
	
	public Mood decideMood() {
		if (mood_status <= MIN_LIMIT || feed_status <= MIN_LIMIT)
			return Mood.SAD;
		else if (mood_status >= MAX_LIMIT && feed_status >= MAX_LIMIT) 
			return Mood.SUPERHAPPY;
		else if (mood_status >= MAX_LIMIT && feed_status <= MIN_LIMIT)
			return Mood.SOHUNGRY;
		else if (mood_status <= MAX_LIMIT && feed_status >= MAX_LIMIT)
			return Mood.FULL;
		else return Mood.NORMAL;
		
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
