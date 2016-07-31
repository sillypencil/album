/*
SQLyog Community v8.63 
MySQL - 5.0.41-community-nt : Database - album
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`album` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `album`;

/*Table structure for table `album` */

DROP TABLE IF EXISTS `album`;

CREATE TABLE `album` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `description` varchar(160) default NULL,
  `pictureNum` int(11) NOT NULL default '0',
  `ownUserId` int(11) NOT NULL,
  `createTime` timestamp NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ownUserId` (`ownUserId`,`name`),
  CONSTRAINT `album_ibfk_1` FOREIGN KEY (`ownUserId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `album` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL auto_increment,
  `content` varchar(160) NOT NULL,
  `imageId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `createTime` timestamp NULL default CURRENT_TIMESTAMP,
  `isRemove` bit(1) NOT NULL default '\0',
  PRIMARY KEY  (`id`),
  KEY `userId` (`userId`),
  KEY `imageId` (`imageId`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`imageId`) REFERENCES `image` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `id` int(11) NOT NULL auto_increment,
  `imagePath` varchar(48) NOT NULL,
  `favorNum` int(11) NOT NULL default '0',
  `collectNum` int(11) NOT NULL default '0',
  `ownAlbumId` int(11) NOT NULL,
  `createTime` timestamp NULL default CURRENT_TIMESTAMP,
  `permission` enum('ALL','FRIEND','PRIVATE') NOT NULL default 'ALL',
  `ownUserId` int(11) NOT NULL,
  `isRemove` bit(1) NOT NULL default '\0',
  PRIMARY KEY  (`id`),
  KEY `ownUserId` (`ownUserId`),
  KEY `ownAlbumId` (`ownAlbumId`),
  CONSTRAINT `image_ibfk_2` FOREIGN KEY (`ownUserId`) REFERENCES `user` (`id`),
  CONSTRAINT `image_ibfk_3` FOREIGN KEY (`ownAlbumId`) REFERENCES `album` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `image` */

/*Table structure for table `notify` */

DROP TABLE IF EXISTS `notify`;

CREATE TABLE `notify` (
  `id` int(11) NOT NULL auto_increment,
  `notifyContentId` int(11) NOT NULL,
  `notifyUserId` int(11) NOT NULL,
  `notifiedUserId` int(11) NOT NULL,
  `createTime` timestamp NULL default CURRENT_TIMESTAMP,
  `isNotify` bit(1) NOT NULL default '',
  `notifyType` enum('COMMENT','FAVOR','COLLECT') NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `notifyUserId` (`notifyUserId`),
  KEY `notifiedUserId` (`notifiedUserId`),
  CONSTRAINT `notify_ibfk_1` FOREIGN KEY (`notifyUserId`) REFERENCES `user` (`id`),
  CONSTRAINT `notify_ibfk_2` FOREIGN KEY (`notifiedUserId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `notify` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `account` varchar(20) NOT NULL,
  `password` varchar(84) NOT NULL,
  `nickname` varchar(36) NOT NULL,
  `avatarPath` varchar(48) default NULL,
  `followNum` int(11) NOT NULL default '0',
  `fansNum` int(11) NOT NULL default '0',
  `notifyNum` int(11) NOT NULL default '0',
  `registerTime` timestamp NULL default CURRENT_TIMESTAMP,
  `favorNum` int(11) NOT NULL default '0',
  `collectNum` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*Table structure for table `user_collect_image` */

DROP TABLE IF EXISTS `user_collect_image`;

CREATE TABLE `user_collect_image` (
  `id` int(11) NOT NULL auto_increment,
  `userId` int(11) NOT NULL,
  `imageId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `userId` (`userId`),
  KEY `imageId` (`imageId`),
  CONSTRAINT `user_collect_image_ibfk_2` FOREIGN KEY (`imageId`) REFERENCES `image` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_collect_image_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_collect_image` */

/*Table structure for table `user_favor_image` */

DROP TABLE IF EXISTS `user_favor_image`;

CREATE TABLE `user_favor_image` (
  `id` int(11) NOT NULL auto_increment,
  `userId` int(11) NOT NULL,
  `imageId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `userId` (`userId`),
  KEY `imageId` (`imageId`),
  CONSTRAINT `user_favor_image_ibfk_2` FOREIGN KEY (`imageId`) REFERENCES `image` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_favor_image_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_favor_image` */

/*Table structure for table `user_follow` */

DROP TABLE IF EXISTS `user_follow`;

CREATE TABLE `user_follow` (
  `id` int(11) NOT NULL auto_increment,
  `followerId` int(11) NOT NULL,
  `leaderId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `followerId` (`followerId`,`leaderId`),
  KEY `leaderId` (`leaderId`),
  CONSTRAINT `user_follow_ibfk_1` FOREIGN KEY (`followerId`) REFERENCES `user` (`id`),
  CONSTRAINT `user_follow_ibfk_2` FOREIGN KEY (`leaderId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_follow` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
