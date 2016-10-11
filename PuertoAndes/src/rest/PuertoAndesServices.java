package rest;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.Aceptado;
import vos.ActividadMaritima;
import vos.AreaDeAlmacenamiento;
import vos.Carga;
import vos.ConsultarMovCargas;
import vos.DatosMovimientoCarga;
import vos.ListaFacturaExp;
import vos.MovimientoAreaAlmacenamiento;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/PuertoAndes...
 * @author 
 */
@Path("/PuertoAndes")
public class PuertoAndesServices 
{


	// Servicios REST tipo GET:


	/**
	 * Atributo que usa la anotaciÃ³n @Context para tener el ServletContext de la conexiÃ³n actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * MÃ©todo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	/**
	 * MÃ©todo que expone servicio REST usando Post de 
	 * 
	 * r/5
	 * 
	 * REGISTRAR SALIDA BUQUE
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/agentePortuario/1/registrarSalidaBuque/2/delMuelle/1/enFecha/02-02-2015
	 */
	@PUT
	@Path("/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response postRegistrarSalidaBuque(@javax.ws.rs.PathParam("idAgen") String idAgen,
			@javax.ws.rs.PathParam("idBuque") String idBuque,
			@javax.ws.rs.PathParam("idM") String idMuelle,
			@javax.ws.rs.PathParam("fecha") String fecha) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida;

