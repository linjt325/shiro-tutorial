package top.linjt.shiro.chapter6.pojo;

import java.util.Objects;

public class RolePermission {
    private Long roleId;
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(getRoleId(), that.getRoleId()) &&
                Objects.equals(getPermissionId(), that.getPermissionId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getRoleId(), getPermissionId());
    }
}
