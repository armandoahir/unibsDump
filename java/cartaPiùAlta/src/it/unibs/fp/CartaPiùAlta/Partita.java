package it.unibs.fp.CartaPiùAlta;
import it.unibs.fp.mylib.InputDati;
import java.lang.*;

public class Partita {
	
	private static final String SCONFITTA = " RITENTA..il banco ha vinto.";
	private static final String VITTORIA = " HAI VINTO!! Il banco ha perso.";
	private static final String PATTA = "La partita è PATTA";
	private static final String PUNTARE = "USCIRE dal gioco? ";
	private static final String SCEGLI_MAZZO = "Scegli con che mazzo vuoi giocare: \n\t1. Mazzo italiano \n\t2. Mazzo Francese";
	
	/**
	 * Istanzia un mazzo francese
	 * @return MazzoFrancese istanziato
	 */

	public static MazzoFrancese creaMazzoFrancese() {
		String[] nomiCarte = {
				"ASSO",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"10",
				"JACK",
				"REGINA",
				"RE"
		};
		
		int i, k, val;
		SemeFrancese[] semeFrancese = SemeFrancese.values();
		Carta[] listaCarte = new Carta[nomiCarte.length * semeFrancese.length];
		
		for(k = 0; k < semeFrancese.length; k++) {
			val = 1;
			
			for (i = (k * nomiCarte.length); i < (nomiCarte.length * (k + 1)); i++) {
				listaCarte[i] = new Carta(nomiCarte [i % nomiCarte.length], val, semeFrancese[k].toString());
				val++;
			}
		}
		
		return new MazzoFrancese(listaCarte);
	}
	
	/**
	 * Istanzia un mazzo italiano
	 * @return MazzoItaliano istanziato
	 */
	
	public static MazzoItaliano creaMazzoItaliano() {
		String[] nomiCarte = {
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"Fante",
				"Cavallo",
				"Re",
				"Asso"
		};
		
		int i,k,val;
		SemeItaliano[] semeItaliano = SemeItaliano.values();
		Carta[] listaCarte = new Carta[nomiCarte.length * semeItaliano.length];
		
		for(k = 0; k < semeItaliano.length; k++) {
			val = 2;
			
			for (i = (k * nomiCarte.length); i < (nomiCarte.length * (k + 1)); i++) {
				listaCarte[i] = new Carta(nomiCarte [i % nomiCarte.length], val, semeItaliano[k].toString());
				val++;
			}
		}
		
		return new MazzoItaliano(listaCarte);
	}
	
	/**
	 * Il gioco inizia col chiedere al giocatore quale mazzo usare durante la partita
	 * @return Mazzo: due scelte --> mazzo francese o italiano
	 */
	
	
	public static Mazzo scegliMazzo() {
		int scelta = InputDati.leggiIntero(SCEGLI_MAZZO, 1, 2);
		
		if(scelta == 1) 
			return creaMazzoItaliano();
		else 
			return creaMazzoFrancese();
	}
	
	
	public static void round(Mazzo mazzo, Utente utente) {
		
		Carta cartaUtente = mazzo.estraiCarta();
		
		System.out.println(cartaUtente.toString());
		
		int puntata = utente.puntata();
		Carta cartaComputer;
		
		do {
			cartaComputer = mazzo.estraiCarta();
		}while(cartaUtente.equals(cartaComputer));
		
		System.out.println(cartaComputer.toString());
		
		if(vincitore(cartaUtente,cartaComputer)) {
			utente.setCapitale(utente.getCapitale() + puntata);
			System.out.println("\n" + VITTORIA + utente.toString());
		}
		else 
			if(cartaUtente.getValore() == cartaComputer.getValore()) {
				System.out.println(PATTA);
			}
			else {
				utente.setCapitale(utente.getCapitale() - puntata);
				System.out.println("\n" + SCONFITTA + utente.toString());
			}
	}
	
	public static boolean terminaPartita(Utente _utente) {
		if(_utente.getCapitale() <= 0 || InputDati.yesOrNo(PUNTARE))
			return true;
		else 
			return false;
	}
	
	public static boolean vincitore(Carta _cartaUtente, Carta _cartaComputer) {
		if( _cartaUtente.getValore() > _cartaComputer.getValore())
			return true;
		else return false;
	}
}
