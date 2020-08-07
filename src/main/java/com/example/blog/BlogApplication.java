package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //在启动器上标注了@EnableTransactionManagement注解来开启事务支持，然后在方法中使用@Transactional注解即可进行事务开发
public class BlogApplication extends SpringBootServletInitializer {

    //重写对servlet的初始化方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(BlogApplication.class);//你的项目启动类名
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
