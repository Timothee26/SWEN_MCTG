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

INSERT INTO userdb.login (username, "token") VALUES('zero', 'zero-mtcgToken');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ed1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8', 'Dragon', 50, 0, 'zero', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('edsjhdvj1dcsdfc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterSpell', 20, 0, 'zero', 'Water');