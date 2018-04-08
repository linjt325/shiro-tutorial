package top.linjt.shiro.chapter7.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("chapter7")
public class SubjectController {


    @RequestMapping(value = {"index", ""})
    @ResponseBody
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");

        return model;
    }

    /*
    进入登陆页面  get
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView login() {
        ModelAndView model = new ModelAndView("login");

        return model;
    }

    /*
    提交登陆用户密码表单 post
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String username, String password) {
        String error = null;
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }

        if (error == null) {
            return new ModelAndView("loginSuccess");
        } else {
            return new ModelAndView("login").addObject("error", error);
        }

    }

    @RequestMapping("isAuthenticated")
    @ResponseBody
    public ModelAndView isAuthenticated() {

        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new ModelAndView("authenticated");
        }
        return null;
    }

    @RequestMapping("isPermitted")
    @ResponseBody
    public ModelAndView isPermitted() {

        if (SecurityUtils.getSubject().isPermitted("user:create")) {
            return new ModelAndView("permitted");
        } else {
            return new ModelAndView("noPermission");
        }
    }

    @GetMapping("hasRole")
    public ModelAndView hasRole(){
        if (SecurityUtils.getSubject().hasRole("admin")) {
            return new ModelAndView("hasRole");
        }
            return new ModelAndView("noRole");
    }
}
