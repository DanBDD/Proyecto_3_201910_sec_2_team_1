package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class MinHeapCP<T extends Comparable<T>> implements ColaDePrioridad<T>, Iterable<T>
{
	private int N;
	private int max;
	private T[] heap;

	public MinHeapCP()
	{
		max=2;
		heap = (T[])new Comparable[max+1];
		N=0;
		
	}
	private boolean less(int i, int j)
	{  
		return heap[i].compareTo(heap[j]) > 0;  
	}
	private void exch(int i, int j)
	{ 
		T t = heap[i]; 
		heap[i] = heap[j]; 
		heap[j] = t; 
	}
	private void swim(int k)
	{
		while (k > 1 && less(k/2, k))
		{
			exch(k/2, k);
			k = k/2; 
		}
	}
	private void sink(int k)
	{
		while (2*k <= N)
		{
			int j = 2*k;
			if (j < N && less(j, j+1)){ 
				j++;
			}
			if (!less(k, j)){
				break;
			}
			exch(k, j);
			k = j;
		} }
	@Override
	public int darNumElementos() {
		return N;
	}

	@Override
	public T delMax() {
		T temp=heap[1];
		exch(1, N--);
		heap[N+1] = null;
		sink(1);
		return temp;
	}

	@Override
	public T max() {
		return heap[1];
	}

	@Override
	public boolean estaVacia() {
		return N==0;
	}

	@Override
	public void agregar(T elemento) {
		 if ( N == max )
         {  // caso de arreglo lleno (aumentar tamaNo)
              max*=2;
              T [ ] copia = heap;
              heap = (T[])new Comparable[max+1];
              for ( int i = 0; i <= N; i++)
              {
               	 heap[i] = copia[i];
              } 
         }	
         heap[++N] = elemento;
         swim(N);
       
	}
    public Iterator<T> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<T> {
        // create a new pq
        private MinHeapCP<T> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new MinHeapCP<T>();
            for ( int i = 0; i <= N; i++)
            {
             	 heap[i] = copy.heap[i];
            } 
            
        }

        public boolean hasNext()  { return !copy.estaVacia();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }

}


