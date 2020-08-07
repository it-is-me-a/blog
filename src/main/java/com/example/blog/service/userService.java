package com.example.blog.service;

        import com.example.blog.bean.User;

/**
 * @author Emma
 * @create 2020 - 05 - 22 - 23:56
 */
public interface userService {

    User checkUser(String username, String password);

    Integer updateuserPrsonalviews(Integer index);//更新博客访客人数

}
