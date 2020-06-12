package com.example.blog.service;

import com.example.blog.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:12
 */
public interface typeService {

    //保存一个type
    Type saveType(Type type);

    //查询
    Type getType(Integer id);

    //分页查询
    Page<Type> listType(Pageable pageable);

    //查询所有且不分页
    List<Type> listType();

    //查询排名前几的type展示在首页上
    List<Type> listTypeTop(Integer index);

    //修改
    Type updateType(Integer id, Type type);

    //删除
    void deleteType(Integer id);

    //通过名称来查询一个type
    Type getTypeBytypeName(String typeName);
}
