package model.data_structures;

public interface ColaDePrioridad<T extends Comparable<T>,I>  {

	int darNumElementos();
	I delMax();
	T max();
	boolean estaVacia();
	void agregar(T elemento, I id);
}
