package es.ua.datos.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.ua.datos.cad.CAD;
import es.ua.datos.cad.Utilities;
import es.ua.datos.model.Application;
import es.ua.datos.model.ApplicationTypes;
import es.ua.datos.model.Category;
import es.ua.datos.model.CategoryDatasetTransp;
import es.ua.datos.model.CategoryTransp;
import es.ua.datos.model.Dataset;
import es.ua.datos.model.DatasetTransp;
import es.ua.datos.model.Language;
import es.ua.datos.model.RelatedDataset;
import es.ua.datos.model.Resource;
import es.ua.datos.model.ResourceTransp;
import es.ua.datos.model.Translation;

@Path ("{dbname}")
public class RestCRUD {
	
	//private Gson gson=new Gson();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	//private CAD miCad = new CAD("oracle");
	
	public RestCRUD(){};
	
	
	/*---DATASETS----*/
	
	/*
	 *  SERVICIOS GET
	 */
	
	//obtiene todos los datasets de la bd
	@Path ("/datasets")
	@GET
	//@Produces ("application/javascript")
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getTodos(@PathParam("dbname") String dbname, @QueryParam("callback") String callback) 
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			//out.print("callbackname:"+callback);
			CAD miCad = new CAD(dbname);
			List<Dataset> listaDatasets = miCad.getAllDatasets();
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
			
			
		}
		else
			return "La api no opera con "+ dbname + " todavia.";
		
		
		
	}
	
	//obtiene los datasets por una query string parametrizada en el path
	@Path ("datasets/description/{desc}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByQuery(@PathParam("dbname") String dbname,@PathParam("desc") String desc,@QueryParam("callback") String callback) throws IOException
	{
		/*llamada a cad
		 * 
		 * x.ejem. CAD cad=new CAD();
		 * 		   cad.getDatasetsByDesc(dbname,desc); devolveria una lista de datasets con descripcion=desc
		 * 
		 * */
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			List<Dataset> listaDatasets= miCad.getDatasetsByDesc(desc);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//obtiene un dataset
	@Path ("datasets/{id}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getById(@PathParam("dbname") String dbname,@PathParam("id") String id,@QueryParam("callback") String callback)
	{
		/*llamada a cad
		 * 
		 * x.ejem. CAD cad=new CAD();
		 * 		   cad.getDatasetById(dbname,id); devolveria el dataset con id=id
		 * 
		 * */
		
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);						
			
			Dataset ds= miCad.getDatasetByID(Integer.parseInt(id));
			
			if(callback == "")
				return gson.toJson(ds);
			else			
				return callback +'('+ gson.toJson(ds) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
		
	}

	//obtiene los datasets de una categoria
	@Path ("datasets/category/{idCat}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByArea(@PathParam("dbname") String dbname,@PathParam("idCat") int idCat,@QueryParam("callback") String callback)
	{
		/*llamada a cad
		 * 
		 * x.ejem. CAD cad=new CAD();
		 * 		   cad.getDatasetsByCat(dbname,cat); devolveria los datasets con categoria=cat
		 * 
		 * */
			
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Dataset> listaDatasets= miCad.getDatasetsByCat(idCat);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else			
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//servicio calzador para vuala- obtiene los datasets de una categoria con una descripcion 
	@Path ("datasets/category/{idCat}/description/{desc}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByAreaDesc(@PathParam("dbname") String dbname,@PathParam("idCat") int idCat,@PathParam("desc") String desc,@QueryParam("callback") String callback) throws IOException
	{
		/*llamada a cad
		 * 
		 * x.ejem. CAD cad=new CAD();
		 * 		   cad.getDatasetsByCat(dbname,cat); devolveria los datasets con categoria=cat
		 * 
		 * */
		//if(desc.equals(""))
			
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			//List<Dataset> listaDatasets= miCad.getDatasetsByCat(idCat, dbname);
			List<Dataset> listaDatasets= miCad.getDatasetByCatDesc(idCat, desc);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else			
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//obtiene los datasets relacionados "auxiliares" de un dataset pasando el id
	@Path("datasets/{id}/related")
	@GET
	@Produces ("application/x-javascript")
	public String getRelated(@PathParam("dbname") String dbname,@PathParam("id") int id,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Dataset> listaDatasets= miCad.getRelatedDatasets(id);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene todas las categorias de los datasets
	@Path ("datasets/categories")
	@GET
	@Produces ("application/x-javascript")
	public String getCategories(@PathParam("dbname") String dbname,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Category> listaCategories= miCad.getCategories();
			
			if(callback == "")
				return gson.toJson(listaCategories);
			else
				return callback +'('+ gson.toJson(listaCategories) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene las categorias a las que pertenece un dataset
	@Path("datasets/{id}/categories")
	@GET
	@Produces ("application/x-javascript")
	public String getDatasetsCategories(@PathParam("dbname") String dbname,@PathParam("id") int id,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Category> listaCategories = miCad.getCategoriesByIdDataset(id);				
			
			if(callback == "")
				return gson.toJson(listaCategories);
			else
				return callback +'('+ gson.toJson(listaCategories) + ')';
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene los num datasets mejor valorados
	@Path("datasets/top/{num}")
	@GET
	@Produces ("application/x-javascript")
	public String getTopDatasets(@PathParam("dbname") String dbname,@PathParam("num") int num,@QueryParam("callback") String callback)
	{
		//llamada al cad para acumular voto y recalcular 
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Dataset> listaDatasets = miCad.getXTopDatasets(num); 
					
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
			//return String.valueOf(idDatasetActualizado);
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	
	//tenemos permisos put para la url pero con jsonp desde uapiclient.js solo puede realizar get
	@Path("datasets/{id}/assessment/{ass}")
	@GET
	@Produces ("application/x-javascript")
	public String setDatasetVoteG(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("ass") int ass,@QueryParam("callback") String callback)
	{
		//llamada al cad para acumular voto y recalcular 
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			float mediaActualizadaDataset = miCad.setVoteToDataset(id, ass); 
			
			if(callback == "")
				return gson.toJson(mediaActualizadaDataset);
			else
				return callback +'('+ mediaActualizadaDataset + ')';
			//return String.valueOf(idDatasetActualizado);
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	@Path("datasets/last/{num}")
	@GET
	@Produces ("application/x-javascript")
	public String getLastDatasets(@PathParam("dbname") String dbname,@PathParam("num") int num,@QueryParam("callback") String callback)
	{
		//llamada al cad para acumular voto y recalcular 
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Dataset> listaDatasets = miCad.getLastXDatasets(num); 
			
			return callback +'('+ gson.toJson(listaDatasets) + ')';
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	/*
	 * SERVICIOS PUT
	 */
	
	//
	/* !!!tenemos permisos put para la url pero con jsonp desde uapiclient.js solo puede realizar get
	@Path("datasets/{id}/assessment/{ass}")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces ("application/x-javascript")
	public String setDatasetVote(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("ass") int ass,@QueryParam("callback") String callback)
	{
		//llamada al cad para acumular voto y recalcular 
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			int idDatasetActualizado = miCad.setVoteToDataset(id, ass); 
						
			return callback +'('+ idDatasetActualizado + ')';
			//return String.valueOf(idDatasetActualizado);
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		//devolver un ok????
	}
	*/
	
	/*--APPS--*/
	

	
	//obtiene todas las apps de bd
	@Path ("/apps")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getTodas(@PathParam("dbname") String dbname,@QueryParam("callback") String callback) 
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			CAD miCad = new CAD(dbname);

			List<Application> listaApps = miCad.getAllApplications();

//			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
//			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	
	
	//obtiene una app mediante su id
	@Path ("apps/{id}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAppById(@PathParam("dbname") String dbname,@PathParam("id") String id,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			CAD miCad = new CAD(dbname);
			
			Application app = miCad.getApplicationByID(Integer.parseInt(id));
			
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
			
			if(callback == "")
				return gson.toJson(app);
			else
				return callback +'('+ gson.toJson(app) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	
	
	//obtiene todas las categorias de los datasets
	@Path ("apps/types")
	@GET
	@Produces ("application/x-javascript")
	public String getAppsTypes(@PathParam("dbname") String dbname,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<ApplicationTypes> listaAppTypes= miCad.getApplicationTypes();
						
			if(callback == "")
				return gson.toJson(listaAppTypes );
			else
				return callback + '('+gson.toJson(listaAppTypes)+')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	/*
	//obtiene las apps de una categoria 
	@Path ("apps/category/{cat}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAppsByCat(@PathParam("dbname") String dbname,@PathParam("cat") String cat,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			List<Application> listaApps=new ArrayList<Application>();
			
			Application app=new Application();
			app.setName("don chuleton");
			app.setDescription("app para comprar a saco");
			
			listaApps.add(app);
			
			Application app2=new Application();
			app2.setName("fatiguo");
			app2.setDescription("app para sudar");
			
			listaApps.add(app2);
			
			
			return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	*/
	
	
	//obtiene las apps que coinciden con desc en descripcion de tabla
	@Path ("apps/description/{desc}")//a�adir expresion regular para descripcion
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAppByQuery(@PathParam("dbname") String dbname,@PathParam("desc") String desc,@QueryParam("callback") String callback) throws IOException
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Application> listaApps = miCad.getApplicationsByDesc(desc);
			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	
	
	//servicio calzador para vuala- obtiene los datasets de una categoria con una descripcion 
	@Path ("apps/type/{idType}/description/{desc}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAppsByTypeDesc(@PathParam("dbname") String dbname,@PathParam("idType") int idType,@PathParam("desc") String desc,@QueryParam("callback") String callback) throws IOException
	{
		
			
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Application> listaApps = miCad.getApplicationsByTypeDesc(idType, desc);
			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	
	//obtiene las apps de un tipo(web,movil,escritorio,...) 
	@Path ("apps/type/{type}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAppsByType(@PathParam("dbname") String dbname,@PathParam("type") String type,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			
			CAD miCad = new CAD(dbname);
			
			List<Application> listaApps = miCad.getApplicationsByType(Integer.parseInt(type));
//			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	
	
	//obtiene los num apps mejor valoradas
	@Path("apps/top/{num}")
	@GET
	@Produces ("application/x-javascript")
	public String getTopApps(@PathParam("dbname") String dbname,@PathParam("num") int num,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			
			CAD miCad = new CAD(dbname);
			List<Application> listaApps = miCad.getXTopApplications(num);
			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
	}
	
	//obtiene las num ultimas apps publicadas
	@Path("apps/last/{num}")
	@GET
	@Produces ("application/x-javascript")
	public String getLastApps(@PathParam("dbname") String dbname,@PathParam("num") int num,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			CAD miCad = new CAD(dbname);
			List<Application> listaApps = miCad.getLastXApplications(num);
			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			
			if(callback == "")
				return gson.toJson(listaApps);
			else
				return callback +'('+ gson.toJson(listaApps) + ')';
		}
		else
			return "La api no opera con "+ dbname;
		
	}
	
	//obtiene los datasets usados en la aplicacion id
	@Path("apps/{id}/datasets")
	@GET
	@Produces ("application/x-javascript")
	public String getUsed(@PathParam("dbname") String dbname,@PathParam("id") int id,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			List<Dataset> listaDatasets= miCad.getDatasetsUsedByApp(id);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//tenemos permisos put para la url pero con jsonp desde uapiclient.js solo puede realizar get
	@Path("apps/{id}/assessment/{ass}")
	@GET
	@Produces ("application/x-javascript")
	public String setAppVoteG(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("ass") int ass,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle"))// || dbname.equals("mysql") || dbname.equals("postgre"))
		{
			
			
//			Application app=new Application();
//			app.setName("don chuleton");
//			app.setDescription("app para comprar a saco");
//			
//			listaApps.add(app);
//			
//			Application app2=new Application();
//			app2.setName("fatiguo");
//			app2.setDescription("app para sudar");
//			
//			listaApps.add(app2);
			CAD miCad = new CAD(dbname);
			
			float mediaActualizadaApp = miCad.setVoteToApplication(id, ass); 
			
			if(callback == "")
				return gson.toJson(mediaActualizadaApp);
			else
				return callback +'('+ mediaActualizadaApp + ')';
			//return String.valueOf(idDatasetActualizado);

		}
		else
			return "La api no opera con "+ dbname;
		
	}
	
	/*
	 * SERVICIOS PARA TRANSPARENCIA.UA.ES 
	 * 
	 * 
	 */
	
	//obtiene un dataset de transparencia
	//TRADUCCION INGLES OK
	@Path ("transparency/lang/{abbrev}/datasets/{id}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByIdTransp(@PathParam("dbname") String dbname,@PathParam("id") String id,@PathParam("abbrev") String lan, @QueryParam("callback") String callback) throws IOException
	{
		
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			//Comprueba si el parametro que se le pasa como String es convertible a entero
			if(Utilities.isNumeric(id)){			
				CAD miCad = new CAD(dbname);
			
				DatasetTransp datasetTransp = miCad.getDatasetTranspByID(Integer.parseInt(id),lan);
			
				if(callback == "")
					return gson.toJson(datasetTransp);
				else
					return callback +'('+ gson.toJson(datasetTransp) + ')';
			}
			else
				return "El parámetro id no es un entero";
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
		
	}
	
	//obtiene los datasets de transparencia por una query string parametrizada en el path
	@Path ("transparency/lang/{abbrev}/datasets/description/{desc}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByQueryTransp(@PathParam("dbname") String dbname,@PathParam("desc") String desc,@PathParam("abbrev") String lan,@QueryParam("callback") String callback) throws IOException
	{
		
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			
			String descDecode = new String(desc.getBytes("ISO-8859-1"));
			
			CAD miCad = new CAD(dbname);
			List<DatasetTransp> listaDatasets = miCad.getDatasetsTranspByDesc(lan,descDecode);
			
			if(callback == "")
				return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//obtiene los datasets de transparencia de una categoria
	@Path ("transparency/lang/{abbrev}/datasets/category/{idCat}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByAreaTransp(@PathParam("dbname") String dbname,@PathParam("idCat") int idCat,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		
			
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{

			 CAD miCad = new CAD(dbname);
			
			
			List<DatasetTransp> listDatasetT = miCad.getDatasetsTranspByCategory(idCat,lan);
			
			
			
			if(callback == "")
				return gson.toJson(listDatasetT);
				//return gson.toJson(listaDatasets);
			else
				return callback +'('+ gson.toJson(listDatasetT) + ')';
				//return callback +'('+ gson.toJson(listaDatasets) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//obtiene los datasets de transparencia de una categoria con una descripcion 
	@Path ("transparency/lang/{abbrev}/datasets/category/{idCat}/description/{desc}")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getByAreaDescTransp(@PathParam("dbname") String dbname,@PathParam("idCat") int idCat,@PathParam("desc") String desc,@PathParam("abbrev") String lan,@QueryParam("callback") String callback) throws IOException
	{
			
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			
			String descDecode = new String(desc.getBytes("ISO-8859-1"));
			
			CAD miCad = new CAD(dbname);		
						
			//List<Dataset> listaDatasets= miCad.getDatasetsByCat(idCat, dbname);
			List<DatasetTransp> listaDatasetsTransp= miCad.getDatasetsTranspByCategoryDesc(idCat, lan, descDecode);
			
			if(callback == "")
				return gson.toJson(listaDatasetsTransp);
			else			
				return callback +'('+ gson.toJson(listaDatasetsTransp) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
		
	}
	
	//obtiene el dataset(open data) relacionado con el dataset transparencia
	//traer el id y montar la url http://datos.ua.es/ficha_dato.html?id=
	@Path("transparency/lang/{abbrev}/datasets/{id}/related")
	@GET
	@Produces ("application/x-javascript")
	public String getRelatedTranspOpenData(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			
			CAD miCad = new CAD(dbname);
			
			List<Integer> listaIdsDatasetsRelated = miCad.getDatasetRelated(id);
			
			if(callback == "")
				return gson.toJson(listaIdsDatasetsRelated);
			else
				return callback +'('+ gson.toJson(listaIdsDatasetsRelated) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene todas las categorias de transparencia 1er Nivel(principales del home)
	//TRADUCCION INGLES OK
	@Path ("transparency/lang/{abbrev}/datasets/categories")
	@GET
	@Produces ("application/x-javascript")
	public String getCategoriesTransp(@PathParam("dbname") String dbname,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			
			CAD miCad = new CAD(dbname);
			
			//List<Category> listaCategories= miCad.getCategories();
			List<CategoryTransp> listaCategoriesTransp=miCad.getMainCategoriesTransp(lan);
			
			if(callback == "")
				return gson.toJson(listaCategoriesTransp);
			else
				return callback +'('+ gson.toJson(listaCategoriesTransp) + ')';
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene todas las categorias de transparencia a partir del 1erNivel - subcategorias de una categoria
	//TRADUCCION INGLES OK
	@Path ("transparency/lang/{abbrev}/datasets/category/{id}/subcategories")
	@GET
	@Produces ("application/x-javascript")
	public String getSubCategoriesTransp(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			//List<Category> listaCategories= miCad.getCategories();
			List<CategoryTransp> listaCategoriesTransp=miCad.getSubcategoriesTransp(id,lan);
			
			if(callback == "")
				return gson.toJson(listaCategoriesTransp);
			else
				return callback +'('+ gson.toJson(listaCategoriesTransp) + ')';
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//obtiene TODOS dataset de transparencia
	@Path ("transparency/lang/{abbrev}/datasets")
	@GET
	//@Produces (MediaType.APPLICATION_JSON)
	@Produces ("application/x-javascript")
	public String getAllTransp(@PathParam("dbname") String dbname,@PathParam("abbrev") String lan, @QueryParam("callback") String callback) throws IOException
	{
		
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
				
			CAD miCad = new CAD(dbname);
			List<DatasetTransp> objeto = miCad.getAllDatasetsTransp();
			
			if(callback == "")
				return gson.toJson(objeto);
			else
				return callback +'('+ gson.toJson(objeto) + ')';
				
			}
			else
				return "La api no opera con "+ dbname + "todavia.";
			
			
	}
	
	////Devuelve la Categoria de transparencia que con el "idCatTransp" en el idioma "abbreviationLanguage"
	//TRADUCCION INGLES OK
	@Path ("transparency/lang/{abbrev}/datasets/categories/{id}")
	@GET
	@Produces ("application/x-javascript")
	public String getCategoryById(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			//List<Category> listaCategories= miCad.getCategories();
			//List<CategoryTransp> listaCategoriesTransp=miCad.getSubcategoriesTransp(id,lan);
			CategoryTransp catTransp=miCad.getCategoryByID(id, lan);
			
			if(callback == "")
				return gson.toJson(catTransp);
			else
				return callback +'('+ gson.toJson(catTransp) + ')';
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	//Devuelve la lista de ids de Categorias de un Dataset "idDatasetTransp" (se supone que cada dataset solo tiene una categoria, pero la BD permite varias por esto devuelvevos una lista, de momento siempre tendrá un elemento)
	@Path ("transparency/lang/{abbrev}/datasets/{id}/categories")
	@GET
	@Produces ("application/x-javascript")
	public String getIdCategoriesByIdDataset(@PathParam("dbname") String dbname,@PathParam("id") int id,@PathParam("abbrev") String lan,@QueryParam("callback") String callback)
	{
		if(dbname.equals("oracle") || dbname.equals("mysql"))// || dbname.equals("postgresql"))
		{
			CAD miCad = new CAD(dbname);
			
			List<Integer> listIdsSubcategories = miCad.getIdCategoriesByIdDataset(id);
			
			if(callback == "")
				return gson.toJson(listIdsSubcategories);
			else
				return callback +'('+ gson.toJson(listIdsSubcategories) + ')';
			
		}
		else
			return "La api no opera con "+ dbname + "todavia.";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}//fin clase
