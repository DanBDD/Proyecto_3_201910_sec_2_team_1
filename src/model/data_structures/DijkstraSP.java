package model.data_structures;

/**
 *  The {@code DijkstraSP} class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Each call to {@code distTo(int)} and {@code hasPathTo(int)} takes constant time;
 *  each call to {@code pathTo(int)} takes time proportional to the number of
 *  edges in the shortest path returned.
 *  <p>
 *  For additional documentation,    
 *  see <a href="https://algs4.cs.princeton.edu/44sp">Section 4.4</a> of    
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DijkstraSP {
	private double[] distTo;          // distTo[v] = distance  of shortest s->v path
	private Edge<Long,String,Double>[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
	private IndexMinPQ<Double> pq;    // priority queue of vertices
	private LinearProbing<Integer, Long> indice;
	private LinearProbing<Long, Integer> indiceT;
	private long s;

	/**
	 * Computes a shortest-paths tree from the source vertex {@code s} to every other
	 * vertex in the edge-weighted digraph {@code G}.
	 *
	 * @param  G the edge-weighted digraph
	 * @param  s the source vertex
	 * @throws IllegalArgumentException if an edge weight is negative
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public DijkstraSP(Graph<Long,String,Double> G, long s) {
		this.s=s;
		indice=G.getIndice();
		indiceT=G.getIndiceT();
		ArregloDinamico<Edge<Long,String,Double>> edges= G.getEdges().get(s);
		for(int i=0;i<edges.darTamano();i++)
		{
			if(edges.darElem(i).getInfo()<0)
				throw new IllegalArgumentException("edge has negative weight");

		}
		distTo = new double[G.V()];
		edgeTo = new Edge[G.V()];

		validateVertex(indiceT.get(s));

		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[indiceT.get(s)] = 0.0;

		// relax vertices in order of distance from s
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(indiceT.get(s), distTo[indiceT.get(s)]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			long ve=indice.get(v);
			ArregloDinamico<Edge<Long,String,Double>> edg=G.getEdges().get(ve);
			for(int i=0;i<edg.darTamano();i++)
				relax(edg.darElem(i));
		}
	}

	// relax edge e and update pq if changed
	private void relax(Edge<Long,String,Double> e) {
		//		System.out.println("Relax, peso del arco "+e.getInfo());
		int v=indiceT.get((long)e.getStartVertexId());
		int w=indiceT.get((long)e.getEndVertexId());
		if (distTo[w] > distTo[v] + (double)e.getInfo()) {
			distTo[w] = distTo[v] + (double)e.getInfo();
			edgeTo[w] = e;
			if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
			else                pq.insert(w, distTo[w]);
		}
	}

	/**
	 * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
	 * @param  v the destination vertex
	 * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
	 *         {@code Double.POSITIVE_INFINITY} if no such path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return {@code true} if there is a path from the source vertex
	 *         {@code s} to vertex {@code v}; {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return a shortest path from the source vertex {@code s} to vertex {@code v}
	 *         as an iterable of edges, and {@code null} if no such path
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Edge<Long,String,Double>> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		Edge<Long,String,Double> ed = edgeTo[indiceT.get(s)];
		Stack<Edge<Long,String,Double>> path = new Stack<Edge<Long,String,Double>>();
		for (Edge<Long,String,Double> e = edgeTo[v]; e != null && e.equals(ed)==false; e = edgeTo[indiceT.get((long)e.getStartVertexId())]) {
			path.push(e);
		}
		return path;
	}
	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}
}
