package top.linjt.shiro.chapter6.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.linjt.shiro.chapter6.dao.PermissionDao;
import top.linjt.shiro.chapter6.pojo.Permission;
import top.linjt.shiro.chapter6.util.JdbcUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PermissionDaoImpl implements PermissionDao{

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public PermissionDaoImpl() {
        JdbcUtil jdbcUtil = JdbcUtil.newInstance();
        DataSource source = jdbcUtil.getDataSource();
        this.dataSource = source;
        this.jdbcTemplate = jdbcUtil.getJdbcTemplate();
    }

    @Override
    public void createPermission(Permission permission) throws SQLException {

        String sql = new String("insert into sys_permissions(permission, description, available) values(?,?,?)");
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql,new String[]{"id"});
            statement.setString(1, permission.getPermission());
            statement.setString(2, permission.getDescription());
            statement.setBoolean(3, permission.getAvailable());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            permission.setId(generatedKeys.getLong(1));
        }

    }

    @Override
    public void deletePermission(Long permissionId) {
        String sql = new String("delete from sys_permissions where id = ?");
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, permissionId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
