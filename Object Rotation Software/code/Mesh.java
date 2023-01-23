package assignment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Mesh extends GraphicalObject{
	
	public HashSet<Polygon> polygons;
	public MeshReader reader;
	public MeshWriter writer;
	
	public void setReader(MeshReader reader) {
		this.reader = reader;
	}
	
	public void setWriter(MeshWriter writer) {
		this.writer = writer;
	}
	
	public void readFromFile(String filename) throws WrongFileFormatException, FileNotFoundException{
		polygons = reader.read(filename);
	}
	
	public void writeToFile(String filename) throws IOException{
		writer.write(filename, this.polygons);
	}
	
	public void transform(double[][] matrix) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		boolean b = true;
		for (Polygon p : this.polygons) {
			for (Vertex v : p.vertices) {
				for (Vertex v0 : vertices) {
					if(v0 == v) {
						b = false;
					}
				}
				if (b) vertices.add(v);
				b = true;
			}
		}
		for (Vertex vv : vertices) {
			vv.transform(matrix);
		}
	}
	
	@Override
	public boolean equals(Object m) {
		if (this == m) return true;
		if (!(m instanceof Mesh)) {
			return false;
		}
		
		else {
			Mesh newM = (Mesh)m;
			
			if ((newM.polygons != null && this.polygons != null && newM.polygons.size() != this.polygons.size()) || 
					(newM.polygons != null && this.polygons == null) || 
					(newM.polygons == null && this.polygons != null)) return false;
			for (Polygon p : this.polygons) {
				if (!newM.polygons.contains(p)) return false;
			}
			return true;
		}	
	}
	
	

	@Override
	public int hashCode() {
        int sum = 0;
        for (Polygon p : this.polygons) {
			sum += p.hashCode();
        }
        return sum;
    }
}
