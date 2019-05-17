package TestBFS;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.ArregloDinamico;
import model.data_structures.MinHeapCP;

public class TestArrDinamico extends TestCase {
	
	private int[] lista = {12,22,60,43,2,1,33,0};
	private ArregloDinamico<Integer> arr;
	
	@Before
	public void setUp() {
		arr = new ArregloDinamico<Integer>(8);
		for(int i : lista) {
			
			arr.agregar(i);
		}
	}
	
	@Test
	public void testSize() {
		assertEquals(8, arr.darTamano());
	}
	
	@Test
	public void testContains() {
		assertTrue(arr.contains(12));
		assertTrue(arr.contains(0));
		arr.cambiarElementoEnPos(null, 0);
		assertEquals(null, arr.darElem(0));
		arr.cambiarElementoEnPos(3, 0);
		assertEquals(Integer.valueOf(3), arr.darElem(0));
		
	}
}
