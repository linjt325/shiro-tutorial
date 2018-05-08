package top.linjt.shiro.chapter6.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import top.linjt.shiro.chapter6.dao.UserDao;
import top.linjt.shiro.chapter6.pojo.User;
import top.linjt.shiro.chapter6.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl() {

        jdbcTemplate = JdbcUtil.newInstance().getJdbcTemplate();

    }

    @Override
    public void createUser(User user) {
        String sql = "insert into sys_users (username,password,salt,locked ) values (?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(sql, new String[]{"id"});
            PreparedStatement statement1 = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getSalt());
            statement.setBoolean(4, user.getLocked());
            return statement;
        }, keyHolder);
//        keyHolder.getKey() //当只有单个返回值的时候使用
//        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        user.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void deleteUser(Long userId) {
        String sql = "delete from sys_roles where id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update sys_roles set username = ?,password=?,salt=?,locked=? where id= ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getSalt(), user.getLocked(), user.getId());
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        if (userId == null || roleIds==null || roleIds.length == 0) {
            return ;
        }
        String sql = "insert into sys_users_roles (user_id,role_id) values(?,?)";
        for (Long roleId : roleIds) {
            jdbcTemplate.update(sql, userId, roleId);
        }
    }

    @Override
    public void unCorrelationRoles(Long userId, Long... roleIds) {
        if (userId == null || roleIds == null || roleIds.length == 0) {
            return;
        }
        String sql = "delete from sys_users_roles where user_id = ? and role_id = ?";
        for (Long roleId : roleIds) {
            jdbcTemplate.update(sql, userId,roleId);
        }
    }

    @Override
    public Set<String> findRoles(String username) {
        String sql = "select r.role from sys_users u , sys_roles r , sys_users_roles ur where username = ? and  u.id = ur.user_id and r.id = ur.role_id  ";
        List<String> result = jdbcTemplate.query(sql, new SingleColumnRowMapper<String>(), username);
        if (result != null && result.size() > 0) {
            return new HashSet<>(result);
    }

        return null;
    }

    @Override
    public Set<String> findPermissions(String username) {
        String sql = "select permission from sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp where u.username=? and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and p.id=rp.permission_id";
        List<String> result = jdbcTemplate.query(sql, new SingleColumnRowMapper<String>() , username);
        if (result != null && result.size() > 0) {
            return new HashSet<>(result);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "select * from sys_users where username =? ";

        List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        if(result.size()>0){
            return result.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User findOne(Long id) {
        String sql = "select * from sys_users where id = ?";
        List<User> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        if(result.size()>0){
            return result.get(0);
        }else{
            return null;
        }
    }
}
