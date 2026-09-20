package com.aireview.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一返回码枚举。
 * <p>约定：{@code 0} 成功；HTTP 层用 401/403/404/500；业务错误用 4xxx 自定义。</p>
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(0, "成功"),

    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    ERROR(500, "服务异常"),

    USERNAME_EXISTS(4001, "用户名已存在"),
    USERNAME_OR_PASSWORD_ERROR(4002, "用户名或密码错误"),
    SHARE_EXPIRED(4003, "分享链接已过期"),
    SHARE_READ_ONLY(4004, "该分享为只读，不可编辑");

    private final int code;
    private final String message;
}
