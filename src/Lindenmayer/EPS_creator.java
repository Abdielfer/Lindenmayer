package Lindenmayer;

import java.io.File;  
import java.io.FileWriter;
import java.io.IOException; 

public class EPS_creator {

	static String filename;
	static FileWriter myWriter; 

	public EPS_creator() throws IOException{

	}

	public void epsCreation(String FileName) throws IOException {
		EPS_creator.filename = FileName + ".eps";
		myWriter = new FileWriter(EPS_creator.filename); 
		try {
			File myObj = new File(EPS_creator.filename);
			myObj.createNewFile();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		epsHeader();

	}

	public void epsHeader() {
		writeEPSFile("%!PS-Adobe-3.0 EPSF-3.0");
		System.out.println("%!PS-Adobe-3.0 EPSF-3.0");

		writeEPSFile("%%Title: Lindenmayer_ IFT2015_TP1");
		System.out.println("%%Title: Lindenmayer_ IFT2015_TP1");

		writeEPSFile("%%Creator: AbdielFernandez & Ekoum Raymond Fred");
		System.out.println("%%Creator: AbdielFernandez & Ekoum Raymond Fred");

		writeEPSFile("%%BoundingBox: (atend)");
		System.out.println("%%BoundingBox: (atend)");

		writeEPSFile("%%EndComments");
		System.out.println("%%EndComments");
		
		writeEPSFile("0.5 setlinewidth");
		System.out.println("0.5 setlinewidth");
		

	}

	public static void writeEPSFile(String data) { 
		try {
			myWriter.write(data + "\n");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void addNewPath(MyTurtle T) {
		writeEPSFile(T.currentState.posX + " " + T.currentState.posY + " newpath moveto");
		System.out.println(T.currentState.posX + " " + T.currentState.posY + " newpath moveto");
	}
	
	
	public void finalTouchEPSFile(Double[] boundingBox) throws IOException {
		
		writeEPSFile("stroke"); 
		System.out.println("stroke"); 
		
		writeEPSFile("%%Trailer");
		System.out.println("%%Trailer");

		String bondBox = "";
		for(int i = 0; i < 4; i++) {
			bondBox += " " + boundingBox[i];
		}
		
		writeEPSFile("%%BoundingBox:"+ bondBox );
		System.out.println("%%BoundingBox:"+ bondBox );
		writeEPSFile("%%EOF"); 
		System.out.println("%%EOF"); 
		myWriter.close();

	}

}
