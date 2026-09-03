CREATE TABLE `store_request` (
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reviewed_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `store_description` varchar(255) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKstorerequestuser` (`user_id`),
  CONSTRAINT `FKstorerequestuser` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
