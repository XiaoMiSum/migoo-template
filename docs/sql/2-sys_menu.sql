/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : ones

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 04/10/2023 17:48:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '菜单名称',
    `permission`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限标识',
    `menu_type`      tinyint                                                       NOT NULL COMMENT '菜单类型',
    `sort`           int                                                           NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `parent_id`      bigint                                                        NOT NULL DEFAULT '0' COMMENT '父菜单ID',
    `path`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT '' COMMENT '路由地址',
    `icon`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT '#' COMMENT '菜单图标',
    `component_name` varchar(50)                                                            DEFAULT NULL COMMENT '组件名称',
    `component`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '组件路径',
    `status`         tinyint                                                       NOT NULL DEFAULT '1' COMMENT '菜单状态',
    `page_type`      tinyint                                                       NOT NULL DEFAULT '2' COMMENT '页面类型，1: 会员页面；2:管理员页面',
    `visible`        tinyint                                                       NOT NULL DEFAULT '1' COMMENT '是否可见',
    `always_show`    tinyint                                                       NOT NULL DEFAULT '0' COMMENT '总是显示：1:是，0:否',
    `keep_alive`     tinyint                                                       NOT NULL DEFAULT '1' COMMENT '是否缓存',
    `deleted`        tinyint                                                       NOT NULL DEFAULT '0' COMMENT '是否删除',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                               DEFAULT NULL COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4110
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (1, '工作台', 'dashboard', 2, 0, 0, '/index', 'ep:alarm-clock', 'Dashboard', 'dashboard/index', 1, 2, 1,
        1, 1, 0, '', NULL, '超级管理员', '2023-09-27 22:37:53');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2, '系统管理', 'system:manager', 1, 9998, 0, '/system', 'ep:setting', NULL, '', 1, 2, 1, 0, 1, 0, '',
        '2022-04-29 18:34:52', '超级管理员', '2023-09-27 22:48:18');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (100, '主题测试', 'layout:setting', 3, 0, 1, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2023-07-23 18:56:46', '超级管理员', '2023-07-23 18:56:46');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (200, '用户管理', 'system:user:query', 2, 0, 2, 'user', 'ep:user', 'SystemUser', 'system/user/index', 1,
        2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:48:39');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (201, '角色管理', 'system:role:query', 2, 1, 2, 'role', 'fa-solid:user-plus', 'UserRole',
        'system/role/index', 1, 2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:49:31');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (202, '部门管理', 'system:dept:query', 2, 2, 2, 'dept', 'ep:basketball', 'SystemDept',
        'system/dept/index', 1, 2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:05');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (203, '岗位管理', 'system:post:query', 2, 3, 2, 'post', 'ep:postcard', 'SystemPost', 'system/post/index',
        1, 2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:32');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (204, '菜单管理', 'system:menu:query', 2, 4, 2, 'menu', 'ep:menu', 'SystemMenu', 'system/menu/index', 1,
        2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:53');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2000, '新增', 'system:user:add', 3, 0, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员', '2022-05-01 13:55:34',
        '超级管理员', '2022-05-01 16:27:05');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2001, '修改', 'system:user:update', 3, 1, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 13:55:59', '超级管理员', '2023-09-30 15:51:13');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2002, '删除', 'system:user:remove', 3, 3, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 13:56:20', '超级管理员', '2022-05-01 16:27:12');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2003, '重置密码', 'system:user:reset-password', 3, 4, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 14:00:56', '超级管理员', '2022-05-01 16:27:16');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2004, '分配角色', 'system:permission:assign-user-role', 3, 4, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:05:28', '超级管理员', '2022-05-01 16:27:23');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2010, '新增', 'system:role:add', 3, 0, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员', '2022-05-01 16:00:30',
        '超级管理员', '2022-05-01 16:27:37');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2011, '修改', 'system:role:update', 3, 1, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:01:01', '超级管理员', '2023-10-01 10:18:19');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2012, '删除', 'system:role:remove', 3, 2, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:01:24', '超级管理员', '2022-05-01 16:27:53');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2013, '分配菜单', 'system:permission:assign-role-menu', 3, 3, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:06:41', '超级管理员', '2022-05-01 16:27:57');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2020, '新增', 'system:dept:add', 3, 0, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员', '2022-05-01 16:07:56',
        '超级管理员', '2022-05-01 16:28:02');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2021, '修改', 'system:dept:update', 3, 1, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:08:12', '超级管理员', '2023-10-01 10:18:13');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2022, '删除', 'system:dept:remove', 3, 3, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:08:24', '超级管理员', '2022-05-01 16:28:10');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2030, '新增', 'system:post:add', 3, 0, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员', '2022-05-01 16:15:22',
        '超级管理员', '2022-05-01 16:28:21');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2031, '修改', 'system:post:update', 3, 1, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:18:11', '超级管理员', '2023-10-01 10:17:56');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2032, '删除', 'system:post:remove', 3, 2, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:18:28', '超级管理员', '2022-05-01 16:28:42');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2040, '新增', 'system:menu:add', 3, 0, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员', '2022-05-01 16:19:11',
        '超级管理员', '2022-05-01 16:28:48');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2041, '修改', 'system:menu:update', 3, 1, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:19:42', '超级管理员', '2023-10-01 10:18:06');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`,
                        `component`, `status`, `page_type`, `visible`, `always_show`, `keep_alive`, `deleted`,
                        `creator`, `create_time`, `updater`, `update_time`)
VALUES (2042, '删除', 'system:menu:remove', 3, 2, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:19:56', '超级管理员', '2022-05-01 16:28:57');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
