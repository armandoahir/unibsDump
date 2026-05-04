/**
 * Definiamo un numero felice tramite il seguente processo: 
 * dato un qualsiasi intero positivo si calcola la somma dei quadrati delle sue cifre. 
 * Se tale somma è pari ad 1, il numero è definito un numero felice, 
 * altrimenti si riapplica il processo al nuovo numero finche o si ottiene 1 oppure si entra in un ciclo che non include mai 1.
 * 
 * Si crei una classe Java NumeriFelici che implementa le seguenti funzionalità: 
 * 
 * 1. Un metodo pubblico isFelice(int k) che passato un intero positivo restituisce true se il numero passato è un numero felice e false in caso contrario (5 punti)
 * 2. Un metodo pubblico contaFelici(int k) che restituisce il numero di numeri felici compresi tra 0 ed il numero k incluso. (2 punti)
 * 3. Un metodo pubblico velocitaFelice(int k) che restituisce -1 se il numero passato non è felice oppure il numero di iterazioni necessarie a determinare che il numero è
 * felice, nel caso del numero 7 il numero restituito sarebbe 5. (3 punti)
 * 
 * @author Armando A.
 */
package it.unibs.pajc;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
	private static String TITLE = "NUMERI FELICI";

	public static boolean isFelice(int k) {
		return velocitaFelici(k) > 0;
	}
	
	public static int contaFelici(int k) {
		int n = 0;
		for(int i = 0; i <= k; i++) {
			if(isFelice(i)) n++;
		}
		return n;
	}
	
	public static int velocitaFelici(int k) {
		if(k <= 0) return -1;
		
		HashSet<Integer> salvaNumeri = new HashSet<>();
		salvaNumeri.add(k);
		int n = k;
		while(true) {
			int somma = 0, u;
			while(n > 0) {
				u = n % 10;
				somma += u*u;
				n = n / 10;
			}
			
			if(somma == 1) return salvaNumeri.size();
			if(salvaNumeri.contains(somma)) return -1;
			n = somma;
			salvaNumeri.add(n);
		}
	}
	
	public static void main(String[] args) {

		System.out.println(TITLE); 
		Scanner sc = new Scanner(System.in);
		int input;
		do {
			System.out.print("Numero --> ");
			input = sc.nextInt();
			System.out.println("\n");
			System.out.printf("Numero %d\n ",input);
			System.out.println("Risultato felice: " + isFelice(input));
			System.out.println("Risultato contaFelici: " + contaFelici(input));
			System.out.println("Risultato velocità felice: " + velocitaFelici(input));
			System.out.println("\n");
	} while(input != 0);
		
		System.out.println("Goodbye!");
		sc.close();
	}
}
