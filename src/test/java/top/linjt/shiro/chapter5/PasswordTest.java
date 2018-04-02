package top.linjt.shiro.chapter5;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Assert;
import org.junit.Test;
import top.linjt.shiro.common.BaseTest;

/**
 * @author: XxX
 * @date: 2018/3/19
 * @Description:
 */
public class PasswordTest extends BaseTest {

    @Test
    /**
     * 默认SHA-256
     * 通过passwordService和HashService 对密码进行计算,验证登陆信息
     */
    public void  testPasswordService (){
        login("classpath:conf/chapter5/shiro-passwordService.ini", "随便填什么,用作演示返回指定的authenticationInfo," +
                "验证时只验证credentials", "123");
        Assert.assertTrue(getSubject().isAuthenticated());
    }

    @Test
    /**
     * 通过passwordService和HashService 对密码进行计算,验证登陆信息
     */
    public void  testJdbcPassword (){
        login("classpath:conf/chapter5/shiro-jdbc-passwordService.ini", "wu", "123");
        Assert.assertTrue(getSubject().isAuthenticated());
    }

    /**
    * 通过credentialsMatcher进行密码验证
    */
    @Test
    public void testCredentials (){
        login("classpath:conf/chapter5/shiro-credentialsMatcher.ini", "liu", "123");
        Assert.assertTrue(getSubject().isAuthenticated());
    }


    /**
     * 通过credentialsMatcher 结合jdbcRealm 进行密码验证
     */
    @Test
    public void testCredentialsWithJdbc (){
        //shiro 默认使用BeanUtils 默认不支持 Enum类型转换,需要自己进行注册Enum的转换器
        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);

        login("classpath:conf/chapter5/shiro-jdbc-credentialsMatcher.ini", "liu", "123");


        Assert.assertTrue(getSubject().isAuthenticated());
    }

    /**
    * 通过credentialsMatcher 结合jdbcRealm 进行密码验证,并实现一段时间内控制重试次数 ,超过次数拒绝登陆请求
    */
    @Test
    public void  testRetryCacheWithCredentialsMatcher () throws InterruptedException {
        //shiro 默认使用BeanUtils 默认不支持 Enum类型转换,需要自己进行注册Enum的转换器
        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);

        login("classpath:conf/chapter5/shiro-jdbc-retryLimitted-credentialsMatcher.ini", "liu", "123");

        for (int i = 0; i < 6; i++) {
            login("liu", "1122");
        }
//        Thread.sleep(1000*20);


        login("liu", "1231");
        Assert.assertTrue(getSubject().isAuthenticated());
    }


    private class EnumConverter extends AbstractConverter {
        @Override
        protected String convertToString(final Object value) throws Throwable {
            return ((Enum) value).name();
        }
        @Override
        protected Object convertToType(final Class type, final Object value) throws Throwable {
            return Enum.valueOf(type, value.toString());
        }

        @Override
        protected Class getDefaultType() {
            return null;
        }

    }
    
}
