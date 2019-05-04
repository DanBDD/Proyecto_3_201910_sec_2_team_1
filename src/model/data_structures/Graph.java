package model.data_structures;

import java.util.Iterator;

public class Graph<K extends Comparable<K>,V,D> implements IGraph<K, V, D>{

	private int V;
	private int E;
	private LinearProbing<K,Vertex<K,V,D>> vertices;
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
	public void addVertex(K idVertex, V infoVertex) 
	{
		vertices.put(idVertex, new Vertex<K, V, D>(idVertex, infoVertex));
		V++;
	}

	@Override
	public void addEdge(K idVertexIni, K idVertexFin, D pPeso) 
	{
		vertices.get(idVertexIni).addEdge(vertices.get(idVertexFin), pPeso);
		vertices.get(idVertexFin).addEdge(vertices.get(idVertexIni), pPeso);
		E++;
	}

	@Override
	public void setInfoVertex(K idVertex, V infoVertex) 
	{
		vertices.get(idVertex).setValue(infoVertex);
	}

	@Override
	public D getInfoEdge(K idVertexIni, K idVertexFin) {
		return vertices.get(idVertexIni).getEdges().get(idVertexFin).getInfo();
	}

	@Override
	public void setInfoEdge(K idVertexIni, K idVertexFin, D info) {
		vertices.get(idVertexIni).getEdges().get(idVertexFin).setInfo(info);
	}

	@Override
	public Iterator<K> adj(K idVertex) 
	{
		return vertices.get(idVertex).getAdjsIds().iterator();
	}	
	public void reducirV() {
		this.V--;
	}
}
