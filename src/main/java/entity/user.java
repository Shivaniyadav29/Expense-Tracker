package entity;

import javax.persistence.*;

@Entity
@Table(name = "UserDetails")
public class user {

        @Id
        private String userEmail;
        @Column
        private String user_name;
        @Column
        private String password;


        public user() {}

        public user(String username, String userEmail , String password) {
            this.user_name = username;
            this.userEmail= userEmail;
            this.password = password;
        }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

}


