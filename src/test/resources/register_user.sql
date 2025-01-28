DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE userdb."user" (
    username varchar(250),
    "password" varchar(250),
    coins int DEFAULT 20,
    name varchar(200),
    bio varchar(200),
    image varchar(200),
    wins int DEFAULT 0,
    losses int DEFAULT 0,
    "ties" int DEFAULT 0,
    elo int DEFAULT 100
);
