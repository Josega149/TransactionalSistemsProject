package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vos.AreaDeAlmacenamiento;
import vos.DatosMovimientoCarga;
import vos.MovimientoAreaAlmacenamiento;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci√≥n
 * @author Jg.tamura10
 */
public class DAOAreaDeAlmacenamiento
{
	@SuppressWarnings("unused")
	private static int CEO = 201423591;
	@SuppressWarnings("unused")
	private static int ID_GERENTE_GENERAL = 201424484;
	
	/**
	 * Arraylists de recursos que se usan para la ejecuciÛn de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi√≥n a la base de datos
	 */
	private Connection conn;

	/**
	 * M√©todo constructor que crea DAO
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOAreaDeAlmacenamiento() 
	{
		recursos = new ArrayList<Object>();
	}

	/**
	 * MÈtodo que cierra todos los recursos que estan en el arreglo de recursos
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
	 * MÈtodo que inicializa la connection del DAO a la base de datos con la conexi√≥n que entra como par√°metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}	
	
	public ArrayList<AreaDeAlmacenamiento> consultaDeAreas (ArrayList<String> idsAreaConCarga)throws Exception
	{
		ArrayList<AreaDeAlmacenamiento> lista = new ArrayList<>();
		
		for(int i=0; i< idsAreaConCarga.size();i++)
		{
			String idArea = idsAreaConCarga.get(i).split(",")[0];
			@SuppressWarnings("unused")
			String idCarga = idsAreaConCarga.get(i).split(",")[1];
			
			String sqlAL = "SELECT * FROM AREA_ALMACENAMIENTO WHERE idAreaAlm = '"+idArea+"'";
			
			System.out.println("SQL verificacion stmt:" + sqlAL);
			PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
			recursos.add(prepStmtFAL);
			ResultSet rsFAL = prepStmtFAL.executeQuery();
			
			if(rsFAL.next())
			{
				AreaDeAlmacenamiento areaNueva = new AreaDeAlmacenamiento(
						rsFAL.getInt("idAreaAlm"),
						rsFAL.getString("tipo"),
						Boolean.parseBoolean(rsFAL.getString("habilitado")),
						rsFAL.getFloat("capacidad_total"),
						rsFAL.getFloat("peso_actual"));
				
				lista.add(areaNueva);
			}
		}
		
		
		
		
		return lista;
	}
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO AREA_ALMACENAMIENTO VALUES (10)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','PaÌses Bajos','Rotterdam');	

				//INSERT INTO BUQUE VALUES (1, 'La Pinta', 'Altamar', 'A300',500,1,5);
				
				//INSERT INTO CAMION VALUES (1, 18.5, 'Colcamiones');
				
				//;

				//INSERT INTO DUE—O VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel sÛlido', 1,1,3,1);
				
				//INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016');
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Gr˙a pÛrtico', 100, 1);
				

				
	}

	public void modificarPesoAreaQuitarCarga(String idAlm, float pesoCarga)throws Exception
	{
		String sql = "UPDATE AREA_ALMACENTAMIENTO SET peso_Actual = peso_Actual -"+pesoCarga+" WHERE idAreaAlm = "+idAlm+";";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	public void modificarPesoAreaPonerCarga(String idAlm, float pesoCarga)throws Exception
	{
		String sql = "UPDATE AREA_ALMACENTAMIENTO SET peso_Actual = peso_Actual +"+pesoCarga+" WHERE idAreaAlm = "+idAlm+";";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}

	public String conseguirALMParaCarga(float pesoCarga,String tipoBuque, String fecha) throws Exception
	{
		String areaSirve = "";
		if(tipoBuque.equals("R"))
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'patio'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		} 
		else if(tipoBuque.equals("P"))//porta contenedores
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'Bodega'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		} 
		else if(tipoBuque.equals("M"))//multiproposito
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'silo'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		}
		
		return areaSirve;
	}
	
	public String darTipo (String idArea) throws Exception
    {
        String sql = "SELECT tipo FROM AREA_ALMACENAMIENTO WHERE idAreaAlm = "+idArea+"";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  tipo = "" ;
		while (rsF.next()) 
		{
		     tipo = rsF.getString("tipo");
	    }
		System.out.println(tipo+"este es el tipo");
		return tipo;
    }
	
	public void deshabilitar(String idArea) throws Exception
	{
		String sql2 = "UPDATE AREA_ALMACENAMIENTO SET habilitado = 'F' WHERE idAreaAlm = "+idArea;
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
	}
	
	public String conseguirOtraAreaParaCarga(float pesoCarga,String tipoArea) throws Exception
	{
		String areaSirve = "";
		if(tipoArea.equals("Patio"))
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'Patio'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		} 
		else if(tipoArea.equals("Bodega"))//porta contenedores
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'Bodega'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		} 
		else if(tipoArea.equals("Silo"))//multiproposito
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'Silo'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		}
		else if(tipoArea.equals("Cobertizo"))//multiproposito
		{
			String sql = "SELECT idAreaAlm FROM AREA_ALMACENAMIENTO WHERE (capacidadTotal - peso_actual) >= "+ pesoCarga+
					" AND tipo = 'Cobertizo'  FOR UPDATE";
			System.out.println("SQL verificacion stmt:" + sql);

			PreparedStatement prepStmtF = conn.prepareStatement(sql);
			recursos.add(prepStmtF);
			ResultSet rsF = prepStmtF.executeQuery();
			
			areaSirve = rsF.getInt("idAreaAlm")+"";
		}
		return areaSirve;
	}
	
	public ArrayList<MovimientoAreaAlmacenamiento> consultarDosAreas(int idArea1, int idArea2) throws Exception
	{
		
		String sql = 
				" SELECT mov.ID_AREA, mov.ID_CARGA, mov.ENTRADA, "
				+ "mov.FECHA, area.TIPO, area.PESO_ACTUAL, "
				+ "area.CAPACIDAD_TOTAL, area.HABILITADO "
				+ "FROM MOVIMIENTO_CARGA_AREA mov "
				+ "INNER JOIN AREA_ALMACENAMIENTO area "
				+ "ON mov.ID_AREA = area.IDAREAALM "
				+ "WHERE mov.ID_AREA = "+idArea1 +" OR mov.ID_AREA = "+idArea2;
				
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList<MovimientoAreaAlmacenamiento> lista = new ArrayList<>();
		while (rsF.next()) 
		{
			int idArea = rsF.getInt("id_Area");
			int idCarga = rsF.getInt("id_Carga");
			String  entr = rsF.getString("entrada");
				boolean entrada = false;
				if (entr.equals("T"))
					entrada=true;
			String  fecha = rsF.getString("fecha");
			String  tipo = rsF.getString("tipo");
			float  pesoActual = rsF.getFloat("peso_Actual");
			float  capacidad = rsF.getFloat("capacidad_Total");
			String hab = rsF.getString("habilitado");
				boolean habilitado = false;
				if(hab.equals("T"))
					habilitado=true;
				
			MovimientoAreaAlmacenamiento x = new MovimientoAreaAlmacenamiento
					(idArea, idCarga, fecha, tipo, entrada, pesoActual, capacidad, habilitado);
			lista.add(x);
		}		
		return lista;
	}

	
}
