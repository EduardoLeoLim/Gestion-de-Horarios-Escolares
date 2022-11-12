-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: bnktupv4hkmgz2gog6kd-mysql.services.clever-cloud.com:3306
-- Generation Time: Nov 12, 2022 at 08:39 PM
-- Server version: 8.0.22-13
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bnktupv4hkmgz2gog6kd`
--

-- --------------------------------------------------------

--
-- Table structure for table `alumno`
--

CREATE TABLE `alumno` (
  `id` int NOT NULL,
  `matricula` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `curp` varchar(20) NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idUsuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `alumno`
--

INSERT INTO `alumno` (`id`, `matricula`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `curp`, `estatus`, `idUsuario`) VALUES
(1, 'MS22012942', 'Angel', 'Martinez', 'Gonzalez', 'MALA22', 1, 9);

-- --------------------------------------------------------

--
-- Table structure for table `asignacion`
--

CREATE TABLE `asignacion` (
  `id` int NOT NULL,
  `idInscripcion` int NOT NULL,
  `idGrupo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `clase`
--

CREATE TABLE `clase` (
  `id` int NOT NULL,
  `idEmpleado` int DEFAULT NULL,
  `idMateria` int NOT NULL,
  `idGrupo` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `edificio`
--

CREATE TABLE `edificio` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `edificio`
--

INSERT INTO `edificio` (`id`, `clave`, `nombre`, `estatus`) VALUES
(1, 'E-001', 'Edificio 1', 1),
(7, 'E-002', 'Edificio 2', 1);

-- --------------------------------------------------------

--
-- Table structure for table `empleado`
--

CREATE TABLE `empleado` (
  `id` int NOT NULL,
  `noPersonal` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellidoPaterno` varchar(50) NOT NULL,
  `apellidoMaterno` varchar(50) DEFAULT NULL,
  `estatus` tinyint(1) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `idUsuario` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `empleado`
--

INSERT INTO `empleado` (`id`, `noPersonal`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `estatus`, `tipo`, `idUsuario`) VALUES
(1, 'ADMIN-001', 'Angel Eduardo', 'Martínez', 'Leo Lim', 1, 'Administrador', 1),
(2, 'ADMIN-002', 'Luisa Mariana', 'Pulido', 'Gonzalez', 1, 'Administrador', 5),
(3, 'ADMIN-003', 'Brandon', 'Lopez', 'Tenorio', 1, 'Administrador', 6),
(4, 'SEC-001', 'Raúl', 'Perez', 'Gomez', 1, 'Secretario', 7),
(5, 'MTRO-001', 'Sergio', 'Sanchez', 'Perez', 1, 'Maestro', 8);

-- --------------------------------------------------------

--
-- Table structure for table `evaluacion`
--

CREATE TABLE `evaluacion` (
  `id` int NOT NULL,
  `calificacion` varchar(15) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `idMateria` int NOT NULL,
  `idAlumno` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `grado`
--

CREATE TABLE `grado` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estatus` tinyint NOT NULL,
  `idPlanEstudio` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `grado`
--

INSERT INTO `grado` (`id`, `clave`, `nombre`, `estatus`, `idPlanEstudio`) VALUES
(1, '121', 'Nivel 1', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `grupo`
--

CREATE TABLE `grupo` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `idGrado` int NOT NULL,
  `idPeriodoEscolar` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `horario`
--

CREATE TABLE `horario` (
  `id` int NOT NULL,
  `horaInicio` time NOT NULL,
  `horaFin` time NOT NULL,
  `idSalon` int NOT NULL,
  `idClase` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inscripcion`
--

CREATE TABLE `inscripcion` (
  `id` int NOT NULL,
  `fechaRegistro` date NOT NULL,
  `idGrado` int NOT NULL,
  `idPeriodoEscolar` int NOT NULL,
  `idAlumno` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `materia`
--

CREATE TABLE `materia` (
  `id` int NOT NULL,
  `clave` varchar(30) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `horasPracticas` int NOT NULL,
  `horasTeoricas` int NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idGrado` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `materia`
--

INSERT INTO `materia` (`id`, `clave`, `nombre`, `horasPracticas`, `horasTeoricas`, `estatus`, `idGrado`) VALUES
(1, 'AAA', 'Inglés 1', 34, 26, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `periodoescolar`
--

CREATE TABLE `periodoescolar` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `estatus` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `periodoescolar`
--

INSERT INTO `periodoescolar` (`id`, `clave`, `nombre`, `fechaInicio`, `fechaFin`, `estatus`) VALUES
(1, '01220623', 'Enero 2023 - Junio 2023', '2023-01-01', '2023-06-30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `planestudio`
--

CREATE TABLE `planestudio` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estatus` tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `planestudio`
--

INSERT INTO `planestudio` (`id`, `clave`, `nombre`, `estatus`) VALUES
(1, '221', 'Plan Educativo 2022', 1);

-- --------------------------------------------------------

--
-- Table structure for table `salon`
--

CREATE TABLE `salon` (
  `id` int NOT NULL,
  `clave` varchar(20) NOT NULL,
  `capacidad` int NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `idEdificio` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `salon`
--

INSERT INTO `salon` (`id`, `clave`, `capacidad`, `estatus`, `idEdificio`) VALUES
(1, 'E1S-001', 25, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id` int NOT NULL,
  `telefono` varchar(12) NOT NULL,
  `correoElectronico` varchar(80) NOT NULL,
  `claveAcceso` varchar(50) NOT NULL,
  `tipo` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `telefono`, `correoElectronico`, `claveAcceso`, `tipo`) VALUES
(1, '22811234565', 'eduardoleolim@hotmail.com', '012345', 'Administrador'),
(5, '2281123456', 'zs18012153@estudiantes.uv.mx', 'a', 'Administrador'),
(6, '1234567890', 'zs18012159@estudiantes.uv.mx', '12345', 'Administrador'),
(7, '1345652465', 'raul@gmail.com', 'a', 'Secretario'),
(8, '2212345653', 'sperez@gmail.com', 'sperez', 'Maestro'),
(9, '2281963942', 'atorre2012@gmail.com', 'a', 'Alumno');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `curp_UNIQUE` (`curp`),
  ADD UNIQUE KEY `matricula_UNIQUE` (`matricula`),
  ADD KEY `fk_Alumno_Usuario1_idx` (`idUsuario`);

--
-- Indexes for table `asignacion`
--
ALTER TABLE `asignacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idInscripcion_uq` (`idInscripcion`),
  ADD KEY `asignacion_grupo_fk` (`idGrupo`);

--
-- Indexes for table `clase`
--
ALTER TABLE `clase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Clase_Empleado1_idx` (`idEmpleado`),
  ADD KEY `fk_Clase_Grupo1_idx` (`idGrupo`),
  ADD KEY `fk_Clase_Materia1_idx` (`idMateria`);

--
-- Indexes for table `edificio`
--
ALTER TABLE `edificio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`);

--
-- Indexes for table `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `noPersonal_UNIQUE` (`noPersonal`),
  ADD KEY `fk_Empleado_Usuario1_idx` (`idUsuario`);

--
-- Indexes for table `evaluacion`
--
ALTER TABLE `evaluacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Evaluacion_Alumno1_idx` (`idAlumno`),
  ADD KEY `fk_Evaluacion_Materia1_idx` (`idMateria`);

--
-- Indexes for table `grado`
--
ALTER TABLE `grado`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`),
  ADD KEY `fk_Grado_PlanEstudio1_idx` (`idPlanEstudio`);

--
-- Indexes for table `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`),
  ADD KEY `fk_Grupo_Grado1_idx` (`idGrado`),
  ADD KEY `fk_Grupo_PeriodoEscolar1_idx` (`idPeriodoEscolar`);

--
-- Indexes for table `horario`
--
ALTER TABLE `horario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Horario_Clase1_idx` (`idClase`),
  ADD KEY `fk_Horario_Salon1_idx` (`idSalon`);

--
-- Indexes for table `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_Inscripcion_Alumno1_idx` (`idAlumno`),
  ADD KEY `fk_Inscripcion_PeriodoEscolar1_idx` (`idPeriodoEscolar`),
  ADD KEY `fk_Inscripcion_Grado` (`idGrado`);

--
-- Indexes for table `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`),
  ADD KEY `fk_Materia_Grado1_idx` (`idGrado`);

--
-- Indexes for table `periodoescolar`
--
ALTER TABLE `periodoescolar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`);

