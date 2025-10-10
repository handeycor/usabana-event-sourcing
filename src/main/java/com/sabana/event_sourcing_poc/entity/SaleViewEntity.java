package com.sabana.event_sourcing_poc.entity;

import com.sabana.event_sourcing_poc.domain.States;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SaleViewEntity {

    private Long saleId;
    private States status;
    private Instant createdAt;
    private Instant updatedAt;

}
