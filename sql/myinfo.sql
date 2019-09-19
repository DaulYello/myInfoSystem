DROP DATABASE IF EXISTS myinfo;
CREATE DATABASE IF NOT EXISTS myinfo DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE myinfo;

SET NAMES utf8mb4;
-- 禁用外键约束
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `sex` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '性别(字典)',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色id(多个逗号隔开)',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id(多个逗号隔开)',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '状态(字典)',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '管理员表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_user` VALUES (1, '1', 'admin', '1d6b1208c7d151d335790276a18e3d99', 'q6taw', '猿梦', '2018-11-16 00:00:00', 'M', 'hs183@qq.com', '18200000000', '1', 27, 'ENABLE', '2016-01-29 08:49:53', NULL, '2018-12-28 22:52:24', 24, 25);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL COMMENT '主键id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'url地址',
  `sort` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `menu_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否是菜单(字典)',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'ENABLE' COMMENT '菜单状态(字典)',
  `new_page_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否打开新页面的标识(字典)',
  `open_flag` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否打开(字典)',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_menu` VALUES (105, 'system', '0', '[0],', '系统管理', 'layui-icon layui-icon-set', '#', 20, 1, 'Y', NULL, 'ENABLE', NULL, '1', NULL, '2019-03-29 16:32:27', NULL, 1);
INSERT INTO `sys_menu` VALUES (120, 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', NULL, '/menu/add', 1, 3, 'N', NULL, 'ENABLE', NULL, '0', NULL, '2019-03-29 16:32:27', NULL, 1);
INSERT INTO `sys_menu` VALUES (119, 'menu', 'system', '[0],[system],', '菜单管理', NULL, '/menu', 4, 2, 'Y', NULL, 'ENABLE', NULL, '0', NULL, '2019-03-29 16:32:27', NULL, 1);


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL COMMENT '主键id',
  `pid` bigint(20) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '提示',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 0, '超级管理员', 'administrator', 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (5, 1, '临时', 'temp', 2, NULL, NULL, NULL, NULL, NULL);


-- ----------------------------
-- Table structure for sys_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_relation`;
CREATE TABLE `sys_relation`  (
  `relation_id` bigint(20) NOT NULL COMMENT '主键',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`relation_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_relation` VALUES (1135205146879512577, 105, 1);
INSERT INTO `sys_relation` VALUES (1135205147068256258, 120, 1);


DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` bigint(20) DEFAULT 0 COMMENT '父部门id',
  `pids` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '父级ids',
  `simple_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '简称',
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '全称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` (pid,pids,simple_name,full_name,description,VERSION,sort) VALUES (0, '[0],', '总公司', '总公司', '', NULL, 1);
INSERT INTO `sys_dept` (pid,pids,simple_name,full_name,description,VERSION,sort) VALUES (24, '[0],[24],', '开发部', '开发部', '', NULL, 2);
INSERT INTO `sys_dept` (pid,pids,simple_name,full_name,description,VERSION,sort) VALUES (24, '[0],[24],', '运营部', '运营部', '', NULL, 3);
INSERT INTO `sys_dept` (pid,pids,simple_name,full_name,description,VERSION,sort) VALUES (24, '[0],[24],', '战略部', '战略部', '', NULL, 4);


-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `dict_type_id` bigint(20) NOT NULL COMMENT '所属字典类型的id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典名称',
  `parent_id` bigint(20) NOT NULL COMMENT '上级代码id',
  `parent_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所有上级id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'ENABLE' COMMENT '状态（字典）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典的描述',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '基础字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (1, 'M', '男', 0, '[0]', 'ENABLE', NULL, '', '2019-03-14 17:11:00', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (1, 'F', '女', 0, '[0]', 'ENABLE', NULL, '', '2019-03-14 17:11:10', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (2, 'ENABLE', '启用', 0, '[0]', 'ENABLE', NULL, '', '2019-03-14 17:11:27', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (2, 'DISABLE', '禁用', 0, '[0]', 'ENABLE', NULL, '', '2019-03-14 17:11:40', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (3, 'ENABLE', '启用', 0, '[0]', 'ENABLE', NULL, '', '2019-03-14 17:12:00', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (3, 'FREEZE', '冻结', 0, '[0]', 'ENABLE', 1, '', '2019-03-14 17:12:10', '2019-03-16 10:56:36', 1, 1);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (3, 'DELETED', '已删除', 0, '[0]', 'ENABLE', -1221, '', '2019-03-14 17:12:22', '2019-03-16 10:56:53', 1, 1);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (4, 'Y', '删除', 0, '[0]', 'ENABLE', 23333, '', '2019-03-14 17:12:36', '2019-03-16 10:58:53', 1, 1);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (4, 'N', '未删除', 0, '[0]', 'ENABLE', 1212211221, '', '2019-03-14 17:12:44', '2019-03-16 10:59:03', 1, 1);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (3, '11212', '122', 0, '[0]', 'ENABLE', 1212, '122112', '2019-03-16 10:56:30', NULL, 1, NULL);
INSERT INTO `sys_dict` (dict_type_id,CODE,NAME,parent_id,parent_ids,STATUS,sort,description,create_time,update_time,create_user,update_user)VALUES (4, '12312', '3123123', 1106120968910258177, '[0],[1106120968910258177]', 'ENABLE', 123, '', '2019-03-16 10:59:14', NULL, 1, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典类型id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典类型编码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典类型名称',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '字典描述',
  `system_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否是系统字典，Y-是，N-否',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'ENABLE' COMMENT '状态(字典)',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) DEFAULT NULL COMMENT '添加时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dict_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` (CODE,NAME,description,system_flag,STATUS,sort,create_time,create_user,update_time,update_user)VALUES ('SEX', '性别', '', 'Y', 'ENABLE', 4, '2019-03-14 17:09:43', 1, NULL, NULL);
INSERT INTO `sys_dict_type` (CODE,NAME,description,system_flag,STATUS,sort,create_time,create_user,update_time,update_user)VALUES ('STATUS', '状态', '', 'Y', 'ENABLE', 3, '2019-03-14 17:09:57', 1, NULL, NULL);
INSERT INTO `sys_dict_type` (CODE,NAME,description,system_flag,STATUS,sort,create_time,create_user,update_time,update_user)VALUES ('ACCOUNT_STATUS', '账号状态', '', 'Y', 'ENABLE', 21112, '2019-03-14 17:10:10', 1, '2019-03-16 10:56:15', 1);
INSERT INTO `sys_dict_type` (CODE,NAME,description,system_flag,STATUS,sort,create_time,create_user,update_time,update_user)VALUES ('DEL_FLAG', '是否删除', '', 'Y', 'ENABLE', 2, '2019-03-14 17:10:26', 1, '2019-03-27 16:26:31', 1);

DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `operation_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日志类型(字典)',
  `log_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日志名称',
  `user_id` bigint(65) DEFAULT NULL COMMENT '用户id',
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '类名称',
  `method` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '方法名称',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `succeed` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否成功(字典)',
  `message` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`operation_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `login_log_id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '日志名称',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '管理员id',
  `create_time` DATETIME(0) DEFAULT NULL COMMENT '创建时间',
  `succeed` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '是否执行成功',
  `message` TEXT CHARACTER SET utf8 COLLATE utf8_bin COMMENT '具体消息',
  `ip_address` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (`login_log_id`) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '登录记录' ROW_FORMAT = DYNAMIC;
