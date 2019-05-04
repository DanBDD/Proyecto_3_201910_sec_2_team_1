package Tests;


import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import model.data_structures.ArregloDinamico;
import model.data_structures.Graph;

public class TestGrafo extends TestCase{
	protected static final int[] ARREGLO = {35078905, 38478383, 15647305, 27845633, 14094567, 23462466, 35390726, 23173456, 3648080, 36289470};

	protected Graph<Integer, String, Double> arr;
	@Before
	public void setUp()
	{
		arr = new Graph<>();
		for(int actual: ARREGLO)
		{
			arr.addVertex(actual,actual+"|"+actual);
		}
		arr.addEdge(35078905, 38478383, 2.0);
		arr.addEdge(35078905, 15647305, 2.0);
		arr.addEdge(35078905, 27845633, 2.0);
		arr.addEdge(38478383, 15647305, 2.0);		
		arr.addEdge(38478383, 23462466, 2.0);
		arr.addEdge(27845633, 3648080, 2.0);
		arr.addEdge(23173456, 3648080, 2.0);
		arr.addEdge(23173456, 36289470, 2.0);	

	}
	@Test
	public void testSize()
	{
		//Vertices
		assertEquals(arr.V(), 10);
		//Arcos
		assertEquals(arr.E(), 8);
		//Set info vertex
		arr.setInfoVertex(35078905, "444|444");
		assertEquals(arr.getV().get(35078905).getValue(),"444|444");
		//getInfoEdge()
		assertEquals(arr.getInfoEdge(27845633, 3648080), new Double(2));
		//setInfoEdge()
		assertEquals(arr.getInfoEdge(23173456, 3648080), new Double(2));
		arr.setInfoEdge(23173456, 3648080, new Double(3));
		assertEquals(arr.getInfoEdge(23173456, 3648080), new Double(3));
		//Lista de ids de los vertices adjacentes
		Iterator<Integer> i = arr.adj(35078905);
		while(i.hasNext())
		{
			Integer actual = i.next();
			System.out.println(actual);
		}
	}
}

