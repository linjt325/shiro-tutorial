package top.linjt.shiro.chapter10.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.Collection;

/**
 * @author: XxX
 * @date: 2018/5/3
 * @Description: 实现SessionListener 接口 , 用于监听会话的创建,过期,以及停止事件
 */
public class MySessionListener implements SessionListener {


    @Override
    public void onStart(Session session) {
        System.out.println("会话创建:"+session.getId());
    }

    @Override
    public void onStop(Session session) {
        Collection<Object> attributeKeys = session.getAttributeKeys();
        System.out.println("会话销毁:"+ session.getHost());
        attributeKeys.forEach(n-> System.out.println(n));
    }

    @Override
    public void onExpiration(Session session) {
        //重新获取session时才会进行判断是否过期
        System.out.println("会话过期:"+session.getLastAccessTime());
    }
}
