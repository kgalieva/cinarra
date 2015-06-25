package com.cinarra.auction.executor;

import com.cinarra.auction.aggregator.Aggregator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class ManualAggregationExecutorTest {

    @InjectMocks
    ManualAggregationExecutor executor;

    @Mock
    Aggregator aggregator;

    LocalDate today = LocalDate.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartDaysRange() {
        executor.start(today.minusDays(5), today.minusDays(2));
        verify(aggregator, timeout(1000)).aggregate(today.minusDays(5));
        verify(aggregator, timeout(1000)).aggregate(today.minusDays(4));
        verify(aggregator, timeout(1000)).aggregate(today.minusDays(3));
        verify(aggregator, timeout(1000)).aggregate(today.minusDays(2));
        verify(aggregator, never()).aggregate(today.minusDays(6));
        verify(aggregator, never()).aggregate(today.minusDays(1));
        verify(aggregator, never()).aggregate(today);
    }


    @Test
    public void testStartSingleDay() {
        executor.start(today.minusDays(15));
        verify(aggregator, timeout(1000)).aggregate(today.minusDays(15));
        verify(aggregator, never()).aggregate(today.minusDays(16));
        verify(aggregator, never()).aggregate(today.minusDays(14));
        verify(aggregator, never()).aggregate(today);
    }

}