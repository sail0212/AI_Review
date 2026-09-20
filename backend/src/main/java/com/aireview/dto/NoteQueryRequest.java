package com.aireview.dto;

import lombok.Data;

/**
 * 笔记列表查询入参。
 */
@Data
public class NoteQueryRequest {

    private String keyword;

    private Long tagId;

    private Boolean isPinned;

    private Boolean isArchived;

    /** 页码，从 1 开始 */
    private long page = 1;

    private long size = 20;
}
