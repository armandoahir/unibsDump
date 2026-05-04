package it.unibs.gestionalebaratto;


public class Main {

	public static void main(String[] args) {
		init();

		do {
			Menu.start();
			Memoria.Save();
		} while(ElementoMenu.attivo != null);		
	}
	
	public static void init() {
		Tastiera.init();
		Menu.init();
		Memoria.Init("data.obj");
	}

}
