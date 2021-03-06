package com.neusoft.controller;


import com.alibaba.fastjson.JSON;
import com.neusoft.domain.*;
import com.neusoft.mapper.*;
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
import java.util.HashMap;
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
    @Autowired
    CollectMapper collectMapper;

//    回帖
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
            comment.setIsRemind(0);
            int i = commentMapper.insertSelective(comment);
            Topic topic;
            if (i > 0) {
                topic = topicMapper.selectByPrimaryKey(comment.getTopicId());
                topic.setCommentNum(topic.getCommentNum()+1);
                int i1 = topicMapper.updateByPrimaryKeySelective(topic);
                if(i1>0){
                    res.setStatus(0);
                    res.setAction(request.getServletContext().getContextPath() + "/jie/detail/" + comment.getTopicId());
                }else {
                    respons.setStatus(1);
                }
            } else {
                respons.setStatus(1);
            }
        }else {
            res.setStatus(0);
            res.setAction(request.getServletContext().getContextPath()+"/user/login/");
            String referer = request.getHeader("referer");
            request.getSession().setAttribute("referer",referer);
        }
        respons.getWriter().println(JSON.toJSONString(res));
    }
//    进分区，提问分享讨论等
    @RequestMapping("/index/{id}/{typeid}")
    public ModelAndView index(@PathVariable Integer id,@PathVariable Integer typeid) {
//        List<Map<String,Object>> mapList = topicMapper.getAllTopicsByCategoryId(id);
        List<Category> categories = categoryMapper.selectAll();
//        for (Map<String,Object> map:mapList) {
//            Date create_time = (Date) map.get("create_time");
//            String stringDate = StringDate.getStringDate(create_time);
//            map.put("create_time",stringDate);
//        }
        List<Map<String, Object>> allTopicsHot = topicMapper.getAllTopicsHot();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("jie/index");
//        modelAndView.addObject("topics",mapList);
        modelAndView.addObject("category",categories);
        modelAndView.addObject("categoryid",id);
        modelAndView.addObject("typeid",typeid);
        modelAndView.addObject("TopicsHot",allTopicsHot);
        return modelAndView;
    }
//    跳转到发帖页
    @RequestMapping("/add/{topicid}")
    public ModelAndView add(@PathVariable Integer topicid){
        ModelAndView modelAndView=new ModelAndView();
        List<Category> categories = categoryMapper.selectAll();
        modelAndView.setViewName("jie/add");
        modelAndView.addObject("categoryinfo",categories);
        if(topicid!=0){
            Topic topic = topicMapper.selectByPrimaryKey(topicid);
            modelAndView.addObject("topic",topic);
        }
        return modelAndView;
    }
//    进帖子详情页
    @RequestMapping("/detail/{titleid}")
    public ModelAndView detail(@PathVariable Integer titleid, HttpSession session) throws IOException {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("jie/detail");
        Topic topic = topicMapper.selectByPrimaryKey(titleid);
        String time=StringDate.getStringDate(topic.getCreateTime());
        topic.setViewTimes(topic.getViewTimes()+1);
        topicMapper.updateByPrimaryKey(topic);
        Map<String,Object> map=new HashMap<>();
        map.put("topicid",titleid);
        User user =(User) session.getAttribute("userinfo");
        int i=0;
        if(user!=null){
            map.put("userid",user.getId());
            Collect collect =new Collect();
            collect.setUserId(user.getId());
            collect.setTopicId(titleid);
            collect = collectMapper.findByUserTopic(collect);
            if(collect!=null){
                i=1;
            }
        }else {
            map.put("userid",0);
        }
        List<Map<String, Object>> commentMaps = commentMapper.selectByTopicid(map);
        for (Map<String,Object> m:commentMaps) {
            Date create_time = (Date) m.get("comment_time");
            String stringDate = StringDate.getStringDate(create_time);
            m.put("comment_time",stringDate);
        }
        Map<String, Object> topicinfo = topicMapper.gettopic(topic.getId());
        List<Category> categories = categoryMapper.selectAll();
        List<Map<String, Object>> allTopicsHot = topicMapper.getAllTopicsHot();
        modelAndView.addObject("topic",topicinfo);
        modelAndView.addObject("createTime",time);
        modelAndView.addObject("commentlist",commentMaps);
        modelAndView.addObject("category",categories);
        modelAndView.addObject("TopicsHot",allTopicsHot);
        modelAndView.addObject("collect",i);
        return modelAndView;
    }
//    发表帖子
    @RequestMapping("/doadd")
    @ResponseBody
    @Transactional(rollbackFor=Exception.class)
    public Respons doadd(Topic topic, HttpServletRequest request){
        Respons respons=new Respons();
//        System.out.println(topic);
        if(topic.getId()==0){
            HttpSession session=request.getSession();
            User user=(User) session.getAttribute("userinfo");
            if(user.getKissNum()>topic.getKissNum()){
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
            }else {
                respons.setStatus(1);
                respons.setMsg("飞吻数不够");
            }
        }else {
            int i = topicMapper.updateByPrimaryKeySelective(topic);
            if(i>0){
                respons.setStatus(0);
                respons.setAction(request.getServletContext().getContextPath() +"/");
            }else {
                respons.setStatus(1);
            }
        }

        return respons;
    }
}
