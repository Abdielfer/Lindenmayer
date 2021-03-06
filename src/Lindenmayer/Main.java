package Lindenmayer;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// Pre-initialization
		ReadJSONFIle jSonFile = new ReadJSONFIle(args[0]);
		MyTurtle turtle = new MyTurtle(jSonFile.JSonObjc);
		turtle.setBoundingBoxStart();
		
		// Initialization
		LSystem lSyst = new LSystem();
		lSyst.initLSystem(jSonFile.JSonObjc);

		int n = Integer.parseInt(args[1]);
		Sequence axiom = (Sequence) lSyst.getAxiom() ;

		// Écriture de fichier *.eps. 
		EPS_creator epsFile = new EPS_creator();
		epsFile.epsCreation(extractName(args[0]));
		EPS_creator.addNewPath(turtle);
		
		lSyst.tell(turtle, axiom, n);
		
		epsFile.finalTouchEPSFile(turtle.boundingBoxToArray());
		

	}

	public static String extractName(String s) {
		int N = s.length();
		if (N <= 1 || s.substring(N-1,N).equals("."))
			return s.substring(0,N-1);
		return extractName(s.substring(0, N-1)) ;
	}
}
