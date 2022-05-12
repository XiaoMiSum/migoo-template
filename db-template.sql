/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : db-template

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 12/05/2022 20:52:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for developer_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `developer_codegen_column`;
CREATE TABLE `developer_codegen_column` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint NOT NULL COMMENT '表编号',
  `column_name` varchar(200) NOT NULL COMMENT '字段名',
  `column_type` varchar(100) NOT NULL COMMENT '字段类型',
  `column_comment` varchar(500) NOT NULL COMMENT '字段描述',
  `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
  `primary_key` bit(1) NOT NULL COMMENT '是否主键',
  `auto_Increment` char(1) NOT NULL COMMENT '是否自增',
  `ordinal_position` int NOT NULL COMMENT '排序',
  `java_type` varchar(32) NOT NULL COMMENT 'Java 属性类型',
  `java_field` varchar(64) NOT NULL COMMENT 'Java 属性名',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `example` varchar(64) DEFAULT NULL COMMENT '数据示例',
  `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
  `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
  `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
  `list_operation_condition` varchar(32) NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
  `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
  `html_type` varchar(32) NOT NULL COMMENT '显示类型',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成表字段定义';

-- ----------------------------
-- Records of developer_codegen_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for developer_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `developer_codegen_table`;
CREATE TABLE `developer_codegen_table` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `import_type` tinyint NOT NULL DEFAULT '1' COMMENT '导入类型',
  `table_name` varchar(200) NOT NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) NOT NULL DEFAULT '' COMMENT '表描述',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `module_name` varchar(30) NOT NULL COMMENT '模块名',
  `business_name` varchar(30) NOT NULL COMMENT '业务名',
  `class_name` varchar(100) NOT NULL DEFAULT '' COMMENT '类名称',
  `class_comment` varchar(50) NOT NULL COMMENT '类描述',
  `author` varchar(50) NOT NULL COMMENT '作者',
  `template_type` tinyint NOT NULL DEFAULT '1' COMMENT '模板类型',
  `parent_menu_id` bigint DEFAULT NULL COMMENT '父菜单编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成表定义';

-- ----------------------------
-- Records of developer_codegen_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户手机号，登录名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='会员信息表';

-- ----------------------------
-- Records of member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父部门id',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `leader_user_id` bigint DEFAULT NULL COMMENT '负责人',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '部门状态（1正常 0停用）',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门信息表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'migoo.club', 0, 0, 1, 'migoo@migoo.xyz', 1, 0, '系统', '2022-04-29 18:04:35', '系统', '2022-04-29 18:04:35');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '董事会', 1, 0, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:08', '系统', '2022-05-01 16:56:43');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '总经办', 1, 10, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:35', '系统', '2022-05-01 16:56:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `menu_type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组件路径',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '菜单状态',
  `visible` tinyint NOT NULL DEFAULT '1' COMMENT '是否可见',
  `keep_alive` tinyint NOT NULL DEFAULT '1' COMMENT '是否缓存',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '系统管理', 'system:manager', 1, 9998, 0, '/manager/system', 'system', '', 1, 1, 1, 0, '', '2022-04-29 18:34:52', '奥丁1', '2022-05-01 13:42:30');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '研发工具', 'developer', 1, 9999, 0, '/manager/developer', 'developer', NULL, 1, 1, 1, 0, '', NULL, '奥丁1', '2022-05-01 13:53:33');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '工作台', 'dashboard', 1, 0, 0, '/manager', 'example', '', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 13:41:29');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (100, '用户管理', 'system:user:query', 2, 0, 1, 'user', 'user', 'manager/system/user/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 15:56:32');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (101, '角色管理', 'system:role:query', 2, 1, 1, 'role', 'role', 'manager/system/role/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 16:00:40');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (102, '部门管理', 'system:dept:query', 2, 2, 1, 'dept', 'dept', 'manager/system/dept/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 16:07:04');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (103, '岗位管理', 'system:post:query', 2, 3, 1, 'post', 'post', 'manager/system/post/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 16:16:37');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (104, '菜单管理', 'system:menu:query', 2, 4, 1, 'menu', 'menu', 'manager/system/menu/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 16:18:43');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (200, '代码生成', 'developer:generator', 2, 0, 2, 'generator', 'code', 'manager/developer/generator/index', 1, 1, 1, 0, '', '2022-04-29 18:45:02', '奥丁1', '2022-05-01 13:53:46');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (300, '工作台', 'dashboard', 2, 0, 3, 'index', 'dashboard', 'manager/dashboard/index', 1, 1, 1, 0, '', NULL, '奥丁1', '2022-05-01 13:34:23');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1000, '新增', 'system:user:add', 3, 0, 100, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 13:55:34', '奥丁1', '2022-05-01 16:27:05');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1001, '修改', 'system:user:edit', 3, 1, 100, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 13:55:59', '奥丁1', '2022-05-01 16:27:08');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1002, '删除', 'system:user:remove', 3, 3, 100, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 13:56:20', '奥丁1', '2022-05-01 16:27:12');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1003, '重置密码', 'system:user:reset-password', 3, 4, 100, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 14:00:56', '奥丁1', '2022-05-01 16:27:16');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1004, '分配角色', 'system:permission:assign-user-role', 3, 4, 100, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:05:28', '奥丁1', '2022-05-01 16:27:23');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1010, '新增', 'system:role:add', 3, 0, 101, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:00:30', '奥丁1', '2022-05-01 16:27:37');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1011, '修改', 'system:role:edit', 3, 1, 101, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:01:01', '奥丁1', '2022-05-01 16:27:48');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1012, '删除', 'system:role:remove', 3, 2, 101, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:01:24', '奥丁1', '2022-05-01 16:27:53');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1013, '分配菜单', 'system:permission:assign-role-menu', 3, 3, 101, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:06:41', '奥丁1', '2022-05-01 16:27:57');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1020, '新增', 'system:dept:add', 3, 0, 102, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:07:56', '奥丁1', '2022-05-01 16:28:02');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1021, '修改', 'system:dept:edit', 3, 1, 102, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:08:12', '奥丁1', '2022-05-01 16:28:05');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1022, '删除', 'system:dept:remove', 3, 3, 102, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:08:24', '奥丁1', '2022-05-01 16:28:10');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1030, '新增', 'system:post:add', 3, 0, 103, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:15:22', '奥丁1', '2022-05-01 16:28:21');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1031, '修改', 'system:post:edit', 3, 1, 103, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:18:11', '奥丁1', '2022-05-01 16:28:29');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1032, '删除', 'system:post:remove', 3, 2, 103, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:18:28', '奥丁1', '2022-05-01 16:28:42');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1040, '新增', 'system:menu:add', 3, 0, 104, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:19:11', '奥丁1', '2022-05-01 16:28:48');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1041, '修改', 'system:menu:edit', 3, 1, 104, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:19:42', '奥丁1', '2022-05-01 16:28:52');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1042, '删除', 'system:menu:remove', 3, 2, 104, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:19:56', '奥丁1', '2022-05-01 16:28:57');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2000, '新增', 'developer:generator:add', 3, 0, 200, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:21:44', '奥丁1', '2022-05-01 16:29:12');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2001, '修改', 'developer:generator:edit', 3, 1, 200, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:23:11', '奥丁1', '2022-05-01 16:29:18');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2002, '预览代码', 'developer:generator:preview', 3, 2, 200, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:23:26', '奥丁1', '2022-05-01 16:29:22');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2003, '下载代码', 'developer:generator:download', 3, 3, 200, '', '', '', 1, 1, 1, 0, '奥丁1', '2022-05-01 16:23:51', '奥丁1', '2022-05-01 16:29:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：1正常；0停用',
  `type` int NOT NULL COMMENT '角色类型',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `remark`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '超级管理员', 'super_admin', 0, 1, 1, '超级管理员', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, 1, 0, '系统', '2022-04-29 20:44:58', '系统', '2022-04-29 20:45:03');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, 2, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 20:46:07');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, 3, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:18:06');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 1, 100, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:38');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 1, 101, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:36');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 1, 102, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:33');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 1, 103, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:31');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 1, 104, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:28');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 1, 200, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:26');
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 1, 300, 0, '系统', '2022-04-29 20:46:02', '系统', '2022-04-29 22:17:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户手机号，登录名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态0禁用、1启用',
  `gender` tinyint DEFAULT NULL COMMENT '性别 1男 2女',
  `dept_id` bigint DEFAULT NULL COMMENT '部门编号',
  `post_ids` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位编号',
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱地址',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `phone`, `password`, `name`, `avatar`, `status`, `gender`, `dept_id`, `post_ids`, `email`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'admin', '$2a$10$DmzOXDEemQOXtQjEHDnMqOcMLi0wjuYluTDSAEvA3VpYOAn9Zh3KC', '奥丁1', '', 1, 1, 1, NULL, NULL, 0, '1', '2022-04-29 16:06:56', 'admin', '2022-05-01 16:29:55');
INSERT INTO `sys_user` (`id`, `phone`, `password`, `name`, `avatar`, `status`, `gender`, `dept_id`, `post_ids`, `email`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '13111111111', '12345678', 'staff1', NULL, 1, 0, 2, '[]', 'mail@cc.com', 0, '奥丁1', '2022-05-01 17:53:00', '奥丁1', '2022-05-01 18:10:51');
INSERT INTO `sys_user` (`id`, `phone`, `password`, `name`, `avatar`, `status`, `gender`, `dept_id`, `post_ids`, `email`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '13111111112', '$2a$10$jxCki/K6kgbb1w./v8KGJ.CqKeMPnXO.2i6fCuufzVTkLodFq/kO6', 'staff2', NULL, 1, NULL, NULL, '[]', NULL, 0, '奥丁1', '2022-05-01 18:23:48', '奥丁1', '2022-05-01 18:23:48');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, 1, 0, '系统', '2022-04-29 20:54:56', '系统', '2022-04-29 20:55:01');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
