package top.linjt.shiro.chapter8.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author: XxX
 * @date: 2018/4/30
 * @Description: 自定义登陆验证过滤器, 添加了 登陆后session的有效时间  ;
 * 可以重写登陆代码  添加 验证码功能  先验证验证码是否正确 如果不对直接返回登陆失败
 */
public class FormAuthenticationFilterWithSessionTimeout extends FormAuthenticationFilter {

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        return super.executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //登录成功后设置session的超时时间为10s, 也可以通过sessionListener  进行配置 或者在配置文件中指定全局的超时时间
        subject.getSession().setTimeout(10000);
        return super.onLoginSuccess(token, subject, request, response);
    }
}
