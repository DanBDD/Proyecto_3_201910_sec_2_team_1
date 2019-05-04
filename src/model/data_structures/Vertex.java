package model.data_structures;

public class Vertex<K extends Comparable<K>,V,D> {

	private K id;
	private V value;
	private V longitud;
	private V latitud;
	private ArregloDinamico<Vertex<K,V,D>> adjs;
	private LinearProbing<K,Edge<K,V,D>> edgs;
	private Cola<K> ajsIds;
	
	
	public Vertex(K pId, V value){
		id = pId;
		this.value=value;
		adjs=new ArregloDinamico<>(5);
		edgs= new LinearProbing<>(2);
		latitud=(V) value.toString().substring(0, 10);
		longitud=(V) value.toString().substring(11);
		ajsIds= new Cola<>();
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
	public ArregloDinamico<Vertex<K,V,D>> getAdjs()
	{
		return adjs;
	}
	public Cola<K> getAdjsIds()
	{
		return ajsIds;
	}
	public V getLatitud()
	{
		return latitud;
	}
	public V getLongitud()
	{
		return longitud;
	}
	public LinearProbing<K,Edge<K,V,D>> getEdges()
	{
		return edgs;
	}
	public void setId(K nuevoId){
		this.id=nuevoId;
	}
	public void setValue(V nuevoValor)
	{
		this.value=nuevoValor;
		
	}
	public void addEdge(Vertex<K,V,D> otherVertex,  D pPeso)
	{	
		adjs.agregar(otherVertex);
		ajsIds.enqueue(otherVertex.id);
		edgs.put(otherVertex.id, new Edge<>(this, otherVertex, pPeso));
	}
	

}