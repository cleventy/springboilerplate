INSERT INTO `user` (`USERNAME`, `PASSWORD`, `EMAIL`, `NAME`, `ROLE`, `STATE`) VALUES('admin', '$2a$16$eWxFgBqd.ktKcouUnBU/h.p9axUdMglZ6VaTU05dc8Jfjf2BxSosS', 'admin@domain.com', 'Admin Application', 'ADMIN', 1);
INSERT INTO `user` (`USERNAME`, `PASSWORD`, `EMAIL`, `NAME`, `ROLE`, `STATE`) VALUES('user', '$2a$16$eWxFgBqd.ktKcouUnBU/h.p9axUdMglZ6VaTU05dc8Jfjf2BxSosS', 'user@domain.com', 'John Doe', 'USER', 1);

INSERT INTO `category` (`NAME`) VALUES('Aventura');
SET @CATEGORY_ADVENTURE_ID = LAST_INSERT_ID();

INSERT INTO `category` (`NAME`) VALUES('Niños');
SET @CATEGORY_KIDS_ID = LAST_INSERT_ID();

INSERT INTO `category` (`NAME`) VALUES('Histórica');
SET @CATEGORY_HISTORIC_ID = LAST_INSERT_ID();

INSERT INTO `category` (`NAME`) VALUES('Fantástica');
SET @CATEGORY_FANTASTIC_ID = LAST_INSERT_ID();




drop procedure if exists doInserts;
DELIMITER //  
CREATE PROCEDURE doInserts()   
BEGIN
DECLARE i INT DEFAULT 1; 
WHILE (i <= 20) DO
	
	INSERT INTO `story` (`TITLE`, `DESCRIPTION`, `WEB_URL`, `IMAGE`, `IMAGE_MINI`, `DIFFICULTY`, `BOOK_ENABLED`, `BOOK_PRICE`, `BOOK_DESCRIPTION`,`ORDER_N`, `STATE`) 
	VALUES(
		CONCAT('Titulo historia ', i), 
		CONCAT('Descripción historia ', i), 
		CONCAT('https://web-dev.application.com/story-', i), 
		'https://s3-eu-west-1.amazonaws.com/application/photo_cropped.jpg', 
		'https://s3-eu-west-1.amazonaws.com/application/photo_mini.jpg',
		i%5+1, 
		true,
		59,
		CONCAT('descripción de la <strong>reserva</strong> para la historia ', i), 
		1,
		1
	);
	SET @STORY_TEST_ID = LAST_INSERT_ID();
	INSERT INTO `story_category` (`STORY_ID`, `CATEGORY_ID`) VALUES (@STORY_TEST_ID, @CATEGORY_ADVENTURE_ID);
	
	IF (i%4=2) THEN
		INSERT INTO `story_category` (`STORY_ID`, `CATEGORY_ID`) VALUES (@STORY_TEST_ID, @CATEGORY_KIDS_ID);
	END IF;
	IF (i%4=3 OR i%4=3) THEN
		INSERT INTO `story_category` (`STORY_ID`, `CATEGORY_ID`) VALUES (@STORY_TEST_ID, @CATEGORY_HISTORIC_ID);
	END IF;
	IF (i%4=4) THEN
		INSERT INTO `story_category` (`STORY_ID`, `CATEGORY_ID`) VALUES (@STORY_TEST_ID, @CATEGORY_FANTASTIC_ID);
	END IF;
	
    SET i = i+1;
END WHILE;
END;
//  
CALL doInserts(); 
drop procedure if exists doInserts;
