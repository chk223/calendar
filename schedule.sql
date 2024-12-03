use calendar;
CREATE TABLE schedule (
    `id` VARCHAR(200) NOT NULL,
    `todo` VARCHAR(200) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `createdAt` DATETIME NOT NULL,
    `updatedAt` DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);
ALTER TABLE `calendar`.`schedule`
    DROP COLUMN `updatedAt`,
    DROP COLUMN `createdAt`,
    CHANGE COLUMN `name` `writerId` VARCHAR(200) NOT NULL ;

CREATE TABLE `calendar`.`writer` (
                                     `id` VARCHAR(200) NOT NULL,
                                     `name` VARCHAR(45) NOT NULL,
                                     `email` VARCHAR(100) NOT NULL,
                                     `createdAt` DATETIME NOT NULL,
                                     `updatedAt` DATETIME NOT NULL,
                                     PRIMARY KEY (`id`));

ALTER TABLE `calendar`.`schedule`
    ADD INDEX `writerId_idx` (`writerId` ASC) VISIBLE;
;
ALTER TABLE `calendar`.`schedule`
    ADD CONSTRAINT `writerId`
        FOREIGN KEY (`writerId`)
            REFERENCES `calendar`.`writer` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

