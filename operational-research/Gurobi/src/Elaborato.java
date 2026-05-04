import java.io.*;

import gurobi.*;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;
import gurobi.GRB.StringAttr;

public class Elaborato {
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																													//
	//	N.B. per leggere da file e' necessario modificare solo il percorso dell'attributo stringa "PATHR" (Read);		//
	//		 NON modificare il file .txt originale!																		//
	//																													//
	//	N.B.2 siccome l'output e' tanto e la console lo limita, settando l'attributo "SCRIVERE" a "true" e				//
	//		  modificando l'attributo "PATHW" con il percoso del file su cui scrivere, e' possibile salvare l'output.	//
	//																													//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static String PATHR = "C:\\Users\\dary2\\Desktop\\coppia_31.txt";	// directory del file .txt [DA CUI LEGGERE]
	public final static String PATHW = "C:\\Users\\dary2\\Desktop\\console.txt";	// directory del file .txt [SU CUI SCRIVERE - il file viene creato automaticamente]
	public final static boolean SCRIVERE = false;	// se si vuole scrivere su file mettere a "true"
	
	public static final int RIGA_INIZIO_MATRICE_NEL_FILE = 3;
	public static double epsilon = 1e-6;	//tiene conto dell'errore di approssimazione del calcolatore
	
	public static final int MAGAZZINI = 38;
	public static final int CLIENTI = 80;
	static double c = 0.03;
	static int k = 12;
	static int[][] d = new int[MAGAZZINI][CLIENTI];
	static int[] r = {13000, 13000, 12000, 6000, 12000, 7000, 11000, 10000, 10000, 6000, 7000, 6000, 11000, 10000, 5000, 13000, 5000, 7000, 5000, 7000, 5000, 7000, 5000, 13000, 12000, 10000, 5000, 13000, 8000, 8000, 10000, 7000, 11000, 10000, 11000, 12000, 6000, 10000, 6000, 13000, 9000, 6000, 7000, 12000, 10000, 11000, 10000, 7000, 11000, 7000, 11000, 5000, 12000, 6000, 7000, 11000, 12000, 11000, 9000, 8000, 9000, 12000, 10000, 13000, 12000, 9000, 7000, 11000, 9000, 7000, 5000, 11000, 12000, 10000, 7000, 6000, 13000, 6000, 7000, 6000};
	static int[] s = {22000, 23000, 18000, 20000, 22000, 15000, 25000, 22000, 16000, 20000, 19000, 19000, 22000, 20000, 18000, 21000, 19000, 19000, 25000, 18000, 17000, 18000, 25000, 19000, 24000, 21000, 18000, 16000, 19000, 22000, 23000, 19000, 15000, 20000, 24000, 18000, 21000, 19000};
	static int[] alfa = {4400, 1100, 4700, 600, 3900, 500, 2900, 4700, 1500, 700, 4500, 600, 700, 2200, 1600, 4300, 3400, 1300, 1200, 4700, 3300, 4900, 2700, 2600, 800, 3300, 1800, 5000, 1000, 3400, 5000, 3600, 1700, 1500, 4500, 3600, 1200, 4600};	
	static double PERC_SFRUTTAMENTO = 50;	//soglia entro cui un magazzino e' considerato "non sfruttato"; 50 significa "50% - magazzini pieni a meta'"
	
