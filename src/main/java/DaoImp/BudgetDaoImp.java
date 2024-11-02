package DaoImp;

import Dao.BudgetDao;
import entity.Budget;
import entity.user;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utill.hibernateUtill;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class BudgetDaoImp implements BudgetDao {
    ExpenseDaoImp expenseDaoImp = new ExpenseDaoImp();

    public void addBudgetToDatabase(Budget budget) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(budget);
            transaction.commit();
            System.out.println("Budget added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error adding budget: " + e.getMessage());
        }
    }

    @Override
    public Budget getBudgetByUserIdAndMonth(String userId, LocalDate month) {
        Budget budget = null;
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            budget = session.createQuery("FROM Budget WHERE userId = :userId AND month = :month", Budget.class)
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error retrieving budget: " + e.getMessage());
        }
        return budget;
    }


    @Override
    public void deleteBudget(String userId, LocalDate month) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Budget budget = session.createQuery("FROM Budget WHERE userId = :userId AND month = :month", Budget.class)
                    .setParameter("userId", userId)
                    .setParameter("month", month)
                    .uniqueResult();
            if (budget != null) {
                session.delete(budget);
                System.out.println("Budget deleted successfully.");
            } else {
                System.out.println("No budget found to delete for the specified month.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error deleting budget: " + e.getMessage());
        }
    }

    @Override
    public void updateBudget(Budget budget) {
        Transaction transaction = null;
        try (Session session = hibernateUtill.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(budget);
            transaction.commit();
            System.out.println("Budget updated successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error updating budget: " + e.getMessage());
        }
    }

        public Map<String, Double> getPreviousMonthBudget(user user) {
            Map<String, Double> budgetAdvice = new HashMap<>();

            LocalDate now = LocalDate.now();
            LocalDate lastMonth = now.minusMonths(1);

                        LocalDate startOfLastMonth = lastMonth.withDayOfMonth(1);
            LocalDate endOfLastMonth = lastMonth.plusMonths(1).withDayOfMonth(1);

            try (Session session = hibernateUtill.getSessionFactory().openSession()) {

                Budget budget = session.createQuery("FROM Budget b WHERE b.userId = :userId AND b.month = :month", Budget.class)
                        .setParameter("userId", user.getUserEmail())
                        .setParameter("month", lastMonth)
                        .uniqueResult();


                Double totalExpenses = expenseDaoImp.getTotalExpensesForMonth(user, lastMonth);

                if (budget != null) {
                    if (totalExpenses > budget.getFixedAmountBudget()) {
                        budgetAdvice.put("Fixed", totalExpenses - budget.getFixedAmountBudget());
                    }
                    if (totalExpenses > budget.getTravelAmountBudget()) {
                        budgetAdvice.put("Travel", totalExpenses - budget.getTravelAmountBudget());
                    }
                    if (totalExpenses > budget.getFoodAmountBudget()) {
                        budgetAdvice.put("Food", totalExpenses - budget.getFoodAmountBudget());
                    }
                    if (totalExpenses > budget.getMiscellaneousAmountBudget()) {
                        budgetAdvice.put("Miscellaneous", totalExpenses - budget.getMiscellaneousAmountBudget());
                    }
                } else {
                    System.out.println("No budget found for the previous month.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return budgetAdvice;
        }

    }

