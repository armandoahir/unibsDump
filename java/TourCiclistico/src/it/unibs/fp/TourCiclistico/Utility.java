package it.unibs.fp.TourCiclistico;

import it.unibs.fp.mylib.*;

public class Utility {
	
	private static final String CONFERMA = "Inserire 1 per confermare, 0 per annullare: ";
	private static final String MSG_OK = "OK! Elemento aggiunto con successo";
	private static final String MSG_ERRORE = "ERRORE! Elemento già esistente";
	private static final String OUTRO = "Programma terminato correttamente.";
	private static final String CITTA_PARTENZA = "Inserisci città di partenza: ";
	private static final String CITTA_ARRIVO = "Inserisci città di arrivo: ";
	private static final String INSERISCI_DISTANZA = "Inserisci la distanza (in Km): ";
	private static final String INSERISCI_ALTITUDINE = "Inserisci l'altitudine (in Km): ";
	private static final String INSERISCI_DATA = "Inserisci in data (dd/mm): ";
	private static final String INSERISCI_VINCITORE = "Inserisci il nome del vincitore:";
	private static final String TAPPA_DA_ELIMINARE = "Inserisci ";

	public static boolean conferma() {
		int input = InputDati.leggiIntero(Utility.CONFERMA, 0, 1);
		if(input == 1)
		{
			return true;
		}
		return false;
	}
	
	public static void sceltaMenu(int _scelta, Tour _tour, Tappa _tappa) {
	
		switch(_scelta)
		{
		case 0:
			System.out.println(Utility.OUTRO);
			break;
			
		case 1:
			Tappa nuovaTappa = new Tappa();
			
			nuovaTappa.setCittà_partenza(InputDati.leggiStringaNonVuota(CITTA_PARTENZA));
			nuovaTappa.setCittà_arrivo(InputDati.leggiStringaNonVuota(CITTA_ARRIVO));
			nuovaTappa.setKm(InputDati.leggiIntero(INSERISCI_DISTANZA));
			nuovaTappa.setIndice_altimetrico(InputDati.leggiIntero(INSERISCI_ALTITUDINE));
			nuovaTappa.setData(InputDati.leggiStringaNonVuota(INSERISCI_DATA));
			
			if(_tour.aggiungiTappa(nuovaTappa) == true)
				System.out.println(MSG_OK);
			
			else 
				System.out.println(MSG_ERRORE);

			break;

		case 2: _tour.setVincitore(InputDati.leggiStringaNonVuota(INSERISCI_VINCITORE));
				System.out.println(MSG_OK);

			break;
		case 3: System.out.println(_tour.toString());
			
		}
	}
}
