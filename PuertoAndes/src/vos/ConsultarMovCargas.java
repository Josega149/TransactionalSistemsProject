package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultarMovCargas 
{
	@JsonProperty(value="origen")
	private String origen;
	
	@JsonProperty(value="destino")
	private String destino;
	
	@JsonProperty(value="idCarga")
	private String idCarga;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="fechaMov")
	private String fechaMov;
	
	@JsonProperty(value="idDelQueSolicita")
	private String idDelQueSolicita;
	
	
	public ConsultarMovCargas()
	{
		
	}
	
	public String darOrig()
	{
		return origen;
	}
	public String darDes()
	{
		return destino;
	}
	public String darIdCarga()
	{
		return idCarga;
	}
	public String darTipo()
	{
		return tipo;
	}
	public String darFechaMov()
	{
		return fechaMov;
	}
	public String darIdSolicita()
	{
		return idDelQueSolicita;
	}
	
	

}
