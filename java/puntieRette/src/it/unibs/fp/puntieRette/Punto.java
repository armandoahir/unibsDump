package it.unibs.fp.puntieRette;

public class Punto {
	private double x;
	private double y;
	
	public Punto(double _x, double _y) {
		this.x = _x;
		this.y = _y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	public double distance(Punto p1) {
		return Math.sqrt(Math.pow(p1.getX() - x, 2) + (Math.pow(p1.getY() - 2, 2)));
	}

	@Override
	public String toString() {
		return "Punto [x=" + x + ", y=" + y + "]";
	}
	
	
}
