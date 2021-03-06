package Lindenmayer;

public class Symbol {

	public char character;

	public Symbol(char character){	
		this.character = character;
	}


	public char getCharacter() {
		return character;
	}
	public void setCharacter(char character) {
		this.character = character;
	}


	public interface Seq extends Iterable<Symbol>{
		public boolean hasNext();
		public Character next();

	}

}
