package com.aireview.service;

import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.dto.ShareCreateRequest;
import com.aireview.dto.ShareVO;
import com.aireview.entity.Share;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ShareService extends IService<Share> {

    ShareVO create(Long noteId, ShareCreateRequest request);

    List<ShareVO> listByNote(Long noteId);

    void revoke(Long id);

    NoteVO getByToken(String token);

    NoteVO updateByToken(String token, NoteUpdateRequest request);
}
