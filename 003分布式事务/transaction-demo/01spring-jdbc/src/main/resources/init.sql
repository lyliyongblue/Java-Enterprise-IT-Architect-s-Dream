create database 01spring_jdbc CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 创建学生表
CREATE TABLE STUDENT
(
    ID   INT         NOT NULL AUTO_INCREMENT comment '主键',
    NAME VARCHAR(20) NOT NULL comment '姓名',
    AGE  INT         NOT NULL comment '年龄',
    CREATED_TIME timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UPDATED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (ID)
) engine=innodb;


CREATE TABLE EMPLOYEE
(
	ID int  NOT NULL PRIMARY KEY,
	FIRST_NAME varchar(255),
	LAST_NAME varchar(255),
	ADDRESS varchar(255)
);


INSERT INTO EMPLOYEE VALUES (1, 'James', 'Gosling', 'Canada');
INSERT INTO EMPLOYEE VALUES (2, 'Donald', 'Knuth', 'USA');
INSERT INTO EMPLOYEE VALUES (3, 'Linus', 'Torvalds', 'Finland');
INSERT INTO EMPLOYEE VALUES (4, 'Dennis', 'Ritchie', 'USA');