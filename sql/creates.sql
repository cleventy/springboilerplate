SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`ID` BIGINT NOT NULL AUTO_INCREMENT,    
	`USERNAME` VARCHAR(128) NOT NULL,
	`PASSWORD` VARCHAR(128) NOT NULL,
	`EMAIL` VARCHAR(128),
	`NAME` VARCHAR(128),
	`ROLE` VARCHAR(16),
	`STATE` INT NOT NULL,
	`REGISTER_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`VERSION` BIGINT NOT NULL DEFAULT 0,
	CONSTRAINT `PK_USER` PRIMARY KEY (`ID`),
	CONSTRAINT `UK_USER_USERNAME` UNIQUE KEY (`USERNAME`),
	CONSTRAINT `UK_USER_EMAIL` UNIQUE KEY (`EMAIL`)
)  CHARACTER SET utf8 COLLATE utf8_bin engine=InnoDB;
CREATE INDEX `I_USER_USERNAME` ON `user`(`USERNAME`);
CREATE INDEX `I_USER_EMAIL` ON `user`(`EMAIL`);

DROP TABLE IF EXISTS `story`;
CREATE TABLE `story` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `TITLE` VARCHAR(256) NOT NULL,
    `DESCRIPTION` TEXT,
    `WEB_URL` TEXT NULL,
    `IMAGE` TEXT,
    `IMAGE_MINI` TEXT,    
    `DIFFICULTY` DOUBLE NOT NULL,
    `BOOK_ENABLED` BOOLEAN NOT NULL,
    `BOOK_PRICE` DOUBLE NULL,
    `BOOK_DESCRIPTION` TEXT NULL,
    `ORDER_N` INT NOT NULL,
    `STATE` INT NOT NULL,
    `VERSION` BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT `PK_STORY` PRIMARY KEY (`ID`)
)  CHARACTER SET utf8 COLLATE utf8_bin engine=InnoDB;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `NAME` VARCHAR(128) NOT NULL,
    `VERSION` BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT `PK_CATEGORY` PRIMARY KEY (`ID`),
    CONSTRAINT `UK_CATEGORY` UNIQUE KEY (`NAME`)
)  CHARACTER SET utf8 COLLATE utf8_bin engine=InnoDB;

DROP TABLE IF EXISTS `story_category`;
CREATE TABLE `story_category` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `STORY_ID` BIGINT NOT NULL,
    `CATEGORY_ID` BIGINT NOT NULL,
    `VERSION` BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT `PK_STORY_CATEGORY` PRIMARY KEY (`ID`),
	CONSTRAINT `FK_STORY_CATEGORY_STORY` FOREIGN KEY (`STORY_ID`) REFERENCES `story` (`ID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
	CONSTRAINT `FK_STORY_CATEGORY_CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`ID`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)  CHARACTER SET utf8 COLLATE utf8_bin engine=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
