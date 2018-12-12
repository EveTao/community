package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Topic;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.util.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TopicController {
    @Autowired
    TopicMapper topicMapper;


    @RequestMapping("api/jie-delete")
    public void deleteTopic(Topic topic, HttpServletResponse response) throws IOException {
        System.out.println(topic.getId());
        topic.setIsDelete(1);
        int i = topicMapper.updateByPrimaryKeySelective(topic);
        Respons res=new Respons();
        if(i>0){
            res.setStatus(0);
            res.setAction("/");
        }else {
            res.setStatus(1);
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
}
