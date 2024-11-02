package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "C_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private user username;

    @Column
    private String description;

    @Column
    private double fixedExpenses;

    @Column
    private double travelExpenses;

    @Column
    private double foodExpenses;

    @Column
    private double miscellaneousExpenses;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Expense> expenses = new HashSet<>();

    public Category() {}

    // Getters and Setters
    public double getFixedExpenses() {
        return fixedExpenses;
    }

    public void setFixedExpenses(double fixedExpenses) {
        this.fixedExpenses = fixedExpenses;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public user getUsername() {
        return username;
    }

    public void setUsername(user username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        expense.setCategory(this);
    }

    public double getTravelExpenses() {
        return travelExpenses;
    }

    public void setTravelExpenses(double travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    public double getFoodExpenses() {
        return foodExpenses;
    }

    public void setFoodExpenses(double foodExpenses) {
        this.foodExpenses = foodExpenses;
    }

    public double getMiscellaneousExpenses() {
        return miscellaneousExpenses;
    }

    public void setMiscellaneousExpenses(double miscellaneousExpenses) {
        this.miscellaneousExpenses = miscellaneousExpenses;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", fixedExpenses=" + fixedExpenses +
                ", travelExpenses=" + travelExpenses +
                ", foodExpenses=" + foodExpenses +
                ", miscellaneousExpenses=" + miscellaneousExpenses +
                ", description='" + description + '\'' +
                '}';
    }
}
