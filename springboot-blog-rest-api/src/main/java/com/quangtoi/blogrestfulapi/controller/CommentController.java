package com.quangtoi.blogrestfulapi.controller;

import com.quangtoi.blogrestfulapi.dto.CommentDto;
import com.quangtoi.blogrestfulapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class CommentController {
    private CommentService commentService;

    @Operation(
            summary = "Create comment REST API"
    )
    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") long postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get comment by post REST API"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") long postId){
        return new ResponseEntity<>(commentService.getCommentsByPost(postId), HttpStatus.OK);
    }

    @Operation(
            summary = "Create comment REST API"
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId")long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Update comment REST API"
    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<CommentDto> updateCommentById(@Valid @RequestBody CommentDto commentDto,
                                                        @PathVariable("postId") long postId,
                                                        @PathVariable("commentId")long commentId){
        return new ResponseEntity<>(commentService.updateByCommentId(commentDto, postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete comment REST API"
    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId")long commentId){
        commentService.deleteById(postId, commentId);
        return new ResponseEntity<>("Delete comment Successfully", HttpStatus.OK);
    }
}
