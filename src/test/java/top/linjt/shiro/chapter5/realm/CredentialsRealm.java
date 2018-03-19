package top.linjt.shiro.chapter5.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * @author: XxX
 * @date: 2018/3/19
 * @Description:
 */
public class CredentialsRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //省略
        return null;
    }

    /**
    * 获取账号的信息,用于计较输入是否正确
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String salt2 = "24520ee264eab73ec09451d0e9ea6aac" ;
        DefaultHashService hashService = new DefaultHashService();
//        hashService.setHashAlgorithmName("MD5");
//        hashService.setHashIterations(2);
//        hashService.setPrivateSalt(new SimpleByteSource(username + salt2));
        HashRequest request = new HashRequest.Builder().setSource(new SimpleByteSource((char[])token.getCredentials()))
                .setAlgorithmName("md5")
                .setIterations(2)
                .setSalt(username + salt2).build();
        String hash = hashService.computeHash(request).toString();

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, hash, getName());

        //指定该验证信息的盐 否则在匹配输入的账号密码时无法进行正确的计算
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username+salt2));
        return simpleAuthenticationInfo;
    }
}
