package com.nightbeer.dao;

public class dados {
	
    public boolean validUser(String user,String password){
        if (user.equalsIgnoreCase("admin") && password.equals("123")) {
            return true;
        } else {
            return false;
        }
    }
    
    
}
