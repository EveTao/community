package com.neusoft.interceptor;

import com.neusoft.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session=httpServletRequest.getSession();
        User user = (User)session.getAttribute("userinfo");
        if(user!=null){
//            邮箱是否激活
            if(user.getActiveState() == 1)
            {
                return true;
            }
            else
            {
                httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/user/active.jsp").forward(httpServletRequest,httpServletResponse);
            }
        }else {
            httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(httpServletRequest,httpServletResponse);
            if(session.getAttribute("referer")==null||session.getAttribute("referer")==""){
                String referer = httpServletRequest.getRequestURI();
                session.setAttribute("referer",referer);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
