package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silo extends AreaDeAlmacenamiento{

	/**
	 * Capacidad en toneladas del silo
	 */
	@JsonProperty(value="capacidad")
	private float capacidad;

	/**
	 * Nombre del silo
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	public Silo(int id, String tipo, 
			boolean habilitado, float capacidadTotal,
			float pesoActual,
			@JsonProperty(value="capacidad")float cap,
			@JsonProperty(value="nombre")String nom) {
		super(id, tipo, habilitado, capacidadTotal, pesoActual);
		this.capacidad = cap;
		this.nombre = nom;
		// TODO Auto-generated constructor stub
	}
	
	public float getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(float cap) {
		this.capacidad = cap;
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nom) {
		this.nombre= nom;
	}
}
