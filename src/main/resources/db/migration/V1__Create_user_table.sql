create table USER
(
    ID           BIGINT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    bio varchar(256),
    AVATAR_URL VARCHAR(100),
    constraint USER_PK
        primary key (ID)
);