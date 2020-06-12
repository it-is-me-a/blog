package com.example.blog.controller;

import com.example.blog.bean.BlogQuery;
import com.example.blog.bean.Tag;
import com.example.blog.service.blogService;
import com.example.blog.service.tagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 06 - 03 - 19:24
 */
@Controller
public class TagShowController {

    @Autowired
    private tagService tagService;

    @Autowired
    private blogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size=10, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Integer id,
                        Model model){
        List<Tag> tags = tagService.listTagTop(1000);
        if(id == -1){
            id = tags.get(0).getId();/*取第一个*/
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
