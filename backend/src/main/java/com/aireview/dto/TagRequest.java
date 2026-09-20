package com.aireview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 标签增改入参。
 */
@Data
public class TagRequest {

    @NotBlank(message = "标签名不能为空")
    @Size(max = 64, message = "标签名过长")
    private String name;
}
