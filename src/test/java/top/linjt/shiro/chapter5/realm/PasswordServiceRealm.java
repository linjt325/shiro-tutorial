package top.linjt.shiro.chapter5.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class PasswordServiceRealm extends AuthorizingRealm {

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    private PasswordService passwordService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //用作演示,正常应该是在创建账户时进行加密保存到数据库,从数据库取出散列计算后的密码
        return new SimpleAuthenticationInfo("zhang",passwordService.encryptPassword("123"),getName());
    }
}
