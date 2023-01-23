package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OBJMeshReader implements MeshReader{
	public OBJMeshReader() {
		
	}
	
	public HashSet<Polygon> read(String filename) throws WrongFileFormatException, FileNotFoundException{
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		String vREGEX = "^\\s*v{1}(\\s+-?((\\d+\\.\\d+((E(\\+||-)\\d+)?))||\\d+)){3}\\s*$";
		Pattern vPattern = Pattern.compile(vREGEX);
		String fREGEX = "^\\s*f{1}(\\s+\\d+){3,}\\s*$";
		Pattern fPattern = Pattern.compile(fREGEX);

		
		      File myObj = new File(filename);
		      Scanner myReader = new Scanner(myObj);
		      boolean v = true;
		      int count = 0;
		      
		      while (myReader.hasNextLine() ) {
			        String data = myReader.nextLine();
			        Matcher vMatcher = vPattern.matcher(data);
			        Matcher fMatcher = fPattern.matcher(data);
				    if (vMatcher.find() && v) {
				        	count++;
				    		String[] splited = data.trim().split(" +");
				    		Vertex v0 = new Vertex(Double.parseDouble(splited[1]), Double.parseDouble(splited[2]), Double.parseDouble(splited[3]));
				    		vertices.add(v0);
				        } else if(fMatcher.find() && count > 2){
				        	v = false;
				        	String[] splited = data.trim().split(" +");
				        	LinkedHashSet<Vertex> vSet = new LinkedHashSet<Vertex>();
				        	for (int i = 1; i < splited.length; i++) {
				        		int bound = Integer.parseInt(splited[i]) - 1;
					    		  if(bound < count) {
					    			  vSet.add(vertices.get(bound));
					    		  }
					    		  else {
					    			  myReader.close(); throw new WrongFileFormatException("Wrong file format!");
					    		  }
				        	}
				        	Polygon p = new Polygon(vSet);
				        	polygons.add(p);
				        }else {
				        	// throw exception
				        	myReader.close();
				        	throw new WrongFileFormatException("Wrong file format!");
				        }
			      }
		      
		      myReader.close();

	    return polygons;
	}
}
