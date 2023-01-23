package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PLYMeshReader implements MeshReader{
	public PLYMeshReader() {	
	}
	
	public HashSet<Polygon> read(String filename) throws WrongFileFormatException, FileNotFoundException{
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		String[] REGEX = {"^\\s*ply\\s*$", "^\\s*format ascii 1.0\\s*$", "^\\s*element vertex\\s+\\d+\\s*$", 
				"^\\s*property float32 x\\s*$", "^\\s*property float32 y\\s*$", "^\\s*property float32 z\\s*$",
				"^\\s*element face\\s+\\d+\\s*$", "^\\s*property list uint8 int32 vertex_indices\\s*$",
				"^\\s*end_header\\s*$"};
		Pattern starterPattern;
		String vREGEX = "^(\\s*-?(((\\d+\\.\\d+((E(\\+||-)\\d+)?))||\\d+)\\s+)){2}-?((\\d+\\.\\d+((E(\\+||-)\\d+)?))||\\d+)\\s*$";
		Pattern vPattern = java.util.regex.Pattern.compile(vREGEX);
		String f_REGEX = "\\s+\\d+";
		String fREGEX;
		Pattern fPattern;
		int vCount = 0;
		int fCount = 0;
		String line;
		Matcher Matcher;
		String[] splited;

		      File myObj = new File(filename);
		      Scanner myReader = new Scanner(myObj);
		      for(int i = 0; i<9; i++) {
		    	  starterPattern = java.util.regex.Pattern.compile(REGEX[i]);
		    	  if(myReader.hasNextLine()) {
		    		  line = myReader.nextLine();
				      Matcher = starterPattern.matcher(line);
				      if(Matcher.find()) {
				    	  if (i==2) {
				    		  splited = line.trim().split(" +");
				    		  vCount = Integer.parseInt(splited[splited.length - 1]);
				    	  }
				    	  if (i==6) {
				    		  splited = line.trim().split(" +");
				    		  fCount = Integer.parseInt(splited[splited.length - 1]);
				    	  }
				      } else {
				    	  myReader.close();
				    	  throw new WrongFileFormatException("Wrong file format!");
				        }   
			      } else {
			    	  myReader.close();
			    	  throw new WrongFileFormatException("Wrong file format!");
			        } 
		      }
		    	  
		     for (int x = 0; x < vCount; x++) {
		    	 if(myReader.hasNextLine()) {
		    		  line = myReader.nextLine();
				      Matcher = vPattern.matcher(line);
				      
				      if(Matcher.find()) {
				    	  splited = line.trim().split(" +");
				    	  Vertex v0 = new Vertex(Double.parseDouble(splited[0]), Double.parseDouble(splited[1]), Double.parseDouble(splited[2]));
				    	  vertices.add(v0);
				      }else {
				    	  myReader.close();
				    	  throw new WrongFileFormatException("Wrong file format!");
				      }
		    	 }else {
		    		 myReader.close();
			    	 throw new WrongFileFormatException("Wrong file format!");
		    	 }
		     }
		     
		     for (int y = 0; y < fCount; y++) {
		    	 if(myReader.hasNextLine()) {
		    		  line = myReader.nextLine();
		    		  String[] tempSplit = line.trim().split(" +");
		    		  int a = Integer.parseInt(tempSplit[0]);
		    		  fREGEX = "^\\s*" + tempSplit[0];
		    		  for(int z=0; z<a; z++) {
		    			  fREGEX += f_REGEX;
		    		  }
		    		  fREGEX += "\\s*$";
		    		  fPattern = java.util.regex.Pattern.compile(fREGEX);
				      Matcher = fPattern.matcher(line);
				      if(Matcher.find()) {
				    	  splited = line.trim().split(" +");
				    	  LinkedHashSet<Vertex> vSet = new LinkedHashSet<Vertex>();
				    	  for (int i = 1; i < splited.length; i++) {
				    		  int bound = Integer.parseInt(splited[i]);
				    		  if(bound < vCount) {
				    			  vSet.add(vertices.get(bound));
				    		  }
				    		  else {
				    			  myReader.close(); throw new WrongFileFormatException("Wrong file format!");
				    		  }
				        	}
				        	Polygon p = new Polygon(vSet);
				        	polygons.add(p);
				      }else { 
				    	  myReader.close();
				    	  throw new WrongFileFormatException("Wrong file format!");
				      }
		    	 }else {
		    		 myReader.close();
			    	 throw new WrongFileFormatException("Wrong file format!");
		    	 }
		     }
		     	if(myReader.hasNextLine()) {
		     		myReader.close();
		     		throw new WrongFileFormatException("Wrong file format!");
		     	}
		      
		      myReader.close();

		return polygons;
	}
}
