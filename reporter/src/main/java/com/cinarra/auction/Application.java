package com.cinarra.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.io.IOException;

@SpringBootApplication
@IntegrationComponentScan
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(Application.class);
        ConfigurableApplicationContext ctx = app.run(args);
        System.out.println("Hit 'Enter' to terminate");
        System.in.read();
        ctx.close();
    }
}
