package com.example.point.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
public class MariaDbConfiguration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;
    @Value("${spring.datasource.hikari.pool-name}")
    String poolName;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    int maxPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    int minIdle;
    @Value("${spring.datasource.hikari.max-lifetime}")
    Long maxLifeTime;
    @Value("${spring.datasource.hikari.idle-timeout}")
    Long idleTimeout;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.example.point.service.domain"});
        em.setJpaVendorAdapter(this.vendorAdapter());
        return em;
    }

    @Bean
    public JpaVendorAdapter vendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();

        dataSourceConfig.setDriverClassName(driver);
        dataSourceConfig.setJdbcUrl(url);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
//        dataSourceConfig.setPoolName(poolName);
//        dataSourceConfig.setMaximumPoolSize(maxPoolSize);
//        dataSourceConfig.setMinimumIdle(minIdle);
//        dataSourceConfig.setMaxLifetime(maxLifeTime);
//        dataSourceConfig.setIdleTimeout(idleTimeout);
//        dataSourceConfig.setTransactionIsolation("TRANSACTION_READ_COMMITTED");

        return new HikariDataSource(dataSourceConfig);
    }
}
