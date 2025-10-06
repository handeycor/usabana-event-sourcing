package com.sabana.event_sourcing_poc.adapter.publish;

import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.gateway.PublishSaleEvent;
import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;
import com.sabana.event_sourcing_poc.repository.view.SalesRepository;
import com.sabana.event_sourcing_poc.repository.view.model.SaleView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PublishSaleEventAdapter implements PublishSaleEvent {

    private final SalesRepository salesRepository;

    @Override
    public void publish(SaleEvent sale) {
        final SaleView saleView = new SaleView();
        saleView.setSaleId(sale.getSaleId());
        saleView.setStatus(sale.getStatus());
        saleView.setLastUpdatedAt(sale.getLastEventDate());
        if (sale.getStatus().equals(States.CREATED)) {
            saleView.setCreatedAt(sale.getLastEventDate());
            salesRepository.save(saleView);
        } else {
            salesRepository.updateSale(sale.getStatus(), sale.getLastEventDate(), sale.getSaleId());
        }
    }
}
