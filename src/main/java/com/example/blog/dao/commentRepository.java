package com.example.blog.dao;

import com.example.blog.bean.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author Emma
 * @create 2020 - 06 - 02 - 11:22
 */
public interface commentRepository extends JpaRepository<Comment,Integer> {
    /*按照blogId查找，并且ParentComment为空*/
    /*List<Comment> findCommentListByBlogIdAndParentCommentIsNull(Integer blogId, Sort sort);*/
    List<Comment> findByBlogIdAndParentCommentNull(Integer blogId, Sort sort);

}
