package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Dueño {
	/**
	 * Id del dueño
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre de la empresa encargada del camión
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	
	
	/**
	 * Método constructor de la clase video
	 */
	public Dueño(@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
