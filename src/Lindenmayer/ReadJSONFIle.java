package Lindenmayer;

import org.json.*;

public class ReadJSONFIle {
	
	public JSONObject JSonObjc;
	
	public ReadJSONFIle(String fileName)throws java.io.IOException {
		
		String file= "src/" + fileName;
		JSonObjc = new JSONObject(new JSONTokener(new java.io.FileReader(file)));
	
	}
}
