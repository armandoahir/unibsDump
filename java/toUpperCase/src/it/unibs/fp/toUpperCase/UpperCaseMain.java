package it.unibs.fp.toUpperCase;
import java.util.*;

public class UpperCaseMain {
	private static final String TESTO = "Scrivi una frase:";
	public static String parola;

	public static void main(String[] args) {
		
		Scanner tastiera = new Scanner(System.in);
		System.out.println(TESTO);
		parola = tastiera.nextLine(); //try next
		tastiera.close();
		System.out.println(parola.toUpperCase());
		System.out.println(parola.codePointAt(3));

	}

}
