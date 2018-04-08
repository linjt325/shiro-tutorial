package top.linjt.shiro.chapter6.dao;

import top.linjt.shiro.chapter6.pojo.Permission;

import java.sql.SQLException;

public interface PermissionDao {

    void createPermission(Permission permission) throws SQLException;

    void deletePermission(Long permissionId);


}
