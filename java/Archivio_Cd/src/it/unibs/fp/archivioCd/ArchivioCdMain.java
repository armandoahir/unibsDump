package it.unibs.fp.archivioCd;
import it.unibs.fp.mylib.*;

public class ArchivioCdMain {
	private static final String TITLE = "Archivio CD";
	private static final String[] OPTIONS = {
			"Add CD",
			"View CD",
			"Delete CD",
			"View CD shelf",
			"Play random Track"
	};
	
	public static void main(String[] args) {
		
		MyMenu menu = new MyMenu(ArchivioCdMain.TITLE, ArchivioCdMain.OPTIONS);
		int choice;
		ArchivioCd archivio = new ArchivioCd();
		
		do {
			choice = menu.scegli();
			Manage.menuChoice(choice, archivio);
		}while(choice != 0);
	}
}
