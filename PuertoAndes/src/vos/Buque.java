package vos;


import org.codehaus.jackson.annotate.JsonProperty;

public class Buque {
////Atributos

	/**
	 * Id del buque
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre del buque
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Nombre del agente mar�timo asociado al buque 
	 */
	@JsonProperty(value="agenteMaritimo")
	private String agenteMaritimo;

	/**
	 * Registro de capitan�a asociado al buque
	 */
	@JsonProperty(value="registroCapitania")
	private String registroCapitania;
	
	/**
	 * Peso en toneladas del buque
	 */
	@JsonProperty(value="peso")
	private float peso;
	
	/**
	 * Tipo del buque (multiprop�sito, portacontenedores o roro)
	 */
	@JsonProperty(value="tipo")
	private char tipo;
	
	/**
	 * True si est� en proceso de carga, false de lo contrario.
	 */
	@JsonProperty(value="procesoDeCarga")
	private boolean procesoDeCarga;

	/**
	 * True si est� en proceso de descarga, false de lo contrario.
	 */
	@JsonProperty(value="procesoDeDescarga")
	private boolean procesoDeDescarga;
	
	/**
	 * True si la carga se complet� totalmente, false de lo contrario.
	 */
	@JsonProperty(value="cargaCompletada")
	private boolean cargaCompletada;
	
	/**
	 * True si la descarga se complet� totalmente, false de lo contrario.
	 */	
	@JsonProperty(value="descargaCompletada")
	private boolean descargaCompletada;
	
	/**
	 * 'A' si est� deshabilitado por aver�a.
	 * 'M' si est� deshabilitado por mantenimiento.
	 * 'L' si est� deshabilitado por razones legales.
	 * 'N' si no est� deshabilitado.
	 */
	@JsonProperty(value="deshabilitado")
	private char deshabilitado;	
	
	/**
	 * Puerto del que viene el buque
	 */
	@JsonProperty(value="puertoOrigen")
	private Puerto puertoOrigen;
	
	/**
	 * Puerto al que se dirige el buque 
	 */
	@JsonProperty(value="puertoDestino")
	private Puerto puertoDestino;
	
	
	
	/**
	 * M�todo constructor de la clase video
	 */
	public Buque(@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre,
			@JsonProperty(value="agenteMaritimo") String agenteMaritimo,
			@JsonProperty(value="registroCapitania") String registroCapitania,
			@JsonProperty(value="peso") float peso,
			@JsonProperty(value="tipo") char tipo,
			@JsonProperty(value="procesoDeCarga") boolean procesoDeCarga,
			@JsonProperty(value="procesoDeDescarga") boolean procesoDeDescarga,
			@JsonProperty(value="cargaCompletada") boolean cargaCompletada,
			@JsonProperty(value="descargaCompletada") boolean descargaCompletada,
			@JsonProperty(value="deshabilitado") char deshabilitado,
			@JsonProperty(value="puertoOrigen") Puerto puertoOrigen,
			@JsonProperty(value="puertoDestino") Puerto puertoDestino) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.agenteMaritimo = agenteMaritimo;
		this.registroCapitania = registroCapitania;
		this.peso = peso;
		this.tipo = tipo;
		this.procesoDeCarga = procesoDeCarga;
		this.procesoDeDescarga = procesoDeDescarga;
		this.cargaCompletada = cargaCompletada;
		this.descargaCompletada = descargaCompletada;
		this.deshabilitado = deshabilitado;
		this.puertoOrigen = puertoOrigen;
		this.puertoDestino = puertoDestino;
	}
	
//M�todos	

	/**
	 * M�todo getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * M�todo setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parámetro
	 * @param id - Id del video
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAgenteMaritimo() {
		return agenteMaritimo;
	}


	public void setAgenteMaritimo(String agMar) {
		this.agenteMaritimo = agMar;
	}
	
	
	public String getRegistroCapitania() {
		return registroCapitania;
	}


	public void setRegistroCapitania(String regCap) {
		this.registroCapitania = regCap;
	}
	
	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}
	
	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo= tipo;
	}
	
	public boolean getProcesoCarga() {
		return procesoDeCarga;
	}

	public void setProcesoCarga(boolean carga) {
		this.procesoDeCarga = carga;
	}
	
	public boolean getProcesoDescarga() {
		return procesoDeDescarga;
	}

	public void setProcesoDescarga(boolean carga) {
		this.procesoDeDescarga = carga;
	}
	
	public boolean getCargaCompletada() {
		return cargaCompletada;
	}

	public void setCargaCompletada(boolean carga) {
		this.cargaCompletada = carga;
	}
	
	public boolean getDescargaCompletada() {
		return descargaCompletada;
	}

	public void setDescargaCompletada(boolean carga) {
		this.descargaCompletada = carga;
	}
	
	public char getDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(char deshabilitado) {
		this.deshabilitado = deshabilitado;
	}
	
	public Puerto getPuertoOrigen() {
		return puertoOrigen;
	}


	public void setPuertoOrigen(Puerto puOrigen) {
		this.puertoOrigen = puOrigen;
	}
	
	public Puerto getPuertoDestino() {
		return puertoDestino;
	}


	public void setPuertoDestino(Puerto puDest) {
		this.puertoDestino = puDest;
	}
	
}
