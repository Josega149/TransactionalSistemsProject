package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver 
 * los requerimientos de la aplicaci贸n
 */
public class DAOActividadEntregaMercancia
{


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
	public DAOActividadEntregaMercancia() 
	{
		recursos = new ArrayList<Object>();
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
	 * M茅todo que inicializa la connection del DAO a 
	 * la base de datos con la conexi贸n que entra como par谩metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}



	/**
	 * Metodo que registra la salida de un muelle
	 * cambia la BD en salida buque = T
	 * 
	 * * REGISTRAR SALIDA BUQUE
	 *
	 */
	
	public String registrarSalidaMuelle(String idAgen,String idBu,String idMuelle, 
			String fechaSalida) throws Exception
	{
		/**
		String sqlVerificacion = "SELECT * FROM ACTIVIDAD_MARITIMA act"+
				" WHERE act.idOperardor = " + "'"+ idAgen + "'"
				+" AND act.idMuelle  =" + "'"+ idMuelle + "'"
				+" AND act.idBuque = " + "'"+ idBu + "'"
			    +" AND act.arriboBuque = " + "'"+ "T" + "'"
			    +" AND act.fechaSalida = "+ "'"+ fechaSalida + "'";
		
		System.out.println("SQL verificacion stmt:" + sqlVerificacion);

		PreparedStatement prepStmt = conn.prepareStatement(sqlVerificacion);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		//  verificar rs.getString(SALIDA_BUQUE) == F
		
		*/
		
		
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET salida_Buque = 'T' "+
				" WHERE act.idOperador = '"+ idAgen + "'"
				+" AND act.idMuelle  = '"+ idMuelle + "'"
				+" AND act.idBuque = '"+ idBu + "'"
			    +" AND act.arribo_Buque = '"+ "T" + "'"
			    +" AND act.fecha_Salida = '"+ fechaSalida + "';";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		return "Cambio Realizado";
	}
	
	/**
	 * Metodo que registra la salida de un muelle
	 * cambia la BD en salida buque = T
	 * 
	 * * REGISTRAR LLEGADA DE UNA CARGA A UN 罵EA DE ALMACENAMIENTO	
	 *
	 */
	
	public String registrarEntregaMercancia(String idAgen, String idMercan, 
			String idDue駉,  String fecha)throws Exception
	{	
		//validaciones
		String sqlAN = "SELECT idDue駉 FROM CARGA c "+ 
				" where idDue駉 = '"+idDue駉+"' and idCarga = '"+idMercan+"'";
		
		System.out.println("SQL verificacion stmt:" + sqlAN);

		PreparedStatement prepStmtFAN = conn.prepareStatement(sqlAN);
		recursos.add(prepStmtFAN);
		ResultSet rsFAN = prepStmtFAN.executeQuery();
		
		boolean tiene = rsFAN.next();
		System.out.println(tiene);
		if(tiene == false){throw new Exception("datos incorrectos");}
		
		
		String sql = "INSERT INTO ACTIVIDAD_ENTREGA_MERCANCIA "+ 
				" VALUES ('" + idMercan+ "','"+idAgen +"','"+idDue駉+"','"+fecha+"','"+idMercan +"')";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		// vaciar almacen, eliminar cargas del registro
		// en el ejemplo antes era 7
		
		String sqlAL = "UPDATE CARGA SET tiempo_estadia = -1 "+ 
				"WHERE CARGA.idCarga = '"+ idMercan +"'";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
		return "{\"entrega de carga\":\"Entrega Realizada\"}";
	}
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO ACTIVIDAD_ENTREGA_MERCANCIA VALUES(0,0,0,'01-02-2015',0)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       String sqlAL2 = "INSERT INTO ACTIVIDAD_ENTREGA_MERCANCIA VALUES(0,0,0,'01-02-2015',0)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL2);

		PreparedStatement prepStmtFAL2 = conn.prepareStatement(sqlAL2);
		recursos.add(prepStmtFAL2);
		ResultSet rsFAL2 = prepStmtFAL2.executeQuery();
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Pa韘es Bajos','Rotterdam');	

				//INSERT INTO BUQUE VALUES (1, 'La Pinta', 'Altamar', 'A300',500,1,5);
				
				//INSERT INTO CAMION VALUES (1, 18.5, 'Colcamiones');
				
				//INSERT INTO AREA_ALMACENAMIENTO VALUES (10);

				//INSERT INTO DUE袿 VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel s髄ido', 1,1,3,1);
				
				//INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016');
				
				//INSERT INTO ACTIVIDAD_MARITIMA VALUES (1, '01-02-2015','02-02-2015', 'T', 'T','F','F', 1, 1, 2, 1, 8, 5);
				
				//INSERT INTO ACTIVIDAD_TERRESTRE VALUES (1, '01-02-2015','02-02-2015', 'T','T', 1,1, 2,10, 4, 3);
				
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Gr鷄 p髍tico', 100, 1);
				

				
	}




}
