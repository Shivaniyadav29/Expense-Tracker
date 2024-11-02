package main;

import DaoImp.*;
import entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserService {
    private userdaoimp userDao;
    private ExpenseDaoImp expenseDao;
    private CategoryDaoImp categoryDao;
    private IncomeDaoImp incomeDao;
    private Scanner scanner;
    private BudgetDaoImp budgetDaoImp;


    public UserService() {
        this.userDao = new userdaoimp();
        this.expenseDao = new ExpenseDaoImp();
        this.categoryDao = new CategoryDaoImp();
        this.incomeDao = new IncomeDaoImp();
        this.scanner = new Scanner(System.in);
        this.budgetDaoImp = new BudgetDaoImp();
    }

    public void start() {
        user user1 = null;

        while (true) {
            showMenu();
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    register();
                    break;
                case 2:
                    user1 = login();
                    if (user1 != null) {
                        showMainMenu(user1);
                    }
                    break;

                case 3:
                    exit();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private void showMainMenu(user user1) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option: ");
            System.out.println("1. Manage Income");
            System.out.println("2. Manage Expense");
            System.out.println("3. Track Expense");
            System.out.println("4. Go Back to Main page");
            System.out.println("Enter Your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    manageIncome(user1);
                    break;
                case 2:
                    manageExpense(user1);
                    break;
                case 3:
                    manageBudgets(user1);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice !! Please Try Again");
            }


        }

    }
//*************************Manage Income*****************************************************************************

    private void manageIncome(user user1) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Choose Income Options:");
            System.out.println("1. Add Income");
            System.out.println("2. View Income");
            System.out.println("3. Delete Income");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int userIncome_choice = scanner.nextInt();
            scanner.nextLine();
            switch (userIncome_choice) {
                case 1:
                    addIncome(user1);
                    break;
                case 2:
                    viewIncome(user1);
                    break;
                case 3:
                    deleteIncome(user1);
                    break;

                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice ! please Try again Later");

            }
        }
    }
//*************************Income Opreation***********************************

    private void addIncome(user user1) {
        if (user1 == null) {
            System.out.println("***************You need to log in first.*******************");
            return;
        }

        System.out.println("Enter source of Income");
        String source = scanner.nextLine();
        System.out.print("Enter income amount: ");
        double incomeAmount = scanner.nextDouble();
        scanner.nextLine();

        Income income = new Income();
        income.setSource(source);
        income.setSalary(incomeAmount);
        income.setUser(user1);
        incomeDao.addIncome(income, user1);
        System.out.println("****************Income added successfully!***************");
    }

    private void viewIncome(user user1) {
        if (user1 == null) {
            System.out.println("You need to log in first.");
            return;
        }

        List<Income> incomes = incomeDao.getIncomesByUser(user1);
        if (incomes != null && !incomes.isEmpty()) {
            System.out.println("Income Details:");
            for (Income income : incomes) {
                System.out.println(income);
            }
        } else {
            System.out.println("No income found for this user.");
        }
    }

    private void deleteIncome(user user){
        if(user==null){
            System.out.println("You need to log in First");
            return;
        }
        incomeDao.deleteIncome(user);
        System.out.println("Income deleted successfully");
    }



//*****************************end Income Operation******************************

