create database 02spring_data_jdbc CHARACTER SET utf8 COLLATE utf8_general_ci;

create table BOOK
(
    ID             varchar(32)  not null,
    TITLE          varchar(255) not null,
    AUTHOR         varchar(255),
    ISBN           varchar(15),
    PUBLISHED_DATE timestamp,
    PAGE_COUNT     integer,
    primary key (ID)
);