		try 
		{
			if((idAgen == null || idBuque == null || idMuelle == null || fecha == null) ||
					(idAgen.equals("") || idBuque.equals("")|| idMuelle.equals("") || fecha.equals( ""))	)
			{
				throw new Exception("Datos invalidos");
			}
			registradaSalida = puerto.registrarSalidaMuelle(idAgen, idBuque, idMuelle, fecha);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * MÃ©todo que expone servicio REST usando Put de 
	 * 
	 * r/6
	 * 
	 * REGISTRAR UN TIPO DE CARGA DE UN BUQUE
	 * 
	 * Si a el tipo de carga, el idOperador, el idBuque, la info del buque y la fecha son
	 * iguales a la qeu llegan por parámetro, esDescarga es false, arriboDeBuque es true,
	 * entonces cargaConfirmada = true.
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarLlegadaCarga/{idCarga}/deLaCarga/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/operador/4/registrarTipoDeCargaDeUnaCarga/2/nombreBuque/La Niña/nombreExportador/Aquiles Bailo/idExportador/8/idCarga/4/tipoCarga/Vehículos/pesoCarga/600/fechaLlegada/01-02-2015
	 * 
	 * 
	 */
	@PUT
	@Path("/operador/{idOperador}/registrarTipoDeCargaDeUnaCarga/{idBuque}/nombreBuque/"
			+ "{nombreBuque}/nombreExportador/{nombreExportador}/idExportador/{idExportador}/"
			+ "idCarga/{idCarga}/tipoCarga/{tipoCarga}/pesoCarga/{pesoCarga}/"
			+ "fechaLlegada/{fechaLlegada}")

	@Produces({ MediaType.APPLICATION_JSON })
	public Response putRegistrarTipoDeCarga(@javax.ws.rs.PathParam("idOperador") String idOperador,
			@javax.ws.rs.PathParam("idBuque") String idBuque,
			@javax.ws.rs.PathParam("nombreBuque") String nombreBuque,
			@javax.ws.rs.PathParam("nombreExportador") String nombreExportador,
			@javax.ws.rs.PathParam("idExportador") String idExportador,
			@javax.ws.rs.PathParam("idCarga") String idCarga,
			@javax.ws.rs.PathParam("tipoCarga") String tipoCarga,
			@javax.ws.rs.PathParam("pesoCarga") String pesoCarga,
			@javax.ws.rs.PathParam("fechaLlegada") String fechaLlegada )

	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		Aceptado registradaSalida;
		try 
		{
			if(( idBuque == null || nombreBuque== null || nombreExportador== null ||
					idExportador==null || idCarga ==null || tipoCarga == null||
					pesoCarga==null  || fechaLlegada == null || idOperador == null) ||
					(idBuque.equals("") || nombreBuque.equals("") || nombreExportador.equals("")
							|| idExportador.equals("")||idCarga.equals("")||tipoCarga.equals("")
							|| pesoCarga.equals("")	|| fechaLlegada.equals("")||idOperador.equals("")))
			{
				throw new Exception("Datos inválidos");
			}

			registradaSalida = puerto.registrarTipoDeCargaDeUnBuque(idOperador,idBuque, 
					//nombreBuque,nombreExportador, idExportador, 
					idCarga, 
					//tipoCarga, pesoCarga,
					fechaLlegada);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();




	}

	/**
	 * MÃ©todo que expone servicio REST usando Put de 
	 * 
	 * r/8
	 * 
	 * REGISTRAR ENTREGA MERCANCIA	
	 *  
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/agentePortuario/0/registrarEntregaMercanciaId/12/delDueno/9/enFecha/05-02-2015
	 * 
	 *  
	 * @return Json con todos los videos de la base de datos O json con 
	 * <b>URL: </b>
	 * el error que se produjo
	 */
	@PUT
	@Path("/agentePortuario/{idAgen}/registrarEntregaMercanciaId/{idMer}/delDueno/{idDueno}/enFecha/{fecha}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response putRegistrarEntregaMercancia(

			@javax.ws.rs.PathParam("idAgen") String idAgen,
			@javax.ws.rs.PathParam("idMer") String idMercan,
			@javax.ws.rs.PathParam("idDueno") String idDueño,
			@javax.ws.rs.PathParam("fecha") String fecha)
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida;

		try 
		{
			if((idAgen == null || idMercan == null || idDueño == null || fecha == null) ||
					(idAgen.equals("")|| idMercan.equals("")||idDueño.equals("")|| fecha.equals(""))	)
			{
				throw new Exception("Datos inválidos");
			}

			registradaSalida = puerto.registrarEntregaMercancia( idAgen, idMercan, idDueño, fecha);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();
	}





	/**
	 * MÃ©todo que expone servicio REST usando Put de 
	 * 
	 * r/9 (bien)
	 * GENERAR FACTURA EXPORTADOR	
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarLlegadaCarga/{idCarga}/deLaCarga/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * 
	 * {"id":id, "fechaLlegada":"",
			  "fechaSalida":"fecha salida",
			  "esDescarga":"T","arriboDeBuque":"",
			  "salidaDeBuque":"","cargaConfirmada":"",
			  "buque":{"idB":idBuque},"carga":{"idC":idCarga},
			  "dueño":{},"muelle":{},"idOperador":idO,
			  "costoFacturado":{}
			}
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/operadorPortuario/0/generarFacturaAct/10/carga/3/fecha/01-02-2015
	 */
	@POST
	@Path("/operadorPortuario/{idP}/generarFacturaAct/{idAct}/carga/{idCarga}/fecha/{fecha}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_JSON })

	public Response putGenerarFactura(
			@javax.ws.rs.PathParam("idP") String idAgen,
			@javax.ws.rs.PathParam("fecha") String fecha,
			@javax.ws.rs.PathParam("idAct") String idAct,
			@javax.ws.rs.PathParam("idCarga") String idCarga)

	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida;

		try 
		{
			if((idAgen == null ||  fecha == null || idCarga.equals("")) || idAct.equals("") ||
					(idAgen.equals("")||  fecha.equals(""))	)
			{
				throw new Exception("Datos inválidos");
			}

			registradaSalida = puerto.putGenerarFactura(fecha, idCarga, idAct);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();
	}


	//
	// consulta usuarios
	//

	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * Informacion de arribos y salidas	
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/usuario/arribosSalidas/rangoFechas/N/N/nombreBuque/Titanic/tipoBuque/N/tipoCarga/N 
	 */
	@GET
	@Path("/usuario/arribosSalidas/rangoFechas/{fechaIni}/{fechaFin}/nombreBuque/{nombreBuque}/tipoBuque/{tipoBuque}/tipoCarga/{tipoCarga}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getArribosYSalidas(

			@javax.ws.rs.PathParam("fechaIni") String fechaIni,
			@javax.ws.rs.PathParam("fechaFin") String fechaFin,
			@javax.ws.rs.PathParam("nombreBuque") String nombreBuque,
			@javax.ws.rs.PathParam("tipoBuque") String tipoBuque,
			@javax.ws.rs.PathParam("tipoCarga") String tipoCarga) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = "";

		try 
		{
			registradaSalida = puerto.consultarSalidas(fechaIni, fechaFin,nombreBuque,tipoBuque,tipoCarga);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Método que expone servicio REST usando get de 
	 * 
	 * Informacion exportadores
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 */
	@GET
	@Path("/consultarExportador/{idExportador}/ordenado/{orden}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInfoExportador(

			@javax.ws.rs.PathParam("idExportador") String idExportador,
			@javax.ws.rs.PathParam("orden") String ordenadoS) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		boolean ordenado = (ordenadoS.equals("Y")? true: false);
		try 
		{
			registradaSalida = puerto.consultarExportador(idExportador, ordenado);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registradaSalida).build();
	}

	//pruebas

	/**
	 * Método que expone servicio REST usando get de 
	 * 
	 * Informacion exportadores
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/pruebaPK
	 */
	@GET
	@Path("/pruebaPK")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response prueba1() 
	{ 
		System.out.println("ENTRA A PRUEBA 1");
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = "Rechazado";
		try 
		{
			registradaSalida = puerto.pruebasLlavePK();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}


	/**
	 * Prueba llave fk no referenciada
	 */
	@GET
	@Path("/pruebaFKnoref")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pruebaFKNoReferenciada() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = "Rechazado";
		try 
		{
			registradaSalida = puerto.pruebasLlaveFKNoReferenciada();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Prueba llave fk  referenciada
	 */
	@GET
	@Path("/pruebaFKref")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pruebaFKReferenciada() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		try 
		{
			registradaSalida = puerto.pruebasLlaveFKReferenciada();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Prueba borrar fk maestra
	 */
	@GET
	@Path("/pruebaFKmaestra")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response borrarTuplaMaestraFK() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		try 
		{
			registradaSalida = puerto.borrarTuplaMaestraFK();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Prueba borrar fk dependiente
	 */
	@GET
	@Path("/pruebaFKdependiente")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response borrarTuplaDependienteFK() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		try 
		{
			registradaSalida = puerto.borrarTuplaDependienteFK();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Prueba cumple chequeo
	 */
	@GET
	@Path("/pruebaCumpleChequeo")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pruebaCumpleChequeo() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		try 
		{
			registradaSalida = puerto.pruebaCumpleChequeo();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}

	/**
	 * Prueba no cumple chequeo
	 */
	@GET
	@Path("/pruebaNoCumpleChequeo")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pruebaNoCumpleChequeo() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		String registradaSalida = null;
		try 
		{
			registradaSalida = puerto.pruebaNoCumpleChequeo();
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		System.out.println(registradaSalida);
		return Response.status(200).entity(registradaSalida).build();
	}


	//
	//    ITERACION 3
	//


	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/10
	 * 
	 * CARGAR BUQUE
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/cargarBuque?agen=&buque=&cargas=,,,,&fecha=
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/cargarBuqueVariasCargas?agen=10&buque=10&cargas=20,21&fecha=14/04/2016
	 */
	@PUT
	@Path("/cargarBuqueVariasCargas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response postCargarBuqueVariasCargas(
			@javax.ws.rs.QueryParam("buque") String idBuque,
			@javax.ws.rs.QueryParam("agen") String idAgen,
			@javax.ws.rs.QueryParam("cargas") String idCargas,
			@javax.ws.rs.QueryParam("fecha") String fecha) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		@SuppressWarnings("unused")
		Aceptado cargarBuque = new Aceptado();

		try 
		{
			if((idAgen == null || idBuque == null || idCargas == null || fecha == null) ||
					(idAgen.equals("") || idBuque.equals("")|| idCargas.equals("") || fecha.equals( ""))	)
			{
				throw new Exception("Datos invalidos");
			}
			if (puerto.darDeshabilitado(idBuque).equals("L"))
				throw new Exception("El buque está deshabilitado por problemas legales.");

			String [] idCargasS = idCargas.split(",");

			// se agregan cargas inconclusas para que si hay un error
			// el sistema pueda cargar las cargas que faltaron
			puerto.agregarCargasInconclusas(idAgen, idBuque, idCargasS ,  fecha);

			puerto.buqueEnProcesoCarga(idBuque);
			for(int i=0; i< idCargasS.length ; i++)
			{
				Aceptado verificarCarga = puerto.registrarTipoDeCargaDeUnBuque(idAgen, idBuque, idCargasS[i] ,  fecha);
				if(verificarCarga != null)
				{
					System.out.println(verificarCarga.recuperarMensaje());
					cargarBuque = puerto.cargarBuque(idAgen, idBuque, idCargasS[i]    , fecha, null);
					puerto.eliminarCargaInconclusa(idCargasS[i], null);
				}
				else
				{
					puerto.eliminarCargaInconclusa(idCargasS[i], null);
					throw new Exception ("carga: "+idCargasS[i] +" no verificada por inconsistencias."+
							" llamar a terminar cargas inconclusas y volver a llamar cargarBuqueVariasCargas con la carga del error arreglada");
				}
			}
			puerto.buqueCargaTerminada(idBuque);

		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity("aceptado").build();
	}

	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/10.2 auxiliar
	 * 
	 * TERMINAR CARGAS INCONCLUSAS
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/terminarCargasInconclusas
	 */
	@PUT
	@Path("/terminarCargasInconclusas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response terminarCargasInconclusas() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		Aceptado cargarBuque = null;

		try 
		{
			cargarBuque = puerto.terminarCargasInconclusas();

		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargarBuque).build();
	}



	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/11
	 * 
	 * DESCARGAR BUQUE
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/descargarBuqueVariasCargas?agen=&buque=&cargas=,,,,&fecha=
	 */
	@SuppressWarnings("null")
	@PUT
	@Path("/descargarBuqueVariasCargas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response postDescargarBuqueVariasCargas(
			@javax.ws.rs.QueryParam("buque") String idBuque,
			@javax.ws.rs.QueryParam("agen") String idAgen,
			@javax.ws.rs.QueryParam("cargas") String idCargas,
			@javax.ws.rs.QueryParam("fecha") String fecha) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		Aceptado descargarBuque = null;

		try 
		{
			if((idAgen == null || idBuque == null || idCargas == null || fecha == null) ||
					(idAgen.equals("") || idBuque.equals("")|| idCargas.equals("") || fecha.equals( ""))	)
			{
				throw new Exception("Datos invalidos");
			}
			if (puerto.darDeshabilitado(idBuque).equals("L"))
				throw new Exception("El buque está deshabilitado por problemas legales.");

			String [] idCargasS = idCargas.split(",");

			// se agregan cargas inconclusas para que si hay un error
			// el sistema pueda cargar las cargas que faltaron
			puerto.agregarDescargasInconclusas(idAgen,idBuque, idCargasS ,  fecha);
			// no tiene que saber el area de almacenamiento porque puede cambiar

			puerto.buqueEnProcesoDescarga(idBuque);
			for(int i=0; i< idCargasS.length ; i++)
			{
				Aceptado verificarCarga = puerto.registrarTipoDeCargaDeUnBuqueParaDescarga(idAgen, idBuque, idCargasS[i] ,  fecha);
				if(verificarCarga != null)
				{
					// busco las areas de almacenamiento 
					Connection connCandadoArea = null;
					try
					{
						String idAreaAlm = puerto.conseguirAreaAlmPara(idCargasS[i], fecha, connCandadoArea);
						System.out.println(verificarCarga.recuperarMensaje());
						descargarBuque = puerto.descargarBuque(idAgen, idAreaAlm, idCargasS[i] , fecha, connCandadoArea);
						puerto.eliminarDescargaInconclusa(idCargasS[i], null);
					}
					catch(Exception e)
					{
						connCandadoArea.rollback();
						throw e;
					}
				}
				else
				{
					puerto.eliminarDescargaInconclusa(idCargasS[i], null);
					throw new Exception ("carga: "+idCargasS[i] +" no verificada por inconsistencias."+
							" llamar a terminar descargas inconclusas y volver a llamar descargarBuqueVariasCargas con la carga del error arreglada");
				}
			}
			puerto.buqueDescargaTerminada(idBuque);

		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(descargarBuque).build();
	}
	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/11.2 auxiliar
	 * 
	 * TERMINAR DESCARGAS INCONCLUSAS
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/terminarCargasInconclusas
	 */
	@PUT
	@Path("/terminarDescargasInconclusas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response terminarDescargasInconclusas() 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		Aceptado cargarBuque = null;

		try 
		{
			cargarBuque = puerto.terminarDescargasInconclusas();

		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargarBuque).build();
	}



	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/12
	 * 
	 * DESHABILITAR Y HABILITAR BUQUE
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/deshabilitarBuque?agen=&razon=&buque=
	 */

	@PUT
	@Path("/deshabilitarBuque")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deshabilitarBuque(
			@javax.ws.rs.QueryParam("razon") String razon,
			@javax.ws.rs.QueryParam("agen") String idAgente,
			@javax.ws.rs.QueryParam("buque") String idBuque) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		Aceptado deshabilitarBuque;

		try 
		{
			if((idAgente == null || idBuque == null || razon == null  ||
					(idAgente.equals("") || idBuque.equals("")|| razon.equals("") )))
			{
				throw new Exception("Datos invalidos");
			}
			deshabilitarBuque = puerto.deshabilitarBuque(idAgente, idBuque, razon);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(deshabilitarBuque).build();
	}

	/**
	 * MÃ©todo que expone servicio REST usando  
	 * 
	 * r/13
	 * 
	 * CERRAR AREA DE ALMACENAMIENTO
	 * 
	 * <b>URL: </b> http://ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/cerrarArea?agen=&area=
	 */
	@PUT
	@Path("/cerrarArea")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response cerrarAreaDeAlmacenamiento(
			@javax.ws.rs.QueryParam("agen") String idAgen,
			@javax.ws.rs.QueryParam("area") String idArea) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		//Aceptado cerrarArea;
		String cerrarArea = "";
		try 
		{
			if(idAgen == null || idArea == null || idAgen.equals("") || idArea.equals("")	)
			{
				throw new Exception("Datos invalidos");
			}
			System.out.println(idAgen+"   "+idArea);
			cerrarArea = puerto.cerrarAreaDeAlmacenamiento(idAgen, idArea);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cerrarArea).build();
	}
	//-------------------------------------------------------------
	//consulta
	//---------------------------------------------------------------
	//
	//


	/**
	 * Método que expone servicio REST usando get de 
	 * 
	 * Informacion carga exportadores e importadores
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return 
	 * el error que se produjo
	 * 
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultarMovimientoCargas?
	 */
	@GET
	@Path("/consultarMovimientoCargas/")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getMovCargasExporImpor(ConsultarMovCargas consulta) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		ArrayList<Carga> cargas  = new ArrayList<Carga>();
		try 
		{
			System.out.println("Llega consulta del solicitante: "+ consulta.darIdSolicita());
			cargas = puerto.consultarCargas(consulta);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cargas).build();
	}

	/**
	 * Método que expone servicio REST usando get de 
	 * 
	 * Informacion areas  exportadores e importadores
	 * 
	 * <b>URL: </b> 
	 * @return  
	 * el error que se produjo
	 * 
	 * /query?from=100&to=200&orderBy=age&orderBy=name”
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultarAreas?idConsulta=&idCargaEspecifica=
	 */
	@GET
	@Path("/consultarAreas")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response getInfAreasAlmExporImpor(
			@javax.ws.rs.QueryParam("idConsulta") String idConsulta,
			@javax.ws.rs.QueryParam("idCargaEspecifica") String idCargaEsp
			) 
	{ 
		PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());
		ArrayList<AreaDeAlmacenamiento> areas  = new ArrayList<AreaDeAlmacenamiento>();
		try 
		{
			System.out.println("Llega consulta del solicitante: "+ idConsulta);
			areas = puerto.consultarAreas(idConsulta, idCargaEsp);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(areas).build();
	}
	//
	//
	//
	//
	//


	//
	// consulta usuarios   PARTE ITERACION 4
	//

	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * Informacion de arribos y salidas	 iteracion 4
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultarArribosSalidas?fechaI=01/06/16&fechaF=02/06/16&nombB=buque65&tipoB=R&tipoC=R&order=idActMar
	 * 
	 * String=  "23/04/2016,30/04/2016,Barco463,M,M,idActMar"
	 * 
	 */
	@GET
	@Path("/consultarArribosSalidas")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public List<ActividadMaritima> getArribosYSalidasIt4(
			@javax.ws.rs.QueryParam("fechaI") String fechaIn,
			@javax.ws.rs.QueryParam("fechaF") String fechaFi,
			@javax.ws.rs.QueryParam("nombB") String nombBarco,
			@javax.ws.rs.QueryParam("tipoB") String tipoBarco,
			@javax.ws.rs.QueryParam("tipoC") String tipoCarg,
			@javax.ws.rs.QueryParam("order") String orden) 
	{ 
		List<ActividadMaritima> respuesta = new ArrayList<ActividadMaritima>();
		try 
		{
			Date fechaIni =  new SimpleDateFormat("dd/MM/yyyy").parse(fechaIn);
			Date fechaFin =  new SimpleDateFormat("dd/MM/yyyy").parse(fechaFi);
			String nombreBuque = nombBarco;
			String tipoBuque = tipoBarco;
			String tipoCarga = tipoCarg;
			PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());

			System.out.println(fechaIni+"        "+fechaFin+"        "+ nombreBuque+"    "+tipoBuque+"     "+tipoCarga);

			respuesta = puerto.consultarSalidasIt4(fechaIn, fechaFi,nombreBuque,tipoBuque,tipoCarga, orden);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		//respuesta.add(new ActividadMaritima(0,null,null,true,true,true,true,null,null,null,null,0,null));

		return respuesta;
	}
	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * Informacion de arribos y salidas	 iteracion 4
	 * 
	 * <b>URL: </b> http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/{idAgen}/registrarSalidaBuque/{idBuque}/delMuelle/{idM}/enFecha/{fecha}"
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultarArribosSalidasNotIn?fechaI=01/06/16&fechaF=02/06/16&nombB=buque65&tipoB=R&tipoC=R&order=idActMar
	 * 
	 * String=  "23/04/2016,30/04/2016,Barco463,M,M"
	 * 
	 */
	@GET
	@Path("/consultarArribosSalidasNotIn")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public String getArribosYSalidasNotInIt4(
			@javax.ws.rs.QueryParam("fechaI") String fechaIn,
			@javax.ws.rs.QueryParam("fechaF") String fechaFi,
			@javax.ws.rs.QueryParam("nombB") String nombBarco,
			@javax.ws.rs.QueryParam("tipoB") String tipoBarco,
			@javax.ws.rs.QueryParam("tipoC") String tipoCarg,
			@javax.ws.rs.QueryParam("order") String orden) 
	{ 

		String respuesta = "";
		try 
		{
			Date fechaIni =  new SimpleDateFormat("dd/MM/yyyy").parse(fechaIn);
			Date fechaFin =  new SimpleDateFormat("dd/MM/yyyy").parse(fechaFi);
			String nombreBuque = nombBarco;
			String tipoBuque = tipoBarco;
			String tipoCarga = tipoCarg;
			PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());

			System.out.println(fechaIni+"        "+fechaFin+"        "+ nombreBuque+"    "+tipoBuque+"     "+tipoCarga);

			respuesta = puerto.consultarSalidasNotInIt4(fechaIn, fechaFi,nombreBuque,tipoBuque,tipoCarga,orden);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		//respuesta.add(new ActividadMaritima(0,null,null,true,true,true,true,null,null,null,null,0,null));

		return respuesta;
	}

	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * RFC9 - Consultar movimientos de carga 
	 *
	 * @return Json			     * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultaMovCarga?idExp=26&valor=3000&tipo=P
	 * 
	 */
	@GET
	@Path("/consultaMovCarga")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public List<DatosMovimientoCarga> consultarMovimientosCarga(
			@javax.ws.rs.QueryParam("idExp") String idExp,
			@javax.ws.rs.QueryParam("valor") String valor,
			@javax.ws.rs.QueryParam("tipo") String tipo) 
	{ 
		List<DatosMovimientoCarga> respuesta = new ArrayList<>();
		try 
		{
			int idExportador = Integer.parseInt(idExp);
			double val = Double.parseDouble(valor);
			PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());

			System.out.println("Se buscan los movimientos de carga del exportador con "
					+ "id " + idExp + "de tipo "+tipo+" que sobrepasen el valor "+ val);
			respuesta = puerto.consultarMovimientosCarga(idExportador, val, tipo);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return respuesta;
	}

	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * RFC10 - Consultar areas de almacenamiento
	 *
	 * @return Json			     * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/consultaAreas?area1=3&area2=5
	 * 
	 */
	@GET
	@Path("/consultaAreas")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public List<MovimientoAreaAlmacenamiento> consultarDosAreas(
			@javax.ws.rs.QueryParam("area1") String area1,
			@javax.ws.rs.QueryParam("area2") String area2) 
	{ 
		List<MovimientoAreaAlmacenamiento> respuesta = new ArrayList<>();
		try 
		{
			int idArea1 = Integer.parseInt(area1);
			int idArea2 = Integer.parseInt(area2);
			PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());

			System.out.println("Se buscan los movimientos de carga de las áreas "
					+ idArea1 + " y "+ idArea2);
			respuesta = puerto.consultarDosAreas(idArea1, idArea2);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return respuesta;
	}



	//-------------------------------------------------------------------------
	// ITERACIÓN 5
	//-------------------------------------------------------------------------

	/**
	 * MÃ©todo que expone servicio REST usando get de 
	 * 
	 * RF14 - Descargar buque it5
	 *
	 * @return Json			     * 
	 * 
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/calcularBono2FC?idExp=3
	 * 
	 */
	@GET
	@Path("/calcularBono2FC")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public String calcularBono2FC(@javax.ws.rs.QueryParam("idExp") String id)
	{ 
		String n= "";
		try 
		{
			PuertoAndesMaster puerto = new PuertoAndesMaster(getPath());

			System.out.println("Se calcula el bono de los exportadores");
			int idExp = Integer.parseInt(id);
			String x = puerto.enCuantosEsta(idExp);
			n = puerto.calcularBono2FC(idExp, x);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return n;
	}

	//
	//-------------------------------------------------------------------
	// POBLAR TABLAS
	//-------------------------------------------------------------------

	/**
	 * poblar tablas
	 * http://localhost:8080/VideoAndes/rest/PuertoAndes/poblarTablasCargasBuque
	 */
	@POST
	@Path("/poblarTablasCargasBuque")
	public Response poblarTablas() 
	{ 
		//Aceptado acep = new Aceptado("Los archivos fueron creados");
		String acep = "aceptado";
		//String path = "C:/Users/Usuario/Desktop/WorkspaceSISTRANS/data";
		String path = "C:/Users/USUARIO/Desktop/Sistrans";
		try 
		{
			File actMarF = new File(path +"/actMar.csv");
			PrintWriter actMar = new PrintWriter(actMarF);
			actMar.println("idActMar,fecha_Llegada,fecha_Salida,fecha_orden,es_Descarga,"+
					"arribo_Buque,salida_Buque,carga_confirmada,proceso_cd_terminado,"+
					"idMuelle,idOperador,idAlmacenamiento,idBuque,idCarga,idCosto");


			File buqueF = new File(path +"/buque.csv");
			PrintWriter buque = new PrintWriter(buqueF);
			buque.println("idBuque,nombre,nombreAgente,registro_Cap,peso,tipo,proceso_de_Carga,"+
					"proceso_de_Descarga,carga_Completada,descarga_Completada,deshabilitado,"+ 
					"ciudad_Destino,ciudad_Origen");

			File cargaF = new File(path +"/carga.csv");
			PrintWriter carga = new PrintWriter(cargaF);
			carga.println("idCarga,peso,tiempo_Estadia,tipo,origen,destino,"+
					"idBuque,idDueño");

			File cargaF2 = new File(path +"/carga2.csv");
			PrintWriter carga2 = new PrintWriter(cargaF2);
			carga2.println("idCarga,peso,tiempo_Estadia,tipo,origen,destino,"+
					"idAlmacenamiento,idDueño");
			File cargaF3 = new File(path +"/carga3.csv");
			PrintWriter carga3 = new PrintWriter(cargaF3);
			carga3.println("idCarga,peso,tiempo_Estadia,tipo,origen,destino,"+
					"idAlmacenamiento,idDueño");
			File cargaF4 = new File(path +"/carga4.csv");
			PrintWriter carga4 = new PrintWriter(cargaF4);
			carga4.println("idCarga,peso,tiempo_Estadia,tipo,origen,destino,"+
					"idBuque,idDueño");


			File buqueRoroF = new File(path +"/buqueRoro.csv");
			PrintWriter buqueRoro = new PrintWriter(buqueRoroF);
			buqueRoro.println("idBuque, capacidadEnToneladas");

			File buqueMultiF = new File(path +"/buqueMulti.csv");
			PrintWriter buqueMulti = new PrintWriter(buqueMultiF);
			buqueMulti.println("idBuque, capacidadEnToneladas");

			File buquePortaF = new File(path +"/buquePorta.csv");
			PrintWriter buquePorta = new PrintWriter(buquePortaF);
			buquePorta.println("idBuque, capacidadEnTEU");


			File areaF = new File(path +"/areaAlm.csv");
			PrintWriter areaAlm = new PrintWriter(areaF);
			areaAlm.println("idAreaAlm,tipo,habilitado,capacidad_total,peso_actual");


			File bodegaF = new File(path +"/bodega.csv");
			PrintWriter bodega = new PrintWriter(bodegaF);
			bodega.println("idBodega,largo,ancho,tiene_Plataforma,separacion_entre_col");

			File patioF = new File(path +"/patio.csv");
			PrintWriter patio = new PrintWriter(patioF);
			patio.println("idPatio,dimensiones,tipo_De_carga");


			File siloF = new File(path +"/silo.csv");
			PrintWriter silo = new PrintWriter(siloF);
			silo.println("idSilo,nombre");


			File costoF = new File(path +"/costo.csv");
			PrintWriter costo = new PrintWriter(costoF);
			costo.println("idCosto,costo,fecha");

			File duenoF = new File(path +"/dueno.csv");
			PrintWriter dueno = new PrintWriter(duenoF);
			dueno.println("idDueño,nombre");

			File importadoresF = new File(path +"/importa.csv");
			PrintWriter impo = new PrintWriter(importadoresF);
			impo.println("idDueño,registro,esHabitual");

			File exportaF = new File(path +"/exporta.csv");
			PrintWriter expo = new PrintWriter(exportaF);
			expo.println("idDueño,rut");

			File movimientosF = new File (path + "/movArea.csv");
			PrintWriter movArea = new PrintWriter(movimientosF);
			movArea.println("idMov,fecha,entrada,id_Area,id_Carga");

			int idDueño=0;
			int idAct =0;
			int idCarga1 =0;
			int idArea =0;
			int idMov=0;
			String [] posiblesTipoBuque = {"P","M","R"};
			String [] tipoAlm =  {"Bodega","Silo","Patio"};

			// actividades de descarga para el buque
			for(int idBuque= 0; idBuque< 200000; idBuque++)
			{
				// cada 20 act tiene otro dueño
				if(idBuque%20 == 0)
				{
					idDueño = idDueño +1;
					dueno.println(idDueño+",dueño"+idDueño);
					dueno.flush();
					String [] habituales = {"T","F"};
					String habit =  habituales[(int)(Math.random()*2)]; 
					impo.println(idDueño+",aduana"+idBuque+","+habit);
					impo.flush();
				}

				int tip = idBuque%3;  // da valores 0,2,1
				String tipoMPR = posiblesTipoBuque[tip];
				// se crea un buque
				buque.println(idBuque+",buque"+idBuque+",empresa"+idBuque+",registroCap"+idBuque+","+
						"1000,"+ tipoMPR+",F,F,F,F,N,Ciudad"+idAct+",Ciudad"+(idAct +1));
				// se crean dos cargas para el buque
				carga.println(idCarga1+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idBuque+","+idDueño);
				carga.println((idCarga1+1)+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idBuque+","+idDueño);
				//se crean dos actividades maritimas para descargar las dos cargas creadas para este buque
				Random rand = new Random();
				int idia = rand.nextInt(29)+1;
				int idiasig = idia+1;
				String dia="";
				String diasig ="";
				String mes="";
				String mesig="";
				if (idia<10)
				{
					dia = "0"+idia;
				}
				else
					dia = idia +"";
				if (idiasig<10)
				{
					diasig = "0"+idiasig;
				}
				else
					diasig=idiasig+"";
				int imes = rand.nextInt(11)+1;
				int imesig = imes+1;
				if (imes<10)
				{
					mes = "0"+imes;
				}
				else
					mes=imes+"";
				if(imesig<10)
				{
					mesig = "0"+imesig;
				}
				else
					mesig=imesig+"";
				float costoAct = (float) (50+(Math.random()*1000));
				costo.println(idAct+","+costoAct+",15/06/2016");
				costo.println((1+idAct)+","+costoAct+",15/06/2016");
				costo.flush();
				actMar.println(idAct+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,T,T,F,F,F,"+idAct+",10,"+idArea+","+idBuque+","+idCarga1+","+idAct+"");
				actMar.println((idAct + 1)+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,T,T,F,F,F,"+idAct+",10,"+idArea+","+idBuque+","+(idCarga1 + 1)+","+(idAct+1)+"");


				//creo el area de almacenamiento donde estan las cargas
				areaAlm.println(idArea+","+tipoAlm[tip]+",T,10000,500");

				if(tipoMPR.equals("P"))
				{
					//porta
					buquePorta.println(idBuque+",1500");

					bodega.println(idArea+",30,50,T,2");

					bodega.flush();
				}
				else if(tipoMPR.equals("M"))
				{
					//multi
					buqueMulti.println(idBuque+",1500");

					silo.println(idArea+",silo"+idArea);

					silo.flush();
				}
				else if(tipoMPR.equals("R"))
				{
					//roro
					buqueRoro.println(idBuque+",1500");

					patio.println(idArea+",300,Carros");

					patio.flush();
				}

				idCarga1 += 2;
				idAct += 2;
				idArea += 1;

				buque.flush();
				carga.flush();
				actMar.flush();
				buqueMulti.flush();
				buquePorta.flush();
				buqueRoro.flush();

				areaAlm.flush();
			}

			// actividades de carga buque
			for(int idBuque= 200000; idBuque< 400000; idBuque++)
			{
				// cada 20 act tiene otro dueño
				if(idBuque%20 == 0)
				{
					idDueño = idDueño +1;
					dueno.println(idDueño+",dueño"+idDueño);
					dueno.flush();
					expo.println(idDueño+",rutnum"+idBuque);
					expo.flush();
				}

				int tip = idBuque%3;  // da valores 0,2,1
				String tipoMPR = posiblesTipoBuque[tip];
				// se crea un buque
				buque.println(idBuque+",buque"+idBuque+",empresa"+idBuque+",registroCap"+idBuque+","+
						"1000,"+ tipoMPR+",F,F,F,F,N,Ciudad"+idAct+",Ciudad"+(idAct +1));
				// se crean dos cargas para el buque
				carga2.println(idCarga1+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idArea+","+idDueño);
				carga2.println((idCarga1+1)+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idArea+","+idDueño);
				//se crean dos actividades maritimas para cargar las dos cargas creadas para este buque
				Random rand = new Random();
				int idia = rand.nextInt(29)+1;
				int idiasig = idia+1;
				String dia="";
				String diasig ="";
				String mes="";
				String mesig="";
				if (idia<10)
				{
					dia = "0"+idia;
				}
				else
					dia = idia +"";
				if (idiasig<10)
				{
					diasig = "0"+idiasig;
				}
				else
					diasig=idiasig+"";
				int imes = rand.nextInt(11)+1;
				int imesig = imes+1;
				if (imes<10)
				{
					mes = "0"+imes;
				}
				else
					mes=imes+"";
				if(imesig<10)
				{
					mesig = "0"+imesig;
				}
				else
					mesig=imesig+"";
				float costoAct = (float) (50+(Math.random()*1000));
				costo.println(idAct+","+costoAct+",15/06/2016");
				costo.println((1+idAct)+","+costoAct+",15/06/2016");
				costo.flush();
				actMar.println(idAct+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,F,T,F,F,F,"+idAct+",10,"+idArea+","+idBuque+","+idCarga1+","+idAct+"");
				actMar.println((idAct + 1)+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,F,T,F,F,F,"+idAct+",10,"+idArea+","+idBuque+","+(idCarga1 + 1)+","+(idAct+1)+"");

				//creo el area de almacenamiento donde estan las cargas
				areaAlm.println(idArea+","+tipoAlm[tip]+",T,10000,500");

				if(tipoMPR.equals("P"))
				{
					//porta y bodega
					buquePorta.println(idBuque+",1500");
					bodega.println(idArea+",30,50,T,2");

					bodega.flush();

				}
				else if(tipoMPR.equals("M"))
				{
					//multi y silo
					buqueMulti.println(idBuque+",1500");
					silo.println(idArea+",silo"+idArea);

					silo.flush();

				}
				else if(tipoMPR.equals("R"))
				{
					//roro y patio
					buqueRoro.println(idBuque+",1500");
					patio.println(idArea+",300,Carros");

					patio.flush();
				}

				idCarga1 += 2;
				idAct += 2;
				idArea = idArea+1;

				areaAlm.flush();
				buque.flush();
				carga2.flush();
				actMar.flush();
				buqueMulti.flush();
				buquePorta.flush();
				buqueRoro.flush();
			}

			//actividades de descarga con mov. de cargas en areas
			for(int idBuque= 400000; idBuque< 600000; idBuque++)
			{
				// cada 20 act tiene otro dueño
				if(idBuque%20 == 0)
				{
					idDueño = idDueño +1;
					dueno.println(idDueño+",dueño"+idDueño);
					dueno.flush();
					String [] habituales = {"T","F"};
					String habit =  habituales[(int)(Math.random()*2)]; 
					impo.println(idDueño+",aduana"+idBuque+","+habit);
					impo.flush();
				}

				int tip = idBuque%3;  // da valores 0,2,1
				String tipoMPR = posiblesTipoBuque[tip];
				// se crea un buque
				buque.println(idBuque+",buque"+idBuque+",empresa"+idBuque+",registroCap"+idBuque+","+
						"1000,"+ tipoMPR+",F,F,F,T,N,Ciudad"+idAct+",Ciudad"+(idAct +1));
				// se crean dos cargas para el buque
				carga3.println(idCarga1+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idArea+","+idDueño);
				carga3.println((idCarga1+1)+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idArea+","+idDueño);

				//se crean dos actividades maritimas para descargar las dos cargas creadas para este buque
				Random rand = new Random();
				int idia = rand.nextInt(29)+1;
				int idiasig = idia+1;
				String dia="";
				String diasig ="";
				String mes="";
				String mesig="";
				if (idia<10)
				{
					dia = "0"+idia;
				}
				else
					dia = idia +"";
				if (idiasig<10)
				{
					diasig = "0"+idiasig;
				}
				else
					diasig=idiasig+"";
				int imes = rand.nextInt(11)+1;
				int imesig = imes+1;
				if (imes<10)
				{
					mes = "0"+imes;
				}
				else
					mes=imes+"";
				if(imesig<10)
				{
					mesig = "0"+imesig;
				}
				else
					mesig=imesig+"";
				float costoAct = (float) (50+(Math.random()*1000));
				costo.println(idAct+","+costoAct+",15/06/2016");
				costo.println((1+idAct)+","+costoAct+",15/06/2016");
				costo.flush();
				actMar.println(idAct+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,T,T,F,T,T,"+idAct+",10,"+idArea+","+idBuque+","+idCarga1+","+idAct+"");
				actMar.println((idAct + 1)+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,T,T,F,T,T,"+idAct+",10,"+idArea+","+idBuque+","+(idCarga1 + 1)+","+(idAct+1)+"");


				//creo el area de almacenamiento donde estan las cargas
				areaAlm.println(idArea+","+tipoAlm[tip]+",T,10000,500");

				//creo movimientos de salida en el area
				movArea.println((idMov)+","+(diasig)+"/"+(mesig)+"/2016,T,"+idArea+","+idCarga1);
				movArea.println((idMov+1)+","+(diasig)+"/"+(mesig)+"/2016,T,"+idArea+","+(idCarga1+1));

				if(tipoMPR.equals("P"))
				{
					//porta
					buquePorta.println(idBuque+",1500");

					bodega.println(idArea+",30,50,T,2");

					bodega.flush();
				}
				else if(tipoMPR.equals("M"))
				{
					//multi
					buqueMulti.println(idBuque+",1500");

					silo.println(idArea+",silo"+idArea);

					silo.flush();
				}
				else if(tipoMPR.equals("R"))
				{
					//roro
					buqueRoro.println(idBuque+",1500");

					patio.println(idArea+",300,Carros");

					patio.flush();
				}

				idCarga1 += 2;
				idAct += 2;
				idArea += 1;
				idMov +=2;

				buque.flush();
				carga3.flush();
				actMar.flush();
				buqueMulti.flush();
				buquePorta.flush();
				buqueRoro.flush();
				movArea.flush();
				areaAlm.flush();
			}

			//actividades de carga con movimientos de cargas en areas
			for(int idBuque= 600000;idBuque< 800000; idBuque++)
			{
				// cada 20 act tiene otro dueño
				if(idBuque%20 == 0)
				{
					idDueño = idDueño +1;
					dueno.println(idDueño+",dueño"+idDueño);
					dueno.flush();
					expo.println(idDueño+",rutnum"+idBuque);
					expo.flush();
				}

				int tip = idBuque%3;  // da valores 0,2,1
				String tipoMPR = posiblesTipoBuque[tip];
				// se crea un buque
				buque.println(idBuque+",buque"+idBuque+",empresa"+idBuque+",registroCap"+idBuque+","+
						"1000,"+ tipoMPR+",F,F,T,F,N,Ciudad"+idAct+",Ciudad"+(idAct +1));
				// se crean dos cargas para el buque
				carga4.println(idCarga1+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idBuque+","+idDueño);
				carga4.println((idCarga1+1)+",500,1,"+tipoMPR+",puerto"+idCarga1+",puerto"+(idCarga1+1)+","+idBuque+","+idDueño);
				//se crean dos actividades maritimas para cargar las dos cargas creadas para este buque
				Random rand = new Random();
				int idia = rand.nextInt(29)+1;
				int idiasig = idia+1;
				String dia="";
				String diasig ="";
				String mes="";
				String mesig="";
				if (idia<10)
				{
					dia = "0"+idia;
				}
				else
					dia = idia +"";
				if (idiasig<10)
				{
					diasig = "0"+idiasig;
				}
				else
					diasig=idiasig+"";
				int imes = rand.nextInt(11)+1;
				int imesig = imes+1;
				if (imes<10)
				{
					mes = "0"+imes;
				}
				else
					mes=imes+"";
				if(imesig<10)
				{
					mesig = "0"+imesig;
				}
				else
					mesig=imesig+"";
				float costoAct = (float) (50+(Math.random()*1000));
				costo.println(idAct+","+costoAct+",15/06/2016");
				costo.println((1+idAct)+","+costoAct+",15/06/2016");
				costo.flush();
				actMar.println(idAct+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,F,T,F,T,T,"+idAct+",10,"+idArea+","+idBuque+","+idCarga1+","+idAct+"");
				actMar.println((idAct + 1)+","+dia+"/"+(mesig)+"/2016,"+(diasig)+"/"+(mesig)+"/2016,"+dia+"/"+mes+"/2016,F,T,F,T,T,"+idAct+",10,"+idArea+","+idBuque+","+(idCarga1 + 1)+","+(idAct+1)+"");

				//creo el area de almacenamiento donde estan las cargas
				areaAlm.println(idArea+","+tipoAlm[tip]+",T,10000,500");

				//creo movimientos de salida en el area
				movArea.println((idMov)+","+dia+"/"+(mesig)+"/2016,F,"+idArea+","+idCarga1);
				movArea.println((idMov+1)+","+dia+"/"+(mesig)+"/2016,F,"+idArea+","+(idCarga1+1));

				if(tipoMPR.equals("P"))
				{
					//porta y bodega
					buquePorta.println(idBuque+",1500");
					bodega.println(idArea+",30,50,T,2");

					bodega.flush();

				}
				else if(tipoMPR.equals("M"))
				{
					//multi y silo
					buqueMulti.println(idBuque+",1500");
					silo.println(idArea+",silo"+idArea);

					silo.flush();

				}
				else if(tipoMPR.equals("R"))
				{
					//roro y patio
					buqueRoro.println(idBuque+",1500");
					patio.println(idArea+",300,Carros");

					patio.flush();
				}

				idCarga1 += 2;
				idAct += 2;
				idArea = idArea+1;
				idMov+=2;

				areaAlm.flush();

				buque.flush();
				carga4.flush();
				actMar.flush();
				movArea.flush();
				buqueMulti.flush();
				buquePorta.flush();
				buqueRoro.flush();
			}

			File camionF = new File(path +"/camion.csv");
			PrintWriter camion = new PrintWriter(camionF);
			camion.println("idCamion,capacidad,empresa_Encargada");
			int idCamion = 0;

			for ( idCamion=0;idCamion<10000;idCamion++)
			{
				camion.println(idCamion+",300,empresaEncargada"+idCamion);
				camion.flush();
			}

			File actTerF = new File(path +"/actTer.csv");
			PrintWriter actTer = new PrintWriter(actTerF);
			camion.println("idActTer,fecha_Llegada,feha_Salida,es_Descarga,"
					+ "carga_Entregada,idOperador,idCamion,idAlmacenamiento,"
					+ "idCosto,idCarga");
			int idActTer = 0;

			for (idActTer=0;idActTer<80;idActTer = idActTer +2)
			{
				actTer.println(idActTer+",14/08/2016,15/08/2016,T,F,"+idActTer+","
						+idActTer+ ","+idActTer+ ","+idActTer+ ","+idActTer);
				actTer.println((idActTer+1)+",14/08/2016,15/08/2016,F,F,"+idActTer+","
						+idActTer+ ","+idActTer+ ","+idActTer+ ","+idActTer);
				actTer.flush();
			}
			costo.close();
			dueno.close();
			actTer.close();
			camion.close();
			areaAlm.close();
			silo.close();
			bodega.close();
			patio.close();
			buque.close();
			carga.close();
			carga2.close();
			carga3.close();
			carga4.close();
			actMar.close();
			buqueMulti.close();
			buquePorta.close();
			buqueRoro.close();
			impo.close();
			expo.close();
			movArea.close();

		} 
		catch (Exception e) 
		{

			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(acep).build();
	}
	
	@GET
	@Path("/facturas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFacturasExp() {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ListaFacturaExp facturas;
		try {
			facturas = tm.darFacturasExpConsu();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(facturas).build();
	}

}
