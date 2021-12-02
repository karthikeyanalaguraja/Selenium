package core.utilities.baseUtilities;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import core.utilities.configs.Config;
import core.utilities.objects.CoreUser;
import core.utilities.objects.UserType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

public abstract class BaseSqlManager {

    String user = "admin";
    String pw = "xQAforL1f3";
    String url = "xqadb.c9csbsjzxa5g.us-east-1.rds.amazonaws.com";

    MysqlDataSource dataSource;

    public BaseSqlManager() {
        dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pw);
        dataSource.setServerName(url);
    }

    /**
     * Executes DML statement and returns the list of rows affected.
     *
     * @param sql
     * @param values
     * @return
     */
    protected int executeDml(String sql, Object... values) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.update(sql, values);
    }

    /**
     * Executes a query and returns a list of objects.
     *
     * @param mapper
     * @param sql
     * @param sqlParams
     * @param <T>
     * @return
     */
    protected <T> List<T> query(RowMapper<T> mapper, String sql, Object... sqlParams) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.query(sql, sqlParams, mapper);
    }

    /**
     * Executes a query and returns a list of Maps containing row data.
     *
     * @param sql
     * @param sqlParams
     * @return
     */
    protected List<Map<String, Object>> query(String sql, Object... sqlParams) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template.queryForList(sql, sqlParams);
    }

    public List<Config> getConfig(){
        String sql = "SELECT * FROM qa.ui_config";

        return query(
                (resultSet, i) -> {
                    Config configuration = new Config();
                    configuration.scope = resultSet.getString("scope");
                    configuration.key1 = resultSet.getString("key1");
                    configuration.key2 = resultSet.getString("key2");
                    configuration.key3 = resultSet.getString("key3");
                    configuration.value = resultSet.getString("value");
                    return configuration;
                }, sql);

    }

    public abstract CoreUser getUser(UserType type);
}