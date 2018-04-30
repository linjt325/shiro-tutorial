package top.linjt.shiro.chapter6;

import org.junit.After;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import top.linjt.shiro.chapter6.credentials.RetryLimitCredentialsMatcher;
import top.linjt.shiro.chapter6.pojo.Permission;
import top.linjt.shiro.chapter6.pojo.Role;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.service.PermissionService;
import top.linjt.shiro.chapter6.service.RoleService;
import top.linjt.shiro.chapter6.service.UserService;
import top.linjt.shiro.chapter6.service.impl.PermissionServiceImpl;
import top.linjt.shiro.chapter6.service.impl.RoleServiceImpl;
import top.linjt.shiro.chapter6.service.impl.UserServiceImpl;
import top.linjt.shiro.chapter6.util.JdbcUtil;
import top.linjt.shiro.common.BaseTest;

import java.sql.SQLException;

/**
 * @author: XxX
 * @date: 2018/3/14
 * @Description:
 */
public class BaseTestWithInitData extends BaseTest {

    protected PermissionService permissionService = new PermissionServiceImpl();
    protected RoleService roleService = new RoleServiceImpl();
    protected UserService userService = new UserServiceImpl();


    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected Role r3;
    protected User u1;
    protected User u2;
    protected User u3;
    protected static String USERNAME = "zhang";
    protected static  String PASSWORD = "123";

    @Before
    public void initData() {

    //清除旧数据
        JdbcTemplate jdbcTemplate = JdbcUtil.newInstance().getJdbcTemplate();
        jdbcTemplate.update("delete from sys_users");
        jdbcTemplate.update("delete from sys_roles");
        jdbcTemplate.update("delete from sys_permissions");
        jdbcTemplate.update("delete from sys_users_roles");
        jdbcTemplate.update("delete from sys_roles_permissions");

        //1.新增权限
        p1 = new Permission("user:create", "用户模块", true);
        p2 = new Permission("user:delete", "用户删除", true);
        p3 = new Permission("menu:create", "菜单新增", true);
        try {
            permissionService.createPermission(p1);
            permissionService.createPermission(p2);
            permissionService.createPermission(p3);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //2. 新增角色

        r1 = new Role("admin", "管理员", true);
        r2 = new Role("client", "临时用户", true);
        r3 = new Role("user", "用户", true);

        roleService.createRole(r1);
        roleService.createRole(r2);
        roleService.createRole(r3);
        //3. 角色关联权限

        roleService.correlationPermission(r1.getId(), new Long[]{p1.getId(), p2.getId(), p3.getId()});
        roleService.correlationPermission(r3.getId(), new Long[]{p1.getId(), p2.getId()});

        //4. 新增用户

        u1 = new User("zhang", PASSWORD, "ghajgskdhffgfvaa", false);
        u2 = new User("liu", PASSWORD, "asdgdsasdfss", false);
        u3 = new User("wang", PASSWORD, "asdddsaAFSFGD", true);

        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        //5. 用户关联角色

        userService.correlationRoles(u1.getId(), r1.getId());
        userService.correlationRoles(u3.getId(), r3.getId());

    }

    @After
    public void after(){
        //关闭对缓存文件的锁 ,否则会导致后续测试方法失败
        RetryLimitCredentialsMatcher.close();
    }

}
