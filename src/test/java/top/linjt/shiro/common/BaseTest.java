package top.linjt.shiro.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

/**
 * @author: XxX
 * @date: 2018/3/14
 * @Description:
 */
public class BaseTest {

    @After
    public void tearDown() {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }

    public void createIniSecurityManager(String iniPath) {

        //1. 创建工厂类实例,解析shrio.ini 配置文件 包含了 用户账号密码, 用户角色; 角色权限
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniPath);
        //2. 通过工厂类创建 SecurityManager 实例

        SecurityManager instance = factory.getInstance();

        //3. 将SecurityManager实例应用到 SecurityUtils 中
        SecurityUtils.setSecurityManager(instance);

    }

    public void login(String iniPath,String username,String password) {

        //1. 创建工厂类实例,解析shrio.ini 配置文件 包含了 用户账号密码, 用户角色; 角色权限
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniPath);
        //通过工厂类创建 SecurityManager 实例

        SecurityManager instance = factory.getInstance();

        //2. 将SecurityManager实例应用到 SecurityUtils 中k
        SecurityUtils.setSecurityManager(instance);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
            //4、登录，即身份验证
            subject.login(token);
            System.out.println(subject.getPrincipals());
//        } catch (AuthenticationException e) {
//            //5、身份验证失败
//            e.printStackTrace();
//        }

    }
    /**
    * 已经securityManager的配置,直接获取全局的securityManager进行登录
    */
    public void login(String username,String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
            //4、登录，即身份验证
            subject.login(token);
//        } catch (AuthenticationException e) {
//            //5、身份验证失败
//            e.printStackTrace();
//        }

    }

    /**
    * 获取当前线程绑定的Subject
    */
    public Subject getSubject (){
        return SecurityUtils.getSubject();
    }



}
