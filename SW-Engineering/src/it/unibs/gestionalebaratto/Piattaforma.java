package it.unibs.gestionalebaratto;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Piattaforma implements Serializable {
	private static final long serialVersionUID = 8226624182029046431L;

	public static Piattaforma attiva;
	
	public String piazza;
	public ArrayList<String> luoghi;
	public long fasceOrarie;
	public int scadenza;
	public int giorni;
	public boolean primaConfigurazione;
		
	private class Fascia {
        @SuppressWarnings("unused")
		public int pos;
        public long oraApertura;
        public long minutiApertura;
        public long oraChiusura;
        public long minutiChiusura;
        
        public Fascia(int oraApertura, int minutiApertura, int oraChiusura, int minutiChiusura) {
			super();
			this.oraApertura = oraApertura;
			this.minutiApertura = minutiApertura;
			this.oraChiusura = oraChiusura;
			this.minutiChiusura = minutiChiusura;
		}
        
		public Fascia() {
		}

		@Override
        public String toString() {
        	return String.format("%02d:%02d - %02d:%02d", oraApertura, minutiApertura, oraChiusura, minutiChiusura);
        }
    }
	
	public Piattaforma(String _piazza) {
		this.piazza = _piazza;
		luoghi = new ArrayList<String>();
		this.primaConfigurazione = false;
	}
	
	public Piattaforma() {
		luoghi = new ArrayList<String>();
		this.primaConfigurazione = true;
	}

	public void aggiungiLuogo(String _luogo) {
			this.luoghi.add(_luogo);
	}
	
	public int getScadenza() {
		return this.scadenza;
	}
	
	// attivazione fascia oraria tramite bitwise OR
	public void attivaFasciaOraria(int _fasciaOraria) {
		fasceOrarie |= (1 << _fasciaOraria); 
	}
	// disattivazione fascia oraria tramite bitwise NAND
	public void disattivaFasciaOraria(int _fasciaOraria) {
		fasceOrarie &= ~(1 << _fasciaOraria); 
	}
	
	public boolean switchGiorno(int _giorno) {
		if((giorni & (1 << _giorno)) != 0) {
			//Se attivo
			giorni &= ~(1 << _giorno);
		} else {
			//Se disattivo
			giorni |= (1 << _giorno);
		}
		
		selezionaGiorniMenu();
		
		return true;
	}
	
	public ArrayList<Fascia> fasceDisponibili() {
		
		ArrayList<Fascia> fasce = new ArrayList<Fascia>();
		Fascia attiva = null;
		
		for(long i = 0; i < 48; i++) {
			if((fasceOrarie & (1L << i)) != 0) {
				boolean apertura = true;
				if(i > 0) {
					if((fasceOrarie & (1L << (i-1))) !=0) {
						apertura = false;
					}
				}
				if(apertura) {
					attiva = new Fascia();
					fasce.add(attiva);
					attiva.oraApertura = i/2;
					attiva.minutiApertura = (i%2)*30;
				}
			}
			if((fasceOrarie & (1L << i)) != 0) {
				boolean chiusura = true;
				if(i < 47) {
					if((fasceOrarie & (1L << (i+1))) !=0) {
						chiusura = false;
					}
				}
				if(chiusura) {
					try {
						attiva.oraChiusura = (i+1)/2;
						attiva.minutiChiusura = ((i+1)%2)*30;
					} catch(Exception e) {
						System.out.println(e);
					}
				}
			}
		}
		
		return fasce;
	}
	
	public String fasceDisponibiliString() {
		ArrayList<Fascia> fasce = fasceDisponibili();
		StringBuilder sb = new StringBuilder();
		
		if(!fasce.isEmpty()) {
			sb.append("Fasce orarie:\n");
			for (Fascia fascia : fasce) {
				sb.append(String.format("- %s\n", fascia.toString()));
			}
		} else {
			sb.append("Nessuna fascia oraria attiva\n");
		}
		
		return sb.toString();		
	}
	
	public ElementoMenu toMenuOption() {
		assert Utente.utenteAttivo != null;
		
		if(primaConfigurazione) {
			System.out.println("PRIMA CONFIGURAZIONE PIATTAFORMA");
			System.out.print("Inserire il nome della piazza: ");
			piazza = Tastiera.leggiStringa();
			primaConfigurazione = false;
		}
		
		Piattaforma.attiva = this;
		ElementoMenu menuCategoria = new ElementoMenu("Menu piattaforma", toString(), Menu.getArr("configuratore"));
		
		menuCategoria.add(1, "Visualizza dati piattaforma", () -> toDataString());
		menuCategoria.add(2, "Attiva fascia oraria", () -> switchFasciaOrariaMenu(true));
		menuCategoria.add(3, "Disattiva fascia oraria", () -> switchFasciaOrariaMenu(false));
		menuCategoria.add(4, "Aggiungi un luogo", () -> aggiungiLuogoMenu());
		menuCategoria.add(5, "Elimina un luogo", () -> eliminaLuogoMenu());
		menuCategoria.add(6, "Attiva/Disattiva giorni", () -> selezionaGiorniMenu());

		return menuCategoria;
	}
	
	public boolean switchFasciaOrariaMenu(boolean attiva) {
		if(attiva) System.out.print("Inserire una fascia oraria da abilitare (hh:mm-HH:MM): ");
		else System.out.print("Inserire una fascia oraria da disabilitare (hh:mm-HH:MM): ");
		String fasciaString = Tastiera.leggiStringa();
		Pattern regexPattern = Pattern.compile("\\d{1,}");
		
		Matcher m = regexPattern.matcher(fasciaString);
		
		ArrayList<Long> nums = new ArrayList<Long>();
		
		for(long i=0; i<4; i++) {
			if(m.find()) {
				long num = Integer.parseInt(m.group());
				boolean errorFlag = false;
				if(i%2==0) { //Ore
					if(num < 0 || num > 23) {
						errorFlag = true;
					}
					if(i==2 && num < nums.get(0)) {
						errorFlag = true;
					}
				}
				if(i%2==1) { //Minuti
					if(num != 0 && num != 30) {
						errorFlag = true;
					}
				}
				if(errorFlag) {
					System.out.println("Fascia oraria non valida. Si ricorda che la fascia oraria puo' iniziare " + 
									   "e terminare solo sull'ora o sulla mezz'ora");
					return false;
				}
				
				nums.add(Long.parseLong(m.group()));
			} else {
				System.out.println("Orario non trovato. Assicurarsi di aver rispettato il pattern hh:mm-HH:MM");
				return false;
			}
		}
		
		for(long i = ((nums.get(0) * 2) + (nums.get(1) / 30)); i < ((nums.get(2) * 2) + (nums.get(3) / 30)); i++) {
			if(attiva) fasceOrarie |= (1L << i);
			else fasceOrarie &= ~(1L << i);
		}
			
		return true;
	}
	
	public boolean toDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("\nPiattaforma %s\n", piazza));
		
		if(!luoghi.isEmpty()) {
			sb.append("Luoghi: \n");
			for (String luogo : luoghi) {
				sb.append(String.format("- %s\n", luogo));
			}
		} else {
			sb.append("Nessun luogo inserito\n");
		}
		
		sb.append(fasceDisponibiliString());
		
		if(giorni == 0) {
			sb.append("Nessun giorno attivo\n");
		} else {
			sb.append("Giorni attivi:\n");
			for(int i = 0; i < 7; i++) {
				if((giorni & (1 << i)) != 0) {
					DayOfWeek giorno = DayOfWeek.of(i+1);
					String giornoStr = Tastiera.maiuscola(giorno.getDisplayName(TextStyle.FULL, new Locale("IT", "it")));
					sb.append(String.format("- %s\n", giornoStr));
				}
			}
		}
		
		System.out.println(sb.toString());
		Tastiera.pausa();
		return true;		
	}
	
	public boolean aggiungiLuogoMenu() {
		System.out.println("Inserire il nome del luogo da aggiungere: ");
		String luogo = Tastiera.leggiStringa();
		aggiungiLuogo(luogo);
		
		return true;
	}
	
	public boolean eliminaLuogoMenu() {
		ElementoMenu menuLuoghi = new ElementoMenu("Elenco luoghi", "Scegli un luogo da eliminare", ElementoMenu.attivo);
		
		for(int i=0; i < luoghi.size(); i++) {
			String l = luoghi.get(i);
			menuLuoghi.add(i+1, l, () -> eliminaLuogo(l));
		}
		
		Menu.vaiA(menuLuoghi);
		
		return true;
	}
	
	/*
	public void selezionaLuogoMenu() {
		ElementoMenu menuSelezioneLuogo = new ElementoMenu("Elenco luoghi", "Scegli un luogo per l'appuntamento", ElementoMenu.attivo);	

	
			for(int i=0; i < luoghi.size(); i++) {
				String l = luoghi.get(i);
				menuSelezioneLuogo.add(i+1, l, () -> selezionaLuogo(l));
		}
	
	}
	
	public void selezionaLuogo() {
	
	}

	 */

	public boolean eliminaLuogo(String luogo) {
		luoghi.remove(luogo);
		
		Menu.vaiA(this.toMenuOption());
		return true;
	}
	
	public boolean selezionaGiorniMenu() {
		ElementoMenu menuGiorni = new ElementoMenu("Elenco giorni", "Scegli un giorno per abilitarlo/disabilitarlo", this.toMenuOption());
		
		for(int i=0; i < 7; i++) {
			DayOfWeek giorno = DayOfWeek.of(i+1);
			boolean giornoAttivo = (giorni & (1 << i)) != 0;
			String giornoStr = String.format("%s%s", giornoAttivo? "(X)" : "( )", Tastiera.maiuscola(giorno.getDisplayName(TextStyle.FULL, new Locale("IT", "it"))));
			int giornoFinal = i;
			menuGiorni.add(i+1, giornoStr, () -> switchGiorno(giornoFinal));
		}
		
		Menu.vaiA(menuGiorni);
		
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%s", piazza);
	}	
}
