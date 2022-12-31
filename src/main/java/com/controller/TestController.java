package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

@Controller
public class TestController {

    @RequestMapping("/regist")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/getpost",method = POST)
    public String getPost(@RequestParam("name")String name,
                          @RequestParam("password")String password,
                          @RequestParam("permission")Integer permission,
                          Model model){
        SqlSession session=MyBatisUtil.openSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);

        model.addAttribute("name",name);
        model.addAttribute("password",password);
        model.addAttribute("permission",permission);

        User user = new User(name,password,permission);
        userMapper.insertUser(user);
        session.commit();

        return "getpost";
    }

}

