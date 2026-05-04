package it.unibs.fp.TamaBase;

import it.unibs.fp.mylib.InputDati;

public class TamaBaseMain {
	private static final String WELCOME = "Hi, WELCOME! Give me a name: ";
	private static final String INITIAL_FEED = "Select my initial feed status: (1 to 100)";
	private static final String INITIAL_MOOD = "Select my initial mood: (1 to 100)";
	private static final String CHOICE = "Take care of me, press:\n\t1. GIVE COOKIES\n\t2. PAT PAT\n\t3. EXIT";
	private static final String FEED_ME = "How many cookies you want to give me?";
	private static final String CUDDLE_ME = "How many PAT PAT you want to give me?";
	private static final String FEELING = "I'm feeling "; 
	private static final String EXIT = ":( GOODBYE FRIEND :(";
	private static final int MIN_GIVE = 20;
	private static final int MAX_GIVE = 40;
	private static final int MIN = 1;
	private static final int MAX = 100;
	
	public static Tamagotchi newBorn(String _name, int _feed_status, int _mood_status) {
		return new Tamagotchi(_name, _feed_status, _mood_status);
	}

	public static void takeCare (int _choice, Tamagotchi _tamagotchi) {
		switch(_choice) {
		case 1: int cookies = InputDati.leggiIntero(FEED_ME, MIN_GIVE, MAX_GIVE);
		_tamagotchi.feedTamagotchi(cookies);
		System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
			break;
		case 2: int cuddles = InputDati.leggiIntero(CUDDLE_ME, MIN_GIVE, MAX_GIVE); 
		_tamagotchi.toCuddle(cuddles);
		System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
			break;
		default: System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
		System.out.println(EXIT);
			break;
		}
	}
	public static void main(String[] args) {
		String name = InputDati.leggiStringa(WELCOME);
		int feed_status = InputDati.leggiIntero(INITIAL_FEED, MIN, MAX);
		int mood_status = InputDati.leggiIntero(INITIAL_MOOD, MIN, MAX);
		Tamagotchi tamagotchi = newBorn(name, feed_status, mood_status);
		int choice;
		
		do {
			choice = InputDati.leggiIntero(CHOICE, 1, 3);
			takeCare(choice, tamagotchi);
		}while(choice != 3);
		
		System.out.println("THANKS FOR PLAYING");
	}
}
