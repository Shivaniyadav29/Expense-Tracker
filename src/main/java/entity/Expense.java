package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @Column(name = "Expenseid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
    private double amount;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    private String username;

    public Expense() {}

    public Expense(String description, double amount, user user, LocalDate date,Category category) {
        this.description = description;
        this.amount = amount;
        this.user = user;
        this.username = user.getUser_name();
        this.date = date;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public double setAmount(double amount) {
        this.amount = amount;
        return amount;
    }

    public entity.user getUser() {
        return user;
    }

    public void setUser(entity.user user) {
        this.user = user;
        this.username = user.getUser_name();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense: \n" +  "Category: " + category.getCategoryName() + "\n"+
        "Date: " + date + "\n" +
                "Description: " + description + "\n" +
                "Amount: " + amount + "\n" +
                "Username: " + username + "\n" ;

    }


}
