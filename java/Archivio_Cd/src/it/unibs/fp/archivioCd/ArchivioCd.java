package it.unibs.fp.archivioCd;

import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.NumeriCasuali;

public class ArchivioCd {
	public static final String INSERT_SIZE = "Number of Tracks on CD: ";
	
	private final String CD_DELETED = "Cd deleted.";
	private final String CD_NOT_FOUND = "Cd not found.";
	private final String CD_EXIST = "Cd already exist.";
	private final String CD_ELIMINATION_ABORTED = "Elimination aborted.";
	private final String CD_AUTHOR = "Cd author: ";
	private final String CD_TITLE = "Cd title: ";
	private final String SHELF_EMPTY = "No cd on the shelf.";
	private final String CD_DOESNT_EXIST = "Cd doesn't exist.";
	
	ArrayList <Cd> shelf = new ArrayList<Cd>();
	private int cdCounter = 0;
	
	/**
	 * Aggiunge, all'archivio, il CD immesso dall'utente
	 * @param _cd, cd immesso dall'utente
	 */
	public void aggiungiCd(Cd _cd) {
		this.shelf.add(_cd);
		cdCounter++;
	}
	
	public int getNumeroCd() {
		return this.cdCounter;
	}
	
	/**
	 * Controllo del titolo per evitare duplicati
	 * @param _title, titolo del CD cercato
	 * @return true se duplicato, analogamente false
	 */
	
	public boolean contiene (String _title) {
		for(int i = 0; i < this.cdCounter; i++) {
			if(_title.equalsIgnoreCase(this.shelf.get(i).getTitolo()))
				return true;
		}
		return false;
	}
	
	/**
	 * Eliminazione di un CD musicale dalla collezione
	 * @param _title, eliminazione tramite titolo. Titolo immesso dall'utente
	 */
	public void eliminaCd(String _title) {
		int pos = searchCd(_title);
		
		if(pos != -1) {
			if(Manage.confirm()) {
				
				this.shelf.remove(pos);
				this.cdCounter--;
				System.out.println(this.CD_DELETED);
				
			} else System.out.println(CD_ELIMINATION_ABORTED);
			
		}else System.out.println(CD_NOT_FOUND);
	}
	
	/**
	 * Metodo che ricerca un CD richiesto dall'utente, mediante il titolo
	 * @param _title, rappresneta il titolo immesso dall'utente
	 * @return i, ovvero la posizione del CD richiesto, se esiste.
	 * @return -1 se l CD non esiste nell'archivio
	 */
	public int searchCd(String _title) {
		for(int i = 0; i < this.cdCounter; i++) {
			if(_title.equalsIgnoreCase(this.shelf.get(i).getTitolo())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * controlla se il titolo immesso dall'utente è già presente nell'archivio
	 * per evitare duplicazioni
	 * @param _title immesso dall'utente
	 * @return true se esiste già il titolo
	 * @return false se non esistono errori di duplicazione
	 */
	public boolean checkHomonymy(String _title) {
		for(int i = 0; i < this.cdCounter; i++) {
			if(_title.equalsIgnoreCase(this.shelf.get(i).getTitolo()))
				return true;
		}
		return false;
	}
	
	public String getRandomCd (ArrayList <Cd> _shelf, int _playthis) {
		return _shelf.get(_playthis).toString();
	}
	
	/**
	 * aggiunge CD musicale all'archivio
	 * controllando se ci sono eventuali duplicazioni
	 * @return Cd con titolo, autore 
	 */
	public Cd loadCd() {
		boolean signal;
		String title;
		
		do {
			signal = true;
			title = InputDati.leggiStringaNonVuota(this.CD_TITLE);
			
			if(checkHomonymy(title)) {
				System.out.println(this.CD_EXIST);
				signal = Manage.confirm();
				
				if(!signal) 
					signal = true;
				else 
					signal = false;
			}
		}while(!signal);
		
		String author = InputDati.leggiStringaNonVuota(this.CD_AUTHOR);
		return new Cd(title, author);
	}
	
	/**
	 * Estrae casualmente un brano dai CD presenti nell'archivio e lo stampa a video
	 * Nel caso in cui l'archivio fosse vuoto, il programma stampa a video SHELF_EMPTY, ovvero una stringa
	 * comunica che non esistono CD nell'archivio
	 */
	
	public void playTrackFromArchivio() {
		if(this.cdCounter > 0) {
			int pos = NumeriCasuali.estraiIntero(0, this.cdCounter - 1);
			this.shelf.get(pos).playRandomTrack();
		}
		else System.out.println(SHELF_EMPTY);
	}
	
	/**
	 * restituisce una stringa che descrive il CD cercato dall'utente
	 * @param _title, titolo inserito dall'utente
	 * @return stringa descrittiva del CD
	 */
	public String viewCd(String _title) {
		int pos = searchCd(_title);
		if(pos != -1) 
			return this.shelf.get(pos).toString();
		
		else return this.CD_DOESNT_EXIST;
	}
	
	/**
	 * Descrive lo stato dell'archivio tramite una stringa
	 * @return Stringa descrittiva dell'archivio
	 */
	public String viewArchivio() {
		int i;
		StringBuffer str = new StringBuffer();
		
		for(i = 0; i < this.cdCounter; i++) {
			str.append(String.format("%s\n", this.shelf.get(i).toString()));
		}
		return str.toString();
	}
}

