CREATE TABLE oosdb.sellers (
  id binary(16) NOT NULL,
  created_by VARCHAR(255) NULL,
  created_date datetime NULL,
  last_modified_by VARCHAR(255) NULL,
  last_modified_date datetime NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(16) NOT NULL,
  business_name VARCHAR(255) NOT NULL,
  business_address LONGTEXT NOT NULL,
  email_confirmed BIT(1) NOT NULL,
  CONSTRAINT pk_register PRIMARY KEY (id)
);

ALTER TABLE oosdb.sellers ADD CONSTRAINT uc_register_business_name UNIQUE (business_name);

ALTER TABLE oosdb.sellers ADD CONSTRAINT uc_register_email UNIQUE (email);

CREATE TABLE oosdb.products (
  id binary(16) NOT NULL,
  created_by VARCHAR(255) NULL,
  created_date datetime NULL,
  last_modified_by VARCHAR(255) NULL,
  last_modified_date datetime NULL,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(512) NULL,
  quantity INT NOT NULL,
  CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE oosdb.user_orders (
  id binary(16) NOT NULL,
  created_by VARCHAR(255) NULL,
  created_date datetime NULL,
  last_modified_by VARCHAR(255) NULL,
  last_modified_date datetime NULL,
  product_id binary(16) NOT NULL,
  quantity INT NOT NULL,
  user_email VARCHAR(255) NOT NULL,
  CONSTRAINT pk_order PRIMARY KEY (id)
);

ALTER TABLE oosdb.user_orders ADD CONSTRAINT FK_ORDER_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);