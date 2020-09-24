create database 03spring_data_jpa CHARACTER SET utf8 COLLATE utf8_general_ci;

create table `user`
(
    `user_id`      INT          NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(20)  NOT NULL,
    `last_name`    VARCHAR(20)  NOT NULL,
    `age`          INT          NOT NULL COMMENT '年龄',
    `created_time` TIMESTAMP NOT NULL COMMENT '创建时间',
    `updated_time` TIMESTAMP NOT NULL COMMENT '最后修改时间',
    primary key (user_id)
) engine = innodb;
