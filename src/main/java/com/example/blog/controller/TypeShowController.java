package com.example.blog.controller;

import com.example.blog.bean.BlogQuery;
import com.example.blog.bean.Type;
import com.example.blog.service.blogService;
import com.example.blog.service.typeService;
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
public class TypeShowController {

    @Autowired
    private typeService typeService;

    @Autowired
    private blogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size=10, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Integer id,
                        Model model){
        List<Type> types = typeService.listTypeTop(1000);
        if(id == -1){
            id = types.get(0).getId();/*取页面第一个type对应的blog*/
        }
        model.addAttribute("types",types);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
