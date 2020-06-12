package com.example.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Emma
 * @create 2020 - 06 - 06 - 16:14
 */
@Controller
public class aboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
