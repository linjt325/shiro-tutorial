package top.linjt.shiro.chapter6.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class JdbcUtil {

    private static volatile JdbcUtil instance;

    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    public JdbcTemplate getJdbcTemplate(){
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }


    private JdbcUtil(){
        init();
    }

    /*
     单例模式 : 延迟加载
     */

    public static  JdbcUtil newInstance() {
        if (instance == null){
            synchronized (JdbcUtil.class){
                if (instance == null) {
                    instance = new JdbcUtil();
                }
            }
        }
        return instance;
    }

    private void init() {
        DruidDataSource  s = new DruidDataSource();

        Properties properties = new Properties();
        try {
            properties.load(JdbcUtil.class.getResourceAsStream("/conf/chapter6/jdbc.properties"));
            s.setUrl(properties.getProperty("url"));
            s.setDriverClassName(properties.getProperty("driverClassName"));
            s.setUsername(properties.getProperty("username"));
            s.setPassword(properties.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataSource = s;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

}
