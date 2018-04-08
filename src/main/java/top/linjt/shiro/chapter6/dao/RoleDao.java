package top.linjt.shiro.chapter6.dao;

import top.linjt.shiro.chapter6.pojo.Role;

public interface RoleDao {

    void createRole(Role role);

    void deleteRole(Long roleId);

    Role findOne(Long roleId);

    void correlationPermission(Long roleId, Long... permissionIds);

    void unCorrelationPermission(Long roleId, Long... permissionId);


}
