package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos 
 * de la aplicaci�n
 */
public class DAODue�o
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
	public DAODue�o() 
	{
		recursos = new ArrayList<Object>();
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

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión
	 *  que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	// consulta dos
	public String consultarExportador(String idExpor, boolean ordenado) throws Exception
	{
		DAOActividadMaritima daoMar = new DAOActividadMaritima();
		daoMar.setConn(conn);
		
		int numActividades =  daoMar.darCantidadActividadesExportador(Integer.parseInt(idExpor));
		String cargas = daoMar.darCargasDelExportador(idExpor, ordenado);
		
		String sql = "SELECT * FROM DUE�O d WHERE d.idDue�o ='"+idExpor +"'";
		
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String name  = "";
		while (rsF.next()) {
			 name = rsF.getString("NOMBRE");
		}
		
		daoMar.cerrarRecursos();
		
		
		return "{ 'Nombre':'"+name+"','num Actividades':'"+numActividades+"', 'Cargas':'"+cargas+"'}";
	}
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO DUE�O VALUES (1, 'Elvis Tek')";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Pa�ses Bajos','Rotterdam');	


				//;
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel s�lido', 1,1,3,1);
				
				//;
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Gr�a p�rtico', 100, 1);
				

				
	}
}