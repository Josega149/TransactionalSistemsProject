package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ActividadMaritima {
////Atributos

	/**
	 * Id de la actividad marítima
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
	 * True si el buque arribó, false de lo contrario
	 */
	@JsonProperty(value="arriboDeBuque")
	private boolean arriboDeBuque;
	
	/**
	 * True si el buque salió, false de lo contrario
	 */
	@JsonProperty(value="salidaDeBuque")
	private boolean salidaDeBuque;
	
	/**
	 * True si se confirmó la carga, false de lo contrario
	 */
	@JsonProperty(value="cargaConfirmada")
	private boolean cargaConfirmada;
	
	/**
	 * Arraylist de equipos de apoyo utilizados en una actividad marítima
	 */
	@JsonProperty(value="equiposDeApoyo")
	private ArrayList <EquipoDeApoyo> equiposDeApoyo;
	
	/**
	 * Buque asociado a la actividad marítima
	 */
	@JsonProperty(value="buque")
	private Buque buque;
	
	/**
	 * Carga asociada a la actividad marítima
	 */
	@JsonProperty(value="carga")
	private Carga carga;
	
	/**
	 * Arraylist de equipos de apoyo utilizados en una actividad marítima
	 */
	@JsonProperty(value="dueño")
	private Dueño dueño;
	
	/**
	 * Muelle asociado a la actividad marítima
	 */
	@JsonProperty(value="muelle")
	private Muelle muelle;
	
	/**
	 * Id del operador asociado a la actividad marítima
	 */
	@JsonProperty(value="idOperador")
	private int idOperador;
	
	/**
	 * Costo facturado asociado a la actividad marítima
	 */
	@JsonProperty(value="costoFacturado")
	private CostoFacturado costoFacturado;
	
	public ActividadMaritima()
	{
		
	}
	
	/**
	 * Método constructor de la clase actividad
	 */
	public ActividadMaritima(@JsonProperty(value="id")int id, 
			@JsonProperty(value="fechaLlegada")String fechaLlegada,
			@JsonProperty(value="fechaSalida") String fechaSalida,
			@JsonProperty(value="esDescarga") boolean esDescarga,
			@JsonProperty(value="arriboDeBuque") boolean arriboDeBuque,
			@JsonProperty(value="salidaDeBuque") boolean salidaDeBuque,
			@JsonProperty(value="cargaConfirmada") boolean cargaConfirmada,
			@JsonProperty(value="buque") Buque buque,
			@JsonProperty(value="carga") Carga carga,
			@JsonProperty(value="dueño") Dueño dueño,
			@JsonProperty(value="muelle") Muelle muelle,
			@JsonProperty(value="idOperador") int idOperador,
			@JsonProperty(value="costoFacturado") CostoFacturado costoFacturado) {
		super();
		this.id = id;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.esDescarga = esDescarga;
		this.arriboDeBuque = arriboDeBuque;
		this.salidaDeBuque = salidaDeBuque;
		this.cargaConfirmada = cargaConfirmada;
		this.buque = buque;
		this.carga = carga;
		this.dueño = dueño;
		this.muelle = muelle;
		this.idOperador = idOperador;
		this.costoFacturado = costoFacturado;
		this.equiposDeApoyo = new ArrayList<EquipoDeApoyo>();
	}
	
//Métodos	

	/**
	 * Método getter del atributo id
	 * @return id del video
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del video ha sido
	 * cambiado con el valor que entra como parÃ¡metro
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
	
	public boolean getArriboDeBuque() {
		return arriboDeBuque;
	}


	public void setArriboDeBuque(boolean arriboDeBuque) {
		this.arriboDeBuque = arriboDeBuque;
	}
	
	public boolean getSalidaDeBuque() {
		return salidaDeBuque;
	}


	public void setSalidaDeBuque(boolean salidaDeBuque) {
		this.salidaDeBuque = salidaDeBuque;
	}
	
	public boolean getCargaConfirmada() {
		return cargaConfirmada;
	}


	public void setCargaConfirmada(boolean cargaConfirmada) {
		this.cargaConfirmada = cargaConfirmada;
	}
	
	public Buque getBuque() {
		return buque;
	}


	public void setBuque(Buque buque) {
		this.buque = buque;
	}
	
	public Carga getCarga() {
		return carga;
	}


	public void setCarga(Carga carga) {
		this.carga = carga;
	}
	
	public Dueño getDueño() {
		return dueño;
	}


	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}
	
	public Muelle getMuelle() {
		return muelle;
	}


	public void setMuelle(Muelle muelle) {
		this.muelle = muelle;
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
