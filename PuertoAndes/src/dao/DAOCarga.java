package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vos.Carga;
import vos.ConsultarMovCargas;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos 
 * de la aplicación
 */
public class DAOCarga
{

	private static int ID_GERENTE_GENERAL = 201424484;
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
	public DAOCarga() 
	{
		recursos = new ArrayList<Object>();
	}
	/**
	 * MÃ©todo que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parÃ¡metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
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


	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO CARGA VALUES (1, 180, 1,'Granel sólido', 1,1,3,1)";

		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();

		//repetida

	}


	public float darPesoCarga(String idCarga) throws Exception
	{
		String sql = "SELECT peso FROM CARGA WHERE idcarga = "+idCarga+"";
		System.out.println("SQL verificacion stmt:" + sql);
		System.out.println("que");
		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		System.out.println("pasa");
		recursos.add(prepStmtF);
		System.out.println("holaa");
		ResultSet rsF = prepStmtF.executeQuery();
		float  peso = -1 ;
		System.out.println(peso);
		while (rsF.next()) 
		{
			peso = Float.parseFloat(rsF.getString("peso"));
		}
		System.out.println(peso);
		return peso;
	}
	public void trasladarAreaABuque(String idCarga, String idBuque) throws Exception
	{
		String sql = "UPDATE CARGA c SET c.idAlmacenamiento = null , c.idBuque = "+idBuque+" WHERE idcarga = "+idCarga+"";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();

	}


	public void trasladarAOtroBuque(String idCarga, String idBuqueOrigen, String idBuqueDestino) throws Exception
	{
		String sql = "UPDATE CARGA c SET c.idBuque = "+idBuqueDestino+
				" WHERE idCarga = "+idCarga+" AND idBuque = "+idBuqueOrigen;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();

	}

	public void trasladarAOtraArea(String idCarga, String idAreaOrigen, String idAreaDestino) throws Exception
	{
		String sql = "UPDATE CARGA c SET c.idAlmacenamiento = "+idAreaDestino+
				" WHERE idCarga = "+idCarga+" AND idAreaAlmacenamiento = "+idAreaOrigen;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();

	}

	public String consultarIDArea(String idCarga) throws Exception
	{
		String sql = "SELECT idAlmacenamiento FROM CARGA WHERE idcarga = "+idCarga+"";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  idArea = "" ;
		while (rsF.next()) 
		{
			idArea = rsF.getString("idAlmacenamiento");
		}

		return idArea;
	}

	//Retorna una lista de los IDs de las cargas que pertenecen a un buque.
	public ArrayList<String> idCargasPorBuque (String idBuque) throws SQLException
	{
		String sql = "SELECT idCarga FROM CARGA WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList <String>  lista = new ArrayList<String>();
		while (rsF.next()) 
		{
			lista.add(rsF.getString("idCarga"));
		}

		return lista;
	}

	public ArrayList<String> idCargasPorArea (String idArea) throws SQLException
	{
		String sql = "SELECT idCarga FROM CARGA WHERE idAlmacenamiento = "+idArea+" FOR UPDATE";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList <String>  lista = new ArrayList<String>();
		while (rsF.next()) 
		{
			lista.add(rsF.getString("idCarga"));
		}

		return lista;
	}

	public String darTipoCarga(String idCarga) throws Exception
	{
		String sql = "SELECT tipo FROM CARGA WHERE idCarga = "+idCarga;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();

		String tipo = "";
		while (rsF.next()) 
		{
			tipo =  (rsF.getString("tipo"));
		}

		return tipo;
	}
	public String consultarIDBuque(String idCarga) throws Exception
	{
		String sql = "SELECT idBuque FROM CARGA WHERE idcarga = "+idCarga+"";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  idBuque = "" ;
		while (rsF.next()) 
		{
			idBuque = rsF.getString("idBuque");
		}

		return idBuque;
	}
	public void trasladarBuqueAArea(String idCarga, String idArea)throws Exception
	{
		String sql = "UPDATE CARGA c SET  c.idBuque  = null ,c.idAlmacenamiento= "+idArea+" WHERE idcarga = "+idCarga+"";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	public ArrayList<Carga> consultaDeCargas(ConsultarMovCargas consulta)throws Exception
	{
		ArrayList<Carga>  cargas= new ArrayList<Carga>();

		String sql = "SELECT c.* FROM (CARGA c INNER JOIN ACTIVIDAD_MARITIMA act) ON c.idCarga =act.idCarga "+
				"  WHERE ("+consulta.darIdSolicita()+" = c.idDueño  or "+consulta.darIdSolicita()+" = "+ID_GERENTE_GENERAL+" )";

		if(  consulta.darOrig()!= null  && !consulta.darOrig().equals("")  )
		{
			sql+= " and c.origen = '"+consulta.darOrig()+"'";
		}
		if( consulta.darDes()!= null  && !consulta.darDes().equals(""))
		{
			sql+= " and c.destino = '"+consulta.darDes()+"'";
		}
		if( consulta.darIdCarga()!= null  && !consulta.darIdCarga().equals(""))
		{
			sql+= " and c.idCarga = "+consulta.darIdCarga()+"";
		}
		if( consulta.darTipo()!= null  && !consulta.darTipo().equals(""))
		{
			sql+= " and c.tipo = '"+consulta.darTipo()+"'";
		}
		if( consulta.darFechaMov()!= null  && !consulta.darFechaMov().equals(""))
		{
			sql+= " and act.fecha_Llegada = '"+consulta.darFechaMov()+"'";
		}


		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();

		while(rsF.next())
		{
			Carga nueva = new Carga(
					rsF.getInt("idCarga"),
					rsF.getFloat("peso"),
					rsF.getInt("tiempo_Estadia"),
					rsF.getString("tipo"),
					rsF.getString("origen"),
					rsF.getString("destino"),
					rsF.getInt("idBuque"),
					rsF.getInt("idAlmacenamiento"),
					rsF.getInt("idDueño"));

			cargas.add(nueva);
		}

		return cargas;
	}
	public ArrayList<String> consultaIdAreasParaCargas(String idConsulta, String idCargaEsp)throws Exception
	{
		if(idConsulta.equals(ID_GERENTE_GENERAL+""))
		{
			// va a recuperar las areas que tienen cargas
			String sql = "SELECT idAlmacenamiento, idCarga FROM CARGA WHERE idAlmacenamiento != null";
			//String sql = "SELECT mov.fecha, mov.fecha, mov.entrada, mov.id_Area, mov.id_Carga FROM MOVIMIENTO_CARGA_AREA mov";
			if(idCargaEsp != null){sql+= " and idCarga = '"+idCargaEsp+"'";}
			sql += " for update";
			
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			ArrayList<String> areas =  new ArrayList<String>();
			while (rsF.next()) 
			{
				String idArea = rsF.getString("idAlmacenamiento");
				String idCarga = rsF.getString("idCarga");
				areas.add(idArea + ","+idCarga);
			}

			return areas;
		}
		else
		{
			// va a recuperar las areas que tienen cargas
			String sql = "SELECT idAlmacenamiento, idCarga FROM CARGA WHERE idAlmacenamiento != null and idDueño = '"+idConsulta+"'";
			
					if(idCargaEsp != null){sql+= " and idCarga = '"+idCargaEsp+"'";}
					
					sql += " for update";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			ArrayList<String> areas =  new ArrayList<String>();
			while (rsF.next()) 
			{
				String idArea = rsF.getString("idAlmacenamiento");
				String idCarga = rsF.getString("idCarga");
				areas.add(idArea + ","+idCarga);
			}

			return areas;	
		}


	}
}