package com.sabana.event_sourcing_poc.repository.view;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.repository.view.model.SaleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<SaleView, Long> {

    List<SaleView> findAll();

    Optional<SaleView> findById(Long id);

    List<SaleView> findByStatus(States status);


    SaleView save(SaleView saleView);

    @Modifying
    @Query("UPDATE SaleView s SET s.status = :status, s.lastUpdatedAt = :lastUpdatedAt WHERE s.saleId = :saleId")
    void updateSale(@Param("status") States status, @Param("lastUpdatedAt") Instant lastUpdatedAt, @Param("saleId") Long saleId);
}
