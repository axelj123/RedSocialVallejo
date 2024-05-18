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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,NULL,1,NULL,'¿Cómo hacer algo?','Detalles de la pregunta...','2024-05-16 00:41:34'),(2,2,1,2,NULL,NULL,'Respuesta a la pregunta...','2024-05-16 00:41:44'),(3,2,NULL,1,NULL,'¿Cuál es la mejor manera de preparar un currículum para un trabajo de diseño gráfico?','Estoy buscando consejos específicos para diseñadores gráficos sobre cómo destacar mi trabajo y habilidades en mi currículum.','2024-05-16 21:11:17'),(4,3,NULL,1,NULL,'¿Qué habilidades son más valoradas en la industria de la tecnología actualmente?','Estoy considerando cambiar de carrera a la tecnología y quiero saber qué habilidades son más demandadas y valoradas en la industria en este momento.','2024-05-16 21:11:17'),(5,4,NULL,1,NULL,'¿Cuáles son los mejores recursos para aprender marketing digital?','Estoy interesado en aprender marketing digital y estoy buscando recomendaciones de libros, cursos en línea o cualquier otro recurso útil.','2024-05-16 21:11:17'),(6,5,NULL,1,NULL,'¿Cómo puedo mejorar mis habilidades de comunicación interpersonal?','Quiero mejorar mis habilidades de comunicación para ser más efectivo en mi vida personal y profesional. ¿Alguien tiene consejos prácticos?','2024-05-16 21:11:17'),(7,6,NULL,1,NULL,'¿Cuál es la mejor manera de aprender a programar en C++?','Estoy interesado en aprender C++ y estoy buscando recomendaciones sobre los mejores recursos y enfoques de aprendizaje.','2024-05-16 21:11:17'),(8,7,NULL,1,NULL,'¿Cómo puedo mejorar mi creatividad?','Me gustaría ser más creativo en mi trabajo y en mi vida diaria. ¿Alguien tiene consejos o ejercicios para mejorar la creatividad?','2024-05-16 21:11:17'),(9,8,NULL,1,NULL,'¿Cuál es la mejor manera de aprender a tocar la guitarra eléctrica?','Estoy interesado en aprender a tocar la guitarra eléctrica y estoy buscando recursos y consejos para principiantes.','2024-05-16 21:11:17'),(10,9,NULL,1,NULL,'¿Cómo puedo mejorar mi capacidad de concentración durante largos períodos de tiempo?','Tengo dificultades para mantenerme concentrado durante largos períodos de tiempo y quiero mejorar esta habilidad. ¿Alguien tiene consejos?','2024-05-16 21:11:17'),(11,10,NULL,1,NULL,'¿Cuál es la mejor manera de aprender a nadar para adultos?','Quiero aprender a nadar pero nunca he tenido la oportunidad de aprender. ¿Alguien tiene consejos para adultos principiantes?','2024-05-16 21:11:17'),(12,11,NULL,1,NULL,'¿Cómo puedo mejorar mi gestión del tiempo?','Siento que no estoy utilizando mi tiempo de manera efectiva y quiero mejorar mi capacidad para administrar mi tiempo de manera más eficiente. ¿Alguien tiene consejos?','2024-05-16 21:11:17'),(13,12,NULL,1,NULL,'¿Qué consejos tienen para mantener un estilo de vida saludable?','Quiero mejorar mi salud y bienestar general y estoy buscando consejos sobre cómo llevar un estilo de vida más saludable.','2024-05-16 21:11:17'),(14,13,NULL,1,NULL,'¿Cómo puedo mejorar mi capacidad de aprendizaje?','Quiero ser un aprendiz más efectivo y quiero conocer técnicas y estrategias para mejorar mi capacidad de aprendizaje.','2024-05-16 21:11:17'),(15,14,NULL,1,NULL,'¿Cuáles son los mejores recursos para aprender fotografía de paisajes?','Estoy interesado en la fotografía de paisajes y estoy buscando recursos y consejos para mejorar mis habilidades en esta área.','2024-05-16 21:11:17'),(16,15,NULL,1,NULL,'¿Cómo puedo aprender a tocar la batería sin tomar clases?','Estoy interesado en aprender a tocar la batería pero no tengo acceso a clases. ¿Alguien tiene consejos sobre cómo puedo aprender por mi cuenta?','2024-05-16 21:11:17');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Pedro Sánchez','pedro.sanchez@example.com','miContraseña123','Estudiante de ingeniería eléctrica con interés en energías renovables.','Ingeniería Eléctrica','1998-04-20'),(2,'Luisa Gómez','luisa.gomez@example.com','contraseña456','Apasionada por la psicología infantil y el desarrollo cognitivo.','Psicología','1993-10-15'),(3,'Javier Ruiz','javier.ruiz@example.com','claveSegura789','Estudiante de derecho con enfoque en derecho internacional y derechos humanos.','Derecho','1997-07-03'),(4,'Elena Vázquez','elena.vazquez@example.com','secreto1234','Futura educadora con experiencia en educación inclusiva y diversidad.','Educación Primaria','1995-01-28'),(5,'Miguel Torres','miguel.torres@example.com','miclave123','Estudiante de ingeniería civil con pasión por la construcción sostenible.','Ingeniería Civil','1994-12-10'),(6,'Carmen García','carmen.garcia@example.com','contraseña789','Interesada en el arte digital y la animación 3D.','Artes & Diseño Gráfico Empresarial','1996-08-05'),(7,'Andrés Martínez','andres.martinez@example.com','mipassword123','Estudiante de economía con enfoque en políticas públicas y desarrollo económico.','Economía','1992-06-22'),(8,'Sofía Rodríguez','sofia.rodriguez@example.com','secreto456','Apasionada por la literatura clásica y el análisis literario.','Literatura','1991-03-14'),(9,'Pablo López','pablo.lopez@example.com','micontraseña456','Estudiante de ciencias de la comunicación con interés en periodismo digital.','Ciencias de la Comunicación','1999-11-18'),(10,'Ana Torres','ana.torres@example.com','clave123','Interesada en la gestión pública y la administración eficiente de recursos.','Gestión Pública','1990-05-30'),(11,'Diego Martín','diego.martin@example.com','contraseña7890','Futuro ingeniero agrónomo con pasión por la agricultura orgánica.','Ingeniería Agroindustrial','1993-09-07'),(12,'Isabel Pérez','isabel.perez@example.com','miclave789','Estudiante de educación física con especialización en deportes adaptados.','Ciencias del Deporte','1996-02-16'),(13,'Lucía Gutiérrez','lucia.gutierrez@example.com','mipass7890','Apasionada por la historia del arte y la conservación del patrimonio cultural.','Historia del Arte','1998-07-12'),(14,'Gabriel Serrano','gabriel.serrano@example.com','contraseña12345','Estudiante de tecnología médica con interés en radiología diagnóstica.','Tecnología Médica','1994-04-24'),(15,'Eva Jiménez','eva.jimenez@example.com','miclave7890','Interesada en la traducción literaria y la interpretación simultánea.','Traducción e Interpretación','1997-12-09');
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

-- Dump completed on 2024-05-18 12:46:45
