package com.example.blog.service;

import com.example.blog.bean.User;
import com.example.blog.dao.userRepository;
import com.example.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 0:19
 */
@Service
public class userServiceImpl implements userService {

    @Autowired
    private userRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code1(password));
        return user;

    }

    @Override
    public Integer updateuserPrsonalviews(Integer index) {
        return userRepository.updatePrsonalviews(index);
    }


}
