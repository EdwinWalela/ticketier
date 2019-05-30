package com.company;

public class User {
    public String userName;
    public String password;
    public String email;
    public String userType;

    public User(String _username, String _password, String _email,String _usertype){
        userName = _username;
        password = _password;
        email = _email;
        userType = _usertype;
    }
}
