package model.data_structures;

public interface ColaDePrioridad<T extends Comparable<T>>  {

	int darNumElementos();
	T delMax();
	T max();
	boolean estaVacia();
	void agregar(T elemento);
}
