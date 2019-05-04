package model.data_structures;

import java.util.Iterator;

public class MaxColaPrioridad <T extends Comparable<T>>implements ColaDePrioridad<T>{

	private int numElementos;
	private Nodo<T> primerNodo;

	public MaxColaPrioridad()
	{
		primerNodo=null;
		numElementos=0;
	}

	@Override
	public T delMax() {
		T max=primerNodo.darElem();
		primerNodo=primerNodo.darSiguiente();
		numElementos--;
		return max;
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
	public void agregar(T elemento) {

		boolean add = false;

		if(elemento == null){
			throw new NullPointerException();
		}

		else
		{

			Nodo<T> nuevo = new Nodo<T>(elemento);

			if(primerNodo == null){
				primerNodo = nuevo;
				add =true;
			}
			else{
				if(primerNodo.darSiguiente() == null){
					if(primerNodo.darElem().compareTo(elemento) > 0){
						primerNodo.cambiarSiguiente(nuevo);
						add =true;
					}
					else{

						Nodo<T> siguiente = primerNodo;
						primerNodo = nuevo;
						nuevo.cambiarSiguiente(siguiente);
						add =true;
					}
				}
				else{
					Nodo<T> actual=primerNodo;
					Nodo<T> anterior = null;
					while(!add){
						if(actual.darElem().compareTo(elemento) > 0){
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
								Nodo<T>siguiente = primerNodo;
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