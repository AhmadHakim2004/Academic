package assignment;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

public class OBJMeshWriter implements MeshWriter{
	public OBJMeshWriter() {	
	}
	
	public void write(String filename, HashSet<Polygon> polygons) throws IOException{
		ArrayList<Vertex> vs = new ArrayList<Vertex>();
		      FileWriter myWriter = new FileWriter(filename, true);
		      for (Polygon p : polygons) {
		    	  for(Vertex v : p.vertices) {
		    		  if(!vs.contains(v)) {
		    			  vs.add(v);
		    			  BigDecimal newX = new BigDecimal(v.x);
		    			  BigDecimal newY = new BigDecimal(v.y);
		    			  BigDecimal newZ = new BigDecimal(v.z);
			    		  myWriter.write("v " + newX.toPlainString() + " " + newY.toPlainString() + " " + newZ.toPlainString() + "\n");
		    		  }
		    	  }
		      }
		      for (Polygon p : polygons) {
		    	  myWriter.write("f");
		    	  for(Vertex v : p.vertices) {
		    		  int index = vs.indexOf(v)+1;
		    		  myWriter.write(" " + index);
		    	  }
		    	  myWriter.write("\n");
		      }
		      myWriter.close();

	}

}
