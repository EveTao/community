package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.User;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("message")
public class MessageController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("nums")
    public void getMsgCount(HttpSession httpSession, HttpServletResponse response) throws IOException {
        User user = (User)httpSession.getAttribute("userinfo");
        int count = commentMapper.countByUseridNew(user);
        Respons res=new Respons();
        res.setStatus(0);
        res.setCount(count);

        response.getWriter().println(JSON.toJSONString(res));
    }

    @RequestMapping("read")
    public void readMsg(HttpSession httpSession, HttpServletResponse response) throws IOException {
        User user = (User)httpSession.getAttribute("userinfo");
        commentMapper.updateIsNewRemind(user);
        Respons res=new Respons();
        res.setStatus(0);
        response.getWriter().println(JSON.toJSONString(res));
    }
}
