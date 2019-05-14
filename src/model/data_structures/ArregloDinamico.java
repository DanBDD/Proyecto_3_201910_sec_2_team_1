package model.data_structures;

import java.util.Iterator;

/**
 * [CODIGO CITADO] En esta clase el c�digo de agregar se tomo del esqueleto del taller 1
 * @author dagar
 *
 * @param <T> Objeto a guardar en ese arreglo dinamico generico
 */
public class ArregloDinamico<T>{

	private int tamanoActual;

	private int tamanoArreglo;

	private T[] elems;

	public ArregloDinamico(int pTamano){

		tamanoArreglo = pTamano;

		elems = (T[]) new Object[pTamano];

		tamanoActual = 0;
		
	}

	public void agregar( T dato )
	{
		if ( tamanoActual == tamanoArreglo )
		{  // caso de arreglo lleno (aumentar tamaNo)
			tamanoArreglo = 2 * tamanoArreglo;
			T [ ] copia = elems;
			elems = (T[]) new Object[tamanoArreglo];
			for ( int i = 0; i < tamanoActual; i++)
			{
				elems[i] = copia[i];
			} 

		}	
		elems[tamanoActual] = dato;
		tamanoActual++;
	}
	public int darTamano(){
		return tamanoActual;
	}
	public T darElem(int pos){
		if(pos >= tamanoActual || pos < 0){
			throw new IndexOutOfBoundsException();
		}

		return elems[pos];
	}
	public void cambiarElementoEnPos(T dato, int pos)
	{
		if(pos>tamanoActual) throw new IndexOutOfBoundsException();
		else{
			T elem = darElem(pos);
			elem = null;
			elem = dato;
		}
	}
	
	public boolean contains(T dato){
		boolean contains = false;
		for(int i = 0; i<tamanoActual && !contains; i++){
			if(this.darElem(i).equals(dato)){
				contains = true;
				break;
			}
			else{
			}
		}
		return contains;
	}
	public int index(T dato) {
		int index = -1;
		boolean encontro = false;
		for(int i = 0; i<tamanoActual && !encontro;i++) {
			T actual = darElem(i);
			
			if(actual.equals(dato)) {
				index = i;
				encontro = true;
			}
			
		}
		return index;
	}
}
