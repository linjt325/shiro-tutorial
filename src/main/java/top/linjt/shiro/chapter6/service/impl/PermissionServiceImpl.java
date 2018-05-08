package top.linjt.shiro.chapter6.service.impl;

import org.springframework.stereotype.Service;
import top.linjt.shiro.chapter6.dao.PermissionDao;
import top.linjt.shiro.chapter6.dao.impl.PermissionDaoImpl;
import top.linjt.shiro.chapter6.pojo.Permission;
import top.linjt.shiro.chapter6.service.PermissionService;

import java.sql.SQLException;
@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao;
    public PermissionServiceImpl() {
        permissionDao = new PermissionDaoImpl();
    }

    @Override
    public void createPermission(Permission permission) throws SQLException {
        permissionDao.createPermission(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
