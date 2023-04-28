package com.quangtoi.blogrestfulapi.controller;

import com.quangtoi.blogrestfulapi.dto.PostDto;
import com.quangtoi.blogrestfulapi.dto.PostResponse;
import com.quangtoi.blogrestfulapi.service.PostService;
import com.quangtoi.blogrestfulapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private PostService postService;

    @Operation(
            summary = "Create post REST API"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all post REST API"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(
            summary = "Get post REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id")Long id){
        return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update post REST API"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id")Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }
    @Operation(
            summary = "Update partial post REST API"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<PostDto> updatePartialPost(@Valid @RequestBody PostDto postDto, @PathVariable("id")Long id){
        return new ResponseEntity<>(postService.updatePartialPost(postDto, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete post REST API"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<String> deletePost(@PathVariable("id")Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Delete post successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Get post by category REST API"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id")long id){
        return new ResponseEntity<>(postService.getByCategory(id), HttpStatus.OK);
    }
}
