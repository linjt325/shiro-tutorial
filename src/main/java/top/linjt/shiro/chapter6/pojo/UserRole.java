package top.linjt.shiro.chapter6.pojo;

import java.util.Objects;

public class UserRole {

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(getUserId(), userRole.getUserId()) &&
                Objects.equals(getRoleId(), userRole.getRoleId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserId(), getRoleId());
    }
}
