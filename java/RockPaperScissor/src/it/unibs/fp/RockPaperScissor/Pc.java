package it.unibs.fp.RockPaperScissor;

public class Pc extends Player {
	
	private final static String NAME = "CPU";

	public Pc() {
		super(NAME);
	}

	@Override
	public String toString() {
		return getName() + ": " + getMove() + "\n";
	}	
}
