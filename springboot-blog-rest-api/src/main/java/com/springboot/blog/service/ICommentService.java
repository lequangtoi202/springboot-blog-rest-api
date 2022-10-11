package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
    void deleteComment(long postId, long commentId);
}
