/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : 127.0.0.1:3306
 Source Schema         : migoo

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 26/10/2023 15:40:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for infra_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_error_log`;
CREATE TABLE `infra_error_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `application_name` varchar(255)  NULL DEFAULT NULL COMMENT '应用名称',
  `request_method` varchar(255)  NULL DEFAULT NULL COMMENT '请求方法',
  `request_url` text  NULL COMMENT '请求地址',
  `request_params` text  NULL COMMENT '请求参数',
  `user_ip` varchar(255)  NULL DEFAULT NULL COMMENT '来源ip',
  `exception_time` datetime NULL DEFAULT NULL COMMENT '异常时间',
  `exception_name` varchar(255)  NULL DEFAULT NULL COMMENT '异常名称',
  `exception_class_name` varchar(255)  NULL DEFAULT NULL COMMENT '异常类',
  `exception_file_name` varchar(255)  NULL DEFAULT NULL COMMENT '异常文件',
  `exception_method_name` varchar(255)  NULL DEFAULT NULL COMMENT '异常方法',
  `exception_line_number` int NULL DEFAULT NULL COMMENT '异常行',
  `exception_stack_trace` text  NULL COMMENT '堆栈信息',
  `exception_root_cause_message` text  NULL,
  `exception_message` text  NULL,
  `status` tinyint NULL DEFAULT 0,
  `deleted` tinyint NULL DEFAULT 0,
  `creator` varchar(64)  NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64)  NULL DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '接口异常表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30)  NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父部门id',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` bigint NULL DEFAULT NULL COMMENT '负责人',
  `email` varchar(50)  NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '部门状态（1正常 0停用）',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `creator` varchar(64)  NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64)  NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = '部门信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50)  NOT NULL COMMENT '菜单名称',
  `permission` varchar(100)  NOT NULL DEFAULT '' COMMENT '权限标识',
  `menu_type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200)  NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100)  NULL DEFAULT '#' COMMENT '菜单图标',
  `component_name` varchar(50)  NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255)  NULL DEFAULT NULL COMMENT '组件路径',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '菜单状态',
  `page_type` tinyint NOT NULL DEFAULT 2 COMMENT '页面类型，1: 会员页面；2:管理员页面',
  `visible` tinyint NOT NULL DEFAULT 1 COMMENT '是否可见',
  `always_show` tinyint NOT NULL DEFAULT 0 COMMENT '总是显示：1:是，0:否',
  `keep_alive` tinyint NOT NULL DEFAULT 1 COMMENT '是否缓存',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `creator` varchar(64)  NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64)  NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4110 COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) NOT NULL COMMENT '岗位名称',
  `code` varchar(255) NOT NULL COMMENT '岗位编码',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1正常；0停用',
  `remark` varchar(512) NULL DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64)  NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64)  NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) NOT NULL COMMENT '角色名称',
  `code` varchar(255) NOT NULL COMMENT '角色编码',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1正常；0停用',
  `type` int NOT NULL COMMENT '角色类型',
  `remark` varchar(512) NULL DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64)  NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64)  NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(255)  NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(255)  NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `phone` varchar(12)  NOT NULL COMMENT '用户手机号，登录名',
  `password` varchar(255)  NOT NULL COMMENT '密码',
  `name` varchar(255)  NOT NULL COMMENT '真实姓名',
  `avatar` varchar(512)  NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态0禁用、1启用',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别 1男 2女',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门编号',
  `post_ids` varchar(255) NULL DEFAULT NULL COMMENT '岗位编号',
  `email` varchar(255) NULL DEFAULT NULL COMMENT '邮箱地址',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64)  NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64)  NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
