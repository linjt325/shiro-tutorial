package top.linjt.shiro.chapter3;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import top.linjt.shiro.common.BaseTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author: XxX
 * @date: 2018/3/14
 * @Description:
 */
public class AuthorizeByRoleTest extends BaseTest {

    /**
     * 基于角色的访问控制（隐式角色）
     * 粗粒度: 通过判断用户是否具有指定的角色 来判断是否能进行某些操作
     */
    @Test
    public void testHasRole() {

        this.login("classpath:conf/chapter3/shiro-role.ini", "aihe", "aihe");

        Subject subject = this.getSubject();

        //断言subject拥有client角色
        Assert.assertEquals(true, subject.hasRole("client") );
        //断言subject同时拥有给定的所有角色
        List a = Arrays.asList(new String[]{"client","goodguy"});
        Assert.assertEquals(true, subject.hasAllRoles(Arrays.asList("client","goodguy")) );
        //hasRoles 对每一个角色进行验证  返回 boolean[]
        boolean[] results = subject.hasRoles(Arrays.asList("client", "badguy"));
//        Assert.assertEquals(false,results[0] );
        Assert.assertEquals(false,results[1] );
    }

    /*
    * 与hasRole 不同的是,当checkRole 为false时,会报 UnauthorizedException 异常
     */
    @Test(expected = UnauthorizedException.class)
    public void  testCheckRole (){
        this.login("classpath:conf/chapter3/shiro-role.ini", "aihe", "aihe");
        Subject subject = this.getSubject();
        subject.checkRole("role1");
        subject.checkRole("goodguy");

    }

    /**
     * 基于资源的访问控制（显式角色）
     * 细粒度: 通过判断用户是否具有指定的权限 来判断是否能进行某些操作,
     * 当某个觉得的权限发生变化,不需要修改代码,只需要将该角色的权限集合中将对应的权限去掉即可
     */
    @Test
    public void  testIsPermitted (){
        this.login("classpath:conf/chapter3/shiro-permission.ini", "aihe", "aihe");
        Subject subject = this.getSubject();
        Assert.assertTrue(subject.isPermitted("look:*"));
        Assert.assertEquals(true, subject.isPermittedAll("look:*","winnebago:drive:eagle5"));
        Assert.assertEquals(false, subject.isPermitted("not:exist"));
    }

    /**
    * checkPermission 如果没有权限报UnauthorizedException 错误
    */
    @Test//(expected = UnauthorizedException.class)
    public void  testCheckPermission (){
        this.login("classpath:conf/chapter3/shiro-permission.ini", "aihe", "aihe");
        Subject subject = this.getSubject();

        subject.checkPermission("look");
        subject.checkPermissions("look:*","winnebago:drive:eagle5,santana");
        subject.checkPermissions("look1:*");
    }
}
