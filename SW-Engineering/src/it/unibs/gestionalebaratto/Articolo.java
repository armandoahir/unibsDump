package it.unibs.gestionalebaratto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Articolo implements Serializable {
	
	private static final long serialVersionUID = 690365553156602506L;
	private Utente proprietario;
	private Categoria categoria;
	private LinkedHashMap<CampoNativo,String> campiCompilati;
	
	private static ArrayList<Articolo> listaArticoli;
	
	
	public Articolo(Categoria categoria, LinkedHashMap<CampoNativo,String> campiCompilati) {
		this.categoria = categoria;
		this.campiCompilati = campiCompilati;
		this.proprietario = Utente.utenteAttivo;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void compilaCampiNativi() {
		for(CampoNativo c : categoria.getCampiNativi()){
			String valoreInserito = richiestaValoreCampo(c);
			campiCompilati.put(c, valoreInserito);
		}
	}
	
	public String getDescrizione() {
		String testo = "N.D.";
		for (Entry<CampoNativo, String> pair: campiCompilati.entrySet()) {
            if(pair.getKey().toString().equalsIgnoreCase("Descrizione Libera")) {
            	testo = pair.getValue();
            	break;
            }
        }
		
		return testo;
	}
	
	public Utente getProprietario() {
		return proprietario;
	}
	
	public boolean visualizzaCampiNativi() {
		StringBuilder sb = new StringBuilder();
		for (Entry<CampoNativo, String> pair: campiCompilati.entrySet()) {
			sb.append(String.format("%s: %s\n", pair.getKey().getNomeRaw(), pair.getValue()));
		}
		
		System.out.println(sb.toString());
		
		Tastiera.pausa();
		
		return true;
	}
	
	public boolean visualizzaAutore() {
		System.out.printf("Autore: %s", getProprietario().getUsername());
		
		Tastiera.pausa();
		
		return true;
	}

	public static String richiestaValoreCampo(CampoNativo c) {
		String valore;
		
		boolean checkObbligatorio;
		do {
			checkObbligatorio = false;
			System.out.print(c.getNomeRaw() + ": ");
			valore = Tastiera.leggiStringa();
			if(c.isObbligatorio() && (valore.isEmpty() || valore == "")) {
				System.out.println("Campo obbligatorio! Inserire un valore");
				checkObbligatorio = true;
			}
		} while(checkObbligatorio);
		
		return valore;
	}
	
	public static Articolo aggiungiArticolo(Categoria categoria, LinkedHashMap<CampoNativo,String> campiCompilati) {
		
		Articolo nuovoArticolo = new Articolo(categoria, campiCompilati);
		listaArticoli.add(nuovoArticolo);
		return nuovoArticolo;
	}
	
	public static boolean aggiungiArticoloMenu() {		
		LinkedHashMap<CampoNativo, String> campiCompilati = new LinkedHashMap<>();
		ArrayList<CampoNativo> campiNativi = Categoria.attiva.getCampiNativi();
		
		for (CampoNativo nativo : campiNativi) {
			campiCompilati.put(nativo, richiestaValoreCampo(nativo));
		}
		
		Articolo articolo = aggiungiArticolo(Categoria.attiva, campiCompilati);
		Pubblicazione.aggiungiPubblicazione(articolo);
		
		return true;
	}
	
	public static void caricaMemoria(ArrayList<Articolo> memoria) {
		Articolo.listaArticoli = memoria;
	}
}