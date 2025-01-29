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
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('test', 'test', 20, 'test1', '', '', 0, 0, 0, 103);
INSERT INTO userdb."user"(username, "password", coins, "name", bio, image, wins, losses, "ties", elo)VALUES('neu', 'neu', 20, 'test2', '', '', 0, 0, 0, 95);

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'Dragon', 50, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('30', 'WaterSpell', 50, 1, 'neu', 'Water');

INSERT INTO userdb.login (username, "token") VALUES('test', 'test-mtcgToken');
INSERT INTO userdb.login (username, "token") VALUES('neu', 'neu-mtcgToken');

INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('ed1dc1bc-f0aa-4a0c-8d43-1402189b33c8', 'Dragon', 50, 'test', 'Fire');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('skdjfgk-1e70-4b79-b3bd-f6eb679dd3b5', 'WaterSpell', 30, 'neu', 'Water');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('spell1', 'FireSpell', 40, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('creature1', 'Dragon', 50, 1, 'test', 'Fire');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('spell2', 'WaterSpell', 30, 1, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('creature2', 'Dragon', 50, 1, 'test', 'Fire');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('spell3', 'FireSpell', 40, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('creature3', 'Golem', 50, 1, 'test', 'Normal');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('spell4', 'NormalSpell', 45, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('creature4', 'Kraken', 55, 1, 'test', 'Water');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('spell5', 'FireSpell', 40, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('creature5', 'Kraken', 55, 1, 'test', 'Water');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('spell6', 'NormalSpell', 50, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('creature6', 'Dragon', 60, 1, 'test', 'Fire');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('spell7', 'WaterSpell', 60, 1, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('creature7', 'Golem', 70, 1, 'test', 'Normal');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('creature8', 'Knight', 100, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('spell8', 'WaterSpell', 50, 1, 'test', 'Water');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special1', 'GoblinWarrior', 30, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special2', 'FireDragon', 50, 1, 'test', 'Fire');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('special3', 'MightyWizzard', 40, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type") VALUES('special4', 'OrkBerserker', 60, 1, 'test', 'Normal');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special5', 'SkyDragon', 70, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special6', 'AquaDragon', 65, 1, 'test', 'Water');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special7', 'DeepSeaKraken', 80, 1, 'test', 'Water');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special8', 'LightningSpell', 50, 1, 'test', 'Electric');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special9', 'FireElve', 45, 1, 'test', 'Fire');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special10', 'ShadowDragon', 75, 1, 'test', 'Dark');

INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special11', 'Warrior', 55, 1, 'test', 'Normal');
INSERT INTO userdb.package (id, "name", damage, pid, bought, "type")VALUES('special12', 'FireSpell', 40, 1, 'test', 'Fire');


INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('1', 'FireDragon', 100, 'test', 'Fire');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('2', 'WaterSpell', 80, 'test', 'Water');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('3', 'Goblin', 70, 'test', 'Normal');


INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('5', 'WaterGoblin', 30, 'neu', 'Water');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('6', 'FireElve', 20, 'neu', 'Fire');
INSERT INTO userdb.deck (id, "name", damage, bought, "type") VALUES('7', 'Ork', 10, 'neu', 'Normal');