	public static void main(String[] args) throws IOException, GRBException {

		inizializzaMatriceDaFile();		//legge da file fornito i dati e inizializza la matrice delle distanze d_ij
		
		// PRESET //
		
		GRBEnv env = new GRBEnv("environment");	// inizializza l'environment
		env.set(GRB.IntParam.OutputFlag, 0);	// toglie gli output "di default" di Gurobi dalla console
		env.set(GRB.IntParam.Presolve, 0);		// nessun pre-solver
		env.set(GRB.IntParam.Method, 0);		// metodo del simplesso primale
				
		GRBModel modello = new GRBModel(env); // crea un modello (inizialmente vuoto)
		
		// VARIABILI DEL MODELLO //
		
		GRBVar[][] x = aggiungiVariabili(modello, GRB.INTEGER, "x ");	// aggiunge variabili x_ij al modello
		GRBVar[][] y = aggiungiVariabili(modello, GRB.BINARY, "y ");	// aggiunge variabili y_ij al modello
		
		// FUNZIONE OBIETTIVO //
		
		aggiungiFunzioneObiettivo(modello, x, d, c);
		
		// VINCOLI //
		
		aggiungiVincoloQuantitaGiornaliera(modello, x, r);		// vincolo 1) [cfr. Modello.pdf]...
		
		aggiungiVincoloQuantitaSpedibile(modello, x, s);		// vincolo 2)
		
		aggiungiVincoloAlmenoUnMagazzino(modello, y);			// vincolo 3)
		
		aggiungiVincoloDistanza(modello, y, d);					// vincolo 4)
				
		aggiungiVincoloQuantitaFuoriRaggio(modello, x, y, r);	// vincolo 5)
		
		// RISOLUZIONE E STAMPA //
		
		/*
		 * questo if serve per stampare in console o su file, a seconda di come si è impostato "SCRIVERE"
		 */
		
		if(SCRIVERE) {
			
			PrintStream outFile = new PrintStream( new File(PATHW) );
			PrintStream console = System.out;
			
			try {			
				
				System.setOut(outFile);
				risolvi(modello);				// metodo che svolge tutto l'elaborato, avvalendosi di altri metodi di supporto				
				System.setOut(console);
				System.out.println("\nOutput scritto correttamente su file");
			
			} catch(Exception e) {
				
				System.err.print("Errore scrittura file");
				
			} finally {
				
				outFile.close();
				console.close();
				
			}
			
		} else
			risolvi(modello);	// metodo che svolge tutto l'elaborato, avvalendosi di altri metodi di supporto
		
		modello.dispose();
		env.dispose();
		
	}
	
	private static void risolvi(GRBModel model) throws GRBException {

		model.optimize();
		stampaEsitoOttimizzazione(model);	// stamapa a parole l'esito della soluzione (metodo opzionale, non importante)

		System.out.println("\n\nGRUPPO <31>\nComponenti: <Tiralongo> <Ahir>\n");
		
		/////////////
		//QUESITO I//
		/////////////
		
		System.out.println("QUESITO I:");
		System.out.println("funzione obiettivo = <" + model.get(GRB.DoubleAttr.ObjVal) + ">");	// stampa il valore della F.O.
		
		/*
		 *  itera per ciascuna variabile e ne stampa il nome ed il valore
		 */
		
		for (GRBVar var : model.getVars()) {
			System.out.print("<" + var.get(StringAttr.VarName) + "> = <");
			System.out.printf("%.4f>\n", var.get(DoubleAttr.X));
		}
		
		/*
		 * controllo se almeno una variabile di base e' uguale a zero, ovvero la soluzione e' degenere
		 */		
		
		System.out.print("Degenere: ");
		boolean isDegenerate = false;
		for (int i = 0; i < model.get(GRB.IntAttr.NumVars); i++) {
			
			var v = model.getVar(i);
		    if (v.get(GRB.IntAttr.VBasis) == GRB.BASIC &&			// VBasis e' un attributo associato a ciascuna variabile, che vale 0 se la variabile e' in base
		        Math.abs(v.get(GRB.DoubleAttr.X)) < epsilon) {		// si usa epsilon perche' il compilatore potrebbe avere una sensibilita' per cui il valore della var non e' esattamente uno zero "assoluto"
		        isDegenerate = true;
		        break;												// appena ne trovo anche solo una degenere (in base, = 0) esco da ciclo e setto isDegenere = true
		    }
		}
		if (isDegenerate) {
		    System.out.printf("Si\n");
		} else {
		    System.out.printf("No\n");
		}
		
		/*
		 *  l'attributo SolCount e' il numero di soluzioni dell'ottimizzazione
		 */	
		
		System.out.print("Multipla: ");
		if (model.get(GRB.IntAttr.SolCount) > 1) {
			System.out.printf("Si\n");
		} else {
			System.out.printf("No\n");
		}
		
		//////////////
		//QUESITO II//
		//////////////
		
		System.out.println("\nQUESITO II:");
		
		// 5 metodi distinti per ciascun sottoquesito del quesito 2, come richiesto da consegna
		vincoliNonAttivi(model);		
		determinaIntervalloParametro(model);	// NON svolto (metodo vuoto)
		variabiliDuale(model);
		intervalloCapacitaMagazzino(model);
		intervalloDistanzaImpiantoCliente(model);
			
		///////////////
		//QUESITO III//
		///////////////
		
		creazioneRisoluzioneNuovoModello(model);
		System.out.println("\nQUESITO III:");
		System.out.println("risparmio = <" + model.get(GRB.DoubleAttr.ObjVal) + ">");	// stampa l'attributo che contiene il valore della F.O.
		magazziniChiusi(model);
		magazziniMenoSfruttati(model);
		GRBModel rilassato = model.relax();	// funzione che restituisce il modello rilassato dell'istanza del modello su cui e' invocata (N.B. in realta' e' superfluo trattandosi gia' di un modello di PL)
		rilassato.optimize();
		System.out.println("rilassamento continuo = <" + model.get(GRB.DoubleAttr.ObjVal) + ">");	// // stampa l'attributo che contiene il valore della F.O. del rilassato (che sara' di fatto identico)
		
	}
	
