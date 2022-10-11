package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.IPostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "CRUD Rest api for post resources")
@RestController
@RequestMapping("/")
public class PostController {

    private IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    // create blog post rest api
    @ApiOperation(value = "Create post Rest Api")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts api
    @ApiOperation(value = "get All post Rest Api")
    @GetMapping("/api/v1/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get post by id api
    @ApiOperation(value = "get post by id Rest Api")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }


    //update post rest api
    @ApiOperation(value = "update post by id Rest Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    @ApiOperation(value = "delete post Rest Api")
    @PreAuthorize("hasRole('ADMIN')")
    //delete post rest api
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity delete successfully", HttpStatus.OK);
    }

}
