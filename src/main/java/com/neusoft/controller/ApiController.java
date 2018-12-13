package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Comment;
import com.neusoft.domain.PageInfo;
import com.neusoft.domain.Topic;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.util.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("api")
public class ApiController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("jie-set")
    public void setTopic(Integer id, Integer rank, String field, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Topic topic=new Topic();
        topic.setId(id);
        if(field.equals("stick")){
            topic.setIsTop(rank);
        }else {
            topic.setIsGood(rank);
        }
        int i = topicMapper.updateByPrimaryKeySelective(topic);
        Respons res=new Respons();
        if(i>0){
            res.setStatus(0);
        }else {
            res.setStatus(1);
        }
        response.getWriter().println(JSON.toJSONString(res));
    }

    @RequestMapping("jie-delete")
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
//    采纳回帖内容
    @RequestMapping("jieda-accept")
    @Transactional(rollbackFor = Exception.class)
    public void accept(Comment comment,HttpServletResponse response) throws IOException {
        System.out.println(comment.getId());
        comment=commentMapper.selectByPrimaryKey(comment.getId());
        comment.setIsChoose(1);
        int i1 = commentMapper.updateByPrimaryKey(comment);
        Respons res=new Respons();
        if(i1>0){
            Topic topic=new Topic();
            topic.setId(comment.getTopicId());
            topic.setIsEnd(1);
            int i = topicMapper.updateByPrimaryKeySelective(topic);
            if(i>0){
                res.setStatus(0);
            }else {
                res.setStatus(1);
            }
        }else {
            res.setStatus(1);
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
}
