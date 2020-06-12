package com.example.blog.controller.adminController;

import com.example.blog.bean.Blog;
import com.example.blog.bean.BlogQuery;
import com.example.blog.bean.Tag;
import com.example.blog.bean.User;
import com.example.blog.service.blogService;
import com.example.blog.service.tagService;
import com.example.blog.service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Emma
 * @create 2020 - 05 - 23 - 17:08
 */
@Controller
@RequestMapping("/admin")
public class blogController {

    private static final String INPUT="admin/blog_input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private blogService blogService;
    @Autowired
    private typeService typeService;
    @Autowired
    private tagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){

        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    /*Ajax 局部刷新*/
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2, sort = {"creatTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){

        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList"; /*返回blogs页面的blogList片段*/
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);

        /*初始化一个blog*/
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        setTypeAndTag(model);
        Blog b = blogService.getBlog(id);
        b.init();//将blog中的集合转为字符串，然后放入页面中
        model.addAttribute("blog", b);
        return INPUT;
    }

    private void setTypeAndTag(Model model){
        /*初始化分类和标签*/
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog,
                       RedirectAttributes attributes,
                       HttpSession session){

        blog.setUser((User) session.getAttribute("user"));/*拿到当前用户*/
        blog.setType(typeService.getType(blog.getType().getId()));

        /*拿到前端传进来的tags，并进行处理.如果有新的tags，就进行保存*/
        String ids = blog.getTagIds();
        ids = handleTags(ids);
        blog.setTags(tagService.listTag(ids));

        Blog b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if(b == null){
            //新增失败
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }



    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

    /*处理传进来的tag,如果tag有新的，进行保存*/
    private String handleTags(String ids){
        boolean num = false;//用来记录是否有新的tag,有的话是true

        /*分割字符串*/
        String[] idarray = ids.split(",");
        for(int i =0; i<idarray.length; i++){
            //判断此字符串是否为数字
            boolean b = StringUtils.isNumeric(idarray[i]);
            if(b == false){
                //不是数字，则添加新tag，并将新tag的id放入数组
                Tag t = tagService.saveTag(new Tag(idarray[i]));
                Integer idnew = t.getId();
                idarray[i] = idnew.toString();
                num = true;
            }
        }
        return num == true? tagsToIds(idarray) : ids;
    }
    /*将数组转为字符串*/
    private String tagsToIds(String[] idarray) {
        StringBuffer ids = new StringBuffer();
        boolean flag = false;
        for (String s : idarray) {
            if (flag) {
                ids.append(",");
            } else {
                flag = true;
            }
            ids.append(s);
        }
        return ids.toString();
    }


}
