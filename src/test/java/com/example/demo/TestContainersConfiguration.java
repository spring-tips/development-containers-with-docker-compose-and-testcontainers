package com.example.demo;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;


@TestConfiguration
class TestContainersConfiguration {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer postgreSQLContainer() {
        return new PostgreSQLContainer("postgres:15.2-alpine");
    }
}
