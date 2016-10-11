package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos 
 * de la aplicación
 */
public class DAODueño
{


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
	public DAODueño() 
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
	 * MÃ©todo que inicializa la connection del DAO a la base de datos con la conexiÃ³n
	 *  que entra como parÃ¡metro.
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
		
		String sql = "SELECT * FROM DUEÑO d WHERE d.idDueño ='"+idExpor +"'";
		
		
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
		String sqlAL = "INSERT INTO DUEÑO VALUES (1, 'Elvis Tek')";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Países Bajos','Rotterdam');	


				//;
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel sólido', 1,1,3,1);
				
				//;
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Grúa pórtico', 100, 1);
				

				
	}
}