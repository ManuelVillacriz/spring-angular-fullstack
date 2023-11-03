-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.8-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando datos para la tabla db_microservicios_examenes.alumnos: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO alumnos (id, apellido, create_at, email, nombre, foto) VALUES
	(1, 'Guzmán', '2019-11-02 15:51:55.000000', 'andres@mail.com', 'Andrés Jose', NULL),
	(2, 'Doe', '2019-11-02 15:52:32.000000', 'john@gmail.com', 'John', NULL),
	(3, 'Doe', '2019-11-05 09:03:14.000000', 'jose@mail.com', 'Jose', NULL),
	(4, 'García', '2019-11-15 09:56:04.000000', 'pepa@mail.com', 'Pepa', NULL),
	(5, 'Mena', '2019-11-15 09:56:30.000000', 'lalo@mail.com', 'Lalo', NULL),
	(6, 'Fernández', '2019-11-15 09:56:49.000000', 'pepe@mail.com', 'Pepe', NULL),
	(7, 'González', '2019-11-15 09:57:33.000000', 'bea@mail.com', 'Bea', NULL),
	(8, 'Martínez', '2019-11-15 09:57:50.000000', 'luci@mail.com', 'Luci', NULL),
	(9, 'Rodríguez', '2019-11-15 09:58:23.000000', 'pato@mail.com', 'Pato', NULL),
	(10, 'Pérez', '2019-11-16 12:47:57.000000', 'jano@mail.com', 'Jano', NULL);
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
