SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE TABLE IF NOT EXISTS `Usuario` (
  `id` INT NOT NULL,
  `telefono` VARCHAR(12) NULL,
  `correoElectronico` VARCHAR(50) NULL,
  `claveAcceso` VARCHAR(50) NULL,
  `tipo` VARCHAR(30) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Empleado` (
  `id` INT NOT NULL,
  `noPersonal` VARCHAR(50) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidoPaterno` VARCHAR(50) NOT NULL,
  `apellidoMaterno` VARCHAR(50) NULL,
  `estatus` TINYINT(1) NOT NULL,
  `tipo` VARCHAR(50) NOT NULL,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `noPersonal_UNIQUE` (`noPersonal` ASC) VISIBLE,
  INDEX `fk_Empleado_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Empleado_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Alumno` (
  `id` INT NOT NULL,
  `matricula` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `apellidoPaterno` VARCHAR(50) NOT NULL,
  `apellidoMaterno` VARCHAR(50) NULL,
  `curp` VARCHAR(20) NOT NULL,
  `estatus` TINYINT(1) NOT NULL,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `matricula_UNIQUE` (`matricula` ASC) VISIBLE,
  UNIQUE INDEX `curp_UNIQUE` (`curp` ASC) VISIBLE,
  INDEX `fk_Alumno_Usuario1_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_Alumno_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Edificio` (
  `id` INT NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `estatus` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE,
  UNIQUE INDEX `estatus_UNIQUE` (`estatus` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Salon` (
  `id` INT NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  `capacidad` INT NOT NULL,
  `estatus` TINYINT(1) NOT NULL,
  `idEdificio` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE,
  INDEX `fk_Salon_Edificio_idx` (`idEdificio` ASC) VISIBLE,
  CONSTRAINT `fk_Salon_Edificio`
    FOREIGN KEY (`idEdificio`)
    REFERENCES `Edificio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `PlanEstudio` (
  `id` INT NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS  `Grado` (
  `id` INT NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `idPlanEstudio` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE,
  INDEX `fk_Grado_PlanEstudio1_idx` (`idPlanEstudio` ASC) VISIBLE,
  CONSTRAINT `fk_Grado_PlanEstudio1`
    FOREIGN KEY (`idPlanEstudio`)
    REFERENCES `PlanEstudio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Materia` (
  `id` INT NOT NULL,
  `clave` VARCHAR(30) NOT NULL,
  `horasPracticas` INT NOT NULL,
  `horasTeoricas` INT NOT NULL,
  `nombre` VARCHAR(40) NOT NULL,
  `estatus` TINYINT(1) NOT NULL,
  `idGrado` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE,
  INDEX `fk_Materia_Grado1_idx` (`idGrado` ASC) VISIBLE,
  CONSTRAINT `fk_Materia_Grado1`
    FOREIGN KEY (`idGrado`)
    REFERENCES `Grado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `PeriodoEscolar` (
  `id` INT NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `fechaInicio` DATE NOT NULL,
  `fechaFin` DATE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Grupo` (
  `id` INT NOT NULL,
  `clave` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(50) NOT NULL,
  `idGrado` INT NOT NULL,
  `idPeriodoEscolar` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `clave_UNIQUE` (`clave` ASC) VISIBLE,
  INDEX `fk_Grupo_Grado1_idx` (`idGrado` ASC) VISIBLE,
  INDEX `fk_Grupo_PeriodoEscolar1_idx` (`idPeriodoEscolar` ASC) VISIBLE,
  CONSTRAINT `fk_Grupo_Grado1`
    FOREIGN KEY (`idGrado`)
    REFERENCES `Grado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Grupo_PeriodoEscolar1`
    FOREIGN KEY (`idPeriodoEscolar`)
    REFERENCES `PeriodoEscolar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Clase` (
  `id` INT NOT NULL,
  `idEmpleado` INT NOT NULL,
  `idMateria` INT NOT NULL,
  `idGrupo` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Clase_Empleado1_idx` (`idEmpleado` ASC) VISIBLE,
  INDEX `fk_Clase_Materia1_idx` (`idMateria` ASC) VISIBLE,
  INDEX `fk_Clase_Grupo1_idx` (`idGrupo` ASC) VISIBLE,
  CONSTRAINT `fk_Clase_Empleado1`
    FOREIGN KEY (`idEmpleado`)
    REFERENCES `Empleado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Clase_Materia1`
    FOREIGN KEY (`idMateria`)
    REFERENCES `Materia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Clase_Grupo1`
    FOREIGN KEY (`idGrupo`)
    REFERENCES `Grupo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Horario` (
  `id` INT NOT NULL,
  `horaInicio` TIME NOT NULL,
  `horaFin` TIME NOT NULL,
  `idSalon` INT NOT NULL,
  `idClase` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Horario_Salon1_idx` (`idSalon` ASC) VISIBLE,
  INDEX `fk_Horario_Clase1_idx` (`idClase` ASC) VISIBLE,
  CONSTRAINT `fk_Horario_Salon1`
    FOREIGN KEY (`idSalon`)
    REFERENCES `Salon` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Horario_Clase1`
    FOREIGN KEY (`idClase`)
    REFERENCES `Clase` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Inscripcion` (
  `id` INT NOT NULL,
  `fechaRegistro` DATE NOT NULL,
  `idGrupo` INT NOT NULL,
  `idPeriodoEscolar` INT NOT NULL,
  `idAlumno` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Inscripcion_Grupo1_idx` (`idGrupo` ASC) VISIBLE,
  INDEX `fk_Inscripcion_PeriodoEscolar1_idx` (`idPeriodoEscolar` ASC) VISIBLE,
  INDEX `fk_Inscripcion_Alumno1_idx` (`idAlumno` ASC) VISIBLE,
  CONSTRAINT `fk_Inscripcion_Grupo1`
    FOREIGN KEY (`idGrupo`)
    REFERENCES `Grupo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inscripcion_PeriodoEscolar1`
    FOREIGN KEY (`idPeriodoEscolar`)
    REFERENCES `PeriodoEscolar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inscripcion_Alumno1`
    FOREIGN KEY (`idAlumno`)
    REFERENCES `Alumno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `Evaluacion` (
  `id` INT NOT NULL,
  `calificacion` VARCHAR(15) NULL,
  `tipo` VARCHAR(50) NULL,
  `idMateria` INT NOT NULL,
  `idAlumno` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Evaluacion_Materia1_idx` (`idMateria` ASC) VISIBLE,
  INDEX `fk_Evaluacion_Alumno1_idx` (`idAlumno` ASC) VISIBLE,
  CONSTRAINT `fk_Evaluacion_Materia1`
    FOREIGN KEY (`idMateria`)
    REFERENCES `Materia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Evaluacion_Alumno1`
    FOREIGN KEY (`idAlumno`)
    REFERENCES `Alumno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
