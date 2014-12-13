package com.example.sylve_000.loanshark;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by sylve_000 on 2014-12-11.
 */
@ParseClassName("Transactions")
public class Transaction extends ParseObject{

    public boolean getType(){
        return getBoolean("type");
    }

    public void setType(boolean type){
        put("type", type);
    }

    public double getAmount(){
        return getDouble("amount");
    }

    public void setAmount(double amount){
        put("amount", amount);
    }

    public String getFriend(){
        return getString("friend");
    }

    public void setFriend(String friend){
        put ("friend", friend);
    }

    public void setUser(ParseUser value){
        put("user", value);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public static ParseQuery<Transaction> getQuery(){
        return ParseQuery.getQuery(Transaction.class);
    }
}
