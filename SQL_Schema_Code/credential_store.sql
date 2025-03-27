CREATE TABLE users (
    uuid        VARCHAR(36) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    password    TEXT NOT NULL,
    email       VARCHAR(320),
    tfa         BOOLEAN DEFAULT FALSE,
    status      VARCHAR(20)
);

CREATE TABLE logins (
    id          SERIAL PRIMARY KEY,
    uuid   VARCHAR(64) REFERENCES users(uuid),
    time        TIMESTAMP NOT NULL,
    location    VARCHAR(255),
    device      VARCHAR(255),
    expiry      TIMESTAMP
);
