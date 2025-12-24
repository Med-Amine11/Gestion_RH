package com.grh.service;

import com.grh.model.User;

public class UserServiceTest {
    public static void main(String[] args){
        UserService userService = new UserService() ;
        User user = userService.login("mohamedamineaswab@gmail.com" , "123456") ;
        System.out.println(user);
    }
}
