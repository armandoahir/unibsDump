package it.unibs.fp.cuoriSolitari;
import it.unibs.fp.mylib.*;
/**
 * @author Ahir Armando
 */

public class CuoriSolitariMain {
	private final static String WELCOME = "Buongiorno e benvenuto nel programma CUORI SOLITARI";
	private static final int ETA_MINIMA = 18;
	private static final int ETA_MASSIMA = 99;
	private static final int MALE = 0;
	private static final int FEMALE = 1;
	private static final String CONGRATULATIONS = "Congratulazioni, sei stato accoppiato!";
	
	public static Persona creaPersona(String messaggio) {
		String user = InputDati.leggiStringaNonVuota(messaggio + "Nickname: ");
		int eta = InputDati.leggiIntero(messaggio + "Quanti anni hai: ");
		int sesso = InputDati.leggiIntero(messaggio + "Sesso (premere 0 se maschio, 1 se femmina: ", 0, 1);
		int orientamento = InputDati.leggiIntero(messaggio + "Dimmi se cerchi un uomo(premi 0), se cerchi una donna (premi 1)", 0, 1);
		return new Persona(user, sesso, eta, orientamento);
	}
	
	public static void main(String[] args) {
		System.out.println(WELCOME);
		int number = InputDati.leggiInteroConMinimo("numero di contendenti: ", 2);
		Persona[] persone = new Persona[number];
		int i,k;
		
		for(i = 0; i < number; i++) {
			persone[i] = creaPersona("Persona " + (i + 1) + " ");
		}
		for( i = 0; i < number; i++)
			for ( k = i + 1; k < number; k++) {
				if (Coppia.affinity(persone[i], persone[k])) {
					System.out.println(CONGRATULATIONS +" " + persone[i].getUSERNAME() + " & " + persone[k].getUSERNAME());
				}else System.out.println("RITENTA!");
				
			}
	}
}
