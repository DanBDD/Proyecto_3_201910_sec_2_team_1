package model.data_structures;

public class Edge<K extends Comparable <K>,V, D> {
	
	//Puede ser el peso u otra informacion

	private D peso;
	private Vertex<K,V,D> startVertex;
	private Vertex<K,V,D> endVertex;
	private boolean marked;

	public Edge( Vertex<K,V,D> startVertex, Vertex<K,V,D> endVertex, D pPeso)
	{	

		this.startVertex=startVertex;
		this.endVertex=endVertex;
		this.peso = pPeso;
		marked=false;
	}
	public D getInfo() {
		return peso;
	}
	public boolean getMark()
	{
		return marked;
	}
	public void setMark(boolean mark)
	{
		marked=mark;
	}
	public void setInfo(D newPeso) {
		this.peso = newPeso;
	}
	
	public Vertex<K,V,D> getStartVertex()
	{
		return startVertex;
	}
	public Vertex<K,V,D> getEndVertex()
	{
		return endVertex;
	}

	public void setStartVertex(Vertex<K,V,D> newStartVertex)
	{
		this.startVertex=newStartVertex;
	}
	public void setEndVertex(Vertex<K,V,D> newEndVertex)
	{
		this.startVertex=newEndVertex;
	}
}
