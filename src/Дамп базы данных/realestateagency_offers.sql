-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: realestateagency
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `offers`
(
  `id`         int(11)      NOT NULL AUTO_INCREMENT,
  `client`     varchar(255) NOT NULL,
  `realtor`    varchar(255) NOT NULL,
  `realEstate` varchar(255) NOT NULL,
  `price`      int(11)      NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_fk_idx` (`client`),
  KEY `realtor_fk_idx` (`realtor`),
  CONSTRAINT `client_fk` FOREIGN KEY (`client`) REFERENCES `clients` (`lastName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `realtor_fk` FOREIGN KEY (`realtor`) REFERENCES `realtors` (`lastName`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 46
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers`
  DISABLE KEYS */;
INSERT INTO `offers`
VALUES (44, 'Фомина', 'Малинина',
        'Квартира [Город=Пенза, Улица=Пушкина, Номер дома=26, Номер квартиры=54, Широта=-79.0, Долгота=43.0, Этаж=5, Кол-во комнат=4, Площадь=44.0]',
        33434),
       (45, 'Кочкина', 'Филина',
        'Земля [Город=Пенза, Улица=Аустрина, Номер дома=15, Номер квартиры=34, Широта=57.0, Долгота=23.0, Площадь=99.0]',
        47988987);
/*!40000 ALTER TABLE `offers`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-06-03 19:14:47
