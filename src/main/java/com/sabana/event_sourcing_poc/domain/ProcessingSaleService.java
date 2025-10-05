package com.sabana.event_sourcing_poc.domain;


import com.sabana.event_sourcing_poc.domain.gateway.ProcessingEvents;
import com.sabana.event_sourcing_poc.domain.gateway.ReadSaleEvents;
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
        final SaleEntity saleEntity = new SaleEntity(saleId, States.CREATED);
        processingEvents.createSale(saleEntity);
        return saleId;
    }

    public void saveSaleState(final Long saleId, final States status) {
        final Optional<SaleEntity> lastStateOfSale = readSaleEvents.getLastStateOfSale(saleId);

        if (lastStateOfSale.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }

        final String nextStateOfCurrent = lastStateOfSale.get().getStatus().getNextState();

        if (!nextStateOfCurrent.equals(status.name())) {
            throw new IllegalArgumentException("id: " + saleId + " - rejected invalid state event");
        }

        final SaleEntity saleEntity = new SaleEntity(saleId, status);

        processingEvents.changeStateOfSale(saleEntity);
    }

    public List<SaleEntity> getSaleStatesBeforeDate(final Long saleId, final Instant date) {
        List<SaleEntity> saleEntities = readSaleEvents.getSaleStatesBeforeDate(saleId, date);
        if (saleEntities.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " and date " + date + " does not exist");
        }
        return saleEntities;
    }

    public SaleEntity getLastSaleState(final Long saleId) {
        Optional<SaleEntity> saleEntity = readSaleEvents.getLastStateOfSale(saleId);
        if (saleEntity.isEmpty()) {
            throw new IllegalArgumentException("SaleEntity with id " + saleId + " does not exist");
        }
        return saleEntity.get();
    }


}
