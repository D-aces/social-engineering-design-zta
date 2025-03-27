CREATE DATABASE IF NOT EXISTS asset_store;
USE asset_store;


CREATE TABLE assets (
    uuid            VARCHAR(36) PRIMARY KEY,
    uri             TEXT NOT NULL,
    owner_uuid      VARCHAR(64),
    sensitivity     INT,
    location        VARCHAR(255),
    FOREIGN KEY (owner_uuid) REFERENCES users(uuid)
);
