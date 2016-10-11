
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Queue;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import dao.DAOActividadEntregaMercancia;
import dao.DAOActividadMaritima;
import dao.DAOActividadTerrestre;
import dao.DAOAreaDeAlmacenamiento;
import dao.DAOBuque;
import dao.DAOCamion;
import dao.DAOCarga;
import dao.DAOCargaInconclusa;
import dao.DAOCosto;
import dao.DAODescargasInconclusas;
import dao.DAODueño;
import dao.DAOEquiposApoyo;
import dao.DAOPuerto;
import vos.Aceptado;
import vos.ActividadMaritima;
import vos.AreaDeAlmacenamiento;
import vos.Carga;
import vos.CargaInconclusa;
import vos.ConsultarMovCargas;
import vos.CostoFacturado;
import vos.DatosMovimientoCarga;
import vos.DescargaInconclusa;
import vos.FacturaExp;
import vos.ListaFacturaExp;
import vos.MovimientoAreaAlmacenamiento;


/**
 * Fachada en patron singleton de la aplicaciÃ³n
 * @author Juan
 */
public class PuertoAndesMaster {


	/**
	 * Atributo estÃ¡tico que contiene el path relativo del archivo que tiene los datos de la conexiÃ³n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estÃ¡tico que contiene el path absoluto del archivo que tiene los datos de la conexiÃ³n
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	private String user2;
	private String user3;
	private String password2;
	private String password3;
	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * ConexiÃ³n a la base de datos
	 */
	private Connection conn;

	/**
	 * Atributos it5
	 */

	@SuppressWarnings("unused")
	private DataSource ds;       
	@SuppressWarnings("unused")
	private DataSource ds2;      
	@SuppressWarnings("unused")
	private DataSource ds3;
	@SuppressWarnings({ "rawtypes", "unused" })
	private Queue colaDefinida1; 
	@SuppressWarnings({ "rawtypes", "unused" })
	private Queue colaDefinida2; 
	@SuppressWarnings({ "rawtypes", "unused" })
	private Queue colaDefinida3;

	private Connection connLaurita;private Connection connNeli;

	private InitialContext context;

	@SuppressWarnings("unused")
	private ConnectionFactory cf;

	@SuppressWarnings("unused")
	private javax.jms.Connection conm;


