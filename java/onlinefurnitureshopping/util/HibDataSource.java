package onlinefurnitureshopping.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibDataSource {

	private static SessionFactory factory;

	private HibDataSource() {

	}
	/**
     * Get the instance of Session Factory
     * 
     * @return sessionFactory
     */
	public static SessionFactory getSessionfactory() {
		if (factory == null) {
			factory = new Configuration().configure().buildSessionFactory();
		}
		return factory;
	}

	 /**
     * Get Session by SessionFactory
     * 
     * @return session
     */
    public static Session getSession() {
        Session session = getSessionfactory().openSession();
        return session;
    }

    /**
     * Close Session
     */
    public static void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }

}
