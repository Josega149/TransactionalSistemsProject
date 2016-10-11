package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Exportador extends Dueño{

	/**
	 * Registro único tributario de exportador
	 */
	@JsonProperty(value="rut")
	private String rut;

	
	
	public Exportador(int id, String nombre,
			@JsonProperty(value="rut")String rut) {
		super(id, nombre);
		// TODO Auto-generated constructor stub
		this.rut = rut;
	}
	
	public String getRUT() {
		return rut;
	}


	public void setRUT(String rut) {
		this.rut = rut;
	}
	

}
