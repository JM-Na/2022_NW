-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- User Table Create SQL
CREATE TABLE User
(
    `u_id`          INT            NOT NULL    AUTO_INCREMENT COMMENT '유저 index',
    `ID`            VARCHAR(45)    NOT NULL    COMMENT 'unique',
    `pwd`           VARCHAR(45)    NOT NULL,
    `status`        VARCHAR(1)     NOT NULL    DEFAULT 'Y' COMMENT '유저 상태',
    `nickname`      VARCHAR(10)    NOT NULL    COMMENT 'unique',
    `email`         VARCHAR(30)    NOT NULL,
    `birthdate`     DATE           NOT NULL,
    `phone`         VARCHAR(45)    NULL,
    `recent_login`  TIMESTAMP      NOT NULL,
    `online`        boolean        NOT NULL    COMMENT '유저 활동중인지',
    `address`       VARCHAR(45)    NOT NULL    COMMENT '주소',
    `web`           TEXT           NULL        COMMENT '깃헙 or 웹페이지',
    `login_num`     INT            NOT NULL    DEFAULT 0 COMMENT '로그인 횟수',
    `quote`         VARCHAR(20)    NULL        COMMENT '상메',
    PRIMARY KEY (u_id)
);

ALTER TABLE User COMMENT '유저';


-- Friend Table Create SQL
CREATE TABLE Friend
(
    `u_id`       INT            NOT NULL    COMMENT '유저 index',
    `friend_id`  INT            NOT NULL,
    `category`   VARCHAR(45)    NULL,
     PRIMARY KEY (u_id, friend_id)
);

ALTER TABLE Friend COMMENT '친구';

ALTER TABLE Friend
    ADD CONSTRAINT FK_Friend_u_id_User_u_id FOREIGN KEY (u_id)
        REFERENCES User (u_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE Friend
    ADD CONSTRAINT FK_Friend_friend_id_User_u_id FOREIGN KEY (friend_id)
        REFERENCES User (u_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- ban Table Create SQL
CREATE TABLE ban
(
    `u_id`    INT    NOT NULL       COMMENT '유저 index',
    `ban_id`  INT    NOT NULL,
     PRIMARY KEY (u_id, ban_id)
);

ALTER TABLE ban
    ADD CONSTRAINT FK_ban_u_id_User_u_id FOREIGN KEY (u_id)
        REFERENCES User (u_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ban
    ADD CONSTRAINT FK_ban_ban_id_User_u_id FOREIGN KEY (ban_id)
        REFERENCES User (u_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


