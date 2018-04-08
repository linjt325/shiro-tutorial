package top.linjt.shiro.chapter6.service.impl;

import top.linjt.shiro.chapter6.dao.RoleDao;
import top.linjt.shiro.chapter6.dao.impl.RoleDaoImpl;
import top.linjt.shiro.chapter6.pojo.Role;
import top.linjt.shiro.chapter6.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public void createRole(Role role) {
        roleDao.createRole(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    public void correlationPermission(Long roleId, Long... permissionIds) {
        roleDao.correlationPermission(roleId, permissionIds);
    }

    @Override
    public void unCorrelationPermission(Long roleId, Long... permissionId) {
        roleDao.unCorrelationPermission(roleId,permissionId);
    }
}
