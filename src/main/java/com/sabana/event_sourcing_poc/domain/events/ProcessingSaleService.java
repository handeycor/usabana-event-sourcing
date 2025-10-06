package com.sabana.event_sourcing_poc.domain.events;


import com.sabana.event_sourcing_poc.domain.States;
import com.sabana.event_sourcing_poc.entity.SaleEventEntity;
import com.sabana.event_sourcing_poc.gateway.event.ProcessingEvents;
import com.sabana.event_sourcing_poc.gateway.event.ReadSaleEvents;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessingSaleService {

    private final ProcessingEvents processingEvents;
    private final ReadSaleEvents readSaleEvents;

    public Long createSale() {
        final Long saleId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        final SaleEventEntity saleEventEntity = new SaleEventEntity(saleId, States.CREATED);
        processingEvents.createSale(saleEventEntity);
        return saleId;
    }

    public void saveSaleState(final Long saleId, final States status) {
        final Optional<SaleEventEntity> lastStateOfSale = readSaleEvents.getLastStateOfSale(saleId);

        if (lastStateOfSale.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }

        final String nextStateOfCurrent = lastStateOfSale.get().getStatus().getNextState();
        if (nextStateOfCurrent == null) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " has complete");
        }

        if (!nextStateOfCurrent.equals(status.name())) {
            throw new IllegalArgumentException("id: " + saleId + " - rejected invalid state event");
        }

        final SaleEventEntity saleEventEntity = new SaleEventEntity(saleId, status);

        processingEvents.changeStateOfSale(saleEventEntity);
    }

    public List<SaleEventEntity> getSaleStatesBeforeDate(final Long saleId, final Instant date) {
        List<SaleEventEntity> saleEntities = readSaleEvents.getSaleStatesBeforeDate(saleId, date);
        if (saleEntities.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " and date " + date + " does not exist");
        }
        return saleEntities;
    }

    public SaleEventEntity getLastSaleState(final Long saleId) {
        Optional<SaleEventEntity> saleEntity = readSaleEvents.getLastStateOfSale(saleId);
        if (saleEntity.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }
        return saleEntity.get();
    }


}
