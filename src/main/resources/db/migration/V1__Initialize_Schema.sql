CREATE TABLE users
(
    userid      INTEGER      NOT NULL,
    namalengkap VARCHAR(255) NOT NULL,
    username    VARCHAR(32)  UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    status      CHAR(1)
);

ALTER TABLE IF
    EXISTS users
    ADD PRIMARY KEY (userid);