	/**
	 * MÃ©todo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexiÃ³n y se
	 * inicializa los atributos que se usan par la conexiÃ³n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP)
	{
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();


	}

	@SuppressWarnings("rawtypes")
	public void inicializarContexto ()
	{
		try 
		{
			Hashtable <String, String> env = new Hashtable<>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			env.put(Context.PROVIDER_URL, "jnp://localhost:8080");
			env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			context = new InitialContext(env);
			ds = (DataSource) context.lookup("java:XAChie1");
			ds2 = (DataSource) context.lookup("java:XAChie2");
			ds3 = (DataSource) context.lookup("java:XAChie3");
			cf = (ConnectionFactory) context.lookup("java:JmsXA");
			colaDefinida3 = (Queue) context.lookup("queue/WebApp3");
			colaDefinida1 = (Queue) context.lookup("queue/WebApp1");
			colaDefinida2 = (Queue) context.lookup("queue/WebApp2");


		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * MÃ©todo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexiÃ³n a la base de datos.
	 */
	private void initConnectionData() 
	{
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			this.user2 = prop.getProperty("usuario2");
			this.user3 = prop.getProperty("usuario3");
			this.password2 = prop.getProperty("contraseña2");
			this.password3 = prop.getProperty("contraseña3");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * MÃ©todo que  retorna la conexiÃ³n a la base de datos
	 * @return Connection - la conexiÃ³n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexiÃ³n a la base de datos
	 */
	private Connection darConexion() throws SQLException 
	{
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	private Connection darConexionLaurita() throws SQLException
	{
		System.out.println("Connecting to: " + url + " With user: " + user2);
		return DriverManager.getConnection(url, user2, password2);
	}

	private Connection darConexionNeli() throws SQLException
	{
		System.out.println("Connecting to: " + url + " With user: " + user3);
		return DriverManager.getConnection(url, user3, password3);
	}

	////////////////////////////////////////
	/////////////Transacciones//////////////
	////////////////////////////////////////


	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * REGISTRAR SALIDA BUQUE
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public String registrarSalidaMuelle(String idAgen, String idBu, String idMuelle, String fechaSalida) throws Exception 
	{
		DAOActividadMaritima actividadMaritima = new DAOActividadMaritima();
		String resp;
		try 
		{
			//////Transaccion
			this.conn = darConexion();
			actividadMaritima .setConn(conn);
			resp = actividadMaritima .registrarSalidaMuelle(idAgen,idBu,idMuelle, fechaSalida);
			actividadMaritima.cerrarRecursos();

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	//
	//
	//     2
	//
	//
	//

	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * REGISTRAR ENTREGA MERCANCÍA
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public String registrarEntregaMercancia(String idAgen,String idMercan,String idDueño,String fecha) throws Exception 
	{
		DAOActividadEntregaMercancia actividadEntregaMercancia= new DAOActividadEntregaMercancia();
		String resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			actividadEntregaMercancia .setConn(conn);
			resp = actividadEntregaMercancia .registrarEntregaMercancia(idAgen,idMercan,idDueño, fecha);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadEntregaMercancia .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public Aceptado registrarTipoDeCargaDeUnBuque(String idOperador,String idBuque,
			String idCarga,String fechaLlegada) throws Exception 
	{ 
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		Aceptado resp = null;
		try 
		{
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.registrarTipoDeCargaDeUnBuque(idOperador, idBuque,
					idCarga,  fechaLlegada);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;

	}
	public Aceptado registrarTipoDeCargaDeUnBuqueParaDescarga(String idOperador,String idBuque,
			String idCarga,String fechaLlegada) throws Exception 
	{ 
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		Aceptado resp = null;
		try 
		{
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.registrarTipoDeCargaDeUnBuqueParaDescarga(idOperador, idBuque,
					idCarga,  fechaLlegada);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;

	}



	/**
	 * r/9 
	 * 
	 * GENERAR FACTURA
	 */
	public String putGenerarFactura(String fecha, String idCargaS, String idAct) throws Exception 
	{
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		String resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			actividadMaritima.setConn(conn);

			DAOCosto daoC = new DAOCosto();
			DAOEquiposApoyo daoA = new DAOEquiposApoyo();

			daoC.setConn(conn);
			daoA.setConn(conn);

			int id = daoC.darProximoId();

			int idCarga = Integer.parseInt(idCargaS);
			int numDias = daoC.darNumDias(idCarga);
			int numEquipos = daoA.darNumEquipos(idAct);
			float peso = daoC.darPeso(idCarga);

			float costo = (numDias * 1000)+ numEquipos*500 + peso * 20;

			CostoFacturado costoT = new CostoFacturado(id, costo, fecha );

			resp = daoC.registrarCosto(costoT, idCarga);

			daoC.cerrarRecursos();
			daoA.cerrarRecursos();

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima.cerrarRecursos();

				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;

	}




	// consultas

	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR SALIDAS
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public String consultarSalidas(String fechaIni,String fechaFin, 
			String nombreBuque, String tipoBuque, String tipoCarga) throws Exception 
	{
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		String resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.consultarSalidas(fechaIni, fechaFin,nombreBuque,tipoBuque,tipoCarga);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR EXPORTADOR
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public String consultarExportador(String idExp, boolean ordenado) throws Exception 
	{
		DAODueño dueño= new DAODueño();
		String resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			dueño.setConn(conn);
			resp = dueño .consultarExportador(idExp, ordenado);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				dueño .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public String pruebaNoCumpleChequeo()
	{
		boolean d = false;
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.pruebaNoCumpleChequeo();
		}
		catch (Exception e)
		{
			d=true;
			actTerrestre.cerrarRecursos();
		}
		if (d)
			return "{\"prueba cumple chequeo \":\"prueba aceptado\"}";;
			return "{\"prueba cumple chequeo \":\"prueba rechazado\"}";
	}

	public String pruebaCumpleChequeo() throws Exception
	{
		String respo = "{\"prueba cumple chequeo \":\"rechazado\"}";
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.pruebaCumpleChequeo();
			respo="{\"prueba cumple chequeo \":\"prueba aceptado\"}";
		}
		catch (Exception e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		finally 
		{
			try 
			{
				actTerrestre.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respo;
	}



	public String borrarTuplaMaestraFK()
	{
		boolean d = false;
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.borrarTuplaMaestraFK();
		}
		catch (Exception e)
		{
			d=true;
			actTerrestre.cerrarRecursos();
		}
		if (d)
			return "{\"borrar tupla maestra fk\":\"Cambio no Realizado, prueba correcta\"}";
		return "{\"borrar tupla maestra fk\":\"Cambio Realizado\"}";
	}

	public String borrarTuplaDependienteFK() throws Exception
	{
		String respo = "{\"borrar tupla dependiente fk\":\"Cambio no Realizado\"}";
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.borrarTuplaDependienteFK();
			respo="{\"borrar tupla dependiente fk\":\"Cambio Realizado, prueba correcta\"}";
		}
		catch (Exception e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		finally 
		{
			try 
			{
				actTerrestre.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respo;
	}

	public String pruebasLlaveFKNoReferenciada()
	{
		boolean d = false;
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.pruebaDeFKNoReferenciada();
		}
		catch (Exception e)
		{
			d=true;
			actTerrestre.cerrarRecursos();
		}
		if (d)
			return "{\"agregar con una fk no referenciada\":\"Cambio no Realizado, prueba correcta\"}";
		return "{\"agregar con una fk no referenciada\":\"Cambio Realizado\"}";
	}

	public String pruebasLlaveFKReferenciada() throws Exception
	{
		String respo = "{\"agregar con una fk referenciada\":\"Cambio no Realizado\"}";
		DAOActividadTerrestre actTerrestre = new DAOActividadTerrestre();
		try
		{
			this.conn = darConexion();
			actTerrestre.setConn(conn);
			actTerrestre.pruebaDeFKReferenciada();
			respo="{\"agregar con una fk referenciada\":\"Cambio Realizado, prueba correcta\"}";
		}
		catch (Exception e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		finally 
		{
			try 
			{
				actTerrestre.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respo;
	}



	public String pruebasLlavePK()
	{
		// son 11 tablas
		int i =0;

		// prueba
		DAOActividadEntregaMercancia p1 = new DAOActividadEntregaMercancia();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p1.setConn(conn);
			p1.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p1.cerrarRecursos();
		}

		// prueba
		DAOActividadMaritima p2 = new DAOActividadMaritima();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p2.setConn(conn);
			p2.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p2.cerrarRecursos();
		}


		// prueba
		DAOActividadTerrestre p3 = new DAOActividadTerrestre();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p3.setConn(conn);
			p3.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p3.cerrarRecursos();
		}


		// prueba
		DAOAreaDeAlmacenamiento p4 = new DAOAreaDeAlmacenamiento();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p4.setConn(conn);
			p4.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p4.cerrarRecursos();
		}


		// prueba
		DAOBuque p5 = new DAOBuque();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p5.setConn(conn);
			p5.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p5.cerrarRecursos();
		}

		//
		// prueba
		DAOCamion p6 = new DAOCamion();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p6.setConn(conn);
			p6.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p6.cerrarRecursos();
		}


		// prueba
		DAOCosto p7 = new DAOCosto();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p7.setConn(conn);
			p7.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p7.cerrarRecursos();
		}


		// prueba
		DAODueño p8 = new DAODueño();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p8.setConn(conn);
			p8.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p8.cerrarRecursos();
		}

		// prueba
		DAOEquiposApoyo p9 = new DAOEquiposApoyo();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p9.setConn(conn);
			p9.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p9.cerrarRecursos();
		}
		// prueba
		DAOPuerto p10 = new DAOPuerto();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p10.setConn(conn);
			p10.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p10.cerrarRecursos();
		}
		// prueba
		DAOCarga p11 = new DAOCarga();
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			p11.setConn(conn);
			p11.insertarDosFilasIguales();
		} 
		catch (Exception e) 
		{
			i = i+1;
			p11.cerrarRecursos();
		}



		if(i == 11)
		{
			return "aceptado";
		}
		return "rechazado";




	}
	//
	//
	// iteracion 3
	//
	//
	//
	public Aceptado cargarBuque(String idAgen, String idBuque, String idCarga, String fecha, Connection conn2)throws Exception
	{
		this.conn = darConexion();
		// cuando se hace por carga inconclusa debe usar la conexion de dicho metodo para poder hacer rollback
		if(conn2 != null ){conn = conn2;}
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		// verifica que el buque tenga la capacidad para aceptar la carga 
		// se usa la conn serializable para garantizar que los cambios que se hagan 
		// correspondan a cuando se leyo el estado
		boolean tieneCapacidad = verificarCapacidad(idBuque , idCarga, conn);

		if(tieneCapacidad)
		{
			ejecutarTraslado(idBuque, idCarga, conn);


			return new Aceptado(" el buque fue cargado exitosamente");
		}

		throw new Exception ("no tiene capacidad el buque");



	}

	private void ejecutarTraslado(String idBuque, String idCarga, Connection conn) throws Exception
	{
		DAOCarga carga = new DAOCarga();
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOActividadMaritima actividadM = new DAOActividadMaritima();
		DAOBuque buque = new DAOBuque();
		try 
		{
			carga.setConn(conn);
			area.setConn(conn);
			actividadM.setConn(conn);
			buque.setConn(conn);

			String idAlm = carga.consultarIDArea(idCarga);
			if(idAlm.equals("")){throw new Exception("no tiene area de almacenamiento");}

			carga.trasladarAreaABuque(idCarga, idBuque);
			float pesoCarga = carga.darPesoCarga(idCarga);
			area.modificarPesoAreaQuitarCarga(idAlm , pesoCarga);
			buque.modificarPesoBuquePonerCarga(idBuque, pesoCarga);

			String idActMar = actividadM.darIdActividadParaCarga(idCarga);
			actividadM.terminarProceso(idActMar, idAlm);

			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			area.cerrarRecursos();
			buque.cerrarRecursos();
			actividadM.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				area.cerrarRecursos();
				actividadM.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public boolean verificarCapacidad(String idBuque, String idCarga,Connection conn) throws Exception
	{
		boolean resp = false;

		DAOBuque buque = new DAOBuque();
		DAOCarga carga = new DAOCarga();
		try 
		{
			//////TransacciÃ³
			buque.setConn(conn);
			float capacidad = buque.darCapacidadActual(idBuque);

			carga.setConn(conn);
			float peso = carga.darPesoCarga(idCarga);
			if(capacidad == -1 || peso == -1){throw new Exception("El buque no existe o la carga no existe");}

			if(peso > capacidad)
			{
				resp = false;
			}
			else
			{
				resp = true;
			}
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			buque.cerrarRecursos();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	public void buqueEnProcesoCarga(String idBuque) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			buque.setConn(conn);
			buque.setEnProcesoDeCarga(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueEnProcesoCarga2(String idBuque, Connection conn) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			buque.setConn(conn);
			buque.setEnProcesoDeCarga(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueCargaTerminada(String idBuque) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			buque.setConn(conn);
			buque.setProcesoDeCargaFinalizado(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueCargaTerminada2(String idBuque, Connection conn) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			buque.setConn(conn);
			buque.setProcesoDeCargaFinalizado(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void agregarCargasInconclusas(String idAgen, String idBuque, String [] cargas, String fecha) throws Exception
	{
		DAOCargaInconclusa inconclusa = new DAOCargaInconclusa();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			inconclusa.setConn(conn);

			for(int i=0; i< cargas.length; i++)
			{
				inconclusa.añadirCargaInconclusa(idAgen,idBuque,cargas[i],fecha);				
			}


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			throw new Exception("No se agrego ninguna carga, el error ocurrio haciendo "
					+ "la lista. por favor verificar que las cargas y buques existan y "
					+ "vuelva a intentar cargar el buque de nuevo con todas las cargas");
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}


	}

	public void eliminarCargaInconclusa(String idCarga, Connection conn2) throws Exception
	{
		DAOCargaInconclusa inconclusa = new DAOCargaInconclusa();
		try 
		{
			// debe usar la conn2 si el metodo esta terminando las cargas inconclusas
			if(conn2 != null ){conn = conn2;}
			else{
				this.conn = darConexion();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			inconclusa.setConn(conn);
			inconclusa.eliminarCargaInconclusa(idCarga);				
			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			throw new Exception("No se borro la carga inconclusa "+idCarga+ " pero si se concluyo ");
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public Aceptado terminarCargasInconclusas()throws Exception
	{
		DAOCargaInconclusa inconclusa = new DAOCargaInconclusa();
		CargaInconclusa cargaActual = new CargaInconclusa();

		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			inconclusa.setConn(conn);

			ArrayList<CargaInconclusa> inconclusas = inconclusa.darCargasInconclusas();	
			for(int i=0; i< inconclusas.size(); i++)
			{
				CargaInconclusa cargaIn = (CargaInconclusa) inconclusas.get(i);

				cargarBuque(cargaIn.darAgen(),cargaIn.darBuque(),  cargaIn.darCarga(), cargaIn.darFecha(), conn);

				eliminarCargaInconclusa(cargaActual.darCarga(), conn);
				conn.commit();
			}

			verificarBuquesInconclusos();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			// si no cumple por capacidad debe ser eliminado. la carga continua en bodega
			if(e.getMessage().startsWith("no tiene")){eliminarCargaInconclusa(cargaActual.darCarga(), conn);}
			throw new Exception("No se termino completo el proceso de cargas inconclusas,"
					+ " volver a intentar terminar cargas inconclusas. "+ 
					e.getMessage()+ "Es neceario volver a pedir cargar dicha carga "
					+ "pues fue eliminada de la cola de cargas incompletas");
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new Aceptado("todas las cargas inconclusas fueron terminadas correctamente");
	}
	public void verificarBuquesInconclusos()throws Exception
	{
		DAOBuque buque = new DAOBuque();
		DAOCargaInconclusa cargasIncompletas = new DAOCargaInconclusa();
		try 
		{
			ArrayList<String> buquesInconclusos = new ArrayList<String>(); // 

			buquesInconclusos = buque.darBuquesInconclusos();

			for(int i=0; i< buquesInconclusos.size();i++)
			{
				boolean buqueConCargaIncompleta = cargasIncompletas.existeCargaIncompParaBuque(buquesInconclusos.get(i));
				if(!buqueConCargaIncompleta)
				{
					buqueCargaTerminada(buquesInconclusos.get(i));
				}
			}
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			cargasIncompletas.cerrarRecursos();
			// si no cumple por capacidad debe ser eliminado. la carga continua en bodega
			throw new Exception("no se verificaron los buques inconclusos");
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				cargasIncompletas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//-------------------------------------------------------------------------------
	// req 11
	//---------------------------------------------------------------------------
	public Aceptado descargarBuque(String idAgen, String idArea, String idCarga, String fecha, Connection conn2)throws Exception
	{
		this.conn = darConexion();
		// cuando se hace por carga inconclusa debe usar la conexion de dicho metodo para poder hacer rollback
		if(conn2 != null ){conn = conn2;}
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);


		// siempre tiene capacidad pues ya se verifico al momento de asignar el area de alm
		ejecutarTrasladoDescarga(idArea, idCarga, conn);


		return new Aceptado(" el buque fue cargado exitosamente");
	}

	private void ejecutarTrasladoDescarga(String idArea, String idCarga, Connection conn) throws Exception
	{
		DAOCarga carga = new DAOCarga();
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOActividadMaritima actividadM = new DAOActividadMaritima();
		DAOBuque buque = new DAOBuque();
		try 
		{
			carga.setConn(conn);
			area.setConn(conn);
			actividadM.setConn(conn);
			buque.setConn(conn);

			String idBuque = carga.consultarIDBuque(idCarga);
			if(idBuque.equals("")){throw new Exception("no tiene buque");}

			carga.trasladarBuqueAArea(idCarga, idArea);
			float pesoCarga = carga.darPesoCarga(idCarga);
			area.modificarPesoAreaPonerCarga(idArea , pesoCarga);
			buque.modificarPesoBuqueQuitarCarga(idBuque, pesoCarga);

			String idActMar = actividadM.darIdActividadParaDescarga(idCarga);
			// terminar proceso pone la variable terminar proceso y 
			// asiga en el actividad maritima el idAlmacenamiento
			actividadM.terminarProceso(idActMar, idArea);

			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			area.cerrarRecursos();
			actividadM.cerrarRecursos();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				area.cerrarRecursos();
				actividadM.cerrarRecursos();
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void eliminarDescargaInconclusa(String idCarga, Connection conn2) throws Exception
	{
		DAODescargasInconclusas inconclusa = new DAODescargasInconclusas();
		try 
		{
			// debe usar la conn2 si el metodo esta terminando las cargas inconclusas
			if(conn2 != null ){conn = conn2;}
			else{
				this.conn = darConexion();
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			inconclusa.setConn(conn);
			inconclusa.eliminarDescargaInconclusa(idCarga);				
			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			throw new Exception("No se borro la carga inconclusa "+idCarga);
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	public void agregarDescargasInconclusas(String idAgen, String idBuque, String [] cargas, String fecha) throws Exception
	{
		DAODescargasInconclusas inconclusa = new DAODescargasInconclusas();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			inconclusa.setConn(conn);

			for(int i=0; i< cargas.length; i++)
			{
				inconclusa.añadirDescargaInconclusa(idAgen, idBuque, cargas[i],fecha);				
			}


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			throw new Exception("No se agrego ninguna descarga, el error ocurrio "
					+ "haciendo la lista. por favor verificar que las cargas y buques "
					+ "existan y vuelva a intentar descargar el buque de nuevo con "
					+ "todas las cargas");
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}


	}
	public void buqueDescargaTerminada(String idBuque) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			buque.setConn(conn);
			buque.setProcesoDeDescargaFinalizada(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueDescargaTerminada2(String idBuque, Connection conn) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			buque.setConn(conn);
			buque.setProcesoDeDescargaFinalizada(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueEnProcesoDescarga(String idBuque) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			buque.setConn(conn);
			buque.setEnProcesoDeDescarga(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void buqueEnProcesoDescarga2(String idBuque, Connection conn) throws Exception
	{
		DAOBuque buque = new DAOBuque();
		try 
		{
			buque.setConn(conn);
			buque.setEnProcesoDeDescarga(idBuque);


			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public String conseguirAreaAlmPara(String idCargasS, String fecha, Connection conn) throws Exception 
	{
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOCarga carga = new DAOCarga();

		String areaId = "";

		try 
		{
			conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			area.setConn(conn);
			carga.setConn(conn);


			float pesoCarga = carga.darPesoCarga(idCargasS);
			String tipoCarga = carga.darTipoCarga(idCargasS);
			areaId = area.conseguirALMParaCarga(pesoCarga,tipoCarga, fecha);
			// lo selecciona for update para que nadie mas lo pueda usar.

			// no se hace commit porque no se ha efectuado ningun cambio de traspaso
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			area.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				area.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		if(areaId.equals("")){throw new Exception("no hay ninguna bodega En este momento para la carga "+ areaId);}
		return areaId;
	}
	public Aceptado terminarDescargasInconclusas()throws Exception
	{
		DAODescargasInconclusas inconclusa = new DAODescargasInconclusas();
		DescargaInconclusa cargaActual = null;

		try 
		{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			inconclusa.setConn(conn);

			ArrayList<DescargaInconclusa> inconclusas = inconclusa.darDescargasInconclusas();	
			for(int i=0; i< inconclusas.size(); i++)
			{
				DescargaInconclusa cargaIn =  inconclusas.get(i);
				cargaActual = cargaIn;

				String area = conseguirAreaAlmPara(cargaIn.darCarga(), cargaIn.darFecha(), conn);
				descargarBuque( cargaIn.darAgen(), cargaIn.darCarga(), area, cargaIn.darFecha(), conn);

				eliminarCargaInconclusa(cargaIn.darCarga(), conn);
				conn.commit();
			}

			verificarBuquesInconclusosDescarga();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			inconclusa.cerrarRecursos();
			// si no cumple por capacidad debe ser eliminado. la carga continua en bodega
			if(e.getMessage().startsWith("no tiene")){eliminarCargaInconclusa(cargaActual.darCarga(), conn);}
			throw new Exception("No se termino completo el proceso de cargas inconclusas, "
					+ "volver a intentar terminar cargas inconclusas. "+ e.getMessage()+ "Es neceario volver"
					+ " a pedir cargar dicha carga pues fue eliminada de la cola de cargas incompletas");
		}
		finally 
		{
			try 
			{
				inconclusa.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new Aceptado("todas las cargas inconclusas fueron terminadas correctamente");
	}
	public void verificarBuquesInconclusosDescarga()throws Exception
	{
		DAOBuque buque = new DAOBuque();
		DAODescargasInconclusas descargasIncompletas = new DAODescargasInconclusas();
		try 
		{
			ArrayList<String> buquesInconclusos = new ArrayList<String>(); // 

			buquesInconclusos = buque.darBuquesInconclusosDescarga();

			for(int i=0; i< buquesInconclusos.size();i++)
			{
				boolean buqueConDescargaIncompleta = descargasIncompletas.existeDescargaIncompParaBuque(buquesInconclusos.get(i));
				if(!buqueConDescargaIncompleta)
				{
					buqueDescargaTerminada(buquesInconclusos.get(i));
				}
			}
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo update un valor
			conn.rollback();
			buque.cerrarRecursos();
			descargasIncompletas.cerrarRecursos();
			// si no cumple por capacidad debe ser eliminado. la carga continua en bodega
			throw new Exception("no se verificaron los buques inconclusos");
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				descargasIncompletas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	//-----------------------------------------------------------------
	//req 12
	//-----------------------------------------------------------------

	public Aceptado deshabilitarBuque (String idAgente, String idBuque, String razon) throws Exception
	{
		this.conn = darConexion();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		Savepoint sp = null;

		DAOBuque buque = new DAOBuque();
		buque.setConn(conn);
		String tipoBuque = buque.darTipo(idBuque);

		String mensaje ="";
		Aceptado a = new Aceptado (mensaje);
		try
		{
			//Se verifica la razón por la cual se debe deshabilitar el buque:
			if (razon.equals("A") || razon.equals("M"))//Si es por avería o mantenimiento...
			{
				if (razon.equals("M"))
					buque.deshabilitarBuqueMantenimiento(idBuque);
				else if (razon.equals("A"))
					buque.deshabilitarBuqueAverio(idBuque);

				//Se revisa si hay otro buque con el mismo destino, tipo y capacidad.
				//Si hay un buque, el id de ese buque queda en idBuqueNuevo.
				ArrayList<String> idCargas = darCargasPorBuque(idBuque, conn);

				for(int i=0;i<idCargas.size();i++)
				{
					String cargaActual = idCargas.get(i);
					int idBuqueNuevo = hayOtroBuque ( cargaActual, idBuque, conn );
					int idArea = cargaCabeEnArea(cargaActual,tipoBuque, conn);

					if (idBuqueNuevo != -1) //Si se encuentra un buque apto para la carga
					{
						//Se mueve la carga a dicho buque
						moverCargaDeUnBuqueAOtro(cargaActual, idBuque, idBuqueNuevo+"", conn);

						mensaje += "La carga "+cargaActual+" se movió a otro buque.\n";
					}

					else if (idArea!=-1)//Si se encuentra una área apropiada
					{
						ejecutarTrasladitoDescarga(idArea+"", cargaActual, conn);

						mensaje += "La carga " +cargaActual+" se movió a un área de almacenamiento. \n";
					}
					else
					{
						mensaje += "La carga " +cargaActual+" no fue procesada. \n";
					}
					sp = conn.setSavepoint();
				}
			}

			else if (razon.equals("L"))                     //Si es por razones legales...
			{
				buque.deshabilitarBuqueLegales(idBuque);

				mensaje = "Se deshabilitó el buque "+idBuque+" por razones legales. \n"
						+ "No se pueden hacer operaciones de carga";

				//El buque no queda disponible para operaciones de carga
				sp = conn.setSavepoint();
			}

			else if (razon.equals("N"))         //Si se vuelve a habilitar el buque
			{
				buque.habilitarBuque(idBuque);
				//Si no es posible realizar todos los movimientos, se informa con la info
				//de todas las cargas que no pudieron ser procesadas
				mensaje = autorizarMovimientoCargas(idBuque, conn);
				sp = conn.setSavepoint();
			}

			a.setMensaje(mensaje);
			conn.commit();
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback(sp);
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return a;
	}

	public void ejecutarTrasladitoDescarga(String idArea, String idCarga, Connection conn) throws Exception
	{
		DAOCarga carga = new DAOCarga();
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOBuque buque = new DAOBuque();
		try 
		{
			carga.setConn(conn);
			area.setConn(conn);
			buque.setConn(conn);

			String idBuque = carga.consultarIDBuque(idCarga);
			if(idBuque.equals("")){throw new Exception("no tiene buque");}

			carga.trasladarBuqueAArea(idCarga, idArea);
			float pesoCarga = carga.darPesoCarga(idCarga);
			area.modificarPesoAreaPonerCarga(idArea , pesoCarga);
			buque.modificarPesoBuqueQuitarCarga(idBuque, pesoCarga);

			conn.commit();
		} 
		catch (Exception e) 
		{
			// solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			area.cerrarRecursos();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				area.cerrarRecursos();
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public String autorizarMovimientoCargas(String idBuque, Connection conn) throws Exception
	{
		String x ="";
		DAOBuque buque = new DAOBuque();
		buque.setConn(conn);
		String tipoBuque = buque.darTipo(idBuque);

		Savepoint sp = null;

		ArrayList<String> cargasBuque = new ArrayList<>();
		ArrayList<String> cargasArea = new ArrayList<>();
		ArrayList<String> cargasFallidas = new ArrayList<>();

		try
		{
			//Se revisa si hay otro buque con el mismo destino, tipo y capacidad.
			//Si hay un buque, el id de ese buque queda en idBuqueNuevo.
			ArrayList<String> idCargas = darCargasPorBuque(idBuque, conn);

			for(int i=0;i<idCargas.size();i++)
			{
				String cargaActual = idCargas.get(i);
				int idBuqueNuevo = hayOtroBuque ( cargaActual, idBuque, conn );
				int idArea = cargaCabeEnArea(cargaActual,tipoBuque, conn);

				if (idBuqueNuevo != -1) //Si se encuentra un buque apto para la carga
				{
					//Se mueve la carga a dicho buque
					moverCargaDeUnBuqueAOtro(cargaActual, idBuque, idBuqueNuevo+"", conn);

					//Se añade la carga añadida a la lista de cargas que lograron ser 
					//movidas a otro buque exitosamente
					cargasBuque.add(cargaActual);
					x+= "La carga "+cargaActual+" se movió a otro buque.\n";
				}

				else if (idArea!=-1)//Si se encuentra una área apropiada
				{
					ejecutarTrasladoDescarga(idArea+"", cargaActual, conn);
					cargasArea.add(cargaActual);
					x+="La carga " +cargaActual+" se movió a un área de almacenamiento. \n";
				}
				else
				{
					cargasFallidas.add(cargaActual);
					x+="La carga " +cargaActual+" no fue procesada. \n";
				}
				sp = conn.setSavepoint();
			}
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback(sp);
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return x;
	}

	public int cargaCabeEnArea(String idCarga, String tipoBuque, Connection conn) throws Exception
	{
		int id = -1;

		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOCarga carga = new DAOCarga();

		try
		{
			area.setConn(conn);
			carga.setConn(conn);
			float pesoCarga = carga.darPesoCarga(idCarga);

			try
			{
				id=Integer.parseInt(area.conseguirALMParaCarga(pesoCarga, tipoBuque, null));
			}
			catch (Exception e)
			{
				id=-1;
			}
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			area.cerrarRecursos();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				area.cerrarRecursos();
				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return id;
	}
	//hola
	public void moverCargaDeUnBuqueAOtro (String idCarga, 
			String idBuqueOrigen, String idBuqueDestino, Connection conn) throws Exception
	{
		DAOCarga carga = new DAOCarga();
		DAOBuque buque = new DAOBuque();
		try
		{
			carga.setConn(conn);
			buque.setConn(conn);

			float pesoCarga = carga.darPesoCarga(idCarga);

			//proceso de carga y descarga
			buqueEnProcesoCarga2(idBuqueDestino, conn);
			buqueEnProcesoDescarga2(idBuqueOrigen, conn);

			//Restar el peso de la carga al buque original
			buque.modificarPesoBuqueQuitarCarga(idBuqueOrigen, pesoCarga);

			//Asignarle a la carga el id del buque nuevo 
			carga.trasladarAOtroBuque(idCarga, idBuqueOrigen, idBuqueDestino);

			//Sumarle el peso         
			buque.modificarPesoBuquePonerCarga(idBuqueDestino, pesoCarga);

			//proceso de carga terminado
			buqueCargaTerminada2(idBuqueDestino, conn);
			buqueDescargaTerminada2(idBuqueOrigen, conn);
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}    	
	}

	public ArrayList<String> darCargasPorBuque(String idBuque, Connection conn) throws SQLException
	{
		DAOCarga carga = new DAOCarga();
		ArrayList <String> a = new ArrayList<String>();

		try
		{
			carga.setConn(conn);
			a = carga.idCargasPorBuque(idBuque);                      
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}    	
		return a;
	}

	public int hayOtroBuque(String idCarga, String idBuque, Connection conn) throws Exception
	{
		int id = -1;

		//Características del buque deshabilitado
		String dest = "";
		String tipo = "";
		float pesoCarga = 0; 

		DAOBuque buque = new DAOBuque();
		DAOCarga carga = new DAOCarga();

		try
		{
			buque.setConn(conn);
			carga.setConn(conn);
			dest = buque.darDestino(idBuque);
			tipo = buque.darTipo(idBuque);
			pesoCarga = carga.darPesoCarga(idCarga);

			ArrayList <String> idBuquesPosibles = buque.buscarBuquePosible(dest, tipo);

			for (int i=0; i<idBuquesPosibles.size() && id==-1; i++)
			{
				String buqueActual = idBuquesPosibles.get(i);
				String idBqAct = buqueActual +"";
				float cap = buque.darCapacidadActual(idBqAct);
				if (cap>=pesoCarga)
				{
					id = Integer.parseInt(idBqAct);
				}
			}

		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			buque.cerrarRecursos();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return id;
	}

	public String darDeshabilitado (String idBuque) throws Exception
	{	
		String a = "";
		DAOBuque buque = new DAOBuque();

		try
		{
			this.conn = darConexion();
			buque.setConn(conn);
			a = buque.darDeshabilitado(idBuque);                      
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			buque.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				buque.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return a;
	}

	//-----------------------------------------------------------------
	//req 13
	//-----------------------------------------------------------------

	public String cerrarAreaDeAlmacenamiento (String idOperador, String idArea) throws Exception
	{
		String mensaje = "Se cerró el área "+idArea+"\n";
		Aceptado a = new Aceptado(mensaje);
		this.conn = darConexion();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

		Savepoint sp1 = null;
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		area.setConn(conn);
		String tipoArea = area.darTipo(idArea);
		System.out.println("hola");

		System.out.println(tipoArea);
		try
		{
			area.deshabilitar(idArea);

			//Se revisa si hay atra area del mismo tipo y con capacidad.
			ArrayList<String> idCargas = darCargasPorArea(idArea, conn);
			System.out.println(idCargas);
			for(int i=0;i<idCargas.size();i++)
			{
				String cargaActual = idCargas.get(i);
				int idAreaNueva = hayAreaDisponible(cargaActual,tipoArea,conn);
				System.out.println(idAreaNueva+ "id area nueva");

				if (idAreaNueva!=-1)//Si se encuentra una área apropiada
				{
					moverCargaDeUnAreaAOtra(cargaActual, idArea, idAreaNueva+"", conn);
					mensaje += "La carga "+cargaActual+" se movió al área "+idAreaNueva+".\n";

				}
				else
				{
					mensaje+="La carga " +cargaActual+" no fue procesada. \n";
				}
				System.out.println(mensaje);
				sp1 = conn.setSavepoint("sp1");
			}
			a.setMensaje(mensaje);
			conn.commit();
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback(sp1);
			throw e;
		}
		finally 
		{
			try 
			{
				area.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return mensaje;
	}

	public ArrayList<String> darCargasPorArea(String idArea, Connection conn) throws SQLException
	{
		DAOCarga carga = new DAOCarga();
		ArrayList <String> a = new ArrayList<String>();

		try
		{
			carga.setConn(conn);
			a = carga.idCargasPorArea(idArea);                      
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{

				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return a;
	}

	public int hayAreaDisponible(String idCarga, String tipoArea, Connection conn) throws Exception
	{
		int id = -1;

		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOCarga carga = new DAOCarga();

		try
		{
			area.setConn(conn);
			carga.setConn(conn);
			float pesoCarga = carga.darPesoCarga(idCarga);

			try
			{   
				String x = area.conseguirOtraAreaParaCarga(pesoCarga, tipoArea);
				System.out.println(x);
				id=Integer.parseInt(x);
			}
			catch (Exception e)
			{
				id=-1;
			}
		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			area.cerrarRecursos();
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				area.cerrarRecursos();
				carga.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return id;
	}

	public void moverCargaDeUnAreaAOtra(String idCarga, 
			String idAreaOrigen, String idAreaDestino, Connection conn) throws Exception
	{
		DAOCarga carga = new DAOCarga();
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		try
		{
			carga.setConn(conn);
			area.setConn(conn);

			float pesoCarga = carga.darPesoCarga(idCarga);

			//Restar el peso de la carga al área original
			area.modificarPesoAreaQuitarCarga(idAreaOrigen, pesoCarga);

			//Asignarle a la carga el id del área nueva 
			carga.trasladarAOtraArea(idCarga, idAreaOrigen, idAreaDestino);

			//Sumarle el peso        
			area.modificarPesoAreaPonerCarga(idAreaDestino, pesoCarga);

		}
		catch (Exception e) 
		{
			//Solo se usa por precaución, este metodo solo selecciona valores
			conn.rollback();
			carga.cerrarRecursos();
			area.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				area.cerrarRecursos();
			} 
			catch (Exception exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}    	
	}

	//------------------------------------------------------------------------------------
	// CONSULTAS 
	//------------------------------------------------------------------------------------

	//
	// Consulta 5
	//
	public ArrayList<Carga> consultarCargas(ConsultarMovCargas consulta)throws Exception
	{
		ArrayList<Carga>  cargas= new ArrayList<Carga>();
		DAOCarga carga = new DAOCarga();
		try 
		{
			carga.setConn(conn);
			cargas = carga.consultaDeCargas(consulta);
		} 
		catch (Exception e) 
		{
			carga.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				carga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cargas;
	}

	//
	// Consulta 6
	//

	public ArrayList<AreaDeAlmacenamiento> consultarAreas(String idConsulta, String idCargaEsp) throws Exception
	{
		ArrayList <AreaDeAlmacenamiento> lista = new ArrayList<>();
		DAOAreaDeAlmacenamiento area = new DAOAreaDeAlmacenamiento();
		DAOCarga carga = new DAOCarga();
		try 
		{
			area.setConn(conn);
			carga.setConn(conn);
			// tiene el id del area donde se encuentra la carga, +","+ el id de la carga que se encuentra ahi
			ArrayList <String> idsAreasQueContinenCargasDelConsultor = 
					carga.consultaIdAreasParaCargas(idConsulta, idCargaEsp);

			//crea las areas con las condiciones de la busqueda anterior
			lista = area.consultaDeAreas(idsAreasQueContinenCargasDelConsultor);
		} 
		catch (Exception e) 
		{
			area.cerrarRecursos();
			throw e;
		}
		finally 
		{
			try 
			{
				area.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return lista;
	}

	//
	//--------------------------------------------------------------------------
	//  ITERACION 4
	//--------------------------------------------------------------------------
	//


	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR SALIDAS
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public ArrayList<ActividadMaritima> consultarSalidasIt4(String fechaIni,String fechaFin, 
			String nombreBuque, String tipoBuque, String tipoCarga, String order) throws Exception 
	{
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		ArrayList<ActividadMaritima> resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.consultarSalidasIt4(fechaIni, fechaFin,nombreBuque,tipoBuque,tipoCarga, order);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}
	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR SALIDAS
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transacciÃ³n
	 */
	public String consultarSalidasNotInIt4(String fechaIni,String fechaFin, String nombreBuque,
			String tipoBuque, String tipoCarga, String orden) throws Exception 
	{
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		String resp;
		try 
		{
			//////TransacciÃ³n
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.consultarSalidasNotInIt4(fechaIni, fechaFin,
					nombreBuque,tipoBuque,tipoCarga, orden);

		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR MOVIMIENTOS DE CARGA
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ArrayList<DatosMovimientoCarga> consultarMovimientosCarga(int idExportador,
			double valor, String tipo) throws Exception 
	{
		DAOActividadMaritima actividadMaritima= new DAOActividadMaritima();
		ArrayList<DatosMovimientoCarga> resp;
		try 
		{
			this.conn = darConexion();
			actividadMaritima.setConn(conn);
			resp = actividadMaritima.consultarMovimientosCarga(idExportador, valor, tipo);
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				actividadMaritima .cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}

	/**
	 * MÃ©todo que modela la transacciÃ³n que retorna el string aceptado si se cumple
	 * 
	 * * CONSULTAR AREAS DE ALMACENAMIENTO
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public ArrayList<MovimientoAreaAlmacenamiento> consultarDosAreas(int idArea1,
			int idArea2) throws Exception 
	{
		DAOAreaDeAlmacenamiento area= new DAOAreaDeAlmacenamiento();
		ArrayList<MovimientoAreaAlmacenamiento> resp;
		try 
		{
			this.conn = darConexion();
			area.setConn(conn);
			resp = area.consultarDosAreas(idArea1, idArea2);
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				area.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} 
			catch (SQLException exception) 
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}


	/**
	 * Metodo que modela la transaccion que retorna el string aceptado si se cumple
	 * 
	 * * CALCULAR BONO 2FC
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public String enCuantosEsta(int idExp) throws Exception 
	{
		String x = "";
		try
		{
			boolean esta1 = false;
			boolean esta2 = false;
			boolean esta3 = false;
			try
			{
				this.conn = darConexion();
				this.connLaurita = darConexionLaurita();
				this.connNeli = darConexionNeli();
				try
				{
					String sql = "SELECT * FROM dueño WHERE iddueño = "+idExp;

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = conn.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					if (rsF.next())
					{
						esta1 = true;
					}

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				try
				{

					String sql = "SELECT * FROM EXPORTADOR WHERE id_exportador = "+idExp;

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = connLaurita.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					if (rsF.next())
					{
						esta2 = true;
					}

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				try
				{
					String sql = "SELECT * FROM entidades WHERE id_Entidad = "+idExp;

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = connNeli.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					if (rsF.next())
					{
						esta3 = true;
					}

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				System.out.println("esta1: "+esta1+"-------esta2: "+esta2+"------esta3: "+esta3);
				if (esta1 && esta2 &&esta3)
					x = "todos";
				else if ((esta1 && esta2)||(esta1 && esta3)||(esta2 && esta3))
					x = "dos";
				else if (esta1||esta2||esta3)
					x ="uno";
				else 
					x="nada";

				conn.commit();
				connLaurita.commit();
				connNeli.commit();
				if (conn!=null && conn.isClosed())
					conn.close();
				if (connLaurita!=null && connLaurita.isClosed())
					connLaurita.close();
				if (connNeli!=null && connNeli.isClosed())
					connNeli.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}

	/**
	 * Metodo que modela la transaccion que retorna el string aceptado si se cumple
	 * 
	 * * CALCULAR BONO 2FC
	 * 
	 * @return aceptado si se completa la transaccion
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public String calcularBono2FC(int idExp, String x) throws Exception 
	{
		String m = "";
		double descuento = 0;
		if (x.equals("todos")) descuento = 0.05;
		else if (x.equals("dos")) descuento = 0.03;
		else descuento = 0;

		try
		{
			try
			{
				this.conn = darConexion();
				this.connNeli = darConexionNeli();
				this.connLaurita = darConexionLaurita();
				try
				{
					String sql = "UPDATE COSTO_FACTURADO SET COSTO = "
							+ "COSTO - (COSTO * "+descuento+") "
							+ "WHERE IDCOSTOFACTURADO IN "
							+ "(SELECT IDCOSTO FROM ACTIVIDAD_MARITIMA "
							+ "WHERE IDCARGA IN (SELECT IDCARGA FROM CARGA "
							+ "WHERE IDDUEÑO = "+idExp+"))";

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = conn.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					System.out.println(rsF);
					m += "Actualizó BDTM \n";

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				try
				{
					String sql ="UPDATE INVENTARIO" 
							+" SET PRECIO = PRECIO*(1-"+descuento+")" 
							+" WHERE ID_FACTURA IN" 
							+" (SELECT ID_FACTURA" 
							+" FROM FACTURA fac JOIN EXPORTADOR exp "
							+" ON fac.ID_CLIENTE = exp.ID_EXPORTADOR " 
							+"WHERE exp.ID_EXPORTADOR ="+ idExp +")";

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = connLaurita.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					m += "Actualizó BD LV\n";

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				try
				{
					String sql = "UPDATE cargamentos "
							+ "SET COSTO = COSTO - (COSTO *"+descuento+") "
							+ "WHERE id_entidad  = "+idExp;

					System.out.println("SQL verificacion stmt:" + sql);

					PreparedStatement prepStmtF = connNeli.prepareStatement(sql);
					ResultSet rsF = prepStmtF.executeQuery();
					m += "Actualizó BD NN\n";

					try
					{
						prepStmtF.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				catch (SQLException e)
				{
					conn.rollback();
					connLaurita.rollback();
					connNeli.rollback();
				}
				conn.commit();
				connNeli.commit();
				connLaurita.commit();
				if (conn!=null && conn.isClosed())
					conn.close();
				if (connLaurita!=null && connLaurita.isClosed())
					connLaurita.close();
				if (connNeli!=null && connNeli.isClosed())
					connNeli.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}



		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return m;
	}


	public ListaFacturaExp darFacturasExpConsu() throws Exception 
	{
		ArrayList<FacturaExp> facturas= new ArrayList<FacturaExp>();
		ArrayList<FacturaExp> facturas1= new ArrayList<FacturaExp>();
		ArrayList<FacturaExp> facturas2= new ArrayList<FacturaExp>();
		try {
			////// TransacciÃ³n		

			this.conn = darConexion();
			String sql = "select e.idExportador, c.tipo, count(*) AS NUM_ELEM,AVG(c.peso) AS PROM_PESO, SUM(cost.costo) AS PRECIO "
					+"From (Exportador e inner join carga c on e.idExportador = c.idDueño) inner join "
					+ "(COSTO_FACTURADO cost) on cost.idCostoFacturado = c.idCarga "
					+"group by e.idExportador, c.tipo";

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()){
				int id = rs.getInt("IDEXPORTADOR");
				String tipo = rs.getString("TIPO");
				int cantidad = rs.getInt("NUM_ELEM");
				double promedio = rs.getDouble("PROM_PESO");
				double precio = rs.getDouble("PRECIO");
				facturas.add(new FacturaExp(id, tipo, cantidad, promedio, precio));
			}


			this.connNeli = darConexionNeli();
			String sql1 = "select id_Entidad, tipo, count(*) as cantidad, avg(peso) as peso_promedio, sum(precio) as total"
					+ " from facturas natural join entidades natural join registros_cargamentos natural join cargamentos"
					+ " where id_Entidad in (select id_Exportador from exportadores) "
					+ " group by id_Entidad, tipo order by total desc";
			PreparedStatement prepStmt1 = connNeli.prepareStatement(sql1);
			ResultSet rs1 = prepStmt1.executeQuery();
			while (rs1.next()){
				int id = rs.getInt("ID_ENTIDAD");
				String tipo = rs.getString("TIPO");
				int cantidad = rs.getInt("CANTIDAD");
				double promedio = rs.getDouble("PESO_PROMEDIO");
				double precio = rs.getDouble("TOTAL");
				facturas1.add(new FacturaExp(id, tipo, cantidad, promedio, precio));
			}

			this.connLaurita = darConexionLaurita();
			String sql2 = "SELECT ID_EXPORTADOR, TIPO_CARGA, COUNT(*) AS NUM_ELEM, CANTIDAD, AVG(VOLUMEN) AS PESO_PROMEDIO, SUM(PRECIO) AS PRECIO_FACTURADO "
					+ "FROM EXPORTADOR ex JOIN FACTURA fa ON ex.ID_EXPORTADOR = fa.ID_FACTURA "
					+ "JOIN INVENTARIO inv ON fa.ID_FACTURA = inv.ID_FACTURA "
					+ "GROUP BY ID_EXPORTADOR, TIPO_CARGA, CANTIDAD "
					+ "ORDER BY PRECIO_FACTURADO DESC";

			PreparedStatement prepStmt2 = connLaurita.prepareStatement(sql2);

			ResultSet rs2 = prepStmt2.executeQuery();
			while (rs2.next()) {

				int id_exportador= Integer.parseInt(rs.getString("ID_EXPORTADOR"));

				String tipo_carga = rs.getString("TIPO_CARGA");

				int num_elementos = (int)(Double.parseDouble(rs.getString("CANTIDAD")));

				double precio_facturado = Double.parseDouble(rs.getString("PRECIO_FACTURADO"));

				double peso_promedio= Double.parseDouble(rs.getString("PESO_PROMEDIO"));

				facturas2.add(new FacturaExp(id_exportador, tipo_carga, num_elementos, peso_promedio, precio_facturado));
			}



			facturas.addAll(facturas1);
			facturas.addAll(facturas2);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			connLaurita.rollback();
			conn.rollback();
			connNeli.rollback();

			e.printStackTrace();
			throw e;

		} catch (Exception e) {
			connLaurita.rollback();
			conn.rollback();
			connNeli.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (this.conn != null && !conn.isClosed())
				{conn.commit();
				this.conn.close();
				}
				if (this.connNeli != null && !!connNeli.isClosed())
				{connNeli.commit();
				this.connNeli.close();
				}
				if (this.connLaurita!= null && !connLaurita.isClosed())
				{this.connLaurita.close();
				connLaurita.commit();
				}
			} catch (SQLException exception) {

				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFacturaExp(facturas);
	}

	public void onMessage(Message mensaje) 
	{
		try
		{
			manejarMensaje();
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void JMSMessage() throws Exception
	{
		context = new InitialContext();
		cf = (ConnectionFactory) context.lookup("java:JmsXA");
		ds = (DataSource) context.lookup("java:XAChie1");
		colaDefinida1 = (Queue) context.lookup("queue/WebApp1");
		ds2 = (DataSource) context.lookup("java:XAChie2");
		colaDefinida2 = (Queue) context.lookup("queue/WebApp2");
		ds3 = (DataSource) context.lookup("java:XAChie3");
		colaDefinida3 = (Queue) context.lookup("queue/WebApp3");
	}

	public void manejarMensaje() throws NamingException
	{
		UserTransaction utx = (UserTransaction) context.lookup("/UserTransaction");
		try
		{
			utx.begin();
			Session session = conm.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer;
			try
			{
				consumer = session.createConsumer((Destination) colaDefinida2);
			}
			catch (JMSException e)
			{
				consumer = session.createConsumer((Destination) colaDefinida3);
			}
			conm.start();
			System.out.println("WebApp2 - Esperando mensaje..");
			Message msn = consumer.receive();
			TextMessage txt = (TextMessage) msn;
			String porInsertar = txt.getText();
			System.out.println("WebApp2 - Recibido... " + porInsertar);
			String[] cosas = porInsertar.split("-");
			int i=0;
			if(cosas[0].equals("11"))
				i=0; //Se llama el metodo.
			if(cosas[0].equals("12"))
				i=0; //Se llama el metodo.
			if(cosas[0].equals("14"))
				i=0; //Se llama el metodo.
			if(cosas[0].equals("15"))
				i=0; //Se llama el metodo.
			//Se llama a un metodo del master el cual se conecta con los DAOs.
			session.close();
			utx.commit();
		}
		catch(Exception e)
		{
			System.out.println("wat");
		}
	}


	/**
	 * 
	 * @param mensaje
	 * @return
	 * @throws Exception
	 */
	public String ayudarDescarga(String mensaje) throws Exception {
		String respuesta = "14-";
		DAOAreaDeAlmacenamiento daoAlmacenamientos = new DAOAreaDeAlmacenamiento();
		DAOCarga carga= new DAOCarga();
		try {
			// Transacción
			this.conn = darConexion();
			daoAlmacenamientos.setConn(conn);
			String cargamentos[] = mensaje.split("-");
			for (int i = 1; i < cargamentos.length; i++) {
				String carg[] = cargamentos[i].split(":");
				//long idAlmacenamiento = daoAlmacenamientos.re1servar(carg[0], Double.parseDouble(carg[1]));
				String idAlmacenamiento = daoAlmacenamientos.conseguirALMParaCarga(Float.parseFloat(carg[0]), carg[1], System.currentTimeMillis()+"");
				if (!idAlmacenamiento.equals(""))
				{
					ejecutarTrasladoDescarga(idAlmacenamiento, "-1", conn);
				} else {
					respuesta += cargamentos[i];
				}
			}
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAlmacenamientos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return respuesta;
	}
}