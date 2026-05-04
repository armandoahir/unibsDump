package it.unibs.gestionalebaratto;

import it.unibs.gestionalebaratto.Pubblicazione.StatoPubblicazione;

public class Fruitore extends Utente {

	public Fruitore(String username, String password, boolean primoAccesso) {
		super(username, password, false);
	}
	
	public static boolean aggiungiUtenteMenu() {
		System.out.print("Username: ");
		String username = Tastiera.leggiStringa();
		System.out.print("Password: ");
		String password = Tastiera.leggiStringa();
		return Utente.aggiungiUtente(username, password, TipoUtente.FRUITORE);
	}
	
	public static boolean visualizzaProprieOfferte() {
		ElementoMenu menuOfferte = new ElementoMenu("Elenco offerte", Menu.getArr("fruitore"));
		int i = 1;
		for (Pubblicazione pubblicazione : Pubblicazione.pubblicazioni) {
			if(pubblicazione.getArticolo().getProprietario() == Utente.utenteAttivo) {
				menuOfferte.add(i++, pubblicazione.getArticolo().getDescrizione(), () -> pubblicazione.visualizzaMenuFruitore());
			}
		}
		
		Menu.vaiA(menuOfferte);
		return true;
	}
	
	public static boolean visualizzaAppuntamenti() {
		ElementoMenu menuAppuntamenti = new ElementoMenu("Elenco appuntamenti", Menu.getArr("fruitore"));
		int i = 1;
		for (Appuntamento appuntamento : Appuntamento.appuntamenti) {
			if((appuntamento.pubblicazione1.getArticolo().getProprietario() == Utente.utenteAttivo && appuntamento.pubblicazione1.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO)
					|| (appuntamento.pubblicazione2.getArticolo().getProprietario() == Utente.utenteAttivo && appuntamento.pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO)
					|| (appuntamento.pubblicazione2.getArticolo().getProprietario() == Utente.utenteAttivo && appuntamento.pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_SELEZIONATA)) {
				
				String testoAppuntamento = appuntamento.pubblicazione1.getArticolo().getDescrizione() + " - " + appuntamento.pubblicazione2.getArticolo().getDescrizione();
				menuAppuntamenti.add(i++, testoAppuntamento, () -> appuntamento.appuntamentoMenu());
			}
		}
		
		Menu.vaiA(menuAppuntamenti);
		return true;
	}
}
