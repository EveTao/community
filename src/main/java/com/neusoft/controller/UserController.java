package com.neusoft.controller;

import com.neusoft.domain.PageInfo;
import com.neusoft.domain.Topic;
import com.neusoft.domain.User;
import com.neusoft.mapper.CommentMapper;
import com.neusoft.mapper.TopicMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.MD5Utils;
import com.neusoft.util.Respons;
import com.neusoft.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping("reg")
    public String reg(){
        return "user/reg";
    }
//    进入登录页面
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        HttpSession session=request.getSession();
        if(session.getAttribute("referer")==null||session.getAttribute("referer")==""){
            String referer = request.getHeader("referer");
            session.setAttribute("referer",referer);
        }
        return "user/login";
    }
//    进入用户中心
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user = (User) session.getAttribute("userinfo");
        List<Topic> topics = topicMapper.selectByUserid(user.getId());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("user/index");
        modelAndView.addObject("topics",topics);
        int count = topicMapper.countByUserId(user.getId());
        modelAndView.addObject("topicCount",count);
        return modelAndView;
    }
//    进入我的消息页
    @RequestMapping("message")
    public ModelAndView message(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("user/message");
        HttpSession session=request.getSession();
        User user =(User) session.getAttribute("userinfo");
        List<Map<String, Object>> commentList = commentMapper.selectByUseridNew(user);
        for (Map<String,Object> map:commentList) {
            Date comment_time = (Date) map.get("comment_time");
            String stringDate = StringDate.getStringDate(comment_time);
            map.put("comment_time",stringDate);
        }
        modelAndView.addObject("commentList",commentList);
        return modelAndView;
    }
    @RequestMapping("set")
    public String set(){
        return "user/set";
    }
//  登录
    @RequestMapping("dologin")
    @ResponseBody
    public Respons dologin(User user, HttpServletRequest request){
//        System.out.println(user);
        HttpSession session=request.getSession();
        String referer;
        if(session.getAttribute("referer")==null||session.getAttribute("referer")==""){
            referer="/";
        }else {
            referer= (String) session.getAttribute("referer");
            session.removeAttribute("referer");
        }
        user.setPasswd(MD5Utils.getPwd(user.getPasswd()));
        user = userMapper.selectByEmailPwd(user);
        Respons respons=new Respons();
        if(user!=null){
            respons.setStatus(0);
            respons.setAction(referer);
            respons.setMsg("登录成功");
            session.setAttribute("userinfo",user);
        }else {
            respons.setStatus(1);
            respons.setAction("/user/login");
            respons.setMsg("登录失败");
        }
        return respons;
    }
//    注册
    @RequestMapping(value = "/doreg", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Respons doreg(User user){
        Respons respons=new Respons();
        user.setPasswd(MD5Utils.getPwd(user.getPasswd()));
        Date now=new Date();
        user.setJoinTime(now);
        user.setKissNum(100);
        int i = userMapper.insertSelective(user);
        if(i>0){
            respons.setStatus(0);
            respons.setMsg("注册成功");
            respons.setAction("/user/login");
        }else {
            respons.setStatus(1);
            respons.setMsg("注册失败");
            respons.setAction("/user/req");
        }
        return respons;
    }

//    修改邮箱之前，确认该邮箱在数据库中是否存在
    @RequestMapping("/checkEmail")
    @ResponseBody
    public Respons checkEmail(String email){
        /*System.out.println("-------------------------------");
        System.out.println(email);*/
        Respons respons=new Respons();
        User user = userMapper.selectByEmail(email);
//        System.out.println(user);
        if (user==null){
            respons.setStatus(0);
            respons.setMsg("该邮箱可以使用");
        }else {
            respons.setStatus(1);
            respons.setMsg("该邮箱已存在");
        }
        return respons;
    }
//  上传图片
    @RequestMapping("upload")
    @ResponseBody
    public Respons upload(@RequestParam MultipartFile file,HttpServletRequest request) throws IOException {
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
            HttpSession session=request.getSession();
            User user = (User) session.getAttribute("userinfo");
            user.setPicPath(realName);
            int i=userMapper.updateByPrimaryKey(user);
            if (i>0){
                session.setAttribute("userinfo",user);
                respons.setStatus(0);
            }
            else {
                respons.setStatus(1);
            }
        }else {
            respons.setStatus(1);
        }
        return respons;
    }
