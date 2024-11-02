package entity;

import javax.persistence.*;


    @Entity
    @Table(name = "split")
    public class split {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "expense_id")
        private Expense expense; // The associated expense

        @ManyToOne
        @JoinColumn(name = "user_id")
        private user user; // The user participating in the split

        @Column(name = "amount")
        private Double amount; // The amount split by the user

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Expense getExpense() {
            return expense;
        }

        public void setExpense(Expense expense) {
            this.expense = expense;
        }

        public entity.user getUser() {
            return user;
        }

        public void setUser(entity.user user) {
            this.user = user;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }


