package it.unibs.gestionalebaratto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unibs.gestionalebaratto.Pubblicazione.StatoPubblicazione;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;


public class Appuntamento implements Serializable {
	private static final long serialVersionUID = -1723723250869816413L;
	public static ArrayList<Appuntamento> appuntamenti;
	public static Appuntamento attivo;
	public Pubblicazione pubblicazione1;
	public Pubblicazione pubblicazione2;
	private String luogo;
	private LocalDateTime dataOraAppuntamento; 
	private LocalDateTime dataOraUltimaModifica;
	private Utente ultimaProposta;
;
	
	public Appuntamento(Pubblicazione pubblicazione1, Pubblicazione pubblicazione2, String luogo) {
		this.pubblicazione1 = pubblicazione1;
		this.pubblicazione2 = pubblicazione2;
		this.luogo = luogo;
	}
	
	public String getLuogo() {
		return luogo;
	}

	public LocalDateTime getDataOraAppuntamento() {
		return dataOraAppuntamento;
	}
	
	private String getDatiAppuntamento() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nData e ora appuntamento: ");
		sb.append(dataOraAppuntamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		sb.append("\nLuogo dell'appuntamento: ");
		sb.append(luogo);
		
		return sb.toString();
		
	}
	
	private boolean getDatiAppuntamentoMenu() {
		System.out.println(getDatiAppuntamento());
		
		return true;
	}
	
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public void setDataOraAppuntamento(int anno, int mese, int giorno, int ora, int minuti) {
		this.dataOraAppuntamento = LocalDateTime.of(anno, mese, giorno, ora, minuti);
	}
	

	public LocalDateTime getDataOraUltimaModifica() {
		return dataOraUltimaModifica;
	}

	public void setUltimaModifica() {
		dataOraUltimaModifica = LocalDateTime.now();
	}
	
	
	public boolean controlloScadenza(LocalDateTime d) {
		
		int scadenza = Piattaforma.attiva.getScadenza();
		LocalDateTime oggi = LocalDateTime.now();
		
		long scarto = ChronoUnit.DAYS.between(oggi, d);
		
		if(scarto <= scadenza) 
			return true;
		return false;
		
	}
	
	public static boolean nuovoCollegamento(Pubblicazione a, Pubblicazione b) {
		a.setStato(StatoPubblicazione.OFFERTA_ACCOPPIATA);
		b.setStato(StatoPubblicazione.OFFERTA_SELEZIONATA);
		
		Appuntamento app =  new Appuntamento(a, b, null);
		
		app.setUltimaModifica();
		appuntamenti.add(app);
		Appuntamento.attivo = app;
		
		System.out.println("Proposta inserita, attendi l'orario comunicato dall'utente");
		
		Menu.vaiA("fruitore");
		
		return true;
	}
	
	//utilizzo in questi due casi:
	//l'autore di pubblicazione2, se accetta la proposta entro la scadenza, deve proporre appuntamento
	//l'autore di pubblicazione1, se NON accetta la proposta di appuntamento, deve prorporne un altro
	
	public boolean proponiAppuntamentoFase1() {
		if(controlloScadenza(this.dataOraUltimaModifica)) {
		ElementoMenu appuntamentoLuogo = new ElementoMenu("Nuovo appuntamento", "Seleziona luogo tra quelli proposti", ElementoMenu.attivo);
		int i = 1;
		Piattaforma.attiva.toDataString();
		for (String luogo : Piattaforma.attiva.luoghi) {
			appuntamentoLuogo.add(i++, luogo, () -> proponiAppuntamentoFase2(luogo));
		}
		
		Menu.vaiA(appuntamentoLuogo);
		return true;
		} else {
			//se la scadenza è ormai superata tornano allo stato iniziale 
			cancellaAppuntamento();
			System.out.println("Scadenza superata, non è possibile proporre appuntamento");
			return false;
		}
	}
	
