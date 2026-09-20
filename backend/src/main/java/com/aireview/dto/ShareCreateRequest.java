package com.aireview.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建分享入参。
 */
@Data
public class ShareCreateRequest {

    /** readonly / editable，默认 readonly */
    private String mode;

    /** 过期时间，null 为永久 */
    private LocalDateTime expiresAt;
}
