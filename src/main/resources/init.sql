-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: snack_shop
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `role` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'admin' COMMENT '角色',
  `status` int DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','admin123','罗光明','admin',1,'2026-06-16 13:34:32','2026-06-14 23:02:25');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `after_sale`
--

DROP TABLE IF EXISTS `after_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `after_sale` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '售后ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `type` int NOT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '售后原因',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '问题描述',
  `images` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图片(多张逗号分隔)',
  `status` int DEFAULT NULL,
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `admin_reply` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '管理员回复',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `after_sale_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `after_sale_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='售后服务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `after_sale`
--

LOCK TABLES `after_sale` WRITE;
/*!40000 ALTER TABLE `after_sale` DISABLE KEYS */;
INSERT INTO `after_sale` VALUES (1,2,2,1,'商品质量问题','收到的辣条包装破损，有漏气现象',NULL,2,NULL,'已为您办理退款，非常抱歉','2026-06-14 23:02:25','2026-06-14 23:02:25'),(2,1,1,4,'配送太慢','等了超过30分钟才送到',NULL,1,NULL,NULL,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(3,13,1,1,'','',NULL,2,NULL,'已批准处理','2026-06-15 00:43:12','2026-06-15 01:07:40');
/*!40000 ALTER TABLE `after_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `goods_id` bigint NOT NULL COMMENT '商品ID',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `sort_order` int DEFAULT '0' COMMENT '排序序号',
  `status` int DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'零食',1,1,'2026-06-14 23:02:25'),(2,'饮料',2,1,'2026-06-14 23:02:25'),(3,'日用品',3,1,'2026-06-14 23:02:25'),(4,'方便食品',4,1,'2026-06-14 23:02:25'),(5,'面包糕点',5,1,'2026-06-14 23:02:25');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID',
  `price` decimal(10,2) NOT NULL COMMENT '商品单价',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价(用于划线价展示)',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存数量',
  `stock_warning` int DEFAULT '5' COMMENT '库存预警值',
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品图片URL',
  `description` text COLLATE utf8mb4_unicode_ci COMMENT '商品描述',
  `is_recommend` int DEFAULT NULL,
  `is_hot` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `sales` int DEFAULT '0' COMMENT '累计销量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'乐事薯片原味75g',1,8.50,10.00,50,5,'/img/snack1.jpg','乐事经典原味薯片，酥脆可口，休闲必备',1,1,0,128,'2026-06-14 23:02:25','2026-06-14 23:27:39'),(2,'好丽友派巧克力味6枚装',1,12.80,15.00,30,5,'/img/snack2.jpg','好丽友经典巧克力派，软糯香甜',1,1,0,95,'2026-06-14 23:02:25','2026-06-14 23:27:40'),(3,'旺旺仙贝520g',1,15.00,18.00,25,5,'/img/snack3.jpg','旺旺经典仙贝，咸香酥脆',0,1,1,76,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(4,'百草味猪肉脯100g',1,18.80,22.00,9,5,'/img/snack4.jpg','精选猪肉制作，香辣入味',1,0,1,64,'2026-06-14 23:02:25','2026-06-15 00:43:45'),(5,'三只松鼠每日坚果25g*7袋',1,29.90,35.00,7,5,'/img/snack5.jpg','每日营养坚果混合装',1,1,1,96,'2026-06-14 23:02:25','2026-06-15 12:16:17'),(6,'卫龙辣条大面筋65g',1,3.50,5.00,78,5,'/img/snack6.jpg','经典辣条，香辣过瘾',0,1,1,212,'2026-06-14 23:02:25','2026-06-15 01:04:18'),(7,'奥利奥夹心饼干97g',1,7.50,9.00,40,5,'/img/snack7.jpg','扭一扭泡一泡，经典奥利奥',0,0,1,65,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(8,'良品铺子鸭脖锁骨套餐200g',1,22.00,28.00,18,5,'/img/snack8.jpg','卤味鸭脖+锁骨组合，麻辣鲜香',1,0,1,42,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(9,'农夫山泉550ml',2,2.00,2.50,100,5,'/img/drink1.jpg','天然饮用水，健康好水',0,1,1,320,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(10,'可口可乐330ml*6罐',2,12.00,15.00,30,5,'/img/drink2.jpg','经典碳酸饮料，冰镇更爽',1,1,1,156,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(11,'元气森林白桃气泡水480ml',2,5.50,6.50,45,5,'/img/drink3.jpg','0糖0脂0卡，清爽白桃味',1,1,1,180,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(12,'蒙牛纯牛奶250ml*12盒',2,42.00,48.00,20,5,'/img/drink4.jpg','优质纯牛奶整箱装',0,0,1,35,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(13,'统一冰红茶500ml',2,3.50,4.00,60,5,'/img/drink5.jpg','经典冰红茶，清凉解渴',0,1,1,245,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(14,'星巴克星冰乐281ml',2,12.50,15.00,25,5,'/img/drink6.jpg','即饮咖啡，提神醒脑',1,0,1,48,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(15,'维达抽纸130抽*3包',3,9.90,12.00,35,5,'/img/daily1.jpg','柔软亲肤抽纸',1,1,1,165,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(16,'黑人牙膏140g',3,12.50,15.00,25,5,'/img/daily2.jpg','双重薄荷牙膏，清新口气',0,0,1,38,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(17,'蓝月亮洗衣液500g',3,8.80,10.00,20,5,'/img/daily3.jpg','深层洁净洗衣液',0,0,1,52,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(18,'舒肤佳香皂115g',3,5.50,7.00,30,5,'/img/daily4.jpg','经典纯白清香香皂',0,0,1,44,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(19,'康师傅红烧牛肉面5连包',4,12.50,15.00,40,5,'/img/fast1.jpg','经典红烧牛肉味方便面',1,1,1,188,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(20,'统一老坛酸菜牛肉面5连包',4,12.50,15.00,35,5,'/img/fast2.jpg','酸爽开胃老坛酸菜面',0,1,1,145,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(21,'自嗨锅麻辣牛肉锅',4,29.90,35.00,15,5,'/img/fast3.jpg','自热火锅，方便美味',1,0,1,62,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(22,'海底捞自热米饭煲仔饭',4,25.00,30.00,18,5,'/img/fast4.jpg','自热米饭，懒人必备',0,0,1,33,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(23,'桃李醇熟切片面包400g',5,8.50,10.00,25,5,'/img/bread1.jpg','新鲜切片面包，早餐首选',1,1,1,132,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(24,'达利园蛋黄派注心250g',5,9.90,12.00,30,5,'/img/bread2.jpg','经典蛋黄派，松软可口',0,0,1,78,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(25,'盼盼法式小面包200g',5,7.50,9.00,28,5,'/img/bread3.jpg','法式风味小面包，香软细腻',0,0,1,55,'2026-06-14 23:02:25','2026-06-14 23:02:25');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `goods_id` bigint NOT NULL COMMENT '商品ID',
  `goods_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品名称(冗余)',
  `goods_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品图片(冗余)',
  `goods_price` decimal(10,2) NOT NULL COMMENT '购买时单价',
  `quantity` int NOT NULL COMMENT '购买数量',
  `subtotal` decimal(10,2) NOT NULL COMMENT '小计金额',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,1,'乐事薯片原味75g','/img/snack1.jpg',8.50,2,17.00),(2,1,11,'可口可乐330ml*6罐','/img/drink2.jpg',12.00,1,12.00),(3,1,21,'维达抽纸130抽*3包','/img/daily1.jpg',9.90,1,9.90),(4,2,6,'卫龙辣条大面筋65g','/img/snack6.jpg',3.50,5,17.50),(5,3,25,'康师傅红烧牛肉面5连包','/img/fast1.jpg',12.50,1,12.50),(6,3,13,'元气森林白桃气泡水480ml','/img/drink3.jpg',5.50,2,11.00),(7,3,29,'桃李醇熟切片面包400g','/img/bread1.jpg',8.50,1,8.50),(8,3,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,1,18.80),(9,4,29,'桃李醇熟切片面包400g','/img/bread1.jpg',8.50,1,8.50),(10,5,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(11,5,27,'自嗨锅麻辣牛肉锅','/img/fast3.jpg',29.90,1,29.90),(12,6,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(13,6,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(14,7,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(15,7,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(16,8,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(17,8,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(18,9,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(19,9,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(20,10,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(21,10,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(22,11,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(23,11,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(24,12,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,1,29.90),(25,12,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,2,37.60),(26,13,4,'百草味猪肉脯100g','/img/snack4.jpg',18.80,1,18.80),(27,14,6,'卫龙辣条大面筋65g','/img/snack6.jpg',3.50,1,3.50),(28,15,6,'卫龙辣条大面筋65g','/img/snack6.jpg',3.50,1,3.50),(29,16,6,'卫龙辣条大面筋65g','/img/snack6.jpg',3.50,1,3.50),(30,17,5,'三只松鼠每日坚果25g*7袋','/img/snack5.jpg',29.90,3,89.70);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '实付金额',
  `pay_type` int DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `status` int DEFAULT NULL,
  `dormitory` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货宿舍号',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货人电话',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单备注',
  `cancel_reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '取消原因',
  `deliver_time` datetime DEFAULT NULL COMMENT '配送时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'O20250601001',1,32.80,32.80,1,'2025-06-01 12:30:00',4,'3栋401','13800001111',NULL,NULL,NULL,NULL,'2025-06-01 12:25:00','2026-06-14 23:02:25'),(2,'O20250602001',2,17.50,17.50,1,'2025-06-02 19:15:00',4,'3栋402','13800002222',NULL,NULL,NULL,NULL,'2025-06-02 19:10:00','2026-06-14 23:02:25'),(3,'O20250603001',3,45.30,45.30,2,'2025-06-03 10:05:00',4,'3栋501','13800003333',NULL,NULL,NULL,'2026-06-14 23:36:02','2025-06-03 10:00:00','2026-06-14 23:36:02'),(4,'O20250604001',4,8.50,8.50,1,'2025-06-04 22:00:00',2,'3栋502','13800004444',NULL,NULL,NULL,NULL,'2025-06-04 21:55:00','2026-06-14 23:36:09'),(5,'O20250605001',5,55.40,55.40,1,'2025-06-05 14:20:00',2,'3栋601','13800005555',NULL,NULL,NULL,NULL,'2025-06-05 14:15:00','2026-06-14 23:02:25'),(6,'O202606142336424327',1,67.50,67.50,0,NULL,0,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-14 23:36:43','2026-06-14 23:36:43'),(7,'O202606142336538639',1,67.50,67.50,0,NULL,0,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-14 23:36:54','2026-06-14 23:36:54'),(8,'O202606142349101305',1,67.50,67.50,1,'2026-06-14 23:49:15',6,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-14 23:49:11','2026-06-15 00:00:53'),(9,'O202606142359004608',1,67.50,67.50,2,'2026-06-14 23:59:20',6,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-14 23:59:00','2026-06-15 00:00:52'),(10,'O202606150000348518',1,67.50,67.50,2,'2026-06-15 00:43:45',1,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 00:00:35','2026-06-15 00:43:45'),(11,'O202606150001566751',1,67.50,67.50,1,'2026-06-15 00:43:42',1,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 00:01:56','2026-06-15 00:43:42'),(12,'O202606150014486670',1,67.50,67.50,2,'2026-06-15 00:15:18',1,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 00:14:48','2026-06-15 00:15:18'),(13,'O202606150042579850',1,18.80,18.80,1,'2026-06-15 00:43:00',1,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 00:42:58','2026-06-15 00:43:00'),(14,'O202606150056392841',1,3.50,3.50,2,'2026-06-15 00:56:46',1,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 00:56:39','2026-06-15 00:56:46'),(15,'O202606150057191124',1,3.50,3.50,0,NULL,5,'3栋401','13800001111','','用户主动取消',NULL,NULL,'2026-06-15 00:57:20','2026-06-15 01:07:31'),(16,'O202606150104154217',1,3.50,3.50,2,'2026-06-15 01:04:18',6,'3栋401','13800001111','',NULL,NULL,NULL,'2026-06-15 01:04:15','2026-06-15 12:17:18'),(17,'O202606151216005314',1,89.70,89.70,2,'2026-06-15 12:16:17',4,'3栋401','13800001111','',NULL,'2026-06-15 12:16:50','2026-06-15 12:16:52','2026-06-15 12:16:00','2026-06-15 12:16:52');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付记录ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `pay_type` int NOT NULL,
  `pay_status` int DEFAULT NULL,
  `transaction_no` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '第三方支付流水号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,1,'O20250601001',1,32.80,1,1,'WX20250601123000001','2025-06-01 12:30:00',NULL,NULL,'2026-06-14 23:02:25'),(2,2,'O20250602001',2,17.50,1,1,'WX20250602191500001','2025-06-02 19:15:00',NULL,NULL,'2026-06-14 23:02:25'),(3,3,'O20250603001',3,45.30,2,1,'ZFB20250603100500001','2025-06-03 10:05:00',NULL,NULL,'2026-06-14 23:02:25'),(4,4,'O20250604001',4,8.50,1,1,'WX20250604220000001','2025-06-04 22:00:00',NULL,NULL,'2026-06-14 23:02:25'),(5,5,'O20250605001',5,55.40,1,1,'WX20250605142000001','2025-06-05 14:20:00',NULL,NULL,'2026-06-14 23:02:25'),(6,8,'O202606142349101305',1,67.50,1,3,'WX20260614234915959592','2026-06-14 23:49:15',67.50,'2026-06-15 00:00:53','2026-06-14 23:49:15'),(7,9,'O202606142359004608',1,67.50,2,3,'ZFB20260614235919926318','2026-06-14 23:59:20',67.50,'2026-06-15 00:00:52','2026-06-14 23:59:20'),(8,12,'O202606150014486670',1,67.50,2,1,'ZFB20260615001518275982','2026-06-15 00:15:18',NULL,NULL,'2026-06-15 00:15:18'),(9,13,'O202606150042579850',1,18.80,1,1,'WX20260615004259810200','2026-06-15 00:43:00',NULL,NULL,'2026-06-15 00:43:00'),(10,11,'O202606150001566751',1,67.50,1,1,'WX20260615004341207227','2026-06-15 00:43:42',NULL,NULL,'2026-06-15 00:43:42'),(11,10,'O202606150000348518',1,67.50,2,1,'ZFB20260615004345149931','2026-06-15 00:43:45',NULL,NULL,'2026-06-15 00:43:45'),(12,14,'O202606150056392841',1,3.50,2,1,'ZFB20260615005645614882','2026-06-15 00:56:46',NULL,NULL,'2026-06-15 00:56:46'),(13,16,'O202606150104154217',1,3.50,2,3,'ZFB20260615010417402905','2026-06-15 01:04:18',3.50,'2026-06-15 12:17:18','2026-06-15 01:04:18'),(14,17,'O202606151216005314',1,89.70,2,1,'ZFB20260615121616138180','2026-06-15 12:16:17',NULL,NULL,'2026-06-15 12:16:17');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_log`
--

DROP TABLE IF EXISTS `system_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID(可为空)',
  `user_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户类型: user/admin',
  `action` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型: login/logout/browse/order/pay等',
  `detail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作详情',
  `ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_log`
--

LOCK TABLES `system_log` WRITE;
/*!40000 ALTER TABLE `system_log` DISABLE KEYS */;
INSERT INTO `system_log` VALUES (1,1,'user','login','用户登录: 2023001',NULL,'2026-06-14 23:25:57'),(2,1,'admin','login','管理员登录: admin',NULL,'2026-06-14 23:27:29'),(3,1,'user','login','用户登录: 2023001',NULL,'2026-06-14 23:28:27'),(4,1,'admin','login','管理员登录: admin',NULL,'2026-06-14 23:35:52'),(5,1,'admin','login','管理员登录: admin',NULL,'2026-06-14 23:37:04'),(6,1,'admin','login','管理员登录: admin',NULL,'2026-06-14 23:49:28'),(7,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 00:00:47'),(8,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 00:15:10'),(9,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 00:15:34'),(10,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 00:21:21'),(11,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 00:36:49'),(12,1,'user','login','用户登录: 2023001',NULL,'2026-06-15 12:26:55'),(13,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 12:27:48'),(14,1,'admin','login','管理员登录: admin',NULL,'2026-06-15 13:25:43'),(15,1,'admin','login','管理员登录: admin',NULL,'2026-06-16 13:24:04'),(16,1,'user','login','用户登录: 2023001',NULL,'2026-06-16 13:25:34'),(17,1,'admin','login','管理员登录: admin',NULL,'2026-06-16 13:25:47'),(18,1,'admin','login','管理员登录: admin',NULL,'2026-06-16 13:26:57'),(19,1,'user','login','用户登录: 2023001',NULL,'2026-06-16 13:28:44'),(20,1,'admin','login','管理员登录: admin',NULL,'2026-06-16 13:34:32'),(21,1,'user','login','用户登录: 2023001',NULL,'2026-06-16 13:35:04');
/*!40000 ALTER TABLE `system_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名(学号)',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `dormitory` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宿舍号',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
  `status` int DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2023001','123456','张三','13800001111','3栋401',NULL,1,'2026-06-16 13:35:04','2026-06-14 23:02:25','2026-06-16 13:35:04'),(2,'2023002','123456','李四','13800002222','3栋402',NULL,1,NULL,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(3,'2023003','123456','王五','13800003333','3栋501',NULL,1,NULL,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(4,'2023004','123456','赵六','13800004444','3栋502',NULL,1,NULL,'2026-06-14 23:02:25','2026-06-14 23:02:25'),(5,'2023005','123456','小明','13800005555','3栋601',NULL,1,NULL,'2026-06-14 23:02:25','2026-06-14 23:02:25');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-16 13:51:33
