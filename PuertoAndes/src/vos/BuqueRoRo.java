package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class BuqueRoRo extends Buque{

	/**
	 * Capacidad en toneladas del buque
	 */
	@JsonProperty(value="capacidad")
	private float capacidad;
	
	public BuqueRoRo(int id, String nombre, String agenteMaritimo, 
			String registroCapitania, float peso,
			char tipo, boolean procesoDeCarga, boolean procesoDeDescarga,
			boolean cargaCompletada, boolean descargaCompletada,
			char deshabilitado,
			Puerto puertoOrigen, Puerto puertoDestino,
			@JsonProperty(value="capacidad")float capacidad) {
		super(id, nombre, agenteMaritimo, registroCapitania, peso,
				tipo, procesoDeCarga, procesoDeDescarga, cargaCompletada,
				descargaCompletada, deshabilitado,
				puertoOrigen, puertoDestino);
		// TODO Auto-generated constructor stub
		this.capacidad = capacidad;
	}
	
	public float getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(float cap) {
		this.capacidad = cap;
	}

}
