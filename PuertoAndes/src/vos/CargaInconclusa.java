package vos;

public class CargaInconclusa 
{
	private String idBuque;
	private String idCarga;
	private String fecha;
	private String idAgent;
	public CargaInconclusa()
	{
		
	}
	public CargaInconclusa(String bu, String car, String fec, String agen)
	{
		idBuque = bu;
		idCarga = car;
		fecha = fec;
		idAgent = agen;
	}
	public String darBuque() {
		return idBuque;
	}
	public String darCarga() {
		return idCarga;
	}
	public String darAgen() {
		return idAgent;
	}
	public String darFecha() {
		return fecha;
	}
	

}
