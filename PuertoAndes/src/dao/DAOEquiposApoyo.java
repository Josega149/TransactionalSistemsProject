package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos 
 * de la aplicaci贸n
 */
public class DAOEquiposApoyo
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
	public DAOEquiposApoyo() 
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
	 * M茅todo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como par谩metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	// consulta dos
	public int darNumEquipos(String idAct) throws Exception
	{
		
		String sql = "SELECT COUNT(*)as NUM FROM EQUIPO_APOYO eq WHERE eq.idActMar = '"+idAct+"' GROUP BY eq.idActMar";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		rsF.next();
		return Integer.parseInt(rsF.getString("NUM"));
	}
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO EQUIPO_APOYO VALUES (1, 'Gr鷄 p髍tico', 100, 1)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Pa韘es Bajos','Rotterdam');	


				//;
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel s髄ido', 1,1,3,1);
				
				
	}
}