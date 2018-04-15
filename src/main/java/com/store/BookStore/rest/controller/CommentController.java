package com.store.BookStore.rest.controller;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.domain.Comment;
import com.store.BookStore.rest.service.BookService;
import com.store.BookStore.rest.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    @Autowired
    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String comment(@ModelAttribute Comment comment, BindingResult bindingResult,
                          @RequestParam(name = "username") String username,
                          @RequestParam(name = "bookId") String bookId,
                          ModelMap model, HttpServletRequest request) {
        String rating= request.getParameter("rating");
        if (rating.equals("empty")) {
            bindingResult
                    .rejectValue("rating", "error.comment",
                            "Rating field cannot be empty.");
            return "comment";
        }else if(StringUtils.isBlank(comment.getMessage())){
            bindingResult
                    .rejectValue("message", "error.comment",
                            "Comment field cannot be empty.");
            return "comment";
        }else if(comment.getRating() < 0 || comment.getRating() > 5){
            bindingResult
                    .rejectValue("message", "error.comment",
                            "Rating should be in the range of 1 to 5!");
            return "comment";
        }
        Comment c = commentService.saveComment(comment, username, rating);
        commentService.attachCommentToBook(c, Long.valueOf(bookId));
        model.put("book", bookService.findBookById(Long.valueOf(bookId)));
        return "book";
    }

    @GetMapping("/book/{bookId}/comment")
    public String commentForm(Model model, @PathVariable("bookId") Long bookId) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("bookId", bookId);
        return "comment";
    }

    @GetMapping("/book/{bookId}/comments")
    public ModelAndView comments(@PathVariable("bookId") Long bookId) {
        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.findBookById(Long.valueOf(bookId));
        if(book.getComments().size() > 0){
            modelAndView.addObject("comments", book.getComments());
        }
        modelAndView.setViewName("/comments");
        return modelAndView;
    }
}
