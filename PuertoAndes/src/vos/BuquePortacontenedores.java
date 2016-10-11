package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class BuquePortacontenedores extends Buque{

	/**
	 * Capacidad en TEU del buque
	 */
	@JsonProperty(value="capacidad")
	private float capacidadTEU;
	
	public BuquePortacontenedores(int id, String nombre, String agenteMaritimo, 
			String registroCapitania, float peso,
			char tipo, boolean procesoDeCarga, boolean procesoDeDescarga,
			boolean cargaCompletada, boolean descargaCompletada,
			char deshabilitado,
			Puerto puertoOrigen, Puerto puertoDestino,
			@JsonProperty(value="capacidadTEU")float capacidadTEU) {
		super(id, nombre, agenteMaritimo, registroCapitania, peso,
				tipo, procesoDeCarga, procesoDeDescarga, cargaCompletada,
				descargaCompletada, deshabilitado,
				puertoOrigen, puertoDestino);
		// TODO Auto-generated constructor stub
		this.capacidadTEU = capacidadTEU;
	}

	
	public float getCapacidadTEU() {
		return capacidadTEU;
	}


	public void setCapacidadTEU(float cap) {
		this.capacidadTEU = cap;
	}
}
