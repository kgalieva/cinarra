package com.cinarra.auction.executor;

import com.cinarra.auction.aggregator.Aggregator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

public class ScheduledAggregationExecutorTest {
    @InjectMocks
    ScheduledAggregationExecutor executor = new ScheduledAggregationExecutor(1, "test-scheduled-aggregator");

    @Mock
    Aggregator aggregator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStart() {
        executor.start();
        verify(aggregator, timeout(5500).times(5)).aggregate(LocalDate.now());
    }
}