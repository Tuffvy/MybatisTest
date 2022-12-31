package com.tjise.mapper;

import com.tjise.entity.User;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> findUserList();
    List<User> findUserById(int userid);
    List<User> findUserByName(String username);
    List<User> findUserByPermission(int permission);
    List<User> findUserByPage(Map<String, Object> map);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int userid);
}