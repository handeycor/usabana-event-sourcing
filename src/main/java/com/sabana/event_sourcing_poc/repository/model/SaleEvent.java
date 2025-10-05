package com.sabana.event_sourcing_poc.repository.model;

import com.sabana.event_sourcing_poc.domain.States;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sale_event")
public class SaleEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_id")
    private Long saleId;

    @Enumerated(EnumType.STRING)
    @Column()
    private States status;

    @Column(name = "last_event_date")
    private Instant lastEventDate;
}
