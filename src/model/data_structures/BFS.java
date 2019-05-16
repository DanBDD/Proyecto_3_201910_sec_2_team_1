package model.data_structures;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac BreadthFirstPaths.java
 *  Execution:    java BreadthFirstPaths G s
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run breadth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5 
 *  1: 0 2 
 *  2: 0 1 3 4 
 *  3: 5 4 2 
 *  4: 3 2 
 *  5: 3 0 
 *
 *  %  java BreadthFirstPaths tinyCG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (1):  0-1
 *  0 to 2 (1):  0-2
 *  0 to 3 (2):  0-2-3
 *  0 to 4 (2):  0-2-4
 *  0 to 5 (1):  0-5
 *
 *  %  java BreadthFirstPaths largeG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (418):  0-932942-474885-82707-879889-971961-...
 *  0 to 2 (323):  0-460790-53370-594358-780059-287921-...
 *  0 to 3 (168):  0-713461-75230-953125-568284-350405-...
 *  0 to 4 (144):  0-460790-53370-310931-440226-380102-...
 *  0 to 5 (566):  0-932942-474885-82707-879889-971961-...
 *  0 to 6 (349):  0-932942-474885-82707-879889-971961-...
 *
 ******************************************************************************/


/**
 *  The {@code BreadthFirstPaths} class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or a set of source vertices)
 *  to every other vertex in an undirected graph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #distTo(int)} and {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BFS <K extends Comparable<K>,V,D>{
	private static final int INFINITY = Integer.MAX_VALUE;
	private LinearProbing<Long, Boolean> marked;
	private LinearProbing<Long, Long> edgeTo;
	private LinearProbing<Long, Integer> distTo;

	private LinearProbing<Long, Vertex<K, V, D>> lista;
	/**
	 * Computes the shortest path between the source vertex {@code s}
	 * and every other vertex in the graph {@code G}.
	 * @param G del grafo
	 * @param s id del vertice de inicio
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public BFS(Graph G, long s) {
		marked = new LinearProbing<>(G.V());
		distTo= new LinearProbing<>(G.V());
		edgeTo= new LinearProbing<>(G.V());
		lista= G.getV();
		validateVertex(s);
		bfs(G, s);
	}

	// breadth-first search from a single source
	// s posicion de source en arreglo
	// arreglo con los ids del grafo
	private void bfs(Graph G, long s) 
	{
		Cola<Long> q = new Cola<Long>();
		Iterator<Long> it= lista.keys();
		while(it.hasNext())
		{
			Long v = it.next();
			distTo.put(v, INFINITY);
			marked.put(v, false);
		}
		distTo.put(s, 0);
		marked.put(s, true);
		q.enqueue(s);
		while (!q.isEmpty()) {
			long v = q.dequeue();
			Vertex<K, V, D> vertice = lista.get(v);
			for (long w : vertice.getIds()) {
				if (marked.get(w)==false) 
				{
					edgeTo.put(w, v);
					distTo.put(w, distTo.get(v)+1);
					marked.put(w, true);
					q.enqueue(w);
				}
			}
		}
	}
	/**
	 * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
	 * @param v the vertex
	 * @return {@code true} if there is a path, and {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(long v) {
		validateVertex(v);
		return marked.get(v);
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex {@code s}
	 * (or sources) and vertex {@code v}?
	 * @param v the vertex
	 * @return the number of edges in a shortest path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public long distTo(long v) {
		validateVertex(v);
		return distTo.get(v);
	}

	/**
	 * Returns a shortest path between the source vertex {@code s} (or sources)
	 * and {@code v}, or {@code null} if no such path.
	 * @param  v the vertex
	 * @param arregloIdsGrafo 
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Long> pathTo(long v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Long> path = new Stack<Long>();
		long x;
		for (x = v; distTo.get(x) != 0; x = edgeTo.get(x))
		{
			path.push(x);
		}
		path.push(x);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(long v) {
		if (lista.get(v)==null)
			throw new IllegalArgumentException("vertex not found");
	}

}

