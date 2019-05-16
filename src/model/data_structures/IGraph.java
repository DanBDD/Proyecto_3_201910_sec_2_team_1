package model.data_structures;

import java.util.Iterator;

public interface IGraph<K extends Comparable<K>,V,D> {
	int V();
	int E();
	 void addVertex(K idVertex, V infoVertex, ArregloDinamico<V> infras, Bag<String> ar2) ;
	void addEdge(K idVertexIni, K idVertexFin, D pPeso );
	void setInfoVertex(K idVertex, V infoVertex);
	D getInfoEdge(K idVertexIni, K idVertexFin);
	void setInfoEdge(K idVertexIni, K idVertexFin, D info);
	Iterator<K> adj(K idVertex);
}
