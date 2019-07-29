/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.27-log : Database - sell
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sell` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `sell`;

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品数量',
  `product_quantity` int(11) NOT NULL COMMENT '商品数量',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '商品小图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单详情表';

/*Data for the table `order_detail` */

insert  into `order_detail`(`detail_id`,`order_id`,`product_id`,`product_name`,`product_price`,`product_quantity`,`product_icon`,`create_time`,`update_time`) values ('1532785814665559308','1532785814571501917','123458','芒果冰','20.00',1,'http://xxx.jpg','2018-07-28 21:50:14','2018-07-28 21:50:14'),('1532786142882971640','1532786142782800580','123458','芒果冰','20.00',1,'http://xxx.jpg','2018-07-28 21:55:42','2018-07-28 21:55:42'),('1532786142916311971','1532786142782800580','123457','皮皮虾','3.20',2,'http://xxxx.jpg','2018-07-28 21:55:42','2018-07-28 21:55:42'),('1532846228711256107','1532846228694169870','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 14:37:08','2018-07-29 14:37:08'),('1532846355058417098','1532846355056462752','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 14:39:15','2018-07-29 14:39:15'),('1532847001867428879','1532847001800649250','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 14:50:03','2018-07-29 14:50:03'),('1532847049276280944','1532847049273890840','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 14:58:12','2018-07-29 14:58:12'),('1532849914325381198','1532849914322167585','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 15:39:57','2018-07-29 15:39:57'),('1532850035385711977','1532850035323864450','123456','皮蛋粥','3.20',1,'http://xxxx.jpg','2018-07-29 15:40:40','2018-07-29 15:40:40');

/*Table structure for table `order_master` */

DROP TABLE IF EXISTS `order_master`;

CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态，默认0未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `idx_buyer_openid` (`buyer_openid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单表';

/*Data for the table `order_master` */

insert  into `order_master`(`order_id`,`buyer_name`,`buyer_phone`,`buyer_address`,`buyer_openid`,`order_amount`,`order_status`,`pay_status`,`create_time`,`update_time`) values ('1532785814571501917','fy','1234567890','ctgu','110110','20.00',1,1,'2018-07-29 13:39:07','2019-07-28 15:52:03'),('1532786142782800580','fy','1234567890','ctgu','110110','70.40',2,0,'2018-07-28 21:55:42','2019-07-28 15:52:05'),('1532846228694169870','wyx','13609068793','ctgu','ewewen12f1d2f1d454fd','3.20',2,0,'2018-07-29 14:37:08','2019-07-28 15:52:06'),('1532846355056462752','xab','13609068793','ctgu','ewewen12f1d2f1d454fd','3.20',2,0,'2018-07-29 14:39:15','2019-07-28 15:52:06'),('1532847001800649250','ys','13609068793','ctgu','ewewen12f1d2f1d454fd','3.20',0,0,'2018-07-29 14:50:03','2019-07-28 15:52:21'),('1532847049273890840','ys','13609068793','湖北省宜昌市三峡大学','ewewen12f1d2f1d454fd','3.20',1,0,'2018-07-29 14:58:12','2019-07-28 15:52:36'),('1532849914322167585','ys','13609068793','西安市','ewewen12f1d2f1d454fd','3.20',0,0,'2018-07-29 15:39:57','2019-07-28 15:53:18'),('1532850035323864450','ys','13609068793','枝江','ewewen12f1d2f1d454fd','3.20',0,0,'2018-07-29 15:40:40','2019-07-28 15:53:35');

/*Table structure for table `product_category` */

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE KEY `uqe_category_type` (`category_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='类目表';

/*Data for the table `product_category` */

insert  into `product_category`(`category_id`,`category_name`,`category_type`,`create_time`,`update_time`) values (1,'热销榜',0,'2018-07-28 10:02:48','2018-08-04 21:32:21'),(2,'男生最爱',1,'2018-07-28 10:07:55','2018-07-28 21:30:16'),(3,'女生最爱',2,'2018-07-28 10:30:10','2018-08-04 20:45:37'),(4,'男生专享',3,'2018-07-28 11:03:14','2018-08-04 20:45:39'),(13,'人气榜',100,'2018-08-04 20:59:53','2019-07-28 16:44:09'),(21,'测试',103,'2018-08-04 21:32:37','2018-08-11 13:34:55'),(25,'ys最爱',102,'2018-08-11 13:35:15','2019-07-28 16:44:30');

/*Table structure for table `product_info` */

DROP TABLE IF EXISTS `product_info`;

CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `product_status` int(11) NOT NULL COMMENT '商品状态',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品表';

/*Data for the table `product_info` */

insert  into `product_info`(`product_id`,`product_name`,`product_price`,`product_stock`,`product_description`,`product_icon`,`category_type`,`create_time`,`update_time`,`product_status`) values ('123456','皮蛋粥','6.67',55,'很好喝的粥','http://img3.imgtn.bdimg.com/it/u=3648971941,3500022642&fm=27&gp=0.jpg',1,'2018-08-04 19:18:37','2018-08-11 18:29:38',0),('123457','皮皮虾123','3.20',102,'很好吃的虾','https://cdn.haochu.com/d/file/20131120/efda5f1bfa2f1f8ab452837495447380.jpg',1,'2018-08-04 19:17:37','2018-08-04 19:40:39',0),('123458','芒果冰','20.00',204,'冰冰的很爽','http://i5.xiachufang.com/image/620/5febb4029f2911e38844b8ca3aeed2d7.jpg',2,'2018-07-28 21:31:33','2018-08-04 19:39:25',0),('1533382637167627934','潮汕牛肉丸','40.00',100,'好吃不上火','http://imgsrc.baidu.com/image/c0%3Dpixel_huitu%2C0%2C0%2C294%2C40/sign=90fa433207d79123f4ed9c34c44c3cee/e4dde71190ef76c6fd95e8119616fdfaaf516740.jpg',1,'2018-08-04 19:37:17','2018-08-04 19:43:37',0),('1533382895077138814','北京烤鸭','66.00',600,'打鸭子','https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4160105330,3932180633&fm=27&gp=0.jpg',2,'2018-08-04 19:41:35','2018-08-04 19:41:35',0),('1533382899077138814','跳得高','32.00',20,'nice','https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4160105330,3932180633&fm=27&gp=0.jpg',1,'2019-07-11 14:09:36','2019-07-28 14:10:45',0);

/*Table structure for table `seller_info` */

DROP TABLE IF EXISTS `seller_info`;

CREATE TABLE `seller_info` (
  `seller_id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `openid` varchar(64) NOT NULL COMMENT '微信openid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`seller_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='卖家信息表';

/*Data for the table `seller_info` */

insert  into `seller_info`(`seller_id`,`username`,`password`,`openid`,`create_time`,`update_time`) values ('1533436038586344602','admin','admin','abc','2018-08-05 10:27:18','2018-08-05 10:27:18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
