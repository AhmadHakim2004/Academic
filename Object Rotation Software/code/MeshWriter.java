package assignment;

import java.io.IOException;
import java.util.HashSet;

public interface MeshWriter {
	public abstract void write(String filename, HashSet<Polygon> polygons) throws IOException;
}
