package assignment;


public class Vertex extends GraphicalObject{
	public double x;
	public double y;
	public double z;
	
	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void transform(double[][] matrix) {
		double newX = (this.x * matrix[0][0]) + (this.y * matrix[0][1]) + (this.z * matrix[0][2]);
		double newY = (this.x * matrix[1][0]) + (this.y * matrix[1][1]) + (this.z * matrix[1][2]);
		double newZ = (this.x * matrix[2][0]) + (this.y * matrix[2][1]) + (this.z * matrix[2][2]);
		this.x = newX;
		this.y = newY;
		this.z = newZ;
	}
		
	
	@Override
	public boolean equals(Object v) {
		if (!(v instanceof Vertex)) return false;
		else {
			Vertex newV = (Vertex)v;
			double eps = 0.00001;
			return (Math.abs(this.x-newV.x) <eps && Math.abs(this.y-newV.y) <eps && Math.abs(this.z-newV.z) <eps);
		}	
	}

	@Override
	public String toString() { 
		double newX = (double)Math.round(this.x*100000)/100000;
		double newY = (double)Math.round(this.y*100000)/100000;
		double newZ = (double)Math.round(this.z*100000)/100000;

        return (newX + " " + newY + " " + newZ);
    }


	@Override
	public int hashCode() {
        return this.toString().hashCode();
    }
	
}
