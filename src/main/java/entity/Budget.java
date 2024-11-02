package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @Column(name = "user_id")
    private String userId; // Use user ID as the primary key

    @Column(name = "user_name")
    private String userName;

    @Column(name = "month")
    private LocalDate month; // Month for which the budget is set


    @Column(name = "fixed_amount_budget")
    private Double fixedAmountBudget;

    @Column(name = "travel_amount_budget")
    private Double travelAmountBudget;

    @Column(name = "food_amount_budget")
    private Double foodAmountBudget;

    @Column(name = "miscellaneous_amount_budget")
    private Double miscellaneousAmountBudget;


    // Constructors
    public Budget() {}

    public Budget(String userId, String userName, Double fixedAmountBudget,
                  Double travelAmountBudget, Double foodAmountBudget,
                  Double miscellaneousAmountBudget, LocalDate month) {
        this.userId = userId;
        this.userName = userName;
        this.fixedAmountBudget = fixedAmountBudget;
        this.travelAmountBudget = travelAmountBudget;
        this.foodAmountBudget = foodAmountBudget;
        this.miscellaneousAmountBudget = miscellaneousAmountBudget;
        this.month = month;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getFixedAmountBudget() {
        return fixedAmountBudget;
    }

    public void setFixedAmountBudget(Double fixedAmountBudget) {
        this.fixedAmountBudget = fixedAmountBudget;
    }

    public Double getTravelAmountBudget() {
        return travelAmountBudget;
    }

    public void setTravelAmountBudget(Double travelAmountBudget) {
        this.travelAmountBudget = travelAmountBudget;
    }

    public Double getFoodAmountBudget() {
        return foodAmountBudget;
    }

    public void setFoodAmountBudget(Double foodAmountBudget) {
        this.foodAmountBudget = foodAmountBudget;
    }

    public Double getMiscellaneousAmountBudget() {
        return miscellaneousAmountBudget;
    }

    public void setMiscellaneousAmountBudget(Double miscellaneousAmountBudget) {
        this.miscellaneousAmountBudget = miscellaneousAmountBudget;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", fixedAmountBudget=" + fixedAmountBudget +
                ", travelAmountBudget=" + travelAmountBudget +
                ", foodAmountBudget=" + foodAmountBudget +
                ", miscellaneousAmountBudget=" + miscellaneousAmountBudget +
                ", month=" + month +
                '}';
    }
}

