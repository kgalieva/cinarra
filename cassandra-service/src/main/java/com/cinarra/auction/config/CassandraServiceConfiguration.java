package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.common.profiles.NotEmbedded;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cassandra.config.java.AbstractCqlTemplateConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@ComponentScan("com.cinarra.auction.cassandra.service")
public class CassandraServiceConfiguration {

    @Configuration
    @Embedded
    @PropertySource("classpath:cassandra-embedded.properties")
    @EnableAutoConfiguration
    static  class EmbeddedCassandraConfig {

        @Value("${cassandra.embedded.host}")
        private String host;
        @Value("${cassandra.embedded.port}")
        private int port;
        @Value("${cassandra.embedded.keyspaceName}")
        private String keyspaceName;
        @Value("${cassandra.embedded.dataSetLocation}")
        private String dataSetLocation;

        @Bean
        public Session session() throws Exception {
            EmbeddedCassandra cassandra = new EmbeddedCassandra(host, port, dataSetLocation, keyspaceName);
            cassandra.start();
            return cassandra.getSession();
        }
    }

    @Configuration
    @NotEmbedded
    @PropertySource("classpath:cassandra.properties")
    @EnableAutoConfiguration
    static class CassandraConfig extends AbstractCqlTemplateConfiguration {

        @Value("${cassandra.keyspaceName}")
        private String keyspaceName;

        @Override
        protected String getKeyspaceName() {
            return keyspaceName;
        }
    }

    @Configuration
    @EnableCassandraRepositories("com.cinarra.auction.cassandra.repository")
    static class TemplatesConfig {

        @Bean
        public CassandraTemplate cassandraTemplate(Session session) {
            return new CassandraTemplate(session);
        }

    }
}