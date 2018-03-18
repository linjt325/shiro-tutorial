package top.linjt.shiro.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import utils.SecurityManagerUtil;

public class LoginLogoutTest {


    @Test
    public void testHelloworld() {
        //封装了 创建securityManager 并绑定到securityUtils的过程
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro.ini");
    /*    //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:conf/chapter2/shiro.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
*/
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @Test
    public void testCustomRealm() {
        //封装了 创建securityManager 并绑定到securityUtils的过程
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro-realm.ini");

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @Test
    public void testMultiCustomRealm() {
        //封装了 创建securityManager 并绑定到securityUtils的过程
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro-multiRealm.ini");

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");
        try {
            //4、登录，即身份验证
            subject.login(token);

            System.out.println(subject.getPrincipals());
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @Test
    public void testJDBCRealm() {
        //封装了 创建securityManager 并绑定到securityUtils的过程
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro-jdbc-realm.ini");

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "1123");
        try {
            //4、登录，即身份验证
            subject.login(token);

            System.out.println(subject.getPrincipals());
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }

    @Test
    /**
     * 将authenticationStratetg指定为AllSuccessfulStrategy时
    * 当账号密码通过所有的realm验证时才登陆成功
    */
    public void  testAuthenticateStrategyAllSuccessfulWithSuccess (){
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro-authenticator-all-success.ini");

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");

        subject.login(token);

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }


    /**
     * * 将authenticationStratetg指定为AllSuccessfulStrategy时
     * 当账号密码在任意一个realm中没有通过验证,登陆失败
     */
    @Test
    public void  testAuthenticateStrategyAllSuccessfulWithFail (){
        SecurityManagerUtil.createIniSecurityManager("classpath:conf/chapter2/shiro-authenticator-all-fail.ini");

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");

        subject.login(token);

        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录

        //6、退出
        subject.logout();
    }


    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
