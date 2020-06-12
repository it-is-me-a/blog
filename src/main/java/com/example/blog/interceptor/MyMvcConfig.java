package com.example.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * springmvc 拓展文件
 */
@Configuration  //配置类
public class MyMvcConfig extends WebMvcConfigurationSupport {

    //一旦写了拓展文件，对于springboot2.0以上，就需要静态资源释放
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    //注册拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // addInterceptor ：注册拦截器； addPathPatterns("拦截的请求")； excludePathPatterns(括号中的请求不拦截)：
        registry.addInterceptor(new loginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login","/admin");
    }

    //这个方法是用来解决：关于SpringBoot 2.0提示,Pageable 无法注入,缺少默认构造方法
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册Spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}
