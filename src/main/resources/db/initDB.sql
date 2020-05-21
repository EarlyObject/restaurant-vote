DROP TABLE IF EXISTS VOTES;
DROP TABLE IF EXISTS USERS_ROLES;
DROP TABLE IF EXISTS ROLES_AUTHORITIES;
DROP TABLE IF EXISTS RESTAURANTS_MEALS;
DROP TABLE IF EXISTS MEALS;
DROP TABLE IF EXISTS RESTAURANTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS ROLES;

DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 1000;

create table ROLES
(
    ID   INT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    NAME VARCHAR(20)                                      not null
);

create table AUTHORITIES
(
    ID   INT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    NAME VARCHAR(20)                                      not null
);

create table USERS
(
    ID                        INT     default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    EMAIL                     VARCHAR(120)                                         not null
        constraint UNIQUE_EMAIL
            unique,
    ENCRYPTED_PASSWORD        VARCHAR(255)                                         not null,
    FIRST_NAME                VARCHAR(50)                                          not null,
    LAST_NAME                 VARCHAR(50)                                          not null,
    USER_ID                   VARCHAR(255)                                         not null
);

create index IDX_EMAIL
    on USERS (EMAIL);

create index IDX_USERID
    on USERS (USER_ID);

create table RESTAURANTS
(
    ID      INT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    ADDRESS VARCHAR(255)                                     not null,
    NAME    VARCHAR(255)                                     not null,
    constraint UNIQUE_NAME_ADDRESS
        unique (NAME, ADDRESS)
);

create table MEALS
(
    ID            INT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    DATE          DATE                                             not null,
    DESCRIPTION   VARCHAR(255)                                     not null,
    PRICE         INT                                              not null,
    RESTAURANT_ID INT                                              not null,
    constraint UNIQUE_DATE_DESC_REST
        unique (DATE, DESCRIPTION, RESTAURANT_ID),
    constraint FK_RESTAURANT_ID
        foreign key (RESTAURANT_ID) references RESTAURANTS (ID)
);

create index IDX_RESTAURANT_ID_IN_MEALS
    on MEALS (RESTAURANT_ID);

create index IDX_DATE
    on MEALS (DATE);

create table VOTES
(
    ID            INT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    CREATED       TIMESTAMP                                        not null,
    DATE          DATE                                             not null,
    RESTAURANT_ID INT,
    USER_ID       VARCHAR(255),
    constraint UNIQUE_DATE_USER_ID
        unique (DATE, USER_ID),
    constraint FK_RESTAURANT_ID_IN_VOTES
        foreign key (RESTAURANT_ID) references RESTAURANTS (ID),
    constraint FK_USER_ID_IN_VOTES
        foreign key (USER_ID) references USERS (ID)
            on delete cascade
);

create index IDX_V_USER_ID_AND_DATE
    on VOTES (USER_ID, DATE);

create table USERS_ROLES
(
    USERS_ID INT not null,
    ROLES_ID INT not null,
    constraint FK_ROLES_ID_IN_UR
        foreign key (ROLES_ID) references ROLES (ID),
    constraint FK_USER_ID_IN_UR
        foreign key (USERS_ID) references USERS (ID)
);

create table ROLES_AUTHORITIES
(
    ROLES_ID       INT not null,
    AUTHORITIES_ID INT not null,
    constraint FK_ROLES_ID_IN_RA
        foreign key (ROLES_ID) references ROLES (ID),
    constraint FK_AUTH_ID_IN_RA
        foreign key (AUTHORITIES_ID) references AUTHORITIES (ID)
);

