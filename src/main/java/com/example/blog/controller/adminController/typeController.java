package com.example.blog.controller.adminController;

import com.example.blog.bean.Type;
import com.example.blog.service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 19:31
 */
@Controller
@RequestMapping("/admin")
public class typeController {

    @Autowired
    private typeService typeService;

    /*
    跳转到分类列表页面
        Pageable 是这个类下的：org.springframework.data.domain.Pageable;
        @PageableDefault(size=10,sort = {"id"},direction = Sort.Direction.DESC)  指定分页的一些配置：大小为10，按照id排序，逆序排序
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //新增一个分类
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type_input";
    }

    //编辑type:现在页面显示之前的type
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Integer id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type_input";
    }

    //提交一个分类
    @PostMapping("/types")   //@Valid代表要校验这个对象;BindingResult接受校验后结果
    public String post(@Valid Type type,
                       BindingResult result,
                       RedirectAttributes attributes){

        Type type1 = typeService.getTypeBytypeName(type.getTypeName());
        if(type1 != null){
            //已经存在
            result.rejectValue("typeName","typeNameError","分类名称不能重复");
        }

        if(result.hasErrors()){
            return "admin/type_input";
        }

        Type t = typeService.saveType(type);

        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    //编辑type
    @PostMapping("/types/{id}")   //@Valid代表要校验这个对象;BindingResult接受校验后结果,而且BindingResult必须要与前一个要校验的紧挨着
    public String editPost(@Valid Type type,
                       BindingResult result,
                       @PathVariable Integer id,
                       RedirectAttributes attributes){
        //校验
        Type type1 = typeService.getTypeBytypeName(type.getTypeName());
        if(type1 != null){
            //已经存在
            result.rejectValue("typeName","typeNameError","分类名称不能重复");
        }
        if(result.hasErrors()){
            return "admin/type_input";
        }

        Type t = typeService.updateType(id,type);
        if(t == null){
            //新增失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    //删除type
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Integer id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
