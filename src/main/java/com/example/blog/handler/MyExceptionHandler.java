package com.example.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * 自己的错误处理器：拦截异常并进行处理
 */
@ControllerAdvice   //会拦截所有的标注有@controller的控制器
public class MyExceptionHandler {

    //获取日志：选择slf4j.Logger
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)  //这个注解是标注这个方法是可以做异常处理的;Exception.class代表只要是Exception级别的错误都可以拦截
    public ModelAndView handleException(Exception e, HttpServletRequest request) throws Exception {//可以从request中获取访问的URL
        //记录异常信息:
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

        //判断拦截的请求是否有状态码
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!= null){
            throw e;
        }

        //new 一个 ModelAndView
        ModelAndView mv= new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");//返回到哪个页面

        //返回
        return mv;
    }
}
