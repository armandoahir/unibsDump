package it.unibs.gestionalebaratto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import it.unibs.gestionalebaratto.Pubblicazione.StatoPubblicazione;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(scope=Categoria.class, generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 6935720655L;
	public static Categoria attiva;
	private static ArrayList<Categoria> radici;
	
	public Integer id; //Richiesto da Jackson
	public String nome;
	public String descrizione;
	public Categoria genitore;
	public ArrayList<Categoria> sottoCategorie;
	public ArrayList<CampoNativo> campiNativi;
	
	/**
	 * Istanzia una nuova categoria.
	 *
	 * @param _nome nome della categoria
	 * @param _descrizione descrizione della categoria
	 * @param _genitore categoria genitore (null se radice)
	 */
	public Categoria(String _nome, String _descrizione, Categoria _genitore) {
		this.nome = _nome;
		this.descrizione = _descrizione;
		this.genitore = _genitore;
		this.sottoCategorie = new ArrayList<Categoria>();
		this.campiNativi = new ArrayList<CampoNativo>();
		if(_genitore == null) {
			radici.add(this);
			campiNativi.add(new CampoNativo("Stato di conservazione", true));
			campiNativi.add(new CampoNativo("Descrizione libera", true));
		} else {
			genitore.aggiungiSottoCategoria(this);
		}
	}

	
	/**
	 * Istanzia una nuova categoria radice.
	 *
	 * @param _nome nome nome della categoria
	 * @param _descrizione descrizione della categoria
	 */
	public Categoria(String _nome, String _descrizione) {
		this(_nome, _descrizione, null); //costruttore senza genitore
	}
	
	public Categoria() {
		this.sottoCategorie = new ArrayList<Categoria>();
		this.campiNativi = new ArrayList<CampoNativo>();
	}
	
	/**
	 * Aggiungi sottocategoria.
	 *
	 * @param _categoria sottocategoria da aggiungere
	 */
	public void aggiungiSottoCategoria(Categoria _categoria) {
		sottoCategorie.add(_categoria);
	}

	/**
	 * Elimina categoria.
	 *
	 * @param _categoria categoria
	 * @deprecated Prima implementazione, non più utilizzata
	 */
	public void eliminaCategoria(Categoria _categoria) {
		for(int i= 0; i < this.sottoCategorie.size(); i++) {
			if(this.sottoCategorie.get(i).equals(_categoria)) {
				this.sottoCategorie.remove(i);
			}
		}
	}
	
	/**
	 * Modifica nome e descrizione della categoria.
	 *
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 */
	public boolean modificaCategoria() {
		assert Utente.utenteAttivo != null;
		assert Utente.utenteAttivo.tipoAttivo == TipoUtente.CONFIGURATORE;
		System.out.printf("Inserire il nuovo nome [%s]: ", nome);
		String nuovoNome = Tastiera.leggiStringa();
		System.out.printf("Inserire la nuova descrizione [%s]: ", descrizione);
		String nuovaDesc = Tastiera.leggiStringa();
		
		if(nuovoNome != "") setNome(nuovoNome);
		if(nuovaDesc != "") setDescrizione(nuovaDesc);
		
		Menu.vaiA(toCategoriaConfigMenuOption()); //Rigenero il menu cateogria con il nome aggiornato
		
		return true;
	}
	
	/**
	 * Aggiunge un campo nativo alla categoria.
	 *
	 * @return true se l'aggiunta va a buon fine, false altrimenti
	 */
	public boolean aggiungiCampoNativo() {
		System.out.print("Inserire il nome del campo nativo: ");
		String nome = Tastiera.leggiStringa();
		
		//Elenco genitori per confronto campi nativi
		ArrayList<Categoria> genitori = new ArrayList<Categoria>();
		for(int i = genitori.size()-1; i >= 0; i--) {
			Categoria genitoreMenu = genitori.get(i);
			for(int j = 0; j < genitoreMenu.campiNativi.size(); j++) {
				if(genitoreMenu.campiNativi.get(i).getNome().equalsIgnoreCase(nome)) {
					System.out.printf("Il campo nativo \"%s\" e' gia' presente\n", nome);
					Tastiera.pausa();
					return false;
				}
			}
		}
		
		boolean obbligatorio = Tastiera.leggiOpzione("Il campo nativo a' obbligatorio?");
		campiNativi.add(new CampoNativo(nome, obbligatorio));
		
		return true;
	}
	
	/**
	 * Ritorna una lista delle categorie genitori.
	 *
	 * @return lista dei genitori
	 */
	public ArrayList<Categoria> elencoGenitori() {
		ArrayList<Categoria> genitori = new ArrayList<Categoria>();
		Categoria ultimaRicerca = this;
		do {
			genitori.add(ultimaRicerca);
			ultimaRicerca = ultimaRicerca.genitore;
		} while(ultimaRicerca != null);
		
		return genitori;
	}
	
	public ArrayList<CampoNativo> getCampiNativi() {
		ArrayList<CampoNativo> nativi = new ArrayList<CampoNativo>();
		
		//Ricorsivita' al contrario, in poche parole salvo tutti i genitori dalla foglia alla radice e li gestisco in ordine inverso
		ArrayList<Categoria> genitori = elencoGenitori();
		
		int kMenu = 1;
		for(int i = genitori.size()-1; i >= 0; i--) {
			Categoria genitoreMenu = genitori.get(i);
			for(int j = 0; j < genitoreMenu.campiNativi.size(); j++) {
				nativi.add(genitoreMenu.campiNativi.get(j));
			}
		}
		
		return nativi;
	}
	
	/**
	 * Visualizza l'elenco dei campi nativi di una categoria.
	 *
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 */
	public boolean visualizzaCampiNativiConfiguratore() {
		//Creazione menu navigazione campi nativi
		ElementoMenu menuNativi = new ElementoMenu("Gestione campi nativi", "Seleziona un campo nativo per visualizzare le opzioni", ElementoMenu.attivo);
		
		ArrayList<CampoNativo> nativi = getCampiNativi();
		
		int i=1;
		for (CampoNativo nativo : nativi) {
			menuNativi.add(i++, nativo.getNome(), () -> Menu.vaiA(nativo.toMenuOption()));
		}
		
		Menu.vaiA(menuNativi);
		
		return true;
	}
	
	public boolean visualizzaCampiNativiFruitore() {
		StringBuilder sb = new StringBuilder();
		for (CampoNativo campo : campiNativi) {
			sb.append(String.format("%s\n", campo.getNomeRaw()));
		}
		
		System.out.println(sb.toString());
		
		Tastiera.pausa();
		
		return true;
	}
	
	/**
	 * Visualizza l'albero della categoria (a partire dalla profondità 0).
	 *
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 * @deprecated Prima iplementazione, sostituita da mappaAlbero
	 */
	public boolean visualizzaAlbero() {
		return visualizzaAlbero(0);
	}

	/**
	 * Visualizza l'albero della categoria (a partire da una profondità definita).
	 *
	 * @param r profondità iniziale sull'albero (numero di spaziature prima del nome della categoria)
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 * @deprecated Prima iplementazione, sostituita da mappaAlbero
	 */
	public boolean visualizzaAlbero(int r) {
		StringBuilder albero = new StringBuilder();
		
		albero.append(tab(r) + toString() + '\n');
		
		if(sottoCategorie.size() > 0) {
			//albero.append(tab(r) + "Sottocategorie:\n");
			
			for(Categoria s : sottoCategorie) {
				albero.append(s.visualizzaAlbero(r+1));
			}
		} else {
			//albero.append(tab(r) + "Nessuna sottocategoria\n\n");
		}
		
		System.out.println(albero.toString());
		
		return true;
	}
	
	/**
	 * Visualizza l'albero delle sottocategorie in formato menu.
	 *
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 */
	public boolean visualizzaAlberoMenu() {
		ElementoMenu alberoMenu = new ElementoMenu("Elenco sottocategorie", "Seleziona una sottocategoria per modificarla", ElementoMenu.attivo);
		
		LinkedHashMap<String, Categoria> alberoMap = mappaAlbero(0);
		
		int iMenu = 1;
		for(Map.Entry<String, Categoria> entry : alberoMap.entrySet()) {
			switch (Utente.tipoAttivo) {
			case CONFIGURATORE:
				alberoMenu.add(iMenu++, entry.getKey(), () -> Menu.vaiA(entry.getValue().toCategoriaConfigMenuOption()));
				break;
			case FRUITORE:
				alberoMenu.add(iMenu++, entry.getKey(), () -> Menu.vaiA(entry.getValue().toCategoriaFruitoreMenuOption()));
				break;
			}
		}
		
		Menu.vaiA(alberoMenu);
		return true;
	}
	
	/**
	 * Crea la mappa dell'albero.
	 *
	 * @param r profondità (numero di spazi prima del nome della categoria)
	 * @return lista delle sottocategorie e del loro nome
	 */
	public LinkedHashMap<String, Categoria> mappaAlbero(int r) {
		LinkedHashMap<String, Categoria> albero = new LinkedHashMap<>();
		
		albero.put(tab(r) + toString(), this);
		
		if(sottoCategorie.size() > 0 ) {
			for(Categoria s : sottoCategorie) {
				albero.putAll(s.mappaAlbero(r+1));
			}
		}
		
		return albero;
	}

	//--Gestione della stampa annidata delle sottocategorie--
	private static String tab(int num) {
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < num; i++) {
			s.append('\t');
		}
		
		return s.toString();
	}
	
	// -- Getters & Setters --
	public Categoria getGenitore() {
		return genitore;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	/**
	 * Elimina la categoria.
	 *
	 * @return true se l'utente conferma la rimozione, false se la annulla
	 */
	public boolean elimina() {
		boolean conferma = Tastiera.leggiOpzione("ATTENZIONE! Rimuovendo la categoria verranno rimosse anche TUTTE le sue sottocategorie.\nVuoi continuare?");
		if(conferma) {
			if(genitore != null) {
				Menu.vaiA(genitore.toCategoriaConfigMenuOption());
				genitore.sottoCategorie.remove(this);
			} else {
				Menu.vaiA("configuratore");
				Categoria.radici.remove(this);
			}
		}
		return conferma;
	}
	
	public boolean visualizzaOfferteAperte() {
		ElementoMenu menuCategoriaFruitore = new ElementoMenu("Visualizza offerte", ElementoMenu.attivo);
		
		ArrayList<Pubblicazione> pubblicazioni = Pubblicazione.cercaPerCategoria(this);
		
		int i = 0;
		for (Pubblicazione pubblicazione : pubblicazioni) {
			if(pubblicazione.getStato() == StatoPubblicazione.OFFERTA_APERTA || pubblicazione.getStato() == StatoPubblicazione.OFFERTA_RITIRATA) {
				String txtOfferta = pubblicazione.getArticolo().getDescrizione();
				if(pubblicazione.getStato() == StatoPubblicazione.OFFERTA_RITIRATA) txtOfferta += " (ritirata)";
				menuCategoriaFruitore.add(i++, txtOfferta, () -> pubblicazione.visualizzaMenuFruitore());
			}
		}
		
		Menu.vaiA(menuCategoriaFruitore);
		
		return true;
		
	}
	
	public boolean visualizzaOfferteInScambio() {
		ElementoMenu menuCategoriaConfiguratore = new ElementoMenu("Visualizza offerte in scambio", ElementoMenu.attivo);
		
		ArrayList<Pubblicazione> pubblicazioni = Pubblicazione.cercaPerCategoria(this);
		
		int i = 0;
		for (Pubblicazione pubblicazione : Pubblicazione.pubblicazioni) {
			if(pubblicazione.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO)
				menuCategoriaConfiguratore.add(i++, pubblicazione.getArticolo().getDescrizione(), () -> pubblicazione.visualizzaMenuConfiguratore());
		}
		
		Menu.vaiA(menuCategoriaConfiguratore);
		
		return true;
	}
	
	public boolean visualizzaOfferteChiuse() {
		ElementoMenu menuCategoriaConfiguratore = new ElementoMenu("Visualizza offerte chiuse", ElementoMenu.attivo);
		
		ArrayList<Pubblicazione> pubblicazioni = Pubblicazione.cercaPerCategoria(this);
		
		int i = 0;
		for (Pubblicazione pubblicazione : Pubblicazione.pubblicazioni) {
			if(pubblicazione.getStato() == StatoPubblicazione.OFFERTA_CHIUSA)
				menuCategoriaConfiguratore.add(i++, pubblicazione.getArticolo().getDescrizione(), () -> pubblicazione.visualizzaMenuConfiguratore());
		}
		
		Menu.vaiA(menuCategoriaConfiguratore);
		
		return true;
		
	}
	

	/**
	 * Descrizione testuale della categoria.
	 *
	 * @return string contenente la descrizione della categoria
	 */
	@Override
	public String toString() { // !! da modificare
		return String.format("%s (%s)", nome, descrizione);
	}
	
	/**
	 * Creazione del menu legato alla categoria.
	 *
	 * @return menu da visualizzare
	 */
	public ElementoMenu toCategoriaConfigMenuOption() {
		assert Utente.utenteAttivo != null;
		assert Utente.tipoAttivo == TipoUtente.CONFIGURATORE;
		Categoria.attiva = this; //Aggiorno la categoria correntemente attiva
		ElementoMenu menuCategoria = new ElementoMenu("Sfoglia categoria", toString(), ElementoMenu.attivo);
		
		menuCategoria.add(1, "Modifica categoria", () -> modificaCategoria());
		menuCategoria.add(2, "Aggiungi sottocategoria", () -> creaCategoria());
		menuCategoria.add(3, "Visualizza sottocategorie", () -> visualizzaAlberoMenu());
		menuCategoria.add(4, "Aggiungi campo nativo", () -> aggiungiCampoNativo());
		menuCategoria.add(5, "Visualizza campi nativi", () -> visualizzaCampiNativiConfiguratore());
		menuCategoria.add(6, "Elimina categoria", () -> elimina());
		
		if(sottoCategorie.isEmpty()) {
			menuCategoria.add(7, "Visualizza offerte aperte", () -> visualizzaOfferteAperte());
			menuCategoria.add(8, "Visualizza offerte in scambio", () -> visualizzaOfferteInScambio());
			menuCategoria.add(9, "Visualizza offerte chiuse", () -> visualizzaOfferteChiuse());
		}
		
		menuCategoria.add(0, "Torna alla radice", () -> Menu.vaiA(Categoria.toRadiciConfigMenuOption()));
		
		return menuCategoria;
	}
	
	
	public ElementoMenu toCategoriaFruitoreMenuOption() {
		assert Utente.utenteAttivo != null;
		assert Utente.tipoAttivo == TipoUtente.FRUITORE;
		Categoria.attiva = this; //Aggiorno la categoria correntemente attiva
		ElementoMenu menuCategoriaFruitore = new ElementoMenu("Sfoglia categoria", toString(), ElementoMenu.attivo);
		
		menuCategoriaFruitore.add(1, "Visualizza sottocategorie", () -> visualizzaAlberoMenu());
		menuCategoriaFruitore.add(2, "Visualizza campi nativi", () -> visualizzaCampiNativiFruitore());
		if(sottoCategorie.isEmpty()) {
			menuCategoriaFruitore.add(3, "Aggiungi un articolo", () -> Articolo.aggiungiArticoloMenu());
			menuCategoriaFruitore.add(4, "Visualizza offerte aperte e ritirate", () -> visualizzaOfferteAperte());
		}
		menuCategoriaFruitore.add(0, "Torna alla radice", () -> Menu.vaiA(Categoria.toRadiciFruitoreMenuOption()));
		
		return menuCategoriaFruitore;
	}
	
	
	
	/**
	 * Creazione del menu categorie radici.
	 *
	 * @return menu da visualizzare
	 */
	public static ElementoMenu toRadiciConfigMenuOption() {
		Categoria.attiva = null; //Se torno all'elenco delle categorie genitori non ho più una categoria attiva
		ElementoMenu menuRadice = new ElementoMenu("Sfoglia categorie genitori", ElementoMenu.attivo);
		menuRadice.add(0, "Torna al menu configuratore", () -> Menu.vaiA("configuratore"));
		for(int i=0; i<radici.size(); i++) {
			Categoria c = radici.get(i);
			menuRadice.add(i+1, radici.get(i).toString(), () -> Menu.vaiA(c.toCategoriaConfigMenuOption()));
		}
		
		return menuRadice;
	}
	
	
	public static ElementoMenu toRadiciFruitoreMenuOption() {
		Categoria.attiva = null;
		ElementoMenu menuRadice = new ElementoMenu("Sfoglia categorie genitori", ElementoMenu.attivo);
		menuRadice.add(0, "Torna al menu fruitore", () -> Menu.vaiA("fruitore"));
		
		for(int i=0; i<radici.size(); i++) {
			Categoria c = radici.get(i);
			menuRadice.add(i+1, radici.get(i).toString(), () -> Menu.vaiA(c.toCategoriaFruitoreMenuOption()));
		}
		return menuRadice;
	}
	
	
	
	/**
	 * Crea la nuova categoria e imposta il nuovo menu in caso l'utente ne richieda il passaggio.
	 *
	 * @return true (soddisfa l'esecuzione del comando da parte del menu)
	 */
	public static boolean creaCategoria() {
		assert Utente.utenteAttivo != null;
		assert Utente.utenteAttivo.tipoAttivo == TipoUtente.CONFIGURATORE;
		System.out.print("Inserisci il nome della categoria: ");
		String nome = Tastiera.leggiStringa();
		System.out.print("Inserisci la descrizione della categoria: ");
		String descrizione = Tastiera.leggiStringa();
		Categoria c = new Categoria(nome, descrizione, Categoria.attiva);
				
		if(Tastiera.leggiOpzione("Vuoi passare alla nuova categoria?")) {
			Categoria.attiva = c;
			Menu.vaiA(c.toCategoriaConfigMenuOption());
		}
		
		return true;
	}
	
	/**
	 * Carica le categorie (radici e sottocategorie) dalla memoria.
	 *
	 * @param memoria lista di categorie da memoria
	 */
	public static void caricaMemoria(ArrayList<Categoria> memoria) {
		Categoria.radici = memoria;
	}
	
	/**
	 * Reset.
	 *
	 * @return true, se la funzione è soddisfatta
	 * @deprecated Funzione di test, non utilizzata
	 */
	public static boolean reset() {
		Categoria.radici.clear();
		return true;
	}
}
