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













































//package DaoImp;
//
//import Dao.IncomeDao;
//import entity.Income;
//import entity.user;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import utill.hibernateUtill;
//
//public class IncomeDaoImp implements IncomeDao {
//    @Override
//    public void addIncome(Income income, user user) {
//        Transaction transaction = null;
//        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(income);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Income getIncomeById(String id , user user) {
//        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
//            Income income =  session.get(Income.class, id);
//            if (income != null && income.getUser().equals(user)) {
//                return income;
//            } else {
//                return null;
//            }
//        }
//        }
//    }
//
//@Override
//public void updateIncome(Income income, user user) {
//    Transaction transaction = null;
//    try (Session session = hibernateUtill.getSessionFactory().openSession()) {
//        transaction = session.beginTransaction();
//        // Ensure that the income belongs to the user before updating
//        Income existingIncome = session.get(Income.class, income.getId());
//        if (existingIncome != null && existingIncome.getUser() != null && existingIncome.getUser().getId().equals(user.getId())) {
//            session.update(income);
//            transaction.commit();
//        } else {
//            System.out.println("You do not have permission to update this income.");
//        }
//    } catch (Exception e) {
//        if (transaction != null) {
//            transaction.rollback();
//        }
//        e.printStackTrace();
//    }
//}
//
//    @Override
//    public double getBalanceAmount(user user) {
//        double balance = 0;
//        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
//            // Retrieve the income for the specific user
//            Income income = session.createQuery("FROM Income i WHERE i.user = :user", Income.class)
//                    .setParameter("user", user)
//                    .uniqueResult();
//
//            // Calculate total expenses
//            ExpenseDaoImp expenseDaoImp = new ExpenseDaoImp();
//            double totalExpenses = expenseDaoImp.getTotalExpenses(user);
//
//            if (income != null) {
//                balance = income.getSalary() - totalExpenses; // Adjust balance calculation
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Math.max(balance, 0); // Ensure balance is not negative
//    }
//
//
//}
