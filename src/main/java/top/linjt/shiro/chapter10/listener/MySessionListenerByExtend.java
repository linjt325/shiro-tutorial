package top.linjt.shiro.chapter10.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * @author: XxX
 * @date: 2018/5/3
 * @Description:通过继承SessionListenerAdapter ,并复写部分方法实现对会话的操作
 */
public class MySessionListenerByExtend extends SessionListenerAdapter {

    @Override
    public void onStart(Session session) {
        System.out.println("会话创建:" + session.getId());
    }
}
