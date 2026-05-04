package it.unibs.gestionalebaratto;

public class Configuratore extends Utente{

	private static final long serialVersionUID = 3051909166928035987L;

	public Configuratore(String username, String password, boolean primoAccesso) {
		super(username,password,primoAccesso);
	}

	
	/**
	 * Aggiunge un utente (richiesta da menu).
	 *
	 * @return true se l'utente è stato creato, false se esiste già
	 */
	public static boolean aggiungiUtenteMenu() {
		System.out.print("Username: ");
		String username = Tastiera.leggiStringa();
		System.out.print("Password: ");
		String password = Tastiera.leggiStringa();
		return Utente.aggiungiUtente(username, password, TipoUtente.CONFIGURATORE);
		
	}
	
	public static boolean visualizzaOfferte() {
		ElementoMenu menuOfferte = new ElementoMenu("Elenco offerte", Menu.getArr("configuratore"));
		int i = 1;
		for (Pubblicazione pubblicazione : Pubblicazione.pubblicazioni) {
			menuOfferte.add(i++, pubblicazione.getArticolo().getDescrizione(), () -> pubblicazione.visualizzaMenuConfiguratore());
		}
		
		Menu.vaiA(menuOfferte);
		return true;
	}
	
	
}
