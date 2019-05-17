package model.data_structures;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac DijkstraUndirectedSP.java
 *  Execution:    java DijkstraUndirectedSP input.txt s
 *  Dependencies: EdgeWeightedGraph.java IndexMinPQ.java Stack.java Edge.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Dijkstra's algorithm. Computes the shortest path tree.
 *  Assumes all weights are nonnegative.
 *
 *  % java DijkstraUndirectedSP tinyEWG.txt 6
 *  6 to 0 (0.58)  6-0 0.58000
 *  6 to 1 (0.76)  6-2 0.40000   1-2 0.36000
 *  6 to 2 (0.40)  6-2 0.40000
 *  6 to 3 (0.52)  3-6 0.52000
 *  6 to 4 (0.93)  6-4 0.93000
 *  6 to 5 (1.02)  6-2 0.40000   2-7 0.34000   5-7 0.28000
 *  6 to 6 (0.00)
 *  6 to 7 (0.74)  6-2 0.40000   2-7 0.34000
 *
 *  % java DijkstraUndirectedSP mediumEWG.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (0.71)  0-44 0.06471   44-93  0.06793  ...   1-107 0.07484
 *  0 to 2 (0.65)  0-44 0.06471   44-231 0.10384  ...   2-42  0.11456
 *  0 to 3 (0.46)  0-97 0.07705   97-248 0.08598  ...   3-45  0.11902
 *  ...
 *
 *  % java DijkstraUndirectedSP largeEWG.txt 0
 *  0 to 0 (0.00)  
 *  0 to 1 (0.78)  0-460790 0.00190  460790-696678 0.00173   ...   1-826350 0.00191
 *  0 to 2 (0.61)  0-15786  0.00130  15786-53370   0.00113   ...   2-793420 0.00040
 *  0 to 3 (0.31)  0-460790 0.00190  460790-752483 0.00194   ...   3-698373 0.00172
 *
 ******************************************************************************/


/**
 *  The {@code DijkstraUndirectedSP} class represents a data type for solving
 *  the single-source shortest paths problem in edge-weighted graphs
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
 *  See {@link DijkstraSP} for a version on edge-weighted digraphs.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Nate Liu
 */
public class Dijkstra {

	private LinearProbing<Long, Double> distTo;// distTo[v] = distance  of shortest s->v path
    private LinearProbing<Long, Edge<Long,String,Double>> edgeTo;           // edgeTo[v] = last edge on shortest s->v path
    private LinearProbing<Long, Vertex<Long,String,Double>> linGrafo;
    private IndexMinPQ<Double> pq;
    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every
     * other vertex in the edge-weighted graph {@code G}.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public Dijkstra(Graph<Long, String, Double> G, long s) {
    	System.out.println("Inicio");
    	linGrafo = G.getV();
    	Iterator<Long> it = linGrafo.keys();
    	distTo = new LinearProbing<>(G.V());
        edgeTo = new LinearProbing<>(G.V());
    	while(it.hasNext()) {
    		Long i = it.next();
    		Vertex<Long, String, Double> v = linGrafo.get(i);
    		for(int a = 0; a<v.getEdges().darTamano();a++) {
    			if(v.getEdges().darElem(a).getInfo() < 0) {
                    throw new IllegalArgumentException("edge " + v.getEdges().darElem(a).toString() + " has negative weight");
    			}
    			System.out.println("Fin ciclo");
    		}
    		distTo.put(i, Double.POSITIVE_INFINITY);
    	}
        validateVertex(s);

        distTo.put(s, 0.0);
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo.get(s));
        System.out.println("Insert");
        while (!pq.isEmpty()) {
            long v = pq.delMin();
            Vertex<Long, String, Double> ver = linGrafo.get(v);
            for(int i = 0; i<ver.getEdges().darTamano();i++) {
            	Edge<Long,String,Double> e = ver.getEdges().darElem(i);
            	relax(e,v);
            	System.out.println("Relax");
            }
//            for (Edge<Long,String,Double> e : G.adj(v))
//                relax(e, v);
        }
        // check optimality conditions
       // assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(Edge<Long, String, Double> e, long v) {
//    	int w = e.other(v);
//        if (distTo[w] > distTo[v] + e.weight()) {
//            distTo[w] = distTo[v] + e.weight();
//            edgeTo[w] = e;
//            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
//            else                pq.insert(w, distTo[w]);
//        }
    	Long a = e.getStartVertex().getId();
    	Long w = e.getEndVertex().getId();
    	Long s = null;
    	if(v == a) {
    		s = w;
    	}
    	else {
    		s = a;
    	}
    	
    	double distToW = distTo.get(s);
    	double distToV = distTo.get(v);
 
    	if(distToW > distToV + e.getInfo()) {
    		System.out.println("Entro");
    		distTo.put(s, distToV + e.getInfo());
    		edgeTo.put(s, e);
    		if(pq.contains(s)) {
    			pq.decreaseKey(s, distTo.get(s));
    		}
    		else {
    			pq.insert(s, distTo.get(s));
    		}
    	}

    }

    /**
     * Returns the length of a shortest path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex {@code s} and
     *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(long v) {
        validateVertex(v);
        return distTo.get(v);
    }

    /**
     * Returns true if there is a path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return {@code true} if there is a path between the source vertex
     *         {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(long v) {
        validateVertex(v);
        System.out.println("has path to " +distTo.get(v));
        return distTo.get(v) < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path between the source vertex {@code s} and vertex {@code v};
     *         {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge<Long,String,Double>> pathTo(long v) {
    	
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge<Long,String,Double>> path = new Stack<Edge<Long,String,Double>>();
        long x = v;
        for (Edge<Long,String,Double> e = edgeTo.get(v); e != null; e = edgeTo.get(x)) {
            path.push(e);
            x = e.getEndVertex().getId();
        }
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(long v) {
        
        if (linGrafo.get(v) == null)
            throw new IllegalArgumentException("vertex null");
    }

 

}


