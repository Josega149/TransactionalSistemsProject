package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Due�o {
	/**
	 * Id del due�o
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre de la empresa encargada del cami�n
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	
	
	/**
	 * M�todo constructor de la clase video
	 */
	public Due�o(@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
