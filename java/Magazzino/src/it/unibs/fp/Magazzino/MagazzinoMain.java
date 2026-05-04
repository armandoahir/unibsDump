package it.unibs.fp.Magazzino;
import it.unibs.fp.mylib.*;
import java.util.*;

public class MagazzinoMain {

	private static final String TITOLO = "MAGAZZINO";
	private static final String[] VOCI = {
			"Aggiungi articolo",
			"Mostra catalogo",
			"Fai un ordine"
	};
	
	public static Magazzino creaMagazzino(ArrayList<Articolo> articoli) {
		return new Magazzino(articoli);
	}
	
	 	public static Articolo creaArticolo (String _nome, int _quantità, int _periodo) {
		return new Articolo(_nome, _quantità, _periodo);
		}
	 	
	public static Articolo creaArticolo() {
		return new Articolo();
	}
	
	public static void main(String[] args) {
		
		int scelta;
		Articolo articolo = creaArticolo();
		ArrayList<Articolo> elencoArticoli = new ArrayList<Articolo>();
		Magazzino magazzino = creaMagazzino(elencoArticoli);
		MyMenu menu = new MyMenu(TITOLO, VOCI);
		do {
			scelta = menu.scegli();
			Utility.sceltaMenu(scelta, magazzino, articolo);
			
		}while(scelta != 0);
		
	}

}
