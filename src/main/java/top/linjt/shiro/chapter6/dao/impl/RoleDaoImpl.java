package top.linjt.shiro.chapter6.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import top.linjt.shiro.chapter6.dao.RoleDao;
import top.linjt.shiro.chapter6.pojo.Role;
import top.linjt.shiro.chapter6.util.JdbcUtil;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class RoleDaoImpl implements RoleDao {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public RoleDaoImpl() {
        JdbcUtil jdbcUtil = JdbcUtil.newInstance();
        DataSource source = jdbcUtil.getDataSource();
        this.dataSource = source;
        this.jdbcTemplate = jdbcUtil.getJdbcTemplate();
    }

    @Override
    public void createRole(Role role) {

        String sql = "insert into sys_roles (role ,description ,available) values (?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getRole());
            statement.setString(2, role.getDescription());
            statement.setBoolean(3, role.getAvailable());
            return statement;
        },keyHolder);

        role.setId(keyHolder.getKey().longValue());

    }


    @Override
    public void deleteRole(Long roleId) {

        //首先把和role关联的相关表数据删掉
        String sql_0 = "delete from sys_users_roles where role_id=? ";
        jdbcTemplate.update(sql_0, roleId);

        String sql = "delete from sys_roles where id = ?";

        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setLong(1, roleId);
                    return preparedStatement;
                }
        );
    }

    @Override
    public Role findOne(Long roleId) {
        String sql = "select id, role ,description,available from sys_roles where id = ?";
        List<Role> query = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role r = new Role(rs.getString(2), rs.getString(3), rs.getBoolean(4));
            r.setId(rs.getLong(1));
            return r;
        });
        return query.get(0);
    }

    @Override
    public void correlationPermission(Long roleId, Long... permissionIds) {
        String sql = "insert into sys_roles_permissions (role_id,permission_id) values (?,?)";
        for (Long permissionId : permissionIds) {
            jdbcTemplate.update(sql, roleId, permissionId);
        }
    }

    @Override
    public void unCorrelationPermission(Long roleId, Long... permissionIds) {

        String sql = "delete from sys_roles_permissions where role_id=? and permission_id =?";
        for (Long permissionId : permissionIds) {
            jdbcTemplate.update(sql, roleId, permissionId);
        }
    }
}
