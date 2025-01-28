DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE userdb.package (
    id varchar(200),
    "name" varchar(200),
    damage float,
    pid int,
    bought varchar(200),
    type varchar(200)
);

CREATE TABLE userdb.login (
                              username varchar(250),
                              "token" varchar(250)
);

