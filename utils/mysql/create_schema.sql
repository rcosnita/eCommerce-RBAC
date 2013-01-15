-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ecommerce_rbac
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.04.3

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
-- Current Database: `ecommerce_rbac`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ecommerce_rbac` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ecommerce_rbac`;

--
-- Table structure for table `AssignedPermissions`
--

DROP TABLE IF EXISTS `AssignedPermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssignedPermissions` (
  `permission_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `fk_assignedpermissions_permission` (`permission_id`),
  KEY `fk_assignedpermissions_role` (`role_id`),
  CONSTRAINT `fk_assignedpermissions_permission` FOREIGN KEY (`permission_id`) REFERENCES `Permissions` (`id`),
  CONSTRAINT `fk_assignedpermissions_role` FOREIGN KEY (`role_id`) REFERENCES `Roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssignedPermissions`
--

LOCK TABLES `AssignedPermissions` WRITE;
/*!40000 ALTER TABLE `AssignedPermissions` DISABLE KEYS */;
INSERT INTO `AssignedPermissions` VALUES (10,2),(10,3),(11,2),(11,3),(12,2),(12,3),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(21,2),(22,2),(23,2),(24,2),(25,2),(26,2),(27,2),(28,2),(28,5),(29,2),(29,5),(30,2),(30,5),(31,2),(33,2),(34,2),(35,2),(36,2),(37,2),(38,2),(39,2),(44,2),(45,2),(46,2);
/*!40000 ALTER TABLE `AssignedPermissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AssignedUsers`
--

DROP TABLE IF EXISTS `AssignedUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AssignedUsers` (
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_assignedusers_user` (`user_id`),
  KEY `fk_assignedusers_role` (`role_id`),
  CONSTRAINT `fk_assignedusers_role` FOREIGN KEY (`role_id`) REFERENCES `Roles` (`id`),
  CONSTRAINT `fk_assignedusers_user` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AssignedUsers`
--

LOCK TABLES `AssignedUsers` WRITE;
/*!40000 ALTER TABLE `AssignedUsers` DISABLE KEYS */;
INSERT INTO `AssignedUsers` VALUES (21,1),(21,2),(21,3),(22,2),(22,3),(22,5);
/*!40000 ALTER TABLE `AssignedUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DynamicSeparationDutySet`
--

DROP TABLE IF EXISTS `DynamicSeparationDutySet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DynamicSeparationDutySet` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `cardinality` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DynamicSeparationDutySet`
--

LOCK TABLES `DynamicSeparationDutySet` WRITE;
/*!40000 ALTER TABLE `DynamicSeparationDutySet` DISABLE KEYS */;
/*!40000 ALTER TABLE `DynamicSeparationDutySet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DynamicSeparationDutySetRoles`
--

DROP TABLE IF EXISTS `DynamicSeparationDutySetRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DynamicSeparationDutySetRoles` (
  `dsd_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`dsd_id`,`role_id`),
  KEY `fk_dsdroles_dsd` (`dsd_id`),
  KEY `fk_dsdroles_role` (`role_id`),
  CONSTRAINT `fk_dsdroles_dsd` FOREIGN KEY (`dsd_id`) REFERENCES `DynamicSeparationDutySet` (`id`),
  CONSTRAINT `fk_dsdroles_role` FOREIGN KEY (`role_id`) REFERENCES `Roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DynamicSeparationDutySetRoles`
--

LOCK TABLES `DynamicSeparationDutySetRoles` WRITE;
/*!40000 ALTER TABLE `DynamicSeparationDutySetRoles` DISABLE KEYS */;
/*!40000 ALTER TABLE `DynamicSeparationDutySetRoles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Objects`
--

DROP TABLE IF EXISTS `Objects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Objects` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_objects_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Objects`
--

LOCK TABLES `Objects` WRITE;
/*!40000 ALTER TABLE `Objects` DISABLE KEYS */;
INSERT INTO `Objects` VALUES (38,'customer'),(1,'menu_articles'),(16,'menu_articles_article'),(3,'menu_articles_categ'),(18,'menu_currencies'),(19,'menu_currencies_currency'),(20,'menu_customers'),(21,'menu_customers_customer'),(22,'menu_documents'),(23,'menu_documents_document'),(24,'menu_documents_document_format'),(32,'menu_images'),(25,'menu_images_image'),(26,'menu_languages'),(27,'menu_languages_language'),(28,'menu_products'),(29,'menu_products_categories'),(30,'menu_products_product'),(33,'menu_security'),(37,'menu_security_object'),(36,'menu_security_permission'),(35,'menu_security_role'),(34,'menu_security_user');
/*!40000 ALTER TABLE `Objects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Operations`
--

DROP TABLE IF EXISTS `Operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Operations` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_operations_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Operations`
--

LOCK TABLES `Operations` WRITE;
/*!40000 ALTER TABLE `Operations` DISABLE KEYS */;
INSERT INTO `Operations` VALUES (50,'add'),(52,'delete'),(51,'edit'),(49,'view');
/*!40000 ALTER TABLE `Operations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Permissions` (
  `operation_id` int(10) DEFAULT NULL,
  `object_id` int(10) DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_permissions_name` (`name`),
  UNIQUE KEY `unq_permissions_objoperation` (`object_id`,`operation_id`),
  KEY `fk_permission_operation` (`operation_id`),
  KEY `fk_permissions_object` (`object_id`),
  CONSTRAINT `fk_permissions_object` FOREIGN KEY (`object_id`) REFERENCES `Objects` (`id`),
  CONSTRAINT `fk_permission_operation` FOREIGN KEY (`operation_id`) REFERENCES `Operations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Permissions`
--

LOCK TABLES `Permissions` WRITE;
/*!40000 ALTER TABLE `Permissions` DISABLE KEYS */;
INSERT INTO `Permissions` VALUES (49,1,10,'view menu_articles'),(49,16,11,'view menu_articles_article'),(49,3,12,'view menu_articles_categ'),(49,18,13,'view menu_currencies'),(49,19,14,'view menu_currencies_currency'),(49,20,15,'view menu_customers'),(49,21,16,'view menu_customers_customer'),(49,22,17,'view menu_documents'),(49,23,18,'view menu_documents_document'),(49,24,19,'view menu_documents_document_format'),(49,25,20,'view menu_images_image'),(49,26,21,'view menu_languages'),(49,27,22,'view menu_languages_language'),(49,28,23,'view menu_products'),(49,29,24,'view menu_products_categories'),(49,30,25,'view menu_products_product'),(49,32,26,'view menu_images'),(50,37,27,'add menu_security_object'),(50,36,28,'add menu_security_permission'),(50,35,29,'add menu_security_role'),(50,34,30,'add menu_security_user'),(49,33,31,'view menu_security'),(49,37,33,'view menu_security_object'),(49,36,34,'view menu_security_permission'),(49,35,35,'view menu_security_role'),(49,34,36,'view menu_security_user'),(50,19,37,'add menu_currencies_currency'),(50,16,38,'add menu_articles_article'),(50,3,39,'add menu_articles_categ'),(50,23,44,'add menu_documents_document'),(50,24,45,'add menu_documents_document_format'),(49,38,46,'view customer 3');
/*!40000 ALTER TABLE `Permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_roles_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Roles`
--

LOCK TABLES `Roles` WRITE;
/*!40000 ALTER TABLE `Roles` DISABLE KEYS */;
INSERT INTO `Roles` VALUES (2,'Administrators'),(3,'Customers'),(5,'Test role 1'),(1,'Visitors');
/*!40000 ALTER TABLE `Roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RolesInheritance`
--

DROP TABLE IF EXISTS `RolesInheritance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RolesInheritance` (
  `role_child` int(10) NOT NULL,
  `role_parent` int(10) NOT NULL,
  PRIMARY KEY (`role_child`,`role_parent`),
  KEY `fk_rolesinheritance_child` (`role_child`),
  KEY `fk_rolesinheritance_parent` (`role_parent`),
  CONSTRAINT `fk_rolesinheritance_child` FOREIGN KEY (`role_child`) REFERENCES `Roles` (`id`),
  CONSTRAINT `fk_rolesinheritance_parent` FOREIGN KEY (`role_parent`) REFERENCES `Roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RolesInheritance`
--

LOCK TABLES `RolesInheritance` WRITE;
/*!40000 ALTER TABLE `RolesInheritance` DISABLE KEYS */;
/*!40000 ALTER TABLE `RolesInheritance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SessionRoles`
--

DROP TABLE IF EXISTS `SessionRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SessionRoles` (
  `session_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL,
  PRIMARY KEY (`session_id`,`role_id`),
  KEY `fk_sessionroles_session` (`session_id`),
  KEY `fk_sessionroles_role` (`role_id`),
  CONSTRAINT `fk_sessionroles_role` FOREIGN KEY (`role_id`) REFERENCES `Roles` (`id`),
  CONSTRAINT `fk_sessionroles_session` FOREIGN KEY (`session_id`) REFERENCES `Sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SessionRoles`
--

LOCK TABLES `SessionRoles` WRITE;
/*!40000 ALTER TABLE `SessionRoles` DISABLE KEYS */;
/*!40000 ALTER TABLE `SessionRoles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sessions`
--

DROP TABLE IF EXISTS `Sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sessions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `active` tinyint(4) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `remote_session` varchar(200) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sessions_user` (`user_id`),
  CONSTRAINT `fk_sessions_user` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sessions`
--

LOCK TABLES `Sessions` WRITE;
/*!40000 ALTER TABLE `Sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (21),(22);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-01-15  7:58:49
