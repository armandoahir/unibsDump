package it.unibs.fp.puntieRette;

public class Retta {
	private Punto p1;
	private Punto p2;
	private double coeffAng; // coefficiente angolare m della retta
	private double offset; // coefficiente q della retta
	
	public Retta (Punto _p1, Punto _p2) {
		this.p1 = _p1;
		this.p2 = _p2;
		this.coeffAng = 0;
		this.offset = 0;
	}
	
	public String equazione() { // y = m x + q
		boolean verticale = p1.getX() == p2.getX();
		if (verticale) {
			return "x = " + p1.getX();
		}else {
			coeffAng = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
			offset = p2.getY() - coeffAng * p2.getX();
			if (coeffAng == 0) {
				return "y = " + offset; 
			}else {
				if(offset == 0) {
					return " y = " + coeffAng + " x ";
				}else {
					return " y = " + coeffAng + " x + " + offset;
				}
			}
		}
	}
	
	public boolean allineato (Punto p) {
		if(p.getY() == p.getX() * coeffAng + offset)
			return true;
		else 
			return false;
	}

}
