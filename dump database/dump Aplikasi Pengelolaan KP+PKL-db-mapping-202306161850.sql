-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: db-mapping
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
-- Table structure for table `criteria_mapping`
--

DROP TABLE IF EXISTS `criteria_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criteria_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `percentage_value` float NOT NULL,
  `is_cost` tinyint(1) NOT NULL,
  `attribute` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criteria_mapping`
--

LOCK TABLES `criteria_mapping` WRITE;
/*!40000 ALTER TABLE `criteria_mapping` DISABLE KEYS */;
INSERT INTO `criteria_mapping` VALUES (1,'Minat Pekerjaan',10,0,NULL),(2,'Bahasa Pemrograman',10,0,NULL),(3,'Database',10,0,NULL),(4,'Framework',10,0,NULL),(5,'Tools',10,0,NULL),(6,'Modelling Tools',10,0,NULL),(7,'Bahasa yang Dikuasai',10,0,NULL),(8,'Domisili',5,0,NULL),(9,'Minat Peserta Terhadap Perusahaan',25,1,NULL);
/*!40000 ALTER TABLE `criteria_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `final_mapping`
--

DROP TABLE IF EXISTS `final_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `prodi_id` int(11) NOT NULL,
  `participant_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_mapping`
--

LOCK TABLES `final_mapping` WRITE;
/*!40000 ALTER TABLE `final_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `final_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participant_ranking`
--

DROP TABLE IF EXISTS `participant_ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participant_ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  `year` int(11) DEFAULT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  `participant_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participant_ranking`
--

LOCK TABLES `participant_ranking` WRITE;
/*!40000 ALTER TABLE `participant_ranking` DISABLE KEYS */;
/*!40000 ALTER TABLE `participant_ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utility`
--

DROP TABLE IF EXISTS `utility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utility` (
  `is_final` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `prodi_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utility`
--

LOCK TABLES `utility` WRITE;
/*!40000 ALTER TABLE `utility` DISABLE KEYS */;
INSERT INTO `utility` VALUES (0,1,0),(0,2,1),(0,3,0),(0,4,1);
/*!40000 ALTER TABLE `utility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utility_date`
--

DROP TABLE IF EXISTS `utility_date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utility_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_utility` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utility_date`
--

LOCK TABLES `utility_date` WRITE;
/*!40000 ALTER TABLE `utility_date` DISABLE KEYS */;
INSERT INTO `utility_date` VALUES (11,1,'2022-06-30 20:59:18',1,2022),(12,2,'2022-06-27 19:27:44',1,2022),(13,3,'2022-06-27 19:27:46',1,2022),(14,1,'2022-07-03 07:32:58',0,2022),(15,2,'2022-06-28 08:16:21',0,2022),(16,3,'2022-06-27 16:15:08',0,2022),(17,1,'2023-03-14 13:06:00',0,2023),(18,2,'2023-02-28 12:21:37',1,2023);
/*!40000 ALTER TABLE `utility_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db-mapping'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-16 18:50:04
