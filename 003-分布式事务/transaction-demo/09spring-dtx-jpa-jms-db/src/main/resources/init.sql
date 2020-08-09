create database 09spring_dtx_jpa_jms_db CHARACTER SET utf8 COLLATE utf8_general_ci;

use 09spring_dtx_jpa_jms_db;

create table `user`
(
    `user_id`  INT         NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL,
    `deposit`  INT         NOT NULL,
    primary key (user_id)
) engine = innodb;
