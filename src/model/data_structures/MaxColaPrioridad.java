package model.data_structures;

import java.util.Iterator;

public class MaxColaPrioridad <T extends Comparable<T>,I>implements ColaDePrioridad<T,I>{

	private int numElementos;
	private NodoMaxCola<T,I> primerNodo;

	public MaxColaPrioridad()
	{
		primerNodo=null;
		numElementos=0;
	}

	@Override
	public I delMax() {
		T max=primerNodo.darElem();
		I id= primerNodo.getId();
		primerNodo=primerNodo.darSiguiente();
		numElementos--;
		return id;
	}
	@Override
	public T max() {
		return primerNodo.darElem();
	}
	@Override
	public boolean estaVacia() {
		return numElementos==0;
	}
	@Override
	public void agregar(T elemento, I id) {

		boolean add = false;

		if(elemento == null){
			throw new NullPointerException();
		}

		else
		{

			Nodo<T,I> nuevo = new Nodo<T,I>(elemento,id);

			if(primerNodo == null){
				primerNodo = nuevo;
				add =true;
			}
			else{
				if(primerNodo.darSiguiente() == null){
					if(primerNodo.darElem().compareTo(elemento) < 0){
						primerNodo.cambiarSiguiente(nuevo);
						add =true;
					}
					else{

						Nodo<T,I> siguiente = primerNodo;
						primerNodo = nuevo;
						nuevo.cambiarSiguiente(siguiente);
						add =true;
					}
				}
				else{
					Nodo<T,I> actual=primerNodo;
					Nodo<T,I> anterior = null;
					while(!add){
						if(actual.darElem().compareTo(elemento) < 0){
							if(actual.darSiguiente() == null){
								actual.cambiarSiguiente(nuevo);
								add = true;
							}
							else{
								anterior = actual;
								actual = actual.darSiguiente();
							}
						}
						else{
							if(anterior == null){
								Nodo<T,I>siguiente = primerNodo;
								primerNodo = nuevo;
								nuevo.cambiarSiguiente(siguiente);
								add = true;
							}
							else{
								anterior.cambiarSiguiente(nuevo);
								nuevo.cambiarSiguiente(actual);
								add = true;
							}
						}
					}
				}
			}
		}
		numElementos++;
	}

	@Override
	public int darNumElementos() {
		return numElementos;
	}

}