	/*
	 * metodo che restituisce l'intervallo di valori che potrebbe assumere d_5,22; sfrutta gli attributi SAObjLow e SAObjUp
	 * che contengono informazioni di sensitivita' dei coefficienti in funzione obiettivo.
	 */
	
	private static void intervalloDistanzaImpiantoCliente(GRBModel model) throws GRBException {
		
		GRBVar x5_22 = model.getVarByName("x 5_22");
		
	    double lowerBound = x5_22.get(GRB.DoubleAttr.SAObjLow);
	    double upperBound = x5_22.get(GRB.DoubleAttr.SAObjLow);

	    System.out.printf("intervallo d_522 [%.4f, %.4f]\n", lowerBound/c, upperBound/c);
	}
	
	/*
	 * metodo che restituisce l'intervallo di valori che potrebbe assumere s_5; sfrutta gli attributi SARHSLow e SARHSUp
	 * che contengono informazioni di sensitivita' dei termini noti.
	 */

	private static void intervalloCapacitaMagazzino(GRBModel model) throws GRBException {
		
		GRBConstr c5 = model.getConstrByName("Quantita' spedibile dal magazzino 5");
		
	    double lowerBound = c5.get(GRB.DoubleAttr.SARHSLow);
	    double upperBound = c5.get(GRB.DoubleAttr.SARHSUp);

	    System.out.println("intervallo s_5 = [" + lowerBound + ", " + upperBound + "]");
	}
	
	/*
	 * metodo che restituisce la lista dei agazzini meno sfruttati.
	 * L'idea e': ogni magazzino esporta la quantita' x_ij per ciascun cliente i, dunque si sommano queste q.ta' per trovare la merce uscente dal magazzino j,
	 * dopodiche' si divide per la q.ta' massima stoccabile dal magazzino j e si moltiplica *100; avro' cosi ottenuto la percentuale di riempimento, che se sara'
	 * minore di "PERC_SFRUTTAMENTO" decretera' la stampa di quel magazzino nella lista visibile in console.
	 */

