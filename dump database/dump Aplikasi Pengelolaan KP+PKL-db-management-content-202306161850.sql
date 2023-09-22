-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: db-management-content
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
-- Table structure for table `assessment_aspect`
--

DROP TABLE IF EXISTS `assessment_aspect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assessment_aspect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aspect_name` varchar(255) NOT NULL,
  `evaluation_form_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpikrsaw8b2oyeve8wow14fggc` (`evaluation_form_id`),
  CONSTRAINT `FKpikrsaw8b2oyeve8wow14fggc` FOREIGN KEY (`evaluation_form_id`) REFERENCES `evaluation_form` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessment_aspect`
--

LOCK TABLES `assessment_aspect` WRITE;
/*!40000 ALTER TABLE `assessment_aspect` DISABLE KEYS */;
INSERT INTO `assessment_aspect` VALUES (1,'Kemampuan teknik',1),(2,'Kemampuan memahami domain pekerjaan',1),(3,'Inisiatif dalam penyelesaian solusi (baik terhadap masalah teknis maupun non-teknis)',1),(4,'Disiplin/punctuality',1),(5,'Kemampuan komunikasi/presentasi',1),(6,'Kemampuan interaksi dengan lingkungan baik dalam kondisi WFO dan WFH (khusus situasi pandem Covid-19)',1),(7,'Kemampuan beradaptasi dengan lingkungan',1),(8,'Kemampuan dokumentasi',1),(9,'Kualifikasi peserta KP dalam menyelesaikan pekerjaan di perusahaan',1),(10,'Kemampuan teknik',2),(11,'Kemampuan memahami domain pekerjaan',2),(12,'Inisiatif dalam penyelesaian solusi (baik terhadap masalah teknis maupun non-teknis)',2),(13,'Disiplin/punctuality',2),(14,'Kemampuan komunikasi/presentasi',2),(15,'Kemampuan interaksi dengan lingkungan baik dalam kondisi WFO dan WFH (khusus situasi pandem Covid-19)',2),(16,'Kemampuan beradaptasi dengan lingkungan',2),(17,'Kemampuan dokumentasi',2),(18,'Kualifikasi peserta KP dalam menyelesaikan pekerjaan di perusahaan',2),(19,'Kemampuan teknik',3),(20,'Kemampuan memahami domain pekerjaan',3),(21,'Inisiatif dalam penyelesaian solusi (baik terhadap masalah teknis maupun non-teknis)',3),(22,'Disiplin/punctuality',3),(23,'Kemampuan komunikasi/presentasi',3),(24,'Kemampuan interaksi dengan lingkungan baik dalam kondisi WFO dan WFH (khusus situasi pandem Covid-19)',3),(25,'Kemampuan beradaptasi dengan lingkungan',3),(26,'Kemampuan dokumentasi',3),(27,'Kualifikasi peserta KP dalam menyelesaikan pekerjaan di perusahaan',3),(28,'Kemampuan teknik',4),(29,'Kemampuan memahami domain pekerjaan',4),(30,'Inisiatif dalam penyelesaian solusi (baik terhadap masalah teknis maupun non-teknis)',4),(31,'Disiplin/punctuality',4),(32,'Kemampuan komunikasi/presentasi',4),(33,'Kemampuan interaksi dengan lingkungan baik dalam kondisi WFO dan WFH (khusus situasi pandem Covid-19)',4),(34,'Kemampuan beradaptasi dengan lingkungan',4),(35,'Kemampuan dokumentasi',4),(36,'Kualifikasi peserta KP dalam menyelesaikan pekerjaan di perusahaan',4),(41,'aspek baru',1);
/*!40000 ALTER TABLE `assessment_aspect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competence`
--

DROP TABLE IF EXISTS `competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pic` varchar(255) NOT NULL,
  `competence_type_id` int(11) NOT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7m4uqtciays658oy2wi0dr801` (`competence_type_id`),
  CONSTRAINT `FK7m4uqtciays658oy2wi0dr801` FOREIGN KEY (`competence_type_id`) REFERENCES `competence_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competence`
--

