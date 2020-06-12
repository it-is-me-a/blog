package com.example.blog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Emma
 * @create 2020 - 05 - 21 - 19:58
 */
@Aspect  //切面类必须加上的注解
@Component  //开启组件扫描，也是必须的
public class LogAspect {

    //拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.blog.controller.*.*(..))")  //声明这是一个切面;execution()规定切面是拦截哪些类
    public void log(){

    }


    //定义切面之前做的事情
    @Before("log()")
    public void doBefore(JoinPoint joinpoint){ //import org.aspectj.lang.JoinPoint;
        //获取url与IP
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //想要获取运行方法名，就要通过JoinPoint
        String classMethod = joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName(); //getDeclaringTypeName()获取类的全路径名；getTypeName()获取方法名
        //获取请求参数
        Object[] args = joinpoint.getArgs();

        //封装到对象中
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        logger.info("Request : {}",requestLog);

    }

    //定义切面之后做的事情
    @After("log()")
    public void doAfter(){
    }

    //定义切面返回之后做的事情
    @AfterReturning(returning = "result", pointcut = "log()") //这样才可以通过result来捕获返回的值
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    //内部类，封装日志记录的内容
    private class RequestLog{
        private String url;
        private String ip;
        private String ClassMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            ClassMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", ClassMethod='" + ClassMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
