INSERT INTO USERS(username, password, email) VALUES('admin', 'admin', 'bla1');
INSERT INTO USERS(username, password, email) VALUES('John', 'askldjal', 'bla2');
INSERT INTO USERS(username, password, email) VALUES('Dani', 'askldjal', 'bla3');
INSERT INTO USERS(username, password, email) VALUES('Banani', 'askldjal', 'bla4');
INSERT INTO USERS(username, password, email) VALUES('Vasile', 'askldjal', 'bla5');
INSERT INTO USERS(username, password, email) VALUES('Costel', 'askldjal', 'bla6');
INSERT INTO USERS(username, password, email) VALUES('Jeanina', 'askldjal', 'bla7');
INSERT INTO USERS(username, password, email) VALUES('Pascalake', 'askldjal', 'bla8');
INSERT INTO USERS(username, password, email) VALUES('Babuta', 'askldjal', 'bla9');
INSERT INTO USERS(username, password, email) VALUES('Tricolor', 'askldjal', 'bla10');
INSERT INTO FOLLOWS(followed_id, follower_id) VALUES (1, 2);
INSERT INTO FOLLOWS(followed_id, follower_id) VALUES (2, 1);
INSERT INTO EVENTS(NAME, EVENT_START, LOCATION) VALUES('PARTY WOOHOO', TO_DATE('10/15/2008 10:06:32 PM','MM/DD/YYYY HH:MI:SS AM'),'PE UNDEVA');
INSERT INTO EVENTS(NAME, EVENT_START, LOCATION) VALUES('Slujba', TO_DATE('10/15/2008 10:06:32 PM','MM/DD/YYYY HH:MI:SS AM'),'PE UNDEVA');
INSERT INTO EVENTS(NAME, EVENT_START, LOCATION) VALUES('La mancat rahat', TO_DATE('5/23/2012 08:00:32 PM','MM/DD/YYYY HH:MI:SS AM'),'PE UNDEVA');
INSERT INTO EVENTS(NAME, EVENT_START, LOCATION) VALUES('Loro ipsum dorem', TO_DATE('1/19/2000 01:21:00 AM','MM/DD/YYYY HH:MI:SS AM'),'PE UNDEVA');
INSERT INTO SUBSCRIPTIONS VALUES(1, 3);
INSERT INTO GROUPS(NAME) VALUES('Gasca Mare');
INSERT INTO GROUPS(NAME) VALUES('Gasca Mica');
INSERT INTO GROUPS(NAME) VALUES('Nu Plange Ana!');
INSERT INTO GROUPS(NAME) VALUES('The fantastic 4rs');
INSERT INTO USERS_GROUPS VALUES(1, 4);
INSERT INTO USERS_GROUPS VALUES(2, 3);
INSERT INTO USERS_GROUPS VALUES(2, 2);
INSERT INTO USERS_GROUPS VALUES(7, 2);
INSERT INTO USERS_GROUPS VALUES(2, 4);
INSERT INTO USERS_GROUPS VALUES(1, 3);
INSERT INTO USERS_GROUPS VALUES(5, 2);
INSERT INTO MESSAGES (SOURCE_USER, RECEIVER_USER, TEXT, IS_READ) VALUES(1, 1, 'Ce mai faci, EU?', true);
INSERT INTO MESSAGES (SOURCE_USER, RECEIVER_USER, TEXT) VALUES(1, 2, 'Ce mai faci, boss?');
INSERT INTO MESSAGES (SOURCE_USER, RECEIVER_USER, TEXT) VALUES(2, 1, 'Bine boss, tu?');
INSERT INTO GROUPS_MESSAGES VALUES(1, 2);
INSERT INTO AUTHORITIES VALUES(1, 'USER');
INSERT INTO AUTHORITIES VALUES(2, 'ADMIN');
INSERT INTO USERS_AUTHORITIES VALUES(1 ,1);
INSERT INTO USERS_AUTHORITIES VALUES(1 ,2);
INSERT INTO USERS_AUTHORITIES VALUES(2 ,1);
INSERT INTO USERS_AUTHORITIES VALUES(3 ,2);