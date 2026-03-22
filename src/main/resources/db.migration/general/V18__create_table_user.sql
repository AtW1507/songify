CREATE TABLE users
(
    id          BIGSERIAL    PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    enabled     BOOLEAN      NOT NULL,
    authorities TEXT[]

);