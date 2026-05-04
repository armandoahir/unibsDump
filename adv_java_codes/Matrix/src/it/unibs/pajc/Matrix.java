/**
 * La classe mi permetterà di creare oggetti in grado di memorizzare e gestire tramite diversi metodi matrici la cui dimensione potrà essere specificata dall'utente 
 * nel momento della creazione degli oggetti stessi.
 * 
 * 1). [2 PTI] Definire una classe MATRIX che permetta di creare matrici bidimensionali. Suggerimento: la classe dovrà avere un costruttore che
 * permetta di definire il numero di righe ed il numero di colonne della matrice che vogliamo creare
 * Nella classe definire due metodi GETEL SETEL che permettono rispettivamente rispettivamente di recuperare il valore di un elemento della matrice 
 * e impostare un certo elemento della matrice ad un valore specificato.
 * 
 * 2). [2 PTI] Nella classe precedentemente definita aggiungere un metodo RANDOMIZE() che inizializza tutti gli elementi della matrice con valori 
 * random. Fare override del metodo toSTRING() per visualizzare a video la matrice correttamente formattata.
 * 
 * 3). [3 PTI] Aggiungere un metodo MAP che permette di modificare ogni elemento della matrice secondo una funzione passata al metodo map stesso.
 * 
 * 4). [3 PTI] Creare un nuovo metodo MapMT che permette di modificare OGNI elemento della matrice tramite un filtro passato dall'esterno 
 * come nell'esempio ma applica il filtro utilizzando 4 thread in parallelo.
 */

package it.unibs.pajc;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

//Punto 1.
public class Matrix {
	private int [][] matrice;
	
	public Matrix(int righe, int colonne) {
		matrice = new int[righe][colonne];
	}
	public int getEl(int righe, int colonne) {
		return matrice[righe][colonne];
	}
	
	public void setEl(int righe, int colonne, int value) {
		matrice[righe][colonne] = value;
	}
	
	public void randomize() {
		Random rand = new Random();
		for(int i = 0; i < matrice.length; i++) {
			for(int j = 0; j < matrice[i].length; j++) {
				matrice[i][j] = rand.nextInt();
			}
		}
	}
	
	public void map(Function<Integer, Integer> funct) {
		for(int i = 0; i < matrice.length; i++) {
			for(int j = 0; j < matrice[i].length; j++) {
				matrice[i][j] = funct.apply(matrice[i][j]);
			}
		}
	}
	
	public void mapMT(Function<Integer, Integer> funct) {
		ExecutorService exe = Executors.newFixedThreadPool(4);
		for(int i = 0; i < matrice.length; i++) {
			final int finalI = i;
			exe.execute(() -> {
				for(int j = 0; j < matrice[finalI].length;j++) {
					matrice[finalI][j] = funct.apply(matrice[finalI][j]);
				}
			});
		}
		exe.shutdown();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i <matrice.length; i++) {
			for(int j = 0; j < matrice[i].length;j++) {
				sb.append(matrice[i][j] + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
