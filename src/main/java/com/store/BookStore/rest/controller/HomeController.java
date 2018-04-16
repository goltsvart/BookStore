package com.store.BookStore.rest.controller;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.rest.service.BookService;
import com.store.BookStore.rest.service.SearchService;
import com.store.BookStore.util.Pager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private BookService bookService;
    private SearchService searchService;

    @Autowired
    public HomeController(BookService bookService, SearchService searchService) {
        this.bookService = bookService;
        this.searchService = searchService;
    }
    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Book> books = bookService.findAllProductsPageable(new PageRequest(evalPage, 6));
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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "author", required = false) String author,
                               @RequestParam(value = "subject", required = false) String subject,
                               @RequestParam(value = "admin", required = false) String admin,
                               @RequestParam("page") Optional<Integer> page,
                               HttpServletRequest request) {
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        String selectedSort= request.getParameter("sort");
        String selectedOrder= request.getParameter("order");
        Page<Book> books = bookService.findAllProductsPageable(new PageRequest(evalPage, 6));
        Pager pager = new Pager(books);

        ModelAndView modelAndView = new ModelAndView();
        List<Book> searchResults = searchService.findBooksByParams(title, author, subject);
        if(searchResults == null){
            searchResults = bookService.findAll();
        }
        if(!StringUtils.isBlank(selectedOrder) && !StringUtils.isBlank(selectedSort)){
            List<Book> sorted = bookService.sortBookList(searchResults, selectedSort, selectedOrder);
            Page<Book> pageList = new PageImpl<>(sorted);
            pager = new Pager(pageList);
            modelAndView.addObject("books", sorted);
        }else{
            modelAndView.addObject("books", searchResults);
        }
        modelAndView.addObject("pager", pager);
        if(!StringUtils.isBlank(admin)){
            modelAndView.setViewName("/adminBooks");
        }else{
            modelAndView.setViewName("/home");
        }
        return modelAndView;
    }
}