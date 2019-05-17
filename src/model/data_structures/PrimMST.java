package model.data_structures;

import java.util.Iterator;

/******************************************************************************
 *  Compilation:  javac PrimMST.java
 *  Execution:    java PrimMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                IndexMinPQ.java UF.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Prim's algorithm.
 *
 *  %  java PrimMST tinyEWG.txt 
 *  1-7 0.19000
 *  0-2 0.26000
 *  2-3 0.17000
 *  4-5 0.35000
 *  5-7 0.28000
 *  6-2 0.40000
 *  0-7 0.16000
 *  1.81000
 *
 *  % java PrimMST mediumEWG.txt
 *  1-72   0.06506
 *  2-86   0.05980
 *  3-67   0.09725
 *  4-55   0.06425
 *  5-102  0.03834
 *  6-129  0.05363
 *  7-157  0.00516
 *  ...
 *  10.46351
 *
 *  % java PrimMST largeEWG.txt
 *  ...
 *  647.66307
 *
 ******************************************************************
/**
 *  The {@code PrimMST} class represents a data type for computing a
 *  <em>minimum spanning tree</em> in an edge-weighted graph.
 *  The edge weights can be positive, zero, or negative and need not
 *  be distinct. If the graph is not connected, it computes a <em>minimum
 *  spanning forest</em>, which is the union of minimum spanning trees
 *  in each connected component. The {@code weight()} method returns the 
 *  weight of a minimum spanning tree and the {@code edges()} method
 *  returns its edges.
 *  <p>
 *  This implementation uses <em>Prim's algorithm</em> with an indexed
 *  binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>
 *  and extra space (not including the graph) proportional to <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the {@code weight()} method takes constant time
 *  and the {@code edges()} method takes time proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For alternate implementations, see {@link LazyPrimMST}, {@link KruskalMST},
 *  and {@link BoruvkaMST}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private LinearProbing<Long, Edge<Long, String, Double>> edgeTo;
    private LinearProbing<Long, Double> distTo;
    private LinearProbing<Long, Boolean> marked;
    private IndexMinPQ<Double> pq;
    private LinearProbing<Long, Vertex<Long, String, Double>> lista;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimMST(Graph<Long, String, Double> G) {
        edgeTo = new LinearProbing<>(G.V());
        distTo = new LinearProbing<>(G.V());
        marked = new LinearProbing<>(G.V());
        pq = new IndexMinPQ<Double>(G.V());
        lista=G.getV();
        Iterator<Long> it = lista.keys();
        while(it.hasNext())
        {
        	Long i=it.next();
        	distTo.put(i, Double.POSITIVE_INFINITY);
     
        	if(marked.get(i)==false)
        	{
        		prim(G,i);
        	}
        }
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(Graph<Long, String, Double> G, long s) {
        distTo.put(s, 0.0);
        pq.insert(s, distTo.get(s));
        while (!pq.isEmpty()) {
            long v = pq.delMin();
            scan(G, v);
        }
    }

    // scan vertex v
    private void scan(Graph G, long v) {
        marked.put(v, true);
        Iterator<Long> it = lista.keys();
        while(it.hasNext())
        {
        	Long i = it.next();
            Vertex<Long, String, Double> r = lista.get(i);
            for(int j=0;j<r.getEdges().darTamano();j++)
            {
            	Edge<Long, String, Double> e = r.getEdges().darElem(j);
            	Vertex<Long, String, Double> inicio = e.getStartVertex();
            	Vertex<Long, String, Double> fin = e.getEndVertex();
            	long w=0L;
            	if(v==inicio.getId())
            	{
            		w=fin.getId();
            	}
            	else
            	{
            		w=inicio.getId();
            	}
            	if(marked.get(w))continue;
            	if(e.getInfo()<distTo.get(w)) {
            		distTo.put(w, e.getInfo());
            		edgeTo.put(w, e);
            		if (pq.contains(w)) pq.decreaseKey(w, distTo.get(w));
                    else                pq.insert(w, distTo.get(w));
            	}
            }
        }
    }
}
