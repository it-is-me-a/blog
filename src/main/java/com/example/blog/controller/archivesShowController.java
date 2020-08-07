package com.example.blog.controller;

import com.example.blog.bean.Blog;
import com.example.blog.service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Emma
 * @create 2020 - 06 - 04 - 20:54
 */
@Controller
public class archivesShowController {

    @Autowired
    private blogService blogService;

    @GetMapping("/archives/{time}")
    public String archives(@PathVariable String time,
                           Model model){

        /*1. 先拿到所有的时间*/
        List<String> times = blogService.blogCreateTime();
        /*2. 对页面状态进行判断*/
        if("-1".equals(time)){
            //如果刚进入页面，就显示最新的时间对应的文章
            time = times.get(0);
        }
        /*3. 如果有了对应的time，那么就将对应的文章放入session*/
        model.addAttribute("archivesBlogs", blogService.archivesBlog(time));//这里的blogService.archivesBlog()返回的是List<Blog>
        /*4. 将对应的所有时间放入session*/
        model.addAttribute("archivesTimes",times);
        /*5. 将数据库中的博客总数放入session*/
        model.addAttribute("blogCount",blogService.countBlog());
        /*6. 将目前输入的时间放入session中*/
        model.addAttribute("activeTime",time);
        return "archives";
    }

}
