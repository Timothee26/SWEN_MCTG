DROP ALL OBJECTS;
CREATE SCHEMA IF NOT EXISTS userdb;

CREATE TABLE userdb.trades (
    id varchar(200),
    cardtotrade varchar(200),
    "type" varchar(200),
    minimumdamage float,
    username varchar(200)
);

CREATE TABLE userdb.login (
    username varchar(250),
    "token" varchar(250)
);

CREATE TABLE userdb.package (
                                id varchar(200),
                                "name" varchar(200),
                                damage float,
                                pid int,
                                bought varchar(200),
                                type varchar(200)
);

CREATE TABLE userdb.deck (
                             id varchar(200),
                             "name" varchar(200),
                             damage float,
                             bought varchar(200),
                             "type" varchar(200)
);

INSERT INTO userdb.login (username, "token") VALUES('test', 'test-mtcgToken');
INSERT INTO userdb.login (username, "token") VALUES('neu', 'neu-mtcgToken');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 30, 1, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'FireGoblin', 30, 0, 'neu', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ksjhdfk-1e70-4b79-b3bd-f6eb679dd3b5', 'Dragon', 50, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('skdjfgk-1e70-4b79-b3bd-f6eb679dd3b5', 'WaterSpell', 50, 1, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('mqwef-1e70-4b79-b3bd-f6eb679dd3b5', 'WaterSpell', 30, 1, 'neu', 'Water');

INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 30, 'test', 'Water');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('skdjfgk-1e70-4b79-b3bd-f6eb679dd3b5', 'WaterSpell', 30, 'test', 'Water');

INSERT INTO userdb.trades (id, cardtotrade, "type", minimumdamage, username) VALUES('1', 'ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'Water', 20, 'test');
INSERT INTO userdb.trades (id, cardtotrade, "type", minimumdamage, username) VALUES('2', 'as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'Fire', 20, 'neu');