//***************************************Manage Expense**********************************************

    private void manageExpense(user user1){
        boolean exit = false;

        while(!exit){
            System.out.println("Choose Expense Options:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. Display Financial Overview ");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int userexpensechoice = scanner.nextInt();
            scanner.nextLine();
            switch (userexpensechoice){
                case 1 :
                    addExpense(user1);
                    break;
                case 2 :
                    viewExpenses(user1);
                    break;

                case 3:
                    deleteExpense(user1);

                case 4:
                    showBalance(user1);

                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice ! please Try again Later");

            }
        }
    }

    private void addExpense(user user1) {
        if (user1 == null) {
            System.out.println("******************You need to log in first.******************");
            return;
        }

        System.out.print("Enter category (fixed/travel/food/miscellaneous): ");
        String categoryType = scanner.nextLine().toLowerCase();

        System.out.print("Enter description for the expense: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline

        System.out.println("Enter Date (yyyy-MM-DD):");
        String dateInput = scanner.nextLine();
        LocalDate date;

        try {
            date = LocalDate.parse(dateInput);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setUser(user1);
        expense.setDate(date);

        Category category = categoryDao.findCategoryByUserAndType(user1, categoryType);
        if (category == null) {
            category = new Category();
            category.setUsername(user1);
            category.setDescription("Expenses for " + categoryType+ expense.getDescription() );
            category.setCategoryName(capitalize(categoryType));
            category.setFixedExpenses(0);
            category.setTravelExpenses(0);
            category.setFoodExpenses(0);
            category.setMiscellaneousExpenses(0);
        }

        switch (categoryType) {
            case "fixed":
                category.setFixedExpenses(category.getFixedExpenses() + amount);
                break;
            case "travel":
                category.setTravelExpenses(category.getTravelExpenses() + amount);
                break;
            case "food":
                category.setFoodExpenses(category.getFoodExpenses() + amount);
                break;
            case "miscellaneous":
                category.setMiscellaneousExpenses(category.getMiscellaneousExpenses() + amount);
                break;
            default:
                System.out.println("Invalid category type. Expense not added.");
                return;
        }

        expense.setCategory(category);
        categoryDao.addCategory(category);
        expenseDao.addExpense(expense);

        System.out.println("****************Expense added and updated successfully!**************");
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void viewExpenses(user user1) {
        List<Expense> expenses = expenseDao.getExpense(user1);
        if (expenses.isEmpty()) {
            System.out.println("************* No expenses found **************");
        } else {
            System.out.println("Expenses: ");
            for (Expense exp : expenses) {
                System.out.println(exp);
            }
        }
    }

    private void deleteExpense(user user) {
        if (user == null) {
            System.out.println("You need to log in first.");
            return;
        }

        incomeDao.deleteIncome(user);
        System.out.println("Expense deleted successfully.");
    }



    private void showBalance(user user1) {
        if (user1 == null) {
            System.out.println("Oops! You need to log in.");
            return;
        }
        double balance = incomeDao.getBalanceAmount(user1);
        double spentAmount = expenseDao.getTotalExpenses(user1);
        System.out.println("**********Total spent amount is: " + spentAmount);
        System.out.println("**********Your current balance is: " + balance);
    }



    private void manageBudgets(user user1) {
        if (user1 == null) {
            System.out.println("You need to log in first.");
            return;
        }

        while (true) {
            System.out.println("1. Add Budget");
            System.out.println("2. View Budgets");
            System.out.println("3. Delete Budget");
            System.out.println("4. Update Budget ");
            System.out.println("5.Analyse previous Month");
            System.out.println("6. Back To main menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBudget(user1);
                    break;
               case 2:
                    viewBudget(user1);
                    break;
                case 3:
                    deleteBudget(user1);
                    break;

                case 4:
                    updateBudget(user1);
                    break;
                case 5:
                    analyzeCurrentExpenses(user1 );

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBudget(user user1) {
        if (user1 == null) {
            System.out.println("******************You need to log in first.******************");
            return;
        }

        Budget budget = new Budget();
        budget.setUserId(user1.getUserEmail());
        budget.setUserName(user1.getUser_name());


        System.out.print("Enter fixed amount budget: ");
        double fixedAmount = scanner.nextDouble();
        budget.setFixedAmountBudget(fixedAmount);

        System.out.print("Enter travel amount budget: ");
        double travelAmount = scanner.nextDouble();
        budget.setTravelAmountBudget(travelAmount);

        System.out.print("Enter food amount budget: ");
        double foodAmount = scanner.nextDouble();
        budget.setFoodAmountBudget(foodAmount);

        System.out.print("Enter miscellaneous amount budget: ");
        double miscellaneousAmount = scanner.nextDouble();
        budget.setMiscellaneousAmountBudget(miscellaneousAmount);

        budget.setMonth(LocalDate.now());

        budgetDaoImp.addBudgetToDatabase(budget);
    }

    private void viewBudget(user user1) {
        if (user1 == null) {
            System.out.println("******************You need to log in first.******************");
            return;
        }

        System.out.print("Enter month for which you want to view the budget (YYYY-MM-DD): ");
        String monthInput = scanner.nextLine();
        LocalDate month = LocalDate.parse(monthInput);

        Budget budget = budgetDaoImp.getBudgetByUserIdAndMonth(user1.getUserEmail(), month);

        if (budget != null) {
            System.out.println("Budget details: " + budget.toString());
        } else {
            System.out.println("No budget found for the specified month.");
        }
    }

    private void deleteBudget(user user1) {
        if (user1 == null) {
            System.out.println("******************You need to log in first.******************");
            return;
        }

        System.out.print("Enter month for which you want to delete the budget (YYYY-MM-DD): ");
        String monthInput = scanner.nextLine();
        LocalDate month = LocalDate.parse(monthInput);

        budgetDaoImp.deleteBudget(user1.getUserEmail(), month);
        System.out.println("Budget Deleted successfully ");
    }


    private void updateBudget(user user1) {
        if (user1 == null) {
            System.out.println("******************You need to log in first.******************");
            return;
        }

        System.out.print("Enter month for which you want to update the budget (YYYY-MM-DD): ");
        String monthInput = scanner.nextLine();
        LocalDate month = LocalDate.parse(monthInput);

        Budget existingBudget = budgetDaoImp.getBudgetByUserIdAndMonth(user1.getUserEmail(), month);

        if (existingBudget != null) {
            System.out.print("Enter new budgeted amount for fixed expenses: ");
            double fixedAmount = scanner.nextDouble();
            existingBudget.setFixedAmountBudget(fixedAmount);

            System.out.print("Enter new budgeted amount for travel expenses: ");
            double travelAmount = scanner.nextDouble();
            existingBudget.setTravelAmountBudget(travelAmount);

            System.out.print("Enter new budgeted amount for food expenses: ");
            double foodAmount = scanner.nextDouble();
            existingBudget.setFoodAmountBudget(foodAmount);

            System.out.print("Enter new budgeted amount for miscellaneous expenses: ");
            double miscellaneousAmount = scanner.nextDouble();
            existingBudget.setMiscellaneousAmountBudget(miscellaneousAmount);


            existingBudget.setFixedAmountBudget(existingBudget.getFixedAmountBudget());
            existingBudget.setTravelAmountBudget(existingBudget.getTravelAmountBudget());
            existingBudget.setFoodAmountBudget(existingBudget.getFoodAmountBudget());
            existingBudget.setMiscellaneousAmountBudget(existingBudget.getMiscellaneousAmountBudget());

            budgetDaoImp.updateBudget(existingBudget);
        } else {
            System.out.println("No budget found for the specified month.");
        }
    }


public void analyzeCurrentExpenses(user user){
        if(user==null){
            System.out.println("You must be login");
        }


    assert user != null;
    List<Expense> currentExpenses = expenseDao.getExpense(user);
    double totalFixedExpenses = 0;
    double totalOtherExpenses = 0;

    for (Expense expense : currentExpenses) {
        String category = expense.getCategory().getCategoryName();
        double amount = expense.getAmount();

        if ("fixed".equalsIgnoreCase(category)) {
            totalFixedExpenses += amount;
        } else {
            totalOtherExpenses += amount;
        }
    }


    LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
    Budget budget = budgetDaoImp.getBudgetByUserIdAndMonth(user.getUserEmail(), currentMonth);
    double budgetedFixedAmount = budget != null ? budget.getFixedAmountBudget() : 0;


    System.out.println("Current Expense Analysis:");
    System.out.printf("Total Fixed Expenses: %.2f (Budgeted: %.2f)\n", totalFixedExpenses, budgetedFixedAmount);
    System.out.printf("Total Other Expenses: %.2f\n", totalOtherExpenses);


    if (totalOtherExpenses > budgetedFixedAmount) {
        System.out.println("Advice: You are spending too much on non-fixed expenses! Consider reducing your discretionary spending.");
    } else {
        System.out.println("Advice: Your spending is within the limits for fixed expenses. Keep up the good work!");
    }
}


//*******************************************End Track Expense*************************************************************
    private void register() {
        System.out.print("Enter  Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Email ID:");
        String userEmail = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        userDao.register(new user(username,userEmail , password));
        System.out.println("**************Registration successful!***************************");
    }

    private user login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        user loggedUser = userDao.login(username, password);
        if (loggedUser != null) {
            System.out.println("***************Login successful!************* " + loggedUser.getUser_name());
            return loggedUser;
        } else {
            System.out.println("**********************Invalid username or password********************");
            return null;
        }
    }

    private void exit() {
        System.out.println("Exiting...");

    }
}