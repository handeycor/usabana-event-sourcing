package com.sabana.event_sourcing_poc.entity;

import com.sabana.event_sourcing_poc.domain.States;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SaleEventEntity {
    private Long saleId;
    private States status;
    private Instant lastEventDate;

    public SaleEventEntity(Long saleId, States status) {
        this.saleId = saleId;
        this.status = status;
    }
}
