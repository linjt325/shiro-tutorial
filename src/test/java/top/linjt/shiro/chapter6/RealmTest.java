package top.linjt.shiro.chapter6;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

public class RealmTest extends BaseTestWithInitData {

    /*
    测试正常登陆
     */
    @Test
    public void  testLoginSuccess(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME, PASSWORD);
        Assert.assertEquals(true, getSubject().isAuthenticated());
    }

    /*
    测试不正确用户名
     */
    @Test(expected = UnknownAccountException.class)
    public void testUnknownAccount(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME+"1", PASSWORD);
        Assert.assertEquals(false, getSubject().isAuthenticated());
    }
    /*
    测试不正确密码
     */
    @Test(expected = IncorrectCredentialsException.class)
    public void testIncorrentCredentials(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME, PASSWORD+"12312");
        Assert.assertEquals(false, getSubject().isAuthenticated());
    }
    /*
    测试被锁定用户
     */
    @Test(expected = LockedAccountException.class)
    public void testAccountLocked(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", "wang", PASSWORD);
        Assert.assertEquals(false, getSubject().isAuthenticated());
    }

    /*
    测试多次尝试失败,禁止登陆
     */
    @Test(expected = ExcessiveAttemptsException.class)
    public void testLimitedRetry(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME, PASSWORD);
        for (int i = 0; i < 5; i++) {
            try {
                login(u2.getUsername(), PASSWORD+"121");
            }catch (Exception e){
                //忽略报错
            }
        }
        //在循环外再次login 因为在里面执行的话报错会被捕获
        login(u2.getUsername(), PASSWORD+"121");
        Assert.assertEquals(false, getSubject().isAuthenticated());
    }

    @Test
    public void testHasRole(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME,PASSWORD);
        boolean admin = getSubject().hasRole("admin");

        Assert.assertTrue(admin);

    }

    @Test
    public void testNoRole() {
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME,PASSWORD);
        boolean client = getSubject().hasRole("client");
        Assert.assertFalse(client);

    }

    @Test
    public void testHasPermission(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", USERNAME,PASSWORD);
        boolean user_create = getSubject().isPermitted("user:create");
        boolean permittedAll = getSubject().isPermittedAll("user:delete", "user:create");
        Assert.assertTrue(user_create);
        Assert.assertTrue(permittedAll);

    }

    @Test
    public void testNoPermission(){
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", u2.getUsername(),PASSWORD);
        boolean permitted = getSubject().isPermitted("menu:create");
        Assert.assertFalse(permitted);

    }


    /*
    自动化测试
     */
    @Test
    public void testAll(){

        //读取配置文件 ,并以zhang /123 登陆
        login("classpath:conf/chapter6/shiro-custom-jdbc.ini", "zhang", "123");

        Subject subject = getSubject();

        boolean adminRole = subject.hasRole(r1.getRole());

        Assert.assertEquals(true, adminRole);

        Assert.assertEquals(true, subject.isPermitted("user:create"));
        Assert.assertEquals(true, subject.isPermitted("user:delete"));
        Assert.assertEquals(true, subject.isPermitted("menu:create"));

    }
}
