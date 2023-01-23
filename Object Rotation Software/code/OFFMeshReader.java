package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OFFMeshReader implements MeshReader{
	public OFFMeshReader() {	
	}
	
	public HashSet<Polygon> read(String filename) throws WrongFileFormatException, FileNotFoundException{
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		String[] headREGEX = {"^\\s*OFF\\s*$", "^\\s*(\\d+\\s+){2}\\d+\\s*$"};
	    	
	    	File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			String vREGEX = "^(\\s*-?(((\\d+\\.\\d+((E(\\+||-)\\d+)?))||\\d+)\\s+)){2}-?((\\d+\\.\\d+((E(\\+||-)\\d+)?))||\\d+)\\s*$";
			Pattern vPattern = java.util.regex.Pattern.compile(vREGEX);
			String f_REGEX = "\\s+\\d+";
			String intREGEX = "^\\d+$";
			Pattern intPattern = java.util.regex.Pattern.compile(intREGEX);
			Matcher intMatcher;
			Pattern fPattern1;
			Pattern fPattern2;
			Matcher Matcher;
			Matcher fMatcher1;
			Matcher fMatcher2;
			Pattern starterPattern;
			String line;
			String[] splited;
			int a;
			int vCount = 0;
			int fCount = 0;
			while(myReader.hasNextLine()) {
				for(int i =0; i<2; i++) {
					if(myReader.hasNextLine()) {
						line = myReader.nextLine();
				    	starterPattern = java.util.regex.Pattern.compile(headREGEX[i]);

					    Matcher = starterPattern.matcher(line);
					    if(Matcher.find()) {
					    	  if (i==1) {
					    		  splited = line.trim().split(" +");
					    		  vCount = Integer.parseInt(splited[0]);
					    		  fCount = Integer.parseInt(splited[1]);
					    	  }
					      } else {
					    	  myReader.close();
					    	  throw new WrongFileFormatException("Wrong file format!");
					        } 
					}else {
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
			    		  splited = line.trim().split(" +");
			    		  intMatcher = intPattern.matcher(splited[0]);
			    		  if(intMatcher.find()) {
			    			  a = Integer.parseInt(splited[0]); 
			    			  String fREGEX1 = "^\\s*" + splited[0];
				    		  String fREGEX2 = "^\\s*" + splited[0];
				    		  for(int z=0; z<a; z++) {
				    			  fREGEX1 += f_REGEX;
				    			  fREGEX2 += f_REGEX;
				    		  }
				    		  for(int z=0; z<3; z++) {
				    			  fREGEX2 += "\\s+\\d+";
				    		  }
				    		  fREGEX1 += "\\s*$";
				    		  fREGEX2 += "\\s*$";
				    		  fPattern1 = java.util.regex.Pattern.compile(fREGEX1);
				    		  fPattern2 = java.util.regex.Pattern.compile(fREGEX2);
						      fMatcher1 = fPattern1.matcher(line);
						      fMatcher2 = fPattern2.matcher(line);
						      if(fMatcher1.find() || fMatcher2.find()) {
						    	  LinkedHashSet<Vertex> vSet = new LinkedHashSet<Vertex>();
						    	  for (int i = 1; i < splited.length; i++) {
						    		  int bound = Integer.parseInt(splited[i]);
						    		  if(i<=a) {
						    			  if(bound < vCount) {
							    			  vSet.add(vertices.get(bound));
							    		  }
							    		  else {
							    			  myReader.close(); throw new WrongFileFormatException("Wrong file format!");
							    		  }
						    		  }else {
						    			  if(0> bound || bound > 255) {
							    			  myReader.close(); throw new WrongFileFormatException("Wrong file format!");
							    		  }
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
			    		  
			    	 }else {
			    		 myReader.close();
				    	 throw new WrongFileFormatException("Wrong file format!");
			    	 }
			     }
			}
			myReader.close();
	    return polygons;
	}
}
