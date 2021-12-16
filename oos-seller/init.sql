
CREATE DATABASE IF NOT EXISTS seller_db;
CREATE USER 'seller_apiuser'@'%' IDENTIFIED BY 'passw0rd';
GRANT ALL PRIVILEGES ON * . * TO 'seller_apiuser'@'%';
FLUSH PRIVILEGES;