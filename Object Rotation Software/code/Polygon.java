package assignment;

import java.util.LinkedHashSet;

public class Polygon extends GraphicalObject{
	
	public LinkedHashSet<Vertex> vertices;

	public Polygon(LinkedHashSet<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	public void transform(double[][] matrix) {
        for (Vertex v : vertices) {
            v.transform(matrix);
        }
	}
	
	@Override
	public boolean equals(Object p) {
		if(this == p) return true;
		if (!(p instanceof Polygon)) return false;
		else {
			Polygon newP = (Polygon)p;
			if (newP.vertices!= null && this.vertices != null && newP.vertices.size() != this.vertices.size())
					 return false;
			for (Vertex v : this.vertices) {
		           if (!newP.vertices.contains(v)) return false;
		        }
				return true;
		}	
	}
	
	

	@Override
	public int hashCode() {
        int sum = 0;
        for (Vertex v : this.vertices) {
			sum += v.hashCode();
        }
        return sum;
    }
}
