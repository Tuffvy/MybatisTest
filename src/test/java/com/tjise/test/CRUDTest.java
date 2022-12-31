package com.tjise.test;

import com.tjise.entity.User;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDTest {

    private SqlSession session;

    @Before
    public void init(){
        //利用MyBatisUtil工具类获取数据库的连接
        session = MyBatisUtil.openSession();
    }

    //不带参数的查询
    @Test
    public void findUserListTest(){
        List<User> userList = session.selectList("userns.findUserList");
        System.out.println(userList);
    }

    //带一个参数
    @Test
    public void findUserByNameTest(){
        List<User> userList = session.selectList("userns.findUserByName", "user");
        System.out.println(userList);
    }

    //带多个参数
    @Test
    public void findUserByPermissionTest(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("minPermission", 60);
        map.put("maxPermission", 100);
        List<User> userList = session.selectList("userns.findUserByPermission", map);
        System.out.println(userList);
    }

    //实现分页效果
    @Test
    public void findUserByPage(){
        int pageSize = 2;
        int currentPage = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageSize", pageSize);
        map.put("currentPage", (currentPage - 1) * pageSize);
        List<User> userList = session.selectList("userns.findUserByPage", map);
        System.out.println(userList);
    }

    //插入数据
    @Test
    public void testInsertUser(){
        try {
            User user = new User(7, "user7", "123456", 10);
            int rows = session.insert("com.tjise.mapper.UserMapper.insertUser", user);
            System.out.println(rows);
            session.commit();
        } catch (Exception e) {
            System.out.println("0");
            e.printStackTrace();
            session.rollback();
        }
    }

    //修改数据
    @Test
    public void testUpdateUser(){
        try {
            User user = session.selectOne("com.tjise.mapper.UserMapper.findUserById", 1);
            user.setPassword("789");
            int rows = session.update("com.tjise.mapper.UserMapper.updateUser", user);
            System.out.println(rows);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    //修改数据
    @Test
    public void testDeleteUser(){
        try {
            int rows = session.delete("com.tjise.mapper.UserMapper.deleteUser", 1);
            System.out.println(rows);
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



