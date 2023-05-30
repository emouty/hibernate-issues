package com.example.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.demo.local"})
@EnableJpaRepositories(basePackages = {"com.example.demo.local"})
@EnableTransactionManagement
public class JpaConfiguration {
}
