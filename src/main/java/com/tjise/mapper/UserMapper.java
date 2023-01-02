package com.tjise.mapper;

import com.tjise.entity.User;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> findUserList();
    User findUserById(int userid);
    User findUserByName(String username);
    List<User> findUserByPermission(int permission);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int userid);
}
