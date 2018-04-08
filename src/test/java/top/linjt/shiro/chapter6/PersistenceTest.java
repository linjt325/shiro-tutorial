package top.linjt.shiro.chapter6;

import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import top.linjt.shiro.chapter6.dao.UserDao;
import top.linjt.shiro.chapter6.dao.impl.PermissionDaoImpl;
import top.linjt.shiro.chapter6.dao.impl.UserDaoImpl;
import top.linjt.shiro.chapter6.pojo.Permission;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.util.JdbcUtil;

import java.sql.SQLException;
import java.util.Set;

public class PersistenceTest extends BaseTestWithInitData {

    @Test
    public void testJdbc(){
        JdbcUtil jdbcUtil = JdbcUtil.newInstance();
    }

    @Test
    public void testAddPermission() throws SQLException {
        permissionService.createPermission(new Permission("1232","测试",true));
    }

    @Test
    public void testAddUser() {
        String username = "zhang";
        String password = "123";
        String salt = "wertyuioopkas";
        DefaultHashService hashs = new DefaultHashService();
//        hashs.setHashAlgorithmName("MD5");
//        hashs.setHashIterations(2);
        HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
                .setIterations(2)
                .setSource(password)
                .setSalt(username + salt).build();
        Hash hash = hashs.computeHash(request);

        User user = new User(username, hash.toHex(), salt, false);

        userService.createUser(user);


    }
    @Test
    public void testfindRole() {
        System.out.println(userService.findRoles("zhang"));
    }

    /*
    测试用户角色权限的数据库操作是否正确
     */
    @Test
    public void testUserRolePermissionRelation(){
        //zhang
        //roles
        Set<String> u1Roles = userService.findRoles(u1.getUsername());
        Assert.assertEquals(1, u1Roles.size());
        Assert.assertEquals(true,u1Roles.contains(r1.getRole()));
        //permissions
        Set<String> u1P = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(true, u1P.contains(p1.getPermission()));
        Assert.assertEquals(true, u1P.contains(p2.getPermission()));
        Assert.assertEquals(true, u1P.contains(p3.getPermission()));

        //liu
        Set<String> u2Roles = userService.findRoles(u2.getUsername());
        Assert.assertEquals(true, null == u2Roles);
        Set<String> u2P = userService.findPermissions(u2.getUsername());
        Assert.assertEquals(true, null == u2P);

        //wang

        Set<String> u3Roles = userService.findRoles(u3.getUsername());
        Assert.assertEquals(1, u3Roles.size());
        Set<String> u3P = userService.findPermissions(u3.getUsername());
        Assert.assertEquals(true, u3P.contains(p1.getPermission()));
        Assert.assertEquals(true, u3P.contains(p2.getPermission()));


        //测试权限的解除关联

        //解除角色权限
        roleService.unCorrelationPermission(r1.getId(), p2.getId());
        Set<String> permissions = userService.findPermissions(u1.getUsername());
        Assert.assertEquals(2, permissions.size());//解除admin的一个权限后还剩下两个
        //解除用户角色
        userService.unCorrelationRoles(u1.getId(), r1.getId());
        Assert.assertEquals(true, null == userService.findRoles(u1.getUsername()));
        Assert.assertEquals(true, null == userService.findPermissions(u1.getUsername()));

    }



}