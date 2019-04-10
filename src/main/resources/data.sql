/*
 * USERS
 */
INSERT INTO USERS(username, password) VALUES('John', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Dani', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Banani', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Vasile', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Costel', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Jeanina', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Pascalake', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Babuta', 'askldjal');
INSERT INTO USERS(username, password) VALUES('Tricolor', 'askldjal');
/*
 * EVENTS
 */
INSERT INTO EVENTS(name, event_start, location) VALUES('PARTY WOOHOO', TO_DATE('10/15/2008 10:06:32 PM','MM/DD/YYYY HH:MI:SS AM'),
'PE UNDEVA');
INSERT INTO EVENTS(name, event_start, location) VALUES('Slujba', TO_DATE('10/15/2008 10:06:32 PM','MM/DD/YYYY HH:MI:SS AM'),
'PE UNDEVA');
INSERT INTO EVENTS(name, event_start, location) VALUES('La mancat rahat', TO_DATE('5/23/2012 08:00:32 PM','MM/DD/YYYY HH:MI:SS AM'),
'PE UNDEVA');
INSERT INTO EVENTS(name, event_start, location) VALUES('Loro ipsum dorem', TO_DATE('1/19/2000 23:21:00 PM','MM/DD/YYYY HH:MI:SS AM'),
'PE UNDEVA');
/*
 * INVITATIONS
 */
INSERT INTO INVITATIONS VALUES(1, 3);
/*
 * GROUP
 */
INSERT INTO GROUPS(NAME) VALUES('Gasca Mare');
INSERT INTO GROUPS(NAME) VALUES('Gasca Mica');
INSERT INTO GROUPS(NAME) VALUES('Nu Plange Ana!');
INSERT INTO GROUPS(NAME) VALUES('The fantastic 4rs');
/*
 * USER_GROUPS
 */
INSERT INTO USERS_GROUPS VALUES(1, 4);
INSERT INTO USERS_GROUPS VALUES(2, 3);
INSERT INTO USERS_GROUPS VALUES(2, 2);
INSERT INTO USERS_GROUPS VALUES(7, 2);
INSERT INTO USERS_GROUPS VALUES(2, 4);
INSERT INTO USERS_GROUPS VALUES(1, 3);
INSERT INTO USERS_GROUPS VALUES(5, 2);