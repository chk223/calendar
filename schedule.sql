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