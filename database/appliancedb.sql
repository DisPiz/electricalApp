-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: appliancedb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appliance`
--

DROP TABLE IF EXISTS `appliance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appliance` (
  `idappliance` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `power` int NOT NULL,
  `apartment` int NOT NULL,
  `plug` tinyint(1) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`idappliance`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appliance`
--

LOCK TABLES `appliance` WRITE;
/*!40000 ALTER TABLE `appliance` DISABLE KEYS */;
INSERT INTO `appliance` VALUES (77,'ПК','Asus',600,1,1,'default'),(78,'Холодильник','LG',350,1,1,'litreage'),(79,'Ноутбук','Lenovo',220,2,0,'battery');
/*!40000 ALTER TABLE `appliance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battery_app`
--

DROP TABLE IF EXISTS `battery_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battery_app` (
  `idbattery` int NOT NULL,
  `battery_status` tinyint(1) NOT NULL,
  PRIMARY KEY (`idbattery`),
  KEY `index2` (`idbattery`),
  CONSTRAINT `batterykey` FOREIGN KEY (`idbattery`) REFERENCES `appliance` (`idappliance`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battery_app`
--

LOCK TABLES `battery_app` WRITE;
/*!40000 ALTER TABLE `battery_app` DISABLE KEYS */;
INSERT INTO `battery_app` VALUES (79,0);
/*!40000 ALTER TABLE `battery_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `litreage_app`
--

DROP TABLE IF EXISTS `litreage_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `litreage_app` (
  `idlitreage` int NOT NULL,
  `litreage` int NOT NULL,
  PRIMARY KEY (`idlitreage`),
  KEY `index2` (`idlitreage`),
  CONSTRAINT `litreagekey` FOREIGN KEY (`idlitreage`) REFERENCES `appliance` (`idappliance`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `litreage_app`
--

LOCK TABLES `litreage_app` WRITE;
/*!40000 ALTER TABLE `litreage_app` DISABLE KEYS */;
INSERT INTO `litreage_app` VALUES (78,300);
/*!40000 ALTER TABLE `litreage_app` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-22 18:21:50
