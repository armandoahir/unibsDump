package it.unibs.fp.Magazzino;
import java.util.ArrayList;


public class Magazzino {
	
	private ArrayList<Articolo> elencoArticoli;
	private int counter = 0;

	public Magazzino(ArrayList<Articolo> _elencoArticoli) {

		this.elencoArticoli = _elencoArticoli;
	}
	
	public Magazzino() {
		
	}

	public ArrayList<Articolo> getElencoArticoli() {
		return elencoArticoli;
	}
	
	  public void aggiungiArticolo(Articolo _articolo) {
		  
		  this.elencoArticoli.add(_articolo);
		  this.counter++;
	  }
	  
	  public boolean evitaOmonimia(Articolo _articolo)
		{
			int i;

			for(i = 0; i < this.counter; i++)
				if(_articolo.getNome().equalsIgnoreCase(this.elencoArticoli.get(i).getNome()))
					return true;
			
			return false;
		}

	public void showElenco() {
		System.out.println(elencoArticoli.toString());
	}
	
	public boolean checkArticolo(String nome) {
		for(int i = 0; i < this.counter; i++) {
			if(nome.equalsIgnoreCase(elencoArticoli.get(i).getNome()))
				return true;
		}
		return false;
	}
	
	public void checkQuantità(Articolo _articolo) {
		
		for(int i = 0; i < elencoArticoli.size(); i++) {
			
			if(checkArticolo(_articolo.getNome())) {
				
				if(_articolo.getQuantità() < elencoArticoli.get(i).getQuantità()) {
					
					elencoArticoli.get(i).scalaQuantità(_articolo.getQuantità());
				} else {
					int giorni = _articolo.getQuantità() * elencoArticoli.get(i).getPeriodo();
					System.out.printf("L'ordine sarà disponibile tra %d giorni lavorativi \n", giorni);	
					
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Magazzino [ARTICOLI: \n" + elencoArticoli.toString() + "\n" + "]";
	}
}
