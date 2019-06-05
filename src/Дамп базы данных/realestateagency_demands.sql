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
-- Table structure for table `demands`
--

DROP TABLE IF EXISTS `demands`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `demands`
(
  `id`                int(11)      NOT NULL AUTO_INCREMENT,
  `client`            varchar(255) NOT NULL,
  `realtor`           varchar(255) NOT NULL,
  `typeRealEstate`    varchar(255) NOT NULL,
  `address`           varchar(255) NOT NULL,
  `minPrice`          int(11)      NOT NULL,
  `maxPrice`          int(11)      NOT NULL,
  `minSquare`         int(11) DEFAULT NULL,
  `maxSquare`         int(11) DEFAULT NULL,
  `minCountOfRooms`   int(11) DEFAULT NULL,
  `maxCountOfRooms`   int(11) DEFAULT NULL,
  `minFloor`          int(11) DEFAULT NULL,
  `maxFloor`          int(11) DEFAULT NULL,
  `minNumberOfFloors` int(11) DEFAULT NULL,
  `maxNumberOfFloors` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_index_idx` (`client`),
  KEY `realtor_index_idx` (`realtor`),
  CONSTRAINT `client_index` FOREIGN KEY (`client`) REFERENCES `clients` (`lastName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `realtor_index` FOREIGN KEY (`realtor`) REFERENCES `realtors` (`lastName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 27
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demands`
--

LOCK TABLES `demands` WRITE;
/*!40000 ALTER TABLE `demands`
  DISABLE KEYS */;
INSERT INTO `demands`
VALUES (1, 'Кочкина', 'Сидоров', 'Дом', 'Пушкина 128', 58, 5, 6, 7, 8, 9, 2, 4, 10, 11),
       (9, 'Асташкин', 'Сидоров', 'Дом', 'Пенза, проспект Строителей 7', 1000000, 2500000, 54, 69, 2, 4, NULL, NULL, 6,
        9),
       (18, 'Кочкина', 'Филина', 'Квартира', 'Знаменск, ул. Пионерская 1-32', 790000, 1850000, 36, 57, 4, 2, 4, 9, NULL,
        NULL),
       (19, 'Асташкин', 'Малинина', 'Квартира', 'Пенза, ул.Кирова 32-5', 1550000, 2370000, 50, 75, 2, 3, 3, 512, NULL,
        NULL),
       (21, 'Асташкин', 'Сидоров', 'Земля', 'Пенза, ул. Кураева 7', 3500000, 9000000, 450, 600, NULL, NULL, NULL, NULL,
        NULL, NULL),
       (23, 'Фомина', 'Филина', 'Земля', 'Терновка', 2500000, 3400000, 500, 700, NULL, NULL, NULL, NULL, NULL, NULL),
       (26, 'Фомина', 'Малинина', 'Земля', 'eqwe', 123, 1234, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `demands`
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

-- Dump completed on 2019-06-03 19:14:43
