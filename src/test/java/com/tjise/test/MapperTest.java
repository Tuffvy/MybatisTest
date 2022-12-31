package com.tjise.test;

import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperTest {
    //数据库连接
    private SqlSession session;
    //mapper接口
    private UserMapper userMapper;

    @Before
    public void init(){
        //利用MyBatisUtil工具类获取数据库的连接
        session = MyBatisUtil.openSession();
        //获取到UserMapper的接口
        userMapper = session.getMapper(UserMapper.class);
    }

    @Test
    public void findUserList(){
        List<User> userList = userMapper.findUserList();
        System.out.println(userList);
    }

    @Test
    public void findUserByNameTest(){
        List<User> userList = userMapper.findUserByName("tom");
        System.out.println(userList);
    }

    @Test
    public void findUserByPermissionTest(){
        List<User> userList = userMapper.findUserByPermission(0);
        System.out.println(userList);
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
    public void testInsertUser(){
        try {
            User user = new User(2, "user2", "123456", 10);
            userMapper.insertUser(user);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void testUpdateUser(){
        try {
            List<User> users = userMapper.findUserById(2);
            for(User user:users){
                user.setPassword("1234567");
                userMapper.updateUser(user);
            }
            session.commit();
        } catch (Exception e) {
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

    @After
    public void destory(){
        //关闭连接
        session.close();
    }
}