	private static void magazziniMenoSfruttati(GRBModel model) throws GRBException {
		System.out.print("lista magazzini meno sfruttati = [");
		int y = 0;
		
		for (int j = 0; j < MAGAZZINI; j++) {
			
			double quantitaMagJ = 0;
			
			for (int i = 0; i < CLIENTI; i++) {
				
				quantitaMagJ += model.getVarByName("x " + i + "_" + j).get(GRB.DoubleAttr.X);
				
			}
			
			double tassoRiempimento = (quantitaMagJ/s[j])*100;	//in percentuale
			
			if (tassoRiempimento < PERC_SFRUTTAMENTO) {
				
				if (y!=0) {
	        		System.out.print(", ");
	        	}
				System.out.print(j);
				y++;
				
			}
		}
		System.out.println("]");
	}
	
	/*
	 * metodo che cerca, per ciascun magazzino (a cui e' associata una variabile z_j), quelli che hanno z_j = 1 e che quindi vengono chiusi
	 */

	private static void magazziniChiusi(GRBModel model) throws GRBException {
		System.out.print("lista magazzini chiusi = [");
		int i = 0;
		for (int j = 0; j < MAGAZZINI; j++) {
			
			double z = model.getVarByName("z " + j).get(GRB.DoubleAttr.X);
			
			if (z > (1-epsilon)) {	//errore macchina: z_j = 1 sse magazzino chiuso
				
				if (i != 0) {
	        		System.out.print(", ");
	        	}
				System.out.print(j);
				i++;
				
			}
		}
		System.out.printf("]\n");
	}
	
	/*
	 * metodo che aggiunge variabili, vincoli e risolve il modello richiesto dal quesito 3
	 */

	private static void creazioneRisoluzioneNuovoModello(GRBModel model) throws GRBException {
		
		// AGGIUNTA VARIABILI z_j
		
		GRBVar[] z = new GRBVar[MAGAZZINI];	
		for (int j = 0; j < MAGAZZINI; j++) {
			
			z[j] = model.addVar(0, 1, 0, GRB.BINARY, "z " + j);
		}
		
		// NUOVA FNZ. OBIETTIVO
		
		GRBLinExpr expr = new GRBLinExpr();
		for (int j=0; j < MAGAZZINI; j++) {
			
			expr.addTerm(alfa[j], z[j]);
		}
		model.setObjective(expr, GRB.MAXIMIZE);
		
		// VINCOLO "ALMENO UN MAGAZZINO DA CHIUDERE"
		
		GRBLinExpr expr1 = new GRBLinExpr();
		for (int j = 0; j < MAGAZZINI; j++) {

			expr1.addTerm(1, z[j]);
		}
		model.addConstr(expr1, GRB.GREATER_EQUAL, 1, "Almeno un magazzino da chiudere");
		
		// VINCOLO "NON TUTTI I MAGAZZINI DA CHIUDERE"
		
		GRBLinExpr expr2 = new GRBLinExpr();
		for (int j = 0; j < MAGAZZINI; j++) {

			expr2.addTerm(1, z[j]);
		}
		model.addConstr(expr2, GRB.LESS_EQUAL, MAGAZZINI-1, "Non tutti i magazzini da chiudere");
		
		// RISOLUZIONE NUOVO MODELLO
		
		model.optimize();
		//stampaEsitoOttimizzazione(model);
	}
	
	/*
	 * metodo che ricava il valore delle variabili duali: per ciascun vincolo ricava il valore della variabile duale associata tramite l'attributo Pi (che si puo' usare solo per vincoli continui)
	 */

	private static void variabiliDuale(GRBModel model) throws GRBException {
		
		System.out.println("Soluzione duale:");
		
		GRBConstr[] vinc = model.getConstrs();
		
		for (int i = 0; i < vinc.length; i++) {
			
			int j = i;
			System.out.printf("<lambda%d> = <%.4f>\n", ++j, vinc[i].get(GRB.DoubleAttr.Pi));			
		}
	}

