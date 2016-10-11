package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Bodega extends AreaDeAlmacenamiento{

	/**
	 * Largo
	 */
	@JsonProperty(value="largo")
	private float largo;

	/**
	 * Ancho
	 */
	@JsonProperty(value="ancho")
	private float ancho;

	/**
	 * True si tiene plataforma, false de lo contrario  
	 */
	@JsonProperty(value="tienePlataforma")
	private boolean tienePlataforma;

	
	/**
	 * Separación entre columnas
	 */
	@JsonProperty(value="separacion")
	private float separacion;
	
	
	public Bodega(int id, String tipo, 
			boolean habilitado, float capacidadTotal,
			float pesoActual,
			@JsonProperty(value="largo")float largo,
			@JsonProperty(value="ancho")float ancho,
			@JsonProperty(value="tienePlataforma")boolean tiene,
			@JsonProperty(value="separacion")float separacion) {
		super(id, tipo, habilitado, capacidadTotal, pesoActual);
		// TODO Auto-generated constructor stub
		this.largo = largo;
		this.ancho = ancho;
		this.tienePlataforma = tiene;
		this.separacion = separacion;
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
	
	public boolean getTienePlataforma() {
		return tienePlataforma;
	}


	public void setTienePlataforma(boolean tiene) {
		this.tienePlataforma = tiene;
	}
	
	public float getSeparacion() {
		return separacion;
	}


	public void setSeparacion(float cap) {
		this.separacion = cap;
	}

}
