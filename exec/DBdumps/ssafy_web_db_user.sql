-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 3.35.171.221    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` int NOT NULL,
  `conference_seq` bigint DEFAULT NULL,
  `emaile` varchar(255) DEFAULT NULL,
  `emails` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKckgxwwjnjt1kph5135gqssbsw` (`conference_seq`),
  CONSTRAINT `FKckgxwwjnjt1kph5135gqssbsw` FOREIGN KEY (`conference_seq`) REFERENCES `conference` (`conference_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','ssafy@ssafy.com','관리자','관리자','$2a$10$IBjugVxyePz2BjM3lWXs6eR6iAmHNj8QNF9ZsiYdLD0oSsQ06UE9e',1055526248,NULL,NULL,NULL),('dilution0216','hglad0216@naver.com','김희석','HUISEOK','$2a$10$2/Xv8V1HI2xPybZuj1BT..f0YpCJRZujMxEnoIzi0dlgDKDfo6IuK',1091689599,NULL,NULL,NULL),('qjawlsqjacks','qjawlsqjacks@naver.com','박범진','BJP','$2a$10$g6g/bp39UAB2A5BI6DrqoeW2jmvXUa7/3mV5euDthbWU3Esbk9nkC',1028732329,NULL,NULL,NULL),('qjini',NULL,'규진','Q진','$2a$10$orITZmuGah6TdCW32nMpqu6faz50Tt5/apE0t7s/yzY2DXUaB9cQ.',1044537878,NULL,NULL,NULL),('test_ms',NULL,'aa','ms','$2a$10$lxkIzQ6jCpQPqEnGSVMh2.67B8eVjcMHKIqqnuJDadc6g80HjUhgK',1044563222,NULL,NULL,NULL),('TestAccount','goodwin','Admin','닉네임이생겼네','$2a$10$1WyZs4pJ2OKSUmRd9dqmC.gTN.6gHDwuQ71TvuOhPgquPg7JShkjO',1028732329,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-19 19:08:13
