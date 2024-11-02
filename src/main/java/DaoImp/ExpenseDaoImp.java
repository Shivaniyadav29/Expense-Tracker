package DaoImp;

import Dao.ExpenseDao;
import entity.Expense;
import entity.user;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utill.hibernateUtill;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDaoImp implements ExpenseDao {
    @Override
    public void addExpense(Expense expense) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(expense);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Expense> getExpense(user user) {
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            Query<Expense> query = session.createQuery("from Expense where user.id = :userId");
            query.setParameter("userId", user.getUserEmail());
            return query.list();
        }
    }
    @Override
    public double getTotalExpenses(user user) {
        double total = 0;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            Query<Expense> query = session.createQuery("FROM Expense WHERE user = :user", Expense.class);
            query.setParameter("user", user);
            List<Expense> expenses = query.list();
            total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public  double getTotalExpensesForMonth(user user, LocalDate month) {
        double total = 0;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            LocalDate startOfMonth = month.withDayOfMonth(1);
            LocalDate startOfNextMonth = month.plusMonths(1).withDayOfMonth(1);

            Double result = session.createQuery("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.date >= :start AND e.date < :end", Double.class)
                    .setParameter("user", user)
                    .setParameter("start", startOfMonth)
                    .setParameter("end", startOfNextMonth)
                    .uniqueResult();

            if (result != null) {
                total = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }


            @Override
            public void deleteExpense(user user) {
                Transaction transaction = null;
                try (Session session = hibernateUtill.getSessionFactory().openSession()) {
                    transaction = session.beginTransaction();
                    String hql = "DELETE FROM Expense e WHERE e.user.userEmail = :userEmail";
                    int deletedCount = session.createQuery(hql)
                            .setParameter("userEmail", user.getUserEmail())
                            .executeUpdate();
                    transaction.commit();
                    System.out.println(deletedCount);
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }


    @Override
    public Map<String, Double> getExpenseCategoryWise(user user) {
        Map<String, Double> categoryTotals = new HashMap<>();

        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            List<Object[]> results = session.createQuery(
                            "SELECT e.category.categoryName, SUM(e.amount) " +
                                    "FROM Expense e WHERE e.user = :user " +
                                    "GROUP BY e.category.categoryName", Object[].class)
                    .setParameter("user", user)
                    .getResultList();


            for (Object[] result : results) {
                String categoryName = (String) result[0];
                Double totalAmount = (Double) result[1];
                categoryTotals.put(categoryName, totalAmount);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error fetching category-wise expenses: " + e.getMessage());
        }

        return categoryTotals;
    }




}
