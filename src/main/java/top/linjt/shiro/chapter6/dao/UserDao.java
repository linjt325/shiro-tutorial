package top.linjt.shiro.chapter6.dao;

import top.linjt.shiro.chapter6.pojo.User;

import java.util.Set;

public interface UserDao {

    void createUser(User user);

    void deleteUser(Long userId);

    void updateUser(User user);

    void correlationRoles(Long userId, Long... roleIds);

    void unCorrelationRoles(Long userId, Long... roleIds);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

    User findByUsername(String username);

    User findOne(Long id);
}
