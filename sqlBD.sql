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
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by_user_id` int DEFAULT NULL,
  `post_id` int DEFAULT NULL,
  `comment_text` text,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (21,19,NULL,1,NULL,'¿Cómo optimizar una consulta SQL para bases de datos grandes?','Estoy trabajando con una base de datos muy grande y mis consultas SQL están tardando demasiado en ejecutarse. ¿Cuáles son algunas técnicas o estrategias para optimizar consultas SQL?','2024-05-25 06:49:01'),(22,19,NULL,1,NULL,'Mejores prácticas para la programación orientada a objetos en Java','Quiero mejorar mis habilidades en programación orientada a objetos utilizando Java. ¿Cuáles son las mejores prácticas y patrones de diseño que debo seguir?','2024-05-25 06:49:34'),(23,19,NULL,1,NULL,'Diferencias entre SQL y NoSQL: ¿Cuál debería usar?','Estoy desarrollando una aplicación y no estoy seguro de si debería usar una base de datos SQL o NoSQL. ¿Cuáles son las principales diferencias y en qué casos es mejor usar una sobre la otra?','2024-05-25 06:56:54'),(24,19,NULL,1,NULL,'Consejos para realizar un proyecto de fin de curso en Ingeniería Informática','Estoy empezando mi proyecto de fin de curso en Ingeniería Informática y me gustaría recibir consejos sobre cómo gestionarlo y asegurarme de que sea un éxito.','2024-05-25 06:57:05'),(25,19,NULL,1,NULL,'Cómo prepararse para entrevistas técnicas en empresas de tecnología','Pronto tendré entrevistas técnicas con algunas empresas de tecnología. ¿Qué tipo de preguntas debo esperar y cómo puedo prepararme de la mejor manera?','2024-05-25 06:58:29'),(26,19,NULL,1,NULL,'¿Qué lenguajes de programación debo aprender para el desarrollo web?','Estoy interesado en convertirme en un desarrollador web. ¿Qué lenguajes de programación y tecnologías debo aprender para tener una carrera exitosa en este campo?','2024-05-25 06:59:05'),(27,18,NULL,1,NULL,'Recomendaciones de libros y recursos para aprender algoritmos y estructuras de datos','Estoy buscando libros y recursos recomendados para aprender algoritmos y estructuras de datos en profundidad. ¿Cuáles son los mejores?','2024-05-25 07:23:17'),(28,18,NULL,1,NULL,'Experiencias y consejos sobre la vida universitaria en el extranjero','Estoy considerando estudiar en el extranjero y me gustaría saber más sobre las experiencias de otros estudiantes. ¿Qué debo tener en cuenta y cómo puedo adaptarme mejor?','2024-05-25 22:36:40'),(29,18,NULL,1,NULL,'¿Cómo implementar una cola de prioridad en Python?','Estoy tratando de implementar una cola de prioridad en Python para un proyecto. ¿Cuál es la mejor manera de hacerlo y qué bibliotecas debo considerar?','2024-05-25 13:30:00'),(30,19,NULL,1,NULL,'Consejos para manejar la carga de trabajo en el primer año de universidad','El primer año de universidad puede ser abrumador con tantas materias y tareas. ¿Cuáles son algunos consejos para manejar esta carga de trabajo de manera efectiva?','2024-05-25 13:35:00'),(31,24,NULL,1,NULL,'Mejores prácticas para la seguridad en aplicaciones web','Estoy desarrollando una aplicación web y quiero asegurarme de que sea segura. ¿Cuáles son las mejores prácticas para la seguridad en aplicaciones web?','2024-05-25 13:40:00'),(32,25,NULL,1,NULL,'¿Cómo prepararse para los exámenes finales en la universidad?','Los exámenes finales están a la vuelta de la esquina y quiero asegurarme de estar bien preparado. ¿Qué estrategias recomiendan para estudiar y rendir bien en los exámenes finales?','2024-05-25 13:45:00'),(33,26,NULL,1,NULL,'Diferencias entre machine learning supervisado y no supervisado','Estoy aprendiendo sobre machine learning y me gustaría entender mejor las diferencias entre los enfoques supervisado y no supervisado. ¿Cuáles son los principales usos y ventajas de cada uno?','2024-05-25 13:50:00'),(34,27,NULL,1,NULL,'Recomendaciones de cursos online para aprender desarrollo móvil','Quiero aprender a desarrollar aplicaciones móviles. ¿Cuáles son los mejores cursos online disponibles para aprender desarrollo móvil tanto para iOS como para Android?','2024-05-25 13:55:00'),(35,28,NULL,1,NULL,'¿Cómo equilibrar el estudio y la vida social en la universidad?','Encontrar un equilibrio entre el estudio y la vida social puede ser un desafío en la universidad. ¿Qué consejos tienen para gestionar el tiempo de manera efectiva sin descuidar ninguna de las dos áreas?','2024-05-25 14:00:00'),(36,29,NULL,1,NULL,'Buenas prácticas para el desarrollo colaborativo en Git','Estamos empezando un proyecto en equipo y vamos a usar Git para el control de versiones. ¿Cuáles son las buenas prácticas para trabajar de forma colaborativa y evitar conflictos?','2024-05-25 14:05:00'),(37,30,NULL,1,NULL,'Cómo mejorar el rendimiento de una aplicación React','Mi aplicación React está funcionando lentamente en el navegador. ¿Qué técnicas y herramientas puedo usar para mejorar su rendimiento?','2024-05-25 14:10:00'),(38,31,NULL,1,NULL,'Mejores estrategias para la resolución de problemas algorítmicos','Estoy preparándome para competiciones de programación y entrevistas técnicas. ¿Cuáles son las mejores estrategias para abordar y resolver problemas algorítmicos complejos?','2024-05-25 14:15:00'),(39,32,NULL,1,NULL,'Consejos para escribir un CV efectivo para trabajos en tecnología','Estoy buscando trabajo en el sector tecnológico y quiero que mi CV se destaque. ¿Cuáles son algunos consejos para escribir un CV efectivo y relevante para trabajos en tecnología?','2024-05-25 14:20:00'),(40,33,NULL,1,NULL,'Cómo elegir un proyecto de investigación en ciencias de la computación','Estoy en el proceso de elegir un proyecto de investigación para mi tesis en ciencias de la computación. ¿Qué factores debo considerar y cómo puedo asegurarme de que el proyecto sea viable y relevante?','2024-05-25 14:25:00');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posttags`
