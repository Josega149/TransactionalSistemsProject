package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Importador extends Dueño{


	/**
	 * Registro aduanero del importador
	 */
	@JsonProperty(value="registroAduana")
	private String registroAduana;

	/**
	 * Capacidad en toneladas del camión 
	 */
	@JsonProperty(value="esHabitual")
	private boolean esHabitual;
	
	
	public Importador(int id, String nombre,
			@JsonProperty(value="registroAduana")String regAd,
			@JsonProperty(value="esHabitual")boolean esHabitual) {
		super(id, nombre);
		// TODO Auto-generated constructor stub
		this.registroAduana = regAd;
		this.esHabitual = esHabitual;
	}
	
	public String getRegistroAduana() {
		return registroAduana;
	}


	public void setRegistroAduana(String regAd) {
		this.registroAduana = regAd;
	}
	
	public boolean getEsHabitual() {
		return esHabitual;
	}


	public void setEsHabitual(boolean esh) {
		this.esHabitual = esh;
	}

}
