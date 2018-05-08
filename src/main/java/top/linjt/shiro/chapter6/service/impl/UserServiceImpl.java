package top.linjt.shiro.chapter6.service.impl;

import org.springframework.stereotype.Service;
import top.linjt.shiro.chapter6.dao.UserDao;
import top.linjt.shiro.chapter6.dao.impl.UserDaoImpl;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.service.UserService;
import top.linjt.shiro.chapter6.util.EncryptPassword;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {

        userDao = new UserDaoImpl();

    }

    @Override
    public void createUser(User user) {
        //加密密码
        EncryptPassword.encryptPassword(user);
        userDao.createUser(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        userDao.correlationRoles(userId,roleIds);
    }

    @Override
    public void unCorrelationRoles(Long userId, Long... roleIds) {
        userDao.unCorrelationRoles(userId, roleIds);
    }

    @Override
    public Set<String> findRoles(String username) {

        return userDao.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findOne(Long id) {
        return userDao.findOne(id);
    }
}
