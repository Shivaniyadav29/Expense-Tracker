package Dao;
import entity.Income;
import entity.user;

import java.util.List;

public interface IncomeDao {

        void addIncome(Income income , user user);
         public List<Income> getIncomesByUser(user user);
        double getBalanceAmount(user user);
         void deleteIncome(user user1);

    }


