package it.unibs.fp.CartaPiùAlta;

public abstract class Mazzo {
	private int NumeroCarte;
	private String tipologia;
	
	public int getNumeroCarte() {
		return NumeroCarte;
	}
	
	public abstract Carta estraiCarta();
	
	public void setNumeroCarte(int numeroCarte) {
		NumeroCarte = numeroCarte;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
}
