package es.ua.datos.cad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLClassLoader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;

import es.ua.datos.model.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.glassfish.jersey.message.internal.XmlCollectionJaxbProvider.App;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.FetchMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.stat.Statistics;
import org.omg.CORBA.Current;

import com.mchange.lang.ArrayUtils;
import com.mchange.v2.log.MLevel;

import es.ua.datos.cad.HibernateUtil;
import es.ua.datos.model.*;



public class CAD {

	private static SessionFactory sessionFactory = null;
	
	public static Statistics stats;
	
	private Session session = null;
	
	
	
	public CAD(String db){
		
		try 
        {         	 
            if(sessionFactory == null || sessionFactory.isClosed()){ 
            	
            	            	
            	Configuration configuration = new Configuration();
            	configuration.configure("hibernate_"+db+".cfg.xml");
            	ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            	sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
            
            	
            }
            /*else{
            	sessionFactory.getCurrentSession();
            	System.out.println("GET CURRENT SESSION (CAD)");
            }*/
            
            /*if(session.isOpen())
            	session = sessionFactory.getCurrentSession();
            else
            	session = sessionFactory.openSession();
           */
            //return sessionFactory;
            
        } catch (HibernateException he) 
        { 
           System.err.println("Ocurrio un error en la inicializacion de la SessionFactory: " + he); 
            throw new ExceptionInInitializerError(he); 
        }
		finally{
			
			 //Poner en el finally
            stats = sessionFactory.getStatistics();
        	stats.setStatisticsEnabled(true);
        	writeStatisticsLog(stats);
        	
		}
		
	}
	
	//Escribe estadisticas de las queries de Hibernate
	public void writeStatisticsLog(Statistics stats){
		
		// Number of connection requests. Note that this number represents 
		// the number of times Hibernate asked for a connection, and 
		// NOT the number of connections (which is determined by your 
		// pooling mechanism).
		Date date = new Date();
		System.out.println("HORA: "+ date);
		System.out.println("getConnectCount(): "+ stats.getConnectCount());
		// Number of flushes done on the session (either by client code or 
		// by hibernate).
		System.out.println("getFlushCount(): "+stats.getFlushCount());
		// The number of completed transactions (failed and successful).
		System.out.println("getTransactionCount(): "+stats.getTransactionCount());
		// The number of transactions completed without failure
		System.out.println("getSuccessfulTransactionCount(): "+stats.getSuccessfulTransactionCount());
		// The number of sessions your code has opened.
		System.out.println("getSessionOpenCount(): "+stats.getSessionOpenCount());
		// The number of sessions your code has closed.
		System.out.println("getSessionCloseCount(): "+stats.getSessionCloseCount());
		// All of the queries that have executed.
		System.out.println("getQueries(): "+stats.getQueries());
		// Total number of queries executed.
		System.out.println("getQueryExecutionCount(): "+stats.getQueryExecutionCount());
		// Time of the slowest query executed.
		System.out.println("getQueryExecutionMaxTime(): "+stats.getQueryExecutionMaxTime());
	}
	
	public static void main(String[] args) throws IOException {
		
		/*Utilities u = new Utilities();
		
		List<String> desc = new ArrayList<String>();
		
		desc.add("matriculas");
		desc.add("de");
		desc.add("los");
		desc.add("alumnos");
		
		u.removeStopWordsTXT(desc);
		*/
		
	}
	
	// ===================== GETS ================================ //
	
	//Return All datasets
	public List<Dataset> getAllDatasets(){
		
		List<Dataset> datasets = null;		
		
		Transaction tx = null;
		
		Session session = null;
		
		
			//session = HibernateUtil.getSessionFactory().openSession(); 
			session = sessionFactory.openSession();		
		
		
	      try{
	         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  datasets = session.createQuery("FROM Dataset d WHERE d.visibility='visible' ORDER BY d.idDataset asc").list();	    	  
	    	  
	    	  
	    	  tx.commit();		
	         
	    	  
	    		 
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		         System.out.println("SESSION: "+session);
		         System.out.println("STATS COUNT CONN: "+ stats.getConnectCount());
		         System.out.println("STATS OpenSession CONN: "+ stats.getSessionOpenCount());
		         System.out.println("STATS GET SUCC TRANS: "+ stats.getSuccessfulTransactionCount());
		         System.out.println("STATS QUERIES: "+ stats.getQueryStatistics("hql"));
		         System.out.println("STATS Session Close: "+ stats.getSessionCloseCount());
		         //System.out.println("SESSION FACTORY: " + stats.);
		         System.out.println("=======================================");
		      }
	      return datasets;  
	}
	
	//Return de last X datasets
	public List<Dataset> getLastXDatasets(int numDatasets){
		
		List<Dataset> datasets = null;		
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();   
		try{
	         
	    	 
		      
	    	  tx = session.beginTransaction();
	    	  Query query = session.createQuery("FROM Dataset d WHERE d.visibility='visible' ORDER BY d.idDataset desc");
	    	  query.setFirstResult(0);
	    	  query.setMaxResults(numDatasets);
	    	  datasets = query.list();	        	  
	         
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		  }
	      finally {
		         session.close(); 
		      }
	      return datasets;  
	}
	
