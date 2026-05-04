package it.unibs.fp.RockPaperScissor;
import it.unibs.fp.mylib.*;

public abstract class Player {
	
	private Moves move;
	private String name;
	private int winCount = 0;
	private int drawCount = 0;
	
	/**
	 * Constructor
	 * @param _name
	 */
	
	public Player(String _name) {
		this.name = _name;
	}

	/**
	 * this setMove is for the CPU Player (PC)
	 */
	public void setMove() {
		Moves[] _move = Moves.values();
		this.move = _move[NumeriCasuali.estraiIntero(0, _move.length - 1)];
	}
	
	public void win() {
		winCount++;
	}
	
	public void draw() {
		drawCount++;
	}
	/**
	 * 
	 * @return Getters and Setters by source
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWinCount() {
		return winCount;
	}
	public int getDrawCount() {
		return drawCount;
	}
	public Moves getMove() {
		return move;
	}
	public void setMove(Moves move) {
		this.move = move;
	}
	
	
}
