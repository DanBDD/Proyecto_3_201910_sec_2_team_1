package model.data_structures;

public interface ColaDePrioridad<T extends Comparable<T>,D>  {

	int darNumElementos();
	D delMax();
	T max();
	boolean estaVacia();
	void agregar(T elemento, D id);
}
