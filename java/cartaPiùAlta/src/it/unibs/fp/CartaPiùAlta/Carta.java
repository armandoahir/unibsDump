package it.unibs.fp.CartaPiùAlta;

public class Carta {
	private int valore;
	private String nome;
	private String seme;
	
	
	public Carta(String nome, int valore, String _seme) {
		this.valore = valore;
		this.nome = nome;
		this.seme = _seme;
	}

	
	public int getValore() {
		return valore;
	}

	public void setValore(int valore) {
		this.valore = valore;
	}

	public String nome() {
		return nome;
	}

	public void nome(String nome) {
		this.nome = nome;
	}


	@Override
	public String toString() {
		return "Carta ["+ nome + " di " + seme + "]";
	}

	
	
}

