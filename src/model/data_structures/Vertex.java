package model.data_structures;

import model.vo.VOMovingViolations;

public class Vertex<K extends Comparable<K>,V,D> {

	private K id;
	private V value;
	private V longitud;
	private V latitud;
	private int cantidadInfracciones;
	private ArregloDinamico<Edge<K, V, D>> edgs;
	private ArregloDinamico<Vertex<K,V,D>> adjs;
	private Cola<K> adjsId;
	
	
	public Vertex(K pId, V value, int infra){
		id = pId;
		this.value=value;
		edgs=new ArregloDinamico<>(5);
		setAdjs(new ArregloDinamico<Vertex<K,V,D>>(5));
		adjsId=new Cola<K>();
		latitud=(V) value.toString().substring(0, 10);
		longitud=(V) value.toString().substring(11);
		cantidadInfracciones=infra;
	}
	public String darInfoVertice(){
		return "Identificador: " + id + "Latitud " + latitud + "Longitud " + longitud;
	}
	public K getId(){
		return id;
	}
	public V getValue()
	{
		return value;
	}
	public ArregloDinamico<Edge<K, V, D>> getEdges()
	{
		return edgs;
	}
	public V getLatitud()
	{
		return latitud;
	}
	public V getLongitud()
	{
		return longitud;
	}
	public void setId(K nuevoId){
		this.id=nuevoId;
	}
	public void setValue(V nuevoValor)
	{
		this.value=nuevoValor;
		
	}
	public void addEdge(Edge<K, V, D> edge)
	{	
		edgs.agregar(edge);
		K a = edge.getStartVertex().id;
		K b = edge.getEndVertex().id;
		if(this.id.equals(a))
			adjsId.enqueue(b);
		else
			adjsId.enqueue(a);
	}
	public Edge<K, V, D> getEdge(K inicial, K fin)
	{
		Edge<K, V, D> r=null;
		for (int i = 0; i < edgs.darTamano(); i++) 
		{
			if(edgs.darElem(i).getStartVertex().id.equals(inicial) &&
					edgs.darElem(i).getEndVertex().id.equals(fin))
			{
				r=edgs.darElem(i);
				break;
			}
		}
		return r;
	}
	public int getCantidadInfracciones() {
		return cantidadInfracciones;
	}
	public void setCantidadInfracciones(int cantidadInfracciones) {
		this.cantidadInfracciones = cantidadInfracciones;
	}
	public void aumentarCantidadInfracciones()
	{
		cantidadInfracciones++;
	}
	public ArregloDinamico<Vertex<K,V,D>> getAdjs() {
		return adjs;
	}
	public void setAdjs(ArregloDinamico<Vertex<K,V,D>> adjs) {
		this.adjs = adjs;
	}
	public Cola<K> getAdjsId() {
		return adjsId;
	}
	public void setAdjsId(Cola<K> adjsId) {
		this.adjsId = adjsId;
	}
	

}