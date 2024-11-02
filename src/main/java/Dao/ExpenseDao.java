package Dao;

import entity.Expense;
import entity.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseDao {
    public void addExpense(Expense expense);
    public List<Expense> getExpense(user user);
    public double getTotalExpenses(user user);
    public double getTotalExpensesForMonth(user user, LocalDate month);
    public void deleteExpense(user user);
    public Map<String , Double> getExpenseCategoryWise(user user);

}
