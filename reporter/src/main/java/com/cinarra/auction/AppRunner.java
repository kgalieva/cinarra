package com.cinarra.auction;

import com.cinarra.auction.executor.ManualAggregationExecutor;
import com.cinarra.auction.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.LocalDate;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    Generator generator;

    @Autowired
    ManualAggregationExecutor manualAggregator;

    @Override
    public void run(String... strings) throws Exception {
        generator.start();
        if(strings != null && strings.length > 0) {
            manualAggregator.aggregateFrom(LocalDate.parse(strings[0]));
        }
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        generator.stop();
    }

}
