CREATE TABLE `tb_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` longtext,
  `launch_date` datetime(6) NOT NULL,
  `price` decimal(65,2) NOT NULL,
  `title` longtext,
  PRIMARY KEY (`id`)
)