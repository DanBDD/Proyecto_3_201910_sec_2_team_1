package model.data_structures;

import java.util.Iterator;

public class Graph<K extends Comparable<K>,V,D> implements IGraph<K, V, D>{

	private int V;
	private int E;
	private LinearProbing<K,Vertex<K,V,D>> vertices;
//	private SeparateChaining<K, Cola<Edge<K, V, D>>> edges;
	public Graph()
	{
		V=0;
		E=0;
		vertices= new LinearProbing<>(3);
	}
	public LinearProbing<K,Vertex<K,V,D>> getV()
	{
		return vertices;
	}
	@Override
	public int V() {
		return V;
	}

	@Override
	public int E() {
		return E;
	}

	@Override
	public void addVertex(K idVertex, V infoVertex, ArregloDinamico<V> infras, Bag<String> ar2) 
	{
		vertices.put(idVertex, new Vertex<K, V, D>(idVertex, infoVertex, infras, ar2));
		V++;
	}

	@Override
	public void addEdge(K idVertexIni, K idVertexFin, D pPeso) 
	{
		
		Edge<K, V, D> a = new Edge<K, V, D>(vertices.get(idVertexIni), vertices.get(idVertexFin), pPeso);
		vertices.get(idVertexIni).addEdge(a);
		vertices.get(idVertexFin).addEdge(a);
		E++;
	}

	@Override
	public void setInfoVertex(K idVertex, V infoVertex) 
	{
		vertices.get(idVertex).setValue(infoVertex);
	}

	@Override
	public D getInfoEdge(K idVertexIni, K idVertexFin) {
		return vertices.get(idVertexIni).getEdge(idVertexIni, idVertexFin).getInfo();
	}

	@Override
	public void setInfoEdge(K idVertexIni, K idVertexFin, D info) {
		vertices.get(idVertexIni).getEdge(idVertexIni, idVertexFin).setInfo(info);
		vertices.get(idVertexFin).getEdge(idVertexIni, idVertexFin).setInfo(info);

	}

	@Override
	public Iterator<K> adj(K idVertex) 
	{
		return vertices.get(idVertex).getAdjsId().iterator();
	}	
	
	public void reducirV() {
		this.V--;
	}
}
