package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vos.CargaInconclusa;
import vos.DescargaInconclusa;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos 
 * de la aplicaci�n
 */
public class DAODescargasInconclusas
{


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAO
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAODescargasInconclusas() 
	{
		recursos = new ArrayList<Object>();
	}
	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	public void a�adirDescargaInconclusa(String idAgen,String idBuque, String idCarga, String fecha)throws Exception
	{
		String sql = "INSERT INTO CARGAS_INCONCLUSAS VALUES ("+idCarga+","+idBuque+","+fecha+","+idAgen+")";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	public void eliminarDescargaInconclusa(String idCarga) throws Exception
	{
		String sql = "DELETE FROM DESCARGAS_INCONCLUSAS WHERE idCarga ="+idCarga+";";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	public ArrayList <DescargaInconclusa>darDescargasInconclusas() throws Exception
	{
		ArrayList<DescargaInconclusa> cargas = new ArrayList<>();
		
		String sql = "SELECT * FROM DESCARGAS_INCONCLUSAS";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		while(rsF.next())
		{
			int idCarga = rsF.getInt("idCarga");
			int idBuque = rsF.getInt("idBuque");
			String fecha = rsF.getString("fecha");
			String idAgen = rsF.getString("idOperador");
			
			DescargaInconclusa nueva = new DescargaInconclusa(idBuque+"", idCarga+"", fecha, idAgen);
			
			cargas.add(nueva);
		}
		
		
		return cargas;
	}
	public boolean existeDescargaIncompParaBuque(String idBuque) throws Exception
	{
		boolean hayCargas = false;
		
		String sql = "SELECT * FROM DESCARGAS_INCONCLUSAS WHERE idBuque = '"+idBuque+"'";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		if(rsF.next())
		{
			hayCargas = true;
		}
		return hayCargas;
	}
	
	
}