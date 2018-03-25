package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
}
