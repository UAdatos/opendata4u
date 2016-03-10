package es.ua.datos.cad;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.stat.Statistics;

public class HibernateUtil
{  
    private static final SessionFactory sessionFactory;   
    //private String file;
    //public static Statistics stats;
    
    static {
        try 
        {         	
             
            Configuration configuration = new Configuration();
            configuration.configure("hibernate_oracle.cfg.xml");
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
            System.out.println("CREO SESSION FACTORY (Class HibernateUtil");
            //stats = sessionFactory.getStatistics();
            //stats.setStatisticsEnabled(true);
            
           // return sessionFactory;
            
        } catch (HibernateException he) 
        { 
           System.err.println("Ocurrió un error en la inicialización de la SessionFactory: " + he); 
            throw new ExceptionInInitializerError(he); 
        } 
   
    }
    public static SessionFactory getSessionFactory() 
    { 
            return sessionFactory; 
    } 
}
