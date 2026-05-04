package it.unibs.gestionalebaratto;

import java.io.IOException;
import java.util.Scanner;

public class Tastiera {

	public static Tastiera t; // Singleton, perchè la statica di Scanner non si può creare

	public Scanner s;

	public Tastiera() {
		s = new Scanner(System.in);
	}

	/**
	 * Esegue le operazioni di input specifiche dei menu.
	 *
	 * @return int opzione scelta del menu, -1 se indietro, -2 se uscita
	 */
	public int eseguiMenu() {
		return eseguiMenu(Integer.MAX_VALUE);
	}

	/**
	 * Esegue le operazioni di input specifiche dei menu.
	 *
	 * @param maxOpt numero massimo opzione
	 * @return int opzione scelta del menu, -1 se indietro, -2 se uscita
	 */
	public static int eseguiMenu(int maxOpt) {
		boolean errore = false;
		do {
			if (errore) {
				System.out.println(Costanti.OPZIONE_NON_VALIDA);
				System.out.print(Costanti.COMANDO_INSERIMENTO);
			}
			String in = t.s.nextLine();

			if (in.matches("^[0-9][0-9]*$")) { // Inserito un numero
				int opzione = Integer.parseInt(in);
				if (opzione >= 0 && opzione <= maxOpt) {
					return opzione;
				}
			} else if (in.matches("^[Bb]$")) { // Inserito spostamento indietro
				return -1;
			} else if (in.matches("^[Qq]$")) { // Yeet
				return -2;
			}
			errore = true;
		} while (true);
	}

	public static String leggiStringa() {
		return t.s.nextLine();
		
	}
	
	public static void pausa() {
		System.out.print("Premere invio per continuare...");
		try {
			while(System.in.read() != '\n');
		} catch (IOException e) {
		}
	}

	public static boolean leggiOpzione(String testo) {
		do {
			System.out.printf("%s [S/N]: ", testo);
			String in = t.s.nextLine();
			if (in.matches("^[Ss]$")) { // Inserito Si
				return true;
			} else if (in.matches("^[Nn]$")) { // Inserito No
				return false;
			}
			System.out.println(Costanti.OPZIONE_NON_VALIDA);
		} while(true);

	}
	
	public static String maiuscola(String str)
	{
	    if(str == null || str.length()<=1) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static void init() {
		Tastiera.t = new Tastiera();
	}

}
