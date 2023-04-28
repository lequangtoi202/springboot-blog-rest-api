package com.quangtoi.blogrestfulapi.service.impl;

import com.quangtoi.blogrestfulapi.dto.CommentDto;
import com.quangtoi.blogrestfulapi.exception.BlogApiException;
import com.quangtoi.blogrestfulapi.exception.ResourceNotFoundException;
import com.quangtoi.blogrestfulapi.model.Comment;
import com.quangtoi.blogrestfulapi.model.Post;
import com.quangtoi.blogrestfulapi.repository.CommentRepository;
import com.quangtoi.blogrestfulapi.repository.PostRepository;
import com.quangtoi.blogrestfulapi.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private ModelMapper mapper;
    private CommentRepository commentRepo;
    private PostRepository postRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setPost(post);

        Comment commentSave = commentRepo.save(comment);

        return mapper.map(commentSave, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPost(long postId) {
        List<Comment> comments = commentRepo.getByPostId(postId);

        return comments.stream()
                .map(c -> mapper.map(c, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not belong to post");
        }
        return mapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateByCommentId(CommentDto commentDto, long postId, long commentId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setPost(post);
        Comment updateComment = commentRepo.save(comment);
        return mapper.map(updateComment, CommentDto.class);
    }

    @Override
    public void deleteById(long postId, long commentId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not belong to post");
        }
        commentRepo.delete(comment);
    }
}
