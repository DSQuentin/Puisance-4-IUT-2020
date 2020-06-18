-- MySQL dump 10.17  Distrib 10.3.22-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 46.105.92.223    Database: db42b
-- ------------------------------------------------------
-- Server version	10.0.38-MariaDB-0+deb8u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `are_friends`
--

DROP TABLE IF EXISTS `are_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `are_friends` (
  `id_user_1` int(11) NOT NULL,
  `id_user_2` int(11) NOT NULL,
  PRIMARY KEY (`id_user_1`,`id_user_2`),
  KEY `id_user_2` (`id_user_2`),
  CONSTRAINT `are_friends_ibfk_1` FOREIGN KEY (`id_user_1`) REFERENCES `users` (`id`),
  CONSTRAINT `are_friends_ibfk_2` FOREIGN KEY (`id_user_2`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `are_friends`
--

LOCK TABLES `are_friends` WRITE;
/*!40000 ALTER TABLE `are_friends` DISABLE KEYS */;
INSERT INTO `are_friends` VALUES (1,2),(1,3),(1,4),(1,12),(1,15),(12,3);
/*!40000 ALTER TABLE `are_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitations`
--

DROP TABLE IF EXISTS `invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invitations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `accepted` char(1) NOT NULL,
  `id_user_sender` int(11) NOT NULL,
  `id_user_receiver` int(11) NOT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `id_user_sender` (`id_user_sender`),
  KEY `id_user_receiver` (`id_user_receiver`),
  CONSTRAINT `invitations_ibfk_1` FOREIGN KEY (`id_user_sender`) REFERENCES `users` (`id`),
  CONSTRAINT `invitations_ibfk_2` FOREIGN KEY (`id_user_receiver`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitations`
--

LOCK TABLES `invitations` WRITE;
/*!40000 ALTER TABLE `invitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `invitations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sent` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` text NOT NULL,
  `id_sender` int(11) NOT NULL,
  `id_receiver` int(11) DEFAULT NULL,
  `opened` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_sender` (`id_sender`),
  KEY `id_received` (`id_receiver`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`id_sender`) REFERENCES `users` (`id`),
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`id_receiver`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (61,'2020-06-17 00:00:00','Hello',4,1,1),(62,'2020-06-17 00:00:00','qsd',1,4,0),(63,'2020-06-17 00:00:00','wxc',1,4,0),(64,'2020-06-17 00:00:00','x',1,2,1),(65,'2020-06-17 00:00:00','qsd',1,2,1),(66,'2020-06-17 00:00:00','x',1,2,1),(67,'2020-06-17 00:00:00','x',1,2,1),(68,'2020-06-17 00:00:00','x',1,2,1),(69,'2020-06-17 00:00:00','123',2,1,1),(70,'2020-06-17 00:00:00','456',2,1,1),(71,'2020-06-17 00:00:00','xxx',1,2,1),(72,'2020-06-17 00:00:00','xxx',1,2,1),(73,'2020-06-17 00:00:00','qsd',1,2,1),(74,'2020-06-17 00:00:00','aze',1,2,1),(75,'2020-06-17 00:00:00','qs',1,2,1),(76,'2020-06-17 00:00:00','xc',1,2,1),(77,'2020-06-17 00:00:00','sz',1,2,1),(78,'2020-06-17 00:00:00','qsdc',1,2,1),(79,'2020-06-17 00:00:00',' v',1,2,1),(80,'2020-06-17 00:00:00','fg',1,2,1),(81,'2020-06-17 00:00:00','f ',1,2,1),(82,'2020-06-17 00:00:00','gd',1,2,1),(83,'2020-06-17 00:00:00','fg',1,2,1),(84,'2020-06-17 00:00:00','df',1,2,1),(85,'2020-06-17 00:00:00','tge',1,2,1),(86,'2020-06-17 00:00:00','rt',1,2,1),(87,'2020-06-17 00:00:00','dfg',1,2,1),(88,'2020-06-17 00:00:00','qsd',1,2,1),(89,'2020-06-17 00:00:00','qsd',1,2,1),(90,'2020-06-17 00:00:00','wxc',1,2,1),(91,'2020-06-17 00:00:00','wxc',1,2,1),(92,'2020-06-17 00:00:00','wxc',1,2,1),(93,'2020-06-17 00:00:00','wxc',1,2,1),(94,'2020-06-17 00:00:00','qsd',2,1,1),(95,'2020-06-17 00:00:00','wxc',2,1,1),(96,'2020-06-17 00:00:00','wxc',2,1,1),(97,'2020-06-17 00:00:00','qsd',2,1,1),(98,'2020-06-17 00:00:00','qsd',1,2,1),(99,'2020-06-17 00:00:00','qsdw',1,2,1),(100,'2020-06-17 00:00:00','xc',1,2,1),(101,'2020-06-17 00:00:00','qsd',1,2,1),(102,'2020-06-17 00:00:00','az',1,2,1),(103,'2020-06-17 00:00:00','eq',1,2,1),(104,'2020-06-17 00:00:00','cq',1,2,1),(105,'2020-06-17 00:00:00','sd',1,2,1),(106,'2020-06-17 00:00:00','qs',1,2,1),(107,'2020-06-17 00:00:00','dq',1,2,1),(108,'2020-06-17 00:00:00','ze',1,2,1),(109,'2020-06-17 00:00:00','qs',1,2,1),(110,'2020-06-17 00:00:00','wxc',1,2,1),(111,'2020-06-17 00:00:00','wx',1,2,1),(112,'2020-06-17 00:00:00','da',1,2,1),(113,'2020-06-17 00:00:00','zeas',1,2,1),(114,'2020-06-17 00:00:00','d',1,2,1),(115,'2020-06-17 00:00:00','qsd',2,1,1),(116,'2020-06-17 00:00:00','wxc',2,1,1),(117,'2020-06-17 00:00:00','wxc',2,1,1),(118,'2020-06-17 00:00:00','dzedezdzedez',2,1,1),(119,'2020-06-17 00:00:00','dezdezd',2,1,1),(120,'2020-06-17 00:00:00','toto',2,1,1),(121,'2020-06-17 00:00:00','test',1,2,1),(122,'2020-06-17 00:00:00','encore',2,1,1),(123,'2020-06-18 00:00:00','Salut',1,2,1),(124,'2020-06-18 00:00:00','wesh',2,1,1),(125,'2020-06-18 00:00:00','dfgfh',1,2,1);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `played_games`
--

DROP TABLE IF EXISTS `played_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `played_games` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `started_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_at` datetime DEFAULT NULL,
  `state_number` int(11) NOT NULL DEFAULT '0',
  `game_state` text NOT NULL,
  `id_user_1` int(11) NOT NULL,
  `id_user_2` int(11) NOT NULL,
  `score_1` int(11) NOT NULL DEFAULT '0',
  `score_2` int(11) NOT NULL DEFAULT '0',
  `id_user_winner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_1` (`id_user_1`),
  KEY `id_user_2` (`id_user_2`),
  CONSTRAINT `played_games_ibfk_1` FOREIGN KEY (`id_user_1`) REFERENCES `users` (`id`),
  CONSTRAINT `played_games_ibfk_2` FOREIGN KEY (`id_user_2`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `played_games`
--

LOCK TABLES `played_games` WRITE;
/*!40000 ALTER TABLE `played_games` DISABLE KEYS */;
INSERT INTO `played_games` VALUES (61,'2020-06-17 16:29:02','2020-06-17 16:29:19',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\"XXX....\",\n		\"OOOO...\"\n	]\n}',2,1,3,4,1),(62,'2020-06-17 16:29:45','2020-06-17 16:29:58',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"X......\",\n		\"XO.....\",\n		\"XO.....\",\n		\"XO.....\"\n	]\n}',2,1,4,3,2),(63,'2020-06-17 16:38:00','2020-06-17 16:38:09',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\"XXX....\",\n		\"OOOO...\"\n	]\n}',1,2,3,4,2),(64,'2020-06-17 16:38:19','2020-06-17 16:38:30',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".O.....\",\n		\".O.....\",\n		\"XOX....\",\n		\"XOX....\"\n	]\n}',1,2,4,4,2),(65,'2020-06-17 16:38:39','2020-06-17 16:38:41',-2,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,2,0,0,2),(66,'2020-06-17 16:39:17','2020-06-17 16:39:19',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,2,0,0,2),(67,'2020-06-17 16:39:35','2020-06-17 16:39:37',-2,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,2,0,0,1),(68,'2020-06-17 16:39:46','2020-06-17 16:39:56',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"X......\",\n		\"X.....O\",\n		\"X.....O\",\n		\"X.....O\"\n	]\n}',2,1,4,3,2),(69,'2020-06-17 16:40:06','2020-06-17 16:40:15',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,2,0,0,1),(70,'2020-06-17 16:40:29',NULL,0,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,2,0,0,NULL),(71,'2020-06-17 16:40:43','2020-06-17 16:40:50',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"O......\",\n		\"O.....X\",\n		\"O.....X\",\n		\"O.....X\"\n	]\n}',1,2,3,4,2),(72,'2020-06-17 16:41:10',NULL,0,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',2,1,0,0,NULL),(73,'2020-06-17 16:41:23','2020-06-17 16:41:26',-2,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',2,1,0,0,2),(74,'2020-06-18 06:59:04','2020-06-18 06:59:25',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".X.....\",\n		\".XXO...\",\n		\".OOXO..\",\n		\".XOOX..\"\n	]\n}',12,1,6,6,12),(75,'2020-06-18 07:52:58','2020-06-18 07:53:09',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"...X...\",\n		\"...X.O.\",\n		\"...X.O.\",\n		\"..XXOO.\"\n	]\n}',12,1,5,4,12),(76,'2020-06-18 07:54:00','2020-06-18 07:54:32',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\"...OX..\",\n		\"...OX..\",\n		\"...OX..\"\n	]\n}',1,3,3,3,1),(77,'2020-06-18 07:55:55','2020-06-18 07:56:03',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"...O...\",\n		\"...OX..\",\n		\"...OX..\",\n		\"...OX..\"\n	]\n}',3,1,3,4,1),(78,'2020-06-18 07:56:19','2020-06-18 07:56:27',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\"OOOXXXX\"\n	]\n}',3,1,4,3,3),(79,'2020-06-18 07:56:38','2020-06-18 07:56:43',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,3,0,0,1),(80,'2020-06-18 07:59:37','2020-06-18 07:59:52',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\"XXXOOOO\"\n	]\n}',3,1,3,4,1),(81,'2020-06-18 08:00:07','2020-06-18 08:00:28',-2,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,3,0,0,3),(82,'2020-06-18 08:01:17','2020-06-18 08:03:36',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,1,0,0,1),(83,'2020-06-18 08:04:16','2020-06-18 08:06:41',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\"\n	]\n}',1,1,0,0,1),(84,'2020-06-18 09:29:56','2020-06-18 09:30:04',-2,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\"....O..\"\n	]\n}',1,2,0,1,2),(85,'2020-06-18 09:30:44','2020-06-18 09:30:53',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"..O....\",\n		\"..OX...\",\n		\"..OX...\",\n		\"..OX...\"\n	]\n}',1,2,3,4,2),(86,'2020-06-18 09:31:02','2020-06-18 09:31:11',-1,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"..O....\",\n		\"..OX...\",\n		\"..OX...\",\n		\"..OX...\"\n	]\n}',1,2,3,4,2),(87,'2020-06-18 15:06:29','2020-06-18 15:07:32',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"X......\",\n		\"OO.....\",\n		\"OO.....\",\n		\"XXXX...\"\n	]\n}',1,3,5,4,1),(88,'2020-06-18 15:20:50','2020-06-18 15:21:51',-2,'{\n	\"side_to_play\": \"YELLOW\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".......\",\n		\".X..O..\"\n	]\n}',1,2,1,1,1),(89,'2020-06-18 15:43:32','2020-06-18 15:43:50',-1,'{\n	\"side_to_play\": \"RED\",\n	\"grid\": [\n		\".......\",\n		\".......\",\n		\"..X....\",\n		\"..XO...\",\n		\"..XO...\",\n		\"..XO...\"\n	]\n}',1,2,4,3,1);
/*!40000 ALTER TABLE `played_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_name` varchar(10) NOT NULL,
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('ADMIN'),('USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `enabled` char(1) NOT NULL,
  `role_name` varchar(10) NOT NULL,
  `image_url` varchar(2083) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `role_name` (`role_name`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_name`) REFERENCES `roles` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'buze','buze','buze','Y','USER','https://i.imgur.com/PTBhbD2.png','2020-06-11 22:18:20'),(2,'dorian','dorian','dorian','Y','USER','https://i.imgur.com/U8ekCQP.jpg','2020-06-11 22:18:42'),(3,'Balkanysama','n','n','Y','ADMIN','https://file1.closermag.fr/var/closermag/storage/images/bio-people/biographie-patrick-balkany-529663/4697057-1-fre-FR/Patrick-Balkany.jpg?alias=exact1024x768_l','2020-06-11 22:18:49'),(4,'m','m','m','Y','USER','https://imgur.com/download/ljrctP0','2020-06-11 22:18:52'),(5,'ok','ok','ok','Y','USER','https://imgur.com/download/ljrctP0','2020-06-15 08:27:51'),(6,'Testmail','blabla','test','Y','USER','https://imgur.com/download/ljrctP0','2020-06-15 08:45:03'),(7,'testmail2','pasunmail','test','Y','USER','https://imgur.com/download/ljrctP0','2020-06-15 08:47:37'),(8,'testmdp','test@jspquoi.ah','mdp13','Y','USER','https://imgur.com/download/ljrctP0','2020-06-15 09:44:18'),(9,'testusername','test@test.test','Test1','Y','USER','https://imgur.com/download/ljrctP0','2020-06-15 09:48:50'),(10,'lol','lol','lol','Y','USER','https://imgur.com/download/ljrctP0','2020-06-16 12:39:56'),(11,'xavier','xavier.lemaire.28@gmail.com','Test1','Y','USER','https://imgur.com/download/ljrctP0','2020-06-17 14:50:55'),(12,'kevin','kevin@gmail.com','Kevin1','Y','USER','https://www.outdoyo.com/media/filer_public/4e/1e/4e1e4cf4-fade-4738-a46a-b1f8202ad22b/2870f606-c307-403a-a848-2e78a3f24af9.jpeg','2020-06-17 16:23:06'),(13,'Testa','Testa@gmail.com','Testa1','Y','USER','https://upload.wikimedia.org/wikipedia/commons/9/9a/Gull_portrait_ca_usa.jpg','2020-06-18 11:12:29'),(14,'Xavier2','zigzag.28@hotmail.fr','Test1','Y','USER','https://imgur.com/download/ljrctP0','2020-06-18 14:14:54'),(15,'macron','macron@demission.com','degage18D','Y','USER','https://picsum.photos/200','2020-06-18 14:15:37'),(16,'totoro','totoro@japan.jp','totoroT123','Y','USER','https://api.adorable.io/avatars/174/totoro@japan.jp.png','2020-06-18 14:18:34');
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

-- Dump completed on 2020-06-18 18:07:14
