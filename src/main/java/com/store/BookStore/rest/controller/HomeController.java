package com.store.BookStore.rest.controller;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.rest.service.BookService;
import com.store.BookStore.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Book> books = bookService.findAllProductsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(books);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", books);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.GET)
    public String displayBook(ModelMap model, HttpServletRequest req,  @PathVariable("bookId") Long bookId) throws Exception {
        model.put("book", bookService.findBookById(Long.valueOf(bookId)));
        return "book";
    }
}