package top.linjt.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final transient Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        //创建工厂类实例,解析shrio.ini 配置文件 包含了 用户账号密码, 用户角色; 角色权限
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:conf/shiro.ini");
        //通过工厂类创建 SecurityManager 实例

        SecurityManager instance = factory.getInstance();

//            将SecurityManager实例应用到 SecurityUtils 中
        SecurityUtils.setSecurityManager(instance);

        //获取Subject
        Subject subject = SecurityUtils.getSubject();

        Session session = subject.getSession();
        //对该subject 设置属性
        session.setAttribute("attr1", "value1");

        //如果该subject 没有过进行验证
        if (!subject.isAuthenticated()) {
            //创建token
            UsernamePasswordToken token = new UsernamePasswordToken("aihe", "aihe");
            //记住密码
            token.setRememberMe(true);

            try {
                //登陆
                subject.login(token);

                //用户身份
                log.info("USER[" + token.getPrincipal() + "] logged in successfully");

                // 查看用户是否有指定的角色
                if (subject.hasRole("client")) {
                    log.info("your role is client");
                } else {
                    log.info(".....");
                }
                //查看用户是否具有指定的权限
                if (subject.isPermitted("loog:desk")) {
                    log.info("you can loog at the desk");
                } else {
                    log.info("别瞎几把看");
                }

                if (subject.isPermitted("ali:1")) {
                    log.info("you can do this");
                } else {
                    log.info("you cant do this ");
                }

                //登出
                subject.logout();

            } catch (AuthenticationException e) {
                e.printStackTrace();
                log.info("buxing");
            }
            assertMe(subject.isAuthenticated());

//                System.out.println(subject.isAuthenticated());
//                System.out.println(subject.isAuthenticated());
        }
        System.exit(0);
    }

    private static void assertMe(boolean bool) {

        //要开启assert 才会进行验证  vm option中加 -ea参数
        assert bool ? true : false;
        System.out.println(bool);
    }
}


