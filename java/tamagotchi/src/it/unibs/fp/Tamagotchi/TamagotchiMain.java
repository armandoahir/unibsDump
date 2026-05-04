package it.unibs.fp.Tamagotchi;
import it.unibs.fp.mylib.InputDati;
/**
 * TAMAGOTCHI, un animale domestico virtuale, del quale bisogna prendersi cura, nutrendolo e coccolandolo.
 * 
 * @author Ahir Armando
 */

public class TamagotchiMain {
	private static final String WELCOME = "WELCOME! I am your tamagotchi !! \n Give me a name\n --> ";
	private static final String INITIAL_FEED = "Select my initial feed status (30 to 70)\n -->";
	private static final String INITIAL_MOOD = "Select my initial mood (30 to 70) \n -->";
	private static final String CHOICE = "TAKE CARE OF ME \n press:\n\t(1) GIVE COOKIES\n\t(2) PAT PAT\n\t(3) EXIT";
	private static final String FEED_ME = "How many cookies you want to give me?\n -->";
	private static final String CUDDLE_ME = "How many PAT PAT you want to give me?\n -->";
	private static final String FEELING = "I'M FEELING... "; 
	private static final String EXIT = ":( GOODBYE FRIEND :(";
	private static final int MIN_GIVE = 1;
	private static final int MAX_GIVE = 50;
	private static final int MIN = 30;
	private static final int MAX = 70;
	
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
	
	public static Tamagotchi newBorn(String _name, int _mood_status, int _feed_status) {
		return new Tamagotchi(_name, _mood_status, _feed_status);
	}

	public static void takeCare (int _choice, Tamagotchi _tamagotchi) {
		
		switch(_choice) {
		
		case 1: int cookies = InputDati.leggiIntero(FEED_ME, MIN_GIVE, MAX_GIVE);
		_tamagotchi.riceviBiscotti(cookies);
		System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
			break;
			
		case 2: int cuddles = InputDati.leggiIntero(CUDDLE_ME, MIN_GIVE, MAX_GIVE); 
		_tamagotchi.riceviCarezze(cuddles);
		System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
			break;
			
		default: System.out.println(_tamagotchi.toString());
		System.out.println(FEELING + _tamagotchi.decideMood());
		System.out.println(EXIT);
			break;	
		}	
	}
}
