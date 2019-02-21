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
INSERT INTO `carriage_types` VALUES (1,'БК','Бизнес-класс (спальный вагон с купе бизнес-класса)',8,7),(2,'СВ(Л)','Спальный вагон с 2-местными купе',18,3.1),(3,'К(К)','Купейный вагон - спальный вагон с 4-местными купе',36,1.5),(4,'ПЛ(П)','лацкартный вагон - спальный вагон открытого типа',57,1),(5,'О(О)','Общий вагон - вагон открытого типа с сидячими местами',81,0.7);
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
INSERT INTO `route_stations` VALUES ('МОСЕКА619',0,'Москва',-1,0),('МОСЕКА619',1,'Владимир',15,124),('МОСЕКА619',2,'Нижний Новгород',0,304),('МОСЕКА619',3,'Казань',20,554),('МОСЕКА619',4,'Ижевск',0,814),('МОСЕКА619',5,'Екатеринбург',-2,1209),('МУРСАР918',0,'Мурманск',-1,0),('МУРСАР918',1,'Петрозаводск',30,554),('МУРСАР918',2,'Санкт-Петербург',0,894),('МУРСАР918',3,'Великий Новгород',30,1024),('МУРСАР918',4,'Тверь',0,1304),('МУРСАР918',5,'Москва',30,1409),('МУРСАР918',6,'Рязань',0,1554),('МУРСАР918',7,'Тамбов',30,1719),('МУРСАР918',8,'Саратов',-2,1974),('СМОЕКА718',0,'Смоленск',-1,0),('СМОЕКА718',1,'Москва',30,119),('СМОЕКА718',2,'Владимир',30,204),('СМОЕКА718',3,'Нижний Новгород',30,309),('СМОЕКА718',4,'Киров',30,499),('СМОЕКА718',5,'Пермь',30,674),('СМОЕКА718',6,'Екатеринбург',-2,819);
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
INSERT INTO `routes` VALUES ('СМОЕКА718',1),('МОСЕКА619',2),('МУРСАР918',3);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route` varchar(45) NOT NULL,
  `departure` datetime NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `route_idx` (`route`),
  CONSTRAINT `route` FOREIGN KEY (`route`) REFERENCES `routes` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'admin@admin.com','admin','Админов','Админ','Админович','0001-01-01',_binary '','ADMIN'),(3,'mail@mail','password','Сидоров','Леонид','Святославович','1989-04-01',_binary '','USER');
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

-- Dump completed on 2019-02-21 19:27:31
