package com.sabana.event_sourcing_poc.repository.event;

import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveEventSale extends JpaRepository<SaleEvent, Long> {
    @Override
    SaleEvent save(SaleEvent saleEvent);

}
