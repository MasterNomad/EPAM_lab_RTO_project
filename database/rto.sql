-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: rto
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carriage_types`
--

DROP TABLE IF EXISTS `carriage_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `carriage_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `description` tinytext NOT NULL,
  `places` int(2) NOT NULL,
  `price_factor` float NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carriage_types`
--

LOCK TABLES `carriage_types` WRITE;
/*!40000 ALTER TABLE `carriage_types` DISABLE KEYS */;
INSERT INTO `carriage_types` VALUES (1,'БК','Бизнес-класс (спальный вагон с купе бизнес-класса)',8,7),(2,'СВ(Л)','Спальный вагон с 2-местными купе',18,3.1),(3,'К(К)','Купейный вагон (спальный вагон с 4-местными купе)',36,1.5),(4,'ПЛ(П)','Плацкартный вагон (спальный вагон открытого типа)',57,1),(5,'О(О)','Общий вагон (вагон открытого типа с сидячими местами)',81,0.7);
/*!40000 ALTER TABLE `carriage_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locomotives`
--

DROP TABLE IF EXISTS `locomotives`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `locomotives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `average_speed` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locomotives`
--

LOCK TABLES `locomotives` WRITE;
/*!40000 ALTER TABLE `locomotives` DISABLE KEYS */;
INSERT INTO `locomotives` VALUES (1,'ЭВС1',200),(2,'ЭТ2',90),(3,'ЭД4',100),(4,'ЭД9',120);
/*!40000 ALTER TABLE `locomotives` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `requests` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `departure_city` varchar(45) NOT NULL,
  `departure_datetime` datetime NOT NULL,
  `destination_city` varchar(45) NOT NULL,
  `arrival_datetime` datetime NOT NULL,
  `carriage_id` int(11) NOT NULL,
  `price` float NOT NULL,
  `request_status` varchar(45) NOT NULL,
  `payment_state` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`request_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `Trip_id_idx` (`trip_id`),
  KEY `departur_city_req_idx` (`departure_city`),
  KEY `destination_city_req_idx` (`destination_city`),
  KEY `carriage_id_req_idx` (`carriage_id`),
  CONSTRAINT `carriage_id_req` FOREIGN KEY (`carriage_id`) REFERENCES `carriage_types` (`id`),
  CONSTRAINT `departur_city_req` FOREIGN KEY (`departure_city`) REFERENCES `stations` (`name`),
  CONSTRAINT `destination_city_req` FOREIGN KEY (`destination_city`) REFERENCES `stations` (`name`),
  CONSTRAINT `trip_id_req` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (1,1,43,'Казань','2019-02-26 17:00:00','Екатеринбург','2019-02-27 03:35:00',3,10177.5,'ACTIVE',_binary '\0'),(2,1,43,'Москва','2019-02-26 07:25:00','Казань','2019-02-26 16:40:00',1,47495,'ACTIVE',_binary ''),(4,1,46,'Великий Новгород','2019-03-04 07:35:00','Тамбов','2019-03-04 18:40:00',4,5750,'CANCELED',_binary ''),(5,1,44,'Москва','2019-03-12 07:25:00','Казань','2019-03-12 16:40:00',1,47495,'ACTIVE',_binary ''),(6,1,52,'Мурманск','2019-03-21 14:00:00','Саратов','2019-03-22 22:55:00',3,8625,'ACTIVE',_binary '\0'),(7,5,44,'Владимир','2019-03-12 09:45:00','Казань','2019-03-12 16:40:00',4,6785,'ACTIVE',_binary '');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_stations`
--

DROP TABLE IF EXISTS `route_stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `route_stations` (
  `title` varchar(10) NOT NULL,
  `order` int(11) NOT NULL,
  `station_name` varchar(45) DEFAULT NULL,
  `stop_duration` int(3) NOT NULL DEFAULT '0',
  `travel_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`title`,`order`),
  CONSTRAINT `title` FOREIGN KEY (`title`) REFERENCES `routes` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_stations`
--

LOCK TABLES `route_stations` WRITE;
/*!40000 ALTER TABLE `route_stations` DISABLE KEYS */;
INSERT INTO `route_stations` VALUES ('АРХМИН207',0,'Архангельск',-1,0),('АРХМИН207',1,'Коноша',25,375),('АРХМИН207',2,'Вологда',10,565),('АРХМИН207',3,'Кострома',15,730),('АРХМИН207',4,'Ярославль',45,800),('АРХМИН207',5,'Владимир',25,980),('АРХМИН207',6,'Москва',60,1130),('АРХМИН207',7,'Калуга',25,1310),('АРХМИН207',8,'Брянск',25,1485),('АРХМИН207',9,'Орёл',25,1595),('АРХМИН207',10,'Курск',10,1730),('АРХМИН207',11,'Воронеж',10,1890),('АРХМИН207',12,'Ростов-на-Дону',35,2285),('АРХМИН207',13,'Краснодар',25,2505),('АРХМИН207',14,'Новороссийск',25,2680),('АРХМИН207',15,'Краснодар',25,2855),('АРХМИН207',16,'Сочи',45,3060),('АРХМИН207',17,'Краснодар',10,3285),('АРХМИН207',18,'Махачкала',25,3405),('АРХМИН207',19,'Минеральные Воды',-2,3710),('ВЛАОРЁ197',0,'Владивосток',-1,0),('ВЛАОРЁ197',1,'Хабаровск',45,230),('ВЛАОРЁ197',2,'Биробиджан',15,330),('ВЛАОРЁ197',3,'Чита',10,920),('ВЛАОРЁ197',4,'Улан-Удэ',10,1080),('ВЛАОРЁ197',5,'Иркутск',35,1225),('ВЛАОРЁ197',6,'Красноярск',25,1570),('ВЛАОРЁ197',7,'Томск',15,1765),('ВЛАОРЁ197',8,'Новосибирск',45,1855),('ВЛАОРЁ197',9,'Омск',0,2095),('ВЛАОРЁ197',10,'Курган',15,2260),('ВЛАОРЁ197',11,'Екатеринбург',45,2400),('ВЛАОРЁ197',12,'Ижевск',15,2625),('ВЛАОРЁ197',13,'Казань',50,2750),('ВЛАОРЁ197',14,'Нижний Новгород',35,2915),('ВЛАОРЁ197',15,'Владимир',20,3025),('ВЛАОРЁ197',16,'Москва',60,3100),('ВЛАОРЁ197',17,'Тула',15,3220),('ВЛАОРЁ197',18,'Орёл',-2,3290),('МИНАРХ207',0,'Минеральные Воды',-1,0),('МИНАРХ207',1,'Махачкала',25,210),('МИНАРХ207',2,'Краснодар',25,315),('МИНАРХ207',3,'Сочи',60,475),('МИНАРХ207',4,'Краснодар',25,670),('МИНАРХ207',5,'Новороссийск',45,805),('МИНАРХ207',6,'Краснодар',25,960),('МИНАРХ207',7,'Ростов-на-Дону',25,1125),('МИНАРХ207',8,'Воронеж',10,1440),('МИНАРХ207',9,'Курск',25,1565),('МИНАРХ207',10,'Орёл',25,1670),('МИНАРХ207',11,'Брянск',10,1760),('МИНАРХ207',12,'Калуга',10,1880),('МИНАРХ207',13,'Москва',60,1980),('МИНАРХ207',14,'Владимир',25,2135),('МИНАРХ207',15,'Ярославль',15,2260),('МИНАРХ207',16,'Кострома',25,2315),('МИНАРХ207',17,'Вологда',15,2460),('МИНАРХ207',18,'Коноша',25,2600),('МИНАРХ207',19,'Архангельск',-2,2905),('МОСЕКА619',0,'Москва',-1,0),('МОСЕКА619',1,'Владимир',15,125),('МОСЕКА619',2,'Нижний Новгород',0,305),('МОСЕКА619',3,'Казань',20,555),('МОСЕКА619',4,'Ижевск',0,815),('МОСЕКА619',5,'Екатеринбург',-2,1210),('МОССАН47',0,'Москва',-1,0),('МОССАН47',1,'Тверь',20,50),('МОССАН47',2,'Великий Новгород',20,195),('МОССАН47',3,'Санкт-Петербург',-2,280),('МУРСАР918',0,'Мурманск',-1,0),('МУРСАР918',1,'Петрозаводск',30,555),('МУРСАР918',2,'Санкт-Петербург',10,895),('МУРСАР918',3,'Великий Новгород',30,1035),('МУРСАР918',4,'Тверь',20,1315),('МУРСАР918',5,'Москва',30,1440),('МУРСАР918',6,'Рязань',15,1585),('МУРСАР918',7,'Тамбов',30,1765),('МУРСАР918',8,'Саратов',-2,2020),('ОРЁВЛА197',0,'Орёл',-1,0),('ОРЁВЛА197',1,'Тула',15,110),('ОРЁВЛА197',2,'Москва',80,250),('ОРЁВЛА197',3,'Владимир',25,445),('ОРЁВЛА197',4,'Нижний Новгород',35,620),('ОРЁВЛА197',5,'Казань',50,880),('ОРЁВЛА197',6,'Ижевск',15,1145),('ОРЁВЛА197',7,'Екатеринбург',35,1515),('ОРЁВЛА197',8,'Курган',15,1800),('ОРЁВЛА197',9,'Омск',0,2145),('ОРЁВЛА197',10,'Новосибирск',15,2535),('ОРЁВЛА197',11,'Томск',15,2695),('ОРЁВЛА197',12,'Красноярск',25,3050),('ОРЁВЛА197',13,'Иркутск',15,3700),('ОРЁВЛА197',14,'Улан-Удэ',10,3985),('ОРЁВЛА197',15,'Чита',10,4290),('ОРЁВЛА197',16,'Биробиджан',35,5455),('ОРЁВЛА197',17,'Хабаровск',45,5600),('ОРЁВЛА197',18,'Владивосток',-2,6100),('ПСКСАЛ87',0,'Псков',-1,0),('ПСКСАЛ87',1,'Санкт-Петербург',45,225),('ПСКСАЛ87',2,'Вологда',15,705),('ПСКСАЛ87',3,'Коноша',15,885),('ПСКСАЛ87',4,'Котлас',15,965),('ПСКСАЛ87',5,'Печора',15,1535),('ПСКСАЛ87',6,'Воркута',15,1905),('ПСКСАЛ87',7,'Салехард',-2,2195),('САЛПСК87',0,'Салехард',-1,0),('САЛПСК87',1,'Воркута',15,210),('САЛПСК87',2,'Печора',15,490),('САЛПСК87',3,'Котлас',15,925),('САЛПСК87',4,'Коноша',15,990),('САЛПСК87',5,'Вологда',15,1130),('САЛПСК87',6,'Санкт-Петербург',45,1470),('САЛПСК87',7,'Псков',-2,1685),('САНМОС47',0,'Санкт-Петербург',-1,0),('САНМОС47',1,'Великий Новгород',20,65),('САНМОС47',2,'Тверь',20,210),('САНМОС47',3,'Москва',-2,280),('САРМУР97',0,'Саратов',-1,0),('САРМУР97',1,'Тамбов',15,250),('САРМУР97',2,'Рязань',15,450),('САРМУР97',3,'Москва',15,595),('САРМУР97',4,'Тверь',15,725),('САРМУР97',5,'Великий Новгород',15,1015),('САРМУР97',6,'Санкт-Петербург',15,1175),('САРМУР97',7,'Петрозаводск',15,1535),('САРМУР97',8,'Мурманск',-2,2170),('СМОЕКА718',0,'Смоленск',-1,0),('СМОЕКА718',1,'Москва',30,200),('СМОЕКА718',2,'Владимир',30,325),('СМОЕКА718',3,'Нижний Новгород',30,480),('СМОЕКА718',4,'Киров',30,780),('СМОЕКА718',5,'Пермь',30,1055),('СМОЕКА718',6,'Екатеринбург',-2,1280);
/*!40000 ALTER TABLE `route_stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `routes` (
  `title` varchar(15) NOT NULL,
  `locomotive_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`title`),
  KEY `locomotive_id_idx` (`locomotive_id`),
  CONSTRAINT `locomotive_id` FOREIGN KEY (`locomotive_id`) REFERENCES `locomotives` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES ('ВЛАОРЁ197',1),('МОССАН47',1),('САНМОС47',1),('АРХМИН207',2),('МОСЕКА619',2),('ПСКСАЛ87',2),('САРМУР97',2),('МУРСАР918',3),('ОРЁВЛА197',3),('МИНАРХ207',4),('САЛПСК87',4),('СМОЕКА718',4);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_connections`
--

DROP TABLE IF EXISTS `station_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `station_connections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_1` varchar(45) NOT NULL,
  `station_2` varchar(45) NOT NULL,
  `distance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `station_1_UNIQUE` (`station_1`,`station_2`),
  KEY `name_idx` (`station_2`),
  CONSTRAINT `name` FOREIGN KEY (`station_1`) REFERENCES `stations` (`name`),
  CONSTRAINT `name2` FOREIGN KEY (`station_2`) REFERENCES `stations` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_connections`
--

LOCK TABLES `station_connections` WRITE;
/*!40000 ALTER TABLE `station_connections` DISABLE KEYS */;
INSERT INTO `station_connections` VALUES (1,'Москва','Тверь',172),(2,'Москва','Владимир',189),(3,'Москва','Рязань',192),(4,'Москва','Тула',208),(5,'Москва','Калуга',182),(6,'Москва','Смоленск',395),(7,'Тверь  ','Великий Новгород',415),(8,'Владимир','Ярославль',200),(9,'Владимир','Нижний Новгород',249),(10,'Рязань','Липецк',290),(11,'Рязань','Тамбов',277),(12,'Рязань','Пенза',535),(13,'Рязань','Ульяновск',657),(14,'Рязань','Казань',745),(15,'Рязань','Нижний Новгород',391),(16,'Тула','Орёл',182),(17,'Калуга','Брянск',223),(18,'Великий Новгород','Санкт-Петербург',214),(19,'Санкт-Петербург','Псков',341),(20,'Санкт-Петербург','Петрозаводск',518),(21,'Санкт-Петербург','Вологда',650),(22,'Петрозаводск','Архангельск',935),(23,'Петрозаводск','Мурманск',929),(24,'Петрозаводск','Коноша',687),(25,'Брянск','Орёл',127),(26,'Орёл','Курск',162),(27,'Курск','Воронеж',226),(28,'Воронеж','Липецк',138),(29,'Воронеж','Ростов-на-Дону',578),(30,'Воронеж','Волгоград',575),(31,'Ростов-на-Дону','Краснодар',275),(32,'Краснодар','Сочи',270),(33,'Краснодар','Новороссийск',223),(34,'Краснодар','Махачкала',163),(35,'Сочи','Махачкала',948),(36,'Махачкала','Минеральные Воды',420),(37,'Волгоград','Астрахань',422),(38,'Волгоград','Ростов-на-Дону',490),(39,'Волгоград','Липецк',611),(40,'Архангельск','Мурманск',1545),(41,'Архангельск','Коноша',563),(42,'Коноша','Вологда',247),(43,'Коноша','Котлас',101),(44,'Котлас','Печора',835),(45,'Печора','Салехард',2693),(46,'Печора','Воркута',529),(47,'Воркута','Салехард',416),(48,'Вологда','Кострома',235),(49,'Кострома','Ярославль',81),(50,'Нижний Новгород','Киров',537),(51,'Нижний Новгород','Казань',378),(52,'Нижний Новгород','Ульяновск',437),(53,'Нижний Новгород','Пенза',458),(54,'Киров','Пермь',491),(55,'Казань','Ижевск',361),(56,'Ульяновск','Самара',257),(57,'Самара','Уфа',482),(58,'Самара','Оренбург',415),(59,'Оренбург','Челябинск',689),(60,'Пенза','Тамбов',288),(61,'Пенза','Саратов',283),(62,'Пенза','Ульяновск',306),(63,'Пенза','Казань',479),(64,'Саратов','Тамбов',375),(65,'Липецк','Тамбов',133),(66,'Ростов-на-Дону','Липецк',608),(67,'Казань','Ульяновск',221),(68,'Екатеринбург','Ижевск',592),(69,'Екатеринбург','Пермь',387),(70,'Екатеринбург','Тюмень',347),(71,'Екатеринбург','Курган',417),(72,'Екатеринбург','Челябинск',245),(73,'Екатеринбург','Уфа',487),(79,'Челябинск','Курган',339),(80,'Курган','Омск',548),(81,'Тюмень','Омск',619),(82,'Тюмень','Тобольск',253),(83,'Омск','Новосибирск',647),(84,'Тобольск','Нижневартовск',766),(85,'Сургут','Нижневартовск',214),(86,'Сургут','Новый Уренгой',742),(87,'Новый Уренгой','Нижневартовск',866),(88,'Новый Уренгой','Надым',242),(89,'Новосибирск','Кемерово',237),(90,'Новосибирск','Томск',245),(91,'Томск','Кемерово',181),(92,'Томск','Красноярск',566),(93,'Красноярск','Лесосибирск',294),(94,'Кемерово','Новокузнецк',231),(95,'Кемерово','Барнаул',377),(96,'Новокузнецк','Абакан',638),(97,'Абакан','Братск',950),(98,'Абакан','Красноярск',409),(99,'Абакан','Иркутск',1331),(100,'Красноярск','Братск',657),(101,'Красноярск','Иркутск',1038),(102,'Иркутск','Братск',611),(103,'Иркутск','Улан-Удэ',450),(104,'Улан-Удэ','Чита',494),(105,'Братск','Усть-Илимск',278),(106,'Братск','Усть-Кут',349),(107,'Усть-Илимск','Усть-Кут',589),(108,'Усть-Кут','Нижнеангарск',351),(109,'Нижнеангарск','Тында',1926),(110,'Тында','Нерюнгри',202),(111,'Тында','Комсомольск-на-Амуре',1419),(112,'Тында','Чита',1055),(113,'Тында','Биробиджан',1154),(114,'Чита','Биробиджан',1924),(115,'Комсомольск-на-Амуре','Советская Гавань',531),(116,'Комсомольск-на-Амуре','Биробиджан',585),(117,'Комсомольск-на-Амуре','Хабаровск',401),(118,'Хабаровск','Биробиджан',187),(119,'Хабаровск','Владивосток',759),(120,'Владивосток','Хасан',163);
/*!40000 ALTER TABLE `station_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `stations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (70,'Абакан'),(44,'Архангельск'),(32,'Астрахань'),(20,'Барнаул'),(78,'Биробиджан'),(71,'Братск'),(41,'Брянск'),(58,'Великий Новгород'),(25,'Владивосток'),(43,'Владимир'),(15,'Волгоград'),(50,'Вологда'),(65,'Воркута'),(14,'Воронеж'),(4,'Екатеринбург'),(19,'Ижевск'),(22,'Иркутск'),(8,'Казань'),(46,'Калуга'),(29,'Кемерово'),(35,'Киров'),(56,'Комсомольск-на-Амуре'),(60,'Коноша'),(54,'Кострома'),(62,'Котлас'),(16,'Краснодар'),(12,'Красноярск'),(48,'Курган'),(37,'Курск'),(69,'Лесосибирск'),(34,'Липецк'),(26,'Махачкала'),(61,'Минеральные Воды'),(1,'Москва'),(51,'Мурманск'),(68,'Надым'),(76,'Нерюнгри'),(74,'Нижнеангарск'),(57,'Нижневартовск'),(5,'Нижний Новгород'),(30,'Новокузнецк'),(55,'Новороссийск'),(3,'Новосибирск'),(67,'Новый Уренгой'),(7,'Омск'),(49,'Орёл'),(28,'Оренбург'),(33,'Пенза'),(13,'Пермь'),(53,'Петрозаводск'),(63,'Печора'),(59,'Псков'),(10,'Ростов-на-Дону'),(31,'Рязань'),(64,'Салехард'),(6,'Самара'),(2,'Санкт-Петербург'),(17,'Саратов'),(47,'Смоленск'),(77,'Советская Гавань'),(40,'Сочи'),(42,'Сургут'),(52,'Тамбов'),(39,'Тверь'),(66,'Тобольск'),(27,'Томск'),(36,'Тула'),(75,'Тында'),(18,'Тюмень'),(38,'Улан-Удэ'),(21,'Ульяновск'),(73,'Усть-Илимск'),(72,'Усть-Кут'),(11,'Уфа'),(23,'Хабаровск'),(79,'Хасан'),(9,'Челябинск'),(45,'Чита'),(24,'Ярославль');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_composition`
--

DROP TABLE IF EXISTS `trip_composition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `trip_composition` (
  `trip_id` int(11) NOT NULL,
  `carriage_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '0',
  `places_sold` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`trip_id`,`carriage_id`),
  KEY `id_idx` (`carriage_id`),
  CONSTRAINT `id` FOREIGN KEY (`carriage_id`) REFERENCES `carriage_types` (`id`),
  CONSTRAINT `trip_id` FOREIGN KEY (`trip_id`) REFERENCES `trips` (`trip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_composition`
--

LOCK TABLES `trip_composition` WRITE;
/*!40000 ALTER TABLE `trip_composition` DISABLE KEYS */;
INSERT INTO `trip_composition` VALUES (43,1,1,1),(43,3,3,1),(43,4,5,0),(44,1,1,-2),(44,3,3,0),(44,4,5,1),(45,1,1,0),(45,2,1,0),(45,3,1,0),(45,4,1,0),(45,5,1,0),(46,1,1,0),(46,2,1,0),(46,3,1,0),(46,4,1,0),(46,5,1,0),(47,1,1,0),(47,2,1,0),(47,3,1,0),(47,4,1,0),(47,5,1,0),(48,1,1,0),(48,2,1,0),(48,3,1,0),(48,4,1,0),(48,5,1,0),(49,1,1,0),(49,2,1,0),(49,3,1,0),(49,4,1,0),(49,5,1,0),(50,1,1,0),(50,2,1,0),(50,3,1,0),(50,4,1,0),(50,5,1,0),(51,1,1,0),(51,2,1,0),(51,3,1,0),(51,4,1,0),(51,5,1,0),(52,1,1,0),(52,2,1,0),(52,3,1,1),(52,4,1,0),(52,5,1,0),(53,1,1,0),(53,2,1,0),(53,3,1,0),(53,4,1,0),(53,5,1,0),(54,1,1,0),(54,2,1,0),(54,3,1,0),(54,4,1,0),(54,5,1,0),(55,1,1,0),(55,2,2,0),(55,3,5,0),(55,4,5,0),(55,5,1,0),(56,1,1,0),(56,2,2,0),(56,3,5,0),(56,4,5,0),(56,5,1,0),(57,1,1,0),(57,2,2,0),(57,3,5,0),(57,4,5,0),(57,5,1,0),(58,1,1,0),(58,2,2,0),(58,3,5,0),(58,4,5,0),(58,5,1,0),(59,1,1,0),(59,2,2,0),(59,3,5,0),(59,4,5,0),(59,5,1,0),(60,1,1,0),(60,2,2,0),(60,3,5,0),(60,4,5,0),(60,5,1,0),(61,1,1,0),(61,2,2,0),(61,3,5,0),(61,4,5,0),(61,5,1,0),(62,1,1,0),(62,2,1,0),(62,3,4,0),(62,4,7,0),(63,1,1,0),(63,2,1,0),(63,3,4,0),(63,4,7,0),(64,1,1,0),(64,2,1,0),(64,3,4,0),(64,4,7,0),(65,1,1,0),(65,2,1,0),(65,3,4,0),(65,4,7,0),(66,1,1,0),(66,2,1,0),(66,3,4,0),(66,4,7,0),(67,1,1,0),(67,2,1,0),(67,3,4,0),(67,4,7,0),(68,1,1,0),(68,2,1,0),(68,3,4,0),(68,4,7,0),(69,1,1,0),(69,2,1,0),(69,3,4,0),(69,4,7,0),(70,2,2,0),(70,3,4,0),(70,4,5,0),(70,5,2,0),(71,2,2,0),(71,3,4,0),(71,4,5,0),(71,5,2,0),(72,2,2,0),(72,3,4,0),(72,4,5,0),(72,5,2,0),(73,2,2,0),(73,3,4,0),(73,4,5,0),(73,5,2,0),(74,2,2,0),(74,3,4,0),(74,4,5,0),(74,5,2,0),(75,2,2,0),(75,3,4,0),(75,4,5,0),(75,5,2,0),(76,2,2,0),(76,3,4,0),(76,4,5,0),(76,5,2,0),(77,2,2,0),(77,3,4,0),(77,4,5,0),(77,5,2,0),(78,2,2,0),(78,3,4,0),(78,4,5,0),(78,5,2,0),(79,2,2,0),(79,3,4,0),(79,4,5,0),(79,5,2,0),(80,2,1,0),(80,3,5,0),(80,4,4,0),(80,5,1,0),(81,2,1,0),(81,3,5,0),(81,4,4,0),(81,5,1,0),(82,2,1,0),(82,3,5,0),(82,4,4,0),(82,5,1,0),(83,2,1,0),(83,3,5,0),(83,4,4,0),(83,5,1,0),(84,2,1,0),(84,3,5,0),(84,4,4,0),(84,5,1,0),(85,2,1,0),(85,3,5,0),(85,4,4,0),(85,5,1,0),(86,2,1,0),(86,3,5,0),(86,4,4,0),(86,5,1,0),(87,2,1,0),(87,3,5,0),(87,4,4,0),(87,5,1,0),(88,3,4,0),(88,4,5,0),(89,3,4,0),(89,4,5,0),(90,3,4,0),(90,4,5,0),(91,3,4,0),(91,4,5,0),(92,3,4,0),(92,4,5,0),(93,3,4,0),(93,4,5,0),(94,3,4,0),(94,4,5,0),(95,3,4,0),(95,4,5,0),(96,3,4,0),(96,4,5,0),(97,3,4,0),(97,4,5,0),(98,3,4,0),(98,4,5,0),(99,3,4,0),(99,4,5,0),(100,3,4,0),(100,4,5,0),(101,3,4,0),(101,4,5,0),(102,2,1,0),(102,3,3,0),(102,4,3,0),(102,5,2,0),(103,2,1,0),(103,3,3,0),(103,4,3,0),(103,5,2,0),(104,2,1,0),(104,3,3,0),(104,4,3,0),(104,5,2,0),(105,2,1,0),(105,3,3,0),(105,4,3,0),(105,5,2,0),(106,2,1,0),(106,3,3,0),(106,4,3,0),(106,5,2,0),(107,2,1,0),(107,3,3,0),(107,4,3,0),(107,5,2,0),(108,2,1,0),(108,3,3,0),(108,4,3,0),(108,5,2,0),(109,2,1,0),(109,3,3,0),(109,4,3,0),(109,5,2,0),(110,2,1,0),(110,3,3,0),(110,4,3,0),(110,5,2,0),(111,2,1,0),(111,3,3,0),(111,4,3,0),(111,5,2,0),(112,5,5,0),(113,5,5,0),(114,5,5,0),(115,5,5,0),(116,5,5,0),(117,5,5,0),(118,5,5,0),(119,5,5,0),(120,5,5,0),(121,5,5,0),(122,5,5,0),(123,5,5,0),(124,5,5,0),(125,5,5,0),(126,5,5,0),(127,5,5,0),(128,5,5,0),(129,5,5,0),(130,5,5,0),(131,5,5,0),(132,5,5,0),(133,5,5,0),(134,5,5,0),(135,5,5,0),(136,5,5,0),(137,5,5,0),(138,5,5,0),(139,5,5,0),(140,5,5,0),(141,5,5,0),(142,5,5,0);
/*!40000 ALTER TABLE `trip_composition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trips`
--

DROP TABLE IF EXISTS `trips`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `trips` (
  `trip_id` int(11) NOT NULL AUTO_INCREMENT,
  `route` varchar(45) NOT NULL,
  `departure` datetime NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`trip_id`),
  KEY `route_idx` (`route`),
  CONSTRAINT `routes` FOREIGN KEY (`route`) REFERENCES `routes` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trips`
--

LOCK TABLES `trips` WRITE;
/*!40000 ALTER TABLE `trips` DISABLE KEYS */;
INSERT INTO `trips` VALUES (43,'МОСЕКА619','2019-02-26 07:25:00',6785),(44,'МОСЕКА619','2019-03-12 07:25:00',6785),(45,'МУРСАР918','2019-02-28 14:00:00',5750),(46,'МУРСАР918','2019-03-03 14:00:00',5750),(47,'МУРСАР918','2019-03-06 14:00:00',5750),(48,'МУРСАР918','2019-03-09 14:00:00',5750),(49,'МУРСАР918','2019-03-12 14:00:00',5750),(50,'МУРСАР918','2019-03-15 14:00:00',5750),(51,'МУРСАР918','2019-03-18 14:00:00',5750),(52,'МУРСАР918','2019-03-21 14:00:00',5750),(53,'МУРСАР918','2019-03-24 14:00:00',5750),(54,'МУРСАР918','2019-03-27 14:00:00',5750),(55,'ВЛАОРЁ197','2019-03-07 06:00:00',12435),(56,'ВЛАОРЁ197','2019-03-12 06:00:00',12435),(57,'ВЛАОРЁ197','2019-03-17 06:00:00',12435),(58,'ВЛАОРЁ197','2019-03-22 06:00:00',12435),(59,'ВЛАОРЁ197','2019-03-27 06:00:00',12435),(60,'ВЛАОРЁ197','2019-04-01 06:00:00',12435),(61,'ВЛАОРЁ197','2019-04-06 06:00:00',12435),(62,'ОРЁВЛА197','2019-03-08 13:00:00',11530),(63,'ОРЁВЛА197','2019-03-12 13:00:00',11530),(64,'ОРЁВЛА197','2019-03-16 13:00:00',11530),(65,'ОРЁВЛА197','2019-03-20 13:00:00',11530),(66,'ОРЁВЛА197','2019-03-24 13:00:00',11530),(67,'ОРЁВЛА197','2019-03-28 13:00:00',11530),(68,'ОРЁВЛА197','2019-04-01 13:00:00',11530),(69,'ОРЁВЛА197','2019-04-05 13:00:00',11530),(70,'АРХМИН207','2019-03-08 10:15:00',7565),(71,'АРХМИН207','2019-03-11 10:15:00',7565),(72,'АРХМИН207','2019-03-14 10:15:00',7565),(73,'АРХМИН207','2019-03-17 10:15:00',7565),(74,'АРХМИН207','2019-03-20 10:15:00',7565),(75,'АРХМИН207','2019-03-23 10:15:00',7565),(76,'АРХМИН207','2019-03-26 10:15:00',7565),(77,'АРХМИН207','2019-03-29 10:15:00',7565),(78,'АРХМИН207','2019-04-01 10:15:00',7565),(79,'АРХМИН207','2019-04-04 10:15:00',7565),(80,'МИНАРХ207','2019-03-07 05:45:00',7335),(81,'МИНАРХ207','2019-03-11 05:45:00',7335),(82,'МИНАРХ207','2019-03-15 05:45:00',7335),(83,'МИНАРХ207','2019-03-19 05:45:00',7335),(84,'МИНАРХ207','2019-03-23 05:45:00',7335),(85,'МИНАРХ207','2019-03-27 05:45:00',7335),(86,'МИНАРХ207','2019-03-31 05:45:00',7335),(87,'МИНАРХ207','2019-04-04 05:45:00',7335),(88,'ПСКСАЛ87','2019-03-10 05:30:00',5220),(89,'ПСКСАЛ87','2019-03-12 05:30:00',5220),(90,'ПСКСАЛ87','2019-03-14 05:30:00',5220),(91,'ПСКСАЛ87','2019-03-16 05:30:00',5220),(92,'ПСКСАЛ87','2019-03-18 05:30:00',5220),(93,'ПСКСАЛ87','2019-03-20 05:30:00',5220),(94,'ПСКСАЛ87','2019-03-22 05:30:00',5220),(95,'ПСКСАЛ87','2019-03-24 05:30:00',5220),(96,'ПСКСАЛ87','2019-03-26 05:30:00',5220),(97,'ПСКСАЛ87','2019-03-28 05:30:00',5220),(98,'ПСКСАЛ87','2019-03-30 05:30:00',5220),(99,'ПСКСАЛ87','2019-04-01 05:30:00',5220),(100,'ПСКСАЛ87','2019-04-03 05:30:00',5220),(101,'ПСКСАЛ87','2019-04-05 05:30:00',5220),(102,'САЛПСК87','2019-03-09 17:00:00',4865),(103,'САЛПСК87','2019-03-12 17:00:00',4865),(104,'САЛПСК87','2019-03-15 17:00:00',4865),(105,'САЛПСК87','2019-03-18 17:00:00',4865),(106,'САЛПСК87','2019-03-21 17:00:00',4865),(107,'САЛПСК87','2019-03-24 17:00:00',4865),(108,'САЛПСК87','2019-03-27 17:00:00',4865),(109,'САЛПСК87','2019-03-30 17:00:00',4865),(110,'САЛПСК87','2019-04-02 17:00:00',4865),(111,'САЛПСК87','2019-04-05 17:00:00',4865),(112,'САНМОС47','2019-03-07 06:00:00',1500),(113,'САНМОС47','2019-03-09 06:00:00',1500),(114,'САНМОС47','2019-03-11 06:00:00',1500),(115,'САНМОС47','2019-03-13 06:00:00',1500),(116,'САНМОС47','2019-03-15 06:00:00',1500),(117,'САНМОС47','2019-03-17 06:00:00',1500),(118,'САНМОС47','2019-03-19 06:00:00',1500),(119,'САНМОС47','2019-03-21 06:00:00',1500),(120,'САНМОС47','2019-03-23 06:00:00',1500),(121,'САНМОС47','2019-03-25 06:00:00',1500),(122,'САНМОС47','2019-03-27 06:00:00',1500),(123,'САНМОС47','2019-03-29 06:00:00',1500),(124,'САНМОС47','2019-03-31 06:00:00',1500),(125,'САНМОС47','2019-04-02 06:00:00',1500),(126,'САНМОС47','2019-04-04 06:00:00',1500),(127,'САНМОС47','2019-04-06 06:00:00',1500),(128,'МОССАН47','2019-03-08 06:00:00',1500),(129,'МОССАН47','2019-03-10 06:00:00',1500),(130,'МОССАН47','2019-03-12 06:00:00',1500),(131,'МОССАН47','2019-03-14 06:00:00',1500),(132,'МОССАН47','2019-03-16 06:00:00',1500),(133,'МОССАН47','2019-03-18 06:00:00',1500),(134,'МОССАН47','2019-03-20 06:00:00',1500),(135,'МОССАН47','2019-03-22 06:00:00',1500),(136,'МОССАН47','2019-03-24 06:00:00',1500),(137,'МОССАН47','2019-03-26 06:00:00',1500),(138,'МОССАН47','2019-03-28 06:00:00',1500),(139,'МОССАН47','2019-03-30 06:00:00',1500),(140,'МОССАН47','2019-04-01 06:00:00',1500),(141,'МОССАН47','2019-04-03 06:00:00',1500),(142,'МОССАН47','2019-04-05 06:00:00',1500);
/*!40000 ALTER TABLE `trips` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `patronymic` varchar(45) NOT NULL,
  `birthDate` date NOT NULL,
  `sex` bit(1) NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'admin@admin.com','admin','Админов','Админ','Админович','0001-01-01',_binary '','ADMIN'),(1,'user@user.ru','password','Сидоров','Леонид','Святославович','1989-04-01',_binary '','USER'),(5,'Lada326@mail.com','password2','Арджеванидзе','Лада','Сергеевна','1981-12-16',_binary '\0','USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-07 16:03:32