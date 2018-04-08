package top.linjt.shiro.chapter6.service;

import top.linjt.shiro.chapter6.pojo.Permission;

import java.sql.SQLException;

public interface PermissionService {

    void createPermission(Permission permission) throws SQLException;

    void deletePermission(Long permissionId);

}
