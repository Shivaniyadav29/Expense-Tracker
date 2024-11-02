package DaoImp;

import Dao.UserDao;
import entity.user;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utill.hibernateUtill;


public class userdaoimp implements UserDao {
//*****************************REGISTER METHOD**************************************************************************
    @Override
    public  void register(user newuser) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(newuser);
            session.getTransaction().commit();


        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }

//***************************LOGIN METHOD*******************************************************************************
    @Override
    public   user  login(String username, String password) {
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            Query<user> query = session.createQuery("from user where user_name = :username and password = :password", user.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }



}



