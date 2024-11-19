--liquibase formatted sql
--changeset Ginitoru:2024-11-19-002 splitStatements:true endDelimiter:
ALTER TABLE aftermarket_part ADD CONSTRAINT UC_aftermarket_part_price_id UNIQUE (price_id);
ALTER TABLE aftermarket_part ADD CONSTRAINT FK_aftermarket_part_price FOREIGN KEY (price_id) REFERENCES price(id);