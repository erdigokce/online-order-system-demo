CREATE TABLE `principles` (
  `email` varchar(255) NOT NULL,
  `email_confirmed` bit(1) NOT NULL,
  `password` varchar(60) NOT NULL,
  `authority` varchar(10) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `address` longtext NOT NULL,
  `principle_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_users_principle_email` FOREIGN KEY (`principle_email`) REFERENCES `principles` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sellers` (
  `id` binary(16) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `business_address` longtext NOT NULL,
  `business_name` varchar(255) NOT NULL,
  `principle_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_business_name` (`business_name`),
  CONSTRAINT `FK_sellers_principle_email` FOREIGN KEY (`principle_email`) REFERENCES `principles` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `id` binary(16) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_orders` (
  `id` binary(16) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `quantity` int NOT NULL,
  `status` varchar(10) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ORDER_ON_PRODUCT` (`product_id`),
  KEY `FK_ORDER_ON_USER` (`user_id`),
  CONSTRAINT `FK_ORDER_ON_USER` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_ORDER_ON_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tokens` (
  `subject` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `expires_at` datetime NOT NULL,
  PRIMARY KEY (`subject`),
  UNIQUE KEY `UK_token` (`token`),
  UNIQUE KEY `UK_subject` (`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;