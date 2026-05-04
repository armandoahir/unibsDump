package it.unibs.fp.PolveriSottivi;
import java.util.ArrayList;

/**
 * I valori di soglia (threshold) sono stati scelti seguento l'esempio dell'esercizio
 * @author Ahir Armando
 *
 */
public class Week {
	
	private static final double MAX_THRESHOLD = 70.00;
	private static final double AVERAGE_THRESHOLD = 50.00;
	
	private ArrayList <Double> measures;
	private int year;
	private int weekday;

	public Week(ArrayList <Double> _measures, int _weekday, int _year) {
		this.measures = _measures;
		this.weekday = _weekday;
		this.year = _year;
	}
	
	/**
	 * averageWarning() e maxWarning().
	 * Metodi di controllo sui dati immessi, per allarmare l'utente se un dato immesso, in un determinato 
	 * giorno della settimana, sia oltre la soglia rispettivamente del valore medio e del valore massimo.
	 * @return boolean type true or false
	 */
	
	public boolean averageWarning() {
		double average = 0;
		for( int i = 0; i < measures.size(); i++) {
			average += measures.get(i);
		}
		average = average / measures.size();
		if(average > AVERAGE_THRESHOLD) 
			return true;
		else return false;
	}

	public boolean maxWarning() {
		
		double max = 0;
		for(int i = 0; i < measures.size(); i++) {
			if(max <= measures.get(i)) {
				max = measures.get(i);
			}
		}
		
		if(max > MAX_THRESHOLD) 
			return true;
		else return false;
	}

	/**
	 * getAverage() e getMax()
	 * Metodo utilizzati rispettivamente per la visione della media settimanale e del valore massimo 
	 * acquisito in una determinata settimana
	 * @return average and max value
	 */
	
	public double getAverage() {
		double average = 0;
		for( int i = 0; i < measures.size(); i++) {
			average += measures.get(i);
		}
		average = average / measures.size();
		return average;
	}

	public double getMax() {
		double max = 0;
		for(int i = 0; i < measures.size(); i++) {
			if(max < measures.get(i))
				max = measures.get(i);
		}
		return max;
	}
	
	/**
	 * 
	 * @return Getters
	 */
	public ArrayList<Double> getMeasures() {
		return measures;
	}

	public int getYear() {
		return year;
	}

	public int getWeekday() {
		return weekday;
	}

	@Override
	public String toString() {
		return "Week " + weekday + " of year " + year + ":\n\tWeekly measures: " + getMeasures() +
				"\n\t" + "Average: " + getAverage() + " --> AVERAGE THRESHOLD EXCEEDED: " + averageWarning() 
				+"\n\tMax value: " + getMax() + " --> MAX THRESHOLD EXCEEDED: " + maxWarning() + "\n";
	}
}
