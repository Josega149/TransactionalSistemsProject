package vos;


import org.codehaus.jackson.annotate.JsonProperty;

public class ActividadTerrestre {
////Atributos

	/**
	 * Id de la actividad terrestre
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Fecha de llegada
	 */
	@JsonProperty(value="fechaLlegada")
	private String fechaLlegada;

	/**
	 * Fecha de salida
	 */
	@JsonProperty(value="fechaSalida")
	private String fechaSalida;

	/**
	 * True si la act. es de descarga, false si es de carga
	 */
	@JsonProperty(value="esDescarga")
	private boolean esDescarga;
	
	/**
	 * True si se entreg� la carga, false de lo contrario
	 */
	@JsonProperty(value="cargaEntregada")
	private boolean cargaEntregada;
	
		
	
	/**
	 * Camion asociado a la actividad mar�tima
	 */
	@JsonProperty(value="camion")
	private Camion camion;
	
	/**
	 * Carga asociada a la actividad mar�tima
	 */
	@JsonProperty(value="carga")
	private Carga carga;
	
	/**
	 * Arraylist de equipos de apoyo utilizados en una actividad mar�tima
	 */
	@JsonProperty(value="due�o")
	private Due�o due�o;
	
	/**
	 * Muelle asociado a la actividad mar�tima
	 */
	@JsonProperty(value="areaDeAlmacenamiento")
	private AreaDeAlmacenamiento areaDeAlmacenamiento;
	
	/**
	 * Id del operador asociado a la actividad mar�tima
	 */
	@JsonProperty(value="idOperador")
	private int idOperador;
	
	/**
	 * Costo facturado asociado a la actividad mar�tima
	 */
	@JsonProperty(value="costoFacturado")
	private CostoFacturado costoFacturado;
	
	
	
	/**
	 * M�todo constructor de la clase video
	 */
	public ActividadTerrestre(@JsonProperty(value="id")int id, 
			@JsonProperty(value="fechaLlegada")String fechaLlegada,
			@JsonProperty(value="fechaSalida") String fechaSalida,
			@JsonProperty(value="esDescarga") boolean esDescarga,
			@JsonProperty(value="cargaEntregada") boolean cargaEntregada,
			@JsonProperty(value="camion") Camion camion,
			@JsonProperty(value="carga") Carga carga,
			@JsonProperty(value="due�o") Due�o due�o,
			@JsonProperty(value="areaDeAlmacenamiento") AreaDeAlmacenamiento areaDeAlmacenamiento,
			@JsonProperty(value="idOperador") int idOperador,
			@JsonProperty(value="costoFacturado") CostoFacturado costoFacturado) {
		super();
		this.id = id;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.esDescarga = esDescarga;
		this.cargaEntregada = cargaEntregada;
		this.camion = camion;
		this.carga = carga;
		this.due�o = due�o;
		this.areaDeAlmacenamiento = areaDeAlmacenamiento;
		this.idOperador = idOperador;
		this.costoFacturado = costoFacturado;
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
	
	
	public String getFechaLlegada() {
		return fechaLlegada;
	}


	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	
	
	public boolean getEsDescarga() {
		return esDescarga;
	}


	public void setEsDescarga(boolean esDescarga) {
		this.esDescarga = esDescarga;
	}
	

	
	public boolean getCargaEntregada() {
		return cargaEntregada;
	}


	public void setCargaEntregada(boolean cargaEnt) {
		this.cargaEntregada = cargaEnt;
	}
	
	public Camion getCamion() {
		return camion;
	}


	public void setCamion(Camion camion) {
		this.camion = camion;
	}
	
	public Carga getCarga() {
		return carga;
	}


	public void setCarga(Carga carga) {
		this.carga = carga;
	}
	
	public Due�o getDue�o() {
		return due�o;
	}


	public void setDue�o(Due�o due�o) {
		this.due�o = due�o;
	}
	
	public AreaDeAlmacenamiento getAreaDeAlmacenamiento() {
		return areaDeAlmacenamiento;
	}


	public void setAreaDeAlmacenamiento(AreaDeAlmacenamiento area) {
		this.areaDeAlmacenamiento = area;
	}
	
	public int getIdOperador() {
		return idOperador;
	}


	public void setIdOperador(int idOperador) {
		this.idOperador = idOperador;
	}
	
	public CostoFacturado getCostoFacturado() {
		return costoFacturado;
	}


	public void setCostoFacturado(CostoFacturado costoFacturado) {
		this.costoFacturado = costoFacturado;
	}
}
