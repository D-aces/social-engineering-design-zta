CREATE DATABASE IF NOT EXISTS policy_store;
USE policy_store;

CREATE TABLE policies (
    uuid        VARCHAR(36) PRIMARY KEY,
    policy_json JSON NOT NULL,
    version     INT DEFAULT 1
);
