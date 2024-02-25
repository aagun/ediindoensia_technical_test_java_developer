CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 2147483647;

CREATE TABLE users
(
    userid      INTEGER      NOT NULL,
    namalengkap VARCHAR(255) NOT NULL,
    username    VARCHAR(32)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    status      CHAR(1)
);

ALTER SEQUENCE
    IF EXISTS user_seq
    OWNED BY users.userid;

ALTER TABLE IF
    EXISTS users
    ADD PRIMARY KEY (userid),
    ALTER COLUMN userid SET DEFAULT nextval('users_seq');

CREATE TABLE user_credentials
(
    id     uuid UNIQUE    NOT NULL,
    salt   VARCHAR(255)   NOT NULL,
    userid INTEGER UNIQUE NOT NULL
);

ALTER TABLE
    IF EXISTS user_credentials
    ADD PRIMARY KEY (id),
    ALTER COLUMN id SET DEFAULT uuid_generate_v4(),
    ADD CONSTRAINT fkey_users_userid FOREIGN KEY (userid) REFERENCES users;