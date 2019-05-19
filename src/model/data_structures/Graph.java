package model.data_structures;

import java.util.Iterator;

public class Graph<K extends Comparable<K>,V,D> implements IGraph<K, V, D>{

	private int V;
	private int E;
	private LinearProbing<K,Vertex<K,V,D>> vertices;
	private SeparateChaining<K, ArregloDinamico<Edge<K, V, D>>> edges;
	//hash de referencia
	private LinearProbing<Integer,K> indice;
	private LinearProbing<K, Integer> indiceT;

	//hash para usar el los metodos
	//arbol para ver los arcos existentes en el GD
	private RedBlackBST<String, Edge<K,V,D>> rb;
	public Graph()
	{
		V=0;
		E=0;
		vertices= new LinearProbing<>(745747);
		edges= new SeparateChaining<>();
		rb= new RedBlackBST<>();
		indice=new LinearProbing<>(745747);
		indiceT=new LinearProbing<>(745747);
	}
	public void crearTablas()
	{
		Iterator<K> ver = vertices.keys();
		int contador=0;
		while(ver.hasNext())
		{
			K a = ver.next();
			indice.put(contador, a);
			indiceT.put(a, contador);
			contador++;
		}
		
	}
	public LinearProbing<K,Vertex<K,V,D>> getV()
	{
		return vertices;
	}
	public SeparateChaining<K, ArregloDinamico<Edge<K, V, D>>> getEdges()
	{
		return edges;
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
	public void addVertex(K idVertex, V infoVertex, ArregloDinamico<V> infras, Bag<Long> ar2) 
	{
		vertices.put(idVertex, new Vertex<K, V, D>(idVertex, infoVertex, infras, ar2));
		V++;
	}

	@Override
	public void addEdge(K idVertexIni, K idVertexFin, D pPeso) 
	{
		if(rb.contains(idVertexFin+"|"+idVertexIni))
		{
		}
		else
		{
			E++;
		}
		Edge<K, V, D> a = new Edge<K, V, D>(idVertexIni, idVertexFin, pPeso);
		if(edges.get(idVertexIni)!=null)
		{
			ArregloDinamico<Edge<K, V, D>> b = edges.get(idVertexIni);
			b.agregar(a);
			edges.put(idVertexIni, b);
		}
		else
		{
			ArregloDinamico<Edge<K,V,D>> ar = new ArregloDinamico<>(2);
			ar.agregar(a);
			edges.put(idVertexIni, ar);
		}
		rb.put(idVertexIni+"|"+idVertexFin, a);
	}

	@Override
	public void setInfoVertex(K idVertex, V infoVertex) 
	{
		vertices.get(idVertex).setValue(infoVertex);
	}

	@Override
	public D getInfoEdge(K idVertexIni, K idVertexFin) {
		D retorno=null;
		ArregloDinamico<Edge<K, V, D>> a = edges.get(idVertexIni);
		for(int i=0;i<a.darTamano();i++)
		{
			Edge<K, V, D> e = a.darElem(i);
			if(e.getStartVertexId().equals(idVertexIni) && e.getEndVertexId().equals(idVertexFin))
			{
				retorno= e.getInfo();
			}
		}
		return retorno;	
	}

	@Override
	public void setInfoEdge(K idVertexIni, K idVertexFin, D info) {
		ArregloDinamico<Edge<K, V, D>> a = edges.get(idVertexIni);
		for(int i=0;i<a.darTamano();i++)
		{
			Edge<K, V, D> e = a.darElem(i);
			if(e.getStartVertexId().equals(idVertexIni) && e.getEndVertexId().equals(idVertexFin))
			{
				e.setInfo(info);
			}
		}
		ArregloDinamico<Edge<K, V, D>> b = edges.get(idVertexFin);
		for(int i=0;i<b.darTamano();i++)
		{
			Edge<K, V, D> e = b.darElem(i);
			if(e.getStartVertexId().equals(idVertexIni) && e.getEndVertexId().equals(idVertexFin))
			{
				e.setInfo(info);
			}
		}
	}

	@Override
	public Iterator<Long> adj(K idVertex) 
	{
		return vertices.get(idVertex).getIds().iterator();
	}	

	public void reducirV() {
		this.V--;
	}
	public RedBlackBST<String, Edge<K,V,D>> getRb() {
		return rb;
	}
	public void setRb(RedBlackBST<String, Edge<K,V,D>> rb) {
		this.rb = rb;
	}
	public void addDirectedEdge(K idVertexIni, K idVertexFin, D pPeso) 
	{
		Edge<K, V, D> a = new Edge<K, V, D>(idVertexIni, idVertexFin, pPeso);
		if(edges.get(idVertexIni)!=null)
		{
			ArregloDinamico<Edge<K, V, D>> b = edges.get(idVertexIni);
			b.agregar(a);
			edges.put(idVertexIni, b);
		}
		else
		{
			ArregloDinamico<Edge<K,V,D>> ar = new ArregloDinamico<>(2);
			ar.agregar(a);
			edges.put(idVertexIni, ar);
		}
		E++;
	}
	public LinearProbing<Integer,K> getIndice() {
		return indice;
	}
	public void setIndice(LinearProbing<Integer,K> indice) {
		this.indice = indice;
	}
	public LinearProbing<K,Integer> getIndiceT() {
		return indiceT;
	}
	public void setIndiceT(LinearProbing<K,Integer> indiceT) {
		this.indiceT = indiceT;
	}

}
