package DaoImp;

import Dao.IncomeDao;
import entity.Income;
import entity.user;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utill.hibernateUtill;

import java.util.List;

public class IncomeDaoImp implements IncomeDao {

//**************************METHOD TO ADD INCOME************************************************************************

    @Override
    public void addIncome(Income income, user user) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            income.setUser(user);
            session.save(income);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
//**********************METHOD TO VIEW INCOME***************************************************************************

    @Override
    public List<Income> getIncomesByUser(user user) {
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            String hql = "FROM Income i WHERE i.user.userEmail = :userEmail";
            return session.createQuery(hql, Income.class)
                    .setParameter("userEmail", user.getUserEmail())
                    .getResultList();
        }
    }
//*******************METHOD TO GET BALANCE AMOUNT***********************************************************************
    @Override
    public double getBalanceAmount(user user) {
        double balance = 0;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            // Retrieve total income for the specific user
            Double totalIncome = session.createQuery("SELECT SUM(i.salary) FROM Income i WHERE i.user = :user", Double.class)
                    .setParameter("user", user)
                    .uniqueResult();

                    ExpenseDaoImp expenseDaoImp = new ExpenseDaoImp();
            double totalExpenses = expenseDaoImp.getTotalExpenses(user);

            balance = (totalIncome != null ? totalIncome : 0) - totalExpenses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Math.max(balance, 0);

    }
//***********************METHOD TO DELETE INCOME OF THE USER************************************************************
    @Override
    public void deleteIncome(user user) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            List<Income> incomes = session.createQuery("FROM Income WHERE user = :user", Income.class)
                    .setParameter("user", user)
                    .getResultList();

            for (Income income : incomes) {
                session.delete(income);
            }

            transaction.commit();
            System.out.println("All incomes deleted successfully for user: " + user.getUser_name());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting incomes: " + e.getMessage());
        }
    }

}













































