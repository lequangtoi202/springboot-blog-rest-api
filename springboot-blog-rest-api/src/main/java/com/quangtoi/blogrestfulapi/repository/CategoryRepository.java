package com.quangtoi.blogrestfulapi.repository;

import com.quangtoi.blogrestfulapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
