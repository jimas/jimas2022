package com.jimas.clickhouse.config;

import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.jdbc.ClickHouseDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author liuqj
 */
@Configuration
@MapperScan(basePackages = "com.jimas.clickhouse.mapper", sqlSessionFactoryRef = "ckSessionFactory")
public class CkDataSourceConfig {
    @Value("${spring.datasource.druid.clickhouse.url}")
    private String url;
    @Value("${spring.datasource.druid.clickhouse.username}")
    private String username;
    @Value("${spring.datasource.druid.clickhouse.password}")
    private String password;

    @Bean("ckDataSource")
    public DataSource dataSource() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        properties.put(ClickHouseClientOption.AUTO_DISCOVERY.getKey(), "false");
        properties.put(ClickHouseClientOption.LOAD_BALANCING_POLICY.getKey(), "roundRobin");
        properties.put(ClickHouseClientOption.SOCKET_TIMEOUT.getKey(), 180000);
        properties.put(ClickHouseClientOption.FAILOVER.getKey(), 1);
        return new ClickHouseDataSource(url, properties);
    }

    /**
     * SqlSessionFactory
     *
     * @param datasource datasource
     * @return ckSessionFactory
     * @throws Exception Exception
     */
    @Bean("ckSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ckDataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/clickhouse/**/*.xml"));
        return bean.getObject();// 设置mybatis的xml所在位置
    }

    /**
     * SqlSessionTemplate
     *
     * @param sessionFactory sessionFactory
     * @return SqlSessionTemplate
     */
    @Bean("ckSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("ckSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean("ckDataSourceTransactionManager")
    public DataSourceTransactionManager rdsTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean("ckJdbcTemplate")
    public JdbcTemplate jdbcTemplate() throws SQLException {
        return new JdbcTemplate(dataSource());
    }

}
