-- Creates the permission table, represents the users permissions on the system

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;