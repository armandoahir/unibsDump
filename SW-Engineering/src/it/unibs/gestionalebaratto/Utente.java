package it.unibs.gestionalebaratto;

import java.io.Serializable;
import java.util.ArrayList;

public class Utente implements Serializable {
	
	private static final long serialVersionUID = -6813700453377021986L;
	private String username;
	private String password;
	private boolean primoAccesso;
	
	private static ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
	public static Utente utenteAttivo;
	public static TipoUtente tipoAttivo;
	
	
	/**
	 * Istanzia un nuovo utente.
	 *
	 * @param username username
	 * @param password password (temporanea se attivo il flag primoAccesso)
	 * @param primoAccesso primo accesso (se true richiede il cambio della password)
	 */
	public Utente(String username, String password, boolean primoAccesso) {
		this.username = username;
		this.password = password;
		this.primoAccesso = primoAccesso;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Verifica correttezza password.
	 *
	 * @param password password da verificare
	 * @return true se la password è corretta, false se errata
	 */
	public boolean verificaPassword(String password) {
		return this.password.equals(password);
	}
	
	/**
	 * Set della nuova password.
	 *
	 * @param password nuova password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Controlla se è primo accesso.
	 *
	 * @return true se è primo accesso, false altrimenti
	 */
	public boolean isPrimoAccesso() {
		return primoAccesso;
	}
	
	
	
	/**
	 * Ricerca e recupero dell'utente (previa verifica della password)
	 *
	 * @param username username dell'utente
	 * @param password password
	 * @return Utente richiesto, null se non esiste un utente con la combinazione username-password richiesta
	 */
	/*
	 * login
	 */
	public static Utente getUtente(String username, String password) {
		for(Utente u : listaUtenti) {
			if(u.getUsername().equals(username)) {
				if(u.verificaPassword(password)) {
					return u;
				}
				else{
					return null;
				}
			}
		}
		//se non trova utente 
		return null;
	}
	
	/**
	 * Modifica password.
	 *
	 * @return true se la password è stata modificata, false se la password inserita è uguale alla precedente
	 */
	public boolean modificaPassword() {	
		System.out.print("Nuova Password: ");
		String nuovaPassword = Tastiera.leggiStringa();
		
		if(!nuovaPassword.equals(this.password)) {
			this.setPassword(nuovaPassword);
			return true;
		}else {
			System.out.println("La nuova password non puo' essere uguale alla password iniziale");
			return false;
		}
	}
	

	/**
	 * Modifica username.
	 *
	 * @deprecated L'userame non è modificabile
	 */
	public boolean modificaUsername(String username) {		
		if(username != this.username) {
			for(Utente u : listaUtenti) {
				if(u.username == username)
					return false;
			}
			return true;
		}
		return false;
	}
	

	/**
	 * Aggiunge un nuovo utente.
	 *
	 * @param username username
	 * @param password password
	 * @param tipo tipologia di utente
	 * @return true se l'utente è stato creato, false se esiste già
	 */
	public static boolean aggiungiUtente(String username, String password, TipoUtente tipo) {
		for(Utente u : listaUtenti) {
			if(u.username == username)
				return false;
		}
		
		if(tipo == TipoUtente.CONFIGURATORE) {
			Utente nuovoUtente = new Configuratore(username, password, true);
			listaUtenti.add(nuovoUtente);
			return true;
		}else if(tipo == TipoUtente.FRUITORE) {
			Utente nuovoUtente = new Fruitore(username, password, false);
			listaUtenti.add(nuovoUtente);
			return true;
		}
		return false;
	}
	
	
	/**
	 * Aggiunge un utente (richiesta da menu).
	 *
	 * @return true se l'utente è stato creato, false se esiste già
	 */
	/*
	public static boolean aggiungiUtenteMenu() {
		System.out.print("Username: ");
		String username = Tastiera.leggiStringa();
		System.out.print("Password: ");
		String password = Tastiera.leggiStringa();
		return Utente.aggiungiUtente(username, password, TipoUtente.CONFIGURATORE);
	}
	*/
	
	
	/**
	 * Avvia flusso di accesso.
	 *
	 * @return true se l'accesso è avvenuto, false altrimenti
	 */
	public static boolean accedi() {
		
		System.out.print("Utente: ");
		String utente = Tastiera.leggiStringa();
		System.out.print("Password: ");
		String password = Tastiera.leggiStringa();
		
		//Recupero dell'utente (se esiste, altrimenti null)
		utenteAttivo = getUtente(utente, password);
		//se le credenziali sono corrette
		if(utenteAttivo != null) {
			//se primo acesso deve cambiare credenziali
			if(utenteAttivo.isPrimoAccesso()) {
				
				if(utenteAttivo.modificaPassword()){
					utenteAttivo.primoAccesso = false;
					return true;
				}else return false;
				
			}else{
				//se non � il primo accesso e le credenziali sono corrette	
				return true;
				}
			}
		//Se non ho trovato l'utente interrompo la funzione con un errore
		System.out.println("Username o password non validi!");
		Tastiera.pausa();
		return false;
	}
	
	/**
	 * Avvia flusso di accesso e rimanda al menu configuratore.
	 *
	 * @return true se l'accesso è avvenuto, false altrimenti
	 */
	public static boolean accediMenu() {
		if(Utente.accedi()) {
			if(utenteAttivo.getClass() == Configuratore.class) {
				tipoAttivo = TipoUtente.CONFIGURATORE;
				Menu.vaiA("configuratore");
				return true;
			}else if(utenteAttivo.getClass() == Fruitore.class) {
				tipoAttivo = TipoUtente.FRUITORE;
				Menu.vaiA("fruitore");
			return true;
			}
		}
		return false;
	}
	
	/**
	 * Reset.
	 * 
	 * @deprecated Non utilizzata
	 */
	public static boolean reset() {
		Utente.listaUtenti.clear();
		
		return true;
	}
	
	/**
	 * Carica gli utenti dalla memoria.
	 *
	 * @param memoria lista di utenti da meoria
	 */
	public static void caricaMemoria(ArrayList<Utente> memoria) {
		Utente.listaUtenti = memoria;
	}
}