--

DROP TABLE IF EXISTS `posttags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posttags` (
  `post_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `posttags_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `posttags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posttags`
--

LOCK TABLES `posttags` WRITE;
/*!40000 ALTER TABLE `posttags` DISABLE KEYS */;
/*!40000 ALTER TABLE `posttags` ENABLE KEYS */;
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
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` int NOT NULL AUTO_INCREMENT,
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
  `nacimiento` date DEFAULT NULL,
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
INSERT INTO `users` VALUES (18,'Axel Jhosmell','axeljhosmell13@gmail.com','$2a$10$MWlA.yoOlfWl9N6oPDeNquBgfB55/0CLwzYWB2myQUuyPzW8DCu0m','\"Soy Axel Jhosmell Muñoz Silva, Ingeniero de Sistemas apasionado por la tecnología y la innovación. Siempre en búsqueda de nuevos desafíos y oportunidades para aprender y crecer. ¡Listo para conectar y compartir conocimientos!\"','ingenieria de sistemas',NULL,'/uploads/MarkZuckerberg.jpg','https://www.facebook.com/axel.jhosmell','https://www.linkedin.com/in/axeljhosmell','https://www.instagram.com/axeljhosmell','https://www.tiktok.com/@axeljhosmell'),(19,'Pedro Cano','Maria13@gmail.com','$2a$10$QIl7eM7reKvAqjhSrjQuy.h9SPBQ54ZyEgtanuxtUx/jTOc17y3H6','soy ingeniero','ingenieria de sistemas','2003-08-04','/uploads/D_NQ_NP_895537-MLM48940213120_012022-O.jpg',NULL,NULL,NULL,NULL),(24,'María López','maria.lopez@example.com','$2a$10$cFtyp6SSGWCbJg/benll5eo0SFZSVyHrlMvg8W7HNQcI8mgv0OY6i','¡Hola! Me llamo María y me encanta la fotografía.','Comunicación Social','1988-09-19','/uploads/images (1).jpeg',NULL,NULL,NULL,NULL),(25,'Juan Pérez','juan.perez@example.com','$2a$10$9KTC3lLrjPNRqOJe1Gk2xOKgAV.T/.VX.VrE0pcckvf0j0dximHA.','Saludos, soy Juan y me gusta el deporte y la lectura.','Educación Física','1995-03-09','/uploads/Alex_turner_2023_4k_background.jpg',NULL,NULL,NULL,NULL),(26,'Laura Martínez','laura.martinez@example.com','$2a$10$eQT5.tGI1E4vVTF8f6DiveggbOIHDA.dR.xt4C9Bs6AyCKMwJV50q','Hola a todos, soy Laura. Amo viajar y descubrir nuevas culturas.','Turismo','1992-11-27','/uploads/images (2).jpeg',NULL,NULL,NULL,NULL),(27,'Carlos Rodríguez','carlos.rodriguez@example.com','$2a$10$nNr7gsowTpoWXTIgq5CAEeJsk2gp.wWY4LplPNV1mibyNTkw60cCC','¡Hola! Soy Carlos y me apasiona la música y la tecnología.','Música','1987-07-04','/uploads/images (3).jpeg',NULL,NULL,NULL,NULL),(28,'Ana García','ana.garcia@example.com','$2a$10$9P7V795CNdQeGp9dqQ05YOgW9gClM5mZPZ59piFWjq7pcoC6yqojS','¡Hola! Me llamo Ana y soy una entusiasta de los libros y el arte.','Historia del Arte','1993-02-13','/uploads/480x624-eza-vip-la-belleza-segun-ana-garcia-sineriz-6906637-2-esl-es-la-belleza-segun-ana-garcia-sineriz-jpg.jpg',NULL,NULL,NULL,NULL),(29,'David Fernández','david.fernandez@example.com','$2a$10$NAd2H8JAmeKN.LdIAhic/OqT6hXpVSCzgjLLZg4PgysXV.nZUKZ26','Saludos, soy David y disfruto mucho del cine y la cocina.','Gastronomía','1991-06-29','/uploads/images (4).jpeg',NULL,NULL,NULL,NULL),(30,'Sofía Morales','sofia.morales@example.com','$2a$10$LwOlwR0ZNI4BvghBXPWsjexE3zkBGk8yO0Vymxl52eiKE9wElHvHW','¡Hola! Me llamo Sofía y me encanta el senderismo y la naturaleza.','Biología','1994-08-11','/uploads/images (5).jpeg',NULL,NULL,NULL,NULL),(31,'Pedro López','pedro.lopez@example.com','$2a$10$ZWhOB9fEt5up1xTvliTIs.dyWM9BKOGDAVDZYIESH64QMRGUGN.Iy','Hola, soy Pedro. Me interesan mucho las finanzas y la economía.','Economía','1989-04-24','/uploads/images (6).jpeg',NULL,NULL,NULL,NULL),(32,'Luisa Ramírez','luisa.ramirez@example.com','$2a$10$GGYpDP69W/.wcNvZwa1pzuUpCB4i7pmU2xW8jxZ4OOB81mQFvAnVK','¡Hola! Soy Luisa y disfruto del yoga y la meditación.','Psicología','1996-01-07','/uploads/DYITcAvWAAAege9.jpg',NULL,NULL,NULL,NULL),(33,'Eduardo Gómez','eduardo.gomez@example.com','$2a$10$Eq5Vq7lnMHTClbFVmbCzaeroP7ERmdXwzdrnKpjV7LY34UNJTWjwu','¡Hola! Soy Eduardo y me gusta la programación y los videojuegos.','Ingeniería en Sistemas','1990-07-17','/uploads/50fb108b-3121-4b42-a712-a26e59a66cb0-1673411269000.png',NULL,NULL,NULL,NULL),(46,'Ciro castillo','eduardo.gomez@ucvvirtual.edu.pe','$2a$10$kCN052BdsOu/TExpkXQtCO2sDpzI5SGPlBFKzsceq0EXM.mo7A3t.','Hola, soy Pedro. Me interesan mucho las finanzas y la economía.','Economía','1989-04-23',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votes`
--

DROP TABLE IF EXISTS `votes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `votes` (
  `id` int NOT NULL AUTO_INCREMENT,
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

-- Dump completed on 2024-05-28  0:32:36
