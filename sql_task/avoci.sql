-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: avoci
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `LIKE_ID` int NOT NULL AUTO_INCREMENT,
  `POST_ID` int DEFAULT NULL,
  `USER_ID` int DEFAULT NULL,
  PRIMARY KEY (`LIKE_ID`),
  UNIQUE KEY `LIKE_ID_UNIQUE` (`LIKE_ID`),
  KEY `fk_like_post_idx` (`POST_ID`),
  KEY `fk_like_user_idx` (`USER_ID`),
  CONSTRAINT `fk_like_post` FOREIGN KEY (`POST_ID`) REFERENCES `posts` (`POST_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_like_user` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (1,1,3),(2,1,2),(3,4,3),(4,7,1),(5,8,2),(6,8,3),(7,9,3),(8,10,2),(9,11,2);
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_photos`
--

DROP TABLE IF EXISTS `post_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_photos` (
  `POST_PHOTO_ID` int NOT NULL AUTO_INCREMENT,
  `POST_ID` int NOT NULL,
  `PHOTO_LINK` varchar(256) NOT NULL,
  PRIMARY KEY (`POST_PHOTO_ID`),
  KEY `fk_photos_post_idx` (`POST_ID`),
  CONSTRAINT `fk_photos_post` FOREIGN KEY (`POST_ID`) REFERENCES `posts` (`POST_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_photos`
--

LOCK TABLES `post_photos` WRITE;
/*!40000 ALTER TABLE `post_photos` DISABLE KEYS */;
INSERT INTO `post_photos` VALUES (1,2,'https://photoresources.wtatennis.com/photo-resources/2019/10/11/7a27a11e-dd1b-41f5-8090-7cb23dfd5e3b/AKbPaNPM.jpg?width=1440&height=797'),(2,10,'https://vignette.wikia.nocookie.net/gerontology/images/e/e7/Nabi_Tajima_115.jpg/revision/latest/scale-to-width-down/340?cb=20151006211618'),(3,10,'https://s.yimg.com/ny/api/res/1.2/vEgFYoSvYh2UVwB4uT7RFA--~A/YXBwaWQ9aGlnaGxhbmRlcjtzbT0xO3c9ODAw/http://media.zenfs.com/en-AU/homerun/y7.ap/94f465c3cd03d45ea74f8098563e376f'),(4,6,'https://i.etsystatic.com/10344186/d/il/ffb465/1841324330/il_340x270.1841324330_7qkx.jpg?version=0');
/*!40000 ALTER TABLE `post_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `POST_ID` int NOT NULL AUTO_INCREMENT,
  `USER_ID` int DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `CREATED_AT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`POST_ID`),
  UNIQUE KEY `POST_ID_UNIQUE` (`POST_ID`),
  KEY `USER_ID_idx` (`USER_ID`),
  CONSTRAINT `fk_post_user` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,'Malcolm X is a famous US civil rights leader. 55 years ago, someone shoots him in New York City, USA. Malcolm X is born Malcolm Little in 1925. He changes his name after he changes his religion to Islam.','2020-02-01 01:29:00'),(2,2,'Maria Sharapova is a tennis player. She comes from Russia. She wins an important match with the best tennis player in the world. That player is Serena Williams. Sharapova becomes very famous after this match. She is only 17 years old.','2020-02-07 14:00:00'),(3,3,'At the 2018 Winter Olympics, the competition and the entertainment was heating up! Some fire artists held a special show on a beach of a coastal city that is helping with the Pyeongchang Games in South Korea. Visitors braved the cold outside to watch the sparks fly in the show. The fire artists came with other artists to celebrate the Olympics. ','2020-01-05 05:30:00'),(4,2,'We\'ve all heard the saying â€˜painting on a smile\'. Well one Japanese artist has taken that phrase to a whole new level. Nobumichi Asai has found a way to project and map expressions onto people\'s faces, effectively creating a facial mask. He and his team displayed their three-dimensional facial projection mapping system to a select group of spectators in Tokyo! ','2020-01-10 21:21:00'),(5,3,'Amazon is working on rules about technology which can recognize a person\'s face. Amazon\'s CEO, Jeff Bezos, says that his team is working on these rules that he wants to present to Congress. Bezos believes that facial recognition has many benefits, but there is also danger that it can be abused. ','2020-01-15 07:30:00'),(6,1,'One farmer from Kansas, USA decided to create cow art. He used his cattle, a feed truck and drones to create a huge smiley face out of cows. If that is not enough to make you smile, the farmer and artist is also known for using his trombone to herd the cows, saying that they have happy cows in Kansas. ','2020-01-20 05:56:00'),(7,3,'In recent years, Australia\'s south west state of Victoria saw a rapid increase of koalas, meaning that the animals have been literally eating themselves out of house and home by destroying gum trees. Their binging and booming population eventually caused them to starve. Environmentalists have moved 500 koalas over the past year, and their objective now is to control them and keep their numbers down. They gave the females contraceptive implants and then released them into other forests. ','2020-01-25 17:30:00'),(8,1,'More than 71 people are dead, 17 missing, and more than 40,000 were displaced after a massive storm struck the Philippines. One of the country\'s officials said that the storm was not strong enough to be rated as a typhoon, so many people were unprepared. Dozens of them died in landslides and flooding caused by the heavy rains. The country already suffered a number of deadly storms recently - Super Typhoon Mangkhut killed more than 80 people in September and Typhoon Yutu killed 17 more. ','2020-01-27 06:36:00'),(9,2,'Two Australian police officers were out in the ocean, and they were conducting safety and compliance checks when a great white shark suddenly swam up to their small boat. Luckily, the heart-stopping meeting ended quickly when the shark swam away. ','2020-01-29 11:00:00'),(10,3,'Nabi Tajima was until recently the oldest person in the world. She was born in August 1900, which made her the last person born in the 19th century. She passed away in a hospital in southern Japan at the age of 117. She had more than 160 descendants, including great-great-great grandchildren. Mrs Tajima was the 3rd oldest person in recorded human history. ','2020-02-01 14:45:00'),(11,1,'Simple TextSimple TextSimple TextSimple TextSimple Text ','2020-01-01 01:25:00');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `TAG_ID` int NOT NULL AUTO_INCREMENT,
  `POST_ID` int NOT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TAG_ID`),
  UNIQUE KEY `TAG_ID_UNIQUE` (`TAG_ID`),
  KEY `fk_tag_post_idx` (`POST_ID`),
  CONSTRAINT `fk_tag_post` FOREIGN KEY (`POST_ID`) REFERENCES `posts` (`POST_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,1,'#NY'),(2,2,'#tennis'),(3,3,'#South_Korea'),(4,3,'#Olympics'),(5,3,'#Interesting'),(6,4,'#Japan'),(7,5,'#Amazon'),(8,5,'#facial_recognition'),(9,5,'#Interesting'),(10,6,'#cow'),(11,6,'#art'),(12,7,'#Australia'),(13,7,'#koalas'),(14,8,'#typhoon'),(15,9,'#shark'),(16,10,'#interesting'),(17,10,'#Japan'),(18,11,'#Nasdad');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `USER_ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) DEFAULT NULL,
  `PHOTO_LINK` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `id_UNIQUE` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'@prog_mid','resources/img/comp.jpg'),(2,'@milky_way','resources/img/planet.jpg'),(3,'@asenyarb','resources/img/asenya.png');
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

-- Dump completed on 2020-05-12 17:01:26
