package com.sabana.event_sourcing_poc.repository.write;

import com.sabana.event_sourcing_poc.repository.model.SaleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveEventSale extends JpaRepository<SaleEvent, Long> {

    public SaleEvent save(SaleEvent saleEvent);

}
