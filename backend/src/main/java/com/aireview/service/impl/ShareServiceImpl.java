package com.aireview.service.impl;

import com.aireview.common.BusinessException;
import com.aireview.common.ResultCode;
import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.dto.ShareCreateRequest;
import com.aireview.dto.ShareVO;
import com.aireview.entity.Share;
import com.aireview.mapper.ShareMapper;
import com.aireview.service.NoteService;
import com.aireview.service.ShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

    private static final String MODE_EDITABLE = "editable";

    private final NoteService noteService;

    @Override
    public ShareVO create(Long noteId, ShareCreateRequest request) {
        // 校验笔记归属（内部会做所有权校验）
        noteService.detail(noteId);

        Share share = new Share();
        share.setNoteId(noteId);
        share.setToken(UUID.randomUUID().toString().replace("-", ""));
        share.setMode(request == null || request.getMode() == null ? "readonly" : request.getMode());
        share.setExpiresAt(request == null ? null : request.getExpiresAt());
        save(share);
        return toVO(share);
    }

    @Override
    public List<ShareVO> listByNote(Long noteId) {
        noteService.detail(noteId);
        return lambdaQuery().eq(Share::getNoteId, noteId).list().stream()
                .map(this::toVO)
                .toList();
    }

    @Override
    public void revoke(Long id) {
        Share share = getById(id);
        if (share == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        // 越权校验：分享所属笔记必须归当前用户所有
        noteService.detail(share.getNoteId());
        removeById(id);
    }

    @Override
    public NoteVO getByToken(String token) {
        Share share = getValidShare(token);
        return noteService.detailShared(share.getNoteId());
    }

    @Override
    public NoteVO updateByToken(String token, NoteUpdateRequest request) {
        Share share = getValidShare(token);
        if (!MODE_EDITABLE.equals(share.getMode())) {
            throw new BusinessException(ResultCode.SHARE_READ_ONLY);
        }
        return noteService.updateShared(share.getNoteId(), request);
    }

    private Share getValidShare(String token) {
        Share share = lambdaQuery().eq(Share::getToken, token).one();
        if (share == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        if (share.getExpiresAt() != null && share.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.SHARE_EXPIRED);
        }
        return share;
    }

    private ShareVO toVO(Share share) {
        ShareVO vo = new ShareVO();
        vo.setId(share.getId());
        vo.setToken(share.getToken());
        vo.setMode(share.getMode());
        vo.setExpiresAt(share.getExpiresAt());
        vo.setUrl("/shared/" + share.getToken());
        return vo;
    }
}