	private static void determinaIntervalloParametro(GRBModel model) throws GRBException {}
	
	/*
	 * metodo che cerca, per ciascun vincolo, le slack che non sono nulle [cfr. Relazione]
	 */

	private static void vincoliNonAttivi(GRBModel model) throws GRBException {
		
		    GRBConstr[] vinc = model.getConstrs();
		    System.out.print("lista vincoli non attivi = [");
		    int i = 0;
		    
		    for (GRBConstr v : vinc) {
		    	
		        double slack = v.get(GRB.DoubleAttr.Slack);	     
		        
		        if (slack > epsilon) {	// valore slack > errore di macchina (circa 0)
		        	if (i != 0) {			// questo if serve per la presentazione: stampa la virgola solo se un oggetto deve essere stampato, tranne se e' il primo (logica lazy, mette la virgola al precedente solo se sta per essere stampato il seguente)
		        		System.out.print(", ");
		        	}
		            System.out.printf("<" + v.get(GRB.StringAttr.ConstrName) + ">");
		            i++;
		        }
		        
		    }
		    
		    System.out.printf("]\n");

	}
	
	private static void stampaEsitoOttimizzazione(GRBModel model) throws GRBException {
		
		System.out.printf("\nEsito ottimizzazione:\n");	
		switch( model.get(IntAttr.Status) ) {
		
			case 2: System.out.printf("-> Soluzione OTTIMA"); break;
			case 3: System.out.printf("-> Soluzione IRREALIZZABILE"); break;
			case 9: System.out.printf("-> Errore limite di tempo"); break;
			case 13: System.out.printf("-> Soluzione SUB-OTTIMA"); break;
			default: System.out.printf("-> Errore: consulta la pagina valori dell'attributo di modello \"Status\""); break;
			
		}
	}
	
	/*
	 * metodo che aggiunge il vincolo 5)
	 */
	
	private static void aggiungiVincoloQuantitaFuoriRaggio(GRBModel model, GRBVar[][] x, GRBVar[][] y, int[] r)
			throws GRBException {
		
		for (int i = 0; i < CLIENTI; i++) {
		
			for (int j = 0; j < MAGAZZINI; j++) {
			
				GRBLinExpr expr = new GRBLinExpr();
				expr.addTerm(1.0, x[i][j]);
				expr.addTerm(-r[i], y[i][j]);
	        
				model.addConstr(expr, GRB.LESS_EQUAL, 0, "Spedibile a cliente " + i + " da magazzino " + j);
			}
		}
	}
	
	/*
	 * metodo che aggiunge il vincolo 4)
	 */
	
	private static void aggiungiVincoloDistanza(GRBModel model, GRBVar[][] y, int[][] d)
			throws GRBException {
		
		for (int i = 0; i < CLIENTI; i++) {
		
			for (int j = 0; j < MAGAZZINI; j++) {
	    	
				GRBLinExpr expr = new GRBLinExpr();
				expr.addTerm(1.0, y[i][j]);
	    	
				if (d[j][i] > k) {
					model.addConstr(expr, GRB.EQUAL, 0, "Cliente " + i + " non servibile dal magazzino " + j);
				}
			}
		}
	}
	
	/*
	 * metodo che aggiunge il vincolo 3)
	 */
	
	private static void aggiungiVincoloAlmenoUnMagazzino(GRBModel model, GRBVar[][] y)
			throws GRBException {

		for (int i = 0; i < CLIENTI; i++) {

			GRBLinExpr expr = new GRBLinExpr();

			for (int j = 0; j < MAGAZZINI; j++) {

				expr.addTerm(1, y[i][j]);
			}

			model.addConstr(expr, GRB.GREATER_EQUAL, 1, "Magazzino serve il cliente " + i);		//DIFFERENZA TRA = E >=
		}
	}
	
	/*
	 * metodo che aggiunge il vincolo 2)
	 */
	
