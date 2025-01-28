DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE userdb.login (
                              username varchar(250),
                              "token" varchar(250)
);

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

INSERT INTO userdb.login (username, "token") VALUES('test1', 'test1-mtcgToken');
INSERT INTO userdb.login (username, "token") VALUES('test2', 'test2-mtcgToken');
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('test1', 'test1', 20, 'test1', '', '', 0, 0, 0, 103);
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('test2', 'test2', 20, 'test2', '', '', 0, 0, 0, 95);