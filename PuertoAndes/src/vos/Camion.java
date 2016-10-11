package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Camion {
	/**
	 * Id del camión
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre de la empresa encargada del camión
	 */
	@JsonProperty(value="empresaEncargada")
	private String empresaEncargada;

	/**
	 * Capacidad en toneladas del camión 
	 */
	@JsonProperty(value="capacidad")
	private float capacidad;

	
	/**
	 * Método constructor de la clase video
	 */
	public Camion(@JsonProperty(value="id")int id, 
			@JsonProperty(value="capacidad")float capacidad,
			@JsonProperty(value="empresaEncargada") String empresaEncargada) {
		super();
		this.id = id;
		this.capacidad = capacidad;
		this.empresaEncargada = empresaEncargada;
	}
	
//Métodos	

	/**
	 * Método getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parÃ¡metro
	 * @param id - Id del video
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getEmpresaEncargada() {
		return empresaEncargada;
	}


	public void setEmpresaEncargada(String empEnc) {
		this.empresaEncargada = empEnc;
	}

	public float getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(float cap) {
		this.capacidad = cap;
	}
	
	
}
