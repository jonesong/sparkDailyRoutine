/*
Navicat MySQL Data Transfer

Source Server         : localhost-trinoma
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : deepblue

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-08-15 20:40:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for levels
-- ----------------------------
DROP TABLE IF EXISTS `levels`;
CREATE TABLE `levels` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for projects
-- ----------------------------
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`user_id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `FK_USERS` (`user_id`),
  CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tasks
-- ----------------------------
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`project_id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `FK_PROJECTS` (`project_id`),
  KEY `id` (`id`),
  CONSTRAINT `FK_PROJECTS` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for todos
-- ----------------------------
DROP TABLE IF EXISTS `todos`;
CREATE TABLE `todos` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`task_id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `FK_TASKS` (`task_id`),
  CONSTRAINT `FK_TASKS` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(40) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `department_name` varchar(3) DEFAULT NULL,
  `level_name` varchar(3) NOT NULL,
  `status` char(1) NOT NULL DEFAULT '1',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
