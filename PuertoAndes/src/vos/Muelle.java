package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Muelle {
////Atributos

	/**
	 * Id del muelle
	 */
	@JsonProperty(value="id")
	private int id;

	

	/**
	 * Método constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public Muelle(@JsonProperty(value="id")int id) {
		super();
		this.id = id;
	}
	
	

	/**
	 * Método getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param id - Id del video
	 */
	public void setId(int id) {
		this.id = id;
	}


}
