package com.example.blog.dao;

import com.example.blog.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 0:13
 */
public interface userRepository extends JpaRepository<User,Integer> {

    //如果继承的没有这个方法，就直接写在这里,jpa会实现的
    User findByUsernameAndPassword(String username, String password);

    @Transactional
    @Modifying
    @Query("update User u set u.personalviews=?1")
    int updatePrsonalviews(Integer index);
}
