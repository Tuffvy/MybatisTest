package com.tjise.entity;
import lombok.Data;

@Data
public class User {
    public int userid;
    private String username;
    private String password;
    private int permission;

    public int getUserId(){
        return this.userid;
    }
    public String getUserName(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public int getPermission(){
        return this.permission;
    }
    public void setUserId(int userid){
        this.userid = userid;
    }
    public void setUserName(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setPermission(int permission){
        this.permission = permission;
    }

    public User(int userid, String username, String password, int permission){
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
    public User(){}
}
