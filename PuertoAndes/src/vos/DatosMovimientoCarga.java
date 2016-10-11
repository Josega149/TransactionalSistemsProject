package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class DatosMovimientoCarga {

	@JsonProperty(value="idCarga")
	private int idCarga;
	
	@JsonProperty(value="origen")
	private String origen;
	
	@JsonProperty(value="destino")
	private String destino;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="fechaSalida")
	private String fechaSalida;
	
	@JsonProperty(value="fechaOrden")
	private String fechaOrden;
	
	@JsonProperty(value="costo")
	private float costo;
	
	public DatosMovimientoCarga (@JsonProperty(value="idCarga")int idCarga, 
			@JsonProperty(value="origen")String origen,
			@JsonProperty(value="destino")String destino, 
			@JsonProperty(value="tipo")String tipo,
			@JsonProperty(value="fechaSalida")String fechaSalida,
			@JsonProperty(value="fechaOrden")String fechaOrden,
			@JsonProperty(value="costo")float costo){
		
		this.idCarga = idCarga;
		this.origen = origen;
		this.destino = destino;
		this.tipo = tipo;
		this.fechaOrden = fechaOrden;
		this.fechaSalida = fechaSalida;
		this.costo = costo;
	}
	
}
