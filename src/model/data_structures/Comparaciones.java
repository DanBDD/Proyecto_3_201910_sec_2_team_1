package model.data_structures;

import java.io.Serializable;
import model.vo.VOMovingViolations;

/**
 * 
 * 
 *
 */
public enum Comparaciones implements Serializable{
	COORD("Coordenadas", new SerializableComparator<Vertex<Long,String,Double>>() {

		@Override
		public int compare(Vertex<Long, String, Double> o1, Vertex<Long, String, Double> o2) {
			double c = Double.parseDouble(o1.getLatitud())-Double.parseDouble(o2.getLatitud());
			if(c<0)
				return -1;
			else if(c<1)
				return 1;
			else
			{
				double c2=Double.parseDouble(o1.getLongitud())-Double.parseDouble(o2.getLongitud());
				if(c2<0)
					return -1;
				else if(c2>0)
					return 1;
				else
					return 0;
			}
		}	
	});

	/**
	 * Nombre para mostrarle al usuario del nombre del criterio de comparaci�n.
	 */
	public String nombre;

	/**
	 * Criterio de comparaci�n del elemento de la enumeraci�n.
	 */
	public SerializableComparator<Vertex<Long,String,Double>> comparador;

	/**
	 * Constructor del enum. Asigna el nombre y el comparador.
	 * @param nombre Nombre para mostrarle al usuario.
	 * @param comparador Comparador del elemento del enum.
	 */
	private Comparaciones(String nombre, SerializableComparator<Vertex<Long,String,Double>> comparador) 
	{
		this.nombre = nombre;
		this.comparador = comparador;
	}

	@Override
	public String toString() 
	{
		return nombre;
	}
}
