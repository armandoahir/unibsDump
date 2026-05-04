package it.unibs.fp.PolveriSottivi;
import java.util.ArrayList;

/**
 * Programma che raccoglie, analizza i dati settimanali sulle polveri sottili. 
 * @author Ahir Armando
 */

import it.unibs.fp.mylib.*;

public class PolveriSottiliMain {
	
	private static final String WELCOME = "Benvenuto nella registrazione dati delle polveri sottili\n";
	private static final String SOLAR_YEAR = "Anno solare: ";
	private static final String MAX_VALUE = "ATTENZIONE! Un valore che è stato registrato in questa settimana ha superato la soglia del valore massimo\n";
	private static final String AVERAGE_VALUE = "ATTENZIONE! Il valore medio dei dati registrati in questa settimana supera la soglia\n";
	private static final String WHICH_WEEK = "Inserisci la settimana da registrare (da 1 a 53)";
	private static final String EXIT = "ARRIVEDERCI!";
	private static final String CONTINUE = "Vuoi continuare a registrare?";
	private static final String VALUE = "value in μg/m3 --> ";
	private static final String SUMMARY = "WEEK REVIEW: ";
	
	private static final int WEEKDAYS = 7;
	private static final int MIN_YEAR = 1990;
	
	public static void main(String[] args) {
		
		System.out.println(WELCOME);
		do 
		{
			Week newWeek = createWeek();
			
			System.out.println(SUMMARY);
			System.out.println(newWeek.toString());
			
		if(newWeek.averageWarning() == true)
		System.out.println(AVERAGE_VALUE);	
		
		if(newWeek.maxWarning() == true)
			System.out.println(MAX_VALUE);
			
		}while(InputDati.yesOrNo(CONTINUE));
		
		System.out.println(EXIT);
	}

	/**
	 * Creazione di un oggetto di tipo Week, il quale inizializza una settimana da analizzare.
	 * Viene richiesto in input all'utente di riempire un ArrayList, che rappresenta una settimana, 
	 * con le eventuali dimensioni delle polveri sottili giornagliere.
	 * @return Week
	 */
	
	private static Week createWeek() {
		
		int week = InputDati.leggiIntero(WHICH_WEEK, 1, 53);
		int year = InputDati.leggiInteroConMinimo(SOLAR_YEAR, MIN_YEAR);
		ArrayList <Double> weeklyvalues = new ArrayList <Double>();
		
		for(int i = 0; i < WEEKDAYS; i++) {
			System.out.printf("Day " + (i+1) + ", ");
			weeklyvalues.add(InputDati.leggiDouble(VALUE));
		}
		
		return new Week(weeklyvalues, week, year);	
	}
}
