/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : ss_demo

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 13/03/2019 09:21:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_resource
-- ----------------------------
DROP TABLE IF EXISTS `s_resource`;
CREATE TABLE `s_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `res_name` varchar(255) DEFAULT NULL,
  `res_code` varchar(255) DEFAULT NULL,
  `url` varchar(1024) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_resource
-- ----------------------------
BEGIN;
INSERT INTO `s_resource` VALUES (1, 'admin', 'AUTH_1', '/admin.html', '1');
COMMIT;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role
-- ----------------------------
BEGIN;
INSERT INTO `s_role` VALUES (1, 'roleAdmin');
COMMIT;

-- ----------------------------
-- Table structure for s_role_res
-- ----------------------------
DROP TABLE IF EXISTS `s_role_res`;
CREATE TABLE `s_role_res` (
  `role_id` bigint(20) NOT NULL,
  `res_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`res_id`),
  KEY `FKn0rw9oaxf6r9k84llqyd9an5t` (`res_id`),
  CONSTRAINT `FKgwx6rp3w6cincqs6cfow9xpeb` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`),
  CONSTRAINT `FKn0rw9oaxf6r9k84llqyd9an5t` FOREIGN KEY (`res_id`) REFERENCES `s_resource` (`id`),
  CONSTRAINT `fk_role_res_res_id` FOREIGN KEY (`res_id`) REFERENCES `s_resource` (`id`),
  CONSTRAINT `fk_role_res_role_id` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_role_res
-- ----------------------------
BEGIN;
INSERT INTO `s_role_res` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user
-- ----------------------------
BEGIN;
INSERT INTO `s_user` VALUES (1, 'user', '$2a$04$1UkjSF87uZunsOTquwNnDeIvyIWgY31jC/p0CP/JMrCJw.PBmewcO');
INSERT INTO `s_user` VALUES (2, 'casuser', '$2a$04$1UkjSF87uZunsOTquwNnDeIvyIWgY31jC/p0CP/JMrCJw.PBmewcO');
COMMIT;

-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK73hg6eioqf8til8u4luq2hu76` (`user_id`),
  CONSTRAINT `FK73hg6eioqf8til8u4luq2hu76` FOREIGN KEY (`user_id`) REFERENCES `s_user` (`id`),
  CONSTRAINT `FKmi8o2ld5s3di95yfp7hwmlvab` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `s_role` (`id`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `s_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_user_role
-- ----------------------------
BEGIN;
INSERT INTO `s_user_role` VALUES (1, 1);
INSERT INTO `s_user_role` VALUES (1, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
