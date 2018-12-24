package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Qiandao;
import com.neusoft.domain.User;
import com.neusoft.mapper.QiandaoMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.Data;
import com.neusoft.util.Respons;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("sign123")
public class SignCotroller123 {
    @Autowired
    QiandaoMapper qiandaoMapper;
    @Autowired
    UserMapper userMapper;

//    签到
    @RequestMapping("in")
    @Transactional(rollbackFor = Exception.class)
    public void in(String token, HttpServletResponse response,HttpServletRequest request) throws IOException {
        System.out.println(token);
        Respons res=new Respons();
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("userinfo");
//        res.setData(33);
        if(user!=null){
            Qiandao qiandao=new Qiandao();
            qiandao.setUserId(user.getId());
            Date date=new Date();
            qiandao.setCreateTime(date);
            Qiandao qiandao1 = qiandaoMapper.selectByUserId(user.getId());
            Date date1=qiandao1.getCreateTime();
            int days = (int) ((date.getTime() - date1.getTime()) / (1000*60*60*24));
            Calendar calendar1=Calendar.getInstance();
            calendar1.setTime(date);
            Calendar calendar2=Calendar.getInstance();
            calendar2.setTime(date1);
            if(days>1){
                qiandao.setTotal(1);
            }else {
                qiandao.setTotal(qiandao1.getTotal()+1);
            }
            int i = qiandaoMapper.insertSelective(qiandao);
            if(i>0){
                res.setStatus(0);
                Data d=new Data();
                d.setSigned(true);
                int  qiandaoKiss = 5;
                Qiandao qiandao2 = qiandaoMapper.selectByUserId(user.getId());
                int qiandaoDay=qiandao2.getTotal();

                d.setExperience(qiandaoKiss);
                d.setDays(qiandaoDay);
                res.setData(d);
                user.setKissNum(user.getKissNum()+qiandaoKiss);
                userMapper.updateByPrimaryKeySelective(user);
            }else {
                res.setStatus(1);
                res.setMsg("签到失败");
            }
        }else {
            res.setStatus(1);
            res.setMsg("请先登录");
        }
        response.getWriter().println(JSON.toJSONString(res));
    }


}
