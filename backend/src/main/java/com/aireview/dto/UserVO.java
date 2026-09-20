package com.aireview.dto;

import lombok.Data;

/**
 * 用户信息返回。
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;
}
