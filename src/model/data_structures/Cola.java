package model.data_structures;

import java.util.Iterator;
/**
 * 
 * Este c�digo fue tomado del Taller 3 de este curso, tambi�n debidamente citado.
 *
 * @param <T> Objeto a guardar en esta estructura
 */
public class Cola<T extends Comparable<T>> implements IQueue<T>{

	private Nodo<T> primerNodo;

	private Nodo<T> ultimo;
	private int numElementos;

	public Cola()
	{
		primerNodo=null;
		numElementos=0;
	}
	public Cola(T p)
	{
		primerNodo=new Nodo<T>(p);
		numElementos++;
	}

	@Override
	public Iterator iterator() {

		return new Iterador<T>(primerNodo);
	}

	@Override
	public boolean isEmpty() {

		if(numElementos == 0){
			return true;
		}
		return false;
	}

	@Override
	public int size() {

		return numElementos;
	}

	@Override
	public void enqueue(T t) {
		Nodo<T> nNode = new Nodo<T>(t);
		if(ultimo != null)
			ultimo.cambiarSiguiente(nNode);
		else
			primerNodo = nNode;
		ultimo = nNode;
		numElementos++;
	}
	
	@Override
	public T dequeue() {
		T elem = primerNodo.darElem();
		primerNodo=primerNodo.darSiguiente();
		numElementos--;
		return elem;
	}
	
	public T get(int i){
		
		T objeto = null;
		Iterator<T> it = this.iterator();
		int cont = 0;
		while(it.hasNext() && cont<i){
			objeto = it.next();
			cont++;
		}
		return objeto;
	}
}