package com.sabana.event_sourcing_poc.repository.event;

import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ReadEventSale extends JpaRepository<SaleEvent, Long> {

    List<SaleEvent> findAllBySaleIdAndLastEventDateBefore(Long saleId, Instant date);


    SaleEvent findFirstBySaleIdOrderByLastEventDateDesc(Long saleId);
}
