package com.aireview.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 笔记返回。
 */
@Data
public class NoteVO {

    private Long id;

    private String title;

    private String content;

    private Boolean isPinned;

    private Boolean isArchived;

    private List<TagVO> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
