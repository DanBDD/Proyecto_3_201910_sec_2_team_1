package TestBFS;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.ArregloDinamico;
import model.data_structures.BFS;
import model.data_structures.Bag;
import model.data_structures.Graph;

public class TestBFS extends TestCase{

	private Graph<Long, String,Double> graph;
	@Before
	public void arreglar() throws Exception
	{
		graph=new Graph<Long, String, Double>();
		Bag<Long> a = new Bag<Long>();
		a.add(196L);
		a.add(128L);
		graph.addVertex(12L, 1208000+"|"+4651535, null, a);
		Bag<Long>c=new Bag<Long>();
		c.add(120L);
		graph.addVertex(196L, 1202000+"|"+4651535, null, c);
		graph.addVertex(120L, 1204000+"|"+4651535, null, null);
		Bag<Long> b= new Bag<Long>();
		b.add(196L);
		graph.addVertex(128L, 1200800+"|"+4651535, null, b);
		graph.addEdge(12L, 196L, 3.0);
		graph.addEdge(12L, 128L, 3.0);
		graph.addEdge(128L, 196L, 3.0);
		graph.addEdge(196L, 120L, 3.0);
	}
	@Test
	public void setUp() throws Exception
	{	
		
		BFS<Long, String, Double> b= new BFS<Long, String, Double>(graph, 12L);
		Iterable<Long> a = b.pathTo(196L);
		Iterator<Long> i=a.iterator();
		while(i.hasNext())
		{
			System.out.println(i.next());
		}
		
	}

}
