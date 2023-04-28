package com.quangtoi.blogrestfulapi.service;

import com.quangtoi.blogrestfulapi.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long postId);

    List<CommentDto> getCommentsByPost(long postId);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateByCommentId(CommentDto commentDto, long postId, long commentId);

    void deleteById(long postId, long commentId);




}
