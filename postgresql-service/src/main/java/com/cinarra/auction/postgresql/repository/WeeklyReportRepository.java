package com.cinarra.auction.postgresql.repository;

import com.cinarra.auction.postgresql.entity.TransactionKey;
import com.cinarra.auction.postgresql.entity.WeeklyReport;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "price", path = "week")
public interface WeeklyReportRepository extends PagingAndSortingRepository<WeeklyReport, TransactionKey> {
}
