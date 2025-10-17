package com.example.servingwebcontent;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DatabaseConnection {

    private final Environment environment;

    public DatabaseConnection(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");

        if (url == null || username == null || password == null) {
            throw new IllegalStateException("Thiếu cấu hình DB: spring.datasource.url/username/password");
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        String sslMode = environment.getProperty("spring.datasource.hikari.data-source-properties.sslMode", "require");
        dataSource.addDataSourceProperty("sslMode", sslMode);
        return dataSource;
    }
}