LOCK TABLES `competence` WRITE;
/*!40000 ALTER TABLE `competence` DISABLE KEYS */;
INSERT INTO `competence` VALUES (1,'PHP','Sistem',1,0),(2,'Java','Sistem',1,0),(3,'Python','Sistem',1,0),(4,'JavaScript','Sistem',1,0),(5,'Kotlin','Sistem',1,0),(6,'Ruby','Sistem',1,0),(7,'HTML','Sistem',1,0),(8,'CSS','Sistem',1,0),(9,'Swift','Sistem',1,0),(10,'Golang','Sistem',1,0),(11,'C','Sistem',1,0),(12,'C++','Sistem',1,0),(13,'C#','Sistem',1,0),(14,'Pascal','Sistem',1,0),(15,'MariaDB','Sistem',2,0),(16,'Oracle','Sistem',2,0),(17,'MySQL','Sistem',2,0),(18,'PostgreSQL','Sistem',2,0),(19,'MongoDB','Sistem',2,0),(20,'Redis','Sistem',2,0),(21,'SQLite','Sistem',2,0),(22,'Laravel','Sistem',3,0),(23,'ReactJS','Sistem',3,0),(24,'Phalcon','Sistem',3,0),(25,'Flutter','Sistem',3,0),(26,'Codeigniter','Sistem',3,0),(27,'Springrails','Sistem',3,0),(28,'VueJS','Sistem',3,0),(29,'AngularJS','Sistem',3,0),(30,'Eclipse','Sistem',4,0),(31,'Android Studio','Sistem',4,0),(32,'Visual Studio Code','Sistem',4,0),(33,'Jasper Report','Sistem',4,0),(34,'Construct','Sistem',4,0),(35,'Linux','Sistem',4,0),(36,'Microsoft Office','Sistem',4,0),(37,'Power Designer','Sistem',4,0),(38,'Git','Sistem',4,0),(39,'Netbeans','Sistem',4,0),(40,'Dev-C++','Sistem',4,0),(41,'Corel Draw','Sistem',4,0),(42,'Adobe Photoshop','Sistem',4,0),(43,'Medibang','Sistem',4,0),(44,'Paint Tool SAI','Sistem',4,0),(45,'Paint 3D','Sistem',4,0),(46,'Sublime Text','Sistem',4,0),(47,'Figma','Sistem',4,0),(48,'Navicat','Sistem',4,0),(49,'Unity Game Engine','Sistem',4,0),(50,'Mockup','Sistem',5,0),(51,'DFD','Sistem',5,0),(52,'ERD','Sistem',5,0),(53,'Use Case','Sistem',5,0),(54,'Class Diagram','Sistem',5,0),(55,'Sequance Diagram','Sistem',5,0),(56,'BPMN','Sistem',5,0),(57,'Flowchart','Sistem',5,0),(58,'Bahasa Indonesia','Sistem',6,0),(59,'Bahasa Inggris','Sistem',6,0),(60,'Bahasa Jepang','Sistem',6,0),(61,'Erlang','Sistem',1,0),(62,'VB.NET','Sistem',1,0),(63,'Perl','Sistem',1,0),(64,'Haskell','Sistem',1,0),(65,'IndexedDB','Sistem',2,0),(66,'HBase','Sistem',2,0),(67,'Flask','Sistem',3,0),(68,'NodeJS','Sistem',3,0),(69,'.NET','Sistem',3,0),(70,'ExpressJS','Sistem',3,0),(71,'Spring','Sistem',3,0),(72,'Django','Sistem',3,0),(73,'Docker','Sistem',4,0),(74,'Kubernetes','Sistem',4,0),(75,'Kanban','Sistem',4,0),(76,'Selenium','Sistem',4,0),(77,'Opium','Sistem',4,0),(78,'Windows','Sistem',4,0),(79,'Bizagi Modeler','Sistem',4,0),(80,'Atom','Sistem',4,0),(81,'Adobe Illustrator','Sistem',4,0),(82,'Adobe XD','Sistem',4,0),(83,'CorelDRAW','Sistem',4,0),(84,'OO Modelling','Sistem',5,0),(85,'Structural/Procedural Modeeling','Sistem',5,0),(86,'Flowchart','Sistem',5,0),(87,'PHP','Sistem',1,1),(88,'Java','Sistem',1,1),(89,'Python','Sistem',1,1),(90,'JavaScript','Sistem',1,1),(91,'Kotlin','Sistem',1,1),(92,'Ruby','Sistem',1,1),(93,'HTML','Sistem',1,1),(94,'CSS','Sistem',1,1),(95,'Swift','Sistem',1,1),(96,'Golang','Sistem',1,1),(97,'C','Sistem',1,1),(98,'C++','Sistem',1,1),(99,'C#','Sistem',1,1),(100,'Pascal','Sistem',1,1),(101,'MariaDB','Sistem',2,1),(102,'Oracle','Sistem',2,1),(103,'MySQL','Sistem',2,1),(104,'PostgreSQL','Sistem',2,1),(105,'MongoDB','Sistem',2,1),(106,'Redis','Sistem',2,1),(107,'SQLite','Sistem',2,1),(108,'Laravel','Sistem',3,1),(109,'ReactJS','Sistem',3,1),(110,'Phalcon','Sistem',3,1),(111,'Flutter','Sistem',3,1),(112,'Codeigniter','Sistem',3,1),(113,'Springrails','Sistem',3,1),(114,'VueJS','Sistem',3,1),(115,'AngularJS','Sistem',3,1),(116,'Eclipse','Sistem',4,1),(117,'Android Studio','Sistem',4,1),(118,'Visual Studio Code','Sistem',4,1),(119,'Jasper Report','Sistem',4,1),(120,'Construct','Sistem',4,1),(121,'Linux','Sistem',4,1),(122,'Microsoft Office','Sistem',4,1),(123,'Power Designer','Sistem',4,1),(124,'Git','Sistem',4,1),(125,'Netbeans','Sistem',4,1),(126,'Dev-C++','Sistem',4,1),(127,'Corel Draw','Sistem',4,1),(128,'Adobe Photoshop','Sistem',4,1),(129,'Medibang','Sistem',4,1),(130,'Paint Tool SAI','Sistem',4,1),(131,'Paint 3D','Sistem',4,1),(132,'Sublime Text','Sistem',4,1),(133,'Figma','Sistem',4,1),(134,'Navicat','Sistem',4,1),(135,'Unity Game Engine','Sistem',4,1),(136,'Mockup','Sistem',5,1),(137,'DFD','Sistem',5,1),(138,'ERD','Sistem',5,1),(139,'Use Case','Sistem',5,1),(140,'Class Diagram','Sistem',5,1),(141,'Sequance Diagram','Sistem',5,1),(142,'BPMN','Sistem',5,1),(143,'Flowchart','Sistem',5,1),(144,'Bahasa Indonesia','Sistem',6,1),(145,'Bahasa Inggris','Sistem',6,1),(146,'Bahasa Jepang','Sistem',6,1),(147,'Erlang','Sistem',1,1),(148,'VB.NET','Sistem',1,1),(149,'Perl','Sistem',1,1),(150,'Haskell','Sistem',1,1),(151,'IndexedDB','Sistem',2,1),(152,'HBase','Sistem',2,1),(153,'Flask','Sistem',3,1),(154,'NodeJS','Sistem',3,1),(155,'.NET','Sistem',3,1),(156,'ExpressJS','Sistem',3,1),(157,'Spring','Sistem',3,1),(158,'Django','Sistem',3,1),(159,'Docker','Sistem',4,1),(160,'Kubernetes','Sistem',4,1),(161,'Kanban','Sistem',4,1),(162,'Selenium','Sistem',4,1),(163,'Opium','Sistem',4,1),(164,'Windows','Sistem',4,1),(165,'Bizagi Modeler','Sistem',4,1),(166,'Atom','Sistem',4,1),(167,'Adobe Illustrator','Sistem',4,1),(168,'Adobe XD','Sistem',4,1),(169,'CorelDRAW','Sistem',4,1),(170,'OO Modelling','Sistem',5,1),(171,'Structural/Procedural Modeeling','Sistem',5,1),(172,'Flowchart','Sistem',5,1),(173,'Springboot','Panitia D3',3,0);
/*!40000 ALTER TABLE `competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competence_type`
--

DROP TABLE IF EXISTS `competence_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competence_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competence_type`
--

LOCK TABLES `competence_type` WRITE;
/*!40000 ALTER TABLE `competence_type` DISABLE KEYS */;
INSERT INTO `competence_type` VALUES (1,'Bahasa Pemrograman',''),(2,'Database',''),(3,'Frameworks',''),(4,'Tools',''),(5,'Modelling Tools',''),(6,'Bahasa Komunikasi','');
/*!40000 ALTER TABLE `competence_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation_form`
--

DROP TABLE IF EXISTS `evaluation_form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prodi_id` int(11) NOT NULL,
  `num_evaluation` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_form`
--

LOCK TABLES `evaluation_form` WRITE;
/*!40000 ALTER TABLE `evaluation_form` DISABLE KEYS */;
INSERT INTO `evaluation_form` VALUES (1,0,1),(2,1,1),(3,1,2),(4,1,3);
/*!40000 ALTER TABLE `evaluation_form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_question`
--

DROP TABLE IF EXISTS `feedback_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `prodi_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_question`
--

LOCK TABLES `feedback_question` WRITE;
/*!40000 ALTER TABLE `feedback_question` DISABLE KEYS */;
INSERT INTO `feedback_question` VALUES (1,'Pertanyaan 1',0),(2,'Pertanyaan 2',0),(3,'Pertanyaan 3',0),(4,'Pertanyaan 4',0),(5,'Pertanyaan 5 new',0),(7,'Pertanyaan PKL 1',1),(8,'Pertanyaan PKL 2',1),(9,'Pertanyaan PKL 3',1),(10,'Pertanyaan PKL 4',1),(11,'Pertanyaan PKL 5',1);
/*!40000 ALTER TABLE `feedback_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_setting`
--

DROP TABLE IF EXISTS `form_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `prodi_id` int(11) NOT NULL,
  `timeline_setting_id` int(11) DEFAULT NULL,
  `day_before` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pk9bn8dmxhutrichx4ciucyd5` (`timeline_setting_id`),
  CONSTRAINT `FKo3n3b62arurdqibteoa2wwnqu` FOREIGN KEY (`timeline_setting_id`) REFERENCES `timeline_setting` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_setting`
--

LOCK TABLES `form_setting` WRITE;
/*!40000 ALTER TABLE `form_setting` DISABLE KEYS */;
INSERT INTO `form_setting` VALUES (1,'Pengajuan perusahaan',2,3,1),(2,'CV',2,2,2),(3,'Pre-requisite',2,4,4),(4,'Pemilihan prioritas perusahaan',2,6,3),(5,'Evaluasi peserta oleh perusahaan',0,14,5),(6,'Evaluasi peserta oleh perusahaan 1',1,15,7),(7,'Evaluasi peserta oleh perusahaan 2',1,16,8),(8,'Evaluasi peserta oleh perusahaan 3',1,17,3),(9,'Feedback Perusahaan KP',0,22,4),(10,'Feedback Perusahaan PKL',1,23,5),(11,'Pelaksanaan KP',0,12,7),(12,'Pelaksanaan PKL',1,13,7);
/*!40000 ALTER TABLE `form_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobscope`
--

DROP TABLE IF EXISTS `jobscope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobscope` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `pic` varchar(255) NOT NULL,
  `prodi_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobscope`
--

LOCK TABLES `jobscope` WRITE;
/*!40000 ALTER TABLE `jobscope` DISABLE KEYS */;
INSERT INTO `jobscope` VALUES (1,'Requirement Analysis','Sistem',0),(2,'Design','Sistem',0),(3,'Coding','Sistem',0),(4,'Testing','Sistem',0),(5,'Data Engineering','Sistem',1),(6,'Maintenance','Sistem',0),(7,'Software Engineering Model and Method','Sistem',1),(8,'Software Engineering Management','Sistem',1),(9,'Deployment/Integraition','Sistem',0),(10,'Documentation (Technical Writer)','Sistem',0),(11,'Database Management','Sistem',0),(12,'Software Requirement','Sistem',1),(13,'Software Design','Sistem',1),(14,'Software Construction (Coding)','Sistem',1),(15,'Software Testing','Sistem',1),(16,'Software Quality','Sistem',1),(17,'Software Maintenance','Sistem',1),(18,'Software Configuration Management (Deployment)','Sistem',1),(19,'Documentation (Technical Writer)','Sistem',1),(20,'Database Management','Sistem',1),(21,'Data Sciences','Sistem',1);
/*!40000 ALTER TABLE `jobscope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9474 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES (1101,'Kab. Simeulue'),(1102,'Kab. Aceh Singkil'),(1103,'Kab. Aceh Selatan'),(1104,'Kab. Aceh Tenggara'),(1105,'Kab. Aceh Timur'),(1106,'Kab. Aceh Tengah'),(1107,'Kab. Aceh Barat'),(1108,'Kab. Aceh Besar'),(1109,'Kab. Pidie'),(1110,'Kab. Bireuen'),(1111,'Kab. Aceh Utara'),(1112,'Kab. Aceh Barat Daya'),(1113,'Kab. Gayo Lues'),(1114,'Kab. Aceh Tamiang'),(1115,'Kab. Nagan Raya'),(1116,'Kab. Aceh Jaya'),(1117,'Kab. Bener Meriah'),(1118,'Kab. Pidie Jaya'),(1171,'Kota Banda Aceh'),(1172,'Kota Sabang'),(1173,'Kota Langsa'),(1174,'Kota Lhokseumawe'),(1175,'Kota Subulussalam'),(1201,'Kab. Nias'),(1202,'Kab. Mandailing Natal'),(1203,'Kab. Tapanuli Selatan'),(1204,'Kab. Tapanuli Tengah'),(1205,'Kab. Tapanuli Utara'),(1206,'Kab. Toba Samosir'),(1207,'Kab. Labuhan Batu'),(1208,'Kab. Asahan'),(1209,'Kab. Simalungun'),(1210,'Kab. Dairi'),(1211,'Kab. Karo'),(1212,'Kab. Deli Serdang'),(1213,'Kab. Langkat'),(1214,'Kab. Nias Selatan'),(1215,'Kab. Humbang Hasundutan'),(1216,'Kab. Pakpak Bharat'),(1217,'Kab. Samosir'),(1218,'Kab. Serdang Bedagai'),(1219,'Kab. Batu Bara'),(1220,'Kab. Padang Lawas Utara'),(1221,'Kab. Padang Lawas'),(1222,'Kab. Labuhan Batu Selatan'),(1223,'Kab. Labuhan Batu Utara'),(1224,'Kab. Nias Utara'),(1225,'Kab. Nias Barat'),(1271,'Kota Sibolga'),(1272,'Kota Tanjung Balai'),(1273,'Kota Pematang Siantar'),(1274,'Kota Tebing Tinggi'),(1275,'Kota Medan'),(1276,'Kota Binjai'),(1277,'Kota Padangsidimpuan'),(1278,'Kota Gunungsitoli'),(1301,'Kab. Kepulauan Mentawai'),(1302,'Kab. Pesisir Selatan'),(1303,'Kab. Solok'),(1304,'Kab. Sijunjung'),(1305,'Kab. Tanah Datar'),(1306,'Kab. Padang Pariaman'),(1307,'Kab. Agam'),(1308,'Kab. Lima Puluh Kota'),(1309,'Kab. Pasaman'),(1310,'Kab. Solok Selatan'),(1311,'Kab. Dharmasraya'),(1312,'Kab. Pasaman Barat'),(1371,'Kota Padang'),(1372,'Kota Solok'),(1373,'Kota Sawah Lunto'),(1374,'Kota Padang Panjang'),(1375,'Kota Bukittinggi'),(1376,'Kota Payakumbuh'),(1377,'Kota Pariaman'),(1401,'Kab. Kuantan Singingi'),(1402,'Kab. Indragiri Hulu'),(1403,'Kab. Indragiri Hilir'),(1404,'Kab. Pelalawan'),(1405,'Kab. S I A K'),(1406,'Kab. Kampar'),(1407,'Kab. Rokan Hulu'),(1408,'Kab. Bengkalis'),(1409,'Kab. Rokan Hilir'),(1410,'Kab. Kepulauan Meranti'),(1471,'Kota Pekanbaru'),(1473,'Kota D U M A I'),(1501,'Kab. Kerinci'),(1502,'Kab. Merangin'),(1503,'Kab. Sarolangun'),(1504,'Kab. Batang Hari'),(1505,'Kab. Muaro Jambi'),(1506,'Kab. Tanjung Jabung Timur'),(1507,'Kab. Tanjung Jabung Barat'),(1508,'Kab. Tebo'),(1509,'Kab. Bungo'),(1571,'Kota Jambi'),(1572,'Kota Sungai Penuh'),(1601,'Kab. Ogan Komering Ulu'),(1602,'Kab. Ogan Komering Ilir'),(1603,'Kab. Muara Enim'),(1604,'Kab. Lahat'),(1605,'Kab. Musi Rawas'),(1606,'Kab. Musi Banyuasin'),(1607,'Kab. Banyu Asin'),(1608,'Kab. Ogan Komering Ulu Selatan'),(1609,'Kab. Ogan Komering Ulu Timur'),(1610,'Kab. Ogan Ilir'),(1611,'Kab. Empat Lawang'),(1671,'Kota Palembang'),(1672,'Kota Prabumulih'),(1673,'Kota Pagar Alam'),(1674,'Kota Lubuklinggau'),(1701,'Kab. Bengkulu Selatan'),(1702,'Kab. Rejang Lebong'),(1703,'Kab. Bengkulu Utara'),(1704,'Kab. Kaur'),(1705,'Kab. Seluma'),(1706,'Kab. Mukomuko'),(1707,'Kab. Lebong'),(1708,'Kab. Kepahiang'),(1709,'Kab. Bengkulu Tengah'),(1771,'Kota Bengkulu'),(1801,'Kab. Lampung Barat'),(1802,'Kab. Tanggamus'),(1803,'Kab. Lampung Selatan'),(1804,'Kab. Lampung Timur'),(1805,'Kab. Lampung Tengah'),(1806,'Kab. Lampung Utara'),(1807,'Kab. Way Kanan'),(1808,'Kab. Tulangbawang'),(1809,'Kab. Pesawaran'),(1810,'Kab. Pringsewu'),(1811,'Kab. Mesuji'),(1812,'Kab. Tulang Bawang Barat'),(1813,'Kab. Pesisir Barat'),(1871,'Kota Bandar Lampung'),(1872,'Kota Metro'),(1901,'Kab. Bangka'),(1902,'Kab. Belitung'),(1903,'Kab. Bangka Barat'),(1904,'Kab. Bangka Tengah'),(1905,'Kab. Bangka Selatan'),(1906,'Kab. Belitung Timur'),(1971,'Kota Pangkal Pinang'),(2101,'Kab. Karimun'),(2102,'Kab. Bintan'),(2103,'Kab. Natuna'),(2104,'Kab. Lingga'),(2105,'Kab. Kepulauan Anambas'),(2171,'Kota B A T A M'),(2172,'Kota Tanjung Pinang'),(3101,'Kab. Kepulauan Seribu'),(3171,'Kota Jakarta Selatan'),(3172,'Kota Jakarta Timur'),(3173,'Kota Jakarta Pusat'),(3174,'Kota Jakarta Barat'),(3175,'Kota Jakarta Utara'),(3201,'Kab. Bogor'),(3202,'Kab. Sukabumi'),(3203,'Kab. Cianjur'),(3204,'Kab. Bandung'),(3205,'Kab. Garut'),(3206,'Kab. Tasikmalaya'),(3207,'Kab. Ciamis'),(3208,'Kab. Kuningan'),(3209,'Kab. Cirebon'),(3210,'Kab. Majalengka'),(3211,'Kab. Sumedang'),(3212,'Kab. Indramayu'),(3213,'Kab. Subang'),(3214,'Kab. Purwakarta'),(3215,'Kab. Karawang'),(3216,'Kab. Bekasi'),(3217,'Kab. Bandung Barat'),(3218,'Kab. Pangandaran'),(3271,'Kota Bogor'),(3272,'Kota Sukabumi'),(3273,'Kota Bandung'),(3274,'Kota Cirebon'),(3275,'Kota Bekasi'),(3276,'Kota Depok'),(3277,'Kota Cimahi'),(3278,'Kota Tasikmalaya'),(3279,'Kota Banjar'),(3301,'Kab. Cilacap'),(3302,'Kab. Banyumas'),(3303,'Kab. Purbalingga'),(3304,'Kab. Banjarnegara'),(3305,'Kab. Kebumen'),(3306,'Kab. Purworejo'),(3307,'Kab. Wonosobo'),(3308,'Kab. Magelang'),(3309,'Kab. Boyolali'),(3310,'Kab. Klaten'),(3311,'Kab. Sukoharjo'),(3312,'Kab. Wonogiri'),(3313,'Kab. Karanganyar'),(3314,'Kab. Sragen'),(3315,'Kab. Grobogan'),(3316,'Kab. Blora'),(3317,'Kab. Rembang'),(3318,'Kab. Pati'),(3319,'Kab. Kudus'),(3320,'Kab. Jepara'),(3321,'Kab. Demak'),(3322,'Kab. Semarang'),(3323,'Kab. Temanggung'),(3324,'Kab. Kendal'),(3325,'Kab. Batang'),(3326,'Kab. Pekalongan'),(3327,'Kab. Pemalang'),(3328,'Kab. Tegal'),(3329,'Kab. Brebes'),(3371,'Kota Magelang'),(3372,'Kota Surakarta'),(3373,'Kota Salatiga'),(3374,'Kota Semarang'),(3375,'Kota Pekalongan'),(3376,'Kota Tegal'),(3401,'Kab. Kulon Progo'),(3402,'Kab. Bantul'),(3403,'Kab. Gunung Kidul'),(3404,'Kab. Sleman'),(3471,'Kota Yogyakarta'),(3501,'Kab. Pacitan'),(3502,'Kab. Ponorogo'),(3503,'Kab. Trenggalek'),(3504,'Kab. Tulungagung'),(3505,'Kab. Blitar'),(3506,'Kab. Kediri'),(3507,'Kab. Malang'),(3508,'Kab. Lumajang'),(3509,'Kab. Jember'),(3510,'Kab. Banyuwangi'),(3511,'Kab. Bondowoso'),(3512,'Kab. Situbondo'),(3513,'Kab. Probolinggo'),(3514,'Kab. Pasuruan'),(3515,'Kab. Sidoarjo'),(3516,'Kab. Mojokerto'),(3517,'Kab. Jombang'),(3518,'Kab. Nganjuk'),(3519,'Kab. Madiun'),(3520,'Kab. Magetan'),(3521,'Kab. Ngawi'),(3522,'Kab. Bojonegoro'),(3523,'Kab. Tuban'),(3524,'Kab. Lamongan'),(3525,'Kab. Gresik'),(3526,'Kab. Bangkalan'),(3527,'Kab. Sampang'),(3528,'Kab. Pamekasan'),(3529,'Kab. Sumenep'),(3571,'Kota Kediri'),(3572,'Kota Blitar'),(3573,'Kota Malang'),(3574,'Kota Probolinggo'),(3575,'Kota Pasuruan'),(3576,'Kota Mojokerto'),(3577,'Kota Madiun'),(3578,'Kota Surabaya'),(3579,'Kota Batu'),(3601,'Kab. Pandeglang'),(3602,'Kab. Lebak'),(3603,'Kab. Tangerang'),(3604,'Kab. Serang'),(3671,'Kota Tangerang'),(3672,'Kota Cilegon'),(3673,'Kota Serang'),(3674,'Kota Tangerang Selatan'),(5101,'Kab. Jembrana'),(5102,'Kab. Tabanan'),(5103,'Kab. Badung'),(5104,'Kab. Gianyar'),(5105,'Kab. Klungkung'),(5106,'Kab. Bangli'),(5107,'Kab. Karang Asem'),(5108,'Kab. Buleleng'),(5171,'Kota Denpasar'),(5201,'Kab. Lombok Barat'),(5202,'Kab. Lombok Tengah'),(5203,'Kab. Lombok Timur'),(5204,'Kab. Sumbawa'),(5205,'Kab. Dompu'),(5206,'Kab. Bima'),(5207,'Kab. Sumbawa Barat'),(5208,'Kab. Lombok Utara'),(5271,'Kota Mataram'),(5272,'Kota Bima'),(5301,'Kab. Sumba Barat'),(5302,'Kab. Sumba Timur'),(5303,'Kab. Kupang'),(5304,'Kab. Timor Tengah Selatan'),(5305,'Kab. Timor Tengah Utara'),(5306,'Kab. Belu'),(5307,'Kab. Alor'),(5308,'Kab. Lembata'),(5309,'Kab. Flores Timur'),(5310,'Kab. Sikka'),(5311,'Kab. Ende'),(5312,'Kab. Ngada'),(5313,'Kab. Manggarai'),(5314,'Kab. Rote Ndao'),(5315,'Kab. Manggarai Barat'),(5316,'Kab. Sumba Tengah'),(5317,'Kab. Sumba Barat Daya'),(5318,'Kab. Nagekeo'),(5319,'Kab. Manggarai Timur'),(5320,'Kab. Sabu Raijua'),(5371,'Kota Kupang'),(6101,'Kab. Sambas'),(6102,'Kab. Bengkayang'),(6103,'Kab. Landak'),(6104,'Kab. Pontianak'),(6105,'Kab. Sanggau'),(6106,'Kab. Ketapang'),(6107,'Kab. Sintang'),(6108,'Kab. Kapuas Hulu'),(6109,'Kab. Sekadau'),(6110,'Kab. Melawi'),(6111,'Kab. Kayong Utara'),(6112,'Kab. Kubu Raya'),(6171,'Kota Pontianak'),(6172,'Kota Singkawang'),(6201,'Kab. Kotawaringin Barat'),(6202,'Kab. Kotawaringin Timur'),(6203,'Kab. Kapuas'),(6204,'Kab. Barito Selatan'),(6205,'Kab. Barito Utara'),(6206,'Kab. Sukamara'),(6207,'Kab. Lamandau'),(6208,'Kab. Seruyan'),(6209,'Kab. Katingan'),(6210,'Kab. Pulang Pisau'),(6211,'Kab. Gunung Mas'),(6212,'Kab. Barito Timur'),(6213,'Kab. Murung Raya'),(6271,'Kota Palangka Raya'),(6301,'Kab. Tanah Laut'),(6302,'Kab. Kota Baru'),(6303,'Kab. Banjar'),(6304,'Kab. Barito Kuala'),(6305,'Kab. Tapin'),(6306,'Kab. Hulu Sungai Selatan'),(6307,'Kab. Hulu Sungai Tengah'),(6308,'Kab. Hulu Sungai Utara'),(6309,'Kab. Tabalong'),(6310,'Kab. Tanah Bumbu'),(6311,'Kab. Balangan'),(6371,'Kota Banjarmasin'),(6372,'Kota Banjar Baru'),(6401,'Kab. Paser'),(6402,'Kab. Kutai Barat'),(6403,'Kab. Kutai Kartanegara'),(6404,'Kab. Kutai Timur'),(6405,'Kab. Berau'),(6409,'Kab. Penajam Paser Utara'),(6471,'Kota Balikpapan'),(6472,'Kota Samarinda'),(6474,'Kota Bontang'),(6501,'Kab. Malinau'),(6502,'Kab. Bulungan'),(6503,'Kab. Tana Tidung'),(6504,'Kab. Nunukan'),(6571,'Kota Tarakan'),(7101,'Kab. Bolaang Mongondow'),(7102,'Kab. Minahasa'),(7103,'Kab. Kepulauan Sangihe'),(7104,'Kab. Kepulauan Talaud'),(7105,'Kab. Minahasa Selatan'),(7106,'Kab. Minahasa Utara'),(7107,'Kab. Bolaang Mongondow Utara'),(7108,'Kab. Siau Tagulandang Biaro'),(7109,'Kab. Minahasa Tenggara'),(7110,'Kab. Bolaang Mongondow Selatan'),(7111,'Kab. Bolaang Mongondow Timur'),(7171,'Kota Manado'),(7172,'Kota Bitung'),(7173,'Kota Tomohon'),(7174,'Kota Kotamobagu'),(7201,'Kab. Banggai Kepulauan'),(7202,'Kab. Banggai'),(7203,'Kab. Morowali'),(7204,'Kab. Poso'),(7205,'Kab. Donggala'),(7206,'Kab. Toli-toli'),(7207,'Kab. Buol'),(7208,'Kab. Parigi Moutong'),(7209,'Kab. Tojo Una-una'),(7210,'Kab. Sigi'),(7271,'Kota Palu'),(7301,'Kab. Kepulauan Selayar'),(7302,'Kab. Bulukumba'),(7303,'Kab. Bantaeng'),(7304,'Kab. Jeneponto'),(7305,'Kab. Takalar'),(7306,'Kab. Gowa'),(7307,'Kab. Sinjai'),(7308,'Kab. Maros'),(7309,'Kab. Pangkajene Dan Kepulauan'),(7310,'Kab. Barru'),(7311,'Kab. Bone'),(7312,'Kab. Soppeng'),(7313,'Kab. Wajo'),(7314,'Kab. Sidenreng Rappang'),(7315,'Kab. Pinrang'),(7316,'Kab. Enrekang'),(7317,'Kab. Luwu'),(7318,'Kab. Tana Toraja'),(7322,'Kab. Luwu Utara'),(7325,'Kab. Luwu Timur'),(7326,'Kab. Toraja Utara'),(7371,'Kota Makassar'),(7372,'Kota Parepare'),(7373,'Kota Palopo'),(7401,'Kab. Buton'),(7402,'Kab. Muna'),(7403,'Kab. Konawe'),(7404,'Kab. Kolaka'),(7405,'Kab. Konawe Selatan'),(7406,'Kab. Bombana'),(7407,'Kab. Wakatobi'),(7408,'Kab. Kolaka Utara'),(7409,'Kab. Buton Utara'),(7410,'Kab. Konawe Utara'),(7471,'Kota Kendari'),(7472,'Kota Baubau'),(7501,'Kab. Boalemo'),(7502,'Kab. Gorontalo'),(7503,'Kab. Pohuwato'),(7504,'Kab. Bone Bolango'),(7505,'Kab. Gorontalo Utara'),(7571,'Kota Gorontalo'),(7601,'Kab. Majene'),(7602,'Kab. Polewali Mandar'),(7603,'Kab. Mamasa'),(7604,'Kab. Mamuju'),(7605,'Kab. Mamuju Utara'),(8101,'Kab. Maluku Tenggara Barat'),(8102,'Kab. Maluku Tenggara'),(8103,'Kab. Maluku Tengah'),(8104,'Kab. Buru'),(8105,'Kab. Kepulauan Aru'),(8106,'Kab. Seram Bagian Barat'),(8107,'Kab. Seram Bagian Timur'),(8108,'Kab. Maluku Barat Daya'),(8109,'Kab. Buru Selatan'),(8171,'Kota Ambon'),(8172,'Kota Tual'),(8201,'Kab. Halmahera Barat'),(8202,'Kab. Halmahera Tengah'),(8203,'Kab. Kepulauan Sula'),(8204,'Kab. Halmahera Selatan'),(8205,'Kab. Halmahera Utara'),(8206,'Kab. Halmahera Timur'),(8207,'Kab. Pulau Morotai'),(8271,'Kota Ternate'),(8272,'Kota Tidore Kepulauan'),(9101,'Kab. Fakfak'),(9102,'Kab. Kaimana'),(9103,'Kab. Teluk Wondama'),(9104,'Kab. Teluk Bintuni'),(9105,'Kab. Manokwari'),(9106,'Kab. Sorong Selatan'),(9107,'Kab. Sorong'),(9108,'Kab. Raja Ampat'),(9109,'Kab. Tambrauw'),(9110,'Kab. Maybrat'),(9171,'Kota Sorong'),(9401,'Kab. Merauke'),(9402,'Kab. Jayawijaya'),(9403,'Kab. Jayapura'),(9404,'Kab. Nabire'),(9408,'Kab. Kepulauan Yapen'),(9409,'Kab. Biak Numfor'),(9410,'Kab. Paniai'),(9411,'Kab. Puncak Jaya'),(9412,'Kab. Mimika'),(9413,'Kab. Boven Digoel'),(9414,'Kab. Mappi'),(9415,'Kab. Asmat'),(9416,'Kab. Yahukimo'),(9417,'Kab. Pegunungan Bintang'),(9418,'Kab. Tolikara'),(9419,'Kab. Sarmi'),(9420,'Kab. Keerom'),(9426,'Kab. Waropen'),(9427,'Kab. Supiori'),(9428,'Kab. Mamberamo Raya'),(9429,'Kab. Nduga'),(9430,'Kab. Lanny Jaya'),(9431,'Kab. Mamberamo Tengah'),(9432,'Kab. Yalimo'),(9433,'Kab. Puncak'),(9434,'Kab. Dogiyai'),(9435,'Kab. Intan Jaya'),(9436,'Kab. Deiyai'),(9471,'Kota Jayapura'),(9473,'Lain-lain');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timeline_setting`
--

DROP TABLE IF EXISTS `timeline_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timeline_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `prodi_id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timeline_setting`
--

LOCK TABLES `timeline_setting` WRITE;
/*!40000 ALTER TABLE `timeline_setting` DISABLE KEYS */;
INSERT INTO `timeline_setting` VALUES (1,'Sosialisasi 1','2021-04-01','2021-04-07',0,'Penyampaian mekanisme + prosedur KP/PKL'),(2,'Pengumpulan CV','2021-04-08','2023-05-03',0,'Pengumpulan CV'),(3,'Pencarian perusahaan','2021-04-01','2023-07-07',0,'Pencarian perusahaan'),(4,'Pengumpulan prerequisite','2021-04-21','2023-06-14',0,'Pengiriman surat penjagaan'),(5,'Waiting response','2021-05-01','2021-06-21',0,'Merekap Perusahaan penerima KP/PKL'),(6,'Pemetaan mahasiswa','2021-06-22','2023-07-07',2,'Pemetaan mahasiswa'),(7,'Penanganan pembatalan perusahaan penerima KP/PKL','2021-07-28','2021-07-07',0,'Penanganan pembatalan perusahaan penerima KP/PKL'),(8,'Pemilihan dosen pembimbing','2021-07-01','2021-07-07',0,'Pemilihan dosen pembimbing'),(9,'Sosialisasi 2','2021-07-07','2021-07-10',0,'Menyampaikan hasik mapping'),(10,'Persiapan acara pembekalan','2021-07-07','2021-07-14',0,'Persiapan acara pembekalan'),(11,'Pembekalan dan pelepasan KP/PKL','2021-07-15','2021-07-21',0,'Pembekalan dan pelepasan KP/PKL'),(12,'Pelaksanaan KP','2021-08-01','2021-10-07',0,'Pelaksanaan KP'),(13,'Pelaksanaan PKL','2021-08-01','2021-11-29',1,'Pelaksanaan PKL'),(14,'Evaluasi KP','2021-10-08','2023-10-14',0,'Evaluasi KP'),(15,'Evaluasi PKL 1','2021-09-02','2021-10-05',1,'Evaluasi PKL 1'),(16,'Evaluasi PKL 2','2021-10-02','2021-10-10',1,'Evaluasi PKL 2'),(17,'Evaluasi PKL 3','2021-10-10','2023-12-01',1,'Evaluasi PKL 3'),(18,'Sosialisasi 3 KP','2021-10-08','2021-10-14',0,'Sosialisasi 3 KP'),(19,'Sosialisasi 3 PKL','2021-12-01','2021-12-07',1,'Sosialisasi 3 PKL'),(20,'Seminar KP','2021-10-15','2021-11-07',0,'Seminar KP'),(21,'Seminar PKL','2021-12-08','2022-01-07',1,'Seminar PKL'),(22,'Pengumpulan Laporan KP','2021-10-22','2023-11-28',0,'Pengumpulan Laporan KP'),(23,'Pengumpulan Laporan PKL','2021-12-18','2023-01-21',1,'Pengumpulan Laporan PKL'),(24,'Sosialisasi 1','2021-04-01','2021-04-07',1,'Penyampaian mekanisme + prosedur KP/PKL'),(25,'Waiting response','2021-05-01','2021-06-21',1,'Merekap Perusahaan penerima KP/PKL'),(26,'Penanganan pembatalan perusahaan penerima KP/PKL','2021-07-28','2021-07-07',1,'Penanganan pembatalan perusahaan penerima KP/PKL'),(27,'Pemilihan dosen pembimbing','2021-07-01','2021-07-07',1,'Pemilihan dosen pembimbing'),(28,'Sosialisasi 2','2021-07-07','2021-07-10',1,'Menyampaikan hasik mapping'),(29,'Persiapan acara pembekalan','2021-07-07','2021-07-14',1,'Persiapan acara pembekalan'),(30,'Pembekalan dan pelepasan KP/PKL','2021-07-15','2021-07-21',1,'Pembekalan dan pelepasan KP/PKL'),(36,'varu','2021-06-22','2022-07-07',1,'vary');
/*!40000 ALTER TABLE `timeline_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db-management-content'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-16 18:50:03
