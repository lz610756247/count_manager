# SQL-Front 5.1  (Build 4.16)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: localhost    Database: sj_glxt
# ------------------------------------------------------
# Server version 5.5.20

#
# Source for table t_dingdan_detail
#

CREATE TABLE `t_dingdan_detail` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(255) NOT NULL DEFAULT '商品名称' COMMENT '商品名称',
  `sp_num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `sp_dw` varchar(20) NOT NULL DEFAULT '' COMMENT '单位',
  `sp_price` varchar(30) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `sp_bz` varchar(1000) NOT NULL DEFAULT '' COMMENT '备注信息',
  `sp_sf` double(11,5) NOT NULL DEFAULT '0.00000',
  `sp_gr` varchar(255) DEFAULT NULL COMMENT '安装工人',
  `sp_azf` double(11,5) DEFAULT NULL COMMENT '安装费',
  `dd_index` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_dingdan_detail
#

/*!40000 ALTER TABLE `t_dingdan_detail` DISABLE KEYS */;
INSERT INTO `t_dingdan_detail` VALUES (2,'桌子',100,'张','45','',4500,'刘志',100,3);
/*!40000 ALTER TABLE `t_dingdan_detail` ENABLE KEYS */;

#
# Source for table t_dingdan_main
#

CREATE TABLE `t_dingdan_main` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `dd_name` varchar(255) NOT NULL DEFAULT '' COMMENT '客户名称',
  `dd_phone` varchar(20) DEFAULT '联系点火' COMMENT '联系电话',
  `dd_jsr` varchar(255) NOT NULL DEFAULT '2014-1-1' COMMENT '经手人',
  `dd_isfinish` varchar(255) NOT NULL DEFAULT 'false' COMMENT '是否完成',
  `create_time` date NOT NULL DEFAULT '0000-00-00' COMMENT '生成时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_dingdan_main
#

/*!40000 ALTER TABLE `t_dingdan_main` DISABLE KEYS */;
INSERT INTO `t_dingdan_main` VALUES (3,'王五','13211111111','刘攀','false','2014-05-11');
/*!40000 ALTER TABLE `t_dingdan_main` ENABLE KEYS */;

#
# Source for table t_jinhuo_detail
#

CREATE TABLE `t_jinhuo_detail` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(255) NOT NULL DEFAULT '商品名称' COMMENT '商品名称',
  `sp_num` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `sp_dw` varchar(20) NOT NULL DEFAULT '' COMMENT '单位',
  `sp_price` varchar(30) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `sp_bz` varchar(1000) NOT NULL DEFAULT '' COMMENT '备注信息',
  `sp_sf` double(11,5) NOT NULL DEFAULT '0.00000',
  `jh_index` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_jinhuo_detail
#

/*!40000 ALTER TABLE `t_jinhuo_detail` DISABLE KEYS */;
INSERT INTO `t_jinhuo_detail` VALUES (2,'木材',1,'方量','11','',1,4);
INSERT INTO `t_jinhuo_detail` VALUES (4,'木材',50,'方量','100','',5000,6);
INSERT INTO `t_jinhuo_detail` VALUES (6,'木材',50,'方量','89','',4450,6);
INSERT INTO `t_jinhuo_detail` VALUES (7,'原木料',60,'方量','100','',5000,7);
INSERT INTO `t_jinhuo_detail` VALUES (8,'红木 ',20,'方量','1002000','',2000,8);
/*!40000 ALTER TABLE `t_jinhuo_detail` ENABLE KEYS */;

#
# Source for table t_jinhuo_main
#

CREATE TABLE `t_jinhuo_main` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `jh_name` varchar(255) NOT NULL DEFAULT '' COMMENT '供货商名称',
  `jh_phone` varchar(20) DEFAULT '联系点火' COMMENT '联系电话',
  `jh_time` varchar(255) NOT NULL DEFAULT '2014-1-1' COMMENT '进货时间',
  `create_time` date NOT NULL DEFAULT '0000-00-00' COMMENT '生成时间',
  `id_czy` int(11) NOT NULL DEFAULT '-1' COMMENT '操作员Id',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_jinhuo_main
#

/*!40000 ALTER TABLE `t_jinhuo_main` DISABLE KEYS */;
INSERT INTO `t_jinhuo_main` VALUES (4,'张三','123','2014-4-1','2014-04-18',-1);
INSERT INTO `t_jinhuo_main` VALUES (6,'李四','13278788788','2014-05-11','2014-05-11',-1);
INSERT INTO `t_jinhuo_main` VALUES (7,'陈勇','135','2014-05-20','2014-05-20',-1);
INSERT INTO `t_jinhuo_main` VALUES (8,'日新达 ','13134631312','2014-09-18','2014-09-18',-1);
/*!40000 ALTER TABLE `t_jinhuo_main` ENABLE KEYS */;

#
# Source for table t_xs_d
#

CREATE TABLE `t_xs_d` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `jg_wp` varchar(255) DEFAULT NULL,
  `jg_sl` double(11,3) DEFAULT '0.000',
  `jg_dj` double(11,3) DEFAULT '0.000',
  `jg_bz` varchar(255) DEFAULT NULL,
  `jg_yt` varchar(255) NOT NULL DEFAULT '正常',
  `jg_je` double(11,3) NOT NULL DEFAULT '0.000',
  `time` date NOT NULL DEFAULT '0000-00-00',
  `xs_Id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

#
# Dumping data for table t_xs_d
#

/*!40000 ALTER TABLE `t_xs_d` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_xs_d` ENABLE KEYS */;

#
# Source for table t_xs_y
#

CREATE TABLE `t_xs_y` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(255) NOT NULL DEFAULT '0000-00',
  `yg_id` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table t_xs_y
#

/*!40000 ALTER TABLE `t_xs_y` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_xs_y` ENABLE KEYS */;

#
# Source for table t_yg
#

CREATE TABLE `t_yg` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `yg_xm` varchar(255) NOT NULL DEFAULT '姓名',
  `yg_pw` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `yg_dh` varchar(255) DEFAULT NULL,
  `yg_zz` varchar(255) DEFAULT NULL,
  `yg_zw` varchar(255) NOT NULL DEFAULT '职务',
  `yg_bz` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_yg
#

/*!40000 ALTER TABLE `t_yg` DISABLE KEYS */;
INSERT INTO `t_yg` VALUES (7,'张国庆','123','190','','普通员工','');
INSERT INTO `t_yg` VALUES (8,'刘志','123','186','','业务管理员','');
INSERT INTO `t_yg` VALUES (9,'刘世杰','123','130','','工资管理员','');
INSERT INTO `t_yg` VALUES (11,'盘文静','','123','','普通员工','');
INSERT INTO `t_yg` VALUES (12,'石瑾','123','123','','系统管理员','');
/*!40000 ALTER TABLE `t_yg` ENABLE KEYS */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
