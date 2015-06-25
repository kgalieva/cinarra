package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.JMX;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;

@JMX
@Configuration
@EnableMBeanExport
public class JMXConfig {

    @Bean
    public MBeanExporter exporter(){
        MBeanExporter exporter = new MBeanExporter();
        exporter.setExcludedBeans("producer");
        return exporter;
    }

}
