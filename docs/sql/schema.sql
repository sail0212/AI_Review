-- 备忘录数据库建表脚本（唯一建表来源）
CREATE DATABASE IF NOT EXISTS memo DEFAULT CHARSET utf8mb4;

USE memo;

-- 用户
CREATE TABLE `user` (
  `id`            BIGINT       NOT NULL AUTO_INCREMENT,
  `username`      VARCHAR(64)  NOT NULL COMMENT '登录名',
  `password_hash` VARCHAR(100) NOT NULL COMMENT 'BCrypt 哈希',
  `nickname`      VARCHAR(64)  DEFAULT NULL COMMENT '昵称',
  `avatar`        VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
  `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB COMMENT='用户';

-- 笔记
CREATE TABLE `note` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT       NOT NULL COMMENT '所属用户',
  `title`       VARCHAR(255) NOT NULL DEFAULT '' COMMENT '标题',
  `content`     LONGTEXT     COMMENT 'Markdown 正文',
  `is_pinned`   TINYINT      NOT NULL DEFAULT 0 COMMENT '是否置顶 0/1',
  `is_archived` TINYINT      NOT NULL DEFAULT 0 COMMENT '是否归档 0/1',
  `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_updated` (`user_id`, `updated_at`)
) ENGINE=InnoDB COMMENT='笔记';

-- 标签
CREATE TABLE `tag` (
  `id`         BIGINT      NOT NULL AUTO_INCREMENT,
  `user_id`    BIGINT      NOT NULL,
  `name`       VARCHAR(64) NOT NULL COMMENT '标签名',
  `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_id`, `name`)
) ENGINE=InnoDB COMMENT='标签';

-- 笔记-标签关联
CREATE TABLE `note_tag` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT,
  `note_id` BIGINT NOT NULL,
  `tag_id`  BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_note_tag` (`note_id`, `tag_id`),
  KEY `idx_tag` (`tag_id`)
) ENGINE=InnoDB COMMENT='笔记标签关联';

-- 分享
CREATE TABLE `share` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT,
  `note_id`    BIGINT       NOT NULL,
  `token`      VARCHAR(32)  NOT NULL COMMENT '分享短链 token',
  `mode`       VARCHAR(16)  NOT NULL DEFAULT 'readonly' COMMENT 'readonly / editable',
  `expires_at` DATETIME     DEFAULT NULL COMMENT '过期时间，NULL 为永久',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_token` (`token`),
  KEY `idx_note` (`note_id`)
) ENGINE=InnoDB COMMENT='分享';

-- 用户自定义主题
CREATE TABLE `user_theme` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT,
  `user_id`    BIGINT       NOT NULL,
  `name`       VARCHAR(64)  NOT NULL,
  `config`     JSON         COMMENT '主题配置(颜色/字体等)',
  `is_default` TINYINT      NOT NULL DEFAULT 0,
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB COMMENT='自定义主题';
