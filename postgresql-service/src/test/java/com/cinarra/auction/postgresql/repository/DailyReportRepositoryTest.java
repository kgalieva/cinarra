package com.cinarra.auction.postgresql.repository;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.RepositoriesTestConfig;
import com.cinarra.auction.postgresql.entity.DailyReport;
import com.cinarra.auction.postgresql.entity.TransactionKey;
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

import static com.cinarra.auction.common.utils.Utils.firstDayOfWeek;
import static com.cinarra.auction.common.utils.Utils.lastDayOfWeek;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {RepositoriesTestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(Embedded.profile)
public class DailyReportRepositoryTest {

    @Autowired
    DailyReportRepository repository;

    @Test
    public void testFindsFirstPageOfReports() {
        Page<DailyReport> reports = repository.findAll(new PageRequest(0, 10));
        assertThat(reports.getTotalElements(), is(greaterThan(2L)));
    }

    @Test
    public void testOne() {
        DailyReport report = repository.findOne(new TransactionKey("123", LocalDate.parse("2015-05-25")));
        assertNotNull(report);
        assertEquals(new BigDecimal("79.30"), report.getTotalCost());
    }

    @Test
    public void testWeekTotalCost() {
        LocalDate day = LocalDate.parse("2015-05-26");
        BigDecimal weekTotal = repository.weekTotalCost("123", firstDayOfWeek(day), lastDayOfWeek(day));
        assertEquals(new BigDecimal("134.96"), weekTotal);
    }

    @Test
    public void testMonthTotalCost() {
        LocalDate day = LocalDate.parse("2015-05-26");
        BigDecimal weekTotal = repository.monthTotalCost("123", day.withDayOfMonth(1), day.withDayOfMonth(day.lengthOfMonth()));
        assertEquals(new BigDecimal("170.24"), weekTotal);
    }

}