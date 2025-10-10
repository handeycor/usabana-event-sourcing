package com.sabana.event_sourcing_poc.adapter.publish;

import com.sabana.event_sourcing_poc.gateway.PublishSaleEvent;
import com.sabana.event_sourcing_poc.repository.event.model.SaleEvent;
import com.sabana.event_sourcing_poc.repository.view.SalesViewRepository;
import com.sabana.event_sourcing_poc.repository.view.model.SaleView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PublishSaleEventAdapter implements PublishSaleEvent {

    private final SalesViewRepository salesViewRepository;

    @Override
    public void publish(SaleEvent sale) {
        final SaleView saleView = new SaleView();
        saleView.setId(sale.getId());
        saleView.setSaleId(sale.getSaleId());
        saleView.setStatus(sale.getStatus());
        saleView.setLastEventDate(sale.getLastEventDate());
        salesViewRepository.save(saleView);
    }
}
