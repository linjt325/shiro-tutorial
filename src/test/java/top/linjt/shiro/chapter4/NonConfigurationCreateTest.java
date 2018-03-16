package top.linjt.shiro.chapter4;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Assert;
import org.junit.Test;
import top.linjt.shiro.common.BaseTest;

import javax.security.auth.Subject;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author: XxX
 * @date: 2018/3/16
 * @Description:
 */
public class NonConfigurationCreateTest extends BaseTest{

    /**
    * 通过java代码创建securityManager
    */
    @Test
    public void  createSecurityManagerByCode (){

        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //创建多个Realm的authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //指定至少一个匹配的验证策略
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        //指定验证器
        securityManager.setAuthenticator(authenticator);

        //创建授权器 authorizer
        ModularRealmAuthorizer authorizer  = new ModularRealmAuthorizer();
        //指定权限解析器
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        //指定授权器
        securityManager.setAuthorizer(authorizer);

        //创建数据源 这里用的是druid 连接池
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123");

        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        realm.setPermissionsLookupEnabled(true);

        securityManager.setRealms(Arrays.asList((Realm) realm));
        SecurityUtils.setSecurityManager(securityManager);

        this.login("zhang", "123");

        Assert.assertTrue( getSubject().isAuthenticated());

    }
}
