package it.unibs.fp.CartaPiùAlta;

import it.unibs.fp.mylib.NumeriCasuali;

public class MazzoItaliano extends Mazzo {
	
	private Carta[] listaCarte;
	private static final int NUMERO_CARTE = 40;
	private String TIPOLOGIA = "Mazzo italiano";
	
	public MazzoItaliano (Carta[] _listaCarte) {
		this.listaCarte = _listaCarte;
		setNumeroCarte(NUMERO_CARTE);
		setTipologia(TIPOLOGIA);
	}

	@Override
	public Carta estraiCarta() {
		int i = NumeriCasuali.estraiIntero(0, listaCarte.length - 1);
		return listaCarte[i];
	}
	
	
}
