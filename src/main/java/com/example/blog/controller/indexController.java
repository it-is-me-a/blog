package com.example.blog.controller;

import com.example.blog.service.blogService;
import com.example.blog.service.tagService;
import com.example.blog.service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.blog.util.TimerUtil;

/**
 * @author Emma
 * @create 2020 - 05 - 21 - 15:49
 */
@Controller
public class indexController {

    @Autowired
    private blogService blogService;
    @Autowired
    private typeService typeService;
    @Autowired
    private tagService tagService;

    @GetMapping({"/","/index.html"})
    public String index(@PageableDefault(size=8, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        /*拿到blog,type,tag的数据显示在首页*/
        model.addAttribute("page",blogService.listBlog(pageable));
        /*拿到排名前8的type展示在对应区域*/
        model.addAttribute("types",typeService.listTypeTop(8));
        /*拿到排名前10的tag展示在对应区域*//*
        model.addAttribute("tags",tagService.listTagTop(10));*/
        /*拿到高考倒计*/
        model.addAttribute("countdown", TimerUtil.fun());
        /*拿到排名前5的文章展示在热门文章*/
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(5));
        /*拿到最新的前5的文章展示在最新文章*/
        model.addAttribute("NewBlogs",blogService.listRecommendBlogTop(5));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size=8, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query,
                         Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Integer id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }
}
