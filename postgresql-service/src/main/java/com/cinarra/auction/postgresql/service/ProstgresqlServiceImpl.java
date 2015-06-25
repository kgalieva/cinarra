package com.cinarra.auction.postgresql.service;

import com.cinarra.auction.postgresql.entity.DailyReport;
import com.cinarra.auction.postgresql.entity.MonthlyReport;
import com.cinarra.auction.postgresql.entity.WeeklyReport;
import com.cinarra.auction.postgresql.repository.DailyReportRepository;
import com.cinarra.auction.postgresql.repository.MonthlyReportRepository;
import com.cinarra.auction.postgresql.repository.WeeklyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.cinarra.auction.common.utils.Utils.firstDayOfWeek;
import static com.cinarra.auction.common.utils.Utils.lastDayOfWeek;

@Service
public class ProstgresqlServiceImpl implements PostgresqlService {

    @Autowired
    DailyReportRepository dailyReportRepository;

    @Autowired
    WeeklyReportRepository weeklyReportRepository;

    @Autowired
    MonthlyReportRepository monthlyReportRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor=Exception.class)
    public void updateSum(String productId, LocalDate day, BigDecimal sum) {
        DailyReport dailyReport = new DailyReport(productId, day, sum);
        dailyReportRepository.save(dailyReport);
        LocalDate firstDayOfWeek = firstDayOfWeek(day);
        sum = dailyReportRepository.weekTotalCost(productId, firstDayOfWeek, lastDayOfWeek(day));
        WeeklyReport weeklyReport = new WeeklyReport(productId, firstDayOfWeek, sum);
        weeklyReportRepository.save(weeklyReport);
        LocalDate firstDayOfMonth = day.withDayOfMonth(1);
        sum = dailyReportRepository.monthTotalCost(productId, firstDayOfMonth, day.withDayOfMonth(day.lengthOfMonth()));
        MonthlyReport monthlyReport = new MonthlyReport(productId, firstDayOfMonth, sum);
        monthlyReportRepository.save(monthlyReport);
    }
}
