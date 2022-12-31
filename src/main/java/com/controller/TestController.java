package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.tjise.entity.User;
import com.tjise.entity.Book;
import com.tjise.mapper.UserMapper;
import com.tjise.mapper.BookMapper;
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

    @RequestMapping(value = "/getadd", method = POST)
    public String Add(@RequestParam("bookname") String bookname,
                      @RequestParam("author") String author,
                      @RequestParam("publish") String publish,
                      @RequestParam("date") String date, @RequestParam("amount") int amount,
                      @RequestParam("picture") String picture)
    {
        SqlSession sqlsession=MyBatisUtil.openSession();
        BookMapper bookMapper=sqlsession.getMapper(BookMapper.class);
        Book new_book = new Book(bookname,author,publish,date,amount,picture);

        bookMapper.insertBook(new_book);
        sqlsession.commit();
        return "redirect:getpost";
    }

}

