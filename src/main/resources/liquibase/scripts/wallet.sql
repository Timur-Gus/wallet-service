-- liquibase formatted sql

-- changeset tgusainov:1
CREATE TABLE wallet
(
    wallet_id      UUID PRIMARY KEY,
    operation_type VARCHAR,
    amount         SERIAL
)
