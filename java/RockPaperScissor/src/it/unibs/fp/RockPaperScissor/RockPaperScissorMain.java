package it.unibs.fp.RockPaperScissor;
import it.unibs.fp.mylib.*;


public class RockPaperScissorMain {
	
	private static final String INTRO = "Rock Paper Scissor";
	private static final String NAME = "Name: ";
	private static final String[] COMMANDS = {
			"New game"
	};

	public static void main(String[] args) {
		
		User user = new User(InputDati.leggiStringaNonVuota(NAME));
		Pc pc = new Pc();
		MyMenu menu = new MyMenu(INTRO,COMMANDS);
		
		RPSGame.rockPaperScissor(user, pc, menu);
		
		toString(user);
	}
	
	private static void toString(User _user) {
		System.out.println(_user.getName() + ": ");
		System.out.println(" - " + _user.getWinCount() + " victories");
		System.out.println(" - " + _user.getDrawCount() + " Draws");
	}
}
