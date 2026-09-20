package com.aireview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分享实体。
 */
@Data
@TableName("share")
public class Share {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long noteId;

    private String token;

    /** readonly / editable */
    private String mode;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt;
}
