package top.linjt.shiro.chapter12.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.service.UserService;
import top.linjt.shiro.chapter6.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService ;

    public void setuserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取用户用户名
        String username = (String) principals.getPrimaryPrincipal();
        //获取角色信息
        Set<String> roles = userService.findRoles(username);
        //获取权限信息
        Set<String> permissions = userService.findPermissions(username);
        //设置授权信息,返回给authorizationReam ,根据请求的permission对象的implied() 方法判断是否具有权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = token.getPrincipal().toString();

        User user = userService.findByUsername(username);

        //找不到账户
        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        //账户锁定
        if (Objects.equals(true, user.getLocked())) {
            throw new LockedAccountException();
        }

        return new SimpleAuthenticationInfo(username,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());

    }

    @Override
    public boolean isCachingEnabled() {
        return super.isCachingEnabled();
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    public static void main(String[] args) {
//        Random rand = new Random();
//
//        byte[] bytes = new byte[16];
//
//        rand.nextBytes(bytes);
//
//        String s = Base64.encodeToString(bytes);
//        System.out.println(s);

        String base64 = "J5AuoePRpY4H57sdadblpg==";
        byte[] decode1 = Base64.decode("J5AuoePRpY4H57sdadblpg==");
        byte[] decode = Base64.decode(base64);


        for (byte b : decode1) {
            System.out.print(b+",");
        }
        System.out.println();
        System.out.println("============");
        for (byte b : decode) {
            System.out.print(b+",");
        }

        System.out.println();
        System.out.println(decode.length);
    }

}
