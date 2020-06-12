package com.example.blog.dao;

import com.example.blog.bean.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 25 - 16:15
 */
public interface tagRepository extends JpaRepository<Tag,Integer> {
    Tag findByTagName(String tagName);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
