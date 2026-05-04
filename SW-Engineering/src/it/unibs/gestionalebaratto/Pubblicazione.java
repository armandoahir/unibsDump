package it.unibs.gestionalebaratto;

import java.io.Serializable;
import java.util.ArrayList;

public class Pubblicazione implements Serializable {
	private static final long serialVersionUID = -5242488085784412413L;

	enum StatoPubblicazione {
		//V3
		OFFERTA_APERTA,
		OFFERTA_RITIRATA,
		//V4
		OFFERTA_ACCOPPIATA,
		OFFERTA_SELEZIONATA,
		OFFERTA_IN_SCAMBIO,
		OFFERTA_CHIUSA;
	}
	
	private Articolo articolo;
	private ArrayList<StatoPubblicazione> storico;
	
	public static ArrayList<Pubblicazione> pubblicazioni;
	
	public Pubblicazione(Articolo articolo) {
		this.setArticolo(articolo);
		this.storico = new ArrayList<StatoPubblicazione>();
		this.storico.add(StatoPubblicazione.OFFERTA_APERTA);
	}
	

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public StatoPubblicazione getStato() {
		return storico.get(storico.size()-1);
	}
	
	public void setStato(StatoPubblicazione statoPubblicazione) {
		storico.add(statoPubblicazione);
	}
	
	private String getDescrizioneArticolo() {
		String testo = getArticolo().getDescrizione();
		
		if(getStato() == StatoPubblicazione.OFFERTA_RITIRATA) {
			testo += " (RITIRATA)";
		}
		
		return testo;
	}
	
	public boolean visualizzaMenuFruitore() {		
		ElementoMenu menuPubblicazione = new ElementoMenu("Visualizza pubblicazione", getDescrizioneArticolo(), getArticolo().getCategoria().toCategoriaFruitoreMenuOption());
		int i = 1;
			menuPubblicazione.add(i++, "Visualizza Campi Nativi", () -> getArticolo().visualizzaCampiNativi());
		if(getArticolo().getProprietario() == Utente.utenteAttivo && getStato() == StatoPubblicazione.OFFERTA_APERTA) {
			menuPubblicazione.add(i++, "Ritira l'offerta", () -> ritira());

		}
		if(getArticolo().getProprietario() != Utente.utenteAttivo && getStato() == StatoPubblicazione.OFFERTA_APERTA) {
			menuPubblicazione.add(i++, "Inserisci proposta di scambio", () -> cercaPerAccoppiamento());
		}
		
		Menu.vaiA(menuPubblicazione);
		
		return true;
	}
	
	public boolean visualizzaMenuConfiguratore() {		
		ElementoMenu menuPubblicazione = new ElementoMenu("Visualizza pubblicazione", getDescrizioneArticolo(), getArticolo().getCategoria().toCategoriaConfigMenuOption());
		
		int i = 1;
		menuPubblicazione.add(i++, "Visualizza autore", () -> getArticolo().visualizzaAutore());
		menuPubblicazione.add(i++, "Visualizza campi nativi", () -> getArticolo().visualizzaCampiNativi());
		
		Menu.vaiA(menuPubblicazione);
		
		return true;
	}
	
	private boolean ritira() {
		setStato(StatoPubblicazione.OFFERTA_RITIRATA);
		
		visualizzaMenuFruitore();
		
		return true;
	}
	
	public static boolean aggiungiPubblicazione(Articolo articolo) {
		pubblicazioni.add(new Pubblicazione(articolo));
		return true;
	}
	
	public static ArrayList<Pubblicazione> cercaPerCategoria(Categoria c) {
		ArrayList<Pubblicazione> filtrati = new ArrayList<Pubblicazione>();
		for (Pubblicazione pubblicazione : pubblicazioni) {
			if(pubblicazione.getArticolo().getCategoria() == c && pubblicazione.getStato() == StatoPubblicazione.OFFERTA_APERTA) {
				filtrati.add(pubblicazione);
			}
		}
		
		return filtrati;
	}
	
	public boolean cercaPerAccoppiamento() {
		ElementoMenu menuOfferte = new ElementoMenu("Elenco offerte scambiabili", ElementoMenu.attivo);
		int i = 1;
		for (Pubblicazione pubblicazione : Pubblicazione.pubblicazioni) {
			if(pubblicazione.getArticolo().getProprietario() == Utente.utenteAttivo
					&& pubblicazione.getStato() == StatoPubblicazione.OFFERTA_APERTA
					&& pubblicazione.getArticolo().getCategoria() == getArticolo().getCategoria()) {
				menuOfferte.add(i++, pubblicazione.getArticolo().getDescrizione(), () -> Appuntamento.nuovoCollegamento(pubblicazione,  this));
			}
		}
		
		Menu.vaiA(menuOfferte);
		return true;
	}
	
	public static void caricaMemoria(ArrayList<Pubblicazione> memoria) {
		Pubblicazione.pubblicazioni = memoria;
	}
}
