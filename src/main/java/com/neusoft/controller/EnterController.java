package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Category;
import com.neusoft.domain.PageInfo;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.util.StringDate;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping("/")
    public ModelAndView index()
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
        modelAndView.setViewName("index");
//        modelAndView.addObject("topics",mapList);
        modelAndView.addObject("toptopics",allTopTopics);
        modelAndView.addObject("category",categories);
        modelAndView.addObject("tops",commentMaps);
        modelAndView.addObject("TopicsHot",allTopicsHot);
        return modelAndView;
    }
//    取得帖子的翻页内容
    @RequestMapping("getTopicPage/{categoryId}")
    public void getTopicPage(@PathVariable Integer categoryId, PageInfo pageInfo, HttpServletResponse response) throws IOException {
        int num;
        List<Map<String,Object>> mapList=new ArrayList<>();
        if(categoryId==0){
            num=topicMapper.count();
            mapList = topicMapper.getAllTopicsByPage(pageInfo);
        }else {
            num=topicMapper.countByCategoryId(categoryId);
            pageInfo.setType(categoryId);
            mapList=topicMapper.getAllTopicsByPageType(pageInfo);
        }
        for (Map<String,Object> map:mapList) {
            Date create_time = (Date) map.get("create_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("create_time",stringDate);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",num);
        map.put("datas",mapList);
        response.getWriter().println(JSON.toJSONString(map));
    }
}
