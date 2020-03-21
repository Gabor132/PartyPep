CREATE TABLE ENTITIES (ID SERIAL PRIMARY KEY);
CREATE TABLE USERS (ID SERIAL PRIMARY KEY, USERNAME VARCHAR(30) NOT NULL, EMAIL VARCHAR(30) NOT NULL UNIQUE, PASSWORD VARCHAR(30) NOT NULL, ENABLED BOOLEAN DEFAULT FALSE);
CREATE TABLE EVENTS (ID SERIAL PRIMARY KEY, NAME VARCHAR(255) NOT NULL, EVENT_START DATE NOT NULL, LOCATION VARCHAR(255) NOT NULL);
CREATE TABLE GROUPS (ID SERIAL PRIMARY KEY, NAME VARCHAR(255) NOT NULL);
CREATE TABLE USERS_GROUPS (USER_ID BIGINT NOT NULL, GROUP_ID BIGINT NOT NULL, FOREIGN KEY(USER_ID) REFERENCES USERS(ID), FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(ID), PRIMARY KEY(USER_ID, GROUP_ID));
CREATE TABLE INVITATIONS (USER_ID BIGINT NOT NULL, EVENT_ID BIGINT NOT NULL, FOREIGN KEY(USER_ID) REFERENCES USERS(ID), FOREIGN KEY(EVENT_ID) REFERENCES EVENTS(ID), PRIMARY KEY(USER_ID, EVENT_ID));
CREATE TABLE MESSAGES (ID SERIAL PRIMARY KEY, SOURCE_USER BIGINT NOT NULL, TEXT VARCHAR(255) NOT NULL, FOREIGN KEY(SOURCE_USER) REFERENCES USERS(ID));
CREATE TABLE GROUPS_MESSAGES (GROUP_ID BIGINT NOT NULL, MESSAGE_ID BIGINT NOT NULL, FOREIGN KEY(GROUP_ID) REFERENCES GROUPS(ID), FOREIGN KEY(MESSAGE_ID) REFERENCES MESSAGES(ID), PRIMARY KEY(GROUP_ID, MESSAGE_ID));
CREATE TABLE AUTHORITIES (ID SERIAL PRIMARY KEY, AUTHORITY VARCHAR(12) NOT NULL);
CREATE TABLE USERS_AUTHORITIES (USER_ID BIGINT NOT NULL, AUTHORITY_ID BIGINT NOT NULL, FOREIGN KEY(USER_ID) REFERENCES USERS(ID), FOREIGN KEY(AUTHORITY_ID) REFERENCES AUTHORITIES(ID), PRIMARY KEY(USER_ID, AUTHORITY_ID));