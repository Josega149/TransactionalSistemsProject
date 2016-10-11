package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cobertizo extends AreaDeAlmacenamiento{

	/**
	 * Dimensiones en metros cuadrados del cobertizo
	 */
	@JsonProperty(value="dimensiones")
	private float dimensiones;

	/**
	 * Tipo de carga que guarda el cobertizo
	 */
	@JsonProperty(value="tipoDeCarga")
	private String tipoDeCarga;
	
	public Cobertizo(int id, String tipo, 
			boolean habilitado, float capacidadTotal,
			float pesoActual,
			@JsonProperty(value="dimensiones")float dimensiones,
			@JsonProperty(value="tipoDeCarga")String tipoDeCarga) {
		super(id, tipo, habilitado, capacidadTotal, pesoActual);
		this.dimensiones = dimensiones;
		this.tipoDeCarga = tipoDeCarga;
		// TODO Auto-generated constructor stub
	}
	
	public float getDimensiones() {
		return dimensiones;
	}


	public void setDimensiones(float dimensiones) {
		this.dimensiones= dimensiones;
	}
	
	public String getTipoDeCarga() {
		return tipoDeCarga;
	}


	public void setTipoDeCarga(String tipo) {
		this.tipoDeCarga= tipo;
	}

}
