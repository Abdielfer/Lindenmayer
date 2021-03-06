package Lindenmayer;


import java.util.ArrayList;
import java.util.Iterator;

import Lindenmayer.Symbol.Seq;

class Sequence implements Seq {

	public ArrayList<Symbol> listSymbol = new ArrayList<Symbol>();

	public Sequence(String seq){
		setSymbolList(seq); 
	}

	public void setSymbolList(String seq) {
		for(int i = 0; i < seq.length();i++) {
			Symbol sym = new Symbol(seq.charAt(i));
			listSymbol.add(sym);
		}
	}

	public void addSymbol (Symbol symbol) {
		this.listSymbol.add(symbol);
	}


	// @arg sec1,sec2
	// construi une sequence sec1sec2.
	public Sequence addSequence(Sequence sec1, Sequence sec2) {
		for(Symbol S:sec2.listSymbol) {
			sec1.listSymbol.add(S);
		}
		return sec1;
	}

	@Override
	public Iterator<Symbol> iterator() {
		Iterator<Symbol> it = listSymbol.iterator();
		return it;
	}

	public boolean hasNext(){
		Iterator<Symbol> it = listSymbol.iterator();
		if (it.hasNext()) return true;
		return false;
	}

	public Character next(){
		Iterator<Symbol> it = this.listSymbol.iterator();
		Character ans = it.next().character;
		return ans;

	}

	public void tranformIn(Sequence tempSeq) {
		this.listSymbol.clear();
		for(Symbol S:tempSeq.listSymbol) {
			this.listSymbol.add(S);
		}

	}

	public String toString() {
		String ans = "";
		for(Symbol S: listSymbol) {
			ans = ans+S.character;
			System.out.println(S.character);	
		}
		return ans;
	}


}
