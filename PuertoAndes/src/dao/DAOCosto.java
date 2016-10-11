package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vos.Carga;
import vos.CostoFacturado;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver
 *  los requerimientos de la aplicaci贸n
 */
public class DAOCosto
{

	int idActual;
	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * M茅todo constructor que crea DAO
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOCosto() 
	{
		recursos = new ArrayList<Object>();
		idActual =20;
	}

	/**
	 * M茅todo que cierra todos los recursos que estan enel arreglo de recursos
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

	/**
	 * M茅todo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como par谩metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	public int darProximoId()
	{
		int resp = idActual + 1;
		
		idActual ++;
		
		return resp;
	}
	
	public String registrarCosto(CostoFacturado costo, int idCarga) throws Exception
	{
		int id = costo.getId(); 
		int costoF = (int) costo.getCosto();
		String fecha = costo.getFecha();
		
		String sql = "INSERT INTO COSTO_FACTURADO VALUES('" + id +"','"+ costoF +"','"+ fecha +"')";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		//actualizar en actividad maritima el costo de la actividad
		String sql2 = "UPDATE ACTIVIDAD_MARITIMA SET idCosto = "+id+" WHERE idCarga = "+idCarga+"";
		
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
		
		return costo.toString();
	}
	
	public int darNumDias(int idCarga)throws Exception
	{
        String sqlAL = "SELECT  tiempo_estadia  FROM CARGA WHERE idCarga = '"+idCarga+"'";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
		rsFAL.next();
		return Integer.parseInt(rsFAL.getString("tiempo_estadia"));
	}
	
	public float darPeso(int idCarga)throws Exception
	{
       String sqlAL = "SELECT  peso  FROM CARGA WHERE idCarga = '"+idCarga+"'";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
		rsFAL.next();
		return Float.parseFloat(rsFAL.getString("peso"));
	}
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016')";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Pa韘es Bajos','Rotterdam');	


				//INSERT INTO DUE袿 VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel s髄ido', 1,1,3,1);
				
				//;
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Gr鷄 p髍tico', 100, 1);
				

				
	}
}