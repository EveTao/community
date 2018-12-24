package com.neusoft.controller;

import com.alibaba.fastjson.JSON;
import com.neusoft.domain.Qiandao;
import com.neusoft.domain.User;
import com.neusoft.mapper.QiandaoMapper;
import com.neusoft.mapper.UserMapper;
import com.neusoft.util.Data;
import com.neusoft.util.GetKissnum;
import com.neusoft.util.Respons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("sign")
public class SignController {
    @Autowired
    QiandaoMapper qiandaoMapper;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("in")
    public void signIn(HttpSession httpSession, HttpServletResponse response) throws IOException {
        Respons res = new Respons();
        Data data = new Data();
        res.setData(data);
        data.setDays(1);
        data.setExperience(5);
        data.setSigned(true);

        User user = (User)httpSession.getAttribute("userinfo");
        if(user == null)
        {
            res.setStatus(1);
            res.setMsg("请先登录");
        }
        else
        {
            res.setStatus(0);
            Qiandao qiandao = qiandaoMapper.selectByUserId(user.getId());
            if(qiandao != null)
            {
//                判断昨天是否签到
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(new Date());

                Calendar calendarLastTime = Calendar.getInstance();
                calendarLastTime.setTime(qiandao.getCreateTime());

                calendarNow.add(Calendar.DATE, -1);
                int date = calendarNow.get(Calendar.DATE);
                int lasttime = calendarLastTime.get(Calendar.DATE);
                if(date == lasttime)
                {
                    qiandao.setCreateTime(new Date());
                    qiandao.setTotal(1 + qiandao.getTotal());
                    qiandaoMapper.updateByPrimaryKeySelective(qiandao);
                    GetKissnum getKissnum=new GetKissnum();
                    int kissnum = getKissnum.getKisssnum(1 + qiandao.getTotal());
                    User user1 = userMapper.selectByPrimaryKey(user.getId());
                    user1.setKissNum(kissnum + user1.getKissNum());
                    userMapper.updateByPrimaryKeySelective(user1);
                    data.setDays(qiandao.getTotal());
                    data.setExperience(kissnum);
                }
                else
                {
                    qiandao.setCreateTime(new Date());
                    qiandao.setTotal(1);
                    qiandaoMapper.updateByPrimaryKeySelective(qiandao);
                    User user1 = userMapper.selectByPrimaryKey(user.getId());
                    user1.setKissNum(5+user1.getKissNum());
                    userMapper.updateByPrimaryKeySelective(user1);

                }
            }
            else
            {
                Qiandao qiandao1 = new Qiandao();
                qiandao1.setUserId(user.getId());
                qiandao1.setCreateTime(new Date());
                qiandao1.setTotal(1);
                qiandaoMapper.insertSelective(qiandao1);

                User user1 = userMapper.selectByPrimaryKey(user.getId());
                user1.setKissNum(5+user1.getKissNum());
                userMapper.updateByPrimaryKeySelective(user1);
            }
        }
        response.getWriter().println(JSON.toJSONString(res));
    }
    @RequestMapping("top")
    public void getTop(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Respons res=new Respons();
//        uid,picPath,nickname,time，days
//        最新，最快，总榜
        int uid=1;
        List<Map<String, Object>> mapNew = qiandaoMapper.selectNew();
        for (Map map:mapNew) {
            String picPath= request.getServletContext().getContextPath()+"/res/uploadImg/"+map.get("picPath");
            map.put("picPath",picPath);
        }
        List<Map<String, Object>> mapFast = qiandaoMapper.selectFast();
        for (Map map:mapFast) {
            String picPath= request.getServletContext().getContextPath()+"/res/uploadImg/"+map.get("picPath");
            map.put("picPath",picPath);
        }
        List<Map<String, Object>> mapTop = qiandaoMapper.selectTop();
        for (Map map:mapTop) {
            String picPath= request.getServletContext().getContextPath()+"/res/uploadImg/"+map.get("picPath");
            map.put("picPath",picPath);
        }
        List list=new ArrayList();
        list.add(mapNew);
        list.add(mapFast);
        list.add(mapTop);
        res.setDataList(list);
        res.setStatus(0);
        response.getWriter().println(JSON.toJSONString(res));
    }
}
