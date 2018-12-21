package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Comment;
import com.neusoft.domain.PageInfo;
import com.neusoft.domain.Topic;
import com.neusoft.domain.User;
import com.neusoft.mapper.CategoryMapper;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("api")
public class ApiController {
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;

//    置顶帖子
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
//  删除帖子
    @RequestMapping("jie-delete")
    public void deleteTopic(Topic topic, HttpServletResponse response) throws IOException {
        System.out.println(topic.getId());
        topic.setIsDelete(1);
        int i = topicMapper.updateByPrimaryKeySelective(topic);
        Respons res=new Respons();
        if(i>0){
            res.setStatus(0);
//            res.setAction("/");
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
        int i1 = commentMapper.updateByPrimaryKeySelective(comment);
        Respons res=new Respons();
        if(i1>0){
            Topic topic=new Topic();
            topic.setId(comment.getTopicId());
            topic.setIsEnd(1);
            int i = topicMapper.updateByPrimaryKeySelective(topic);
            topic.getKissNum();
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            int kissnum=user.getKissNum()+topic.getKissNum();
            user.setKissNum(kissnum);
            int i2 = userMapper.updateByPrimaryKeySelective(user);
            if(i>0 && i2>0){
                res.setStatus(0);
            }else {
                res.setStatus(1);
            }
        }else {
            res.setStatus(1);
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
//    点赞回复
    @RequestMapping("jieda-zan")
    public void zan(Integer id,Boolean ok, HttpSession session,HttpServletResponse response) throws IOException {
        User user=(User) session.getAttribute("userinfo");
        Respons res=new Respons();
        if(user!=null){
            res.setStatus(0);
//            取消点赞，当前评论likenum--
//            在点赞表里删除一行记录
            if(ok){

            }else {
//            点赞，当前评论likenum++
//            在点赞表里新增一行记录
            }
        }else {
            res.setStatus(1);
            res.setMsg("请登录");
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
//    评论上传图片
    @RequestMapping("upload")
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException {
        Respons respons=new Respons();
        String filename = file.getOriginalFilename();
/*        File file1 = new File("d:/head.jpg");
        file.transferTo(file1);*/
        if(file.getSize()>0) {
            String realPath = request.getServletContext().getRealPath("/res/uploadImg") + File.separator;
//            String realPath ="/res/images/avatar" + File.separator;
            File files = new File(realPath);
            if (!files.exists()) {
                files.mkdirs();
            }
            System.out.println(realPath);
            String realName=UUID.randomUUID().toString()+filename;
            File file1 = new File(realPath + realName);
            file.transferTo(file1);
            Respons res=new Respons();
            res.setStatus(0);
            res.setUrl(request.getServletContext().getContextPath()+"/res/uploadImg/"+realName);
            response.getWriter().println(JSON.toJSONString(res));
        }
    }
}
