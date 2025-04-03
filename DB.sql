CREATE TABLE IF NOT EXISTS `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `auth_id` VARCHAR(50) DEFAULT NULL UNIQUE,
    `username` VARCHAR(50) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `gender` ENUM('male', 'female', 'other') NOT NULL DEFAULT 'other',
    `email` VARCHAR(50) NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT NULL NULL,
    `modified_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `modified_by` INT DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY(`id`) USING BTREE,
    FOREIGN KEY (`created_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`modified_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `oauth2_access_tokens`;
CREATE TABLE `oauth2_access_tokens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token_id` VARCHAR(255) NOT NULL UNIQUE,
  `token_value` TEXT NOT NULL,
  `user_id` INT NOT NULL,
  `issued_at` TIMESTAMP NOT NULL,
  `expires_at` TIMESTAMP NOT NULL,
  `user_agent` VARCHAR(150) DEFAULT NULL,
  `operating_system` VARCHAR(50) DEFAULT NULL,
  `remote_addr` VARCHAR(20) DEFAULT NULL,
  `revoked` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `oauth2_refresh_tokens`;
CREATE TABLE `oauth2_refresh_tokens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token_id` VARCHAR(255) NOT NULL UNIQUE,
  `token_value` TEXT NOT NULL,
  `user_id` INT NOT NULL DEFAULT 1,
  `issued_at` TIMESTAMP NOT NULL,
  `expires_at` TIMESTAMP NOT NULL,
  `user_agent` VARCHAR(150) DEFAULT NULL,
  `operating_system` VARCHAR(50) DEFAULT NULL,
  `remote_addr` VARCHAR(20) DEFAULT NULL,
  `revoked` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `oauth2_refresh_token_usage`;
CREATE TABLE `oauth2_refresh_token_usage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token_id` VARCHAR(255) NOT NULL UNIQUE,
  `token_value` TEXT NOT NULL,
  `used_at` DATETIME NOT NULL,
  `user_agent` VARCHAR(150) DEFAULT NULL,
  `operating_system` VARCHAR(50) DEFAULT NULL,
  `remote_addr` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `event_groups` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `color_code` VARCHAR(10) NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT NULL NULL,
    `modified_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `modified_by` INT DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY(`id`) USING BTREE,
    FOREIGN KEY (`created_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`modified_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `events` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `event_uid` VARCHAR(36) NOT NULL UNIQUE COMMENT 'Unique ID for the event',
    `event_group_id` INT NOT NULL,
    `timezone` VARCHAR(50) NOT NULL DEFAULT 'Asia/Phnom_Penh',
    `summary` VARCHAR(50) NOT NULL,
    `description` TEXT DEFAULT NULL,
    `location` VARCHAR(150) DEFAULT NULL,
    `timestamp` DATETIME NOT NULL COMMENT 'Creation timestamp (UTC format)',
    `is_allday` BOOLEAN DEFAULT FALSE,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NOT NULL,
    `status` ENUM('CONFIRMED', 'TENTATIVE', 'CANCELLED', NULL) DEFAULT NULL COMMENT 'CONFIRMED: The event is definite and scheduled, TENTATIVE: The event is planned but not yet finalized, CANCELLED: The event has been canceled and should be removed from the calendar.',
    `categories` VARCHAR(50) DEFAULT NULL,
    `url` VARCHAR(50) DEFAULT NULL,
    `recurring_frequency` ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY', NULL) DEFAULT NULL COMMENT 'Specifies how frequent it occur',
    `recurring_interval` VARCHAR(10) DEFAULT NULL COMMENT 'Defines how often it repeats (e.g., INTERVAL=2 means every 2 weeks)',
    `recurring_byday` VARCHAR(50) DEFAULT NULL COMMENT 'Specifies which days (MO, TU, WE, TH, FR, SA, SU)',
    `recurring_bymonth` VARCHAR(50) DEFAULT NULL COMMENT 'Specifies which months (1-12)',
    `recurring_bymonthday` VARCHAR(50) DEFAULT NULL COMMENT 'Specifies day of the month (1-31). Use along side with bymonth',
    `recurring_bysetpos` VARCHAR(10) DEFAULT NULL COMMENT 'Specific occurrence from a list of repeating dates within a given frequency',
    `recurring_count` INT DEFAULT 0 COMMENT 'Defines the total number of occurrences',
    `recurring_until` DATETIME DEFAULT NULL COMMENT 'Defines the last occurrence (YYYYMMDDT000000Z)',
    `recurring_exdate` TEXT DEFAULT NULL COMMENT 'Specifies except dates',
    `alarm_action` ENUM('DISPLAY', 'AUDIO', 'EMAIL', NULL) DEFAULT NULL COMMENT 'Type of alarm (DISPLAY, AUDIO, EMAIL)',
    `alarm_trigger` VARCHAR(10) DEFAULT NULL COMMENT 'When the alarm should trigger (-PT15M means 15 minutes before)',
    `alarm_description` VARCHAR(150) DEFAULT NULL COMMENT 'Notification message',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT NULL NULL,
    `modified_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `modified_by` INT DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY(`id`) USING BTREE,
    FOREIGN KEY (`event_group_id`) REFERENCES event_groups(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`created_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`modified_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `user_events` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `event_id` INT NOT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `created_by` INT NULL NULL,
    `modified_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
    `modified_by` INT DEFAULT NULL,
    `is_active` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY(`id`) USING BTREE,
    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`event_id`) REFERENCES events(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`created_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY (`modified_by`) REFERENCES users(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;