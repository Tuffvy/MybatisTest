package com.controller;

import com.tjise.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjise.entity.User;
import com.tjise.mapper.UserMapper;
import com.tjise.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import com.tjise.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class HOME_Controller {

    SqlSession sqlsession = MyBatisUtil.openSession();
    UserMapper userMapper = sqlsession.getMapper(UserMapper.class);
    BookMapper bookMapper = sqlsession.getMapper(BookMapper.class);

    @RequestMapping(value = "/lib" , method = POST)
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
                return "redirect:/lib/login";
        }
        else //用户不存在
        {
            /*提示用户不存在*/
            return "redirect:/lib/login";
        }

    }

    @RequestMapping(value = "/lib/signin", method = POST)
    public String SignIn(@RequestParam("Username") String username,
                         @RequestParam("Password") String in_password, Model model,
                        HttpServletResponse response) throws ServletException, IOException
    {
        User user = userMapper.findUserByName(username);
        String cur_name = user.getUserName();
        if(cur_name.equals(username))
            model.addAttribute("msg","用户名已存在！");
        else
        {
            User new_user = new User();
            new_user.setUserName(username);
            new_user.setPassword(in_password);
            new_user.setPermission(0);
            userMapper.insertUser(new_user);
            sqlsession.commit();
        }
        return "redirect:/lib/login";
    }

    @RequestMapping(value = "/lib/list", method = POST)
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

    @RequestMapping(value = "/lib/list/add", method = POST)
    public String Add(@RequestParam("bookname") String name,
                      @RequestParam("author") String author,
                      @RequestParam("publish") String publish,
                      @RequestParam("date") String date, @RequestParam("amount") int amount,
                      @RequestParam("picture") String picture)
    {
        Book new_book = new Book();
        new_book.setAmount(amount);
        new_book.setAuthor(author);
        new_book.setBookname(name);
        new_book.setDate(date);
        new_book.setPublish(publish);
        new_book.setPicture(picture);
        bookMapper.insertBook(new_book);
        sqlsession.commit();
        return "redirect:/lib/list";
    }

    @RequestMapping(value = "/lib/list/del", method = POST)
    public String del(@RequestParam("bookid") int id)
    {
        bookMapper.deleteBook(id);
        sqlsession.commit();
        return "redirect:/lib/list/";
    }

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

    @RequestMapping(value = "/lib/seek", method = POST)
    public String Seek_byId(HttpServletRequest request, @RequestParam("bookname") String name, Model model)
    {
        List<Book> cur_book = bookMapper.findBookByName(name);
        model.addAttribute("books", cur_book);
        return "directory1";
    }

    @RequestMapping(value = "/lib/logout", method = POST)
    public String logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("UserName");
        request.getSession().removeAttribute("per");
        /* 删除session中的内容 */
        return "redirect:/lib/login";
    }


}
