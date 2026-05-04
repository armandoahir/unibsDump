package it.unibs.fp.cuoriSolitari;

public class Persona {
	private String USERNAME;
	private int SEX;
	private int PREFERENCE;
	private int AGE;
	
	public Persona (String _username, int _age, int _eta, int sessualita) {
		this.USERNAME = _username;
		this.AGE = _age;
		this.SEX = _eta;
		this.PREFERENCE = sessualita;
	}

	
	public String getUSERNAME() {
		return USERNAME;
	}

	public int getSEX() {
		return SEX;
	}

	public int getPREFERENCE() {
		return PREFERENCE;
	}

	public int getAGE() {
		return AGE;
	}

	
	
	
	

}
