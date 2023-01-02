package com.tjise.test;

import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.entity.Book;
import com.tjise.mapper.BookMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperTest {
    private SqlSession session;
    private UserMapper userMapper;
    private BookMapper bookMapper;

    @Before
    public void init(){
        //利用MyBatisUtil工具类获取数据库的连接
        session = MyBatisUtil.openSession();
        //获取到UserMapper的接口
        userMapper = session.getMapper(UserMapper.class);
        bookMapper = session.getMapper(BookMapper.class);
    }

    @Test
    public void findUserList(){
        List<User> userList = userMapper.findUserList();
        System.out.println(userList);
    }
    @Test
    public void findBookList(){
        List<Book> bookList = bookMapper.findBookList();
        System.out.println(bookList);
    }

    @Test
    public void findUserByNameTest(){
        User user = userMapper.findUserByName("tom");
        System.out.println(user);
    }
    @Test
    public void findBookByNameTest(){
        List<Book> books = bookMapper.findBookByName("book1");
        System.out.println(books);
    }

    @Test
    public void findUserByPermissionTest(){
        List<User> userList = userMapper.findUserByPermission(0);
        System.out.println(userList);
    }
    @Test
    public void findBookByAuthorTest(){
        List<Book> books = bookMapper.findBookByAuthor("汪");
        System.out.println(books);
    }

    @Test
    public void findUserByPage(){
        int pageSize = 2;
        int currentPage = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("currentPage", (currentPage - 1) * pageSize);
        List<User> userList = userMapper.findUserByPage(map);
        System.out.println(userList);
    }
    @Test
    public void findBookByPublishTest(){
        List<Book> books = bookMapper.findBookByPublish("人民出版社");
        System.out.println(books);
    }

    @Test
    public void testInsertUser(){
        try {
            User user = new User("user1", "123456", 0);
            userMapper.insertUser(user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    @Test
    public void testInsertBook(){
        try {
            Book book = new Book("book2", "天", "人民出版社", "2022-1-1", 15, "index.jpg");
            bookMapper.insertBook(book);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void testUpdateUser(){
        try {
            User user = userMapper.findUserById(2);
            user.setPassword("1234567");
            userMapper.updateUser(user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }
    @Test
    public void testUpdateBook(){
        try{
            Book book = bookMapper.findBookById(5);
            book.setDate("2023-1-1");
            bookMapper.updateBook(book);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void testDeleteUser(){
        try {
            userMapper.deleteUser(2);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        }
    }
    @Test
    public void testDeleteBook(){
        try{
            bookMapper.deleteBook(3);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
    }

    @After
    public void destory(){
        //关闭连接
        session.close();
    }
}


