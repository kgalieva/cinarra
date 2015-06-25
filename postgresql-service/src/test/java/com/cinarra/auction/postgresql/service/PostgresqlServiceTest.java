package com.cinarra.auction.postgresql.service;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.RepositoriesTestConfig;
import com.cinarra.auction.postgresql.entity.DailyReport;
import com.cinarra.auction.postgresql.entity.MonthlyReport;
import com.cinarra.auction.postgresql.entity.TransactionKey;
import com.cinarra.auction.postgresql.entity.WeeklyReport;
import com.cinarra.auction.postgresql.repository.DailyReportRepository;
import com.cinarra.auction.postgresql.repository.MonthlyReportRepository;
import com.cinarra.auction.postgresql.repository.WeeklyReportRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.cinarra.auction.common.utils.Utils.firstDayOfWeek;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {RepositoriesTestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(Embedded.profile)
public class PostgresqlServiceTest extends TestCase {

    @Autowired
    PostgresqlService postgresqlService;

    @Autowired
    DailyReportRepository dailyReportRepository;

    @Autowired
    WeeklyReportRepository weeklyReportRepository;

    @Autowired
    MonthlyReportRepository monthlyReportRepository;

    @Test
    public void testUpdateSum() throws Exception {
        String productId = "1234";
        LocalDate transactionDate = LocalDate.parse("2015-05-27");
        BigDecimal sum = new BigDecimal("9876.54");
        postgresqlService.updateSum(productId, transactionDate, sum);

        LocalDate transactionDateModified = transactionDate.minusDays(1);
        postgresqlService.updateSum(productId, transactionDateModified, sum);

        transactionDateModified = transactionDate.minusDays(10);
        postgresqlService.updateSum(productId, transactionDateModified, sum);

        transactionDateModified = transactionDate.minusMonths(1);
        postgresqlService.updateSum(productId, transactionDateModified, sum);


        TransactionKey key = new TransactionKey(productId, transactionDate);
        DailyReport dailyReport = dailyReportRepository.findOne(key);
        assertNotNull(dailyReport);
        assertEquals(sum, dailyReport.getTotalCost());

        key.setTransactionDate(firstDayOfWeek(transactionDate));
        WeeklyReport weeklyReport = weeklyReportRepository.findOne(key);
        assertNotNull(weeklyReport);
        assertEquals(sum.multiply(BigDecimal.valueOf(2)), weeklyReport.getTotalCost());

        key.setTransactionDate(transactionDate.withDayOfMonth(1));
        MonthlyReport monthlyReport = monthlyReportRepository.findOne(key);
        assertNotNull(monthlyReport);
        assertEquals(sum.multiply(BigDecimal.valueOf(3)), monthlyReport.getTotalCost());
    }
}