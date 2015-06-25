package com.cinarra.auction.config;

import com.cinarra.auction.postgresql.utils.TransactionKeyConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;

@Configuration
@ComponentScan("com.cinarra.auction.service")
public class PostgresqlServiceConfig {

    @Bean
    public ConfigurableConversionService configureConversionService(@Qualifier("defaultConversionService") ConfigurableConversionService conversionService) {
        conversionService.addConverter(new TransactionKeyConverter());
        return conversionService;
    }
}