--
-- Indexes for table `planestudio`
--
ALTER TABLE `planestudio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`);

--
-- Indexes for table `salon`
--
ALTER TABLE `salon`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `clave_UNIQUE` (`clave`),
  ADD KEY `fk_Salon_Edificio_idx` (`idEdificio`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alumno`
--
ALTER TABLE `alumno`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `asignacion`
--
ALTER TABLE `asignacion`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clase`
--
ALTER TABLE `clase`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `edificio`
--
ALTER TABLE `edificio`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `evaluacion`
--
ALTER TABLE `evaluacion`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `grado`
--
ALTER TABLE `grado`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `horario`
--
ALTER TABLE `horario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inscripcion`
--
ALTER TABLE `inscripcion`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `materia`
--
ALTER TABLE `materia`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `periodoescolar`
--
ALTER TABLE `periodoescolar`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `planestudio`
--
ALTER TABLE `planestudio`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `salon`
--
ALTER TABLE `salon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `fk_Alumno_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`);

--
-- Constraints for table `asignacion`
--
ALTER TABLE `asignacion`
  ADD CONSTRAINT `asignacion_grupo_fk` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`id`),
  ADD CONSTRAINT `asignacion_inscripcion_fk` FOREIGN KEY (`idInscripcion`) REFERENCES `inscripcion` (`id`);

--
-- Constraints for table `clase`
--
ALTER TABLE `clase`
  ADD CONSTRAINT `fk_Clase_Empleado1` FOREIGN KEY (`idEmpleado`) REFERENCES `empleado` (`id`),
  ADD CONSTRAINT `fk_Clase_Grupo1` FOREIGN KEY (`idGrupo`) REFERENCES `grupo` (`id`),
  ADD CONSTRAINT `fk_Clase_Materia1` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`id`);

--
-- Constraints for table `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `fk_Empleado_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`);

