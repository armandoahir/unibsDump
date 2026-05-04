/**
 * Si sviluppi un programma in linguaggio Java che implementa un server che permette a più client (telnet) di connettersi, e mandare messaggi al server.
 * Le funzionalità da implementare sono: 
 * 1. creazione del un server che accetta connessioni da più client sulla porta 1234, ad ogni client viene dato un identificativo univoco numerico (2 punti)
 * 2. inviando tramite telnet al server un testo preceduto dal simbolo @ il server rimanda al client lo stesso testo in lettere maiuscole (2 punti)
 * 3. Inviando tramite telnet al server il comando “!LIST” il server restituisce al client l’elenco di tutti i client collegati (elenco degli identificativi) (3 punti)
 * 4. Inviando tramite telnet al server un testo preceduto dal simbolo # il server rimanda
 * lo stesso testo a tutti i client collegati (3 punti)
 */

package it.unibs.pajc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {
	private static int clientId = 0;
	private static ArrayList<PrintWriter> clients = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		int port = 1234;
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("In attesa di connessione client...");
		
		while(true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connessione accettata da " + clientSocket.getInetAddress() + "\n Port: " + clientSocket.getPort());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			clients.add(out);
			new ClientHandler(in, out, clientId++).start();
			}
		}
	
	public static class ClientHandler extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		private int id;
		
		public ClientHandler(BufferedReader in,PrintWriter out,int id) {
			this.in = in;
			this.out = out;
			this.id = id;	
		}
		
		public void run() {
			try {
				out.println("Numero identificativo " + "> " + id);
				
				String command;
				
				while((command = in.readLine()) != null) {
					System.out.println("From: " + id + ": " + command);
					
					if(command.startsWith("@")) { 
						out.println(command.substring(1).toUpperCase());
					} else if(command.equalsIgnoreCase("!LIST")) {
						out.println("Client connessi > " + clients.size());
						for(int i = 0; i < clients.size(); i++) {
							out.println(" - Client: " + i);
						}
						
						for(int i = 0; i < clients.size(); i++) {
							System.out.println(" - Client:  " + i);
						}
					} else if(command.startsWith("#")) {
						for(PrintWriter client : clients) 
							client.println("From: " + id + ":" + command.substring(1));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				clients.remove(out);
			}
		}
	}
}