	public boolean proponiAppuntamentoFase2(String luogo) {
				
			if(controlloScadenza(this.dataOraUltimaModifica)) {
					//luogo da fare in stile menu
					this.setLuogo(luogo);
					
					boolean checkData = false;
					boolean checkOrario = false; 
					String dataString = null;
					String orarioString = null;
					
					do {
						dataString = richiestaData();
						checkData = controlloData(dataString);
						
					}while(!checkData);
						
					
					if(checkData) {
						do {
							orarioString = richiestaOrario();
							checkOrario = controlloOra(orarioString);
							
						}while(!checkOrario);
						
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY);
						LocalDate data = LocalDate.parse(dataString, formatter);
						
						LocalTime ora = LocalTime.parse(orarioString);	
							
						this.setDataOraAppuntamento(data.getYear(), data.getMonthValue(), data.getDayOfMonth(), ora.getHour(), ora.getMinute());
						
						this.setOfferteInScambio();
						this.setUltimaModifica();
						this.ultimaProposta = Utente.utenteAttivo;
					}
					
					appuntamentoMenu();
				
				}else {
					
					//se la scadenza è ormai superata tornano allo stato iniziale 
					cancellaAppuntamento();

					System.out.println("Scadenza superata, non è possibile proporre appuntamento");
			}
			return true;
	}
	
	public String richiestaData() {
		System.out.println("Inserire data appuntamento [gg/mm/aaaa]: ");
		String dataString = Tastiera.leggiStringa();
		return dataString;
		
	}
	
	public String richiestaOrario() {
		Piattaforma.attiva.fasceDisponibiliString();
		System.out.println("Inserisci orario tra quelli proposti [hh:mm]");
		String orario = Tastiera.leggiStringa();
		return orario;
	}
	
	public boolean controlloData(String dataString) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALY);
			Calendar c = Calendar.getInstance();
		
			if(dataString.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
			
				LocalDate dataCorretta = LocalDate.parse(dataString, formatter);
				LocalDate oggi = LocalDate.now();
				
				boolean giornoFuturo = false;
				
				if(dataCorretta.isAfter(oggi)) {
					giornoFuturo = true;
				}
				
				if(giornoFuturo) {					
					DayOfWeek giornoSettimana = dataCorretta.getDayOfWeek();

					
					if( ((Piattaforma.attiva.giorni  >> (giornoSettimana.getValue()-1)) & 1) == 1 ) {
						return true;
					}
				}	
			}
		return false;
	}
		

	public boolean controlloOra(String orario) {
		
		Pattern regexPattern = Pattern.compile("\\d{1,}");
		Matcher m = regexPattern.matcher(orario);
		ArrayList<Long> nums = new ArrayList<Long>();
		
		for(long i=0; i<2; i++) {
			if(m.find()) {
				long num = Integer.parseInt(m.group());
				boolean errorFlag = false;
				if(i==0) { //Ore
					if(num < 0 || num > 23) {
						errorFlag = true;
					}
				}
				if(i==1) { //Minuti
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
				System.out.println("Orario non trovato. Assicurarsi di aver rispettato il pattern hh:mm");
				return false;
			}
		}
		
		long maskVerifica = 1L << ((nums.get(0) * 2L) + (nums.get(1)/30L));
		
		if((Piattaforma.attiva.fasceOrarie & maskVerifica) != 0L) {
			//Fascia valida
			return true;
		}
		
		return false;
		
	}


	public void setOfferteInScambio() {
		pubblicazione1.setStato(StatoPubblicazione.OFFERTA_IN_SCAMBIO);
		pubblicazione2.setStato(StatoPubblicazione.OFFERTA_IN_SCAMBIO);
	}
	
	public boolean accettaAppuntamento() {
		pubblicazione1.setStato(StatoPubblicazione.OFFERTA_CHIUSA);
		pubblicazione2.setStato(StatoPubblicazione.OFFERTA_CHIUSA);
		
		this.setUltimaModifica();
		
		return true;
		
	}
	
	public void cancellaAppuntamento() {
		pubblicazione1.setStato(StatoPubblicazione.OFFERTA_APERTA);
		pubblicazione2.setStato(StatoPubblicazione.OFFERTA_APERTA);

		appuntamenti.remove(this);
		
	}
	
	public void dettagliAppuntamento() {
		
		
		
	}
	
	public boolean isUltimoUtente() {
		return this.ultimaProposta == Utente.utenteAttivo;
	}
	
	public boolean appuntamentoMenu() {
		boolean valido = controlloScadenza(this.dataOraUltimaModifica);
		
		String testoAppuntamento = pubblicazione1.getArticolo().getDescrizione() + " - " + pubblicazione2.getArticolo().getDescrizione();
		if(!valido) {
			testoAppuntamento += " (SCADUTO!)";
		}
		

		ElementoMenu menuApp = new ElementoMenu("VISUALIZZA APPUNTAMENTO", testoAppuntamento, ElementoMenu.attivo);
		int i = 1;
		if(valido) {
			//Caso prima proposta da utente 2 offerta selezionata
			if(pubblicazione2.getArticolo().getProprietario() == Utente.utenteAttivo && pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_SELEZIONATA) {
				menuApp.add(i++, "Inserisci la prima proposta", ()-> proponiAppuntamentoFase1());
			}
			if(pubblicazione1.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO && pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO) {
				menuApp.add(i++, "Visualizza dettagli", () -> getDatiAppuntamentoMenu());
			}
			
			if(pubblicazione1.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO && pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO && !isUltimoUtente()) {
				menuApp.add(i++, "Inserisci controproposta", () -> proponiAppuntamentoFase1());
				menuApp.add(i++, "Accetta la proposta", () -> accettaAppuntamento());
			}
		}
		
		Menu.vaiA(menuApp);
		
		return true;
		
	}
	
	//eseguo quando si apre menu per gestire appuntamenti?
	
	public void controllaScadenzaTuttiAppuntamenti(){
		for(Appuntamento app : appuntamenti) {
			
			if(app.pubblicazione1.getStato()==StatoPubblicazione.OFFERTA_ACCOPPIATA && 
				app.pubblicazione2.getStato()==StatoPubblicazione.OFFERTA_SELEZIONATA) {
				
				if(!controlloScadenza(dataOraUltimaModifica)) {
					cancellaAppuntamento();
				}
			
			}else if(app.pubblicazione1.getStato()== StatoPubblicazione.OFFERTA_IN_SCAMBIO &&
					app.pubblicazione2.getStato() == StatoPubblicazione.OFFERTA_IN_SCAMBIO) {
					
				if(!controlloScadenza(dataOraUltimaModifica)) {
					cancellaAppuntamento();
				}
			}
		}
	}
	
	public static void caricaMemoria(ArrayList<Appuntamento> memoria) {
		Appuntamento.appuntamenti = memoria;
	}
	
}
