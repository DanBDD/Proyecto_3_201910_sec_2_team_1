package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IndexMinPQ <Key extends Comparable<Key>> implements Iterable<Long> {
	private int maxN;        // maximum number of elements on PQ
	private int n;           // number of elements on PQ
	//    private int[] pq;        // binary heap using 1-based indexing
	private LinearProbing<Integer, Long> pq;
	//    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	private LinearProbing<Long, Integer> qp;
	//    private Key[] keys;      // keys[i] = priority of i
	private LinearProbing<Long, Key> keys;
	/**
	 * Initializes an empty indexed priority queue with indices between {@code 0}
	 * and {@code maxN - 1}.
	 * @param  maxN the keys on this priority queue are index from {@code 0}
	 *         {@code maxN - 1}
	 * @throws IllegalArgumentException if {@code maxN < 0}
	 */
	public IndexMinPQ(int maxN) {
		if (maxN < 0) throw new IllegalArgumentException();
		this.maxN = maxN;
		n = 0;
		//        keys = (Key[]) new Comparable[maxN + 1];    // make this of length maxN??
		keys=new LinearProbing<>(maxN+1);
		//        pq   = new int[maxN + 1];
		pq=new LinearProbing<>(maxN+1);
		//        qp   = new int[maxN + 1];         
		qp=new LinearProbing<>(maxN+1);
		// make this of length maxN??
		Iterator<Long> i = qp.keys();
		while(i.hasNext())
		{
			qp.put(i.next(), -1);
		}
		//        for (int i = 0; i <= maxN; i++)
		//            qp[i] = -1;
	}

	/**
	 * Returns true if this priority queue is empty.
	 *
	 * @return {@code true} if this priority queue is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Is {@code i} an index on this priority queue?
	 *
	 * @param  s an index
	 * @return {@code true} if {@code i} is an index on this priority queue;
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 */
	public boolean contains(long s) {
		return qp.get(s) != null;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Associates key with index {@code i}.
	 *
	 * @param  s an index
	 * @param  key the key to associate with index {@code i}
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException if there already is an item associated
	 *         with index {@code i}
	 */
	public void insert(long s, Key key) {
		
		if (contains(s)) throw new IllegalArgumentException("index is already in the priority queue");
		n++;
		//        qp[s] = n;
		qp.put(s, n);
		//        pq[n] = s;
		pq.put(n, s);
		//        keys[s] = key;
		keys.put(s, key);
		swim(n);
	}

	/**
	 * Removes a minimum key and returns its associated index.
	 * @return an index associated with a minimum key
	 * @throws NoSuchElementException if this priority queue is empty
	 */
	public long delMin() {
		if (n == 0) throw new NoSuchElementException("Priority queue underflow");
		Iterator<Integer> i = pq.keys();
		long min = pq.get(i.next());
		exch(1, n--);
		sink(1);
		//assert min == pq[n+1];
		qp.put(min, -1);
		//qp[min] = -1;        // delete
		keys.put(min, null);
		//keys[min] = null;    // to help with garbage collection
		pq.put(n+1, Long.parseLong("-1"));
		//pq[n+1] = -1;        // not needed
		return min;
	}



	/**
	 * Decrease the key associated with index {@code i} to the specified value.
	 *
	 * @param  i the index of the key to decrease
	 * @param  key decrease the key associated with index {@code i} to this key
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException if {@code key >= keyOf(i)}
	 * @throws NoSuchElementException no key is associated with index {@code i}
	 */
	public void decreaseKey(long i, Key key) {
		
		if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
		if (keys.get(i).compareTo(key) <= 0)
			throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
		keys.put(i, key);
		swim(qp.get(i));
	}


	/***************************************************************************
	 * General helper functions.
	 ***************************************************************************/
	private boolean greater(int i, int j) {
		return keys.get(pq.get(i)).compareTo(keys.get(pq.get(j))) > 0;
	}

	private void exch(int i, int j) {
		long swap = pq.get(i);
		pq.put(i, pq.get(j));
		pq.put(j, swap);
		qp.put(pq.get(i), i);
		qp.put(pq.get(j), j);
	}


	/***************************************************************************
	 * Heap helper functions.
	 ***************************************************************************/
	private void swim(int k) {
		while (k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= n) {
			int j = 2*k;
			if (j < n && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}


	/***************************************************************************
	 * Iterators.
	 ***************************************************************************/

	/**
	 * Returns an iterator that iterates over the keys on the
	 * priority queue in ascending order.
	 * The iterator doesn't implement {@code remove()} since it's optional.
	 *
	 * @return an iterator that iterates over the keys in ascending order
	 */
	public Iterator<Long> iterator() { return new HeapIterator(); }

	private class HeapIterator implements Iterator<Long> {
		// create a new pq
		private IndexMinPQ<Key> copy;

		// add all elements to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			copy = new IndexMinPQ<Key>(pq.size() - 1);
			Iterator<Integer> i = pq.keys();
			while(i.hasNext())
			{
				Integer actual = i.next();
				copy.insert(pq.get(actual), keys.get(pq.get(actual)));
			}
			
		}
		

		public boolean hasNext()  { return !copy.isEmpty();                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Long next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMin();
		}

	}
}
