/*
Navicat MySQL Data Transfer

Source Server         : localhost-trinoma
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : deepblue

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-10-12 11:07:14
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
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES ('1', 'User', '1');
INSERT INTO `departments` VALUES ('2', 'Project', '1');

-- ----------------------------
-- Table structure for levels
-- ----------------------------
DROP TABLE IF EXISTS `levels`;
CREATE TABLE `levels` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of levels
-- ----------------------------

-- ----------------------------
-- Table structure for modules
-- ----------------------------
DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_USERS` (`user_id`) USING BTREE,
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of modules
-- ----------------------------
INSERT INTO `modules` VALUES ('1', '1', 'User', '1');
INSERT INTO `modules` VALUES ('2', '1', 'Project', '1');

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
  KEY `FK_USERS` (`user_id`),
  KEY `name` (`name`) USING BTREE,
  CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of projects
-- ----------------------------

-- ----------------------------
-- Table structure for tasks
-- ----------------------------
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `due_date` varchar(10) DEFAULT '',
  `note` varchar(100) DEFAULT '',
  `done` char(1) NOT NULL DEFAULT '1',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`project_id`),
  KEY `FK_PROJECTS` (`project_id`),
  KEY `id` (`id`),
  KEY `name` (`name`) USING BTREE,
  CONSTRAINT `FK_PROJECTS` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tasks
-- ----------------------------

-- ----------------------------
-- Table structure for todos
-- ----------------------------
DROP TABLE IF EXISTS `todos`;
CREATE TABLE `todos` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) NOT NULL,
  `note` varchar(100) DEFAULT '',
  `date_started` varchar(10) NOT NULL,
  `time_total` varchar(8) NOT NULL,
  `time_start` varchar(8) NOT NULL,
  `time_end` varchar(8) NOT NULL,
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`task_id`),
  KEY `FK_TASKS` (`task_id`),
  CONSTRAINT `FK_TASKS` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of todos
-- ----------------------------

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
  `status` char(1) NOT NULL DEFAULT '1',
  `deleted` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'jonesong', 'jonesong', 'Jones', 'Ong', '1', '1');
