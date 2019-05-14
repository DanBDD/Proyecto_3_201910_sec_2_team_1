package Tests;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.*;

public class TestMax extends TestCase{
	protected static final int[] ARREGLO = {350, 383, 105, 233, 140, 266, 356, 236, 80, 360, 221, 241, 130, 244, 352, 446, 18, 98, 97, 396};

	protected MaxColaPrioridad<Integer,Integer> cola;
	@Before
	public void setUp()
	{
		cola= new MaxColaPrioridad<Integer,Integer>();
		for(int actual: ARREGLO)
		{
			cola.agregar(actual);
		}

	}
	@Test
	public void testSize()
	{
		assertEquals(cola.darNumElementos(), 20);

	}
	@Test
	public void testDarMax(){
		assertEquals(cola.max(), Integer.valueOf(18));
		
	}
	@Test
	public void testDelMax(){
		assertEquals(cola.delMax(), Integer.valueOf(18));
		assertEquals(cola.darNumElementos(), 19);
		
		
	}
	
}

