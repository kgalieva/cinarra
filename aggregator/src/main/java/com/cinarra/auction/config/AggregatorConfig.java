package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.Load;
import com.cinarra.auction.common.profiles.TestScenario;
import com.cinarra.auction.executor.ManualAggregationExecutor;
import com.cinarra.auction.executor.ScheduledAggregationExecutor;
import com.cinarra.auction.executor.TestScenarioAggregationExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@Configuration
@PropertySource("classpath:aggregator.properties")
public class AggregatorConfig {

    @Value("${aggregator.periodSeconds}")
    private long period;

    @Load
    @Bean(destroyMethod = "stop")
    public ScheduledAggregationExecutor scheduledAggregator() {
        ScheduledAggregationExecutor aggregator = new ScheduledAggregationExecutor(period, "scheduled-aggregator");
        aggregator.start();
        return aggregator;
    }

    @TestScenario
    @Bean(destroyMethod = "stop")
    public TestScenarioAggregationExecutor testScenarioAggregationExecutor() {
        TestScenarioAggregationExecutor aggregator = new TestScenarioAggregationExecutor(period, "test-scenario-aggregator");
        aggregator.start(LocalDate.parse("2015-06-01"), LocalDate.parse("2015-06-14"));
        return aggregator;
    }


    @Bean(destroyMethod = "stop")
    public ManualAggregationExecutor manualAggregator() {
        return new ManualAggregationExecutor();
    }

}
