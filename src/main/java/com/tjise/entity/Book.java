package com.tjise.entity;
import lombok.Data;

@Data
public class Book {
    private int bookid;
    private String bookname;
    private String author;
    private String publish;
    private String date;
    private int amount;
    private String picture;

    public int getBookid() {
        return bookid;
    }
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Book(String bookname,String author,String publish,String date,int amount,String picture){
        this.bookname=bookname;
        this.author=author;
        this.publish=publish;
        this.date=date;
        this.amount=amount;
        this.picture=picture;
    }
    public Book(){}
}
