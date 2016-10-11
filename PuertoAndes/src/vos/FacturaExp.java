package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FacturaExp {

	@JsonProperty(value = "id_exportador")
	private int id_exportador;
	
	@JsonProperty(value ="tipo_carga")
	private String tipo_carga;
	
	@JsonProperty(value = "num_elementos")
	private int num_elementos;
	
	@JsonProperty(value = "peso_promedio")
	private double peso_promedio;
	
	@JsonProperty(value = "precio")
	private double precio;

	public FacturaExp(@JsonProperty(value = "id_exportador") int id_exportador, 
					@JsonProperty(value ="tipo_carga") String tipo_carga, 
					@JsonProperty(value = "num_elementos") int num_elementos, 
					@JsonProperty(value = "peso_promedio") double peso_promedio, 
					@JsonProperty(value = "precio") double precio) 
	{
		super();
		this.id_exportador = id_exportador;
		this.tipo_carga = tipo_carga;
		this.num_elementos = num_elementos;
		this.peso_promedio = peso_promedio;
		this.precio = precio;
	}

	public int getId_exportador() {
		return id_exportador;
	}

	public void setId_exportador(int id_exportador) {
		this.id_exportador = id_exportador;
	}

	public String getTipo_carga() {
		return tipo_carga;
	}

	public void setTipo_carga(String tipo_carga) {
		this.tipo_carga = tipo_carga;
	}

	public int getNum_elementos() {
		return num_elementos;
	}

	public void setNum_elementos(int num_elementos) {
		this.num_elementos = num_elementos;
	}

	public double getPeso_promedio() {
		return peso_promedio;
	}

	public void setPeso_promedio(double peso_promedio) {
		this.peso_promedio = peso_promedio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	
}
