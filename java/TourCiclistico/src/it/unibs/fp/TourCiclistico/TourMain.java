package it.unibs.fp.TourCiclistico;
import it.unibs.fp.mylib.*;

public class TourMain {
	
	private static final int ANNO_MIN = 1990;
	private static final String TOUR_NAME = "Inserisci il nome del tour: ";
	private static final String ANNO = "Inserisci l'anno del tour: ";
	private static final String OWNER = "Inserisci il nome dell'organizzatore del tour: ";
	private static final String TITOLO = "GESTORE TOUR CICLISTICO";
	private static final String[] VOCI = {
			
			"Aggiungi tappa",
			"Aggiungi vincitore del tour",
			"Resoconto tour"
	};

	public static Tour creaTour(String _name_tour, int _anno, String _owner) {
		return new Tour (_name_tour, _anno, _owner);
	}
	public static Tappa creaTappa() {
		return new Tappa();
	}
	
	public static void main(String[] args) {
	
		String tour_name = InputDati.leggiStringaNonVuota(TOUR_NAME);
		int anno = InputDati.leggiInteroConMinimo(ANNO, ANNO_MIN);
		String owner = InputDati.leggiStringaNonVuota(OWNER);
		
		Tour tour = creaTour(tour_name, anno, owner);
		Tappa tappa = creaTappa();
		
		MyMenu menu = new MyMenu(TITOLO, VOCI);
		int scelta;
		do {
			scelta = menu.scegli();
			Utility.sceltaMenu(scelta, tour, tappa);
			
		}while(scelta != 0);
	}

}
