package com.sabana.event_sourcing_poc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleEntity {
    private Long saleId;
    private String status;
}
