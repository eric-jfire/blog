/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2016-02-15 20:34:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text_content` text,
  `html_content` text,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `markdown` tinyint(4) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `imgs` varchar(6000) DEFAULT NULL COMMENT '该文章中所有的图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('16', '\n        <p>我想的就是你好，哈哈哈</p><p><br></p>', '\n        <p>我想的就是你好，哈哈哈</p><p><br></p>', '2016-02-12 23:32:40', '2016-02-13 23:34:50', '0', '0', null, '撒大大', '');
INSERT INTO `article` VALUES ('17', '\n#一级标题\n##二级标题\n\n+ 列表\n + 二级列表\n \n|年龄|学籍|住址|\n|-|-|-|\n|12|本省|天鹅湾|\n\n```java\npublic void main(){\n System.out.println(\"sdadsasda\");\n}\n```\n\n```seq\ntitle: 这是一个序列图测试\n林斌->小朋友: 亲一个\n小朋友->林斌: 脱衣服\n```', null, '2016-02-13 10:56:49', '2016-02-14 00:14:39', '0', '1', null, '这是markdown的测试文章', ',');
INSERT INTO `article` VALUES ('18', '%23%E8%AE%A9%E6%88%91%E4%BB%AC%E6%9D%A5%E6%B5%8B%E8%AF%95%0A%23%23%E5%93%88%E5%93%88%E5%93%88', null, '2016-02-14 10:25:40', '2016-02-14 10:25:40', '0', '1', null, 'wwqeqwe', '');
INSERT INTO `article` VALUES ('19', '#没有错', null, '2016-02-14 10:27:10', '2016-02-14 10:58:31', '0', '1', null, '即使以前', ',');
INSERT INTO `article` VALUES ('20', '我的生撒打萨达撒的奥迪啊是', null, '2016-02-14 10:27:59', '2016-02-14 10:27:59', '0', '1', null, '撒打算的', '');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'eric', '1e7a727b3ba846ae5d7f28453602d886');
