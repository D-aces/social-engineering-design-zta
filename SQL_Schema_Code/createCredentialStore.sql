CREATE DATABASE IF NOT EXISTS credential_store;
USE credential_store;

CREATE TABLE users (
    uuid        VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    password    TEXT NOT NULL,
    email       VARCHAR(320),
    tfa         BOOLEAN DEFAULT FALSE,
    status      VARCHAR(20)
);

CREATE TABLE logins (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_uuid   VARCHAR(36),
    time        DATETIME NOT NULL,
    location    VARCHAR(255),
    device      VARCHAR(255),
    expiry      DATETIME,
    FOREIGN KEY (user_uuid) REFERENCES users(uuid)
);
