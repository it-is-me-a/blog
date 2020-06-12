package com.example.blog.controller.adminController;

import com.example.blog.bean.User;
import com.example.blog.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录的控制器
 */
@Controller
@RequestMapping("/admin") //加一个全局的映射
public class loginController {

    @Autowired
    private userService userService;

    @GetMapping  //什么都不加，默认访问admin就会跳转到
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){ //@RequestParam:post提交的方式，用它来接收参数
        User user = userService.checkUser(username, password);
        if(user != null){
            user.setPassword(null);//不要把密码传到前端
            session.setAttribute("user",user);
            return "admin/homePage";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");//用重定向的方式返回，还需要携带错误信息的：用RedirectAttributes
            return "redirect:/admin"; //不要直接写死返回页面，再次登录会有问题
        }
    }

    //注销当前登录用户
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user"); //将user的信息清空
        return "redirect:/admin";
    }

}
