-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.28 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4160
-- Date/time:                    2012-12-22 15:30:43
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for carrental
DROP DATABASE IF EXISTS `carrental`;
CREATE DATABASE IF NOT EXISTS `carrental` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `carrental`;


-- Dumping structure for table carrental.access
DROP TABLE IF EXISTS `access`;
CREATE TABLE IF NOT EXISTS `access` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `value` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.access: ~4 rows (approximately)
DELETE FROM `access`;
/*!40000 ALTER TABLE `access` DISABLE KEYS */;
INSERT INTO `access` (`id`, `name`, `value`) VALUES
	(1, 'Вход в систему', 'login'),
	(2, 'Работа с заказами клиентов', 'order.admin'),
	(3, 'Заказ машины', 'order.make'),
	(6, 'Просмотр пользователей', 'users.view');
/*!40000 ALTER TABLE `access` ENABLE KEYS */;


-- Dumping structure for table carrental.car
DROP TABLE IF EXISTS `car`;
CREATE TABLE IF NOT EXISTS `car` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regNumber` char(10) NOT NULL,
  `model_id` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double(10,0) NOT NULL,
  `photo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `regNumber` (`regNumber`),
  UNIQUE KEY `photo` (`photo`),
  KEY `FK_auto_model` (`model_id`),
  CONSTRAINT `FK_auto_model` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.car: ~4 rows (approximately)
DELETE FROM `car`;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` (`id`, `regNumber`, `model_id`, `name`, `price`, `photo`) VALUES
	(1, 'M081FFN', 1, 'Ласточка', 100, 'mazda626.jpg'),
	(2, 'Z192DOA', 3, 'Путешественник', 200, 'tribute.jpg'),
	(3, 'A654BDA', 6, 'Бизнесмен', 500, 'audiA6.jpg'),
	(4, 'F777FNH', 7, 'Спортсмен', 600, 'rx9.jpg');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;


-- Dumping structure for table carrental.damage
DROP TABLE IF EXISTS `damage`;
CREATE TABLE IF NOT EXISTS `damage` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(10) unsigned NOT NULL DEFAULT '0',
  `description` varchar(255) NOT NULL DEFAULT '0',
  `price` double unsigned NOT NULL DEFAULT '0',
  `repaired` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_damage_order` (`order_id`),
  CONSTRAINT `FK_damage_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.damage: ~3 rows (approximately)
DELETE FROM `damage`;
/*!40000 ALTER TABLE `damage` DISABLE KEYS */;
INSERT INTO `damage` (`id`, `order_id`, `description`, `price`, `repaired`) VALUES
	(4, 56, 'Разбито лобовое стекло', 90, 1),
	(5, 71, 'Лопнуло колесо', 20, 1),
	(6, 56, 'Порвали сидение', 300, 0);
/*!40000 ALTER TABLE `damage` ENABLE KEYS */;


-- Dumping structure for table carrental.model
DROP TABLE IF EXISTS `model`;
CREATE TABLE IF NOT EXISTS `model` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vendor_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vendor_id_name` (`vendor_id`,`name`),
  CONSTRAINT `FK_Model_vendor` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.model: ~6 rows (approximately)
DELETE FROM `model`;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` (`id`, `vendor_id`, `name`) VALUES
	(1, 2, '626'),
	(4, 2, 'RX 7'),
	(7, 2, 'RX 9'),
	(3, 2, 'Tribute'),
	(5, 6, 'Qashqai'),
	(6, 19, 'A6');
/*!40000 ALTER TABLE `model` ENABLE KEYS */;


-- Dumping structure for table carrental.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `car_id` int(10) unsigned NOT NULL DEFAULT '0',
  `client_id` int(10) unsigned NOT NULL DEFAULT '0',
  `beginRent` datetime NOT NULL,
  `endRent` datetime NOT NULL,
  `status` tinyint(3) unsigned NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_car` (`car_id`),
  KEY `FK_order_user` (`client_id`),
  CONSTRAINT `FK_order_car` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`client_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.order: ~5 rows (approximately)
DELETE FROM `order`;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` (`id`, `car_id`, `client_id`, `beginRent`, `endRent`, `status`, `createTime`) VALUES
	(56, 1, 38, '2012-12-15 00:00:00', '2012-12-16 00:00:00', 1, '2012-12-14 16:23:53'),
	(58, 1, 38, '2012-12-15 16:28:00', '2012-12-16 00:00:00', 0, '2012-12-14 16:28:45'),
	(71, 2, 42, '2012-12-16 10:00:00', '2012-12-31 11:00:00', 0, '2012-12-15 17:55:20'),
	(72, 1, 38, '2012-12-17 00:00:00', '2012-12-19 00:00:00', 1, '2012-12-16 12:59:52'),
	(73, 2, 38, '2013-01-25 00:00:00', '2013-02-28 00:00:00', 0, '2012-12-20 17:15:02');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;


-- Dumping structure for table carrental.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL DEFAULT '0',
  `password` char(32) NOT NULL DEFAULT '0',
  `firstName` varchar(50) NOT NULL DEFAULT '0',
  `lastName` varchar(50) NOT NULL DEFAULT '0',
  `middleName` varchar(50) NOT NULL DEFAULT '0',
  `email` varchar(30) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.user: ~4 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `firstName`, `lastName`, `middleName`, `email`, `telephone`) VALUES
	(38, 'Max', '202cb962ac5975b964b7152d234b70', 'Максим', 'Денисов', 'Александрович', 'max.denissov@gmail.com', '+7(701)255-44-47'),
	(40, 'User', '202cb962ac5975b964b7152d234b70', 'Пользователь', 'Пользователь', 'Пользователь', 'user@mail.ru', NULL),
	(41, 'Kirill', '202cb962ac5975b964b7152d234b70', 'Кирилл', 'Панкрашев', 'Николаевич', NULL, NULL),
	(42, 'Marina', '202cb962ac5975b964b7152d234b70', 'Марина', 'Козаченко', 'Александровна', NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table carrental.user_access
DROP TABLE IF EXISTS `user_access`;
CREATE TABLE IF NOT EXISTS `user_access` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `access_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_access_id` (`user_id`,`access_id`),
  KEY `FK_user_access_access` (`access_id`),
  CONSTRAINT `FK_user_access_access` FOREIGN KEY (`access_id`) REFERENCES `access` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_access_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.user_access: ~10 rows (approximately)
DELETE FROM `user_access`;
/*!40000 ALTER TABLE `user_access` DISABLE KEYS */;
INSERT INTO `user_access` (`id`, `user_id`, `access_id`) VALUES
	(1, 38, 1),
	(2, 38, 2),
	(3, 38, 3),
	(5, 38, 6),
	(6, 40, 3),
	(7, 41, 1),
	(8, 41, 3),
	(9, 41, 6),
	(10, 42, 1),
	(11, 42, 3);
/*!40000 ALTER TABLE `user_access` ENABLE KEYS */;


-- Dumping structure for table carrental.vendor
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE IF NOT EXISTS `vendor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Dumping data for table carrental.vendor: ~14 rows (approximately)
DELETE FROM `vendor`;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` (`id`, `name`) VALUES
	(19, 'Audi'),
	(13, 'BMW'),
	(9, 'Fiat'),
	(11, 'Honda'),
	(7, 'Hyundai'),
	(2, 'Mazda'),
	(3, 'Mercedes'),
	(6, 'Nissan'),
	(4, 'Opel'),
	(14, 'Suzuki'),
	(8, 'Toyota'),
	(16, 'VAZ'),
	(15, 'Volvo'),
	(18, 'ZAZ');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
