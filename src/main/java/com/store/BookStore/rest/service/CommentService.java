package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Comment;

public interface CommentService {
    Comment findCommentById(long id);
    Comment saveComment(Comment comment, String username, String rating);
    void attachCommentToBook(Comment comment, Long id);
}
