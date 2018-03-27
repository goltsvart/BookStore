package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.domain.Comment;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.data.repo.CommentRepository;
import com.store.BookStore.data.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Comment findCommentById(long id) {
        return commentRepository.findCommentById(id);
    }

    public Comment saveComment(Comment comment, String username) {
        comment.setUser(userRepository.findUserByUsername(username));
        return commentRepository.saveAndFlush(comment);
    }
    public void attachCommentToBook(Comment comment, Long id){
        Book book = bookRepository.findBookById(id);
        book.getComments().add(comment);
        bookRepository.save(book);
    }
}
