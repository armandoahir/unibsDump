package it.unibs.fp.RockPaperScissor;

import it.unibs.fp.mylib.MyMenu;

public class RPSGame {
	private final static String ROCK = "Rock";
	private final static String PAPER = "Paper";
	private final static String SCISSOR = "Scissor";
	private final static String WIN = "WIN";
	private final static String LOST ="LOST";
	private final static String DRAW = "DRAW";
	
	public static String game(User _user, Pc _pc) {
		StringBuffer _result = new StringBuffer();
		
		if(_user.getMove().equals(_pc.getMove())) {
			
			_result.append(DRAW);
			_user.draw();
			_pc.draw();
		}
		else {
			if(
					(_pc.getMove().toString() == ROCK && _user.getMove().toString() == PAPER) ||
					(_pc.getMove().toString() == SCISSOR && _user.getMove().toString() == ROCK) ||
					(_pc.getMove().toString() == PAPER && _user.getMove().toString() == SCISSOR))
			{
				_result.append(LOST);
				_pc.win();
			}
			else {
				if(
						(_user.getMove().toString() == ROCK && _pc.getMove().toString() == PAPER) ||
						(_user.getMove().toString() == SCISSOR && _pc.getMove().toString() == ROCK) ||
						(_user.getMove().toString() == PAPER && _pc.getMove().toString() == SCISSOR))
				{
					_result.append(WIN);
					_user.win();
				}
			else
			{
				_result.append(LOST);
				_pc.win();
			} 
		}
	}	return _result.toString();	
}
	
	public static void rockPaperScissor(User _user, Pc _pc, MyMenu _menu) {
		int _choice;
		do {
			_choice = _menu.scegli();
			StringBuffer _result = new StringBuffer();
			
			if(_choice != 0) {
				_user.setMove();
				_result.append(_user.toString());
				_pc.setMove();
				_result.append(_pc.toString());
				_result.append(RPSGame.game(_user, _pc));
				System.out.println("\n" + _result.toString());
			}
		}while(_choice != 0);
	}
}
