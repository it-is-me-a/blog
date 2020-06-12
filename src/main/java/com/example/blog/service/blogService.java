package com.example.blog.service;

import com.example.blog.bean.Blog;
import com.example.blog.bean.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Emma
 * @create 2020 - 05 - 25 - 23:49
 */
public interface blogService {

    Blog saveBlog(Blog blog);

    Blog getBlog(Integer id);

    Blog getAndConvert(Integer id);

    /*在搜索框搜索文章的时候*/
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    /*按照分页的方式取出blog*/
    Page<Blog> listBlog(Pageable pageable);

    /*按照tagId*/
    Page<Blog> listBlog(Integer tagId, Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer index);/*拿到排名前几的文章，按照views的倒序进行排列*/

    List<String> blogCreateTime();/*查出所有的博客的创建时间*/

    Map<String,List<Blog>> archivesBlog();/*查出对应日期的博客，这里的日期也是从数据库查出来的*/

    List<Blog> archivesBlog(String time);/*查出对应日期的博客，这里的日期是自己输入的*/

    Long countBlog();/*计算总共有多少博客*/

    Blog updateBlog(Integer id, Blog blog);

    void deleteBlog(Integer id);

}
