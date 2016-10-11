package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ActividadMaritima;
import vos.Buque;
import vos.Carga;
import vos.CostoFacturado;
import vos.Dueño;
import vos.Muelle;


/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaciÃ³n
 * @author Jg.tamura10
 */
public class DAOBuque
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
	public DAOBuque() 
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
	
	
	
	public void insertarDosFilasIguales() throws Exception
	{
		String sqlAL = "INSERT INTO BUQUE VALUES (1, 'La Pinta', 'Altamar', 'A300',500,1,5)";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Países Bajos','Rotterdam');	

				//;
				
				//INSERT INTO CAMION VALUES (1, 18.5, 'Colcamiones');
				
				//;

				//INSERT INTO DUEÑO VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel sólido', 1,1,3,1);
				
				//INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016');
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Grúa pórtico', 100, 1)
	}
	
	public float darCapacidadActual(String idBuque) throws Exception
	{
		String sql = "SELECT tipo, peso FROM BUQUE WHERE idbuque = "+idBuque+"";
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  peso = null;
		String tipo = null;
		while (rsF.next()) 
		{
		     peso = rsF.getString("peso");
			 tipo = rsF.getString("tipo");
			
	    }
		String capacidad = null;
		if(peso != null && tipo != null)
		{
			if(tipo.equals("R"))
			{
				String sql2 = "SELECT capacidadEnToneladas FROM BUQUE_RORO WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getString("capacidadEnToneladas");
				}
			}
			else if(tipo.equals("P"))
			{
				String sql2 = "SELECT capacidadEnTEU FROM BUQUE_PORTACONTENEDORES WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getString("capacidadEnTEU");
				}
			}
			else if(tipo.equals("M"))
			{
				String sql2 = "SELECT capacidadEnToneladas FROM BUQUE_MULTIPROPOSITO WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getString("capacidadEnToneladas");
				}
			}
		}
		if(capacidad != null && peso != null)
		{
			return 10000 - Float.parseFloat(peso);
		}
		
		return 10000;
	}

	public void setEnProcesoDeCarga(String idBuque) throws Exception
	{
		String sql2 = "UPDATE BUQUE SET proceso_de_carga = 'T' WHERE idbuque = "+idBuque+"";
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
	}
	public void setEnProcesoDeDescarga(String idBuque) throws Exception
	{
		String sql2 = "UPDATE BUQUE SET proceso_de_descarga = 'T' WHERE idbuque = "+idBuque+"";
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
	}

	public void setProcesoDeCargaFinalizado(String idBuque)throws Exception
	{
		String sql2 = "UPDATE BUQUE SET carga_completada = 'T', proceso_de_carga = 'F' WHERE idbuque = "+idBuque+"";
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
	}
	public void setProcesoDeDescargaFinalizada(String idBuque)throws Exception
	{
		String sql2 = "UPDATE BUQUE SET descarga_completada = 'T', proceso_de_descarga = 'F' WHERE idbuque = "+idBuque+"";
		System.out.println("SQL verificacion stmt:" + sql2);

		PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
		recursos.add(prepStmtF2);
		ResultSet rsF2 = prepStmtF2.executeQuery();
	}
	public void modificarPesoBuquePonerCarga(String idBuque, float pesoCarga) throws Exception
	{
		String sql = "UPDATE BUQUE set peso = peso +"+pesoCarga+" WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	
	public void modificarPesoBuqueQuitarCarga(String idBuque, float pesoCarga) throws Exception
	{
		String sql = "UPDATE BUQUE set peso = peso -"+pesoCarga+" WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	
	public float darCapacidadTotal (String idBuque) throws SQLException
    {
        String sql = "SELECT tipo FROM BUQUE WHERE idbuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String tipo = "";
		while (rsF.next()) 
		{
			 tipo = rsF.getString("tipo");
	    }
		float capacidad = 0;
		if( tipo != "")
		{
			if(tipo.equals("R"))
			{
				String sql2 = "SELECT capacidadEnToneladas FROM BUQUE_RORO WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getFloat("capacidadEnToneladas");
				}
			}
			else if(tipo.equals("P"))
			{
				String sql2 = "SELECT capacidadEnTEU FROM BUQUE_PORTACONTENEDORES WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getFloat("capacidadEnTEU");
				}
			}
			else if(tipo.equals("M"))
			{
				String sql2 = "SELECT capacidadEnToneladas FROM BUQUE_MULTIPROPOSITO WHERE idbuque = "+idBuque+"";
				System.out.println("SQL verificacion stmt:" + sql2);

				PreparedStatement prepStmtF2 = conn.prepareStatement(sql2);
				recursos.add(prepStmtF2);
				ResultSet rsF2 = prepStmtF2.executeQuery();
				while (rsF2.next()) 
				{
				     capacidad = rsF2.getFloat("capacidadEnToneladas");
				}
			}
		}
		
		
		return capacidad;
    }

    public ArrayList<String> buscarBuquePosible (String destino, String tipo) throws SQLException
    {
        String sql = "SELECT idBuque FROM BUQUE WHERE ciudad_destino = '"+destino+"' AND "
                        +"tipo = '"+tipo+"' for update";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList <String> lista = new ArrayList <String>();
		while (rsF.next()) 
		{
		     lista.add(rsF.getString("idBuque"));
	    }
		
		return lista;
    }
    
    public String darDeshabilitado (String idBuque) throws SQLException
    {
        String sql = "SELECT deshabilitado FROM BUQUE WHERE idBuque = "+idBuque+"";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  deshabilitado = "" ;
		while (rsF.next()) 
		{
		     deshabilitado = rsF.getString("deshabilitado");
	    }
		
		return deshabilitado;
    }
    
    public String darDestino (String idBuque) throws SQLException
    {
        String sql = "SELECT ciudad_destino FROM BUQUE WHERE idBuque = "+idBuque+"";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  destino = "" ;
		while (rsF.next()) 
		{
		     destino = rsF.getString("ciudad_destino");
	    }
		
		return destino;
    }
    
    public String darTipo (String idBuque) throws SQLException
    {
        String sql = "SELECT tipo FROM BUQUE WHERE idBuque = "+idBuque+"";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		String  tipo = "" ;
		while (rsF.next()) 
		{
		     tipo = rsF.getString("tipo");
	    }
		
		return tipo;
    }
    
    public float darPeso (String idBuque) throws SQLException
    {
        String sql = "SELECT peso FROM BUQUE WHERE idBuque = "+idBuque+"";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		float  peso = 0 ;
		while (rsF.next()) 
		{
		     peso = rsF.getFloat("peso");
	    }
		
		return peso;
    }

	public ArrayList<String> darBuquesInconclusos()throws Exception
	{
		String sql = "SELECT idBuque FROM BUQUE WHERE proceso_de_Carga = 'T'";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList<String>  buques = new ArrayList<String>() ;
		while (rsF.next()) 
		{
		     buques.add(rsF.getInt("idBuque") + "");
	    }
		
		return buques;
	}
	public ArrayList<String> darBuquesInconclusosDescarga()throws Exception
	{
		String sql = "SELECT idBuque FROM BUQUE WHERE proceso_de_Descarga = 'T'";
        System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
		ArrayList<String>  buques = new ArrayList<String>() ;
		while (rsF.next()) 
		{
		     buques.add(rsF.getInt("idBuque") + "");
	    }
		
		return buques;
	}
	public void deshabilitarBuqueMantenimiento(String idBuque) throws Exception
	{
		String sql = "UPDATE BUQUE SET deshabilitado = 'M' WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	
	public void deshabilitarBuqueAverio(String idBuque) throws Exception
	{
		String sql = "UPDATE BUQUE SET deshabilitado = 'A' WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	
	public void deshabilitarBuqueLegales(String idBuque) throws Exception
	{
		String sql = "UPDATE BUQUE SET deshabilitado = 'L' WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}
	
	public void habilitarBuque(String idBuque) throws Exception
	{
		String sql = "UPDATE BUQUE SET deshabilitado = 'N' WHERE idBuque = "+idBuque;
		System.out.println("SQL verificacion stmt:" + sql);

		PreparedStatement prepStmtF = conn.prepareStatement(sql);
		recursos.add(prepStmtF);
		ResultSet rsF = prepStmtF.executeQuery();
	}

}
