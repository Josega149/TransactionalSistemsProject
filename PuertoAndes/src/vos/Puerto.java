package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Puerto {
	/**
	 * Id del puerto
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre del puerto
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Nombre del pa�s donde est� el puerto 
	 */
	@JsonProperty(value="pais")
	private String pais;

	/**
	 * Registro de la ciudad donde est� el puerto
	 */
	@JsonProperty(value="ciudad")
	private String ciudad;
	
	
	
	/**
	 * M�todo constructor de la clase video
	 */
	public Puerto(@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="pais") String pais,
			@JsonProperty(value="ciudad") String ciudad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
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

	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	
	
}
