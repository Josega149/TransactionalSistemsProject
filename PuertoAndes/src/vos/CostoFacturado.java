package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CostoFacturado 
{
	/**
	 * Id del costo facturado
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Fecha en que se factura el costo
	 */
	@JsonProperty(value="fecha")
	private String fecha;

	/**
	 * Costo en dólares 
	 */
	@JsonProperty(value="costo")
	private float costo;

	
	/**
	 * Método constructor de la clase video
	 */
	public CostoFacturado(@JsonProperty(value="id")int id, 
			@JsonProperty(value="costo")float costo,
			@JsonProperty(value="fecha") String fecha) {
		super();
		this.id = id;
		this.costo = costo;
		this.fecha = fecha;
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
	
	
	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public float getCosto() {
		return costo;
	}


	public void setCapacidad(float costo) {
		this.costo = costo;
	}
	
	public String toString()
	{
		String todo ="{\"id\":"+id+",\"fecha\":\""+fecha+"\",\"costo\":\""+costo
                +"\"}";;
		return todo;
	}
}
