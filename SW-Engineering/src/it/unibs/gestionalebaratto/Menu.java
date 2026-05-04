package it.unibs.gestionalebaratto;

import java.util.HashMap;

public class Menu {	
	public static ElementoMenu iniziale;
	public static HashMap<String, ElementoMenu> menus;
	
	/**
	 * Definizione della struttura menu e dei menu base.
	 */
	public static void init() {
		menus = new HashMap<String, ElementoMenu>();
		
		
		// --- SEZIONE MENU PRINCIPALE ---
		ElementoMenu iniziale = new ElementoMenu("Menu Principale", "Accedi o crea un utente");
		iniziale.add(1, "Esegui il login", () -> Utente.accediMenu());
		iniziale.add(8, "Crea un fruitore", () -> Fruitore.aggiungiUtenteMenu());
		iniziale.add(9, "Crea un configuratore", () -> Configuratore.aggiungiUtenteMenu());
		Menu.iniziale = iniziale;
		menus.put("iniziale", iniziale);
		
		// --- SEZIONE MENU CONFIGURATORE ---
		ElementoMenu configuratore = new ElementoMenu("Menu configuratore", iniziale);
		configuratore.add(1, "Crea categoria genitore", () -> Categoria.creaCategoria());
		configuratore.add(2, "Sfoglia categorie genitori", () -> vaiA(Categoria.toRadiciConfigMenuOption()));
		configuratore.add(3, "Visualizza e modifica dati piattaforma", () -> vaiA(Piattaforma.attiva.toMenuOption()));
		configuratore.add(4, "Visualizza tutte le offerte", () -> Configuratore.visualizzaOfferte());
		configuratore.add(5, "Importa i dati della piattaforma", () -> Memoria.m.ImportPiattaforma());
		configuratore.add(6, "Importa i dati delle categorie", () -> Memoria.m.ImportCategorie());
		menus.put("configuratore", configuratore);
		
		
		// ---SEZIONE MENU FRUITORE ---
		ElementoMenu fruitore = new ElementoMenu("Menu fruitore", iniziale);
		fruitore.add(1, "Visualizza categorie", () -> vaiA(Categoria.toRadiciFruitoreMenuOption()));
		fruitore.add(2, "Visualizza le mie offerte", () -> Fruitore.visualizzaProprieOfferte());
		fruitore.add(3, "Visualizza i miei appuntamenti", () -> Fruitore.visualizzaAppuntamenti());
		menus.put("fruitore", fruitore);
		
		ElementoMenu.attivo = iniziale;

	}
	
	/**
	 * Avvio del menu a partire dal menu iniziale.
	 */
	public static void start() {
		ElementoMenu.attivo.visualizzaEdEsegui();
	}
	
	/**
	 * Imposta un nuovo menu come attivo.
	 *
	 * @param chiaveMenu nome del nuovo menu
	 * @return true se il menu esiste ed è stato programmato per la visualizzazione, false altrimenti
	 */
	public static boolean vaiA(String chiaveMenu) {
		ElementoMenu nuovoMenu = menus.get(chiaveMenu);
		
		if(nuovoMenu != null) {
			ElementoMenu.attivo = nuovoMenu;
			return true;
		} else {
			return false;
		}
	}

	public static ElementoMenu getArr(String chiaveMenu) {
		ElementoMenu nuovoMenu = menus.get(chiaveMenu);
		
		return nuovoMenu;
	}
	
	/**
	 * Imposta un nuovo menu come attivo.
	 *
	 * @param nuovoMenu riferimento al nuovo menu
	 * @return true se il menu esiste ed è stato programmato per la visualizzazione, false altrimenti
	 */
	public static boolean vaiA(ElementoMenu nuovoMenu) {
		if(nuovoMenu != null) {
			ElementoMenu.attivo = nuovoMenu;
			return true;
		} else {
			return false;
		}
	}

}
