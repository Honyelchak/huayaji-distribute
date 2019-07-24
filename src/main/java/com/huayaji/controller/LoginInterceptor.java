package com.huayaji.controller;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的地址（根域名以外的部分）
        String uri = request.getRequestURI();
        if (uri.indexOf("/index.html") >= 0){
            return true;
        }
        //获取session，有就是说明已经登录，没有就是拦截访问并跳转到登录页面
        HttpSession session = request.getSession();
        String useradmin = (String)session.getAttribute("u_name");
        if (useradmin != null){
            return true;
        }
        request.setAttribute("msg","还没登陆！快去登陆啊！");
        request.getRequestDispatcher("/page/login/login.html").forward(request,response);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
