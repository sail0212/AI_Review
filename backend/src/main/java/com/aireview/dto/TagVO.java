package com.aireview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签返回（精简）。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVO {

    private Long id;

    private String name;
}
