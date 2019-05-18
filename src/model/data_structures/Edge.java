package model.data_structures;

public class Edge<K extends Comparable <K>,V, D> implements Comparable<Edge<K,V,D>>{
	
	//Puede ser el peso u otra informacion

	private D peso;
	private K startVertex;
	private K endVertex;
	private boolean marked;

	public Edge( K idVertexIni, K idVertexFin, D pPeso)
	{	

		this.startVertex=idVertexIni;
		this.endVertex=idVertexFin;
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
	
	public K getStartVertexId()
	{
		return startVertex;
	}
	public K getEndVertexId()
	{
		return endVertex;
	}

	public void setStartVertexId(K newStartVertex)
	{
		this.startVertex=newStartVertex;
	}
	public void setEndVertexiD(K newEndVertex)
	{
		this.startVertex=newEndVertex;
	}
	public String toString() {
		return "Arco vertice ini " + getStartVertexId() + " vertice fin " + getEndVertexId();
	}
	@Override
	public int compareTo(Edge<K, V, D> arg0) {
		int comp = 0;
		
		return comp;
				
	}
}
