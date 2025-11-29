/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : localhost:3306
 Source Schema         : sys_wygl

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 22/03/2024 17:12:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fee_park
-- ----------------------------
DROP TABLE IF EXISTS `fee_park`;
CREATE TABLE `fee_park`  (
  `park_fee_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `park_id` int(11) NULL DEFAULT NULL COMMENT '车位id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `pay_park_month` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费年月',
  `pay_park_money` decimal(18, 2) NULL DEFAULT NULL COMMENT '缴费金额',
  `pay_park_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费状态 0：未缴费，1：已缴费',
  `pay_park_time` datetime(0) NULL DEFAULT NULL COMMENT '缴费时间',
  PRIMARY KEY (`park_fee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fee_park
-- ----------------------------
INSERT INTO `fee_park` VALUES (19, 9, 19, '2024-01', 123.00, '1', '2024-03-15 18:32:15');
INSERT INTO `fee_park` VALUES (20, 10, 22, '2024-03', 20.00, '1', NULL);

-- ----------------------------
-- Table structure for fee_power
-- ----------------------------
DROP TABLE IF EXISTS `fee_power`;
CREATE TABLE `fee_power`  (
  `power_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `house_id` int(11) NULL DEFAULT NULL COMMENT '房屋id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `pay_power_month` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费年月',
  `pay_power_money` decimal(18, 2) NULL DEFAULT NULL COMMENT '缴费金额',
  `power_num` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表显',
  `pay_power_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费状态 0：未缴费，1：已缴费',
  `pay_power_time` datetime(0) NULL DEFAULT NULL COMMENT '缴费时间',
  PRIMARY KEY (`power_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fee_power
-- ----------------------------
INSERT INTO `fee_power` VALUES (12, 16, 19, '2024-03', 100.00, '100', '1', '2024-03-15 18:32:00');
INSERT INTO `fee_power` VALUES (13, 19, 22, '2024-03', 500.00, '600', '0', NULL);

-- ----------------------------
-- Table structure for fee_water
-- ----------------------------
DROP TABLE IF EXISTS `fee_water`;
CREATE TABLE `fee_water`  (
  `water_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `house_id` int(11) NULL DEFAULT NULL COMMENT '房屋id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `pay_water_month` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费年月',
  `pay_water_money` decimal(18, 2) NULL DEFAULT NULL COMMENT '缴费金额',
  `water_num` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表显',
  `pay_water_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '缴费状态 0：未缴费，1：已缴费',
  `pay_water_time` datetime(0) NULL DEFAULT NULL COMMENT '缴费时间',
  PRIMARY KEY (`water_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fee_water
-- ----------------------------
INSERT INTO `fee_water` VALUES (12, 16, 19, '2024-03', 100.00, '100', '0', NULL);
INSERT INTO `fee_water` VALUES (13, 19, 22, '2024-03', 200.00, '300', '1', NULL);

-- ----------------------------
-- Table structure for house_building
-- ----------------------------
DROP TABLE IF EXISTS `house_building`;
CREATE TABLE `house_building`  (
  `build_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '栋数id·',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '栋数类型 0：普通房，1：电梯房',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '栋数名称',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`build_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house_building
-- ----------------------------
INSERT INTO `house_building` VALUES (6, '0', 'A栋', 1);
INSERT INTO `house_building` VALUES (7, '1', 'B栋', 2);
INSERT INTO `house_building` VALUES (8, '0', 'C栋', 3);
INSERT INTO `house_building` VALUES (9, '1', 'D栋', 4);
INSERT INTO `house_building` VALUES (10, '1', 'E栋', 5);
INSERT INTO `house_building` VALUES (11, '1', 'F栋', 6);
INSERT INTO `house_building` VALUES (14, '0', 'G栋', 1);

-- ----------------------------
-- Table structure for house_list
-- ----------------------------
DROP TABLE IF EXISTS `house_list`;
CREATE TABLE `house_list`  (
  `house_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '房屋id',
  `unit_id` int(11) NULL DEFAULT NULL COMMENT '单元id ',
  `house_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '房屋编号',
  `house_area` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '房屋面积',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用状态 0：未使用， 1：已使用',
  PRIMARY KEY (`house_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house_list
-- ----------------------------
INSERT INTO `house_list` VALUES (16, 15, 'A-101', '90', '1');
INSERT INTO `house_list` VALUES (17, 15, 'A-102', '100', '1');
INSERT INTO `house_list` VALUES (18, 15, 'A-103', '120', '1');
INSERT INTO `house_list` VALUES (19, 17, 'B-101', '90', '1');
INSERT INTO `house_list` VALUES (20, 17, 'B-102', '120', '0');
INSERT INTO `house_list` VALUES (21, 17, 'B-103', '110', '0');
INSERT INTO `house_list` VALUES (22, 16, 'A-101', '100', '0');
INSERT INTO `house_list` VALUES (23, 16, 'A-102', '120', '1');
INSERT INTO `house_list` VALUES (24, 18, 'B-101', '120', '0');

-- ----------------------------
-- Table structure for house_unit
-- ----------------------------
DROP TABLE IF EXISTS `house_unit`;
CREATE TABLE `house_unit`  (
  `unit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单元id ',
  `build_id` int(11) NULL DEFAULT NULL COMMENT '栋数id·',
  `unit_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '单元名称',
  PRIMARY KEY (`unit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house_unit
-- ----------------------------
INSERT INTO `house_unit` VALUES (15, 6, '1单元');
INSERT INTO `house_unit` VALUES (16, 6, '2单元');
INSERT INTO `house_unit` VALUES (17, 7, '1单元');
INSERT INTO `house_unit` VALUES (18, 7, '2单元');
INSERT INTO `house_unit` VALUES (19, 8, '1单元');
INSERT INTO `house_unit` VALUES (20, 8, '2单元');
INSERT INTO `house_unit` VALUES (21, 10, '1单元');
INSERT INTO `house_unit` VALUES (22, 10, '2单元');
INSERT INTO `house_unit` VALUES (23, 9, '1单元');
INSERT INTO `house_unit` VALUES (24, 9, '2单元');

-- ----------------------------
-- Table structure for live_house
-- ----------------------------
DROP TABLE IF EXISTS `live_house`;
CREATE TABLE `live_house`  (
  `live_house_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `house_id` int(11) NULL DEFAULT NULL COMMENT '房屋id',
  `use_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用状态 0:使用中，1：解绑',
  PRIMARY KEY (`live_house_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of live_house
-- ----------------------------
INSERT INTO `live_house` VALUES (24, 19, 16, '1');
INSERT INTO `live_house` VALUES (25, 19, 16, '1');
INSERT INTO `live_house` VALUES (26, 19, 16, '0');
INSERT INTO `live_house` VALUES (27, 20, 18, '0');
INSERT INTO `live_house` VALUES (28, 21, 23, '0');
INSERT INTO `live_house` VALUES (29, 22, 19, '0');

-- ----------------------------
-- Table structure for live_park
-- ----------------------------
DROP TABLE IF EXISTS `live_park`;
CREATE TABLE `live_park`  (
  `live_park_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `park_id` int(11) NULL DEFAULT NULL COMMENT '车位id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `live_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用状态 0:使用中，1：解绑或退车位',
  PRIMARY KEY (`live_park_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of live_park
-- ----------------------------
INSERT INTO `live_park` VALUES (17, 9, 19, '1');
INSERT INTO `live_park` VALUES (18, 9, 19, '0');
INSERT INTO `live_park` VALUES (19, 10, 22, '0');

-- ----------------------------
-- Table structure for live_role
-- ----------------------------
DROP TABLE IF EXISTS `live_role`;
CREATE TABLE `live_role`  (
  `liv_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  PRIMARY KEY (`liv_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of live_role
-- ----------------------------
INSERT INTO `live_role` VALUES (41, 15, 19);
INSERT INTO `live_role` VALUES (43, 15, 20);
INSERT INTO `live_role` VALUES (44, 15, 21);
INSERT INTO `live_role` VALUES (45, 6, 23);
INSERT INTO `live_role` VALUES (46, 15, 24);
INSERT INTO `live_role` VALUES (47, 15, 25);
INSERT INTO `live_role` VALUES (48, 15, 22);

-- ----------------------------
-- Table structure for live_user
-- ----------------------------
DROP TABLE IF EXISTS `live_user`;
CREATE TABLE `live_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '业主id',
  `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账号',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业主电话',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别 0：男，1：女',
  `login_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '账户状态 0:启用，1：禁用',
  `is_account_non_expired` int(255) NULL DEFAULT NULL COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` int(255) NULL DEFAULT NULL COMMENT '帐户是否被锁定(1 未过期，0已过期)',
  `is_credentials_non_expired` int(255) NULL DEFAULT NULL COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` int(255) NULL DEFAULT NULL COMMENT '帐户是否可用(1 可用，0 删除用户)',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of live_user
-- ----------------------------
INSERT INTO `live_user` VALUES (19, 'zhangsan', '123456', '0', '张三', '$2a$10$gufgNJJy0E3M8IzwwckNduBb82/oxhCzDPUq2MRjrTYhlQSj2RslW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (20, 'wangwu', '123456789', '0', '王五', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (21, 'zhaoliu', '132456', '0', '赵六', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (22, 'lisi', '1730363921', '0', '李四', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (23, 'cs', '111111', '0', '测试', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (24, 'zs', '123456', '0', 'zs', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);
INSERT INTO `live_user` VALUES (25, 'zss1', '123456', '0', 'zss', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '0', 1, 1, 1, 1);

-- ----------------------------
-- Table structure for parking_list
-- ----------------------------
DROP TABLE IF EXISTS `parking_list`;
CREATE TABLE `parking_list`  (
  `park_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '车位id',
  `park_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '车位类型 0:地上，1：地下',
  `park_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '车位名称',
  `park_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '车位使用状态 0：未使用，1：已使用',
  `park_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`park_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of parking_list
-- ----------------------------
INSERT INTO `parking_list` VALUES (9, '0', '1001', '1', 1);
INSERT INTO `parking_list` VALUES (10, '0', '1002', '1', 2);
INSERT INTO `parking_list` VALUES (11, '1', '1003', '0', 3);
INSERT INTO `parking_list` VALUES (12, '1', '1004', '0', 4);
INSERT INTO `parking_list` VALUES (13, '0', '1005', '0', 5);
INSERT INTO `parking_list` VALUES (14, '0', '1006', '0', 6);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父级菜单id',
  `menu_label` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限字段',
  `name` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由地址',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路由',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型 0：目录 1：菜单 2按钮',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `parent_name` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级菜单名称',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (3, 0, '系统管理', 'sys:system', '', '/system', '', '0', 'el-icon-menu', 1, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (4, 3, '员工管理', 'sys:sysUserList', 'system', '/sysUserList', '/system/sysUserList', '1', 'el-icon-s-custom', 1, '', '系统管理');
INSERT INTO `sys_menu` VALUES (5, 3, '角色管理', 'sys:sysRoleList', 'sysRoleList', '/sysRoleList', '/system/sysRoleList', '1', 'el-icon-date', 2, '', '系统管理');
INSERT INTO `sys_menu` VALUES (6, 3, '权限管理', 'sys:sysMenuList', 'sysMenuList', '/sysMenuList', '/system/sysMenuList', '1', 'el-icon-school', 3, '', '系统管理');
INSERT INTO `sys_menu` VALUES (7, 4, '新增', 'sys:user:add', '', '', '', '2', '', 1, '', '员工管理');
INSERT INTO `sys_menu` VALUES (8, 4, '编辑', 'sys:user:edit', '', '', '', '2', '', 2, '', '员工管理');
INSERT INTO `sys_menu` VALUES (9, 4, '删除', 'sys:user:delete', '', '', '', '2', '', 3, '', '员工管理');
INSERT INTO `sys_menu` VALUES (10, 5, '新增', 'sys:role:add', '', '', '', '2', '', 1, '', '角色管理');
INSERT INTO `sys_menu` VALUES (11, 5, '编辑', 'sys:role:edit', '', '', '', '2', '', 2, '', '角色管理');
INSERT INTO `sys_menu` VALUES (12, 5, '删除', 'sys:role:delete', '', '', '', '2', '', 3, '', '角色管理');
INSERT INTO `sys_menu` VALUES (13, 6, '新增', 'sys:menu:add', '', '', '', '2', '', 1, '', '权限管理');
INSERT INTO `sys_menu` VALUES (14, 6, '编辑', 'sys:menu:edit', '', '', '', '2', '', 2, '', '权限管理');
INSERT INTO `sys_menu` VALUES (15, 6, '删除', 'sys:menu:delete', '', '', '', '2', '', 3, '', '权限管理');
INSERT INTO `sys_menu` VALUES (16, 0, '房屋管理', 'sys:home:index', '', '/sysHouse', '', '0', 'el-icon-s-help', 2, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (17, 16, '房屋列表', 'sys:house:list', 'sysHouseList', '/sysHouseList', '/house/houseList', '1', 'el-icon-mobile', 3, '', '房屋管理');
INSERT INTO `sys_menu` VALUES (18, 17, '新增', 'sys:house:add', '', '', '', '2', '', 1, '', '房屋列表');
INSERT INTO `sys_menu` VALUES (19, 17, '编辑', 'sys:house:edit', '', '', '', '2', '', 2, '', '房屋列表');
INSERT INTO `sys_menu` VALUES (20, 17, '删除', 'sys:house:delete', '', '', '', '2', '', 3, '', '房屋列表');
INSERT INTO `sys_menu` VALUES (21, 16, '单元管理', 'sys:houseUnit', 'houseUnit', '/houseUnit', '/house/houseUnit', '1', 'table', 2, '', '房屋管理');
INSERT INTO `sys_menu` VALUES (22, 16, '栋数管理', 'sys:houseBuilding', 'houseBuilding', '/houseBuilding', '/house/houseBuilding', '1', 'table', 1, '', '房屋管理');
INSERT INTO `sys_menu` VALUES (23, 22, '新增', 'sys:houseBuilding:add', '', '', '', '2', '', 1, '', '栋数管理');
INSERT INTO `sys_menu` VALUES (24, 22, '编辑', 'sys:houseBuilding:edit', '', '', '', '2', '', 2, '', '栋数管理');
INSERT INTO `sys_menu` VALUES (25, 22, '删除', 'sys:houseBuilding:delete', '', '', '', '2', '', 3, '', '栋数管理');
INSERT INTO `sys_menu` VALUES (26, 21, '新增', 'sys:houseUnit:add', '', '', '', '2', '', 1, '', '单元管理');
INSERT INTO `sys_menu` VALUES (27, 21, '编辑', 'sys:houseUnit:edit', '', '', '', '2', '', 2, '', '单元管理');
INSERT INTO `sys_menu` VALUES (28, 21, '删除', 'sys:houseUnit:delete', '', '', '', '2', '', 3, '', '单元管理');
INSERT INTO `sys_menu` VALUES (29, 0, '车位管理', 'sys:sysPark', '', '/sysPark', '', '0', 'el-icon-money', 3, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (30, 29, '车位管理', 'sys:parkList', 'parkList', '/parkList', '/park/parkList', '1', 'el-icon-money', 1, '', '车位管理');
INSERT INTO `sys_menu` VALUES (31, 30, '新增', 'sys:parkList:add', '', '', '', '2', '', 1, '', '车位管理');
INSERT INTO `sys_menu` VALUES (32, 30, '编辑', 'sys:parkList:edit', '', '', '', '2', '', 2, '', '车位管理');
INSERT INTO `sys_menu` VALUES (33, 30, '删除', 'sys:parkList:delete', '', '', '', '2', '', 3, '', '车位管理');
INSERT INTO `sys_menu` VALUES (34, 0, '业主管理', 'sys:live', '', '/live', '', '0', 'el-icon-s-grid', 4, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (35, 34, '业主列表', 'sys:liveUser', 'liveUser', '/liveUser', '/live/liveUser', '1', 'el-icon-s-data', 1, '', '业主管理');
INSERT INTO `sys_menu` VALUES (36, 35, '新增', 'sys:liveUser:add', '', '', '', '2', '', 1, '', '业主列表');
INSERT INTO `sys_menu` VALUES (37, 35, '编辑', 'sys:liveUser:edit', '', '', '', '2', '', 2, '', '业主列表');
INSERT INTO `sys_menu` VALUES (38, 35, '删除', 'sys:liveUser:delete', '', '', '', '2', '', 3, '', '业主列表');
INSERT INTO `sys_menu` VALUES (39, 35, '分配房屋', 'sys:liveUser:assignHome', '', '', '', '2', '', 4, '', '业主列表');
INSERT INTO `sys_menu` VALUES (40, 35, '分配车位', 'sys:liveUser:assignCar', '', '', '', '2', '', 6, '', '业主列表');
INSERT INTO `sys_menu` VALUES (41, 35, '退房', 'sys:liveUser:returnHome', '', '', '', '2', '', 7, '', '业主列表');
INSERT INTO `sys_menu` VALUES (42, 35, '退车位', 'sys:liveUser:returnCar', '', '', '', '2', '', 8, '', '业主列表');
INSERT INTO `sys_menu` VALUES (43, 0, '收费管理', 'sys:fee', '', '/fee', '', '0', 'el-icon-s-open', 5, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (44, 43, '电费管理', 'sys:feePower', 'feePower', '/feePower', '/fee/feePower', '1', 'el-icon-picture', 1, '', '收费管理');
INSERT INTO `sys_menu` VALUES (45, 43, '水费管理', 'sys:feeWater', 'feeWater', '/feeWater', '/fee/feeWater', '1', 'el-icon-s-data', 2, '', '收费管理');
INSERT INTO `sys_menu` VALUES (46, 43, '停车管理', 'sys:feePark', 'feePark', '/feePark', '/fee/feePark', '1', 'el-icon-s-order', 3, '', '收费管理');
INSERT INTO `sys_menu` VALUES (47, 44, '新增', 'sys:feePower:add', '', '', '', '2', '', 1, '', '电费管理');
INSERT INTO `sys_menu` VALUES (48, 44, '编辑', 'sys:feePower:edit', '', '', '', '2', '', 2, '', '电费管理');
INSERT INTO `sys_menu` VALUES (49, 44, '删除', 'sys:feePower:delete', '', '', '', '2', '', 3, '', '电费管理');
INSERT INTO `sys_menu` VALUES (50, 45, '新增', 'sys:feeWater:add', '', '', '', '2', '', 1, '', '水费管理');
INSERT INTO `sys_menu` VALUES (51, 45, '编辑', 'sys:feeWater:edit', '', '', '', '2', '', 2, '', '水费管理');
INSERT INTO `sys_menu` VALUES (52, 45, '删除', 'sys:feeWater:delete', '', '', '', '2', '', 3, '', '水费管理');
INSERT INTO `sys_menu` VALUES (53, 46, '新增', 'sys:feePark:add', '', '', '', '2', '', 1, '', '停车管理');
INSERT INTO `sys_menu` VALUES (54, 46, '编辑', 'sys:feePark:edit', '', '', '', '2', '', 2, '', '停车管理');
INSERT INTO `sys_menu` VALUES (55, 46, '删除', 'sys:feePark:delete', '', '', '', '2', '', 3, '', '停车管理');
INSERT INTO `sys_menu` VALUES (56, 0, '投诉管理', 'sys:userComplain', '', '/userComplain', '', '0', 'el-icon-date', 6, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (57, 56, '投诉列表', 'sys:userComplainList', 'userComplainList', '/userComplainList', '/userComplain/userComplainList', '1', 'el-icon-edit-outline', 1, '', '投诉管理');
INSERT INTO `sys_menu` VALUES (58, 56, '我的投诉', 'sys:myUserComplainList', 'myUserComplainList', '/myUserComplainList', '/userComplain/myUserComplainList', '1', 'el-icon-menu', 2, '', '投诉管理');
INSERT INTO `sys_menu` VALUES (62, 58, '新增', 'sys:myUserComplainList:add', '', '', '', '2', '', 1, '', '我的投诉');
INSERT INTO `sys_menu` VALUES (63, 58, '编辑', 'sys:myUserComplainList:edit', '', '', '', '2', '', 2, '', '我的投诉');
INSERT INTO `sys_menu` VALUES (64, 58, '删除', 'sys:myUserComplainList:delete', '', '', '', '2', '', 3, '', '我的投诉');
INSERT INTO `sys_menu` VALUES (65, 0, '维修管理', 'sys:userRepair', '', '/userRepair', '', '0', 'el-icon-picture-outline', 7, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (66, 65, '维修列表', 'sys:userRepairList', 'userRepairList', '/userRepairList', '/userRepair/userRepairList', '1', 'el-icon-s-marketing', 1, '', '维修管理');
INSERT INTO `sys_menu` VALUES (67, 65, '我的维修', 'sys:myUserRepairList', 'myUserRepairList', '/myUserRepairList', '/userRepair/myUserRepairList', '1', 'el-icon-picture-outline', 2, '', '维修管理');
INSERT INTO `sys_menu` VALUES (68, 67, '新增', 'sys:userRepairList:add', '', '', '', '2', '', 1, '', '我的维修');
INSERT INTO `sys_menu` VALUES (69, 67, '编辑', 'sys:userRepairList:edit', '', '', '', '2', '', 2, '', '我的维修');
INSERT INTO `sys_menu` VALUES (70, 67, '删除', 'sys:userRepairList:delete', '', '', '', '2', '', 3, '', '我的维修');
INSERT INTO `sys_menu` VALUES (71, 0, '公告管理', 'sys:notice', '', '/notice', '', '0', 'el-icon-document-copy', 8, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (72, 71, '公告列表', 'sys:noticeList', 'noticeList', '/noticeList', '/notice/noticeList', '1', 'el-icon-s-marketing', 1, '', '公告管理');
INSERT INTO `sys_menu` VALUES (73, 72, '新增', 'sys:noticeList:add', '', '', '', '2', '', 1, '', '公告列表');
INSERT INTO `sys_menu` VALUES (74, 72, '编辑', 'sys:noticeList:edit', '', '', '', '2', '', 2, '', '公告列表');
INSERT INTO `sys_menu` VALUES (75, 72, '删除', 'sys:noticeList:delete', '', '', '', '2', '', 3, '', '公告列表');
INSERT INTO `sys_menu` VALUES (76, 72, '查看', 'sys:notice:look', '', '', '', '2', '', 4, '', '公告列表');
INSERT INTO `sys_menu` VALUES (77, 4, '分配角色', 'sys:user:assignRole', '', '', '', '2', '', 5, '', '员工管理');
INSERT INTO `sys_menu` VALUES (78, 5, '分配权限', 'sys:role:assignMenu', '', '', '', '2', '', 4, '', '角色管理');
INSERT INTO `sys_menu` VALUES (79, 66, '处理', 'sys:userRepairList:do', '', '', '', '2', '', 1, '', '维修列表');
INSERT INTO `sys_menu` VALUES (80, 57, '处理', 'sys:userComplainList:do', '', '', '', '2', '', 1, '', '投诉列表');
INSERT INTO `sys_menu` VALUES (81, 44, '缴费', 'sys:feePower:pay', '', '', '', '2', '', 4, '', '电费管理');
INSERT INTO `sys_menu` VALUES (82, 46, '缴费', 'sys:feePark:pay', '', '', '', '2', '', 4, '', '停车管理');
INSERT INTO `sys_menu` VALUES (83, 45, '缴费', 'sys:feeWater:pay', '', '', '', '2', '', 4, '', '水费管理');
INSERT INTO `sys_menu` VALUES (84, 0, '缴费记录', 'sys:feeList', '', '/feeList', '', '0', 'el-icon-c-scale-to-original', 6, '', '顶级菜单');
INSERT INTO `sys_menu` VALUES (85, 84, '我的电费', 'sys:myPowerFee', 'myPowerFee', '/myPowerFee', '/feeList/myPowerFee', '1', 'el-icon-date', 1, '', '缴费记录');
INSERT INTO `sys_menu` VALUES (86, 84, '我的水费', 'sys:myWaterFee', 'myWaterFee', '/myWaterFee', '/feeList/myWaterFee', '1', 'el-icon-s-shop', 2, '', '缴费记录');
INSERT INTO `sys_menu` VALUES (87, 84, '我的停车费', 'sys:myParkFee', 'myParkFee', '/myParkFee', '/feeList/myParkFee', '1', 'el-icon-s-finance', 3, '', '缴费记录');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标题',
  `notice_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (2, NULL, '公共卫生', '请大家不要乱扔垃圾', '2024-03-18 15:39:50');
INSERT INTO `sys_notice` VALUES (3, NULL, '测试', '测试', '2024-03-18 15:39:59');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(56) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员角色', '管理员角色');
INSERT INTO `sys_role` VALUES (2, '水费管理员', '手水费');
INSERT INTO `sys_role` VALUES (6, '测试', '测试');
INSERT INTO `sys_role` VALUES (15, '业主', '租住房子的人');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 745 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (34, 6, 10);
INSERT INTO `sys_role_menu` VALUES (35, 6, 12);
INSERT INTO `sys_role_menu` VALUES (36, 6, 13);
INSERT INTO `sys_role_menu` VALUES (37, 6, 3);
INSERT INTO `sys_role_menu` VALUES (38, 6, 5);
INSERT INTO `sys_role_menu` VALUES (39, 6, 6);
INSERT INTO `sys_role_menu` VALUES (48, 2, 7);
INSERT INTO `sys_role_menu` VALUES (49, 2, 77);
INSERT INTO `sys_role_menu` VALUES (50, 2, 10);
INSERT INTO `sys_role_menu` VALUES (51, 2, 78);
INSERT INTO `sys_role_menu` VALUES (52, 2, 13);
INSERT INTO `sys_role_menu` VALUES (53, 2, 3);
INSERT INTO `sys_role_menu` VALUES (54, 2, 4);
INSERT INTO `sys_role_menu` VALUES (55, 2, 5);
INSERT INTO `sys_role_menu` VALUES (56, 2, 6);
INSERT INTO `sys_role_menu` VALUES (613, 15, 22);
INSERT INTO `sys_role_menu` VALUES (614, 15, 23);
INSERT INTO `sys_role_menu` VALUES (615, 15, 24);
INSERT INTO `sys_role_menu` VALUES (616, 15, 25);
INSERT INTO `sys_role_menu` VALUES (617, 15, 29);
INSERT INTO `sys_role_menu` VALUES (618, 15, 30);
INSERT INTO `sys_role_menu` VALUES (619, 15, 31);
INSERT INTO `sys_role_menu` VALUES (620, 15, 32);
INSERT INTO `sys_role_menu` VALUES (621, 15, 33);
INSERT INTO `sys_role_menu` VALUES (622, 15, 43);
INSERT INTO `sys_role_menu` VALUES (623, 15, 44);
INSERT INTO `sys_role_menu` VALUES (624, 15, 47);
INSERT INTO `sys_role_menu` VALUES (625, 15, 48);
INSERT INTO `sys_role_menu` VALUES (626, 15, 49);
INSERT INTO `sys_role_menu` VALUES (627, 15, 81);
INSERT INTO `sys_role_menu` VALUES (628, 15, 45);
INSERT INTO `sys_role_menu` VALUES (629, 15, 50);
INSERT INTO `sys_role_menu` VALUES (630, 15, 51);
INSERT INTO `sys_role_menu` VALUES (631, 15, 52);
INSERT INTO `sys_role_menu` VALUES (632, 15, 83);
INSERT INTO `sys_role_menu` VALUES (633, 15, 46);
INSERT INTO `sys_role_menu` VALUES (634, 15, 53);
INSERT INTO `sys_role_menu` VALUES (635, 15, 54);
INSERT INTO `sys_role_menu` VALUES (636, 15, 55);
INSERT INTO `sys_role_menu` VALUES (637, 15, 82);
INSERT INTO `sys_role_menu` VALUES (638, 15, 56);
INSERT INTO `sys_role_menu` VALUES (639, 15, 57);
INSERT INTO `sys_role_menu` VALUES (640, 15, 80);
INSERT INTO `sys_role_menu` VALUES (641, 15, 58);
INSERT INTO `sys_role_menu` VALUES (642, 15, 62);
INSERT INTO `sys_role_menu` VALUES (643, 15, 63);
INSERT INTO `sys_role_menu` VALUES (644, 15, 64);
INSERT INTO `sys_role_menu` VALUES (645, 15, 65);
INSERT INTO `sys_role_menu` VALUES (646, 15, 66);
INSERT INTO `sys_role_menu` VALUES (647, 15, 79);
INSERT INTO `sys_role_menu` VALUES (648, 15, 67);
INSERT INTO `sys_role_menu` VALUES (649, 15, 68);
INSERT INTO `sys_role_menu` VALUES (650, 15, 69);
INSERT INTO `sys_role_menu` VALUES (651, 15, 70);
INSERT INTO `sys_role_menu` VALUES (652, 15, 71);
INSERT INTO `sys_role_menu` VALUES (653, 15, 72);
INSERT INTO `sys_role_menu` VALUES (654, 15, 73);
INSERT INTO `sys_role_menu` VALUES (655, 15, 74);
INSERT INTO `sys_role_menu` VALUES (656, 15, 75);
INSERT INTO `sys_role_menu` VALUES (657, 15, 76);
INSERT INTO `sys_role_menu` VALUES (658, 15, 84);
INSERT INTO `sys_role_menu` VALUES (659, 15, 85);
INSERT INTO `sys_role_menu` VALUES (660, 15, 86);
INSERT INTO `sys_role_menu` VALUES (661, 15, 87);
INSERT INTO `sys_role_menu` VALUES (662, 15, 16);
INSERT INTO `sys_role_menu` VALUES (663, 1, 3);
INSERT INTO `sys_role_menu` VALUES (664, 1, 4);
INSERT INTO `sys_role_menu` VALUES (665, 1, 7);
INSERT INTO `sys_role_menu` VALUES (666, 1, 8);
INSERT INTO `sys_role_menu` VALUES (667, 1, 9);
INSERT INTO `sys_role_menu` VALUES (668, 1, 77);
INSERT INTO `sys_role_menu` VALUES (669, 1, 5);
INSERT INTO `sys_role_menu` VALUES (670, 1, 10);
INSERT INTO `sys_role_menu` VALUES (671, 1, 11);
INSERT INTO `sys_role_menu` VALUES (672, 1, 12);
INSERT INTO `sys_role_menu` VALUES (673, 1, 78);
INSERT INTO `sys_role_menu` VALUES (674, 1, 6);
INSERT INTO `sys_role_menu` VALUES (675, 1, 13);
INSERT INTO `sys_role_menu` VALUES (676, 1, 14);
INSERT INTO `sys_role_menu` VALUES (677, 1, 15);
INSERT INTO `sys_role_menu` VALUES (678, 1, 16);
INSERT INTO `sys_role_menu` VALUES (679, 1, 17);
INSERT INTO `sys_role_menu` VALUES (680, 1, 18);
INSERT INTO `sys_role_menu` VALUES (681, 1, 19);
INSERT INTO `sys_role_menu` VALUES (682, 1, 20);
INSERT INTO `sys_role_menu` VALUES (683, 1, 21);
INSERT INTO `sys_role_menu` VALUES (684, 1, 26);
INSERT INTO `sys_role_menu` VALUES (685, 1, 27);
INSERT INTO `sys_role_menu` VALUES (686, 1, 28);
INSERT INTO `sys_role_menu` VALUES (687, 1, 22);
INSERT INTO `sys_role_menu` VALUES (688, 1, 23);
INSERT INTO `sys_role_menu` VALUES (689, 1, 24);
INSERT INTO `sys_role_menu` VALUES (690, 1, 25);
INSERT INTO `sys_role_menu` VALUES (691, 1, 29);
INSERT INTO `sys_role_menu` VALUES (692, 1, 30);
INSERT INTO `sys_role_menu` VALUES (693, 1, 31);
INSERT INTO `sys_role_menu` VALUES (694, 1, 32);
INSERT INTO `sys_role_menu` VALUES (695, 1, 33);
INSERT INTO `sys_role_menu` VALUES (696, 1, 34);
INSERT INTO `sys_role_menu` VALUES (697, 1, 35);
INSERT INTO `sys_role_menu` VALUES (698, 1, 36);
INSERT INTO `sys_role_menu` VALUES (699, 1, 37);
INSERT INTO `sys_role_menu` VALUES (700, 1, 38);
INSERT INTO `sys_role_menu` VALUES (701, 1, 39);
INSERT INTO `sys_role_menu` VALUES (702, 1, 40);
INSERT INTO `sys_role_menu` VALUES (703, 1, 41);
INSERT INTO `sys_role_menu` VALUES (704, 1, 42);
INSERT INTO `sys_role_menu` VALUES (705, 1, 43);
INSERT INTO `sys_role_menu` VALUES (706, 1, 44);
INSERT INTO `sys_role_menu` VALUES (707, 1, 47);
INSERT INTO `sys_role_menu` VALUES (708, 1, 48);
INSERT INTO `sys_role_menu` VALUES (709, 1, 49);
INSERT INTO `sys_role_menu` VALUES (710, 1, 81);
INSERT INTO `sys_role_menu` VALUES (711, 1, 45);
INSERT INTO `sys_role_menu` VALUES (712, 1, 50);
INSERT INTO `sys_role_menu` VALUES (713, 1, 51);
INSERT INTO `sys_role_menu` VALUES (714, 1, 52);
INSERT INTO `sys_role_menu` VALUES (715, 1, 83);
INSERT INTO `sys_role_menu` VALUES (716, 1, 46);
INSERT INTO `sys_role_menu` VALUES (717, 1, 53);
INSERT INTO `sys_role_menu` VALUES (718, 1, 54);
INSERT INTO `sys_role_menu` VALUES (719, 1, 55);
INSERT INTO `sys_role_menu` VALUES (720, 1, 82);
INSERT INTO `sys_role_menu` VALUES (721, 1, 56);
INSERT INTO `sys_role_menu` VALUES (722, 1, 57);
INSERT INTO `sys_role_menu` VALUES (723, 1, 80);
INSERT INTO `sys_role_menu` VALUES (724, 1, 58);
INSERT INTO `sys_role_menu` VALUES (725, 1, 62);
INSERT INTO `sys_role_menu` VALUES (726, 1, 63);
INSERT INTO `sys_role_menu` VALUES (727, 1, 64);
INSERT INTO `sys_role_menu` VALUES (728, 1, 65);
INSERT INTO `sys_role_menu` VALUES (729, 1, 66);
INSERT INTO `sys_role_menu` VALUES (730, 1, 79);
INSERT INTO `sys_role_menu` VALUES (731, 1, 67);
INSERT INTO `sys_role_menu` VALUES (732, 1, 68);
INSERT INTO `sys_role_menu` VALUES (733, 1, 69);
INSERT INTO `sys_role_menu` VALUES (734, 1, 70);
INSERT INTO `sys_role_menu` VALUES (735, 1, 71);
INSERT INTO `sys_role_menu` VALUES (736, 1, 72);
INSERT INTO `sys_role_menu` VALUES (737, 1, 73);
INSERT INTO `sys_role_menu` VALUES (738, 1, 74);
INSERT INTO `sys_role_menu` VALUES (739, 1, 75);
INSERT INTO `sys_role_menu` VALUES (740, 1, 76);
INSERT INTO `sys_role_menu` VALUES (741, 1, 84);
INSERT INTO `sys_role_menu` VALUES (742, 1, 85);
INSERT INTO `sys_role_menu` VALUES (743, 1, 86);
INSERT INTO `sys_role_menu` VALUES (744, 1, 87);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `login_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账号',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别 0：女，1：男',
  `id_card` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号码',
  `is_admin` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否是管理员 1：是，0：不是',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否在职 0:在职，1：禁用',
  `is_used` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否禁用 0：启用，1：禁用',
  `is_account_non_expired` int(255) NULL DEFAULT NULL COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` int(255) NULL DEFAULT NULL COMMENT '帐户是否被锁定(1 未过期，0已过期)',
  `is_credentials_non_expired` int(255) NULL DEFAULT NULL COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` int(255) NULL DEFAULT NULL COMMENT '帐户是否可用(1 可用，0 删除用户)',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', 'admin', '17303703921', '1', '123456789', '1', '0', '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (2, 'zs', '$2a$10$3N59aiL741P5C9tVhMBCQ.unhxSWtft4uY.iTYAvBLG6GUaAowPCu', 'zhangsan', '17303703921', '0', '123456789', NULL, '0', '1', 1, 1, 1, 1);
INSERT INTO `sys_user` VALUES (11, 'ceshi', '$2a$10$JU1Ja4JzvtSNFdKCHpfRG.hIbbbqzusIJu9qDPtnizxOBAsnFqyfW', '测试', '123456', '1', '123', NULL, '1', '0', NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (12, '张三1', '$2a$10$ETPXxehQd4jt31JCOd4NKO0aq2leS86V4GmYDMIOfDRDDN2c6G3ri', 'zs', '173037039551', '1', '1324561', NULL, '0', '0', 1, 1, 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (3, 11, 2);
INSERT INTO `sys_user_role` VALUES (5, 10, 1);
INSERT INTO `sys_user_role` VALUES (7, 1, 1);
INSERT INTO `sys_user_role` VALUES (9, 2, 2);

-- ----------------------------
-- Table structure for user_complaint
-- ----------------------------
DROP TABLE IF EXISTS `user_complaint`;
CREATE TABLE `user_complaint`  (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '投诉标题',
  `complaint_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '投诉内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '投诉时间',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '处理状态 0:未处理，1：已处理',
  PRIMARY KEY (`complaint_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_complaint
-- ----------------------------
INSERT INTO `user_complaint` VALUES (4, 1, '乱丢垃圾', '1栋3单元', '2024-03-17 19:53:58', '1');
INSERT INTO `user_complaint` VALUES (5, 1, '随地吐痰', '2栋3单元', '2024-03-22 17:07:10', '0');
INSERT INTO `user_complaint` VALUES (6, 1, '测试1', '测试1', '2024-03-17 20:09:04', '0');

-- ----------------------------
-- Table structure for user_repair
-- ----------------------------
DROP TABLE IF EXISTS `user_repair`;
CREATE TABLE `user_repair`  (
  `repair_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '业主id',
  `phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `repair_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '维修地址',
  `repair_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '维修内容',
  `conmmit_time` datetime(0) NULL DEFAULT NULL COMMENT '保修时间',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '维修状态 0：未维修，1：已维修',
  PRIMARY KEY (`repair_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_repair
-- ----------------------------
INSERT INTO `user_repair` VALUES (4, 1, '15736064704', '1栋1单元', '水管坏了', '2024-03-18 13:32:37', '0');
INSERT INTO `user_repair` VALUES (7, 1, '测试', '测试', '测试', '2024-03-22 17:05:51', '0');
INSERT INTO `user_repair` VALUES (8, 1, '17303703658', '3栋1单元', '天然气门阀漏气', '2024-03-18 14:26:09', '1');

SET FOREIGN_KEY_CHECKS = 1;
