package com.cinarra.auction.postgresql.repository;

import com.cinarra.auction.postgresql.entity.MonthlyReport;
import com.cinarra.auction.postgresql.entity.TransactionKey;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "price", path = "month")
public interface MonthlyReportRepository extends PagingAndSortingRepository<MonthlyReport, TransactionKey> {
}
