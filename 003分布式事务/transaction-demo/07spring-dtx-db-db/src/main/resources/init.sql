create database 07spring_dtx_db_db_user CHARACTER SET utf8 COLLATE utf8_general_ci;

use 07spring_dtx_db_db_user;

create table `user`
(
    `user_id`  INT         NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL,
    `deposit`  INT         NOT NULL,
    primary key (user_id)
) engine = innodb;

create database 07spring_dtx_db_db_order CHARACTER SET utf8 COLLATE utf8_general_ci;

use 07spring_dtx_db_db_order;

create table `user_order`
(
    `order_id` INT          NOT NULL AUTO_INCREMENT,
    `user_id`  INT          NOT NULL,
    `title`    VARCHAR(100) NOT NULL,
    `amount`   INT          NOT NULL,
    primary key (order_id)
) engine = innodb;
