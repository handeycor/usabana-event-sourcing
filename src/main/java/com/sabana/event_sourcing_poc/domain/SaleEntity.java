package com.sabana.event_sourcing_poc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SaleEntity {
    private Long saleId;
    private States status;
    private Instant lastEventDate;

    public SaleEntity(Long saleId, States status) {
        this.saleId = saleId;
        this.status = status;
    }
}
