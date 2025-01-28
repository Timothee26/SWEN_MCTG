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

CREATE TABLE userdb.deck (
    id varchar(200),
    "name" varchar(200),
    damage float,
    bought varchar(200),
    "type" varchar(200)
);

INSERT INTO userdb.login (username, "token") VALUES('test', 'test-mtcgToken');
INSERT INTO userdb.login (username, "token") VALUES('neu', 'neu-mtcgToken');


INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 25, 0, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'Dragon', 50, 0, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('55ef46c4-016c-4168-bc43-6b9b1e86414f', 'WaterSpell', 20, 0, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('f3fad0f2-a1af-45df-b80d-2e48825773d9', 'Ork', 45, 0, 'test', 'Normal');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ased1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 25, 1, 'neu', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('as65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'Dragon', 50, 1, 'neu', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('as55ef46c4-016c-4168-bc43-6b9b1e86414f', 'WaterSpell', 20, 1, 'neu', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('asf3fad0f2-a1af-45df-b80d-2e48825773d9', 'Ork', 45, 1, 'neu', 'Normal');

INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 25, 'test', 'Water');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'Dragon', 50,  'test', 'Fire');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('55ef46c4-016c-4168-bc43-6b9b1e86414f', 'WaterSpell', 20,  'test', 'Water');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('f3fad0f2-a1af-45df-b80d-2e48825773d9', 'Ork', 45, 'test', 'Normal');
