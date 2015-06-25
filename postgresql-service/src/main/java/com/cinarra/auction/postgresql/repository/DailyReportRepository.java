package com.cinarra.auction.postgresql.repository;

import com.cinarra.auction.postgresql.entity.DailyReport;
import com.cinarra.auction.postgresql.entity.TransactionKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigDecimal;
import java.time.LocalDate;

@RepositoryRestResource(collectionResourceRel = "price", path = "day")
public interface DailyReportRepository extends PagingAndSortingRepository<DailyReport, TransactionKey> {

    @Query("select sum(r.totalCost) from DailyReport r where r.pk.productId = :productId and r.pk.transactionDate >= :firstDayOfWeek and r.pk.transactionDate <= :lastDayOfWeek")
    BigDecimal weekTotalCost(@Param("productId") String productId, @Param("firstDayOfWeek") LocalDate firstDayOfWeek, @Param("lastDayOfWeek") LocalDate lastDayOfWeek);

    @Query("select sum(r.totalCost) from DailyReport r where r.pk.productId = :productId and r.pk.transactionDate >= :firstDayOfMonth and r.pk.transactionDate <= :lastDayOfMonth")
    BigDecimal monthTotalCost(@Param("productId") String productId, @Param("firstDayOfMonth") LocalDate firstDayOfWeek, @Param("lastDayOfMonth") LocalDate lastDayOfMonth);

}
