package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Collect;
import com.neusoft.domain.Comment;
import com.neusoft.domain.PageInfo;
import com.neusoft.domain.User;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.CollectMapper;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.util.Respons;
import com.neusoft.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OtherCotroller {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CollectMapper collectMapper;
    //    取得帖子的翻页内容
    @RequestMapping("getTopicPage")
    public void getTopicPage(PageInfo pageInfo, HttpServletResponse response) throws IOException {
        int num=topicMapper.countByCategoryId(pageInfo);
        List<Map<String,Object>> mapList = topicMapper.getAllTopicsByPage(pageInfo);
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
    //    清空全部消息提醒
    @RequestMapping("message/remove")
    public void getTopicPage(String id,boolean all, HttpServletResponse response, HttpSession session) throws IOException {
//        System.out.println(id);
        Respons res=new Respons();
        Comment comment=new Comment();
        User user =(User) session.getAttribute("userinfo");
        if(all){
            int i = commentMapper.updateIsRemindAll(user);
            if(i>0){
                res.setStatus(0);
            }else {
                res.setStatus(1);
            }
        }else {
            comment.setId(Integer.parseInt(id));
            comment.setIsRemind(1);
            int i = commentMapper.updateByPrimaryKeySelective(comment);
            if(i>0){
                res.setStatus(0);
            }else {
                res.setStatus(1);
            }
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
    //    帖子的收藏与取消收藏
    @RequestMapping("collection/{type}")
    public void add(@PathVariable String type, Integer cid, HttpSession session, HttpServletResponse response) throws IOException {
        User user=(User) session.getAttribute("userinfo");
        Collect collect=new Collect();
        collect.setTopicId(cid);
        collect.setUserId(user.getId());
        collect.setCollectTime(new Date());
        int i ;
        if(type.equals("add")){
            i= collectMapper.insertSelective(collect);
        }else {
            i =collectMapper.deleteByUserTopic(collect);
        }
        Respons res=new Respons();
        if(i>0){
            res.setStatus(0);
        }else {
            res.setStatus(1);
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
}
