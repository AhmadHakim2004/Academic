package Tester;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import assignment.Mesh;
import assignment.OBJMeshReader;
import assignment.OBJMeshWriter;
import assignment.OFFMeshReader;
import assignment.OFFMeshWriter;
import assignment.PLYMeshReader;
import assignment.PLYMeshWriter;
import assignment.Polygon;
import assignment.Vertex;
import assignment.WrongFileFormatException;

class Tester {
	Path currentRelativePath = Paths.get("");
	String d = currentRelativePath.toAbsolutePath().toString() + "\\temporary.";
	String[] REGEX = {"ply", "format ascii 1.0", "element vertex 4", 
			"property float32 x", "property float32 y", "property float32 z",
			"element face 2", "property list uint8 int32 vertex_indices",
			"end_header"};
	
	Vertex v1 = new Vertex(1, 1, 1);
	Vertex v2 = new Vertex(2, 2, 2);
	Vertex v3 = new Vertex(3, 3, 3);
	Vertex v4 = new Vertex(4, 4, 4);
	ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v1, v2, v3, v4));
	ArrayList<Vertex> vArray1 = new ArrayList<>(Arrays.asList(v1, v2, v3));
	ArrayList<Vertex> vArray2 = new ArrayList<>(Arrays.asList(v2, v3, v4));
	ArrayList<Vertex> vArray3 = new ArrayList<>(Arrays.asList(v1, v2, v4));

	LinkedHashSet<Vertex> LHS1 = new LinkedHashSet<Vertex>(vArray1);
	LinkedHashSet<Vertex> LHS2 = new LinkedHashSet<Vertex>(vArray2);
	LinkedHashSet<Vertex> LHS3 = new LinkedHashSet<Vertex>(vArray3);
	Polygon p1 = new Polygon(LHS1);
	Polygon p2 = new Polygon(LHS2);
	Polygon p3 = new Polygon(LHS3);
	ArrayList<Polygon> pArray = new ArrayList<>(Arrays.asList(p1,p2));	
	ArrayList<Polygon> pArray1 = new ArrayList<>(Arrays.asList(p1,p2));
	ArrayList<Polygon> pArray2 = new ArrayList<>(Arrays.asList(p1,p3));

	HashSet<Polygon> polys = new HashSet<Polygon>(pArray);
	HashSet<Polygon> polys1 = new HashSet<Polygon>(pArray1);
	HashSet<Polygon> polys2 = new HashSet<Polygon>(pArray2);
	

	@Test
	void testVertexHashCodeTrue() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.0000001, 0.999999, 1.99999999);
	    Assert.assertTrue(x.hashCode() == y.hashCode());
	}
	
	@Test
	void testVertexHashCodeFalse() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.0001, 0.999999, 1.99999999);
	    Assert.assertFalse(x.hashCode() == y.hashCode());
	}

	@Test
	void testVertexTransform() {
	    Vertex v1 = new Vertex(1, 1, 1);
	    double[][] matrix = {
				{1, 0, 0},
				{0,-1, 1},
				{1, 1, 1},
		};
	    v1.transform(matrix);
	    Vertex v2 = new Vertex(1, 0, 3);
	    Assert.assertEquals(v1, v2);
	}

	@Test
	void testVertex() {
		Vertex v = new Vertex(2,1,-1);
		Assert.assertEquals(2, v.x, 0.00001);
		Assert.assertEquals(1, v.y, 0.00001);
		Assert.assertEquals(-1, v.z, 0.00001);
	}

	@Test
	void testVertexEqualsObjectTrue() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.0000001, 0.999999, 1.99999999);
	    Assert.assertTrue(x.equals(y));
	}
	
	@Test
	void testVertexEqualsObjectFalse_1() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    String y = new String("hi");
	    Assert.assertFalse(x.equals(y));
	    }
	
	@Test
	void testVertexEqualsObjectFalse_2() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.0001, 0.999999, 1.99999999);
	    Assert.assertFalse(x.equals(y));
	    }
	
	@Test
	void testVertexEqualsObjectFalse_3() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.000001, 1.999999, 1.99999999);
	    Assert.assertFalse(x.equals(y));
	    }
	
	@Test
	void testVertexEqualsObjectFalse_4() {
		Vertex x = new Vertex(0, 1, 2);  // equals and hashCode check name field value
	    Vertex y = new Vertex(0.000001, 0.999999, 1.99);
	    Assert.assertFalse(x.equals(y));
	    }

	@Test
	void testVertexToString() {
		Vertex v = new Vertex(1.23456789, -2.98765433, 0);
		Assert.assertEquals(v.toString(), "1.23457 -2.98765 0.0");

	}

	@Test
	void testVertexRotateXAxis() {
		Vertex v1 = new Vertex(1, -2, 3);
		v1.rotateXAxis(Math.PI/2);
		Vertex v2 = new Vertex(1, -3, -2);
		Assert.assertEquals(v1, v2);
	}

	@Test
	void testVertexRotateYAxis() {
		Vertex v1 = new Vertex(1, -2, 3);
		v1.rotateYAxis(Math.PI/4);
		Vertex v2 = new Vertex(Math.cos(Math.PI/4) + (3 * Math.sin(Math.PI/4)), -2, (-1 * Math.sin(Math.PI/4)) + (3 * Math.cos(Math.PI/4)));
		Assert.assertEquals(v1, v2);

	}

	@Test
	void testVertexRotateZAxis() {
		Vertex v1 = new Vertex(1, -2, 3);
		v1.rotateZAxis(Math.PI/6);
		Vertex v2 = new Vertex(Math.cos(Math.PI/6) + (2 * Math.sin(Math.PI/6)), Math.sin(Math.PI/6) + (-2 * Math.cos(Math.PI/6)), 3);
		Assert.assertEquals(v1, v2);

	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void testPolygonHashCodeTrue() {
		
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v3, v2, v1));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
	    Assert.assertTrue(p1.hashCode() == p.hashCode());	
	}
	
	@Test
	void testPolygonHashCodeFalse() {
	    Assert.assertFalse(p1.hashCode() == p2.hashCode());	
	}

	@Test
	void testPolygonTransform() {
		double[][] matrix = {
				{1, 1, 1},
				{1, 1, 1},
				{1, 1, 1},
		};
		p1.transform(matrix);
		Vertex v4 = new Vertex(3, 3, 3);
		Vertex v5 = new Vertex(6, 6, 6);
		Vertex v6 = new Vertex(9, 9, 9);
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v4, v5, v6));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
	    Assert.assertTrue(p1.equals(p));
	}

	@Test
	void testPolygon() {
		Assert.assertEquals(p1.vertices, LHS1);
	}
	
	@Test
	void testPolygonEqualsObjectTrue() {
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>();
		Polygon p = new Polygon(LHS);
	    Assert.assertTrue(p.equals(p));	}
	
	@Test
	void testPolygonEqualsObjectTrue_2() {
		Polygon p = new Polygon(LHS1);
	    Assert.assertTrue(p1.equals(p));	}
	
	@Test
	void testPolygonEqualsObjectFalse_1() {
		String s = new String("hi");
	    Assert.assertFalse(p1.equals(s));
	    }
	
	@Test
	void testPolygonEqualsObjectFalse_2() {
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v4, v3, v2, v1));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
	    Assert.assertFalse(p1.equals(p));
	}
	
	@Test
	void testPolygonEqualsObjectFalse_3() {
	    Assert.assertFalse(p1.equals(p2));
	}
	
	@Test
	void testPolygonEqualsObjectFalse_4() {
		LinkedHashSet<Vertex> LHS0 = new LinkedHashSet<Vertex>();
		Polygon p0 = new Polygon(LHS0);
	    Assert.assertFalse(p1.equals(p0));
	}
	
	@Test
	void testPolygonEqualsObjectFalse_5() {
		LinkedHashSet<Vertex> LHS0 = new LinkedHashSet<Vertex>();
		Polygon p0 = new Polygon(LHS0);
	    Assert.assertFalse(p0.equals(p1));
	}
	
	@Test
	void testPolygonEqualsObjectFalse_6() {
	    Assert.assertFalse(p1.equals(null));
	}
	
	
	@Test
	void testPolygonRotateXAxis() {
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v3, v2, v1));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
		p.rotateXAxis(Math.PI/2);
		
		Vertex v4 = new Vertex(1, -1, 1);
		Vertex v5 = new Vertex(2, -2, 2);
		Vertex v6 = new Vertex(3, -3, 3);
		ArrayList<Vertex> vArray0 = new ArrayList<>(Arrays.asList(v4, v5, v6));
		LinkedHashSet<Vertex> LHS0 = new LinkedHashSet<Vertex>(vArray0);
		Polygon p0 = new Polygon(LHS0);
		Assert.assertTrue(p.equals(p0));
	}

	@Test
	void testPolygonRotateYAxis() {
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v3, v2, v1));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
		p.rotateYAxis(Math.PI/2);
		
		Vertex v4 = new Vertex(1, 1, -1);
		Vertex v5 = new Vertex(2, 2, -2);
		Vertex v6 = new Vertex(3, 3, -3);
		ArrayList<Vertex> vArray0 = new ArrayList<>(Arrays.asList(v4, v5, v6));
		LinkedHashSet<Vertex> LHS0 = new LinkedHashSet<Vertex>(vArray0);
		Polygon p0 = new Polygon(LHS0);
		Assert.assertTrue(p.equals(p0));
	}

	@Test
	void testPolygonRotateZAxis() {
		ArrayList<Vertex> vArray = new ArrayList<>(Arrays.asList(v3, v2, v1));
		LinkedHashSet<Vertex> LHS = new LinkedHashSet<Vertex>(vArray);
		Polygon p = new Polygon(LHS);
		p.rotateZAxis(Math.PI/2);
		
		Vertex v4 = new Vertex(-1, 1, 1);
		Vertex v5 = new Vertex(-2, 2, 2);
		Vertex v6 = new Vertex(-3, 3, 3);
		ArrayList<Vertex> vArray0 = new ArrayList<>(Arrays.asList(v4, v5, v6));
		LinkedHashSet<Vertex> LHS0 = new LinkedHashSet<Vertex>(vArray0);
		Polygon p0 = new Polygon(LHS0);
		Assert.assertTrue(p.equals(p0));
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	@Test
	void testOBJMeshReaderReadSuccess() throws WrongFileFormatException, FileNotFoundException, IOException{
			FileWriter myWriter = new FileWriter(d + "obj", true);
			
			for(Vertex v: vArray) {
				myWriter.write("     v          " + v.toString() + "   \n");
			}
			for (Polygon p: polys) {
				myWriter.write("     f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 1));
				}
				myWriter.write("   \n");
			}
			myWriter.close();
			OBJMeshReader r = new OBJMeshReader();
			HashSet<Polygon> polygons = r.read(d + "obj");
		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(polys.equals(polygons));

	}
	
	//invalid v line
	@Test
	void testOBJMeshReaderReadCatching_1() throws FileNotFoundException{
		try {
			boolean thrown = false;
			FileWriter myWriter = new FileWriter(d+"obj", true);
			
			for(Vertex v: vArray) {
				myWriter.write("v          " + v.toString() + " $  \n");
			}
			for (Polygon p: polys) {
				myWriter.write("f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 1));
				}
				myWriter.write("   \n");
			}
			myWriter.close();
			OBJMeshReader r = new OBJMeshReader();
			try {
				r.read(d+"obj");
			}catch (WrongFileFormatException w) {
				thrown = true;
			}

		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(thrown);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//invalid f line
	@Test
	void testOBJMeshReaderReadCatching_2() throws FileNotFoundException{
		try {
			boolean thrown = false;
			FileWriter myWriter = new FileWriter(d+"obj", true);

			for(Vertex v: vArray) {
				myWriter.write("v          " + v.toString() + "  \n");
			}
			for (Polygon p: polys) {
				myWriter.write("f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 1));
				}
				myWriter.write("  $ \n");
			}
			myWriter.close();
			OBJMeshReader r = new OBJMeshReader();
			try {
				r.read(d+"obj");
			}catch (WrongFileFormatException w) {
				thrown = true;
			}

		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(thrown);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// v line after f line
	@Test
	void testOBJMeshReaderReadCatching_3() throws FileNotFoundException{
		try {
			boolean thrown = false;
			FileWriter myWriter = new FileWriter(d+"obj", true);

			for(Vertex v: vArray) {
				myWriter.write("v          " + v.toString() + "  \n");
			}
			for (Polygon p: polys) {
				myWriter.write("f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 1));
				}
				myWriter.write("   \n");
				myWriter.write("v          " + v1.toString() + "  \n");	
			}
			myWriter.close();
			OBJMeshReader r = new OBJMeshReader();
			try {
				r.read(d+"obj");
			}catch (WrongFileFormatException w) {
				thrown = true;
			}

		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(thrown);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// f out of bound
	@Test
	void testOBJMeshReaderReadCatching_4() throws FileNotFoundException{
		try {
			boolean thrown = false;
			FileWriter myWriter = new FileWriter(d+"obj", true);

			for(Vertex v: vArray) {
				myWriter.write("v          " + v.toString() + "  \n");
			}
			for (Polygon p: polys) {
				myWriter.write("f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 2));
				}
				myWriter.write("   \n");
			}
			myWriter.close();
			OBJMeshReader r = new OBJMeshReader();
			try {
				r.read(d+"obj");
			}catch (WrongFileFormatException w) {
				thrown = true;
			}

		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(thrown);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	void testOBJMeshWriter() throws WrongFileFormatException, IOException, FileNotFoundException{
		Vertex v1 = new Vertex(5.1, 1.2, 0.3);
		Vertex v2 = new Vertex(4.9, 1.2,0.3);
		Vertex v3 = new Vertex(3.8, 1.4, 0.5);
		Vertex v4 = new Vertex(4.1, 1.6, 0.6); 
		ArrayList<Vertex> vArray1 = new ArrayList<>(Arrays.asList(v1, v2, v3));
		ArrayList<Vertex> vArray2 = new ArrayList<>(Arrays.asList(v2, v3, v4));

		LinkedHashSet<Vertex> LHS1 = new LinkedHashSet<Vertex>(vArray1);
		LinkedHashSet<Vertex> LHS2 = new LinkedHashSet<Vertex>(vArray2);
		Polygon p1 = new Polygon(LHS1);
		Polygon p2 = new Polygon(LHS2);
		ArrayList<Polygon> pArray = new ArrayList<>(Arrays.asList(p1,p2));

		HashSet<Polygon> polys = new HashSet<Polygon>(pArray);
		
		OBJMeshReader r = new OBJMeshReader();
		OBJMeshWriter w = new OBJMeshWriter();
		w.write(d+"obj", polys);
		HashSet<Polygon> polygons = r.read(d+"obj");
		File myObj = new File(d+"obj");
	    myObj.delete();
		Assert.assertTrue(polygons.equals(polys));

	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	@Test
	void testOFFMeshReaderReadSuccess() throws WrongFileFormatException, FileNotFoundException, IOException{
			FileWriter myWriter = new FileWriter(d+"off", true);
			
			myWriter.write("OFF");
			myWriter.write("\n4 2 0");
			for(Vertex v: vArray) {
				
				myWriter.write("\n" + v.toString());
			}
			for (Polygon p: polys) {
				myWriter.write("\n3 ");
				for (Vertex vv: p.vertices) {
					myWriter.write(" " + ( vArray.indexOf(vv)));
				}
				myWriter.write(" 0 1 2");
			}
			myWriter.close();
			OFFMeshReader r = new OFFMeshReader();
			HashSet<Polygon> polygons = r.read(d+"off");
		    File myObj = new File(d+"off");
		    myObj.delete();
			Assert.assertTrue(polys.equals(polygons));
	}
	
	@Test
	void testOFFMeshReaderReadSuccess_2() throws WrongFileFormatException, FileNotFoundException, IOException{
			FileWriter myWriter = new FileWriter(d+"off", true);
			
			myWriter.write("OFF");
			myWriter.write("\n4 2 0");
			for(Vertex v: vArray) {
				
				myWriter.write("\n" + v.toString());
			}
			for (Polygon p: polys) {
				myWriter.write("\n3 ");
				for (Vertex vv: p.vertices) {
					myWriter.write(" " + ( vArray.indexOf(vv)));
				}
			}
			myWriter.close();
			OFFMeshReader r = new OFFMeshReader();
			HashSet<Polygon> polygons = r.read(d+"off");
		    File myObj = new File(d+"off");
		    myObj.delete();
			Assert.assertTrue(polys.equals(polygons));
	}
	
	//invalid header line
		@Test
		void testOFFMeshReaderReadCatching_0() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0 1");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 0 1 2");
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//	
	//invalid v line
		@Test
		void testOFFMeshReaderReadCatching_1() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				myWriter.write("abc");
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 0 1 2");
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	//invalid f line
		@Test
		void testOFFMeshReaderReadCatching_2() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 0 1 2 5");
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	// v line after f line
		@Test
		void testOFFMeshReaderReadCatching_3() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 0 1 2");
					myWriter.write("\n" + v2.toString());
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	
	// f out of bound
		@Test
		void testOFFMeshReaderReadCatching_4() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv) + 2));
					}
					myWriter.write(" 0 1 2");
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// RGB >255
		@Test
		void testOFFMeshReaderReadCatching_RGB() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 266 300 266");
				}
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// RGB <0
				@Test
				void testOFFMeshReaderReadCatching_RGB_2() throws FileNotFoundException{
					try {
						boolean thrown = false;
						FileWriter myWriter = new FileWriter(d+"off", true);

						myWriter.write("OFF");
						myWriter.write("\n4 2 0");
						for(Vertex v: vArray) {		
							myWriter.write("\n" + v.toString());
						}
						
						for (Polygon p: polys) {
							myWriter.write("\n3 ");
							for (Vertex vv: p.vertices) {
								myWriter.write(" " + ( vArray.indexOf(vv)));
							}
							myWriter.write(" -5 -1 -2");
						}
						
						myWriter.close();
						OFFMeshReader r = new OFFMeshReader();
						try {
							r.read(d+"off");
						}catch (WrongFileFormatException w) {
							thrown = true;
						}

					    File myObj = new File(d+"off");
					    myObj.delete();
						Assert.assertTrue(thrown);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		//<8 lines
		@Test
		void testOFFMeshReaderReadCatching_5() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);
				
				myWriter.write("OFF");
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//not enough v
		@Test
		void testOFFMeshReaderReadCatching_6() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);
				Vertex v1 = new Vertex(5.1, 1.2, 0.3);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				myWriter.write("\n" + v1.toString());
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//not enough f
		@Test
		void testOFFMeshReaderReadCatching_7() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				myWriter.write("\n3 ");
				for (Vertex vv: p1.vertices) {
					myWriter.write(" " + ( vArray.indexOf(vv)));
				}
				myWriter.write(" 0 1 2");
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	//big file
		@Test
		void testOFFMeshReaderReadCatching_8() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"off", true);

				myWriter.write("OFF");
				myWriter.write("\n4 2 0");
				for(Vertex v: vArray) {		
					myWriter.write("\n" + v.toString());
				}
				
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write(" " + ( vArray.indexOf(vv)));
					}
					myWriter.write(" 0 1 2");
				}
				myWriter.write("\n" + v1.toString());
				
				myWriter.close();
				OFFMeshReader r = new OFFMeshReader();
				try {
					r.read(d+"off");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"off");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	


	@Test
	void testOFFMeshWriter() throws WrongFileFormatException, IOException, FileNotFoundException{
		Vertex v1 = new Vertex(5.1, 1.2, 0.3);
		Vertex v2 = new Vertex(4.9, 1.2,0.3);
		Vertex v3 = new Vertex(3.8, 1.4, 0.5);
		Vertex v4 = new Vertex(4.1, 1.6, 0.6);
		ArrayList<Vertex> vArray1 = new ArrayList<>(Arrays.asList(v1, v2, v3));
		ArrayList<Vertex> vArray2 = new ArrayList<>(Arrays.asList(v2, v3, v4));

		LinkedHashSet<Vertex> LHS1 = new LinkedHashSet<Vertex>(vArray1);
		LinkedHashSet<Vertex> LHS2 = new LinkedHashSet<Vertex>(vArray2);
		Polygon p1 = new Polygon(LHS1);
		Polygon p2 = new Polygon(LHS2);
		ArrayList<Polygon> pArray = new ArrayList<>(Arrays.asList(p1,p2));

		HashSet<Polygon> polys = new HashSet<Polygon>(pArray);
		
		OFFMeshReader r = new OFFMeshReader();
		OFFMeshWriter w = new OFFMeshWriter();
		w.write(d+"off", polys);
		HashSet<Polygon> polygons = r.read(d+"off");
		
		File myObj = new File(d+"off");
	    myObj.delete();
		Assert.assertTrue(polygons.equals(polys));

	}

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	
	@Test
	void testPLYMeshReaderReadSuccess() throws WrongFileFormatException, FileNotFoundException, IOException{
		try {
			FileWriter myWriter = new FileWriter(d+"ply", true);
			
			myWriter.write(REGEX[0]);
			for(int i =1; i <9; i++) {
				myWriter.write("\n" + REGEX[i]);
			}
			for(Vertex v: vArray) {
				
				myWriter.write("\n" + v.toString());
			}
			for (Polygon p: polys) {
				myWriter.write("\n3 ");
				for (Vertex vv: p.vertices) {
					myWriter.write(" " + ( vArray.indexOf(vv)));
				}
			}
			myWriter.close();
			PLYMeshReader r = new PLYMeshReader();
			HashSet<Polygon> polygons = r.read(d+"ply");
		    File myObj = new File(d+"ply");
		    myObj.delete();
			Assert.assertTrue(polys.equals(polygons));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//invalid header line
		@Test
		void testPLYMeshReaderReadCatching_0() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				String[] REGEX2 = {"ply abc", "format ascii 1.0", "element vertex 4", 
						"property float32 x", "property float32 y", "property float32 z",
						"element face 2", "property list uint8 int32 vertex_indices",
						"end_header"};
				myWriter.write(REGEX2[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX2[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)));
					}
				}
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//	
	//invalid v line
		@Test
		void testPLYMeshReaderReadCatching_1() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString() + " 0");
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)));
					}
				}
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");			
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	//invalid f line
		@Test
		void testPLYMeshReaderReadCatching_2() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)) + " & ");
					}
				}
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	// v line after f line
		@Test
		void testPLYMeshReaderReadCatching_3() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)));
					}
				}
				myWriter.write("\n" + v1.toString());
				
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	
	// f out of bound
		@Test
		void testPLYMeshReaderReadCatching_4() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv) +3));
					}
				}
				
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//<8 lines
		@Test
		void testPLYMeshReaderReadCatching_5() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <8; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
			
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//not enough v
		@Test
		void testPLYMeshReaderReadCatching_6() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}	
				
				myWriter.write("\n" + v1.toString());
	
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//not enough f
		@Test
		void testPLYMeshReaderReadCatching_7() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				myWriter.write(REGEX[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				myWriter.write("\n3 ");
				for (Vertex vv: p1.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)));
					}
				
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	//big file
		@Test
		void testPLYMeshReaderReadCatching_8() throws FileNotFoundException{
			try {
				boolean thrown = false;
				FileWriter myWriter = new FileWriter(d+"ply", true);

				String[] REGEX2 = {"ply", "format ascii 1.0", "element vertex 4", 
						"property float32 x", "property float32 y", "property float32 z",
						"element face 1", "property list uint8 int32 vertex_indices",
						"end_header"};
				myWriter.write(REGEX2[0]);
				for(int i =1; i <9; i++) {
					myWriter.write("\n" + REGEX2[i]);
				}
				for(Vertex v: vArray) {
					
					myWriter.write("\n" + v.toString());
				}
				for (Polygon p: polys) {
					myWriter.write("\n3 ");
					for (Vertex vv: p.vertices) {
						myWriter.write("        " + ( vArray.indexOf(vv)));
					}
				}
				
				myWriter.close();
				PLYMeshReader r = new PLYMeshReader();
				try {
					r.read(d+"ply");
				}catch (WrongFileFormatException w) {
					thrown = true;
				}

			    File myObj = new File(d+"ply");
			    myObj.delete();
				Assert.assertTrue(thrown);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	@Test
	void testPLYMeshWriter() throws WrongFileFormatException, IOException, FileNotFoundException{
		Vertex v1 = new Vertex(5.1, 1.2, 0.3);
		Vertex v2 = new Vertex(4.9, 1.2,0.3);
		Vertex v3 = new Vertex(3.8, 1.4, 0.5);
		Vertex v4 = new Vertex(4.1, 1.6, 0.6);
		ArrayList<Vertex> vArray1 = new ArrayList<>(Arrays.asList(v1, v2, v3));
		ArrayList<Vertex> vArray2 = new ArrayList<>(Arrays.asList(v2, v3, v4));

		LinkedHashSet<Vertex> LHS1 = new LinkedHashSet<Vertex>(vArray1);
		LinkedHashSet<Vertex> LHS2 = new LinkedHashSet<Vertex>(vArray2);
		Polygon p1 = new Polygon(LHS1);
		Polygon p2 = new Polygon(LHS2);
		ArrayList<Polygon> pArray = new ArrayList<>(Arrays.asList(p1,p2));

		HashSet<Polygon> polys = new HashSet<Polygon>(pArray);
		
		PLYMeshReader r = new PLYMeshReader();
		PLYMeshWriter w = new PLYMeshWriter();
		w.write(d+"ply", polys);
		HashSet<Polygon> polygons = r.read(d+"ply");
		
		File myObj = new File(d+"ply");
	    myObj.delete();
		Assert.assertTrue(polygons.equals(polys));

	}

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	@Test
	void testMeshHashCodeTrue() {
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
		HashSet<Polygon> polys = new HashSet<Polygon>();
		polys.add(p2);
		polys.add(p1);
		Mesh mesh = new Mesh();
		mesh.polygons = polys;
	    Assert.assertTrue(mesh1.hashCode() == mesh.hashCode());	

	}
	
	
	@Test
	void testMeshHashCodeFalse() {
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
		Mesh mesh2 = new Mesh();
		mesh2.polygons = polys2;
	    Assert.assertFalse(mesh1.hashCode() == mesh2.hashCode());	

	}

	@Test
	void testMeshTransform() {
		double[][] matrix = {
				{1, 1, 1},
				{1, 1, 1},
				{1, 1, 1},
		};
		Vertex v1a = new Vertex(3, 3, 3);
		Vertex v2a = new Vertex(6, 6, 6);
		Vertex v3a = new Vertex(9, 9, 9);
		Vertex v4a = new Vertex(12, 12, 12);
		Mesh meshA = new Mesh();
		meshA.polygons = polys1;
		meshA.transform(matrix);
		
		Mesh meshB = new Mesh();
		ArrayList<Vertex> vArrayA = new ArrayList<>(Arrays.asList(v1a, v2a, v3a));
		ArrayList<Vertex> vArrayB = new ArrayList<>(Arrays.asList(v2a, v3a, v4a));
		LinkedHashSet<Vertex> LHSa = new LinkedHashSet<Vertex>(vArrayA);
		LinkedHashSet<Vertex> LHSb = new LinkedHashSet<Vertex>(vArrayB);
		Polygon p1a = new Polygon(LHSa);
		Polygon p2a = new Polygon(LHSb);
		
		ArrayList<Polygon> pArrayC = new ArrayList<>(Arrays.asList(p1a,p2a));

		HashSet<Polygon> polyC = new HashSet<Polygon>(pArrayC);
		meshB.polygons = polyC;
		
	    Assert.assertTrue(meshA.equals(meshB));

		
		
	}

	@Test
	void testMeshSetReader() {
		Mesh mesh = new Mesh();
		OBJMeshReader r = new OBJMeshReader();
		mesh.setReader(r);
		Assert.assertTrue(mesh.reader == r);
	}

	@Test
	void testMeshSetWriter() {
		Mesh mesh = new Mesh();
		OBJMeshWriter w = new OBJMeshWriter();
		mesh.setWriter(w);
		Assert.assertTrue(mesh.writer == w);
	}

	@Test
	void testMeshReadFromFile() throws WrongFileFormatException{
		try {
			FileWriter myWriter = new FileWriter(d+"obj", true);
			
			for(Vertex v: vArray) {
				myWriter.write("v          " + v.toString() + "   \n");
			}
			for (Polygon p: polys1) {
				myWriter.write("f");
				for (Vertex vv: p.vertices) {
					myWriter.write("        " + ( vArray.indexOf(vv) + 1));
				}
				myWriter.write("   \n");
			}
			myWriter.close();
			Mesh mesh = new Mesh();
			mesh.setReader(new OBJMeshReader());
			mesh.readFromFile(d+"obj");
		    File myObj = new File(d+"obj");
		    myObj.delete();
			Assert.assertTrue(polys1.equals(mesh.polygons));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testMeshWriteToFile()  throws WrongFileFormatException, IOException, FileNotFoundException{
		Mesh mesh = new Mesh();
		mesh.setReader(new OBJMeshReader());
		mesh.setWriter(new OBJMeshWriter());
		mesh.polygons = polys1;
		mesh.writeToFile(d+"obj");
		mesh.readFromFile(d+"obj");
		File myObj = new File(d+"obj");
	    myObj.delete();
		Assert.assertTrue(mesh.polygons.equals(polys1));
	}

	@Test
	void testMeshEqualsObjectTrue_1() {
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
		HashSet<Polygon> polys = new HashSet<Polygon>();
		polys.add(p2);
		polys.add(p1);
		Mesh mesh = new Mesh();
		mesh.polygons = polys;
	    Assert.assertTrue(mesh1.equals(mesh));	
	}
	
	@Test
	void testMeshEqualsObjectTrue_2() {
		Mesh mesh1 = new Mesh();
	    Assert.assertTrue(mesh1.equals(mesh1));	
	}
	
	@Test
	void testMeshEqualsObjectFalse_1() {
		String s = new String("hi");
		Mesh mesh = new Mesh();
	    Assert.assertFalse(mesh.equals(s));
	    }
	
	@Test
	void testMeshEqualsObjectFalse_2() {
		ArrayList<Polygon> pArray = new ArrayList<>(Arrays.asList(p1,p2, p3));

		HashSet<Polygon> polys = new HashSet<Polygon>(pArray);
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys;
		
		Mesh mesh2 = new Mesh();
		mesh2.polygons = polys1;
	    Assert.assertFalse(mesh1.equals(mesh2));
	}
	
	@Test
	void testMeshEqualsObjectFalse_3() {
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
		Mesh mesh2 = new Mesh();
		mesh2.polygons = polys2;
	    Assert.assertFalse(mesh1.equals(mesh2));
	}
	
	
	@Test
	void testMeshEqualsObjectFalse_4() {
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
	    Assert.assertFalse(mesh1.equals(null));
	}
	
	@Test
	void testMeshEqualsObjectFalse_5() {
		Mesh mesh = new Mesh();
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
	    Assert.assertFalse(mesh1.equals(mesh));
	}
	
	@Test
	void testMeshEqualsObjectFalse_6() {
		Mesh mesh = new Mesh();
		Mesh mesh1 = new Mesh();
		mesh1.polygons = polys1;
	    Assert.assertFalse(mesh.equals(mesh1));
	}
	

	@Test
	void testMeshRotateXAxis() {
		Vertex v1 = new Vertex(1, 1, 1);
		Vertex v2 = new Vertex(1, -1, 1);
		
		LinkedHashSet<Vertex> vertices1 = new LinkedHashSet<Vertex>();
		vertices1.add(v1);
		
		LinkedHashSet<Vertex> vertices2 = new LinkedHashSet<Vertex>();
		vertices2.add(v2);
		
		Polygon p1 = new Polygon(vertices1);
		Polygon p2 = new Polygon(vertices2);
		
		HashSet<Polygon> polygons1 = new HashSet<Polygon>();
		polygons1.add(p1);
		HashSet<Polygon> polygons2 = new HashSet<Polygon>();
		polygons2.add(p2);
		
		Mesh mesh1 = new Mesh();
		Mesh mesh2 = new Mesh();
		mesh1.polygons = polygons1;
		mesh2.polygons = polygons2;
		mesh1.rotateXAxis(Math.PI/2);


		Assert.assertTrue(mesh1.equals(mesh2));

	}



}
