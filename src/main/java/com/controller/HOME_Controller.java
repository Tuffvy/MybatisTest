package com.controller;

import com.tjise.mapper.BookMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import com.tjise.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HOME_Controller {

    SqlSession sqlsession = MyBatisUtil.openSession();
    UserMapper userMapper = sqlsession.getMapper(UserMapper.class);
    BookMapper bookMapper = sqlsession.getMapper(BookMapper.class);

    @RequestMapping(value = "/lib")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("UserName") == null)
            return "login";
        else
            return "redirect:/lib/list";
    }

    @RequestMapping(value = "/lib/login", method = POST)
    public String login(@RequestParam("username") String username,
             @RequestParam("password") String in_password,HttpServletRequest request)
    {
        User user =  userMapper.findUserByName(username);
        if(user != null)
        {
            String password = user.getPassword();
            HttpSession session = request.getSession();
            int per = user.getPermission();
            if (password.equals(in_password)) {
                session.setAttribute("UserName", username);
                session.setAttribute("per", per);
                return "redirect:/lib/list";
            } else
                return "redirect:/lib";
        }
        else //用户不存在
        {
            /*提示用户不存在*/
            return "redirect:/lib";
        }

    }

    @RequestMapping("/lib/getsign")
    public String getSign(){return "register";}
    @RequestMapping(value = "/lib/signin",method = POST)
    public String SignIn(@RequestParam("username") String username,
                         @RequestParam("password") String password, Model model,
                        HttpServletResponse response) throws ServletException, IOException
    {
        User user = userMapper.findUserByName(username);
        if(user != null)
            model.addAttribute("msg","用户名已存在！");
        else
        {
            User new_user = new User(username, password, 0);
            userMapper.insertUser(new_user);
            sqlsession.commit();
        }
        return "redirect:/lib";
    }

    @RequestMapping(value = "/lib/list")
    public String ad_directory(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        int per = (int) session.getAttribute("per");
        List<Book> books = bookMapper.findBookList();
        model.addAttribute("books", books);
        if(per == 1) model.addAttribute("per", 1);
        else model.addAttribute("per", 0);
        return "directory";
    }

    @RequestMapping("/lib/list/getadd")
    public String getAdd(){
        return "add";
    }

    @RequestMapping(value = "/lib/list/add", method = POST)
    public String Add(@RequestParam("bookname") String name,
                      @RequestParam("author") String author,
                      @RequestParam("publish") String publish,
                      @RequestParam("date") String date, @RequestParam("amount") int amount)
    {
        Book new_book = new Book();
        new_book.setAmount(amount);
        new_book.setAuthor(author);
        new_book.setBookname(name);
        new_book.setDate(date);
        new_book.setPublish(publish);
        new_book.setPicture("https://img12.360buyimg.com/n1/jfs/t1/69706/14/20858/498844/62f48b83E4f1cb479/439c82e68a58b694.png");
        bookMapper.insertBook(new_book);
        sqlsession.commit();
        return "redirect:/lib/list";
    }

    @RequestMapping("/lib/list/getdel")
    public String getDel(){return "del";}
    @RequestMapping(value = "/lib/list/del", method = POST)
    public String del(@RequestParam("bookid") int id)
    {
        bookMapper.deleteBook(id);
        sqlsession.commit();
        return "redirect:/lib/list/";
    }

    @RequestMapping("/lib/list/getupd")
    public String getUpd(){return "upd";}
    @RequestMapping(value = "/lib/list/upd", method = POST)
    public String update(@RequestParam("id") int id,
                         @RequestParam("bookname") String name,
                         @RequestParam("author") String author,
                         @RequestParam("publish") String pub,
                         @RequestParam("date") String date, @RequestParam("amount") String am,
                         @RequestParam("picture") String pic)
    {
        Book up_book = bookMapper.findBookById(id);
        if(name != null) up_book.setBookname(name);
        if(author != null) up_book.setAuthor(author);
        if (pub != null) up_book.setPublish(pub);
        if(date != null) up_book.setDate(date);
        if(am != null) up_book.setAmount(Integer.valueOf(am));
        if(pic != null) up_book.setPublish(pic);
        bookMapper.updateBook(up_book);
        sqlsession.commit();
        return "redirect:/lib/list";
    }

    @RequestMapping("/lib/list/getseek")
    public String getSeek(){return "seek";}
    @RequestMapping(value = "/lib/list/seek", method = POST)
    public String Seek_byName(HttpServletRequest request, @RequestParam("bookname") String name, Model model)
    {
        List<Book> cur_book = bookMapper.findBookByName(name);
        model.addAttribute("books", cur_book);
        return "directory1";
    }

    @RequestMapping(value = "/lib/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("UserName");
        request.getSession().removeAttribute("per");
        /* 删除session中的内容 */
        return "redirect:/lib";
    }

    @RequestMapping(value = "/lib/list/usermsg", method = POST)
    public String User_msg(HttpServletRequest request, Model model)
    {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("UserName");
        User user = userMapper.findUserByName(username);
        model.addAttribute("user", user);
        return "usermsg";
    }

    @RequestMapping(value = "/lib/list/usermsg/return", method = POST)
    public String Return(){return "redirect:/lib/list";}

    @RequestMapping(value = "/lib/list/usermsg/get_repswd", method = POST)
    public String getps(){return "repswd";}

    @RequestMapping(value = "/lib/list/usermsg/repswd", method = POST)
    public String RePassword(HttpServletRequest request, Model model,
                             @RequestParam("new_password") String new_ps,
                            @RequestParam("old_password") String old_ps)

    {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("UserName");
        User user = userMapper.findUserByName(name);
        String password = user.getPassword();
        if(password.equals(old_ps))
        {
            user.setPassword(new_ps);
            userMapper.updateUser(user);
            sqlsession.commit();
            return "redirect:/lib/logout";
        }
        else
        {
            model.addAttribute("msg","old password is wrong..");
            return "repswd";
        }
    }

    @RequestMapping(value = "/lib/list/usermsg/logoff", method = POST)
    public String Logoff(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("UserName");
        User user = userMapper.findUserByName(username);
        int id = user.getUserId();
        userMapper.deleteUser(id);
        sqlsession.commit();
        return "redirect:/lib/logout";
    }

}
