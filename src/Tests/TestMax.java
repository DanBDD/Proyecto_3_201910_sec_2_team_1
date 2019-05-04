package Tests;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.*;

public class TestMax extends TestCase{
	protected static final int[] ARREGLO = {350, 383, 105, 233, 140, 266, 356, 236, 80, 360, 221, 241, 130, 244, 352, 446, 18, 98, 97, 396};

	protected MaxColaPrioridad<Integer> cola;
	protected MaxHeapCP<Integer> heap;
	@Before
	public void setUp()
	{
		cola= new MaxColaPrioridad<Integer>();
		heap=new MaxHeapCP<Integer>();
		for(int actual: ARREGLO)
		{
			cola.agregar(actual);
			heap.agregar(actual);
		}

	}
	@Test
	public void testSize()
	{
		assertEquals(cola.darNumElementos(), 20);
		assertEquals(heap.darNumElementos(), 20);

	}
	@Test
	public void testDarMax(){
		assertEquals(heap.max(), Integer.valueOf(446));
		assertEquals(cola.max(), Integer.valueOf(446));
		
	}
	@Test
	public void testDelMax(){
		assertEquals(cola.delMax(), Integer.valueOf(446));
		assertEquals(cola.darNumElementos(), 19);
		assertEquals(heap.delMax(), Integer.valueOf(446));
		assertEquals(heap.darNumElementos(), 19);
		
		
	}
	
}

