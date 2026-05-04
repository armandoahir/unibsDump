package it.unibs.gestionalebaratto;

import java.io.Serializable;

public class CampoNativo implements Serializable {
	
	private static final long serialVersionUID = 2805146738742997432L;
	private String nome;
	private boolean obbligatorio;
	
	/**
	 * Istanzia un nuovo campo nativo.
	 *
	 * @param _nome nome del campo
	 * @param _obbligatorio flag di obligatorietà
	 */
	public CampoNativo (String _nome, boolean _obbligatorio) {
		this.nome =_nome;
		this.obbligatorio = _obbligatorio;
	}
	
	public CampoNativo() {
		
	}

	public String getNome() {
		return String.format("%s%s", this.obbligatorio? "*" : " ", this.nome);
	}
	
	public String getNomeRaw() {
		return this.nome;
	}

	public boolean isObbligatorio() {
		return obbligatorio;
	}
	
	/**
	 * Genera il menu del campo nativo.
	 *
	 * @return menu da visualizzare
	 */
	public ElementoMenu toMenuOption() {
		ElementoMenu menu;
		if(Categoria.attiva.campiNativi.contains(this)) {
			menu = new ElementoMenu("Menu campo nativo", nome, ElementoMenu.attivo);
			switch (Utente.tipoAttivo) {
			case CONFIGURATORE:
				menu.add(1, "Elimina campo nativo", () -> elimina());
				break;
			case FRUITORE:
				break;
			}
		} else {
			menu = new ElementoMenu("Menu campo nativo", nome + " (ereditato)", ElementoMenu.attivo);
		}
		
		return menu;
		
	}
	
	
	/**
	 * Elimina il campo nativo.
	 *
	 * @return Se la rimozione è stata confermata il risultato dell'azione di remove dalla lista, altrimenti false
	 */
	public boolean elimina() {
		boolean conferma = Tastiera.leggiOpzione("ATTEZIONE: Questo campo nativo verra' eliminato anche dalle sottocategorie. Vuoi continuare?");
		if(conferma) {
			Menu.vaiA(Categoria.attiva.toCategoriaConfigMenuOption());
			return Categoria.attiva.campiNativi.remove(this);
		} else {
			return false;
		}
		
	}
	
	@Override
	public String toString() {
		return nome;
	}

}
