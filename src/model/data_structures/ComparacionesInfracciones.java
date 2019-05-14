package model.data_structures;

import java.io.Serializable;
import model.vo.VOMovingViolations;

/**
 * 
 * 
 *
 */
public enum ComparacionesInfracciones implements Serializable{
	COORD("Coordenadas", new SerializableComparator<VOMovingViolations>() {

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			double c = Double.parseDouble(o1.darLat())-Double.parseDouble(o2.darLat());
			if(c<0)
				return -1;
			else if(c<1)
				return 1;
			else
			{
				double c2=Double.parseDouble(o1.darLon())-Double.parseDouble(o2.darLon());
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
	public SerializableComparator<VOMovingViolations> comparador;

	/**
	 * Constructor del enum. Asigna el nombre y el comparador.
	 * @param nombre Nombre para mostrarle al usuario.
	 * @param comparador Comparador del elemento del enum.
	 */
	private ComparacionesInfracciones(String nombre, SerializableComparator<VOMovingViolations> comparador) 
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
