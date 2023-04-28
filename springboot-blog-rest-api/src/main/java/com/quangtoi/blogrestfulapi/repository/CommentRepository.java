package com.quangtoi.blogrestfulapi.repository;

import com.quangtoi.blogrestfulapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getByPostId(long postId);
}
