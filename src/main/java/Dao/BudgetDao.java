package Dao;

import entity.Budget;
import entity.Category;
import entity.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BudgetDao {

     void addBudgetToDatabase(Budget budget);
     Budget getBudgetByUserIdAndMonth(String userId , LocalDate month );
     void deleteBudget(String userId , LocalDate month);
     void updateBudget(Budget budget);
     public Map<String, Double> getPreviousMonthBudget(user user);



}
