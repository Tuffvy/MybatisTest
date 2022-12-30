package com.tjise.entity;
import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;
    private String password;
    private int score;

    public User(String userName, String password, int score){
        this.userName = userName;
        this.password = password;
        this.score = score;
    }
    public User(){}
}
