package it.unibs.fp.TourCiclistico;
import java.util.*;

public class Tour {
	private String nome;
	private int anno;
	private String organizzatore;
	private String vincitore;
	private ArrayList<Tappa> elencoTappe;
	
	public Tour(String _nome, int _anno, String _organizzatore) {
		elencoTappe = new ArrayList<Tappa>();
		this.nome = _nome;
		this.anno = _anno;
		this.organizzatore = _organizzatore;
	}
	
	public ArrayList<Tappa> getTappe() {
		return elencoTappe;
	}
	
	public void eliminaTappa(Tappa _tappa) {
		for(Tappa i: elencoTappe) {
			if((i.getCittà_partenza().equalsIgnoreCase(_tappa.getCittà_partenza()) && (i.getCittà_arrivo().equalsIgnoreCase(_tappa.getCittà_arrivo()))))
				elencoTappe.remove(i);
		}
	}

	public void showTappe() {
		System.out.println(elencoTappe.toString());
	}
	
	public boolean aggiungiTappa(Tappa _nuovaTappa) {
		boolean flag = false;

		 if(! elencoTappe.contains(_nuovaTappa)) {
		 flag = true;
		 elencoTappe.add(_nuovaTappa);
		}
		return flag;
	}

	/**
	 * Getters e setters generati
	 * @return Getters and setters
	 */
	public String getVincitore() {
		return vincitore;
	}

	public void setVincitore(String vincitore) {
		this.vincitore = vincitore;
	}

	public String getNome() {
		return nome;
	}


	@Override
	public String toString() {
		return  "TOUR: " + this.nome + " - " + this.anno + "\n" + 
				"Organizzato da: " + this.organizzatore + "\n" + 
				this.elencoTappe.toString() + "\n" + "VINCITORE: " + this.vincitore;
	}
	
}
