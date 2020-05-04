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
    ID   BIGINT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    NAME VARCHAR(20)                                         not null
);

create table AUTHORITIES
(
    ID   BIGINT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    NAME VARCHAR(20)                                         not null
);

create table USERS
(
    ID                        BIGINT  default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    EMAIL                     VARCHAR(120)                                         not null
        constraint UK_6DOTKOTT2KJSP8VW4D0M25FB7
            unique,
    EMAIL_VERIFICATION_STATUS BOOLEAN default TRUE                                 not null,
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
    ID      BIGINT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    ADDRESS VARCHAR(255)                                        not null,
    NAME    VARCHAR(255)                                        not null
);

create table MEALS
(
    ID            BIGINT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    DATE          DATE                                                not null,
    DESCRIPTION   VARCHAR(255)                                        not null,
    PRICE         DOUBLE                                              not null,
    RESTAURANT_ID BIGINT                                              not null,
    constraint FKBGJH8QHPL94LSO4D5L6NBIYRY
        foreign key (RESTAURANT_ID) references RESTAURANTS (ID)
);

create index IDX_ID
    on MEALS (ID);

create index IDX_RESTAURANTID
    on MEALS (RESTAURANT_ID);

create index IDX_DATE
    on MEALS (DATE);

create index IDX_RESTAURANTID_DATE
    on MEALS (RESTAURANT_ID, DATE);

create table VOTES
(
    ID            BIGINT default NEXT VALUE FOR "PUBLIC"."GLOBAL_SEQ" not null
        primary key,
    CREATED       TIMESTAMP                                           not null,
    DATE          DATE                                                not null,
    RESTAURANT_ID BIGINT,
    USER_ID       VARCHAR(255),
    constraint FK93NQD6KKY7CYVBE4Q1EUP9GCX
        foreign key (RESTAURANT_ID) references RESTAURANTS (ID),
    constraint FKLI4UJ3IC2VYPF5PIALCHJ925E
        foreign key (USER_ID) references USERS (ID)
            on delete cascade
);

create index IDX_V_USERID
    on VOTES (USER_ID);

create index IDX_V_USERIDANDDATE
    on VOTES (USER_ID, DATE);

create table USERS_ROLES
(
    USERS_ID BIGINT not null,
    ROLES_ID BIGINT not null,
    constraint FKA62J07K5MHGIFPP955H37PONJ
        foreign key (ROLES_ID) references ROLES (ID),
    constraint FKML90KEF4W2JY7OXYQV742TSFC
        foreign key (USERS_ID) references USERS (ID)
);

create table ROLES_AUTHORITIES
(
    ROLES_ID       BIGINT not null,
    AUTHORITIES_ID BIGINT not null,
    constraint FKCJ918H3EE3QAD1XWBX4JVVCGC
        foreign key (ROLES_ID) references ROLES (ID),
    constraint FKE4PJSN2C2TTG8BPBE1YK29SNN
        foreign key (AUTHORITIES_ID) references AUTHORITIES (ID)
);

