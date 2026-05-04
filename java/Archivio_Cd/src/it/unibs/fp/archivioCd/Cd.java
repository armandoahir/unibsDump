package it.unibs.fp.archivioCd;

import java.util.*;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.NumeriCasuali;

public class Cd {
	private final String RANDOM_TRACK = "Shuffled:  ";
	private final String DESCRIPTION = "Titolo: %s, Autore: %s, ";
	private final String LIST = "Lista dei brani: ";
	
	private String cdTitle;
	private String author;
	ArrayList<Brano> tracklist = new ArrayList<Brano>();
	
	public Cd(String _cdTitle, String _author) {
		this.author =_author;
		this.cdTitle =_cdTitle;
	}

	/**
	 * Aggiunge brano all'arraylist di Brani del Cd
	 * @param _track, brano inserito dall'utente
	 */
	public void aggiungiBrano(Brano _track) {
		this.tracklist.add(_track);
	}
	
	/**
	 * Chiede all'utente nome e durata del brano
	 * @return nuovo Brano
	 */
	public Brano loadTrack() {
		String _name = InputDati.leggiStringaNonVuota(Brano.INSERT_TRACK_TITLE);
		int _minutes = InputDati.leggiIntero(Brano.INSERT_MINUTES, Brano.MIN, Brano.MAX);
		int _seconds = InputDati.leggiIntero(Brano.INSERT_SECONDS, Brano.MIN, Brano.MAX);
		return new Brano(_name, _minutes, _seconds);
	}

	/**
	 * estrazione casuale di un brano da un CD della collezione 
	 */
	public void playRandomTrack() {
		System.out.println(this.RANDOM_TRACK + branoCasuale().getTitolo()); 
	}
	
	/**
	 * Sceglie casualmente un brano da un CD
	 * @return brano scelto
	 */
	public Brano branoCasuale() {
		int pos = NumeriCasuali.estraiIntero(0, this.tracklist.size() - 1);
		return this.tracklist.get(pos);
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer(String.format(this.DESCRIPTION, this.cdTitle, this.author));
		result.append(this.LIST);
		
		if(this.tracklist.size() != 0) {
			for(int i = 0; i < this.tracklist.size(); i++) {
				result.append(String.format("%s",this.tracklist.get(i).toString()));
			}
		} 
		
		return result.toString();
	}
	
	/**
	 * @return arraylist di brani inseriti dall'utente nel cd
	 */
	public ArrayList<Brano> getTracklist() {
		return this.tracklist;
	}
	
	public String getTitolo() {
		return this.cdTitle;
	}
	
	/**
	 * Controlla se il titolo esiste già nella collezione
	 * @param _title titolo inserito dall'utente
	 * @return true se presente
	 * @return false se non è presente
	 */
	public boolean haTitolo(String _title) {
		if(this.cdTitle.equalsIgnoreCase(_title))
			return true;
		else
			return false;
	}
}
