package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Carga {
////Atributos

	/**
	 * Id del buque
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Tipo de la carga
	 */
	@JsonProperty(value="tipo")
	private String tipo;

	/**
	 * Tiempo de estad�a de la carga en d�as  
	 */
	@JsonProperty(value="tiempoEstadia")
	private int tiempoEstadia;

	
	/**
	 * Peso en toneladas del buque
	 */
	@JsonProperty(value="peso")
	private float peso;
	
	@JsonProperty(value="origen") 
	private String origen;
	
	@JsonProperty(value="destino")
	private String destino;
	
	
	@JsonProperty(value="idbuque")
	private int idbuque;
	
	@JsonProperty(value="areaDeAlmacenamiento")
	private int areaDeAlmacenamiento;
	
	@JsonProperty(value="due�o") 
	private int due�o;
	
	public Carga()
	{
		
	}
	/**
	 * M�todo constructor de la clase video
	 */
	public Carga(@JsonProperty(value="id")int id, 
			@JsonProperty(value="peso")float peso,
			@JsonProperty(value="tiempoEstadia") int tiempoEstadia,
			@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="origen") String origen,
			@JsonProperty(value="destino") String destino,
			@JsonProperty(value="buque") int idbuque,
			@JsonProperty(value="areaDeAlmacenamiento") int areaDeAlmacenamiento,
			@JsonProperty(value="due�o") int iddue�o) {
		super();
		this.id = id;
		this.peso = peso;
		this.tiempoEstadia = tiempoEstadia;
		this.tipo = tipo;
		this.idbuque = idbuque;
		this.areaDeAlmacenamiento = areaDeAlmacenamiento;
		this.due�o = iddue�o;
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
	
	
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTiempoEstadia() {
		return tiempoEstadia;
	}


	public void setTiempoEstadia(int tiempo) {
		this.tiempoEstadia = tiempo;
	}
	
	
	public int getBuque() {
		return idbuque;
	}


	public void setBuque(int buque) {
		this.idbuque = buque;
	}
	
	public float getPeso() {
		return peso;
	}


	public void setPeso(float peso) {
		this.peso = peso;
	}
	
	public int getAreaAlmacenamiento() {
		return areaDeAlmacenamiento;
	}


	public void setAreaDeAlmacenamiento(int area) {
		this.areaDeAlmacenamiento = area;
	}
	

	public int getDue�o ()
	{
		return due�o;
	}
	
	public void setDue�o(int due) {
		this.due�o = due;
	}
}
