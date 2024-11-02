package Dao;

import entity.user;

public interface UserDao {
    public void register(user newuser);
    public user login(String username,String password);

}
