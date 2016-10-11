package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Aceptado 
{
	@JsonProperty(value = "mensaje")
	private String mensaje;
	
	public Aceptado()
	{
		
	}
	public Aceptado(String mensaje)
	{
		super();
		this.mensaje = mensaje;
	}
	public String recuperarMensaje()
	{
		return mensaje;
	}
	public void setMensaje (String m)
	{
		mensaje = m;
	}

}
