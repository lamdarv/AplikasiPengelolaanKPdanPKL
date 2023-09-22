-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: db-company
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
-- Table structure for table `advantages`
--

DROP TABLE IF EXISTS `advantages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advantages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `submission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKet446rehqqlp0uut00g9hqlhl` (`submission_id`),
  CONSTRAINT `FKet446rehqqlp0uut00g9hqlhl` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advantages`
--

LOCK TABLES `advantages` WRITE;
/*!40000 ALTER TABLE `advantages` DISABLE KEYS */;
/*!40000 ALTER TABLE `advantages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) NOT NULL,
  `company_email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `no_phone` varchar(20) DEFAULT NULL,
  `cp_name` varchar(255) NOT NULL,
  `cp_phone` varchar(20) NOT NULL,
  `cp_email` varchar(255) NOT NULL,
  `cp_position` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `num_employee` int(11) DEFAULT NULL,
  `since_year` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  `account_id` int(11) NOT NULL,
  `lecturer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_un` (`company_name`),
  UNIQUE KEY `company_un2` (`company_email`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (30,'PT. PADEPOKAN TUJUH SEMBILAN','padepokan@gmail.com','Bandung','1234','test_padepokan','1234','test_padepokan@gmail.com','CEO','padepokan.net',100,2022,1,123,1),(31,'CV BIMS','bims@gmail.com','Jakarta','1234','test_bims','1234','test_bims@gmail.com','CEO','',NULL,NULL,1,124,2),(32,'PT Bee Solution Partners','beeso@gmail.com','Bandung','1234','test_beeso','1234','test_beeso@gmail.com','CEO','',NULL,NULL,0,125,1),(33,'PT. Neural Technologies Indonesia','neural@gmail.com','Jakarta','1234','test_neural','1234','test_neural@gmail.com','CEO','',NULL,NULL,0,126,2),(34,'PT LAPI Divusi','lapi@gmail.com','Bandung','1234','test_lapi','1234','test_lapi@gmail.com','CEO','',NULL,NULL,0,127,1),(35,'PT Kerjaku Inti Nusantara','kerjaku@gmail.com','Bandung','1234','test_kerjaku','1234','test_kerjaku@gmail.com','CEO','',NULL,NULL,0,128,1),(36,'PT Kazee Digital Indonesia','kazee@gmail.com','Bandung','1234','test_kazee','1234','test_kazee@gmail.com','CEO','',100,NULL,0,129,1),(37,'PT Karisma Technologies','karisma@gmail.com','Bandung','1234','test_karisma','1234','test_karisma@gmail.com','CEO','',NULL,NULL,0,130,2),(38,'PT. Bejana Investidata Globalindo','bejana@gmail.com','Ciladap','1234','test_bejana','1234','test_bejana@gmail.com','CEO','',100,NULL,0,131,1),(39,'Digital Oasis','digital@gmail.com','Bandung','1234','test_digital','1234','test_digital@gmail.com','CEO','',NULL,NULL,0,132,2),(44,'testCompany2','testCompany2@gmail.com','testAddress','08923940243','cptestname','09019301031','testCompany1@gmail.com','direktur','website',120,2020,1,121,1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criteria`
--

DROP TABLE IF EXISTS `criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `criteria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `criteria_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criteria`
--

LOCK TABLES `criteria` WRITE;
/*!40000 ALTER TABLE `criteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `year` int(11) NOT NULL,
  `num_evaluation` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `position` varchar(255) DEFAULT NULL,
  `prodi_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `participant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
INSERT INTO `evaluation` VALUES (48,'bagus bagus',2023,1,1,'BE',0,44,181524012);
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `prodi_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_answer`
--

DROP TABLE IF EXISTS `feedback_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `value` int(11) NOT NULL,
  `feedback_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKod8p7409bpdsa2kb1af6jxv3f` (`feedback_id`),
  CONSTRAINT `FKod8p7409bpdsa2kb1af6jxv3f` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_answer`
--

LOCK TABLES `feedback_answer` WRITE;
/*!40000 ALTER TABLE `feedback_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequisite`
--

DROP TABLE IF EXISTS `prerequisite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prerequisite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `practical_address` varchar(255) DEFAULT NULL,
  `in_advisor_name` varchar(255) DEFAULT NULL,
  `in_advisor_position` varchar(255) DEFAULT NULL,
  `in_advisor_mail` varchar(255) DEFAULT NULL,
  `facility` varchar(255) DEFAULT NULL,
  `total_d3` int(11) DEFAULT 0,
  `total_d4` int(11) DEFAULT 0,
  `work_system` varchar(10) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `region_id` int(11) DEFAULT NULL,
  `project` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4u3nq5fsgvayleghp4bi2wf4g` (`company_id`),
  CONSTRAINT `FK4u3nq5fsgvayleghp4bi2wf4g` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequisite`
--

LOCK TABLES `prerequisite` WRITE;
/*!40000 ALTER TABLE `prerequisite` DISABLE KEYS */;
INSERT INTO `prerequisite` VALUES (30,'bandung','test_padepokan_pembimbing','CEO','test_padepokan_pembimbing@gmail.com','Komputer',5,0,'WFO','<p></p>',2023,1,30,3204,'test_proyek_padepokan**5**d3'),(31,'Bandung','test_bims','CEO','test_bims@gmail.com','komputer',10,0,'WFO','',2023,1,31,3273,'bims_proyek**10**d3'),(32,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,32,0,NULL),(33,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,33,0,NULL),(34,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,34,0,NULL),(35,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,35,0,NULL),(36,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,36,0,NULL),(37,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,37,0,NULL),(38,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,38,0,NULL),(39,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,2023,0,39,0,NULL);
/*!40000 ALTER TABLE `prerequisite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequisite_competence`
--

DROP TABLE IF EXISTS `prerequisite_competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prerequisite_competence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prerequisite_id` int(11) NOT NULL,
  `competence_id` int(11) NOT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dnqukdlpht99eyhgn7c0tb6m` (`prerequisite_id`),
  CONSTRAINT `FK5dnqukdlpht99eyhgn7c0tb6m` FOREIGN KEY (`prerequisite_id`) REFERENCES `prerequisite` (`id`),
  CONSTRAINT `FKrry28dppj0ghv8dn1w4l1safp` FOREIGN KEY (`prerequisite_id`) REFERENCES `prerequisite` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequisite_competence`
--

LOCK TABLES `prerequisite_competence` WRITE;
/*!40000 ALTER TABLE `prerequisite_competence` DISABLE KEYS */;
INSERT INTO `prerequisite_competence` VALUES (218,30,1,0),(219,30,2,0),(220,30,17,0),(221,30,22,0),(222,30,173,0),(223,30,50,0),(224,30,30,0),(225,30,32,0),(226,30,58,0),(227,31,2,0),(228,31,8,0),(229,31,17,0),(230,31,22,0),(231,31,32,0);
/*!40000 ALTER TABLE `prerequisite_competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prerequisite_jobscope`
--

DROP TABLE IF EXISTS `prerequisite_jobscope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prerequisite_jobscope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prerequisite_id` int(11) NOT NULL,
  `id_jobscope` int(11) NOT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5dnqukdlpht99eyhgn7c0tb6m` (`prerequisite_id`) USING BTREE,
  CONSTRAINT `FK5dnqukdlpht99eyhgn7c0tb6m_copy` FOREIGN KEY (`prerequisite_id`) REFERENCES `prerequisite` (`id`),
  CONSTRAINT `FKmltbtjsr6bpvibc2abchbbe4a` FOREIGN KEY (`prerequisite_id`) REFERENCES `prerequisite` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prerequisite_jobscope`
--

LOCK TABLES `prerequisite_jobscope` WRITE;
/*!40000 ALTER TABLE `prerequisite_jobscope` DISABLE KEYS */;
INSERT INTO `prerequisite_jobscope` VALUES (124,30,3,0),(125,31,3,0),(126,31,2,0);
/*!40000 ALTER TABLE `prerequisite_jobscope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `submission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `projects_id_IDX` (`id`) USING BTREE,
  KEY `FKdo57vaovxysxmld5vkw0vr4cf` (`submission_id`),
  CONSTRAINT `FKdo57vaovxysxmld5vkw0vr4cf` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proposer`
--

DROP TABLE IF EXISTS `proposer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proposer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `submission_id` int(11) NOT NULL,
  `proposer_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh3k4rm5ay7qdhk4l5l8lh0a6u` (`company_id`),
  KEY `FKce5qoytrpjkuqhfl5mrxrxu2o` (`submission_id`),
  CONSTRAINT `FKce5qoytrpjkuqhfl5mrxrxu2o` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`),
  CONSTRAINT `FKh3k4rm5ay7qdhk4l5l8lh0a6u` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proposer`
--

LOCK TABLES `proposer` WRITE;
/*!40000 ALTER TABLE `proposer` DISABLE KEYS */;
/*!40000 ALTER TABLE `proposer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submission`
--

DROP TABLE IF EXISTS `submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) NOT NULL,
  `company_mail` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `no_phone` varchar(20) NOT NULL,
  `cp_name` varchar(255) NOT NULL,
  `cp_phone` varchar(20) NOT NULL,
  `cp_mail` varchar(255) NOT NULL,
  `cp_position` varchar(255) NOT NULL,
  `website` varchar(255) DEFAULT NULL,
  `since_year` int(11) DEFAULT NULL,
  `num_employee` int(11) DEFAULT NULL,
  `recept_mechanism` varchar(255) DEFAULT NULL,
  `prodi` varchar(20) NOT NULL,
  `year_kp_pkl` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `submission_un` (`company_name`),
  UNIQUE KEY `submission_un2` (`company_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission`
--

LOCK TABLES `submission` WRITE;
/*!40000 ALTER TABLE `submission` DISABLE KEYS */;
/*!40000 ALTER TABLE `submission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `submission_criteria`
--

DROP TABLE IF EXISTS `submission_criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `submission_criteria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `criteria_id` int(11) NOT NULL,
  `submission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsk1urye5kbs050j72c13cj8m9` (`criteria_id`),
  KEY `FKig2ar0l9lthnb5n7yp3kebjur` (`submission_id`),
  CONSTRAINT `FKig2ar0l9lthnb5n7yp3kebjur` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`),
  CONSTRAINT `FKsk1urye5kbs050j72c13cj8m9` FOREIGN KEY (`criteria_id`) REFERENCES `criteria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `submission_criteria`
--

LOCK TABLES `submission_criteria` WRITE;
/*!40000 ALTER TABLE `submission_criteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `submission_criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valuation`
--

DROP TABLE IF EXISTS `valuation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `valuation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aspect_name` varchar(255) NOT NULL,
  `value` int(11) NOT NULL,
  `evaluation_id` int(11) NOT NULL,
  `is_core` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl2tnyaksxowruarlgf57c2rn3` (`evaluation_id`),
  CONSTRAINT `FKl2tnyaksxowruarlgf57c2rn3` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valuation`
--

LOCK TABLES `valuation` WRITE;
/*!40000 ALTER TABLE `valuation` DISABLE KEYS */;
INSERT INTO `valuation` VALUES (184,'aspek 4',80,48,''),(185,'aspek 2',70,48,''),(186,'aspek 3',50,48,'\0');
/*!40000 ALTER TABLE `valuation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db-company'
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
