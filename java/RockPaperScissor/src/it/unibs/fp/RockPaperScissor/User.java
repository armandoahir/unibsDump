package it.unibs.fp.RockPaperScissor;
import it.unibs.fp.mylib.InputDati;

public class User extends Player {
	private static final String MAKE_MOVE ="Choose your move: \n";

	public User(String _name) {
		super(_name);
	}

	@Override
	public void setMove() {
		Moves[] _move = Moves.values(); 
		int i;
		System.out.println(MAKE_MOVE);
		
		for(i = 1; i <= _move.length; i++) {
			System.out.println(i + " - " + _move[i - 1]);
		}
		
	int _userMove = InputDati.leggiIntero("-->", 1, _move.length);
	setMove(_move[_userMove - 1]); //??
	}

	@Override
	public String toString() {
		return getName() + ": " + getMove() + "\n";
	}
}
