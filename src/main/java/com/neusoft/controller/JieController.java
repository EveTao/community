package com.neusoft.controller;


import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Category;
import com.neusoft.domain.Comment;
import com.neusoft.domain.Topic;
import com.neusoft.domain.User;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.Page;
import com.neusoft.util.Respons;
import com.neusoft.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("jie")
public class JieController {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("/reply")
    public void reply(Comment comment,String content, HttpServletRequest request,HttpServletResponse respons) throws IOException {
        HttpSession session=request.getSession();
        comment.setCommentContent(content);
        Object obj= session.getAttribute("userinfo");
        Respons res=new Respons();
        if(obj!=null){
            User user=(User)obj;
            comment.setUserId(user.getId());
            comment.setCommentTime(new Date());
            int i = commentMapper.insertSelective(comment);
            if (i > 0) {
                res.setStatus(0);
                res.setAction(request.getServletContext().getContextPath() + "/jie/detail/" + comment.getTopicId());
            } else {
                respons.setStatus(1);
            }
        }else {
            res.setStatus(0);
            res.setAction(request.getServletContext().getContextPath()+"/user/login/");
        }
        respons.getWriter().println(JSON.toJSONString(res));
    }
    @RequestMapping("/index/{id}")
    public ModelAndView index(@PathVariable Integer id) {
        List<Map<String,Object>> mapList = topicMapper.getAllTopicsByCategoryId(id);
        List<Category> categories = categoryMapper.selectAll();
        for (Map<String,Object> map:mapList) {
            Date create_time = (Date) map.get("create_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("create_time",stringDate);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jie/index");
        modelAndView.addObject("topics",mapList);
        modelAndView.addObject("category",categories);
        modelAndView.addObject("categoryid",id);
        return modelAndView;
    }
    @RequestMapping("/add")
    public ModelAndView add(){
        ModelAndView modelAndView=new ModelAndView();
        List<Category> categories = categoryMapper.selectAll();
        modelAndView.setViewName("jie/add");
        modelAndView.addObject("categoryinfo",categories);
        return modelAndView;
    }
    @RequestMapping("/detail/{titleid}")
    public ModelAndView detail(@PathVariable Integer titleid, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("jie/detail");
        Topic topic = topicMapper.selectByPrimaryKey(titleid);
        String time=StringDate.getStringDate(topic.getCreateTime());
        topic.setViewTimes(topic.getViewTimes()+1);
        topicMapper.updateByPrimaryKey(topic);
        List<Map<String, Object>> maps = commentMapper.selectByTopicid(topic.getId());
        for (Map<String,Object> map:maps) {
            Date create_time = (Date) map.get("comment_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("comment_time",stringDate);
        }
        Map<String, Object> topicinfo = topicMapper.gettopic(topic.getId());
        List<Category> categories = categoryMapper.selectAll();
        modelAndView.addObject("topic",topicinfo);
        modelAndView.addObject("createTime",time);
        modelAndView.addObject("commentlist",maps);
        modelAndView.addObject("category",categories);
        return modelAndView;
    }
    @RequestMapping("/doadd")
    @ResponseBody
    @Transactional(rollbackFor=Exception.class)
    public Respons doadd(Topic topic, HttpServletRequest request){
        Respons respons=new Respons();
        System.out.println(topic);
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("userinfo");
        topic.setUserid(user.getId());
        topic.setCreateTime(new Date());
        int i = topicMapper.insertSelective(topic);
        User user1=userMapper.selectByPrimaryKey(user.getId());
        user1.setKissNum(user1.getKissNum()-topic.getKissNum());
        userMapper.updateByPrimaryKeySelective(user1);
        User user2=userMapper.selectByPrimaryKey(user.getId());
        session.setAttribute("userinfo",user2);
        if(i>0){
            respons.setStatus(0);
            respons.setAction(request.getServletContext().getContextPath() +"/");
        }else {
            respons.setStatus(1);
        }
        return respons;
    }
}