	private static void aggiungiVincoloQuantitaSpedibile(GRBModel model, GRBVar[][] x, int[] s)
			throws GRBException {

		for (int j = 0; j < MAGAZZINI; j++) {

			GRBLinExpr expr = new GRBLinExpr();

			for (int i = 0; i < CLIENTI; i++) {

				expr.addTerm(1, x[i][j]);
			}

			model.addConstr(expr, GRB.LESS_EQUAL, s[j], "Quantita' spedibile dal magazzino " + j);
		}
	}
	
	/*
	 * metodo che aggiunge il vincolo 1)
	 */
	
	private static void aggiungiVincoloQuantitaGiornaliera(GRBModel model, GRBVar[][] x, int[] r)
			throws GRBException {

		for (int i = 0; i < CLIENTI; i++) {

			GRBLinExpr expr = new GRBLinExpr();

			for (int j = 0; j < MAGAZZINI; j++) {

				expr.addTerm(1, x[i][j]);
			}

			model.addConstr(expr, GRB.EQUAL, r[i], "Quantita' che deve ricevere giornalmente il cliente " + i);
		}
	}
	
	/*
	 * metodo che aggiunge aggiunge la funzione obiettivo
	 */
	
	private static void aggiungiFunzioneObiettivo(GRBModel model, GRBVar[][] x, int[][] d, double c) throws GRBException {
		
		GRBLinExpr expr = new GRBLinExpr();
		
		for (int i=0; i<CLIENTI; i++) {
			
			for (int j=0; j<MAGAZZINI; j++) {
				
				expr.addTerm(c*d[j][i], x[i][j]);	//c*d_ij*x_ij
				
			}
		}
		
		model.setObjective(expr, GRB.MINIMIZE);
		
	}
	
	/*
	 * metodo che aggiunge variabili al modello [cfr. Modello.pdf]
	 */
	
	private static GRBVar[][] aggiungiVariabili(GRBModel model, char type, String name) throws GRBException {
		GRBVar[][] xij = new GRBVar[CLIENTI][MAGAZZINI];

		for (int i = 0; i < CLIENTI; i++) {
			
			for (int j = 0; j < MAGAZZINI; j++) {
				
				// N.B. le variabili sono tutte continue, viene solo limitato l'intervallo dei valori assumibili;
				//		il risultato puo' essere approssimato perche' non intero, tuttavia cio' non avviene perche' vi sono vincoli che impediscono,
				//		ad esempio, alle variabili binarie di assumere valori diversi da zero o uno;
				//		Tutto questo perche' e' vantaggioso avere un problema di PL piuttosto che PLI per una serie di motivi.
				
				if (type == GRB.INTEGER)				
					xij[i][j] = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, name + i + "_" + j);
				
				else if (type == GRB.BINARY)
					xij[i][j] = model.addVar(0, 1, 0, GRB.CONTINUOUS, name + i + "_" + j);
				
				else
					System.err.print("Errore: la variabile che si vuole aggiungere e' di un tipo non pertinente al modello assegnato");
					
			}
		}
		
		return xij;
	}

	/*
	 * metodo che legge la matrice delle distanze sfruttando gli Stream (semplice I/O Java, non correlato a Gurobi)
	 */
	
	private static void inizializzaMatriceDaFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(PATHR));
        try {
        	
            String line;
            
            for (int m = 0; m < RIGA_INIZIO_MATRICE_NEL_FILE; m++)
            	br.readLine();
            
            int i = 0;
            while ((line = br.readLine()) != null && i < MAGAZZINI) {
            	
                String[] values = line.split(" ");
                
                for (int j = 0; j < CLIENTI && j < values.length; j++) {
                	
                    d[i][j] = Integer.parseInt(values[j].trim());
                }
                
                i++;
            }
            
		} catch (Exception e) {
			
			System.err.println("Errore legato alla lettura del file");
			
		} finally {
			
			br.close();
			
		}
	}
}