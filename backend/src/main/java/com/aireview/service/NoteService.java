package com.aireview.service;

import com.aireview.common.PageResult;
import com.aireview.dto.NoteCreateRequest;
import com.aireview.dto.NoteQueryRequest;
import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoteService extends IService<Note> {

    PageResult<NoteVO> page(NoteQueryRequest request);

    NoteVO detail(Long id);

    NoteVO create(NoteCreateRequest request);

    NoteVO update(Long id, NoteUpdateRequest request);

    void delete(Long id);

    NoteVO togglePin(Long id);

    NoteVO toggleArchive(Long id);

    /** 公开分享读取：不做所有权校验。 */
    NoteVO detailShared(Long noteId);

    /** 公开分享（可编辑）更新：仅更新标题与正文。 */
    NoteVO updateShared(Long noteId, NoteUpdateRequest request);
}
