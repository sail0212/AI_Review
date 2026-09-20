package com.aireview.service.impl;

import com.aireview.common.BusinessException;
import com.aireview.common.PageResult;
import com.aireview.common.ResultCode;
import com.aireview.dto.NoteCreateRequest;
import com.aireview.dto.NoteQueryRequest;
import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.dto.TagVO;
import com.aireview.entity.Note;
import com.aireview.entity.NoteTag;
import com.aireview.entity.Share;
import com.aireview.entity.Tag;
import com.aireview.mapper.NoteMapper;
import com.aireview.mapper.NoteTagMapper;
import com.aireview.mapper.ShareMapper;
import com.aireview.mapper.TagMapper;
import com.aireview.service.NoteService;
import com.aireview.util.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    private final NoteTagMapper noteTagMapper;
    private final TagMapper tagMapper;
    private final ShareMapper shareMapper;

    @Override
    public PageResult<NoteVO> page(NoteQueryRequest request) {
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getUserId, userId);

        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            wrapper.and(w -> w.like(Note::getTitle, request.getKeyword())
                    .or().like(Note::getContent, request.getKeyword()));
        }
        if (request.getTagId() != null) {
            List<Long> noteIds = noteTagMapper.selectList(
                            new LambdaQueryWrapper<NoteTag>().eq(NoteTag::getTagId, request.getTagId()))
                    .stream().map(NoteTag::getNoteId).toList();
            if (noteIds.isEmpty()) {
                return empty(request);
            }
            wrapper.in(Note::getId, noteIds);
        }
        if (request.getIsPinned() != null) {
            wrapper.eq(Note::getIsPinned, request.getIsPinned());
        }
        if (request.getIsArchived() != null) {
            wrapper.eq(Note::getIsArchived, request.getIsArchived());
        }
        wrapper.orderByDesc(Note::getIsPinned).orderByDesc(Note::getUpdatedAt);

        Page<Note> notePage = page(new Page<>(request.getPage(), request.getSize()), wrapper);
        PageResult<NoteVO> result = new PageResult<>();
        result.setRecords(notePage.getRecords().stream().map(this::toVO).toList());
        result.setTotal(notePage.getTotal());
        result.setPage(notePage.getCurrent());
        result.setSize(notePage.getSize());
        return result;
    }

    @Override
    public NoteVO detail(Long id) {
        return toVO(getOwnedNote(id));
    }

    @Override
    @Transactional
    public NoteVO create(NoteCreateRequest request) {
        Note note = new Note();
        note.setUserId(UserContext.getUserId());
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setIsPinned(false);
        note.setIsArchived(false);
        save(note);
        saveTags(note.getId(), request.getTagIds());
        return toVO(note);
    }

    @Override
    @Transactional
    public NoteVO update(Long id, NoteUpdateRequest request) {
        Note note = getOwnedNote(id);
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        updateById(note);
        saveTags(id, request.getTagIds());
        return toVO(note);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getOwnedNote(id);
        removeById(id);
        noteTagMapper.delete(new LambdaQueryWrapper<NoteTag>().eq(NoteTag::getNoteId, id));
        shareMapper.delete(new LambdaQueryWrapper<Share>().eq(Share::getNoteId, id));
    }

    @Override
    public NoteVO togglePin(Long id) {
        Note note = getOwnedNote(id);
        note.setIsPinned(!Boolean.TRUE.equals(note.getIsPinned()));
        updateById(note);
        return toVO(note);
    }

    @Override
    public NoteVO toggleArchive(Long id) {
        Note note = getOwnedNote(id);
        note.setIsArchived(!Boolean.TRUE.equals(note.getIsArchived()));
        updateById(note);
        return toVO(note);
    }

    @Override
    public NoteVO detailShared(Long noteId) {
        Note note = getById(noteId);
        if (note == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return toVO(note);
    }

    @Override
    public NoteVO updateShared(Long noteId, NoteUpdateRequest request) {
        Note note = getById(noteId);
        if (note == null) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        updateById(note);
        return toVO(note);
    }

    private Note getOwnedNote(Long id) {
        Note note = getById(id);
        if (note == null || !note.getUserId().equals(UserContext.getUserId())) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return note;
    }

    /** 事务内先删后插 note_tag 关联。 */
    private void saveTags(Long noteId, List<Long> tagIds) {
        noteTagMapper.delete(new LambdaQueryWrapper<NoteTag>().eq(NoteTag::getNoteId, noteId));
        if (tagIds == null) {
            return;
        }
        for (Long tagId : tagIds.stream().distinct().toList()) {
            NoteTag noteTag = new NoteTag();
            noteTag.setNoteId(noteId);
            noteTag.setTagId(tagId);
            noteTagMapper.insert(noteTag);
        }
    }

    private List<TagVO> listTags(Long noteId) {
        List<Long> tagIds = noteTagMapper.selectList(
                        new LambdaQueryWrapper<NoteTag>().eq(NoteTag::getNoteId, noteId))
                .stream().map(NoteTag::getTagId).toList();
        if (tagIds.isEmpty()) {
            return List.of();
        }
        return tagMapper.selectBatchIds(tagIds).stream()
                .map(tag -> new TagVO(tag.getId(), tag.getName()))
                .toList();
    }

    private NoteVO toVO(Note note) {
        NoteVO vo = new NoteVO();
        vo.setId(note.getId());
        vo.setTitle(note.getTitle());
        vo.setContent(note.getContent());
        vo.setIsPinned(note.getIsPinned());
        vo.setIsArchived(note.getIsArchived());
        vo.setTags(listTags(note.getId()));
        vo.setCreatedAt(note.getCreatedAt());
        vo.setUpdatedAt(note.getUpdatedAt());
        return vo;
    }

    private PageResult<NoteVO> empty(NoteQueryRequest request) {
        PageResult<NoteVO> result = new PageResult<>();
        result.setRecords(List.of());
        result.setTotal(0);
        result.setPage(request.getPage());
        result.setSize(request.getSize());
        return result;
    }
}
