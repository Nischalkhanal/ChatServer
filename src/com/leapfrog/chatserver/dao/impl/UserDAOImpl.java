/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leapfrog.chatserver.dao.impl;

import com.leapfrog.chatserver.dao.UserDAO;
import com.leapfrog.chatserver.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nick
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public User login(String userName, String Password) {
        for(User u:getAll()){
            if (u.getUserName().equals(userName)&&u.getPassword().equals(Password)){
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "nisha", "prasai", true));
        userList.add(new User(2, "nischal", "khanal", true));
        userList.add(new User(3, "karan", "shrestha", true));
        userList.add(new User(4, "mandeep", "rimal", true));
                
        return userList;

    }

}
