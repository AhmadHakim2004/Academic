package assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class PLYMeshWriter implements MeshWriter{
	public PLYMeshWriter() {	
	}
	
	public void write(String filename, HashSet<Polygon> polygons) throws IOException{
		ArrayList<Vertex> vs = new ArrayList<Vertex>();
			FileWriter myWriter = new FileWriter("\\Users\\Ahmad Hakim\\temporary.txt", true);
		      
		      for (Polygon p : polygons) {
		    	  for(Vertex v : p.vertices) {
		    		  if(!vs.contains(v)) {
		    			  vs.add(v);
		    			  BigDecimal newX = new BigDecimal(v.x);
		    			  BigDecimal newY = new BigDecimal(v.y);
		    			  BigDecimal newZ = new BigDecimal(v.z);
			    		  myWriter.write(newX.toPlainString() + " " + newY.toPlainString() + " " + newZ.toPlainString() + "\n");
		    		  }
		    	  }
		      }
		      for (Polygon p : polygons) {
		    	  myWriter.write(""+p.vertices.size());
		    	  for(Vertex v : p.vertices) {
		    		  myWriter.write(" " + vs.indexOf(v));
		    	  }
		    	  myWriter.write("\n");
		      }
		      myWriter.close();
		    
			FileWriter myWriterMain = new FileWriter(filename, true);
		    int vnum = vs.size();
		    int fnum = polygons.size();
		    String[] firstLines = {"ply", "format ascii 1.0", "element vertex " + vnum, 
					"property float32 x", "property float32 y", "property float32 z",
					"element face " + fnum, "property list uint8 int32 vertex_indices",
					"end_header"};
		    myWriterMain.write(firstLines[0]);
		    for (int i = 1; i < 9; i++) {
		    	myWriterMain.write("\n"+firstLines[i]);
		    }
		    
		    File myObj = new File("\\Users\\Ahmad Hakim\\temporary.txt");
		    Scanner myReader = new Scanner(myObj);
		    while(myReader.hasNextLine()) {
		    	String line = myReader.nextLine();
		    	myWriterMain.write("\n"+line);
		    }
		    myReader.close();
		    myWriterMain.close();
		    myObj.delete();
		
		
			
	}

}
