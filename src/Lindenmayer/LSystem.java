package Lindenmayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LSystem {

	public JSONObject jSonObj = new JSONObject();;
	//variables
	public ArrayList<Symbol> alphabet = new ArrayList<Symbol>();
	public HashMap<Character,LinkedList<Sequence>> rules = new HashMap<Character,LinkedList<Sequence>>();
	public Sequence axiom;
	public Sequence sequence = new Sequence("");
	public HashMap<Character,String> actions = new HashMap<Character,String> ();
	public JSONObject startInfo;
	
	public LSystem() {
	}


	public void initLSystem(JSONObject jSonObjc) {

		importVars(jSonObjc);
		setActions(); // and Alphabet
		setRules();
		setAxiom(this.jSonObj.getString("axiom"));

	}
 
	public void importVars(JSONObject jSonObjc) {
		this.jSonObj = jSonObjc;
	}
	
	public Symbol addSymbol(char sym) {
		Symbol symbol = new Symbol(sym);
		this.alphabet.add(symbol);
		return symbol; 
	}

	public void setActions() {  // and alphabet
		JSONArray alph = this.jSonObj.getJSONArray("alphabet");
		JSONObject actions = this.jSonObj.getJSONObject("actions");
		for(int i =0; i<alph.length();i++ ) {
			String str = (String)alph.get(i);
			char sym = alph.getString(i).charAt(0);
			Symbol symbol = addSymbol(sym); // build this.alphabet
			if (actions.has(str)) {
				this.actions.put(symbol.character,actions.getString(str)); //build this.actions
			}
		}
	}		

	public void addRule(Symbol sym, String expansion) {
		Sequence nouvelleSeq = new Sequence(expansion);
		if(this.rules.containsKey(sym.character)) 
			this.rules.get(sym.character).add(nouvelleSeq);
		else 
		{    
			LinkedList<Sequence> lst = new LinkedList<Sequence>();
			lst.add(nouvelleSeq);
			this.rules.put(sym.character,lst);
		}
	}

	public void setRules() {
		JSONObject jObjRules = this.jSonObj.getJSONObject("rules");

		for (int i = 0; i < this.alphabet.size(); i++) {
			Symbol sym = this.alphabet.get(i);
			String key = ""+sym.character;

			if (jObjRules.has(key)) {
				JSONArray rulesForKey = jObjRules.getJSONArray(key);
				for (int k = 0; k < rulesForKey.length(); k++)
					addRule(sym, rulesForKey.getString(k));
			}
		}

	}

	public  void setAxiom(String str){
		axiom = new Sequence(str);
	}

	public Symbol.Seq getAxiom(){
		return this.axiom;
	}

	public Symbol.Seq rewrite(Symbol sym) {
		LinkedList<Sequence> rulesOfKey = this.rules.get(sym.character);

		if(rulesOfKey.size()!= 0) {
			int choixAlea = (int) Math.floor(Math.random()*rulesOfKey.size());
			return rulesOfKey.get(choixAlea);
		}
		else {
			return null;
		}
	}

	public Sequence applyRules(Sequence seq, int n) {
		if(n==0) return seq;
		Sequence lastSeq= new Sequence("");
		lastSeq.tranformIn(seq); 
		Sequence tempSeq =new Sequence("");
		for(int i = 1; i <= n ;i++) {
			for(Symbol S: lastSeq) {
				if(this.rules.containsKey(S.character)) {
					tempSeq = tempSeq.addSequence(tempSeq, (Sequence)rewrite(S) );
				}else {
					tempSeq.addSymbol(S);
				}
			}
			lastSeq.tranformIn(tempSeq);
		}
		return lastSeq;
	}
	
	// EPS_creator.addNewPath(T);
	public Double[] tell(MyTurtle T, Sequence seq, int n) {
		if(n==0) return T.boundingBoxToArray();
		if(n==1){
			for(Symbol S: applyRules(seq, 1))  
				tell(T,S);
		}else {
			this.sequence.tranformIn(applyRules(seq, 1));
			tell(T,applyRules(seq, 1),n-1);	
		}
		for(Symbol S: this.sequence)  
			tell(T,S);
		
		return T.boundingBoxToArray();
	}

	
	
	
	
	// Advanced Method
	public void tell(MyTurtle turtle,Symbol sym) {
 		String test = "" + this.actions.get(sym.character);
		if("draw".equals(test)) 
			turtle.draw();
		else if ("move".equals(test))  
			turtle.move(); 
		else if ("turnR".equals(test)) 
			turtle.turnR(); 
		else if ("turnL".equals(test)) 
			turtle.turnL();
		else if ("push".equals(test))  
			turtle.push(); 
		else if ("pop".equals(test)) 
			turtle.pop(); 
	}

}

