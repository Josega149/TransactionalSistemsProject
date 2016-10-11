package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOActividadTerrestre {

	/**
	 * Arraylits de recursos que se usan para la ejecuciÃ³n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiÃ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * MÃ©todo constructor que crea DAO
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOActividadTerrestre() 
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * MÃ©todo que cierra todos los recursos que estan enel arreglo de recursos
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
	 * MÃ©todo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parÃ¡metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	public void pruebaDeFKReferenciada () throws Exception
	{
		//se inserta una tupla que tiene una FK  de idAlmacenamiento referenciada,
		//es el tercer int despues de los boolean (7)
		String sql ="INSERT INTO ACTIVIDAD_TERRESTRE VALUES "
				+ "(15, '02-02-2017','03-02-2017', 'T','F', 2,2, 7, 9, 6,10);";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();		
	}
	
	public void pruebaDeFKNoReferenciada () throws Exception
	{
		//se inserta una tupla que tiene una FK  de idAlmacenamiento no referenciada,
		//es el tercer int despues de los boolean (55)
		String sql ="INSERT INTO ACTIVIDAD_TERRESTRE VALUES "
				+ "(15, '02-02-2017','03-02-2017', 'T','F', 2,2, 55, 9, 6,10);";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();		
	}
	
	public void borrarTuplaMaestraFK () throws Exception
	{
		String sql ="DELETE FROM COSTO_FACTURADO WHERE "
				+ "IDCOSTOFACTURADO=8;";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
	}
	
	public void borrarTuplaDependienteFK () throws Exception
	{
		String sql ="DELETE FROM ACTIVIDAD_TERRESTRE WHERE "
				+ "IDACTTER=8;";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
	}
	
	
	public void pruebaCumpleChequeo () throws Exception
	{
		//se inserta una tupla que tiene una FK  de idAlmacenamiento no referenciada,
		//es el tercer int despues de los boolean (55)
		String sql ="INSERT INTO ACTIVIDAD_TERRESTRE VALUES "
				+ "(20, '02-02-2020','03-02-2060', 'T','F', 2,2, 7, 9, 6,10);";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();		
	}
	
	public void pruebaNoCumpleChequeo () throws Exception
	{
		//se inserta una tupla que tiene una FK  de idAlmacenamiento no referenciada,
		//es el tercer int despues de los boolean (55)
		String sql ="INSERT INTO ACTIVIDAD_TERRESTRE VALUES "
				+ "(30, '02-02-2020','03-02-2060', 'holahola','que', 2,2, 7, 9, 6,10);";
		
		PreparedStatement prepStmtFAL = conn.prepareStatement(sql);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();		
	}

	
	
//	public String registrarLlegadaCarga(String idAgen,String idCarga,String pesoCarga, 
//			String tipoCarga, String idDueño, String areaDeAl, String fecha, String idCamion
//			) throws Exception
//	{
//		/**
//		String sqlVerificacion = "SELECT * FROM ACTIVIDAD_MARITIMA act"+
//				" WHERE act.idOperador = " + "'"+ idAgen + "'"
//				+" AND act.idMuelle  =" + "'"+ idMuelle + "'"
//				+" AND act.idBuque = " + "'"+ idBu + "'"
//			    +" AND act.arriboBuque = " + "'"+ "T" + "'"
//			    +" AND act.fechaSalida = "+ "'"+ fechaSalida + "'";
//		
//		System.out.println("SQL verificacion stmt:" + sqlVerificacion);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sqlVerificacion);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//		//  verificar rs.getString(SALIDA_BUQUE) == F
//		
//		*/
//		
//		
//		String sql = "UPDATE ACTIVIDAD_TERRESTRE "+ "SET CARGA_ENTREGADA = 'T' "+
//				" WHERE ACTIVIDAD_TERRESTRE.idOperador = " + "'"+ idAgen + "'"
//				+" AND ACTIVIDAD_TERRESTRE.idDueño  =" + "'"+ idDueño + "'"
//				+" AND ACTIVIDAD_TERRESTRE.fecha_Llegada = " + "'"+ fecha + "'"
//			    +" AND ACTIVIDAD_TERRESTRE.idalmacenamiento ='"+areaDeAl+"'"
//			    +" AND ACTIVIDAD_TERRESTRE.idCamion ='"+idCamion+"'"
//			    +" AND ACTIVIDAD_TERRESTRE.idCarga = "
//			    + "(SELECT c.ID "
//			    + " FROM CARGA c"
//			    + " WHERE c.PESO = '"+ pesoCarga + "'"
//			    + " AND c.TIPO = '"+tipoCarga+"');";
//		
//		System.out.println("SQL verificacion stmt:" + sql);
//
//		PreparedStatement prepStmtF = conn.prepareStatement(sql);
//		recursos.add(prepStmtF);
//		ResultSet rsF = prepStmtF.executeQuery();
//		
//		return "Cambio Realizado";
//	}
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO ACTIVIDAD_TERRESTRE VALUES (1, '01-02-2015','02-02-2015', 'T','T', 1,1, 2,10, 4, 3)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Países Bajos','Rotterdam');	

				//INSERT INTO BUQUE VALUES (1, 'La Pinta', 'Altamar', 'A300',500,1,5);
				
				//INSERT INTO CAMION VALUES (1, 18.5, 'Colcamiones');
				
				//INSERT INTO AREA_ALMACENAMIENTO VALUES (10);

				//INSERT INTO DUEÑO VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel sólido', 1,1,3,1);
				
				//INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016');
				
				//INSERT INTO ACTIVIDAD_MARITIMA VALUES (1, '01-02-2015','02-02-2015', 'T', 'T','F','F', 1, 1, 2, 1, 8, 5);
				
				//INSERT INTO ACTIVIDAD_TERRESTRE VALUES (1, '01-02-2015','02-02-2015', 'T','T', 1,1, 2,10, 4, 3);
				
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Grúa pórtico', 100, 1);
				

				
	}
	
	

}
