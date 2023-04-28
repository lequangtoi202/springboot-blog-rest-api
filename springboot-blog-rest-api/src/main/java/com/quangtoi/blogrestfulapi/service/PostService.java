package com.quangtoi.blogrestfulapi.service;

import com.quangtoi.blogrestfulapi.dto.PostDto;
import com.quangtoi.blogrestfulapi.dto.PostResponse;
import com.quangtoi.blogrestfulapi.model.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getById(long id);

    List<PostDto> getByCategory(long id);

    PostDto updatePost(PostDto postDto, long id);

    PostDto updatePartialPost(PostDto postDto, long id);

    void deletePost(long id);


}
