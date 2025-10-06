CREATE TABLE IF NOT EXISTS sale_event (
                                          id BIGSERIAL PRIMARY KEY,
                                          sale_id BIGINT NOT NULL,
                                          status TEXT NOT NULL,
                                          last_event_date TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_sale_event_sale_id
    ON sale_event (sale_id);

CREATE INDEX IF NOT EXISTS idx_sale_event_last_event_date
    ON sale_event (last_event_date);

CREATE TABLE IF NOT EXISTS sale_view (
                                         sale_id       BIGINT       PRIMARY KEY,
                                         status        TEXT         NOT NULL,
                                         created_at    TIMESTAMPTZ  DEFAULT NOW(),
                                         last_updated_at    TIMESTAMPTZ  DEFAULT NOW()
    );

CREATE INDEX IF NOT EXISTS idx_sale_view_status
    ON sale_view (status);

CREATE INDEX IF NOT EXISTS idx_sale_view_last_updated_at
    ON sale_view (last_updated_at);