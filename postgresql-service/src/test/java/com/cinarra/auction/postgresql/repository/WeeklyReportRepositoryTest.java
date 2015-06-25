package com.cinarra.auction.postgresql.repository;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.RepositoriesTestConfig;
import com.cinarra.auction.postgresql.entity.TransactionKey;
import com.cinarra.auction.postgresql.entity.WeeklyReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {RepositoriesTestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(Embedded.profile)
public class WeeklyReportRepositoryTest {

    @Autowired
    WeeklyReportRepository repository;

    @Test
    public void testFindsFirstPageOfReports() {
        Page<WeeklyReport> reports = repository.findAll(new PageRequest(0, 10));
        assertThat(reports.getTotalElements(), is(greaterThan(0L)));
    }

    @Test
    public void testOne() {
        WeeklyReport report = repository.findOne(new TransactionKey("123", LocalDate.parse("2015-05-25")));
        assertNotNull(report);
        assertEquals(new BigDecimal("134.96"), report.getTotalCost());
    }

}