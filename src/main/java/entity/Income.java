package entity;

import javax.persistence.*;

@Entity
@Table(name = "income_details")
public class Income {

    @Id
    @Column(name = "user_id")
    private String userEmail;

    @Column(name = "User_salary")
    private double salary;

    @Column(name = "Spent_Amount")
    private double spentAmount;

    @Column
    private String source;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private user user;

    public Income() {}

    public Income(double salary, double spentAmount, user user) {
        this.userEmail = user.getUserEmail();
        this.salary = salary;
        this.spentAmount = spentAmount;
        this.user = user;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
        this.userEmail = user.getUserEmail();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Income: \n" +
                "User Email: " + userEmail + "\n" +
                "Source of Income: " + source + "\n" +
                "Salary: " + salary + "\n" +
                "Spent Amount: " + spentAmount + "\n" +
                "Username: " + user.getUser_name() + "\n";
    }
}
