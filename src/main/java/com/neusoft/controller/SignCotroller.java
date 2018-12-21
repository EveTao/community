package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Qiandao;
import com.neusoft.domain.User;
import com.neusoft.mapper.QiandaoMapper;
import com.neusoft.util.Respons;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("sign")
public class SignCotroller {
    @Autowired
    QiandaoMapper qiandaoMapper;

//    签到
    @RequestMapping("in")
    public void in(String token, HttpServletResponse response,HttpServletRequest request) throws IOException {
        System.out.println(token);
        Respons res=new Respons();
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("userinfo");
//        res.setData(33);
        if(user!=null){
            Qiandao qiandao=new Qiandao();
            qiandao.setUserId(user.getId());
            qiandao.setCreateTime(new Date());
            qiandao.setTotal(1);
            int i = qiandaoMapper.insertSelective(qiandao);
            if(i>0){
                res.setStatus(0);
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
