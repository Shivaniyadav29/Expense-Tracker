package DaoImp;

import Dao.CategoryDao;
import entity.Category;
import entity.user;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utill.hibernateUtill;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CategoryDaoImp implements CategoryDao {

    @Override
    public void addCategory(Category category) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Category getCategoryByName(String categoryName) { // Update parameter name for clarity
        Category category = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            Query<Category> query = session.createQuery("FROM Category WHERE categoryName = :categoryName", Category.class); // Update query
            query.setParameter("categoryName", categoryName);
            category = query.uniqueResult(); // Corrected the syntax error
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public Category findCategoryByUserAndType(user username, String categoryType) {
        Category category = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            Query<Category> query = session.createQuery(
                    "FROM Category c WHERE c.username = :username AND c.categoryName = :categoryName", Category.class);
            query.setParameter("username", username);
            query.setParameter("categoryName", capitalize(categoryType));
            category = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}