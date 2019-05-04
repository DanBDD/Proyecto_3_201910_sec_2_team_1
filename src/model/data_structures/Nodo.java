package model.data_structures;
/**
 * 
 * Este c�digo fue tomado del Taller 3 de este curso, tambi�n debidamente citado.
 *
 * @param <T> Objeto a guardar en esta estructura
 */
public class Nodo<T>{

	private Nodo<T> siguiente;
	
	private T elemento;
	
	public Nodo(T pElemento){
		elemento = pElemento;
		siguiente = null;
	}
	
	public void cambiarSiguiente(Nodo<T> nuevo){
		siguiente = nuevo;
	}
	
	public T darElem(){
		return elemento;
	}
	
	public Nodo<T> darSiguiente(){
		return siguiente;
	}
	public void cambiarElemento(Comparable aux)
	{
		this.elemento = (T) aux;
	}
}