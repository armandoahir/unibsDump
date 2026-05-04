package it.unibs.fp.Magazzino;
import it.unibs.fp.mylib.*;

public class Utility {
	
	private static final String CONFERMA = "Inserire 1 per confermare, 0 per annullare: ";
	private static final String MSG_OK = "OK! Elemento aggiunto con successo";
	private static final String MSG_ERRORE = "ERRORE! Elemento già esistente";
	private static final String OUTRO = "Programma terminato correttamente.";
	private static final String NOME_ARTICOLO = "Inserisci il nome dell'articolo da aggiungere: ";
	private static final String COMPRA_ARTICOLO = "Che articolo vuoi comprare? ";
	private static final String QUANTITA_ARTICOLO = "Quantità da comprare: ";
	private static final String PERIODO_DISPONIBILITA = "Inserisci i giorni lavorativi per la manodopera dell'articolo: ";
	private static final String FAI_ORDINE = "Fai il tuo ordine: ";
	private static final int MIN = 1;
	private static final int MAX = 100;
	

	public static boolean conferma() {
		int input = InputDati.leggiIntero(Utility.CONFERMA, 0, 1);
		if(input == 1)
		{
			return true;
		}
		return false;
	}
	
	public static void sceltaMenu(int _scelta, Magazzino _magazzino, Articolo _articolo) {
		
	
		switch(_scelta)
		{
		case 0:
			System.out.println(Utility.OUTRO);
			break;
			
		case 1: Articolo nuovoArticolo = new Articolo();
				
				nuovoArticolo.setNome(InputDati.leggiStringaNonVuota(NOME_ARTICOLO));
				nuovoArticolo.setQuantità(NumeriCasuali.estraiIntero(MIN, MAX));
				nuovoArticolo.setPeriodo(InputDati.leggiIntero(PERIODO_DISPONIBILITA));
			
			if(_magazzino.evitaOmonimia(nuovoArticolo) == true)
				System.out.println(MSG_ERRORE);
			
			else {
				_magazzino.aggiungiArticolo(nuovoArticolo);
				System.out.println(MSG_OK);
			}

			break;


		case 2: _magazzino.showElenco();

			break;

		case 3: 
			Articolo articolo = new Articolo();
			_magazzino.showElenco();
			
			System.out.println(FAI_ORDINE);
			String nome_articolo = InputDati.leggiStringaNonVuota(COMPRA_ARTICOLO);
			int quantità = InputDati.leggiIntero(QUANTITA_ARTICOLO, MIN, MAX);
			
			articolo.setQuantità(quantità);
			articolo.setNome(nome_articolo);
			Utility.conferma();
			
			_magazzino.checkQuantità(articolo);
		
			
		}
	}
}