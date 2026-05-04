package it.unibs.fp.TourCiclistico;

public class Tappa {
	
	private int km;
	private String data;
	private String città_partenza;
	private String città_arrivo;
	private int indice_altimetrico;
	
	
	
	public Tappa(String _città_partenza, String _città_arrivo, String _data, int _indice_altimetrico) {
		
		this.città_partenza = _città_partenza;
		this.città_arrivo = _città_arrivo;
		this.data =_data;
		this.indice_altimetrico = _indice_altimetrico;
		}
	
public Tappa() {

		}


	public int getKm() {
		return km;
	}

	public void setKm(int km) {
		this.km = km;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCittà_partenza() {
		return città_partenza;
	}

	public void setCittà_partenza(String città_partenza) {
		this.città_partenza = città_partenza;
	}

	public String getCittà_arrivo() {
		return città_arrivo;
	}

	public void setCittà_arrivo(String città_arrivo) {
		this.città_arrivo = città_arrivo;
	}
	



	public void setIndice_altimetrico(int indice_altimetrico) {
		this.indice_altimetrico = indice_altimetrico;
	}

	@Override
	public String toString() {
		return "Tappa: " + this.data + " " + this.città_partenza + " - " + città_arrivo + " (" + km + " km ) " +  " " + indice_altimetrico + "  Mt. altitudine" + "\n";
	}

}
