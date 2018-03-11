package top.linjt.shiro.chapter2.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class Realm1 implements Realm {
    public String getName() {
        return "myRealm1";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        //只支持UsernamePasswordToken类型
        return authenticationToken instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        //死循环.. 因为每次验证都会进入到这个Realm中
//        return SecurityUtils.getSecurityManager().authenticate(authenticationToken);

        if(!username .equals("aihe")){
            throw new UnknownAccountException();
        }
        if (!password.equals("aihe")) {
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username+"-1",password,this.getName());
    }
}
