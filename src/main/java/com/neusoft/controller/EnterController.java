package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.*;
import com.neusoft.mapper.*;
import com.neusoft.util.GetKissnum;
import com.neusoft.util.Respons;
import com.neusoft.util.StringDate;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class EnterController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    QiandaoMapper qiandaoMapper;
//    进入首页
    @RequestMapping("/")
    public ModelAndView index(HttpSession session)
    {
//        List<Map<String,Object>> mapList = topicMapper.getAllTopics();
        List<Map<String, Object>> allTopTopics = topicMapper.getAllTopTopics();
        List<Category> categories = categoryMapper.selectAll();
        ModelAndView modelAndView = new ModelAndView();
//        for (Map<String,Object> map:mapList) {
//            Date create_time = (Date) map.get("create_time");
//            String stringDate = StringDate.getStringDate(create_time);
//            map.put("create_time",stringDate);
//        }
        for (Map<String,Object> map:allTopTopics) {
            Date create_time = (Date) map.get("create_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("create_time",stringDate);
        }
        List<Map<String, Object>> commentMaps = commentMapper.selectTop();
        List<Map<String, Object>> allTopicsHot = topicMapper.getAllTopicsHot();
        int countToday=0;
        User user=(User) session.getAttribute("userinfo");
        int qiandaoKiss=5;
        int qiandaoDay=0;
        if(user!=null){
            countToday= qiandaoMapper.countToday(user.getId());
//            Qiandao qiandao = qiandaoMapper.selectCurrent(user.getId());
            Qiandao qiandao = qiandaoMapper.selectByUserId(user.getId());
            if(qiandao!=null){
                qiandaoDay=qiandao.getTotal();
                GetKissnum getKissnum=new GetKissnum();
                qiandaoKiss=getKissnum.getKisssnum(qiandaoDay);
            }
        }
        System.out.println("qiandaoDay:"+qiandaoDay);
        System.out.println("qiandaoKiss:"+qiandaoKiss);
        modelAndView.setViewName("index");
//        modelAndView.addObject("topics",mapList);
        modelAndView.addObject("toptopics",allTopTopics);
        modelAndView.addObject("category",categories);
        modelAndView.addObject("tops",commentMaps);
        modelAndView.addObject("typeid",0);
        modelAndView.addObject("TopicsHot",allTopicsHot);
        modelAndView.addObject("qiandaoKiss",qiandaoKiss);
        modelAndView.addObject("qiandaoDay",qiandaoDay);
        modelAndView.addObject("countToday",countToday);
        return modelAndView;
    }
}
