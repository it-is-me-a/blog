package com.example.blog.service;

import com.example.blog.bean.Tag;
import com.example.blog.bean.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 分类
 */
public interface tagService {

    //保存一个tag
    Tag saveTag(Tag tag);

    //查询
    Tag getTag(Integer id);

    //分页查询
    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer index);

    //修改
    Tag updateTag(Integer id, Tag tag);

    //删除
    void deleteTag(Integer id);

    //通过名称来查询一个tag
    Tag getTagBytagName(String tagName);
}
