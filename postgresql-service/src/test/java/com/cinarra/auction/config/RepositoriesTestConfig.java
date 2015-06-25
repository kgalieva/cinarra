package com.cinarra.auction.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("com.cinarra.auction.postgresql.entity")
@EnableJpaRepositories("com.cinarra.auction.postgresql")
@EnableAutoConfiguration
@ComponentScan("com.cinarra.auction.postgresql.service")
public class RepositoriesTestConfig {
}