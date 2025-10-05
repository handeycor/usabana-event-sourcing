CREATE TABLE IF NOT EXISTS sale_event (
                                          id BIGSERIAL PRIMARY KEY,
                                          sale_id BIGINT NOT NULL,
                                          item TEXT NOT NULL,
                                          status TEXT NOT NULL,
                                          last_event_date TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_sale_event_sale_id
    ON sale_event (sale_id);

CREATE INDEX IF NOT EXISTS idx_sale_event_last_event_date
    ON sale_event (last_event_date);