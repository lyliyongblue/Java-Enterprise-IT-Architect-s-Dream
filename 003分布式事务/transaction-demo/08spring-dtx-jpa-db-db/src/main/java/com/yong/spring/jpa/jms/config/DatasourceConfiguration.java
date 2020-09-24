package com.yong.spring.jpa.jms.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.user-datasource")
    public DataSource userDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.order-datasource")
    public DataSource orderDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /** 创建链事务管理器 */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager user = new JpaTransactionManager();
        user.setEntityManagerFactory(entityManagerFactory);
        DataSourceTransactionManager order = new DataSourceTransactionManager(orderDataSource());
        return new ChainedTransactionManager(user, order);
    }

}
