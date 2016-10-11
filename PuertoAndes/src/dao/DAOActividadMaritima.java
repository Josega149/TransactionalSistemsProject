package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import vos.Aceptado;
import vos.ActividadMaritima;
import vos.Buque;
import vos.Carga;
import vos.CostoFacturado;
import vos.DatosMovimientoCarga;
import vos.Dueño;
import vos.Muelle;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaciÃ³n
 * @author Jg.tamura10
 */
public class DAOActividadMaritima
{


	/**
	 * Arraylists de recursos que se usan para la ejecución de sentencias SQL
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
	public DAOActividadMaritima() 
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recursos del arreglo de recursos han sido cerrados
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
	 * Método que inicializa la connection del DAO a la base de datos con la conexiÃ³n que entra como parÃ¡metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}



	/**
	 * Método que registra la salida de un muelle
	 * cambia la BD en salida buque = T
	 * 
	 * * REGISTRAR SALIDA BUQUE
	 *
	 */
	
	public String registrarSalidaMuelle(String idAgen,String idBu,String idMuelle, String fechaSalida) throws Exception
	{
		/**
		String sqlVerificacion = "SELECT * FROM ACTIVIDAD_MARITIMA act"+
				" WHERE act.idOperador = " + "'"+ idAgen + "'"
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
		System.out.println( "datos entrada = "+ idAgen+ "  "+ idBu + "  "+idMuelle + "  "+ fechaSalida);
		
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET salida_Buque = 'T'"+
				" WHERE ACTIVIDAD_MARITIMA.idOperador = '"+ idAgen + "'"
				+" AND ACTIVIDAD_MARITIMA.idMuelle  ='"+ idMuelle + "'"
				+" AND ACTIVIDAD_MARITIMA.idBuque = '"+ idBu + "'"
			    +" AND ACTIVIDAD_MARITIMA.arribo_Buque = '"+ "T" + "'"
			    +" AND ACTIVIDAD_MARITIMA.fecha_Salida = '"+ fechaSalida + "'";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		return "{\"salida de buque\":\"Cambio Realizado\"}";
	}
	
	/**
	 * 
	 * 
	 * * REGISTRAR TIPO DE UNA CARGA DE UN BUQUE
	 *
	 */
	
	public Aceptado registrarTipoDeCargaDeUnBuque(String idOperador,String idBuque,
			String idCarga,String fechaLlegada) throws Exception
	{
		/**
		
		//http://localhost:8080/VideoAndes/rest/PuertoAndes/operador/4/registrarTipoDeCargaDeUnaCarga/2/nombreBuque/La Niña/nombreExportador/Aquiles Bailo/idExportador/8/idCarga/4/tipoCarga/Vehículos/pesoCarga/600/fechaLlegada/01-02-2015
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET CARGA_CONFIRMADA = 'T' "+
				" WHERE ACTIVIDAD_MARITIMA.idOperador = '"+ idOperador + "'"
				+" AND ACTIVIDAD_MARITIMA.idBuque  = '"+ idBuque + "'"
				+" AND ACTIVIDAD_MARITIMA.fecha_Llegada = '"+ fechaLlegada + "'"
			    +" AND ACTIVIDAD_MARITIMA.arribo_Buque = 'T'"
			    +" AND ACTIVIDAD_MARITIMA.es_descarga = 'F'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = '"+idCarga+"'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = "
			    + "(SELECT idCarga "
			    + " FROM CARGA"
			    + " WHERE CARGA.PESO = '"+ pesoCarga + "'"
			    + " AND CARGA.TIPO = '"+tipoCarga+"'"
			    + " AND CARGA.IDDUEÑO = '"+idExportador+"')";
	  */
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET CARGA_CONFIRMADA = 'T' "+
				" WHERE ACTIVIDAD_MARITIMA.idOperador = '"+ idOperador + "'"
				+" AND ACTIVIDAD_MARITIMA.idBuque  = '"+ idBuque + "'"
				+" AND ACTIVIDAD_MARITIMA.fecha_Llegada = '"+ fechaLlegada + "'"
			    +" AND ACTIVIDAD_MARITIMA.arribo_Buque = 'T'"
			    +" AND ACTIVIDAD_MARITIMA.es_descarga = 'F'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = '"+idCarga+"'";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		return new Aceptado(" carga confirmada ");
	}
	public Aceptado registrarTipoDeCargaDeUnBuqueParaDescarga(String idOperador,String idBuque,
			String idCarga,String fechaLlegada) throws Exception
	{
		/**
		
		//http://localhost:8080/VideoAndes/rest/PuertoAndes/operador/4/registrarTipoDeCargaDeUnaCarga/2/nombreBuque/La Niña/nombreExportador/Aquiles Bailo/idExportador/8/idCarga/4/tipoCarga/Vehículos/pesoCarga/600/fechaLlegada/01-02-2015
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET CARGA_CONFIRMADA = 'T' "+
				" WHERE ACTIVIDAD_MARITIMA.idOperador = '"+ idOperador + "'"
				+" AND ACTIVIDAD_MARITIMA.idBuque  = '"+ idBuque + "'"
				+" AND ACTIVIDAD_MARITIMA.fecha_Llegada = '"+ fechaLlegada + "'"
			    +" AND ACTIVIDAD_MARITIMA.arribo_Buque = 'T'"
			    +" AND ACTIVIDAD_MARITIMA.es_descarga = 'F'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = '"+idCarga+"'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = "
			    + "(SELECT idCarga "
			    + " FROM CARGA"
			    + " WHERE CARGA.PESO = '"+ pesoCarga + "'"
			    + " AND CARGA.TIPO = '"+tipoCarga+"'"
			    + " AND CARGA.IDDUEÑO = '"+idExportador+"')";
	  */
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET CARGA_CONFIRMADA = 'T' "+
				" WHERE ACTIVIDAD_MARITIMA.idOperador = '"+ idOperador + "'"
				+" AND ACTIVIDAD_MARITIMA.idBuque  = '"+ idBuque + "'"
				+" AND ACTIVIDAD_MARITIMA.fecha_Llegada = '"+ fechaLlegada + "'"
			    +" AND ACTIVIDAD_MARITIMA.arribo_Buque = 'T'"
			    +" AND ACTIVIDAD_MARITIMA.es_descarga = 'T'"
			    +" AND ACTIVIDAD_MARITIMA.idCarga = '"+idCarga+"')";
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		return new Aceptado(" carga confirmada ");
	}
	
	
	//consulta 
	public String consultarSalidas(String fechaIni,String fechaFin, String nombreBuque, String tipoBuque, String tipoCarga) throws Exception
	{
		/**
		 * SELECT act.* FROM (ACTIVIDAD_MARITIMA  act INNER JOIN BUQUE buque ON act.idBuque = buque.idBuque)
INNER JOIN CARGA c ON act.idCarga = c.idCarga WHERE 
 act.fecha_llegada >= '14/04/2016'and act.fecha_salida <= '15/04/2016' and buque.nombre = 'buque196' and buque.tipo='M' and c.tipo = 'M'
		 */
		String sql = "SELECT act.* FROM (ACTIVIDAD_MARITIMA  act INNER JOIN BUQUE buque ON act.idBuque = buque.idBuque) INNER JOIN CARGA c ON act.idCarga = c.idCarga WHERE 1=1";
		
		if(fechaIni != null && !fechaIni.equals("N"))
		{
			sql+= " and act.fecha_llegada = '"+fechaIni+"'";
		}
		if(fechaFin != null && !fechaFin.equals("N"))
		{
			sql+= " and act.fecha_salida = '"+fechaFin+"'";
		}
		if(nombreBuque != null && !nombreBuque.equals("N"))
		{
			sql+= " and buque.nombre = '"+nombreBuque+"'";
		}
		
		if(tipoCarga != null && !tipoCarga.equals("N"))
		{
			sql+= " and c.tipo = '"+tipoCarga+"'";
		}
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		int j = 1;
		ArrayList<ActividadMaritima> lista = new ArrayList<ActividadMaritima>();
		String consulta = "{\"actividades\":[";
		while (rsF.next()) 
		{
			String  id = rsF.getString("idActMar");
			String  fechaLlegada = rsF.getString("fecha_Llegada");
			String  fechaSalida = rsF.getString("fecha_Salida");
			String  esDescarga = rsF.getString("es_Descarga");
			String  arriboBuque= rsF.getString("arribo_Buque");
			String  salidaBuque = rsF.getString("salida_Buque");
			String  cargaConf = rsF.getString("carga_Confirmada");
			String  idBuque = rsF.getString("idBuque");
			String  idCarga = rsF.getString("idCarga");
			String  idDueño = rsF.getString("idDueño");
			String  idMuelle = rsF.getString("idMuelle");
			String  idCosto = rsF.getString("idCosto");
				
			lista.add(new ActividadMaritima
					(Integer.parseInt(id), fechaLlegada, fechaSalida, 
					Boolean.valueOf(esDescarga), Boolean.valueOf(arriboBuque) ,
					Boolean.valueOf(salidaBuque), Boolean.valueOf(cargaConf),
					new Buque(Integer.parseInt(idBuque),"","","",0,'a',false,false,false,false,'b',null,null),
					new Carga(),
					new Dueño(Integer.parseInt(idDueño),""),
					new Muelle(Integer.parseInt(idMuelle)),
					-1,
					new CostoFacturado(Integer.parseInt(idCosto),0, "") ));
			consulta +="{\"id\":"+id+",\"fechaLlegada\":\""+fechaLlegada+"\",\"fechaSalida\":\""+fechaSalida+"\", "+
					"\"esDescarga\":\""+esDescarga+"\","
			     +  "\"arriboBuque\":\""+arriboBuque+"\","
			     +  "\"salidaBuque\":\""+salidaBuque+"\","
			     +  "\"carga confirmada\":\""+cargaConf+"\","
			     +  "\"idCarga\":\""+idCarga+"\","
			     +  "\"idDueño\":\""+idDueño+"\","
			     +  "\"idMuelle\":\""+idMuelle+"\","
			     +  "\"idCosto\":\""+idCosto+"\"}";
			
			if(j ==1){consulta += ",";}
			j=j+ 1;
			
			
			
		}
		consulta += "]}";
		System.out.println(consulta);
		
		
		return consulta;
	}
	
	
	public int darCantidadActividadesExportador(int idExport) throws Exception
	{
		String sql = "SELECT COUNT(*) as CUANTOS FROM ACTIVIDAD_MARITIMA act WHERE act.idDueño ='" + idExport + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		int resp =0;
		if (rs.next())
		{
			resp = Integer.parseInt(rs.getString("CUANTOS")); 
		}

		return resp;

	}
	
	
	
	
	
	public String darCargasDelExportador(String idExpor, boolean porIdCarga) throws Exception
	{
		String sql = "SELECT act.idCarga as CARGA FROM ACTIVIDAD_MARITIMA act WHERE act.idDueño ='" + idExpor + "'";
		if(porIdCarga)
		{
			sql += "ORDER BY act.idCarga";
		}
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		String resp ="";
		if (rs.next())
		{
			resp += rs.getString("CARGA"); 
		}

		return resp;

	}
	/**
	 * MÃ©todo que busca el/los videos con el nombre que entra como parÃ¡metro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	/**public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
		ArrayList<Video> videos = new ArrayList<Video>();

		String sql = "SELECT * FROM VIDEOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
			videos.add(new Video(id, name2, duration));
		}

		return videos;
	}*/
	
	
	
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO ACTIVIDAD_MARITIMA VALUES (1, '01-02-2015','02-02-2015', 'T', 'T','F','F', 1, 1, 2, 1, 8, 5)";
		
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

	public void terminarProceso(String idActividad, String idAlmac)throws Exception
	{
		String sql = "UPDATE ACTIVIDAD_MARITIMA SET proceso_cd_terminado = 'T', idAlmacenamiento = '"+idAlmac+"'  WHERE idActMar = "+idActividad+";";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
	}

	public String darIdActividadParaCarga(String idCarga) throws Exception
	{
		String sql = "SELECT idActMar FROM ACTIVIDAD_MARITIMA WHERE idCarga = "+idCarga+
				" AND es_descarga = 'F';";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		String idActividad = "";
		while(rsF.next())
		{
			idActividad = rsF.getString("idActMar");
		}
		return (idActividad);
	}
	public String darIdActividadParaDescarga(String idCarga) throws Exception
	{
		String sql = "SELECT idActMar FROM ACTIVIDAD_MARITIMA WHERE idCarga = "+idCarga+
				" AND es_descarga = 'T';";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		
		String idActividad = "";
		while(rsF.next())
		{
			idActividad = rsF.getString("idActMar");
		}
		return (idActividad);
	}
	//
	//
	//
	
	
	public ArrayList<ActividadMaritima> consultarSalidasIt4(String fechaIni,String fechaFin, String nombreBuque, String tipoBuque, String tipoCarga, String orderby) throws Exception
	{
		/** con datos:
		 * with buqueP as
			 (Select buque.* from BUQUE buque where buque.nombre = 'buque65' and buque.tipo = 'R' ),
			 actP as
			 (SELECT act.* from ACTIVIDAD_MARITIMA  act  where act.fecha_llegada BETWEEN '01/06/16' and  '02/06/16' OR act.fecha_salida between '01/06/16' and  '02/06/16')
			SELECT actP.* FROM Carga c  INNER JOIN 
			(actP INNER JOIN buqueP ON actP.idBuque = buqueP.idBuque)
			ON actP.idCarga = c.idCarga Where c.tipo= 'R'
		 */
		String sql = 
				"with buqueP as "+
				"(Select buque.* from BUQUE buque where buque.nombre = '"+nombreBuque+"' and buque.tipo = '"+tipoBuque+"'"+
				"),actP as"+
				"(SELECT act.* from ACTIVIDAD_MARITIMA  act  where act.fecha_llegada BETWEEN '"+fechaIni+"' and '"+fechaFin+"' OR act.fecha_salida BETWEEN '"+fechaIni+"' and '"+fechaFin+"')"+
				"SELECT actP.*,c.idDueño FROM Carga c "+
				"INNER JOIN (actP INNER JOIN buqueP ON actP.idBuque = buqueP.idBuque)ON actP.idCarga = c.idCarga Where c.tipo= '"+tipoCarga+"'" + "ORDER BY "+ orderby;
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		long tiempoI =System.currentTimeMillis();
		ResultSet rsF = prepStmtF.executeQuery();

		System.out.println("Tiempo en consulta salidas normal: "+(System.currentTimeMillis() - tiempoI));
		//int j = 1;
		ArrayList<ActividadMaritima> lista = new ArrayList<ActividadMaritima>();
		//String consulta = "{\"actividades\":[";
		while (rsF.next()) 
		{
			String  id = rsF.getString("idActMar");
			String  fechaLlegada = rsF.getString("fecha_Llegada");
			String  fechaSalida = rsF.getString("fecha_Salida");
			String  esDescarga = rsF.getString("es_Descarga");
			String  arriboBuque= rsF.getString("arribo_Buque");
			String  salidaBuque = rsF.getString("salida_Buque");
			String  cargaConf = rsF.getString("carga_Confirmada");
			String  idBuque = rsF.getString("idBuque");
			String  idCarga = rsF.getString("idCarga");
			String  idDueño = rsF.getString("idDueño");
			String  idMuelle = rsF.getString("idMuelle");
			String  idCosto = rsF.getString("idCosto");
				
			lista.add(new ActividadMaritima
					(Integer.parseInt(id), fechaLlegada, fechaSalida, 
					Boolean.valueOf(esDescarga), Boolean.valueOf(arriboBuque) ,
					Boolean.valueOf(salidaBuque), Boolean.valueOf(cargaConf),
					new Buque(Integer.parseInt(idBuque),"","","",0,'a',false,false,false,false,'b',null,null),
					new Carga(Integer.parseInt(idCarga),0,0,null,tipoCarga,null,-1,-1,Integer.parseInt(idDueño)),
					new Dueño(Integer.parseInt(idDueño),""),
					new Muelle(Integer.parseInt(idMuelle)),
					-1,
					new CostoFacturado(idCosto != null?Integer.parseInt(idCosto):0,0, "") ));
			/**consulta +="{\"id\":"+id+",\"fechaLlegada\":\""+fechaLlegada+"\",\"fechaSalida\":\""+fechaSalida+"\", "+
					"\"esDescarga\":\""+esDescarga+"\","
			     +  "\"arriboBuque\":\""+arriboBuque+"\","
			     +  "\"salidaBuque\":\""+salidaBuque+"\","
			     +  "\"carga confirmada\":\""+cargaConf+"\","
			     +  "\"idCarga\":\""+idCarga+"\","
			     +  "\"idDueño\":\""+idDueño+"\","
			     +  "\"idMuelle\":\""+idMuelle+"\","
			     +  "\"idCosto\":\""+idCosto+"\"}";
			
			if(j ==1){consulta += ",";}
			j=j+ 1;
			
			*/
			
		}
		//consulta += "]}";
		//System.out.println(consulta);
		
		
		return lista;
	}
	public String consultarSalidasNotInIt4(String fechaIni,String fechaFin, String nombreBuque, String tipoBuque, String tipoCarga, String orden) throws Exception
	{
		/** con datos:
		 *      
		 *      
			 with buqueP as
			 (Select buque.* from BUQUE buque where buque.nombre != 'buque65' and buque.tipo != 'R' ),
			 actP as
			 (SELECT act.* from ACTIVIDAD_MARITIMA  act  where act.fecha_llegada BETWEEN '01/06/16' and  '02/06/16' OR act.fecha_salida between '01/06/16' and  '02/06/16')
			SELECT actP.* FROM Carga c  INNER JOIN 
			(actP INNER JOIN buqueP ON actP.idBuque = buqueP.idBuque)
			ON actP.idCarga = c.idCarga Where c.tipo != 'R' order by idActMar     
		 *      
		 *      
		 *      
		 */
		String sql = 
				"with buqueP as "+
				"(Select buque.* from BUQUE buque where buque.nombre != '"+nombreBuque+"' and buque.tipo != '"+tipoBuque+"'"+
				"),actP as"+
				"(SELECT act.* from ACTIVIDAD_MARITIMA  act  where act.fecha_llegada BETWEEN '"+fechaIni+"' and '"+fechaFin+"' OR act.fecha_salida BETWEEN '"+fechaIni+"' and '"+fechaFin+"')"+
				"SELECT actP.*,c.idDueño FROM Carga c "+
				"INNER JOIN (actP INNER JOIN buqueP ON actP.idBuque = buqueP.idBuque)ON actP.idCarga = c.idCarga Where c.tipo != '"+tipoCarga+"' order by " + orden;
		
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		long tiempoI =System.currentTimeMillis();
		ResultSet rsF = prepStmtF.executeQuery();

		System.out.println("Tiempo en consulta salidas sin algunos: "+(System.currentTimeMillis() - tiempoI));
		//int j = 1;
//		ArrayList<ActividadMaritima> lista = new ArrayList<ActividadMaritima>();
		//String consulta = "{\"actividades\":[";
		int i=0;
		String hastaAqui = "[";
		while (rsF.next()) 
		{
			String  id = rsF.getString("idActMar");
			String  fechaLlegada = rsF.getString("fecha_Llegada");
			String  fechaSalida = rsF.getString("fecha_Salida");
			String  esDescarga = rsF.getString("es_Descarga");
			String  arriboBuque= rsF.getString("arribo_Buque");
			String  salidaBuque = rsF.getString("salida_Buque");
			String  cargaConf = rsF.getString("carga_Confirmada");
			String  idBuque = rsF.getString("idBuque");
			String  idCarga = rsF.getString("idCarga");
			String  idDueño = rsF.getString("idDueño");
			String  idMuelle = rsF.getString("idMuelle");
			String  idCosto = rsF.getString("idCosto");
				
			ActividadMaritima actMN =(new ActividadMaritima
					(Integer.parseInt(id), fechaLlegada, fechaSalida, 
					Boolean.valueOf(esDescarga), Boolean.valueOf(arriboBuque) ,
					Boolean.valueOf(salidaBuque), Boolean.valueOf(cargaConf),
					new Buque(Integer.parseInt(idBuque),"","","",0,'a',false,false,false,false,'b',null,null),
					new Carga(Integer.parseInt(idCarga),0,0,null,tipoCarga,null,-1,-1,Integer.parseInt(idDueño)),
					new Dueño(Integer.parseInt(idDueño),""),
					new Muelle(Integer.parseInt(idMuelle)),
					-1,
					new CostoFacturado(idCosto != null?Integer.parseInt(idCosto):0,0, "") ));
			
			  	
			if(i != 0)
			{
				hastaAqui += ", "+ new Gson().toJson(actMN);
			}
			else
			{
				hastaAqui += new Gson().toJson(actMN); i = i+1;
			}
				
			
			/**consulta +="{\"id\":"+id+",\"fechaLlegada\":\""+fechaLlegada+"\",\"fechaSalida\":\""+fechaSalida+"\", "+
					"\"esDescarga\":\""+esDescarga+"\","
			     +  "\"arriboBuque\":\""+arriboBuque+"\","
			     +  "\"salidaBuque\":\""+salidaBuque+"\","
			     +  "\"carga confirmada\":\""+cargaConf+"\","
			     +  "\"idCarga\":\""+idCarga+"\","
			     +  "\"idDueño\":\""+idDueño+"\","
			     +  "\"idMuelle\":\""+idMuelle+"\","
			     +  "\"idCosto\":\""+idCosto+"\"}";
			
			if(j ==1){consulta += ",";}
			j=j+ 1;
			
			*/
			
		}
		//consulta += "]}";
		//System.out.println(consulta);
		
		hastaAqui += "]";
		return hastaAqui;
	}
	
	public ArrayList<DatosMovimientoCarga> consultarMovimientosCarga(int idExportador, 
			double valor, String pTipo) throws Exception
	{
		
		String sql = 
				" SELECT carga.IDCARGA, carga.ORIGEN, carga.DESTINO, "
				+ "carga.TIPO, am.FECHA_SALIDA, am.FECHA_ORDEN, costo.COSTO "
				+ "FROM COSTO_FACTURADO costo INNER JOIN ACTIVIDAD_MARITIMA am "
				+ "ON costo.IDCOSTOFACTURADO = am.IDCOSTO INNER JOIN CARGA carga "
				+ "ON am.IDCARGA = carga.IDCARGA "
				+ "WHERE idDueño = "+ idExportador +" AND costo > "+valor+" "
				+ "AND tipo = '"+pTipo+"'";
				
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList<DatosMovimientoCarga> lista = new ArrayList<>();
		while (rsF.next()) 
		{
			int  idCarga = rsF.getInt("idCarga");
			String  origen = rsF.getString("origen");
			String  destino = rsF.getString("destino");
			String  tipo = rsF.getString("tipo");
			String  fechaSalida= rsF.getString("fecha_Salida");
			String  fechaOrden = rsF.getString("fecha_Orden");
			float  costo = rsF.getFloat("costo");
				
			DatosMovimientoCarga x = new DatosMovimientoCarga(idCarga, origen, destino, tipo, fechaSalida, fechaOrden, costo);
			lista.add(x);
			
		}		
		return lista;
	}
}
