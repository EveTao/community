package com.neusoft.controller;

import com.neusoft.domain.Category;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class EnterController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @RequestMapping("/")
    public ModelAndView index()
    {
        List<Map<String,Object>> mapList = topicMapper.getAllTopics();
        List<Map<String, Object>> allTopTopics = topicMapper.getAllTopTopics();
        List<Category> categories = categoryMapper.selectAll();
        ModelAndView modelAndView = new ModelAndView();
        for (Map<String,Object> map:mapList) {
            Date create_time = (Date) map.get("create_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("create_time",stringDate);
        }
        for (Map<String,Object> map:allTopTopics) {
            Date create_time = (Date) map.get("create_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("create_time",stringDate);
        }
        modelAndView.setViewName("index");
        modelAndView.addObject("topics",mapList);
        modelAndView.addObject("toptopics",allTopTopics);
        modelAndView.addObject("category",categories);
        return modelAndView;
    }
}
