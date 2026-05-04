/**
 * Si sviluppi un programma in linguaggio Java che implementa un server TCP/IP: 
 * 1. creazione di un server che accetta in ingresso una connessione sulla porta 1234.
 * 2. inviando un ‘comando’ tramite telnet al server lo stesso risponde con lo stesso comando in lettere maiuscole.
 * 3. il server scrive tutti i comandi ricevuti in un file locale chiamato ‘log.txt’ in formato: <data corrente>:<comando ricevuto>.
 * 4. modificare il server in modo che possa accettare più connessioni contemporanee in ingresso.
 * @author Ahir A. 
 */
package it.unibs.pajc;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(1234);
		System.out.println("In attesa di client...");
		
		while(true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connesso...");
			
			new Thread(() -> {
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
					
					while(true) {
						String command = in.readLine();
						if(command == null) break;
						
						out.println(command.toUpperCase());
						logCommand(command); 
						}
					
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				finally {
					
					try {
						clientSocket.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	public static void logCommand(String command) {
		try { 
			PrintWriter log = new PrintWriter(new FileWriter("salvataggio.txt",true));
			String timestamp = new SimpleDateFormat("dd-mm-yyyy").format(new Date());
			log.println("<" + timestamp + ">" + "<" + command + ">");
			log.flush();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
