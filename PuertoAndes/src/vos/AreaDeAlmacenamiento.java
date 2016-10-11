package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AreaDeAlmacenamiento {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="habilitado")
	private boolean habilitado;
	
	@JsonProperty(value="capacidadTotal")
	private float capacidadTotal;
	
	@JsonProperty(value="pesoActual")
	private float pesoActual;
	
	public AreaDeAlmacenamiento(){
		
	}
	
	public AreaDeAlmacenamiento (@JsonProperty(value="id")int id, 
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="habilitado")boolean habilitado, 
			@JsonProperty(value="capacidadTotal")float capTotal,
			@JsonProperty(value="pesoActual")float pesoActual)
	{
		this.id = id;
		this.tipo = tipo;
		this.habilitado = habilitado;
		this.capacidadTotal = capTotal;
		this.pesoActual = pesoActual;
	}
	
	/**
	 * MÈtodo getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * MÈtodo setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como par√°metro
	 * @param id - Id del video
	 */
	public void setId(int id) {
		this.id = id;
	}


}
