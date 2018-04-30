package top.linjt.shiro.chapter6.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.service.UserService;
import top.linjt.shiro.chapter6.service.impl.UserServiceImpl;

import java.util.Objects;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    UserService userServie = new UserServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取用户用户名
        String username = (String) principals.getPrimaryPrincipal();
        //获取角色信息
        Set<String> roles = userServie.findRoles(username);
        //获取权限信息
        Set<String> permissions = userServie.findPermissions(username);
        //设置授权信息,返回给authorizationReam ,根据请求的permission对象的implied() 方法判断是否具有权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = token.getPrincipal().toString();

        User user = userServie.findByUsername(username);

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
}
