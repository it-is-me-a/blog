package com.example.blog.dao;

import com.example.blog.bean.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 26 - 0:01
 */
public interface blogRepository extends JpaRepository<Blog,Integer>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id=?1")
    int updateViews(Integer id);

    /*查出所有的博客对应的日期*/
    @Query("select function('date_format', b.creatTime, '%Y年%m月') from Blog b group by function('date_format', b.creatTime, '%Y年%m月') order by function('date_format', b.creatTime, '%Y年%m月') desc")
    List<String> findGroupYear();

    /*查出对应日期的博客*/
    @Query("select b from Blog b where function('date_format', b.creatTime, '%Y年%m月') =?1")
    List<Blog> findByYear(String year);
}
