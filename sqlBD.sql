CREATE DATABASE  IF NOT EXISTS `conexionvallejo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `conexionvallejo`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: conexionvallejo
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `carreras`
--

DROP TABLE IF EXISTS `carreras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carreras` (
  `id` int NOT NULL AUTO_INCREMENT,
  `carrera` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carreras`
--

LOCK TABLES `carreras` WRITE;
/*!40000 ALTER TABLE `carreras` DISABLE KEYS */;
INSERT INTO `carreras` VALUES (1,'Ingeniería de Ciberseguridad'),(2,'Ingeniería de Ciencia de Datos'),(3,'Ingeniería Empresarial'),(4,'Ingeniería Agroindustrial'),(5,'Ingeniería Ambiental'),(6,'Ingeniería Civil'),(7,'Ingeniería de Minas'),(8,'Ingeniería de Sistemas'),(9,'Ingeniería Industrial'),(10,'Ingeniería Mecánica Eléctrica'),(11,'Enfermería'),(12,'Estomatología'),(13,'Medicina'),(14,'Nutrición'),(15,'Psicología'),(16,'Tecnología Médica'),(17,'Administración y Marketing'),(18,'Administración y Negocios Internacionales'),(19,'Administración'),(20,'Administración en Turismo y Hotelería'),(21,'Contabilidad'),(22,'Economía'),(23,'Gestión Pública'),(24,'Artes & Diseño Gráfico Empresarial'),(25,'Ciencias de la Comunicación'),(26,'Ciencias del Deporte'),(27,'Derecho'),(28,'Educación Inicial'),(29,'Educación Primaria'),(30,'Traducción e Interpretación'),(31,'Arquitectura');
/*!40000 ALTER TABLE `carreras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciclo`
--

DROP TABLE IF EXISTS `ciclo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ciclo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ciclo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciclo`
--

LOCK TABLES `ciclo` WRITE;
/*!40000 ALTER TABLE `ciclo` DISABLE KEYS */;
INSERT INTO `ciclo` VALUES (1,'I'),(2,'II'),(3,'III'),(4,'IV'),(5,'V'),(6,'VI'),(7,'VII'),(8,'VIII'),(9,'IX'),(10,'X');
/*!40000 ALTER TABLE `ciclo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by_user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `comment_text` text,
  `created_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `created_by_user_id` (`created_by_user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`created_by_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by_user_id` int DEFAULT NULL,
  `parent_question_id` int DEFAULT NULL,
  `post_type_id` int DEFAULT NULL,
  `accepted_answer_id` int DEFAULT NULL,
  `post_title` varchar(255) DEFAULT NULL,
  `post_details` text,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `posts_ibfk_1_idx` (`created_by_user_id`),
  KEY `posts_ibfk_2_idx` (`parent_question_id`),
  KEY `posts_ibfk_3_idx` (`post_type_id`),
  KEY `posts_ibfk_4_idx` (`accepted_answer_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`created_by_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`parent_question_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `posts_ibfk_3` FOREIGN KEY (`post_type_id`) REFERENCES `posttypes` (`id`),
  CONSTRAINT `posts_ibfk_4` FOREIGN KEY (`accepted_answer_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (100,27,NULL,1,NULL,'Cómo implementar un algoritmo de búsqueda binaria en Python','<p><span style=\"font-family:\'Courier New\', Courier, monospace;\">Estoy tratando de implementar un algoritmo de búsqueda binaria en Python. He leído sobre cómo funciona el algoritmo, pero tengo dificultades para traducir la lógica a código. A continuación, incluyo mi código actual y una descripción de los problemas que estoy encontrando:</span></p><p>&nbsp;</p><p>&nbsp;</p><pre><code class=\"language-plaintext\">def busqueda_binaria(lista, objetivo):\r\n    inicio = 0\r\n    fin = len(lista) - 1\r\n\r\n    while inicio &lt;= fin:\r\n        medio = (inicio + fin) // 2\r\n        if lista[medio] == objetivo:\r\n            return medio\r\n        elif lista[medio] &lt; objetivo:\r\n            inicio = medio + 1\r\n        else:\r\n            fin = medio - 1\r\n\r\n    return -1\r\n\r\n# Prueba de la función\r\nlista = [1, 3, 5, 7, 9, 11]\r\nobjetivo = 7\r\nresultado = busqueda_binaria(lista, objetivo)\r\nprint(f\'Índice del objetivo: {resultado}\')\r\n</code></pre>','2024-06-12 23:25:13'),(112,19,NULL,1,NULL,'Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.','<ol style=\"-webkit-text-stroke-width:0px;background-color:rgb(240, 244, 252);box-sizing:border-box;color:rgb(0, 0, 0);font-family:Poppins, sans-serif;font-size:14px;font-style:normal;font-variant-caps:normal;font-variant-ligatures:normal;font-weight:400;letter-spacing:normal;list-style:none;margin-bottom:0px;margin-right:0px;margin-top:0px;orphans:2;padding:0px;text-align:start;text-decoration:none;text-indent:0px;text-transform:none;white-space:normal;widows:2;word-spacing:0px;\"><li style=\"box-sizing:border-box;font-family:Poppins, sans-serif;list-style:none;margin-bottom:0px;margin-right:0px;margin-top:0px;padding:0px;text-decoration:none;\"><p style=\"margin-left:0px;\">Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.</p></li></ol><p><br>Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.</p><p><br>Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.</p><p><br>&nbsp;</p>','2024-06-15 03:25:09'),(121,19,100,2,NULL,NULL,'<p>ssasdsasdasadsadssadsadsadsadssad</p>\r\n','2024-06-15 03:43:48'),(124,18,100,2,NULL,NULL,'<p>sadsasadsadsad</p>\r\n','2024-06-15 03:54:45'),(127,18,112,2,NULL,NULL,'<p>JAJAJA CLARO</p>\r\n','2024-06-15 04:06:50'),(128,19,100,2,NULL,NULL,'<p>ssasadsadsadsa</p>\r\n','2024-06-15 04:22:09'),(129,18,NULL,1,NULL,'Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.','<ol style=\"-webkit-text-stroke-width:0px;background-color:rgb(240, 244, 252);box-sizing:border-box;color:rgb(0, 0, 0);font-family:Poppins, sans-serif;font-size:14px;font-style:normal;font-variant-caps:normal;font-variant-ligatures:normal;font-weight:400;letter-spacing:normal;list-style:none;margin-bottom:0px;margin-right:0px;margin-top:0px;orphans:2;padding:0px;text-align:start;text-decoration:none;text-indent:0px;text-transform:none;white-space:normal;widows:2;word-spacing:0px;\"><li style=\"box-sizing:border-box;font-family:Poppins, sans-serif;list-style:none;margin-bottom:0px;margin-right:0px;margin-top:0px;padding:0px;text-decoration:none;\"><p style=\"margin-left:0px;\">Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.</p></li></ol><p><br>Agregue \"etiquetas\" que ayuden a hacer llegar su pregunta a los miembros de la comunidad.</p><p><br>&nbsp;</p>','2024-06-15 05:05:21');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posttag`
--

DROP TABLE IF EXISTS `posttag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posttag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7dh8j7x0lb14vgm6yl4l6cqwm` (`tag_id`),
  KEY `FKk8ao1sog3ndkaa2is60c3gnjk` (`post_id`),
  CONSTRAINT `FK7dh8j7x0lb14vgm6yl4l6cqwm` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `FKk8ao1sog3ndkaa2is60c3gnjk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posttag`
--

LOCK TABLES `posttag` WRITE;
/*!40000 ALTER TABLE `posttag` DISABLE KEYS */;
INSERT INTO `posttag` VALUES (4,100,1),(35,112,17),(36,112,5),(37,112,21),(38,129,18),(39,129,21),(40,129,25);
/*!40000 ALTER TABLE `posttag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posttypes`
--

DROP TABLE IF EXISTS `posttypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posttypes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posttypes`
--

LOCK TABLES `posttypes` WRITE;
/*!40000 ALTER TABLE `posttypes` DISABLE KEYS */;
INSERT INTO `posttypes` VALUES (1,'Pregunta'),(2,'Respuesta');
/*!40000 ALTER TABLE `posttypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'ROLE_USER'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savedposts`
--

DROP TABLE IF EXISTS `savedposts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savedposts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_post` (`user_id`,`post_id`),
  KEY `fk_saved_posts_post_id` (`post_id`),
  CONSTRAINT `fk_saved_posts_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `fk_saved_posts_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savedposts`
--

LOCK TABLES `savedposts` WRITE;
/*!40000 ALTER TABLE `savedposts` DISABLE KEYS */;
INSERT INTO `savedposts` VALUES (11,18,100),(14,18,112),(12,19,100);
/*!40000 ALTER TABLE `savedposts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) DEFAULT NULL,
  `tag_description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'informatica','Etiqueta relacionada con temas de informática y tecnología de la información.'),(2,'desarrollo-web','Etiqueta relacionada con el desarrollo de aplicaciones web.'),(3,'inteligencia-artificial','Etiqueta relacionada con temas de inteligencia artificial y aprendizaje automático.'),(4,'medicina','Etiqueta relacionada con temas de medicina y salud.'),(5,'biologia','Etiqueta relacionada con temas de biología y ciencias de la vida.'),(6,'fisica','Etiqueta relacionada con temas de física y ciencias naturales.'),(7,'matematicas','Etiqueta relacionada con temas de matemáticas y estadísticas.'),(8,'economia','Etiqueta relacionada con temas de economía y finanzas.'),(9,'psicologia','Etiqueta relacionada con temas de psicología y comportamiento humano.'),(10,'educacion','Etiqueta relacionada con temas de educación y pedagogía.'),(11,'programacion','Etiqueta relacionada con temas de programación y desarrollo de software.'),(12,'inteligencia-natural','Etiqueta relacionada con temas de inteligencia natural y ciencias cognitivas.'),(13,'robotica','Etiqueta relacionada con temas de robótica y automatización.'),(14,'criptografia','Etiqueta relacionada con temas de criptografía y seguridad informática.'),(15,'big-data','Etiqueta relacionada con temas de análisis de datos y big data.'),(16,'redes','Etiqueta relacionada con temas de redes de computadoras y comunicaciones.'),(17,'ciberseguridad','Etiqueta relacionada con temas de ciberseguridad y protección de datos.'),(18,'blockchain','Etiqueta relacionada con temas de tecnología blockchain y criptomonedas.'),(19,'realidad-aumentada','Etiqueta relacionada con temas de realidad aumentada y realidad virtual.'),(20,'ciencia-datos','Etiqueta relacionada con temas de ciencia de datos y análisis estadístico.'),(21,'bioinformatica','Etiqueta relacionada con temas de bioinformática y análisis genómico.'),(22,'ingenieria','Etiqueta relacionada con temas de ingeniería en general.'),(23,'diseño','Etiqueta relacionada con temas de diseño gráfico y diseño de experiencias de usuario.'),(24,'marketing','Etiqueta relacionada con temas de marketing digital y estrategias de publicidad.'),(25,'finanzas','Etiqueta relacionada con temas de finanzas personales y empresariales.'),(26,'arte','Etiqueta relacionada con temas de arte y cultura.'),(27,'literatura','Etiqueta relacionada con temas de literatura y escritura creativa.'),(28,'historia','Etiqueta relacionada con temas de historia y ciencias sociales.'),(29,'filosofia','Etiqueta relacionada con temas de filosofía y pensamiento crítico.'),(30,'religion','Etiqueta relacionada con temas de religión y espiritualidad.');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) DEFAULT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `about_me` text,
  `carrera` varchar(255) DEFAULT NULL,
  `nacimiento` datetime(6) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `facebook_url` varchar(255) DEFAULT NULL,
  `linkedin_url` varchar(255) DEFAULT NULL,
  `instagram_url` varchar(255) DEFAULT NULL,
  `tiktok_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email_address` (`email_address`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (18,'Axel Jhosmell','axeljhosmell13@gmail.com','$2a$10$MWlA.yoOlfWl9N6oPDeNquBgfB55/0CLwzYWB2myQUuyPzW8DCu0m','\"Soy Axel Jhosmell Muñoz Silva, Ingeniero de Sistemas apasionado por la tecnología y la innovación. Siempre en búsqueda de nuevos desafíos y oportunidades para aprender y crecer. ¡Listo para conectar y compartir conocimientos!\"','ingenieria de chistemas',NULL,'/uploads/MarkZuckerberg.jpg','https://www.facebook.com/axel.jhosmell','https://www.linkedin.com/in/axeljhosmell','https://www.instagram.com/axeljhosmell','https://www.tiktok.com/@axeljhosmell'),(19,'Pedro Cano','Maria13@gmail.com','$2a$10$QIl7eM7reKvAqjhSrjQuy.h9SPBQ54ZyEgtanuxtUx/jTOc17y3H6','soy ingeniero','ingenieria de sistemas','2003-08-04 00:00:00.000000','/uploads/D_NQ_NP_895537-MLM48940213120_012022-O.jpg',NULL,NULL,NULL,NULL),(24,'María López','maria.lopez@example.com','$2a$10$cFtyp6SSGWCbJg/benll5eo0SFZSVyHrlMvg8W7HNQcI8mgv0OY6i','¡Hola! Me llamo María y me encanta la fotografía.','Comunicación Social','1988-09-19 00:00:00.000000','/uploads/images (1).jpeg',NULL,NULL,NULL,NULL),(25,'Juan Pérez','juan.perez@example.com','$2a$10$9KTC3lLrjPNRqOJe1Gk2xOKgAV.T/.VX.VrE0pcckvf0j0dximHA.','Saludos, soy Juan y me gusta el deporte y la lectura.','Educación Física','1995-03-09 00:00:00.000000','/uploads/Alex_turner_2023_4k_background.jpg',NULL,NULL,NULL,NULL),(26,'Laura Martínez','laura.martinez@example.com','$2a$10$eQT5.tGI1E4vVTF8f6DiveggbOIHDA.dR.xt4C9Bs6AyCKMwJV50q','Hola a todos, soy Laura. Amo viajar y descubrir nuevas culturas.','Turismo','1992-11-27 00:00:00.000000','/uploads/images (2).jpeg',NULL,NULL,NULL,NULL),(27,'Carlos Rodríguez','carlos.rodriguez@example.com','$2a$10$nNr7gsowTpoWXTIgq5CAEeJsk2gp.wWY4LplPNV1mibyNTkw60cCC','¡Hola! Soy Carlos y me apasiona la música y la tecnología.','Música','1987-07-04 00:00:00.000000','/uploads/images (3).jpeg',NULL,NULL,NULL,NULL),(28,'Ana García','ana.garcia@example.com','$2a$10$9P7V795CNdQeGp9dqQ05YOgW9gClM5mZPZ59piFWjq7pcoC6yqojS','¡Hola! Me llamo Ana y soy una entusiasta de los libros y el arte.','Historia del Arte','1993-02-13 00:00:00.000000','/uploads/480x624-eza-vip-la-belleza-segun-ana-garcia-sineriz-6906637-2-esl-es-la-belleza-segun-ana-garcia-sineriz-jpg.jpg',NULL,NULL,NULL,NULL),(29,'David Fernández','david.fernandez@example.com','$2a$10$NAd2H8JAmeKN.LdIAhic/OqT6hXpVSCzgjLLZg4PgysXV.nZUKZ26','Saludos, soy David y disfruto mucho del cine y la cocina.','Gastronomía','1991-06-29 00:00:00.000000','/uploads/images (4).jpeg',NULL,NULL,NULL,NULL),(30,'Sofía Morales','sofia.morales@example.com','$2a$10$LwOlwR0ZNI4BvghBXPWsjexE3zkBGk8yO0Vymxl52eiKE9wElHvHW','¡Hola! Me llamo Sofía y me encanta el senderismo y la naturaleza.','Biología','1994-08-11 00:00:00.000000','/uploads/images (5).jpeg',NULL,NULL,NULL,NULL),(31,'Pedro López','pedro.lopez@example.com','$2a$10$ZWhOB9fEt5up1xTvliTIs.dyWM9BKOGDAVDZYIESH64QMRGUGN.Iy','Hola, soy Pedro. Me interesan mucho las finanzas y la economía.','Economía','1989-04-24 00:00:00.000000','/uploads/images (6).jpeg',NULL,NULL,NULL,NULL),(32,'Luisa Ramírez','luisa.ramirez@example.com','$2a$10$GGYpDP69W/.wcNvZwa1pzuUpCB4i7pmU2xW8jxZ4OOB81mQFvAnVK','¡Hola! Soy Luisa y disfruto del yoga y la meditación.','Psicología','1996-01-07 00:00:00.000000','/uploads/DYITcAvWAAAege9.jpg',NULL,NULL,NULL,NULL),(33,'Eduardo Gómez','eduardo.gomez@example.com','$2a$10$Eq5Vq7lnMHTClbFVmbCzaeroP7ERmdXwzdrnKpjV7LY34UNJTWjwu','¡Hola! Soy Eduardo y me gusta la programación y los videojuegos.','Ingeniería en Sistemas','1990-07-17 00:00:00.000000','/uploads/50fb108b-3121-4b42-a712-a26e59a66cb0-1673411269000.png',NULL,NULL,NULL,NULL),(46,'Ciro castillo','eduardo.gomez@ucvvirtual.edu.pe','$2a$10$kCN052BdsOu/TExpkXQtCO2sDpzI5SGPlBFKzsceq0EXM.mo7A3t.','Hola, soy Pedro. Me interesan mucho las finanzas y la economía.','Economía','1989-04-23 00:00:00.000000',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vote_types`
--

DROP TABLE IF EXISTS `vote_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vote_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vote_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vote_types`
--

LOCK TABLES `vote_types` WRITE;
/*!40000 ALTER TABLE `vote_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `vote_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `votes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `vote_type_id` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  KEY `user_id` (`user_id`),
  KEY `vote_type_id` (`vote_type_id`),
  CONSTRAINT `votes_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `votes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `votes_ibfk_3` FOREIGN KEY (`vote_type_id`) REFERENCES `votetypes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votes`
--

LOCK TABLES `votes` WRITE;
/*!40000 ALTER TABLE `votes` DISABLE KEYS */;
/*!40000 ALTER TABLE `votes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votetypes`
--

DROP TABLE IF EXISTS `votetypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `votetypes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vote_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votetypes`
--

LOCK TABLES `votetypes` WRITE;
/*!40000 ALTER TABLE `votetypes` DISABLE KEYS */;
/*!40000 ALTER TABLE `votetypes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-15  9:24:51
