package com.sabana.event_sourcing_poc.repository.view;

import com.sabana.event_sourcing_poc.repository.view.model.SaleView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface SalesViewRepository extends JpaRepository<SaleView, Long> {

    List<SaleView> findAllBySaleIdAndLastEventDateBeforeOrderByLastEventDateAsc(Long saleId, Instant date);

    List<SaleView> findAllBySaleIdOrderByLastEventDateAsc(Long saleId);

    SaleView findFirstBySaleIdOrderByLastEventDateDesc(Long saleId);

    SaleView save(SaleView saleView);

}
