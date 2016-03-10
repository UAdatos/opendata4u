package es.ua.datos.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/documentacion")
public class RestDocumentacion {
	
	//atributos
	
	
	public RestDocumentacion(){};
	
	@GET  
	@Produces(MediaType.TEXT_HTML) 
	public String getDocu()
	{
		return "<HTML><HEAD></HEAD><BODY><H1>Documentacion uapi - OpenData4u</H1><p>GET <a href=http://localhost:8080/openData4u/uapi/oracle/datasets>todos</a></p></BODY><FOOTER></FOOTER </HTML>";
	}
	
	
	
	
	
	

}