	//Return Datasets by description
	public List<Dataset> getDatasetsByDesc(String desc) throws IOException{
		
		List<Dataset> datasets = null;	
		
		
		//Quitamos las STOP WORDS para hacer la busqueda
		Utilities utilities = new Utilities();
		//TRIM
		desc.trim();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
		
		//System.out.println(splitDescriptionWithoutStopWords.toString());
		
		if(splitDescriptionWithoutStopWords.size() > 0){//Si despues de eliminar las stopwords el size de la lista es 0
			
			//Creamos la QUERY dinamicamente
			String query = "FROM Dataset d WHERE d.visibility='visible' AND (";
			
			for(int i = 0;i < splitDescriptionWithoutStopWords.size(); i++){				
				
				query += "UPPER(d.name) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') or UPPER(d.description) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') ";
				if(i < splitDescriptionWithoutStopWords.size() - 1){
					query += "or ";
				}
			}
			query +=") ORDER BY d.idDataset desc";
			
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
		      try{       
		    	 			      
		    	  tx = session.beginTransaction();
		         
		    	  datasets = session.createQuery(query).list();
		         
		    	  tx.commit();				         
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		      
		}
		else{//Si La lista de palabras a buscar esta vacia 
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
		      try{       
		    	 			      
		    	  tx = session.beginTransaction();
		         
		    	  datasets = session.createQuery("FROM Dataset d WHERE d.visibility='visible' ORDER BY d.idDataset desc").list();	 
		         
		    	  tx.commit();				         
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		}
		
	      return datasets;  
	}
	
	//Return last X Datasets by description
	public List<Dataset> getLastXDatasetsByDesc(String desc, int numDatasets) throws IOException{
		
		List<Dataset> datasets = null;
				
		Transaction tx = null;
		
		
		//Quitamos las STOP WORDS para hacer la busqueda
		Utilities utilities = new Utilities();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
			
		if(splitDescriptionWithoutStopWords.size() > 0){//Si despues de eliminar las stopwords el size de la lista es 0
			
			//Creamos la QUERY dinamicamente
			String query = "FROM Dataset d WHERE d.visibility='visible' AND (";
			
			for(int i = 0;i < splitDescriptionWithoutStopWords.size(); i++){
				
				query += "UPPER(d.name) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') or UPPER(d.description) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') ";
				if(i < splitDescriptionWithoutStopWords.size() - 1){
					query += "or ";
				}
			}
			query +=") ORDER BY d.idDataset desc";
			
			//Session session = HibernateUtil.getSessionFactory().openSession();
			Session session = sessionFactory.openSession();
		      try{       

		    	  

		    	  
			      Query queryHQL = session.createQuery(query);
			      
			      queryHQL.setFirstResult(0);
			      queryHQL.setMaxResults(numDatasets);
		    	  tx = session.beginTransaction();
		         
		    	  datasets = queryHQL.list();
		         
		    	  tx.commit();		
		         
		      
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			  }
		      finally {
			         session.close(); 
			      }
		      
		}
		else//Si La lista de palabras a buscar esta vacia		
		{
						
			//Session session = HibernateUtil.getSessionFactory().openSession();
			Session session = sessionFactory.openSession();	
		      try{       
		    	 			      

			      Query queryHQL = session.createQuery("FROM Dataset d WHERE d.visibility='visible' ORDER BY d.idDataset desc");
			      
			      queryHQL.setFirstResult(0);
			      queryHQL.setMaxResults(numDatasets);
		    	  tx = session.beginTransaction();
		         
		    	  datasets = queryHQL.list();	 
		         
		    	  tx.commit();				         
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		}
			datasets = new ArrayList<Dataset>();
		
		
	     return datasets;
		
	}
	
	//Return dataset by ID
	public Dataset getDatasetByID(int id){
		
		Dataset ds = new Dataset();
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();

	      try{         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  ds = (Dataset) session.createQuery("FROM Dataset d WHERE d.visibility='visible' AND idDataset="+id).uniqueResult();//(Dataset.class,id);
	         
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
		
		return ds;	
		
	}

	//Return datasets by Category
	public List<Dataset> getDatasetsByCat(int idCat){
		
		List<CategoryDataset> c_datasets = null;
		List<Dataset> datasets = new ArrayList<Dataset>();
				
		Transaction tx = null;
		Session session = sessionFactory.openSession();		
		//Session session = HibernateUtil.getSessionFactory().openSession();

		
	      try{
	         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  c_datasets = session.createQuery("FROM CategoryDataset WHERE idCategory="+idCat).list();
	    	  
	    	  tx.commit();		
	         
	    	  //Instanciamos una lista con los datasets de esa categoria
	    	  for(int i = 0;i < c_datasets.size();i++){
	    		  if(c_datasets.get(i).getDataset().getVisibility().equals("visible"))
	    			  datasets.add(c_datasets.get(i).getDataset());
	    	  }
	    	  
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return datasets;  
	}
	
	//Return Categories
	public List<Category> getCategories(){
			
			List<Category> cats = null;
			
			
			Transaction tx = null;
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();

		      try{
		         
		    	 
			      
		    	  tx = session.beginTransaction();
		         
		    	  cats = session.createQuery("FROM Category").list();
		    	  
		    	  tx.commit();		
		         
		    	  //dataset.setCategories(null);
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		      return cats;  
		}

	//Return Categories by ID dataset
	public List<Category> getCategoriesByIdDataset(int idDataset){
		

		List<CategoryDataset> c_datasets = null;
		List<Category> categories = new ArrayList<Category>();
				
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();		
		//Session session = HibernateUtil.getSessionFactory().openSession();

	      try{	         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  c_datasets = session.createQuery("FROM CategoryDataset WHERE idDataset="+idDataset).list();
	    	  
	    	  tx.commit();		
	         
	    	  //Instanciamos una lista con los datasets de esa categoria
	    	  for(int i = 0;i < c_datasets.size();i++){
	    		  categories.add(c_datasets.get(i).getCategory());
	    	  }
	    	  
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return categories; 
	}
	
	//Return related datasets	
	public List<Dataset> getRelatedDatasets(int idDataset){
		
		List<RelatedDataset> r_datasets = null;
		List<Dataset> datasets = new ArrayList<Dataset>();
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();

		
	      try{
	         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  r_datasets = session.createQuery("FROM RelatedDataset WHERE idDataset="+idDataset).list();
	    	  
	    	  tx.commit();		
	         
	    	//Instanciamos una lista con los datasets de esa categoria
	    	  for(int i = 0;i < r_datasets.size();i++){
	    		  if(r_datasets.get(i).getDataset().getVisibility().equals("visible"))
	    			  datasets.add(r_datasets.get(i).getRelated());
	    	  }
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      
	      return datasets;
		
	}
	
	//Return datasets by Category and Desc
	public List<Dataset> getDatasetByCatDesc(int idCat,String desc) throws IOException{
		
		List<Dataset> datasets = new ArrayList<Dataset>();
		
		Transaction tx = null;
		

		
		//Quitamos las STOP WORDS para hacer la busqueda
		Utilities utilities = new Utilities();
		//TRIM
		desc.trim();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
		
		if(splitDescriptionWithoutStopWords.size() > 0){//Si despues de eliminar las stopwords el size de la lista es 0
			
			//Creamos la QUERY dinamicamente
			String query = "Select cd.dataset FROM CategoryDataset cd WHERE cd.idCategory="+idCat+" AND cd.dataset.visibility='visible' AND (";
			
			for(int i = 0;i < splitDescriptionWithoutStopWords.size(); i++){
				
				query += "UPPER(cd.dataset.name) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') or UPPER(cd.dataset.description) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') ";
				if(i < splitDescriptionWithoutStopWords.size() - 1){
					query += "or ";
				}
			}
			query +=") ORDER BY cd.dataset.idDataset desc";
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
		      try{
		         
		    	  Query queryHQL = session.createQuery(query);
			    
		    	  tx = session.beginTransaction();
		         
		    	  datasets = queryHQL.list();
		         
		    	  tx.commit();		
		    	  
		    	  
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		}
		else{
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
		         
				
		    	  Query queryHQL = session.createQuery("Select cd.dataset FROM CategoryDataset cd WHERE cd.idCategory="+idCat+" AND cd.dataset.visibility='visible' AND ORDER BY cd.dataset.idDataset desc");
			    
		    	  tx = session.beginTransaction();
		         
		    	  datasets = queryHQL.list();
		         
		    	  tx.commit();		
		    	  
		    	  
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
			
		}			
			
			
		
		return datasets;
		
	}
	
	//Return Top Datasets
	public List<Dataset> getXTopDatasets(int numDatasets){
		
		List<Dataset> datasets = null;		
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
	      try{        
	    	 
		      
	    	  tx = session.beginTransaction();
	    	  Query query = session.createQuery("FROM Dataset d WHERE d.assessment > 0 AND d.visibility='visible' ORDER BY d.assessment desc");
	    	  query.setFirstResult(0);
	    	  query.setMaxResults(numDatasets);
	    	  datasets = query.list();	        	  
	         
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return datasets;  
	}
	
	//
	//=============== APPLICATIONS ==================
	//
	//Return All Applications
	public List<Application> getAllApplications(){
		
		List<Application> applications = null;	
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//		Session session = HibernateUtil.getSessionFactory().openSession();		
		
	      try{	    	 
		      
	    	  tx = session.beginTransaction();
	      	  
	    	  applications = session.createQuery("FROM Application a ORDER BY a.id desc").list();		    	  
	    	  
	    	  tx.commit();			  
	    		 
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		        	  
		    	  
		      }
	      return applications;  
	}
	
	//Return application by ID	
	public Application getApplicationByID(int id){
		
		Application app = new Application();
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
	      try{         
	    	 
	
	    	  tx = session.beginTransaction();
	         
	    	  app = (Application) session.get(Application.class,id);
	         
	    	  tx.commit();		
	         
		      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
	
	      }
			
	      return app;	
			
		}
	
	//Return ALL application Types
	public List<ApplicationTypes> getApplicationTypes(){
		
		List<ApplicationTypes> appTypes = null;	
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();		
		
	      try{	    	 
		      
	    	  tx = session.beginTransaction();
	      	  
	    	  appTypes = session.createQuery("FROM ApplicationTypes").list();		    	  
	    	  
	    	  tx.commit();			  
	    		 
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		        	  
		    	  
		      }
	      return appTypes;
	}
	
	//Return Application by description
	public List<Application> getApplicationsByDesc(String desc) throws IOException{
		
		List<Application> apps = null;	
		
		
		//Quitamos las STOP WORDS para hacer la busqueda
		Utilities utilities = new Utilities();
		//TRIM
		desc.trim();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
		
		if(splitDescriptionWithoutStopWords.size() > 0){//Si despues de eliminar las stopwords el size de la lista > 0
			
			//Creamos la QUERY dinamicamente
			String query = "FROM Application a WHERE ";
			
			for(int i = 0;i < splitDescriptionWithoutStopWords.size(); i++){				
				
				query += "UPPER(a.name) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') or UPPER(a.description) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') ";
				if(i < splitDescriptionWithoutStopWords.size() - 1){
					query += "or ";
				}
			}
			query +="ORDER BY a.id desc";
			
			
			//Transaccion
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
			
		      try{       
		    	 			      
		    	  tx = session.beginTransaction();
		         
		    	  apps = session.createQuery(query).list();
		         
		    	  tx.commit();				         
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		      
		}
		else{//Si La lista de palabras a buscar esta vacia 
			//Transaccion
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
			
		      try{       
		    	 			      
		    	  tx = session.beginTransaction();
		         
		    	  apps = session.createQuery("FROM Application a ORDER BY a.id desc").list();
		         
		    	  tx.commit();				         
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		}
		
	      return apps;  
	
		
	}
	
	//Return Applications by Type and Description
	public List<Application> getApplicationsByTypeDesc(int type,String desc) throws IOException{
		
		List<Application> apps = null;	
		List<Application> appsFilterTypesDesc = new ArrayList<Application>();
		
		//Quitamos las STOP WORDS para hacer la busqueda
		Utilities utilities = new Utilities();
		//hacemos TRIM para eliminar espacios que sobren
		desc.trim();
		//dividimos las palabras en una lista
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
		
		if(splitDescriptionWithoutStopWords.size() > 0){//Si despues de eliminar las stopwords el size de la lista > 0
			
			//Creamos la QUERY dinamicamente
			String query = "SELECT a FROM Application a WHERE ";//"FROM Application a WHERE ";
			
			for(int i = 0;i < splitDescriptionWithoutStopWords.size(); i++){				
				
				query += "UPPER(a.name) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') or UPPER(a.description) like UPPER('%"+splitDescriptionWithoutStopWords.get(i)+"%') ";
				if(i < splitDescriptionWithoutStopWords.size() - 1){
					query += "or ";
				}
			}
			query +="ORDER BY a.id desc";
			
			
			//Transaccion
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
			
		      try{       
		    	 			      
		    	  tx = session.beginTransaction();
		         
		    	  apps = session.createQuery(query).list();
		    	  
		    	  tx.commit();
		    	  
		    	  
		    	  //====== MIRAR COM HACERLO CON  LA SELECT ============
		    	  //Filtramos el tipo una vez tenemos las apps filtradas por descripcion
		    	  for(int i=0;i<apps.size();i++)
		    		  for(int j=0;j < apps.get(i).getResourcesApps().size();j++)
		    			  if(apps.get(i).getResourcesApps().get(j).getIdTypeApplication() == type){
		    				  appsFilterTypesDesc.add(apps.get(i));
		    				  break;
		    			  }
		    	 
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         session.close(); 
			      }
		      
		}
		else{//Si La lista de palabras a buscar esta vacia 
			apps = new ArrayList<Application>();
			
			//Transaccion
			Transaction tx = null;
			
			Session session = sessionFactory.openSession();
			//Session session = HibernateUtil.getSessionFactory().openSession();
			
			try{
				
				tx = session.beginTransaction();
		         
				 appsFilterTypesDesc = session.createQuery("FROM Application a ORDER BY a.id desc").list();
		    	  
		    	  tx.commit();
		    	  
			}catch(HibernateException e){
				if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
			}
			finally{
				session.close(); 
			}
			 
		}
		
	      return appsFilterTypesDesc;  
		
	}

	//Return Applications by Type
	public List<Application> getApplicationsByType(int type){
		
		List<Application> applications = null;	
		List<Application> appsGroupingBy = new ArrayList<Application>();
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();			
		
	      try{	    	 
		      
	    	  tx = session.beginTransaction();
	      	  
	    	  applications = session.createQuery("SELECT a FROM Application a JOIN a.resourcesApps ra WHERE ra.idTypeApplication="+ type +" ORDER BY a.id desc").list();		    	  
	    	  
	    	  tx.commit();			  
	    		 
	    	  for(int i=0;i<applications.size();i++)		    		  
	    		  if(!appsGroupingBy.contains(applications.get(i)))
	    				  appsGroupingBy.add(applications.get(i));
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		        	  
		    	  
		      }
	      return appsGroupingBy; 
		
	} 
	
	public List<Application> getXTopApplications(int numApplications){
		
		List<Application> apps = null;		
		
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
	      try{        
	    	 
		      
	    	  tx = session.beginTransaction();
	    	  Query query = session.createQuery("FROM Application a WHERE a.assessment > 0 ORDER BY a.assessment desc");
	    	  query.setFirstResult(0);
	    	  query.setMaxResults(numApplications);
	    	  apps = query.list();	        	  
	         
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return apps;  
	}
	
	public List<Application> getLastXApplications(int numApplications){
		
		List<Application> apps = null;		
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{
	         
	    	 
		      
	    	  tx = session.beginTransaction();
	    	  Query query = session.createQuery("FROM Application a ORDER BY a.id desc");
	    	  query.setFirstResult(0);
	    	  query.setMaxResults(numApplications);
	    	  apps = query.list();	        	  
	         
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return apps;  
	}
	
	public List<Dataset> getDatasetsUsedByApp(int idApplication){
		
		List<Dataset> datasets = null;
						
		Transaction tx = null;
		
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
	      try{	         
	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  datasets = session.createQuery("SELECT ad.dataset FROM ApplicationDataset ad WHERE ad.idApplication="+idApplication).list();
	    	  
	    	  tx.commit();		
	         
	    	  //Instanciamos una lista con los datasets de esa categoria
//	    	  for(int i = 0;i < c_datasets.size();i++){
//	    		  categories.add(c_datasets.get(i).getCategory());
//	    	  }
	    	  
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	      return datasets;
		
	}
	
	
	// ===================== SETS ================================ //
	
	public float setVoteToDataset(int idDataset,int vote){
		
		Dataset ds = new Dataset();
		int id = 0;
		
		float average = 0;
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{         	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  ds = (Dataset) session.get(Dataset.class,idDataset);
	    	  
	    	  int votes = ds.getNumVotes();
    		  int score = ds.getScore();    		  
	    	  
	    	  //Comprobamos si SCORE es 0
	    	  if(score == 0){//No sumamos en numvotes (PARA EVITAR LA DIVISION POR 0)
	    		  	
	    		  	ds.setScore(score+vote);//Sumamos el voto
	    	  		average = (float)(score+vote)/(float)(votes);
	    	  }
	    	  else{
	    		  ds.setScore(score+vote);//Sumamos el voto
	    		  ds.setNumVotes(votes+1);//Sumamos 1 a los votos	
	    		  average = (float)(score+vote)/(float)(votes+1);
	    		  
	    	  }    	  
	    	 
	    	  
    		  ds.setAssessment(average);
    		  
	    	  session.update(ds);
	    	  
	    	  id = ds.getIdDataset();
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	
		return average;
	}
	
	
	public float setVoteToApplication(int idApplication,int vote){
		
		Application app = new Application();
		int id = 0;
		float average = 0;
		
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		//Session session = HibernateUtil.getSessionFactory().openSession();
		
		try{         	    	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  app = (Application) session.get(Application.class,idApplication);
	    	  
	    	  //Comprobamos si SCORE es 0
	    	  if(app.getScore() == 0)//No sumamos en numvotes (PARA EVITAR LA DIVISION POR 0)
	    		  app.setScore(app.getScore()+vote);//Sumamos el voto
	    	  else{
	    		  app.setScore(app.getScore()+vote);//Sumamos el voto
	    		  app.setNumVotes(app.getNumVotes()+1);//Sumamos 1 a los votos		    		  
	    	  }
	    	 
	    	  //Sacamos la media
    		  int votes = app.getNumVotes();
    		  int score = app.getScore();
    		  average = (float)score/(float)votes;
    		  app.setAssessment(average);
    		  
	    	  session.update(app);
	    	  
	    	  id = app.getId();
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		      }
	
		return average;
	}
	
	
	//////////// TRANSPARENCIA ////////////////////
	
	//Devuelve el Dataset de transparencia que con el "idDatasetTransp" en el idioma "abbreviationLanguage" FuNCIONA NUEVO POOL
	public DatasetTransp getDatasetTranspByID(int idDatasetTransp, String abbreviationLanguage){
			
			DatasetTransp datasetTransp = null;		
			
			Transaction tx = null;
			
			//Session session = null;
			
			/*if(this.session.isOpen())
				this.session = sessionFactory.getCurrentSession();
			else*/
				//this.session = sessionFactory.openSession();	
			this.session = sessionFactory.getCurrentSession();
			
			try{
	        	 
			      
		    	  tx = this.session.beginTransaction();
		    	  
		    	  Criteria criteria = this.session.createCriteria(DatasetTransp.class).
		    			  add(Restrictions.eq("idDatasetTransp",idDatasetTransp)).
		    			  add(Restrictions.eq("visibility","visible"));
		    	  
		    	  datasetTransp = (DatasetTransp) criteria.uniqueResult();
		    	  
		    	  //Hacemos commit para que los cambios sobre el objeto no sean persistentes
		    	  tx.commit();
		    	  
		    	  List<String> fieldsDataset = Arrays.asList("NAME_DATASET_TRANSP","DESCRIPTION","PUBLISHER");
		    	  
		    	  int language = 0;
		    	  
		    	  
		    	  //Comprobamos el idioma
		    	  if(abbreviationLanguage.equals("va") && datasetTransp != null)
		    	  {
		    		  language = 1;// Valencià	    		  
		    		  
		    		  datasetTransp = (DatasetTransp) getTranslation(datasetTransp, language,"DATASETS_TRANSP",fieldsDataset);
		    	  }
		    	  else if(abbreviationLanguage.equals("en") && datasetTransp != null){
		    		  
		    		  language = 2; //Ingles
		    				  
		    		  datasetTransp = (DatasetTransp) getTranslation(datasetTransp, language,"DATASETS_TRANSP",fieldsDataset);
		    		  
		    	  }
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      
			  }
		      finally {
			         //this.session.close(); 
			         //System.out.println("SESSION CLOSE === getDatasetTranspByID");
			      }
		      
		    return datasetTransp;
		}
	
	//Devuelve todos los datasets que contengan la descripción "desc" en el idioma "abbreviationLanguage" -- FuNCIONA NUEVO POOL
	public List<DatasetTransp> getDatasetsTranspByDesc(String abbreviationLanguage,String desc){
		
		List<DatasetTransp> datasetsTransp = new ArrayList<DatasetTransp>();
		
		Utilities utilities = new Utilities();
		
		desc.trim();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = null;
		if(abbreviationLanguage.equals("es"))
			splitDescriptionWithoutStopWords = utilities.removeStopWordsTXT(splitDescription);
		else if(abbreviationLanguage.equals("va"))
			splitDescriptionWithoutStopWords = utilities.removeStopWordsTXTVa(splitDescription);
		else if(abbreviationLanguage.equals("en"))
			splitDescriptionWithoutStopWords = utilities.removeStopWordsTXTEn(splitDescription);
		
		Transaction tx = null;
		
		//Session session = null;
		
		/*if(this.session.isOpen())
			this.session = sessionFactory.getCurrentSession();
		else*/
			//this.session = sessionFactory.openSession();	
		this.session = sessionFactory.getCurrentSession();
		
	      try{
	    	  
	    	  
	    	  
	    	  if(abbreviationLanguage.equals("es")){	         	 
		      
	    		  tx = this.session.beginTransaction();
	    		  
	    		  Criteria criteria = this.session.createCriteria(DatasetTransp.class);
	    		  
	    		  for(String str : splitDescriptionWithoutStopWords){
	    			  System.out.println(str);
	    			  Criterion name = Restrictions.ilike("nameDatasetTransp","%"+str+"%");
	    			  Criterion description = Restrictions.ilike("description","%"+str+"%");
	    			  Criterion publisher = Restrictions.ilike("publisher","%"+str+"%");
	    			  //LogicalExpression orRestriction = Restrictions.or(description, name,publisher);
	    			  //criteria.add(orRestriction);
	    			  criteria.add(Restrictions.or(Restrictions.or(name,description),publisher));
	    		  }
	    		
		    	  Criterion visibility = Restrictions.eq("visibility", "visible");
		    	 		    	  
		    	  criteria.add(visibility);    	  
		    	//Sin duplicados
					criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
					
		    	  datasetsTransp = criteria.list();	   
		    	  
		    	  for(DatasetTransp ds : datasetsTransp)
		    		  System.out.println(ds.getIdDatasetTransp());
		    	  
		    	  tx.commit();
	    	  
	    	  }
	    	  else {// Si no es en castellano traducimos	   
	    		  
	    		  datasetsTransp = getTranslationDatasetTranspByDesc(abbreviationLanguage,desc);
	    		  
	    		  
	    	  }
	    	  
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
	    	  //Si esta abierta la cerramos
	    	  //if(this.session.isOpen())
		        // this.session.close();System.out.println("SESSION CLOSE === getDatasetsTranspByDesc");
	    	  
		         
		      }
	      
	      
	      return datasetsTransp;  
	}
	
	//Devuelve la lista de DatasetsTransp de la subcategoría "idCat" en el "language"  -- FuNCIONA NUEVO POOL
	public List<DatasetTransp> getDatasetsTranspByCategory(int idCat,String language){
			
			List<DatasetTransp> listDatasetTransp = new ArrayList<DatasetTransp>();
			List<CategoryDatasetTransp> listCatDatasetTransp = new ArrayList<CategoryDatasetTransp>();
			
			Transaction tx = null;
			
			//Session session = null;
			
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
				
				tx = session.beginTransaction();
				
				Criteria criteria = session.createCriteria(CategoryDatasetTransp.class).
						add(Restrictions.eq("idCategoryTransp", idCat));	
				//Sin duplicados
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				
				listCatDatasetTransp = criteria.list();
						
				tx.commit();
				
				for(CategoryDatasetTransp listCatDT : listCatDatasetTransp){
					//System.out.println(listCatDT.getIdDatasetTransp()+" "+listCatDT.getIdCategoryTransp());
					if(language.equals("es"))
						listDatasetTransp.add(listCatDT.getDatasetTransp());					
					else
						listDatasetTransp.add(getDatasetTranspByID(listCatDT.getIdDatasetTransp(), language));					
				}	
			
			 }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      }
		      finally {
			         //session.close(); 
			         System.out.println("FINALLY  === getDatasetTranspByCategory");
			      }
			
			return listDatasetTransp;
			
		}
	
	
	//Devuelve una lista de DatasetTransp de la categoría "idCat" que coincidan con la "desc" y en el "language" --FuNCIONA NUEVO POOL
	public List<DatasetTransp> getDatasetsTranspByCategoryDesc(int idCat,String language,String desc){
				
				List<DatasetTransp> listDatasetsTranspByDesc = getDatasetsTranspByDesc(language, desc);
				
				List<Integer> listIdsDatasetsByCategory = new ArrayList<Integer>();
				
				List<CategoryTransp> listSubCategoriesTransp = getSubcategoriesTransp(idCat, language);			
				
				//Recogemos las subcategorías de la categoría idCat, la recorremos y añadimos a la lista de datasetsIdsByCategory
				for(CategoryTransp subCat : listSubCategoriesTransp){				
					
					listIdsDatasetsByCategory.addAll(getIdsDatasetsTranspByCategory(subCat.getIdCategoryTransp(), language));
				}
				
				//Esta lista contendrá las coincidencias entre las listas de listDatasetsTranspbyDesc y listDatsetsTranspByCategory 
				List<DatasetTransp> listDatasetsTranspByCatAndDesc = new ArrayList<DatasetTransp>();
				
				//REcorremos las lista de DatasetsbyDesc y la lista de Ids de Categorias y creamos una con un merge de las dos
				for(DatasetTransp dtTransDesc : listDatasetsTranspByDesc)			
					for(Integer idDTranspbyCat : listIdsDatasetsByCategory)			
						if(dtTransDesc.getIdDatasetTransp() == idDTranspbyCat)
							listDatasetsTranspByCatAndDesc.add(dtTransDesc);
					
				
				return listDatasetsTranspByCatAndDesc;
			}
		
	//Devuelve una lista ids de datasets (datos.ua.es) relacionados con el dataset de Transparencia de la "idDatasetTransp" --FuNCIONA NUEVO POOL
	public List<Integer> getDatasetRelated(int idDatasetTransp){
			
			List<Integer> listIdDatasetsRelated = new ArrayList<Integer>();
			List<DatasetDatasetTransp> datasetsRelated = new ArrayList<DatasetDatasetTransp>();
			
			Transaction tx = null;
			//Session session = null;
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
				
				tx = this.session.beginTransaction();
				
				Criteria criteria = session.createCriteria(DatasetDatasetTransp.class).
						add(Restrictions.eq("idDatasetTransp",idDatasetTransp));
				
				datasetsRelated = criteria.list();
				
				tx.commit();
				
				for(DatasetDatasetTransp ddt : datasetsRelated)
					listIdDatasetsRelated.add(ddt.getIdDataset());
				
			}
			catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      
			}
			finally {
		         
				//session.close(); 
		         
		    }
			return listIdDatasetsRelated;
		}
	
	//Devuelve las categorías principales (las del nivel superior) en el lenguaje "lan" --FuNCIONA NUEVO POOL
	public List<CategoryTransp> getMainCategoriesTransp(String lan){
			
			List<CategoryTransp> listCategories = new ArrayList<CategoryTransp>();
			
			Transaction tx = null;
			//Session session = null;
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
				
				tx = session.beginTransaction();
				
				Criteria criteria = session.createCriteria(CategoryTransp.class);
				criteria.addOrder(Order.asc("priority"));
				criteria.add(Restrictions.isNull("idHigherCategory"));
				
				listCategories=criteria.list();
				
				tx.commit();
				
				List<CategoryTransp> listCategoriesTrad = new ArrayList<CategoryTransp>();			
				List<String> fieldsCategory = Arrays.asList("NAME_CATEGORY_TRANSP","DESCRIPTION");
				int idLang = 0;
				
				if(lan.equals("es")){
					idLang = 0;
					listCategoriesTrad = listCategories;
				}
				else if(lan.equals("va")){
					idLang = 1;
					for(CategoryTransp cat : listCategories)
						listCategoriesTrad.add((CategoryTransp)getTranslation(cat, idLang, "CATEGORIES_TRANSP", fieldsCategory));
				}
				else if(lan.equals("en")){
					idLang = 2;
					for(CategoryTransp cat : listCategories)
						listCategoriesTrad.add((CategoryTransp)getTranslation(cat, idLang, "CATEGORIES_TRANSP", fieldsCategory));
				}
					
			}
			catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      
			}
			finally {
		         //session.close(); 
		         //System.out.println("SESSION CLOSE === getResourcesTranspByIDDataset");
		    }
			
			return listCategories;

		}
	
	//Devuelve las subcategorías de la categoría "idCat" en el lenguaje "lan" --FuNCIONA NUEVO POOL
	public List<CategoryTransp> getSubcategoriesTransp(int idCat, String lan){
			
			List<CategoryTransp> listCategories=null;
			
			Transaction tx = null;
			//Session session = null;
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
				
				tx = session.beginTransaction();
				
				Criteria criteria = session.createCriteria(CategoryTransp.class);
				criteria.addOrder(Order.asc("priority"));
				criteria.add(Restrictions.eq("idHigherCategory", new Integer(idCat)));
				
				listCategories=criteria.list();
				
				tx.commit();
				
				List<CategoryTransp> listCategoriesTrad = new ArrayList<CategoryTransp>();			
				List<String> fieldsCategory = Arrays.asList("NAME_CATEGORY","DESCRIPTION");
				int idLang = 0;
				
				if(lan.equals("es")){
					idLang = 0;
					listCategoriesTrad = listCategories;
				}
				else if(lan.equals("va")){
					idLang = 1;
					for(CategoryTransp cat : listCategories)
						listCategoriesTrad.add((CategoryTransp)getTranslation(cat, idLang, "CATEGORIES_TRANSP", fieldsCategory));
				}
				else {
					idLang = 2;
					for(CategoryTransp cat : listCategories)
					listCategoriesTrad.add((CategoryTransp)getTranslation(cat, idLang, "CATEGORIES_TRANSP", fieldsCategory));
				}
			}
			catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      
			}
			finally {
		         //session.close(); 
		         //System.out.println("SESSION CLOSE === getResourcesTranspByIDDataset");
		    }
			
			return listCategories;
		}

	//Devuelve todos los datasets de transparencia (NO SE LLAMA DESDE NINGUN SERVICIO REST) --FuNCIONA NUEVO POOL
	public List<DatasetTransp> getAllDatasetsTransp(){
		
		List<DatasetTransp> datasetsTransp = new ArrayList<DatasetTransp>();		
		
		Transaction tx = null;
		
		//Session session = null;
		
		//session = sessionFactory.openSession();
		this.session = sessionFactory.getCurrentSession();
		
		
	      try{
	         	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  Criteria criteria = session.createCriteria(DatasetTransp.class).
	    			  add(Restrictions.eq("visibility","visible")).
	    			  addOrder(Order.asc("idDatasetTransp"));
	    	  datasetsTransp = criteria.list();//session.createQuery("FROM DatasetTransp d WHERE d.visibility='visible' ORDER BY d.idDatasetTransp asc").list();	    	  
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         //session.close(); 
		         //System.out.println("SESSION CLOSE === getAllDatasetsTransp");
		      }
	      
	      
	      return datasetsTransp;  
	}
	
	//Devuelve la Categoria de transparencia que con el "idCatTransp" en el idioma "abbreviationLanguage" --FuNCIONA NUEVO POOL
	public CategoryTransp getCategoryByID(int idCatTransp,String abbreviationLanguage){
			
			CategoryTransp categoryTransp = null;
			
			
			Transaction tx = null;
			
			//Session session = null;
			
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
	        	 
			      
		    	  tx = session.beginTransaction();
		    	  
		    	  Criteria criteria = session.createCriteria(CategoryTransp.class).
		    			  add(Restrictions.eq("idCategoryTransp",idCatTransp));
		    	  
		    	  categoryTransp = (CategoryTransp) criteria.uniqueResult();
		    	  
		    	  //Hacemos commit para que los cambios sobre el objeto no sean persistentes
		    	  tx.commit();
		    	  
		    	  List<String> fieldsCategory = Arrays.asList("NAME_CATEGORY_TRANSP","DESCRIPTION","PUBLISHER");
		    	  
		    	  int language = 0;	    	  
		    	  
		    	  //Comprobamos el idioma
		    	  if(abbreviationLanguage.equals("va") && categoryTransp != null)
		    	  {
		    		  language = 1;// Valencià	    		  
		    		  
		    		  categoryTransp = (CategoryTransp) getTranslation(categoryTransp, language,"CATEGORIES_TRANSP",fieldsCategory);
		    	  }
		    	  else if(abbreviationLanguage.equals("en") && categoryTransp != null){
		    		  
		    		  language = 2;// Ingles    		  
		    		  
		    		  categoryTransp = (CategoryTransp) getTranslation(categoryTransp, language,"CATEGORIES_TRANSP",fieldsCategory);
		    		  
		    	  }
		      }
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      
			  }
		      finally {
			         //session.close(); 
			         //System.out.println("SESSION CLOSE === getCategoryByID");
			      }
		      
		    
			
			return categoryTransp;
		}
	
	//Devuelve la lista de ids de Categorias de un Dataset "idDatasetTransp" (se supone que cada dataset solo tiene una categoria, pero la BD permite varias por esto devuelvevos una lista, de momento siempre tendrá un elemento)
	public List<Integer> getIdCategoriesByIdDataset(int idDatasetTransp){
			
			List<Integer> listIdsSubcategories = new ArrayList<Integer>();
			List<CategoryDatasetTransp> categoriesDataset = new ArrayList<CategoryDatasetTransp>();
			
			Transaction tx = null;
			
			//Session session = null;
			
			//session = sessionFactory.openSession();
			this.session = sessionFactory.getCurrentSession();
			
			try{
	        	 
				tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(CategoryDatasetTransp.class).
						add(Restrictions.eq("idDatasetTransp",idDatasetTransp));
				
				//Sin duplicados
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				categoriesDataset = criteria.list();
				
				tx.commit();
				
				for(CategoryDatasetTransp cdt : categoriesDataset)
					listIdsSubcategories.add(cdt.getIdCategoryTransp());
			
			}
		      catch (HibernateException e) {
			         if (tx!=null) tx.rollback();
			         e.printStackTrace(); 
			      
			  }
		      finally {
			         //session.close(); 
			         //System.out.println("SESSION CLOSE === getIdCategoriesByIdDataset");
			      }
		      
			return  listIdsSubcategories;
		}
		
	
	//FUNCIONES QUE UTILIZAN LAS FUNCIONES PRINCIPALES
		
	//Devuelve los Resources del dataset de transparencia que se pasa como parámetro (NO SE LLAMA DESDE NINGUN SERVICIO REST)
	public List<ResourceTransp> getResourcesTranspByIDDataset(int idDatasetTransp){
		
		List<ResourceTransp> resourcesTransp = new ArrayList<ResourceTransp>();
		
		Transaction tx = null;
		
		Session session = null;
		
		session = sessionFactory.openSession();
		this.session = sessionFactory.getCurrentSession();
		
		try{
        	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  //resourcesTransp
	    	  String hql = "SELECT d.resourcesTransp FROM DatasetTransp d WHERE d.visibility='visible' AND d.idDatasetTransp= :idDatasetTransp";
	    	  
	       	  Query query = session.createQuery(hql);
	    	  query.setParameter("idDatasetTransp",idDatasetTransp);
	    	  resourcesTransp = query.list();
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		         System.out.println("SESSION CLOSE === getResourcesTranspByIDDataset");
		      }
	      
	      
	      return resourcesTransp;
	}
	
	

	
	//Devuelve el Lenguaje de la "abbreviationLanguage"(NO SE LLAMA DESDE NINGUN SERVICIO REST)
	public Language getAllLanguageTranslations(String abbreviationLanguage){
		
		Language lang = null;	
		
		Transaction tx = null;
		
		Session session = null;
		
		session = sessionFactory.openSession();		
		
	      try{
	         	 
		      
	    	  tx = session.beginTransaction();
	         
	    	  Criteria criteria = session.createCriteria(Language.class).
	    			  add(Restrictions.eq("abbreviation",abbreviationLanguage));
	    			 
	    	  
	    	  lang = (Language) criteria.uniqueResult();
	    	  //translations = criteria.list();
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		         System.out.println("SESSION CLOSE === getAllLanguageTranslations");
		      }
	      
	      
	      return lang;
	      
		
	}

	//Traduce un Objeto(DatasetTransp o Category) al "lenguaje" que se pide (Función que se reutiliza desde otras)
	public Object getTranslation(Object object,int language, String tableName, List<String> fields){
		
		DatasetTransp datasetTransp = null;
		
		CategoryTransp categoryTransp = null;
		
		ResourceTransp resourceTransp = null;
		
		Object objectTranslated = null;
		
		List<Translation> translations = new ArrayList<Translation>();
		
		Transaction tx = null;
		
		/*Session session = null;
		
		if(sess.isOpen())
			session = sessionFactory.getCurrentSession();
		else
			session = sessionFactory.openSession();
		*/
		this.session = sessionFactory.getCurrentSession();
		
		try{
        	 
		      
	    	  tx = session.beginTransaction();
		
		
			//Cast del objeto que vamos a traducir
			if(tableName.equals("DATASETS_TRANSP")){
				
				datasetTransp = (DatasetTransp) object;
			
				Criteria criteria = session.createCriteria(Translation.class).
						add(Restrictions.eq("idLanguage",language)).
						add(Restrictions.eq("idObject", datasetTransp.getIdDatasetTransp())).
						add(Restrictions.eq("tableName",tableName));
								
				translations = (List<Translation>) criteria.list();
				
				//Hacemos commit para que los cambios sobre el objeto no sean persistentes === MIRAR MÉTODO PARA NO HACER PERSISTENTE EL OBJETO MODIFICADO
				tx.commit();		
				
				//AQUI TRADUCIMOS LOS RECURSOS DEL DATASET
				List<ResourceTransp> resourcesTransp = new ArrayList<ResourceTransp>();
				List<String> fieldsRescource = Arrays.asList("NAME","URL");
				
				//Recorremos los resources y los traducimos
				for(ResourceTransp resTransp : datasetTransp.getResourcesTransp())					
					resourcesTransp.add((ResourceTransp) getTranslation(resTransp, language,"RESOURCES_TRANSP",fieldsRescource));
					
				//modificamos la lista de recursos del dataset por los recursos traducidos
				datasetTransp.setResourcesTransp(resourcesTransp);
				
				//Traduciomos los campos del dat
				for(Translation trans : translations)					
					if(trans.getFieldName().equals("NAME_DATASET_TRANSP"))
						datasetTransp.setNameDatasetTransp(trans.getTranslation());
					else if(trans.getFieldName().equals("DESCRIPTION"))
						datasetTransp.setDescription(trans.getTranslation());
					else if(trans.getFieldName().equals("PUBLISHER"))
						datasetTransp.setPublisher(trans.getTranslation());
					else if(trans.getFieldName().equals("LICENSE"))
						datasetTransp.setLicense(trans.getTranslation());
					else if(trans.getFieldName().equals("UPDATE_FRECUENCY"))
						datasetTransp.setUpdateFrecuency(trans.getTranslation());
					else if(trans.getFieldName().equals("TEMPORAL_COVERAGE"))
						datasetTransp.setTemporalCoverage(trans.getTranslation());
				
				
				objectTranslated = datasetTransp;
			}
			else if(tableName.equals("CATEGORIES_TRANSP")){
				
				categoryTransp = (CategoryTransp) object;
				
				
				Criteria criteria = session.createCriteria(Translation.class).
						add(Restrictions.eq("idLanguage",language)).
						add(Restrictions.eq("idObject", categoryTransp.getIdCategoryTransp())).
						add(Restrictions.eq("tableName",tableName));
								
				translations = (List<Translation>) criteria.list();
				
				//Hacemos commit para que los cambios sobre el objeto no sean persistentes === MIRAR MÉTODO PARA NO HACER PERSISTENTE EL OBJETO MODIFICADO
				tx.commit();				
				
				for(Translation trans : translations)					
					if(trans.getFieldName().equals("NAME_CATEGORY_TRANSP"))
						categoryTransp.setNameCategoryTransp(trans.getTranslation());
					else if(trans.getFieldName().equals("DESCRIPTION"))
						categoryTransp.setDescription(trans.getTranslation());	
				
				objectTranslated = categoryTransp;
			}
			else if(tableName.equals("RESOURCES_TRANSP")){
				
				resourceTransp = (ResourceTransp) object;
				
				Criteria criteria = session.createCriteria(Translation.class).
						add(Restrictions.eq("idLanguage",language)).
						add(Restrictions.eq("idObject",resourceTransp.getIdResourceTransp())).
						add(Restrictions.eq("tableName",tableName));
				
				translations = (List<Translation>) criteria.list();
				
				tx.commit();
				
				for(Translation trans : translations)
					if(trans.getFieldName().equals("NAME"))
						resourceTransp.setName(trans.getTranslation());
					else if(trans.getFieldName().equals("URL"))
						resourceTransp.setUrl(trans.getTranslation());
				
				objectTranslated = resourceTransp;
				
			}
			
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         //session.close(); 
		         //System.out.println("SESSION CLOSE === getTranslation");
		      }
	 		
		
		return objectTranslated;
	}
	
	/*public List<ResourceTransp> getTranslationResourcesDatasetTransp(List<ResourceTransp> resources, int language){
		
		
	}*/
	
	//Devuelve una lista de DatasetsTransp traducidos al "language" que coincidan con la "desc"(NO SE LLAMA DESDE NINGUN SERVICIO REST)
	public List<DatasetTransp> getTranslationDatasetTranspByDesc(String language, String desc){
		
		List<DatasetTransp> listDsConT = new ArrayList<DatasetTransp>();
		
		List<Integer> IdsDatasetTransp = new ArrayList<Integer>();
		
		List<Translation> translations = new ArrayList<Translation>();
		
		String tableName = "DATASETS_TRANSP";
		
		int idLang = 0;
		if(language.equals("va"))
			 idLang = 1;
		else if(language.equals("en"))
			 idLang = 2;
		
		translations = getTranslationsByDesc(idLang, desc, tableName);
	    	  
		DatasetTransp dsT = null;
		
		for(Translation trans : translations){
	    	
			dsT = getDatasetTranspByID(trans.getIdObject(), "es"); //Nos devolverá el dataset sin traducción
			
			if(!IdsDatasetTransp.contains(dsT.getIdDatasetTransp())){
				
				IdsDatasetTransp.add(dsT.getIdDatasetTransp());//Guardamos la lista de ids de datasets que traduciremos
				
			}
				
		}
				
		for(Integer idDT : IdsDatasetTransp){
		
			listDsConT.add(getDatasetTranspByID(idDT, language));//Utilizamos la función de traducción de Datasets
			
		}
		
		
		return listDsConT;
	}
	
	
	
	//Devuelve una lista de IdsDatasetsTransp de la Categoría idCat
	public List<Integer> getIdsDatasetsTranspByCategory(int idCat,String language){
		
		List<Integer> listIdsDatasetTransp = new ArrayList<Integer>();
		List<CategoryDatasetTransp> listCatDatasetTransp = new ArrayList<CategoryDatasetTransp>();
		
		Transaction tx = null;
		
		//Session session = null;
		
		//session = sessionFactory.openSession();
		this.session = sessionFactory.getCurrentSession();
		
		try{
			
			tx = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(CategoryDatasetTransp.class).
					add(Restrictions.eq("idCategoryTransp", idCat));	
			
			//Sin duplicados
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			listCatDatasetTransp = criteria.list();
					
			
			tx.commit();
			
			for(CategoryDatasetTransp catDT : listCatDatasetTransp){
				
				listIdsDatasetTransp.add(catDT.getIdDatasetTransp());					
					
			}
			
		
		 }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         //session.close(); 
		         //System.out.println("SESSION CLOSE === getIdsDatasetsTranspByCategory");
		      }
		
		return listIdsDatasetTransp;
		
	}
	
	
	//Devuelve una lista de DatasetTransp de la subcategoría "idCat" que coincidan con la "desc" y en el "language"
	public List<DatasetTransp> getDatasetsTranspBySubCategoryDesc(int idCat,String language,String desc){
		
		List<DatasetTransp> listDatasetsTranspByDesc = getDatasetsTranspByDesc(language, desc);
		
		List<DatasetTransp> listDatasetsTranspByCat = getDatasetsTranspByCategory(idCat, language);
		
		List<DatasetTransp> listDatasetsTranspByCatAndDesc = new ArrayList<DatasetTransp>();
		
		
		
		for(DatasetTransp dtTransCat : listDatasetsTranspByCat){
			
			for(DatasetTransp dtTransDesc : listDatasetsTranspByDesc){
		
				if(dtTransDesc.getIdDatasetTransp() == dtTransCat.getIdDatasetTransp())
					listDatasetsTranspByCatAndDesc.add(dtTransDesc);
			}
				
		}
			
		
		return listDatasetsTranspByCatAndDesc;
	}
	
	
	
	
	
	
	
	//Devuelve todas las categorías de Transparencia (NO SE LLAMA DESDE NINGUN SERVICIO REST) cambiar a nuevo pool!!!!!!!!!!!!!!
	public List<CategoryDatasetTransp> getAllCategoriesTransp(){
		
		List<CategoryDatasetTransp> categoriesTransp = null;		
		
		Transaction tx = null;
		
		Session session = null;
		
		session = sessionFactory.openSession();		
		
		
	      try{
	         	 
		      
	    	  tx = session.beginTransaction();
	    	  
	    	  Criteria criteria = session.createCriteria(CategoryDatasetTransp.class);//.add(Restrictions.eq("idCategoryTransp", new Integer(1)))	;
	    	  
	    	  categoriesTransp = criteria.list();	    	  
	    	  
	    	  tx.commit();		
	         
	      
	      }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
		         session.close(); 
		         System.out.println("SESSION CLOSE === getAllCategoriesTransp");
		      }
	      
	      
	      return categoriesTransp;  
		
	}

	
	//Devuelve las traducciones que coincidan con "desc" de la Tabla "tableName" (NO SE LLAMA DESDE NINGUN SERVICIO REST)
	public List<Translation> getTranslationsByDesc(int lang,String desc,String tableName){
		
		List<Translation> translations = new ArrayList<Translation>();
		
		//StopWords
		Utilities utilities = new Utilities();
		
		desc.trim();
		List<String> splitDescription = Arrays.asList(desc.split(" "));
		List<String> splitDescriptionWithoutStopWords = null;
		if(lang == 1)
			splitDescriptionWithoutStopWords = utilities.removeStopWordsTXTVa(splitDescription);
		else if(lang == 2)
			splitDescriptionWithoutStopWords = utilities.removeStopWordsTXTEn(splitDescription);
		
		Transaction tx = null;
		
		//Session session = null;
		
		//this.session = sessionFactory.openSession();
		this.session = sessionFactory.getCurrentSession();
		
		 try{
	    	  
	    	  tx = this.session.beginTransaction();
	    	  
	    	  //Criterion descripcion = Restrictions.ilike("translation","%"+desc+"%");
	    	  
	    	  Criteria criteria = this.session.createCriteria(Translation.class);
	    	  for(String str : splitDescriptionWithoutStopWords){
		    	  
    			  //Criterion name = Restrictions.ilike("nameDatasetTransp","%"+str+"%");
    			  //Criterion description = Restrictions.ilike("description","%"+str+"%");
    			 // LogicalExpression orRestriction = Restrictions.or(description, name);
    			  criteria.add(Restrictions.ilike("translation", "%"+str+"%"));
    		  }
	    	  
	    	  criteria.add(Restrictions.eq("idLanguage",lang)).
						add(Restrictions.eq("tableName","DATASETS_TRANSP"));
	    	  
	    	  translations = (List<Translation>) criteria.list();    	  
	    	  
	    	  //for(Translation tr : translations)
	    		//  System.out.println("IDTrad: "+tr.getIdTranslation()+" TRAD: "+ tr.getTranslation());
	    	  
	    	  tx.commit();
	    	  
	    	    	  
	    	  
		 }
	      catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }
	      finally {
	    	  
	    	  
		         //session.close(); 
		         //System.out.println("Finally === getTranslationsByDesc");
		      }
		
		return translations;
		
	}
	
	
	
	
	
	
	
	
}



