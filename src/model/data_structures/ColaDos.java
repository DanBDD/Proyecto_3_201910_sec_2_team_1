package model.data_structures;

import java.util.Iterator;
/**
 * 
 * Este c�digo fue tomado del Taller 3 de este curso, tambi�n debidamente citado.
 *
 * @param <T> Objeto a guardar en esta estructura
 */
public class ColaDos<T extends Comparable<T>,I> implements IQueue2<T,I>{

	private NodoDos<T,I> primerNodo;

	private NodoDos<T,I> ultimo;
	private int numElementos;

	public ColaDos()
	{
		primerNodo=null;
		numElementos=0;
	}
	public ColaDos(T p,I i)
	{
		primerNodo=new NodoDos<T,I>(p,i);
		numElementos++;
	}

	@Override
	public Iterator iterator() {

		return new Iterador2<T,I>(primerNodo);
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
	public void enqueue(T t,I i) {
		NodoDos<T,I> nNode = new NodoDos<T,I>(t,i);
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