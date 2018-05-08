package top.linjt.shiro.chapter12.credentials;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: XxX
 * @date: 2018/3/19
 * @Description:
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String,AtomicInteger> passwordRetryCache;

    public RetryLimitCredentialsMatcher(CacheManager cacheManager) {

        super();
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }


    /**
     * 覆写父类验证登陆方法,在调用父类方法前对尝试次数进行缓存
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String username = token.getPrincipal().toString();
        //获取缓存中的重试次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null){
            retryCount=new AtomicInteger(1);
            passwordRetryCache.put(username, retryCount);//AtomicInteger 对象存入ehcache时 与原对象不是同一个对象 所以需要再次取出缓存的对象才能进行相应的操作,或者直接将尝试次数设为1
        }else{
            //次数自增
            if( retryCount.incrementAndGet() > 5){
                throw new ExcessiveAttemptsException("别试了");
            }
        }

        boolean result = super.doCredentialsMatch(token, info);
        //登陆成功清楚重试次数
        if (result) {
            passwordRetryCache.remove(username);
        }

        return result;
    }
}
