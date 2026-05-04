package it.unibs.gestionalebaratto;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ElementoMenu {
	
	public static ElementoMenu attivo;

	private class OpzioneMenu {
		private int position;
		private String text;
		private Callable<Boolean> operation;
		
		/**
		 * Istanzia una nuova opzione utilizzabile in un menu.
		 *
		 * @param position numero da tastiera corrispondente
		 * @param text testo dell'opzione
		 * @param operation operazione da svolgere
		 */
		public OpzioneMenu(int position, String text, Callable<Boolean> operation) {
			this.position = position;
			this.text = text;
			this.operation = operation;
		}
		
		/**
		 * Esegue l'operazione associata all'opzione.
		 */
		public void execute() {
			try {
				operation.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Genera la stringa da viusalizzare all'interno del menu.
		 *
		 * @return string stringa opzione
		 */
		@Override
		public String toString() {
			return String.format("[%d] %s", position, text);
		}
		
	}
	
	private String title;
	private String subtitle;
	private ArrayList<OpzioneMenu> opzioni;
	private ElementoMenu precedente;

	/**
	 * Istanzia un nuovo menu.
	 *
	 * @param title titolo del menu
	 */
	public ElementoMenu(String title) {
		this(title, null, null);
	}
	
	/**
	 * Istanzia un nuovo elemento menu (con indicazione del precedente, per concatenazione).
	 *
	 * @param title titolo del menu
	 * @param precedente menu precedente
	 */
	public ElementoMenu(String title, ElementoMenu precedente) {
		this(title, null, precedente);
	}
	
	/**
	 * Istanzia un nuovo elemento menu (con sottotitolo).
	 *
	 * @param title titolo del menu
	 * @param subtitle sottotitolo del menu
	 */
	public ElementoMenu(String title, String subtitle) {
		this(title, subtitle, null);
	}

	/**
	 * Istanzia un nuovo elemento menu (con sottotitolo e indicazione del precedente).
	 *
	 * @param title titolo del menu
	 * @param subtitle sottotitolo del menu
	 * @param precedente menu precedente
	 */
	public ElementoMenu(String title, String subtitle, ElementoMenu precedente) {
		this.title = title;
		this.subtitle = subtitle;
		this.precedente = precedente;
		opzioni = new ArrayList<OpzioneMenu>();
	}
	
	/**
	 * Aggiunge un'opzione al menu.
	 *
	 * @param number numero da tastiera corrispondente
	 * @param text testo dell'opzione
	 * @param operation operazione da eseguire
	 */
	public void add(int number, String text, Callable<Boolean> operation) {
		opzioni.add(new OpzioneMenu(number, text, operation));
	}
	
	/**
	 * Visualizza il menu.
	 */
	public void visualizza() {
		System.out.printf("\n\n\n"); //Spaziatura tra i menu
		System.out.printf("--- %s ---\n", title.toUpperCase());
		if(subtitle != null) System.out.printf("- %s -\n\n", subtitle);
		else System.out.printf("\n");
		if(opzioni.size() == 0) {
			System.out.println("Nessuna opzione disponibile");
		} else {
			for (OpzioneMenu option : opzioni) {
				System.out.println(option.toString());
			}
		}
	}
	
	/**
	 * Visualizza il menu e rimane in attesa di input da parte dell'utente.
	 */
	public void visualizzaEdEsegui() {
		ElementoMenu.attivo = this; //Imposto questo menu come attivo
		visualizza();
		System.out.print(Costanti.COMANDO_INSERIMENTO);
		int scelta = Tastiera.t.eseguiMenu();
		
		switch (scelta) {
		case -1: //Indietro
			ElementoMenu.attivo = ElementoMenu.attivo.precedente;
			break;
		case -2: //Esci
			ElementoMenu.attivo = null;
			break;
		default:
			for(OpzioneMenu opzione : opzioni) { //Ricerca della scelta
				if(opzione.position == scelta) { //Scelta trovata
					try {
						opzione.execute();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
