package utill;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class hibernateUtill {
    private final static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try{
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();


        }
        catch(Throwable e ){
            throw new ExceptionInInitializerError(e);
        }

    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static Session getSession(){
        return getSessionFactory().openSession();
    }
    public  static void shutdown(){
        getSessionFactory().close();
    }
}
