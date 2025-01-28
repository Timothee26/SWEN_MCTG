DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE userdb.login (
                              username varchar(250),
                              "token" varchar(250)
);

INSERT INTO userdb.login (username, "token") VALUES('neu', 'neu-mtcgToken');