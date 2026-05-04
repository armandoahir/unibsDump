package it.unibs.fp.Magazzino;

public class Articolo {
	
	String nome;
	int quantità;
	int periodo;

	public Articolo(String _nome, int _quantità, int _periodo) {
		this.nome = _nome;
		this.quantità = _quantità;
		this.periodo = _periodo;
	}
	
	public Articolo() {
		
	}

	public String getNome() {
		return this.nome;
	}
	
	public String setNome(String _nome) {
		return this.nome = _nome;
	}

	public int getQuantità() {
		return this.quantità;
	}

	
	public void scalaQuantità (int _quantità) {
		setQuantità(this.quantità - _quantità); 
		
	}
	public void setQuantità(int _quantità) {
		this.quantità = _quantità;
	}

	public int getPeriodo() {
		return this.periodo;
	}
	public void setPeriodo(int _periodo) {
		this.periodo = _periodo;
	}

	@Override
	public String toString() {
		return " Articolo: " + this.getNome() + " - Q.ta disponibili: " + this.getQuantità() + " - Tempo di produzione: " + this.getPeriodo() + " giorni" + "\n";
	}
	
}