--
-- Constraints for table `evaluacion`
--
ALTER TABLE `evaluacion`
  ADD CONSTRAINT `fk_Evaluacion_Alumno1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`id`),
  ADD CONSTRAINT `fk_Evaluacion_Materia1` FOREIGN KEY (`idMateria`) REFERENCES `materia` (`id`);

--
-- Constraints for table `grado`
--
ALTER TABLE `grado`
  ADD CONSTRAINT `fk_Grado_PlanEstudio1` FOREIGN KEY (`idPlanEstudio`) REFERENCES `planestudio` (`id`);

--
-- Constraints for table `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `fk_Grupo_Grado1` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`id`),
  ADD CONSTRAINT `fk_Grupo_PeriodoEscolar1` FOREIGN KEY (`idPeriodoEscolar`) REFERENCES `periodoescolar` (`id`);

--
-- Constraints for table `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `fk_Horario_Clase1` FOREIGN KEY (`idClase`) REFERENCES `clase` (`id`),
  ADD CONSTRAINT `fk_Horario_Salon1` FOREIGN KEY (`idSalon`) REFERENCES `salon` (`id`);

--
-- Constraints for table `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD CONSTRAINT `fk_Inscripcion_Alumno1` FOREIGN KEY (`idAlumno`) REFERENCES `alumno` (`id`),
  ADD CONSTRAINT `fk_Inscripcion_Grado` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`id`),
  ADD CONSTRAINT `fk_Inscripcion_PeriodoEscolar1` FOREIGN KEY (`idPeriodoEscolar`) REFERENCES `periodoescolar` (`id`);

--
-- Constraints for table `materia`
--
ALTER TABLE `materia`
  ADD CONSTRAINT `fk_Materia_Grado1` FOREIGN KEY (`idGrado`) REFERENCES `grado` (`id`);

--
-- Constraints for table `salon`
--
ALTER TABLE `salon`
  ADD CONSTRAINT `fk_Salon_Edificio` FOREIGN KEY (`idEdificio`) REFERENCES `edificio` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
