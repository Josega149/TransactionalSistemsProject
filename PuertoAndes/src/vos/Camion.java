package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Camion {
	/**
	 * Id del cami�n
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre de la empresa encargada del cami�n
	 */
	@JsonProperty(value="empresaEncargada")
	private String empresaEncargada;

	/**
	 * Capacidad en toneladas del cami�n 
	 */
	@JsonProperty(value="capacidad")
	private float capacidad;

	
	/**
	 * M�todo constructor de la clase video
	 */
	public Camion(@JsonProperty(value="id")int id, 
			@JsonProperty(value="capacidad")float capacidad,
			@JsonProperty(value="empresaEncargada") String empresaEncargada) {
		super();
		this.id = id;
		this.capacidad = capacidad;
		this.empresaEncargada = empresaEncargada;
	}
	
//M�todos	

	/**
	 * M�todo getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * M�todo setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parámetro
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