//    登出
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:" + request.getServletContext().getContextPath() +"/";
    }
//    修改用户邮箱等信息
    @RequestMapping("changeInfo")
    @ResponseBody
    public Respons changeInfo(User user,HttpServletRequest request){
        System.out.println(user);
        Respons respons=new Respons();
        int i = userMapper.updateByPrimaryKeySelective(user);
        if(i>0){
            HttpSession session=request.getSession();
            User u=userMapper.selectByPrimaryKey(user.getId());
            session.setAttribute("userinfo",u);
            respons.setStatus(0);
            respons.setAction("user/set");
        }else {
            respons.setStatus(1);
        }
        return respons;
    }
//    修改密码之前确认原来密码是否正确
    @RequestMapping("/surePasswd/{passwd}")
    @ResponseBody
    public Respons surePasswd(@PathVariable String passwd,HttpServletRequest request) {
        HttpSession session=request.getSession();
        System.out.println(passwd);
        User user= (User) session.getAttribute("userinfo");
        Respons respons=new Respons();
        if (MD5Utils.getPwd(passwd).equals(user.getPasswd())){
            respons.setStatus(0);
            respons.setMsg("密码确认成功，请填写新密码");
        }else {
            respons.setStatus(1);
            respons.setMsg("密码确认失败，请重新确认密码");
        }
        return respons;
    }
//    修改密码
    @RequestMapping("/repass")
    @ResponseBody
    public Respons repass(User user,HttpServletRequest request){
        Respons respons=new Respons();
        System.out.println("---------------------------");
        System.out.println(user);
        user.setPasswd(MD5Utils.getPwd(user.getPasswd()));
        int i=userMapper.updateByPrimaryKeySelective(user);
        if(i>0){
            User user1=userMapper.selectByPrimaryKey(user.getId());
            System.out.println(user1);
            HttpSession session=request.getSession();
            session.setAttribute("userinfo",user1);
            respons.setStatus(0);
            respons.setAction("user/set");
        }else {
            respons.setStatus(1);
            respons.setAction("user/set");
        }
        return respons;
    }
//  进个人主页
    @RequestMapping("/home/{userid}")
    public ModelAndView home(@PathVariable Integer userid, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        HttpSession session=request.getSession();
        User user = userMapper.selectByPrimaryKey(userid);
        modelAndView.addObject("user",user);
        SimpleDateFormat fdate=new SimpleDateFormat("yyyy年MM月dd日");
        String time=fdate.format(user.getJoinTime());
        session.setAttribute("joinTime",time);
        List<Topic> topics = topicMapper.selectByUserid(user.getId());
        //        獲取全部的評論
//        List<Map<String, Object>> maps = commentMapper.selectByUserid(userid);
        //        獲取部分評論
        List<Map<String, Object>> maps = commentMapper.selectByUseridPart(userid);
        for (Map<String,Object> map:maps) {
            Date create_time = (Date) map.get("comment_time");
            String stringDate = StringDate.getStringDate(create_time);
            map.put("comment_time",stringDate);
        }
        modelAndView.addObject("topic",topics);
        modelAndView.addObject("comment",maps);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }
//    从@跳转到用户主页
    @RequestMapping("jumphome/{username}")
    public ModelAndView jumphome(@PathVariable String username)
    {
        ModelAndView modelAndView = new ModelAndView();
        User user = userMapper.selectByNickname(username);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("user/home/"+user.getId());
        return modelAndView;
    }
}
