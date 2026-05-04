package it.unibs.fp.cuoriSolitari;

public class Coppia {
	private final static int MAX_DIFF_AGE = 5;
	
	public static int calcAge (Persona p1, Persona p2) {
		return Math.abs(p1.getAGE() - p2.getAGE());
	}
	
	public static boolean affinity (Persona p1, Persona p2) {
		
		if(calcAge(p1,p2) <= MAX_DIFF_AGE) {
			if(p1.getPREFERENCE() == p2.getSEX() && p2.getPREFERENCE() == p1.getSEX()) {	
				return true;
			}else return false; 
		}else return false;		
	}

}
