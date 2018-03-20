package top.linjt.shiro.chapter5.credentialsMatcher;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.core.Ehcache;
import org.ehcache.xml.XmlConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: XxX
 * @date: 2018/3/19
 * @Description:
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache<String,AtomicInteger> passwordRetryCache;

    public RetryLimitCredentialsMatcher() {
        super();
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManager(new XmlConfiguration(getClass().getClassLoader().getResource("conf/chapter5/ehcache.xml")));
        cacheManager.init();
        passwordRetryCache = (Ehcache)cacheManager.getCache("passwordRetryCache",String.class,AtomicInteger.class );
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
            retryCount=new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        //次数自增
        if( retryCount.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException("别试了");
        }
        boolean result = super.doCredentialsMatch(token, info);
        //登陆成功清楚重试次数
        if (result) {
            passwordRetryCache.remove(username);
        }

        return result;
    }
}
