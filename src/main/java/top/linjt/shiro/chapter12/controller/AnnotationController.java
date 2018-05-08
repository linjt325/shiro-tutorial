package top.linjt.shiro.chapter12.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: XxX
 * @date: 2018/5/7
 * @Description:
 */
@Controller
@RequestMapping("chapter12")
public class AnnotationController {


    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @GetMapping("hello2")
    @ResponseBody
    @RequiresRoles("admin1")
    public String helloByAnnotation() {
        return "success";
    }


}
