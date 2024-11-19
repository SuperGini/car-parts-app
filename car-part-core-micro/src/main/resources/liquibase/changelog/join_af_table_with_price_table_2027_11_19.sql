--liquibase formatted sql
--changeset Ginitoru:2024-11-19-001 splitStatements:true endDelimiter:
ALTER TABLE aftermarket_part ADD price_id VARCHAR(255);
