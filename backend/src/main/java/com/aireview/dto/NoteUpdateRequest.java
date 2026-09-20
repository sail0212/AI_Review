package com.aireview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 更新笔记入参。
 */
@Data
public class NoteUpdateRequest {

    @NotBlank(message = "标题不能为空")
    @Size(max = 255, message = "标题过长")
    private String title;

    private String content;

    private List<Long> tagIds;
}
