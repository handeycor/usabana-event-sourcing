package com.sabana.event_sourcing_poc.repository.view.model;

import com.sabana.event_sourcing_poc.domain.States;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Entity
@Table(name = "sale_view")
public class SaleView {

    @Id
    @Column(name = "sale_id")
    private Long saleId;

    @Enumerated(EnumType.STRING)
    private States status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "last_updated_at")
    private Instant lastUpdatedAt;
}
