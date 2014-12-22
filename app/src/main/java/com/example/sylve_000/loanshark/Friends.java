package com.example.sylve_000.loanshark;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseClassName;

/**
 * Created by sylve_000 on 2014-11-29.
 */
@ParseClassName("Friends")
public class Friends extends ParseObject{

    public double getAmount(){
        return getDouble("amount");
    }

    public void setAmount(double amount){
        put("amount", amount);
    }

    public String getFirstName(){
        return getString("firstName");
    }

    public void setFirstName(String name){
        put("firstName", name);
    }

    public String getLastName(){
        return getString("lastName");
    }

    public void setLastName(String name){
        put("lastName", name);
    }

    public void setUser(ParseUser value){
        put("user", value);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public static ParseQuery<Friends> getQuery(){
        return ParseQuery.getQuery(Friends.class);
    }
}