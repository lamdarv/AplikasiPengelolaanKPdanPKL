-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: db-account
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.27-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'panitiaD3','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',0),(2,'panitiaD4','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',0),(3,'kaprodiD3','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',3),(4,'kaprodiD4','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',3),(5,'181511003','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(6,'181511015','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(7,'181511041','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(8,'191511001','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(9,'191511002','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(10,'191511003','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(11,'191511004','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(12,'191511005','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(13,'191511006','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(14,'191511007','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(15,'191511008','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(16,'191511009','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(17,'191511010','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(18,'191511011','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(19,'191511012','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(20,'191511013','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(21,'191511014','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(22,'191511015','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(23,'191511016','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(24,'191511017','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(25,'191511019','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(26,'191511021','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(27,'191511022','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(28,'191511023','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(29,'191511025','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(30,'191511026','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(31,'191511028','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(32,'191511029','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(33,'191511030','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(34,'191511031','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(35,'191511032','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(36,'191511033','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(37,'191511034','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(38,'191511035','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(39,'191511036','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(40,'191511037','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(41,'191511038','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(42,'191511039','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(43,'191511040','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(44,'191511041','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(45,'191511042','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(46,'191511043','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(47,'191511044','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(48,'191511045','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(49,'191511046','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(50,'191511047','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(51,'191511048','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(52,'191511049','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(53,'191511050','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(54,'191511051','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(55,'191511052','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(56,'191511054','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(57,'191511056','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(58,'191511057','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(59,'191511058','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(60,'191511059','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(61,'191511060','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(62,'191511061','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(63,'191511063','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(64,'191511064','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(65,'191511065','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(66,'padepokan','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(67,'bims','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(68,'beeso','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(69,'neural','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(70,'lapi','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(71,'kerjaku','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(72,'kazee','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(73,'karisma','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(74,'bejana','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(75,'digital','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(76,'hirata','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(77,'velocite','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(78,'kabayan','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(79,'garuda','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(80,'beete','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(81,'star','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(82,'ebdesk','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(83,'kreasi','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(84,'qroi','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(85,'selada','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(86,'181524002','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(87,'181524003','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(88,'181524004','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(89,'181524005','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(90,'181524006','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(91,'181524007','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(92,'181524008','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(93,'181524009','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(94,'181524010','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(95,'181524012','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(96,'181524013','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(97,'181524014','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(98,'181524015','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(99,'181524016','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(100,'181524017','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(101,'181524020','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(102,'181524021','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(103,'181524022','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(104,'181524023','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(105,'181524025','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(106,'181524026','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(107,'181524027','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(108,'181524028','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(109,'181524029','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(110,'181524030','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(111,'181524031','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(112,'181524032','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(113,'nubela','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(114,'valvo','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(115,'len','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(116,'sense','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(117,'idouble','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(118,'periplus','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(119,'fujitsu','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',2),(120,'201511016','$2a$10$oGnsCxrhB5Qw1IWHZTt3juTqPNljxBpBhrEIWX5apLK8RDWwlr9rW',1),(121,'roy12ali@gmail.com','$2a$10$gs5yb9jDOA96jwtXibSLSe6XOMFqErfqTZi9sEfjda.1o338xhfp.',2),(122,'company@gmail.com','$2a$10$Ofz3p6QPaoTRMxtNgzsizOzXqWhE9egifpLYbfBSWxFirLrU1Fhi6',2),(123,'padepokan@gmail.com','$2a$10$OptXxP5iRXvy2eIsrcCFAuE2GbbF05l0CZSPU38O8Eol6b.oofx7m',2),(124,'bims@gmail.com','$2a$10$qMsX4DI/j.TRYQetXEpD9e/tUIcN/yLa7QREQ1b0G.DnSF7whrs5a',2),(125,'beeso@gmail.com','$2a$10$lOxVIuKEvpraCIC3QRxhjOrX.bHBAr3n2PCOPfqr1e8NzOo9.wCL2',2),(126,'neural@gmail.com','$2a$10$X8a1U71gpN.UDoBaWMBPAOAK9ybfdNlDqSNLkdqEOkf1JgoJ/DwS2',2),(127,'lapi@gmail.com','$2a$10$V1V/t/Q/zjOOXQjW0mkSoe6vuu7mACR1NUAnD5FI5jSWGlxKEEWEa',2),(128,'kerjaku@gmail.com','$2a$10$ajOHPvRsNCKLGwa6IYgf0uqMpWRc6UuysJYbmdF5UeG73DxgjtzS6',2),(129,'kazee@gmail.com','$2a$10$/XfES3otnY3mIYAZSZubRuaLLfq.R/sfSrPzXB4TvcNtyNWcGOzYu',2),(130,'karisma@gmail.com','$2a$10$n88zzAt6vQq.CT4Mpx9QMO7FM.DTrBlLJFm8GfmLRmn.hjv00Pyoe',2),(131,'bejana@gmail.com','$2a$10$ZQSzfDoUx.tGBm11UBZ6PuGo3r6ZLRjma8tG9Acq.ztD/r.emsoGq',2),(132,'digital@gmail.com','$2a$10$vAuBQVHsd4R1KMS5PMLncOohExNuFu0OpxDwRtLz7hfNjqome8r4S',2),(164,'testPerson_1','password',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecturer`
--

DROP TABLE IF EXISTS `lecturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecturer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `prodi_id` int(11) NOT NULL,
  `account_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4ghn2q3b8w3s59t4fjy2r5t38` (`account_id`),
  UNIQUE KEY `UK_cgx7t80t03hcahdbbwkfqbaaq` (`account_id`),
  CONSTRAINT `FK2gt31e48btt9xql0dof7xlagb` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecturer`
--

LOCK TABLES `lecturer` WRITE;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` VALUES (1,'Panitia D3',0,1),(2,'Panitia D4',1,2),(3,'Kaprodi D3',0,3),(4,'Kaprodi D4',1,4),(10,'panitiabaru',1,120);
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db-account'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-16 18:50:02
