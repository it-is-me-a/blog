package com.example.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *拦截器
 */
public class loginInterceptor implements HandlerInterceptor {

    //预处理方法，在请求未到达之前进行处理
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(request.getSession().getAttribute("user") == null){
            //说明未登陆，返回登陆页面
            response.sendRedirect("/admin");//重定向
            return false; //不放行
        }

        return true;
    }
}
