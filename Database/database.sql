-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gestor_escolar_test
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alumno`
--

DROP TABLE IF EXISTS `alumno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumno` (
  `id` int NOT NULL AUTO_INCREMENT,
  `matricula` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `curp` varchar(20) NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `curp_UNIQUE` (`curp`),
  UNIQUE KEY `matricula_UNIQUE` (`matricula`),
  KEY `fk_Alumno_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Alumno_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clase`
--

DROP TABLE IF EXISTS `clase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clase` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idEmpleado` int NOT NULL,
  `idMateria` int NOT NULL,
  `idGrupo` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Clase_Empleado1_idx` (`idEmpleado`),
  KEY `fk_Clase_Grupo1_idx` (`idGrupo`),
  KEY `fk_Clase_Materia1_idx` (`idMateria`),
  CONSTRAINT `fk_Clase_Empleado1` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`id`),
  CONSTRAINT `fk_Clase_Grupo1` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`id`),
  CONSTRAINT `fk_Clase_Materia1` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `edificio`
--

DROP TABLE IF EXISTS `edificio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `edificio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  UNIQUE KEY `estatus_UNIQUE` (`estatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `noPersonal` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `estatus` tinyint(1) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `idUsuario` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `noPersonal_UNIQUE` (`noPersonal`),
  KEY `fk_Empleado_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Empleado_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `evaluacion`
--

DROP TABLE IF EXISTS `evaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `calificacion` varchar(15) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `idMateria` int NOT NULL,
  `idAlumno` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Evaluacion_Alumno1_idx` (`idAlumno`),
  KEY `fk_Evaluacion_Materia1_idx` (`idMateria`),
  CONSTRAINT `fk_Evaluacion_Alumno1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`id`),
  CONSTRAINT `fk_Evaluacion_Materia1` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grado`
--

DROP TABLE IF EXISTS `grado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `idPlanEstudio` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  KEY `fk_Grado_PlanEstudio1_idx` (`idPlanEstudio`),
  CONSTRAINT `fk_Grado_PlanEstudio1` FOREIGN KEY (`idPlanEstudio`) REFERENCES `planestudio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `grupo`
--

DROP TABLE IF EXISTS `grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `idGrado` int NOT NULL,
  `idPeriodoEscolar` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  KEY `fk_Grupo_Grado1_idx` (`idGrado`),
  KEY `fk_Grupo_PeriodoEscolar1_idx` (`idPeriodoEscolar`),
  CONSTRAINT `fk_Grupo_Grado1` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`id`),
  CONSTRAINT `fk_Grupo_PeriodoEscolar1` FOREIGN KEY (`idPeriodoEscolar`) REFERENCES `periodoescolar` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `horaInicio` time NOT NULL,
  `horaFin` time NOT NULL,
  `idSalon` int NOT NULL,
  `idClase` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Horario_Clase1_idx` (`idClase`),
  KEY `fk_Horario_Salon1_idx` (`idSalon`),
  CONSTRAINT `fk_Horario_Clase1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`id`),
  CONSTRAINT `fk_Horario_Salon1` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inscripcion`
--

DROP TABLE IF EXISTS `inscripcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripcion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fechaRegistro` date NOT NULL,
  `idGrupo` int NOT NULL,
  `idPeriodoEscolar` int NOT NULL,
  `idAlumno` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Inscripcion_Alumno1_idx` (`idAlumno`),
  KEY `fk_Inscripcion_Grupo1_idx` (`idGrupo`),
  KEY `fk_Inscripcion_PeriodoEscolar1_idx` (`idPeriodoEscolar`),
  CONSTRAINT `fk_Inscripcion_Alumno1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`id`),
  CONSTRAINT `fk_Inscripcion_Grupo1` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`id`),
  CONSTRAINT `fk_Inscripcion_PeriodoEscolar1` FOREIGN KEY (`idPeriodoEscolar`) REFERENCES `periodoescolar` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(30) NOT NULL,
  `horasPracticas` int NOT NULL,
  `horasTeoricas` int NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idGrado` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  KEY `fk_Materia_Grado1_idx` (`idGrado`),
  CONSTRAINT `fk_Materia_Grado1` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `periodoescolar`
--

DROP TABLE IF EXISTS `periodoescolar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodoescolar` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `planestudio`
--

DROP TABLE IF EXISTS `planestudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planestudio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `clave` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salon`
--

DROP TABLE IF EXISTS `salon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `clave` varchar(20) NOT NULL,
  `capacidad` int NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idEdificio` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clave_UNIQUE` (`clave`),
  KEY `fk_Salon_Edificio_idx` (`idEdificio`),
  CONSTRAINT `fk_Salon_Edificio` FOREIGN KEY (`idEdificio`) REFERENCES `edificio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `telefono` varchar(12) DEFAULT NULL,
  `correoElectronico` varchar(50) DEFAULT NULL,
  `claveAcceso` varchar(50) DEFAULT NULL,
  `tipo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-27 23:22:26
