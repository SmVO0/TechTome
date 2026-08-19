ALTER TABLE store_item
    ADD COLUMN rating      DECIMAL(2,1) NOT NULL DEFAULT 0.0,
    ADD COLUMN review_count INT          NOT NULL DEFAULT 0;
