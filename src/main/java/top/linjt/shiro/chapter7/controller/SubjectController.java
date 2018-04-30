package top.linjt.shiro.chapter7.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public ModelAndView login(String error) {
        ModelAndView model = new ModelAndView("login");
        if (error != null && !"".equals(error)) {
            model.addObject("error", error);
        }
        return model;
    }

    /*
    提交登陆用户密码表单 post ; 验证工作都交给shiro的filter做 因此不需要获取到用户密码 ,
    当登陆出错,才会将请求指派到该controller ,
    从req中获取报错信息即可 , 属性名通过ini配置文件指定
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {

        String shiroLoginFailure = (String) req.getAttribute("shiroLoginFailure");
        String error = null;

        if (UsernamePasswordToken.class.getName().equals(shiroLoginFailure)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
            error = "用户名/密码错误";
        } else if (AuthenticationException.class.getName().equals(shiroLoginFailure)) {
            error = "其他错误: "+shiroLoginFailure;
        }

        ModelAndView view = new ModelAndView("login");
        if (error != null) {
//            req.setAttribute("error", error);
            view.addObject("error", error);
            System.out.println(req.getAttribute("error"));
        }
        return view;
//        return  login(error );
//        req.getRequestDispatcher("login").forward(, );
//        try {
//            req.getRequestDispatcher("login").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        return null;
//        return new ModelAndView("login").addObject("error", error);
//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(new UsernamePasswordToken(username, password));
//        } catch (UnknownAccountException e) {
//            error = "用户名/密码错误";
//        } catch (IncorrectCredentialsException e) {
//            error = "用户名/密码错误";
//        } catch (AuthenticationException e) {
//            //其他错误，比如锁定，如果想单独处理请单独catch处理
//            error = "其他错误：" + e.getMessage();
//        }
//        String url = "login";
//        HashMap<String,String> params= null;
//        if (error == null) {
//            SavedRequest savedRequest = WebUtils.getSavedRequest(req);
//
//            if (savedRequest != null) {
//                url = savedRequest.getRequestURI().replace("/shiro", "");
//            }
//        } else {
//            //无法将post请求转为get请求
////            req.getRequestDispatcher("").forward(req, resp);
//            params = new HashMap<>();
//            params.put("error", error);
////            return new ModelAndView("login").addObject("error", error);

//        }
//        WebUtils.issueRedirect(req, resp,url
//                ,params,true);

//        try {
//            req.getRequestDispatcher("/Html/login.jsp").forward(req, resp);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @RequestMapping("isAuthenticated")
    @ResponseBody
    public ModelAndView isAuthenticated() {

//        if (SecurityUtils.getSubject().isAuthenticated()) {
            return new ModelAndView("authenticated");
//        }
//        return null;
    }

    @RequestMapping("isPermitted")
    @ResponseBody
    public ModelAndView isPermitted() {

        //由于在ini配置文件中配置了该url的权限 为 authc,perm[user:create] 所以在进入到该url时已经进行了权限的验证, 因此下面的代码有点重复了
        return new ModelAndView("permitted");
    }

    @GetMapping("hasRole")
    public ModelAndView hasRole(){
        return new ModelAndView("hasRole");
    }

    @GetMapping("unauthorized")
    public ModelAndView unauthorized(){
        return new ModelAndView("noPermission");
    }

}
