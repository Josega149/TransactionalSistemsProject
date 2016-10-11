package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MovimientoAreaAlmacenamiento {
	
	@JsonProperty(value="idArea")
	private int idArea;
	
	@JsonProperty(value="idCarga")
	private int idCarga;
	
	@JsonProperty(value="entrada")
	private boolean entrada;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="pesoActual")
	private float pesoActual;
	
	@JsonProperty(value="capacidadTotal")
	private float capacidadTotal;
	
	@JsonProperty(value="habilitado")
	private boolean habilitado;
	
	public MovimientoAreaAlmacenamiento(@JsonProperty(value="idArea")int idArea,
			@JsonProperty(value="idCarga")int idCarga,
			@JsonProperty(value="fecha")String fecha,
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="entrada")boolean entrada, 
			@JsonProperty(value="pesoActual")float pesoActual,
			@JsonProperty(value="capacidadTotal")float capacidadTotal,
			@JsonProperty(value="habilitado")boolean habilitado){
		this.idArea = idArea;
		this.idCarga = idCarga;
		this.fecha = fecha;
		this.tipo = tipo;
		this.entrada = entrada;
		this.pesoActual = pesoActual;
		this.capacidadTotal = capacidadTotal;
		this.habilitado = habilitado;
	}

}
