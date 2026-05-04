package it.unibs.gestionalebaratto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Memoria implements Serializable {

	private static final long serialVersionUID = 67053L;
	private static FileInputStream fileIn;
	private static ObjectInputStream objIn;
	
	private static FileOutputStream fileOut;
	private static ObjectOutputStream objOut;
	
	private static String percorso;
	
	public static Memoria m;
	
	private static ObjectMapper mapper;
	
	private ArrayList<Categoria> radici;
	private ArrayList<Utente> listaUtenti;
	private Piattaforma piattaforma;
	private ArrayList<Articolo> articoli;
	private ArrayList<Pubblicazione> pubblicazioni;
	private ArrayList<Appuntamento> appuntamenti;

	
	/**
	 * Istanzia un nuovo elemento memoria e lo inizializza per la lettura delle informazioni.
	 */
	public Memoria() {
		this.radici = new ArrayList<Categoria>();
		this.listaUtenti = new ArrayList<Utente>();
		this.piattaforma = new Piattaforma();
		this.articoli = new ArrayList<Articolo>();
		this.pubblicazioni = new ArrayList<Pubblicazione>();
		this.appuntamenti = new ArrayList<Appuntamento>();
	}

	/**
	 * Inizializza la memoria del programma, caricandola da file o creando un nuovo file vuoto.
	 *
	 * @param percorso percorso del file
	 */
	public static void Init(String percorso) {
		Memoria.percorso = percorso;
		try {
			fileIn = new FileInputStream(new File(percorso));
			objIn = new ObjectInputStream(fileIn);
			
			m = (Memoria) objIn.readObject();
			
			if(m == null) {
				System.out.println("File di salvataggio non esistente!");
				Tastiera.pausa();
				m = new Memoria();
			}
			
			//Verifica se tutti gli oggetti esistono in memoria
			if(m.radici == null) {
				m.radici = new ArrayList<Categoria>();
			}
			if(m.listaUtenti == null) {
				m.listaUtenti = new ArrayList<Utente>();
			}
			if(m.piattaforma == null) {
				m.piattaforma = new Piattaforma();
			}
			if(m.articoli == null) {
				m.articoli = new ArrayList<Articolo>();
			}
			if(m.pubblicazioni == null) {
				m.pubblicazioni = new ArrayList<Pubblicazione>();
			}
			if(m.appuntamenti == null) {
				m.appuntamenti = new ArrayList<Appuntamento>();
			}
			
			objIn.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			System.out.println("File di salvataggio non esistente!");
			m = new Memoria();
		} catch (IOException|NullPointerException e) {
			System.out.println("IO o NullPointer:");
			e.printStackTrace();
			m = new Memoria();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound");
			m = new Memoria();
		}
		
		Categoria.caricaMemoria(m.radici);
		Utente.caricaMemoria(m.listaUtenti);
		Piattaforma.attiva = m.piattaforma;
		Articolo.caricaMemoria(m.articoli);
		Pubblicazione.caricaMemoria(m.pubblicazioni);
		Appuntamento.caricaMemoria(m.appuntamenti);
		
		
		
		try {
			fileOut = new FileOutputStream(new File(percorso));
			objOut = new ObjectOutputStream(fileOut);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean ImportPiattaforma() {
		if(mapper == null) mapper = new ObjectMapper();
		
		try {
			Piattaforma p = mapper.readValue(new File("piattaforma.json"), Piattaforma.class);
			
			this.piattaforma = p;
			Piattaforma.attiva = m.piattaforma;
			
			return true;
		} catch (StreamReadException e) {
			System.out.println("Errore nella formattazione del file");
			e.printStackTrace();
			return false;
		} catch (DatabindException e) {
			System.out.println("Errore nella formattazione del file");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean ImportCategorie() {
		if(mapper == null) mapper = new ObjectMapper();
		
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Categoria> cat = mapper.readValue(new File("categorie.json"), new TypeReference<ArrayList<Categoria>>() {});
			
			this.radici = cat;
			Categoria.caricaMemoria(m.radici);
			return true;
		} catch (StreamReadException e) {
			System.out.println("Errore nella formattazione del file");
			e.printStackTrace();
			return false;
		} catch (DatabindException e) {
			System.out.println("Errore nella formattazione del file");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Errore nella lettura del file");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Salva le informazioni del programma nel file di memorizzazione.
	 */
	public static void Save() {
		try {
			fileOut = new FileOutputStream(new File(Memoria.percorso));
			objOut = new ObjectOutputStream(fileOut);
			
			objOut.writeObject(m);

			objOut.flush();
			fileOut.flush();
			
			objOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {}
	}
	
	/**
	 * Chiude il file di memorizzazione.
	 */
	public static void Close() {
		try {
			objOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
