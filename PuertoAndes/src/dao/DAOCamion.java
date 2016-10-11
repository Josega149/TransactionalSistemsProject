package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class DAOCamion
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
	public DAOCamion() 
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
		String sqlAL = "INSERT INTO CAMION VALUES (1, 18.5, 'Colcamiones')";
		
		System.out.println("SQL verificacion stmt:" + sqlAL);

		PreparedStatement prepStmtFAL = conn.prepareStatement(sqlAL);
		recursos.add(prepStmtFAL);
		ResultSet rsFAL = prepStmtFAL.executeQuery();
		
       //repetida
		
				
		
				//INSERT INTO PUERTO VALUES(1, 'Puerto de Rotterdam','Países Bajos','Rotterdam');	


				//INSERT INTO DUEÑO VALUES (1, 'Elvis Tek');
			
				//INSERT INTO CARGA VALUES (1, 180, 1,'Granel sólido', 1,1,3,1);
				
				//INSERT INTO COSTO_FACTURADO VALUES (1, 10500, '01-02-2016');
			
				//INSERT INTO EQUIPO_APOYO VALUES (1, 'Grúa pórtico', 100, 1);
				

				
	}

}
