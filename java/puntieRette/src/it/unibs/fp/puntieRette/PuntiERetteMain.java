package it.unibs.fp.puntieRette;
import it.unibs.fp.mylib.*;
/**
 * @author Ahir Armando
 */

public class PuntiERetteMain {
	public static Punto creaPunto (String messaggio) {
		double x = InputDati.leggiDouble(messaggio + " x:");
		double y = InputDati.leggiDouble(messaggio + " y:");
		return new Punto(x,y);
	}
	
	public static void main(String[] args) {
		Punto p1 = creaPunto("Punto 1");
		Punto p2 = creaPunto("Punto 2");
		
		System.out.println("Punto 1" + p1);
		System.out.println("Punto 2" + p2);
		System.out.println("Distanza:" + p1.distance(p2));
		
		Retta retta = new Retta(p1,p2);
		System.out.println(retta.equazione());
		
		Punto p3 = creaPunto("Punto 3");
		System.out.println("Il punto è allineato? " +retta.allineato(p3));
		
	}

}
