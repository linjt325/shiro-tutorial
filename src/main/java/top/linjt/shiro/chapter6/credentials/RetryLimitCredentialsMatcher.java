package top.linjt.shiro.chapter6.credentials;

//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Ehcache;
//import net.sf.ehcache.Element;
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

    private static CacheManager cacheManager;

//    private Ehcache passwordRetryCache;
//
//    public RetryLimitCredentialsMatcher() {
//        CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("conf/ehcache.xml"));
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//    }

    public static void close(){
//        passwordRetryCache.close();
        if(cacheManager != null ){
            cacheManager.getStatus();
            cacheManager.close();
        }
    }
    public RetryLimitCredentialsMatcher() {
        super();
        cacheManager = CacheManagerBuilder
                .newCacheManager(new XmlConfiguration(getClass().getClassLoader().getResource("conf/chapter5/ehcache.xml")));
//        cacheManager.close();
//        System.out.println(cacheManager.getStatus());
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
//        Element retryCount = passwordRetryCache.get(username);
        if(retryCount == null){
//            retryCount = new Element(username, new AtomicInteger(1));
//            passwordRetryCache.put(retryCount);
            retryCount=new AtomicInteger(1);
            passwordRetryCache.put(username, retryCount);//AtomicInteger 对象存入ehcache时 与原对象不是同一个对象 所以需要再次取出缓存的对象才能进行相应的操作,或者直接将尝试次数设为1
        }else{
            //次数自增
//            AtomicInteger count = (AtomicInteger) retryCount.getObjectValue();
            if( retryCount.incrementAndGet() > 5){
                throw new ExcessiveAttemptsException("别试了");
            }
//            if( count.incrementAndGet() > 5){
//                throw new ExcessiveAttemptsException("别试了");
//            }
        }


        boolean result = super.doCredentialsMatch(token, info);
        //登陆成功清楚重试次数
        if (result) {
            passwordRetryCache.remove(username);
        }

        return result;
    }
}
