package utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;

public class SecurityManagerUtil {

    public static void createIniSecurityManager(String iniPath) {

        //创建工厂类实例,解析shrio.ini 配置文件 包含了 用户账号密码, 用户角色; 角色权限
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(iniPath);
        //通过工厂类创建 SecurityManager 实例
        SecurityManager instance = factory.getInstance();

        //将SecurityManager实例应用到 SecurityUtils 中
        SecurityUtils.setSecurityManager(instance);

    }

}
