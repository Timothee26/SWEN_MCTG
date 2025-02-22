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

CREATE TABLE userdb.package (
                                id varchar(200),
                                "name" varchar(200),
                                damage float,
                                pid int,
                                bought varchar(200),
                                type varchar(200)
);

INSERT INTO userdb.login (username, "token") VALUES('test', 'test-mtcgToken');
INSERT INTO userdb.login (username, "token") VALUES('testNOC', 'test-mtcgToken');
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('test', 'test', 20, '', '', '', 0, 0, 0, 100);
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('testNOC', 'testMOC', 0, '', '', '', 0, 0, 0, 100);

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'WaterGoblin', 25, 1, 'zero', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('65ff5f23-1e70-4b79-b3bd-f6eb679dd3b5', 'Dragon', 50, 1, 'zero', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('55ef46c4-016c-4168-bc43-6b9b1e86414f', 'WaterSpell', 20, 1, 'zero', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('f3fad0f2-a1af-45df-b80d-2e48825773d9', 'Ork', 45, 1, 'zero', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('shjdfkjdb-a1af-45df-b80d-2e48825773d9', 'Elf', 45, 1, 'zero', 'Normal');

