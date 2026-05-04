package it.unibs.fp.archivioCd;

import it.unibs.fp.mylib.InputDati;

public class Manage {
	
	private static final String CONFIRM = "0 DECLINE\n1 CONFIRM\n";
	private static final String SEARCH_CD = "Search CD: ";
	private static final String DELETE_CD = "Delete CD: ";
	private static final String EXIT = "Program closed";
	
	/**
	 * Richiesta di conferma o declino all'utente
	 * @return true se l'utente conferma l'azione
	 * @return false se l'utente nega l'azione
	 */
	public static boolean confirm() {
		int input = InputDati.leggiIntero(Manage.CONFIRM, 0, 1);
		if(input == 1) return true;
		else return false;
	}
	
	/**
	 * Gestisce le azioni richieste dall'utente
	 * @param _choice scelta input dell'utente
	 * @param _library archivio del programma
	 */
	public static void menuChoice(int _choice, ArchivioCd _library) {
		String titolo, str;
		switch(_choice) {
		
		case 0: System.out.println(Manage.EXIT);
		break;
		
		case 1: 
			Cd cd = _library.loadCd();
			int i, size = InputDati.leggiInteroPositivo(ArchivioCd.INSERT_SIZE);
			
			for(i = 0; i < size; i++) {
				cd.aggiungiBrano(cd.loadTrack());
			}
			_library.aggiungiCd(cd);
			break;
			
		case 2: 
			titolo = InputDati.leggiStringaNonVuota(Manage.SEARCH_CD);
			str = _library.viewCd(titolo);
			System.out.println(str);
			break;
			
		case 3:
			titolo = InputDati.leggiStringaNonVuota(Manage.DELETE_CD);
			_library.eliminaCd(titolo);
			break;
			
		case 4:
			str = _library.viewArchivio();
			System.out.println(str);
			break;
			
		case 5: 
			_library.playTrackFromArchivio();
		}
	}
}
