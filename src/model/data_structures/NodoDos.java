package model.data_structures;
/**
 * 
 * Este c�digo fue tomado del Taller 3 de este curso, tambi�n debidamente citado.
 *
 * @param <T> Objeto a guardar en esta estructura
 */
public class NodoDos<T,I>{

	private NodoDos<T,I> siguiente;
	
	private T elemento;
	private I id;
	
	public NodoDos(T pElemento, I id){
		elemento = pElemento;
		this.setId(id);
		siguiente = null;
	}
	
	public void cambiarSiguiente(NodoDos<T,I> nuevo){
		siguiente = nuevo;
	}
	
	public T darElem(){
		return elemento;
	}
	
	public NodoDos<T,I> darSiguiente(){
		return siguiente;
	}
	public void cambiarElemento(Comparable aux)
	{
		this.elemento = (T) aux;
	}

	public I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}
}