package com.example.blog.dao;

import com.example.blog.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:20
 */
public interface typeRepository extends JpaRepository<Type,Integer> {

    Type findByTypeName(String typeName);

    @Query("select t from Type t ")  //自定义查询
    List<Type> findTop(Pageable pageable);
}
