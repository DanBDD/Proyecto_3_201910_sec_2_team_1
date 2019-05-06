package model.data_structures;
/**
 * 
 * Este c�digo fue tomado del Taller 3 de este curso, tambi�n debidamente citado.
 *
 * @param <T> Objeto a guardar en esta estructura
 */
public class Nodo<T,D>{

	private Nodo<T,D> siguiente;
	
	private T elemento;
	private D id;
	
	public Nodo(T pElemento, D id){
		elemento = pElemento;
		this.setId(id);
		siguiente = null;
	}
	
	public void cambiarSiguiente(Nodo<T,D> nuevo){
		siguiente = nuevo;
	}
	
	public T darElem(){
		return elemento;
	}
	
	public Nodo<T,D> darSiguiente(){
		return siguiente;
	}
	public void cambiarElemento(Comparable aux)
	{
		this.elemento = (T) aux;
	}

	public D getId() {
		return id;
	}

	public void setId(D id) {
		this.id = id;
	}
}