package model.data_structures;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth first search on an undirected graph.
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
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/

/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex <em>s</em> to every other vertex
 *  in an undirected graph.
 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@link #hasPathTo(int)} takes constant time;
 *  each call to {@link #pathTo(int)} takes time proportional to the length
 *  of the path.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>   
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DFS<K extends Comparable<K>,V,D>{
	private LinearProbing<Long, Boolean> marked;
	private LinearProbing<Long, Long> edgeTo;
	private LinearProbing<Long, Vertex<Long, String, Double>> lista;
	private Bag<Long> vertices;

	private final long s;         // source vertex

	/**
	 * Computes a path between {@code s} and every other vertex in graph {@code G}.
	 * @param G the graph
	 * @param s the source vertex
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public DFS(Graph G, long s) {
		this.s = s;
		edgeTo = new LinearProbing<Long, Long>(G.V());
		marked = new LinearProbing<Long, Boolean>(G.V());
		vertices = new Bag<Long>();
		lista= G.getV();
		validateVertex(s);
		dfs(G, s);
	}

	public DFS(Graph<Long, String, Double> grafo1, long a, LinearProbing<Long, Boolean> marked2) {
		this.s = a;
		edgeTo = new LinearProbing<Long, Long>(grafo1.V());
		marked = marked2;
		lista= grafo1.getV();
		vertices = new Bag<Long>();
		validateVertex(a);
		dfs(grafo1, a);	
	}

	// depth first search from v
	private void dfs(Graph G, long s2) 
	{
		vertices.add(s2);
		marked.put(s2, true);
		Vertex<Long, String, Double> vertice = lista.get(s2);
		for (long w : vertice.getIds()) {
			if(lista.get(w)!=null)
			{
				if (marked.get(w)==false) 
				{
					edgeTo.put(w, s2);
					dfs(G, w);
				}
			}
		}
	}
	public LinearProbing<Long, Boolean> darMarcados()
	{
		return marked;
	}
	public Bag<Long> darBagDeVertices()
	{
		return vertices;
	}

	/**
	 * Is there a path between the source vertex {@code s} and vertex {@code v}?
	 * @param v the vertex
	 * @return {@code true} if there is a path, {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(long v) {
		validateVertex(v);
		return marked.get(v);
	}

	/**
	 * Returns a path between the source vertex {@code s} and vertex {@code v}, or
	 * {@code null} if no such path.
	 * @param  v the vertex
	 * @return the sequence of vertices on a path between the source vertex
	 *         {@code s} and vertex {@code v}, as an Iterable
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Long> pathTo(long v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Stack<Long> path = new Stack<Long>();
		for (long x = v; x != s; x = edgeTo.get(x))
			path.push(x);
		path.push(s);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(long v) {
		if (lista.get(v)==null)
		{
			throw new IllegalArgumentException("vertex not found");
		}
	}
}
