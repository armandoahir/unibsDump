package it.unibs.fp.CartaPiùAlta;

import it.unibs.fp.mylib.InputDati;

public class Utente {
	private final String PUNTATA = "Punta: ";
	private String nome;
	private int capitale;
	
	public Utente(String _nome, int _capitale) {
		this.nome = _nome;
		this.capitale = _capitale;
	}

	public int puntata() {
		return InputDati.leggiIntero(PUNTATA, 1, capitale);
	}

	public int getCapitale() {
		return capitale;
	}

	public void setCapitale(int capitale) {
		this.capitale = capitale;
	}

	@Override
	public String toString() {
		return nome + ": " + capitale + "$";
	}
	
	
	
	
	
	

}
