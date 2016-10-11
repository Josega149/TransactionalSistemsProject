package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CuartoFrio {

	/**
	 * id del cuarto frio
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * ¡rea del cuarto
	 */
	@JsonProperty(value="area")
	private float area;

	/**
	 * largo  
	 */
	@JsonProperty(value="largo")
	private float largo;

	
	/**
	 * ancho
	 */
	@JsonProperty(value="ancho")
	private float ancho;
	
	/**
	 * alto
	 */
	@JsonProperty(value="alto")
	private float alto;
	
	/**
	 * area en funcion de la bodega 
	 */
	@JsonProperty(value="areaEnFuncionBodega")
	private int areaEnFuncionBodega;
	
	/**
	 * Bodega asociada al cuarto frio
	 */
	@JsonProperty(value="bodega")
	private Bodega bodega;
	
	public CuartoFrio(@JsonProperty(value="id")int id,
			@JsonProperty(value="area")float area,
			@JsonProperty(value="largo")float largo,
			@JsonProperty(value="ancho")float ancho,
			@JsonProperty(value="alto")float alto,
			@JsonProperty(value="areaEnFuncionBodega")int aefb,
			@JsonProperty(value="bodega")Bodega bodega) {
		this.id = id;
		this.area = area;
		this.largo = largo;
		this.ancho = ancho;
		this.alto =alto;
		this.areaEnFuncionBodega = aefb;
		this.bodega = bodega;
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
	
	public float getArea() {
		return area;
	}


	public void setArea(float area) {
		this.area= area;
	}
	
	public float getLargo() {
		return largo;
	}


	public void setLargo(float largo) {
		this.largo= largo;
	}
	
	public float getAncho() {
		return ancho;
	}


	public void setAncho(float ancho) {
		this.ancho = ancho;
	}
	
	public float getAlto() {
		return alto;
	}


	public void setAlto(float alto) {
		this.alto= alto;
	}
	
	public int getAreaEnFuncionBodega() {
		return areaEnFuncionBodega;
	}


	public void setAreaEnFuncionBodega(int aefb) {
		this.areaEnFuncionBodega = aefb;
	}
	
	public Bodega getBodega() {
		return bodega;
	}


	public void setBodega(Bodega bod ) {
		this.bodega = bod;
	}
	
}
