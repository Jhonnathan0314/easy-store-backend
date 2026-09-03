CREATE TABLE `account` (
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `user` ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`), ADD CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

CREATE TABLE `account_has_user` (
  `account_id` bigint NOT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account_id`,`user_id`),
  KEY `FKbq2yjhsyflar68k1prvdvq910` (`user_id`),
  CONSTRAINT `FK2yxgwxfsy56h6dfkhamq127cj` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKbq2yjhsyflar68k1prvdvq910` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category` (
  `account_id` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6ymhc01g9q7834m0220ruu83m` (`account_id`),
  KEY `FKpfk8djhv5natgshmxiav6xkpu` (`user_id`),
  CONSTRAINT `FK6ymhc01g9q7834m0220ruu83m` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKpfk8djhv5natgshmxiav6xkpu` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment_type` (
  `create_by` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `category_has_payment_type` (
  `category_id` bigint NOT NULL,
  `payment_type_id` bigint NOT NULL,
  `phone` bigint DEFAULT NULL,
  `account_bank` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`,`payment_type_id`),
  KEY `FK4cvji8o9mnu7pooep14itbpf7` (`payment_type_id`),
  CONSTRAINT `FK1crgtv00kkjkoxnueana202t7` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK4cvji8o9mnu7pooep14itbpf7` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `codes` (
  `code` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `subcategory` (
  `category_id` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `update_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe4hdbsmrx9bs9gpj1fh4mg0ku` (`category_id`),
  CONSTRAINT `FKe4hdbsmrx9bs9gpj1fh4mg0ku` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `image_last_number` int DEFAULT NULL,
  `image_number` int DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `qualification` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `subcategory_id` bigint DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKku369nri8u3s17uom8or57trs` (`subcategory_id`),
  CONSTRAINT `FKku369nri8u3s17uom8or57trs` FOREIGN KEY (`subcategory_id`) REFERENCES `subcategory` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `purchase` (
  `total` decimal(38,2) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `creation_date` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_type_id` bigint DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc91m4lisdfl8exek7whlxaqdq` (`category_id`),
  KEY `FK5h1ljl2l01yf8m6kpfiavupn5` (`payment_type_id`),
  KEY `FK86i0stm7cqsglqptdvjij1k3m` (`user_id`),
  CONSTRAINT `FK5h1ljl2l01yf8m6kpfiavupn5` FOREIGN KEY (`payment_type_id`) REFERENCES `payment_type` (`id`),
  CONSTRAINT `FK86i0stm7cqsglqptdvjij1k3m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKc91m4lisdfl8exek7whlxaqdq` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `purchase_has_product` (
  `quantity` int DEFAULT NULL,
  `subtotal` decimal(38,2) DEFAULT NULL,
  `unit_price` decimal(38,2) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `purchase_id` bigint NOT NULL,
  PRIMARY KEY (`product_id`,`purchase_id`),
  KEY `FKj3tuv0q3y7frdk45f24x7tywe` (`purchase_id`),
  CONSTRAINT `FKe02he4uudl7nvjkxjgaadw28a` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKj3tuv0q3y7frdk45f24x7tywe` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
