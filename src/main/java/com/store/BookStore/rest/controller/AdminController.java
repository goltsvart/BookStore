package com.store.BookStore.rest.controller;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.rest.service.BookService;
import com.store.BookStore.rest.service.PurchaseHistoryService;
import com.store.BookStore.rest.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final PurchaseHistoryService purchaseHistoryService;

    @Autowired
    public AdminController(UserService userService, BookService bookService, PurchaseHistoryService purchaseHistoryService) {
        this.userService = userService;
        this.bookService = bookService;
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping("/admin")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin");
        return modelAndView;
    }
    @GetMapping("/adminBooks")
    public ModelAndView adminBooksPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.setViewName("/adminBooks");
        return modelAndView;
    }
    @GetMapping("/customers")
    public ModelAndView adminCustomersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("users", userService.getCustomers());
        modelAndView.setViewName("/customers");
        return modelAndView;
    }

    @RequestMapping(value= "/purchaseHistoryDetails/{id}", method = RequestMethod.GET)
    public String purchaseHistoryDetails(@PathVariable("id") long id, ModelMap model) {
        model.put("details", purchaseHistoryService.findHistoryById(id).getPurchaseList());
        return "purchaseHistoryDetails";
    }
    @RequestMapping(value="/saveBook",method= RequestMethod.POST)
    public ModelAndView saveEditedBook(@ModelAttribute("book") Book book, BindingResult bindingResult)
    {
        if (StringUtils.isBlank(book.getAuthor())) {
            bindingResult.rejectValue("author", "error.book", "Author field cannot be empty");
        }else if(StringUtils.isBlank(book.getTitle())) {
            bindingResult.rejectValue("title", "error.book", "Title field cannot be empty");
        }else if(StringUtils.isBlank(book.getSubject())){
            bindingResult.rejectValue("subject", "error.book", "Subject field cannot be empty");
        }else if(StringUtils.isBlank(book.getImage())){
            bindingResult.rejectValue("image", "error.book", "Image field cannot be empty");
        }else if(book.getPrice() == BigDecimal.ZERO || book.getPrice().intValue() == 0 || String.valueOf(book.getPrice().intValue()).isEmpty()){
            bindingResult.rejectValue("price", "error.book", "Price field cannot be empty");
        }else if(book.getQuantity() == 0 || StringUtils.isBlank(String.valueOf(book.getQuantity()))){
            bindingResult.rejectValue("quantity", "error.book", "Quantity field cannot be empty");
        }
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/saveBook");
        } else {
            bookService.saveBook(book);

            modelAndView.addObject("successMessage", "Book created successfully");
            modelAndView.setViewName("/saveBook");
        }
        return modelAndView;
    }
    @GetMapping("/saveBook")
    public ModelAndView saveBookForm()
    {
        ModelAndView modelAndView = new ModelAndView();
        Book book = new Book();
        modelAndView.addObject("book", book);
        modelAndView.setViewName("/saveBook");
        return modelAndView;
    }

    @RequestMapping(value= "/books/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") long id, ModelMap model) {
        model.put("book", bookService.findBookById(id));
        return "editBook";
    }

    @RequestMapping(value= "/books/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteBook(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        bookService.deleteBookById(id);
        modelAndView.addObject("successMessage", "Book deleted successfully");
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.setViewName("/adminBooks");
        return modelAndView;
    }

    @RequestMapping(value="/editBook",method=RequestMethod.POST)
    public ModelAndView saveEditedBook(@ModelAttribute("Book") Book book)
    {
        ModelAndView modelAndView = new ModelAndView();
        bookService.editBook(book);
        modelAndView.addObject("successMessage", "Book edited successfully");
        modelAndView.setViewName("/adminBooks");
        return modelAndView;
    }

    @RequestMapping(value= "/purchaseHistory/{username}", method = RequestMethod.GET)
    public ModelAndView purchaseHistory(@PathVariable("username") String username) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("purchaseHistories", userService.findByUsername(username).getPurchaseHistoryList());
        modelAndView.setViewName("/purchaseHistory");
        return modelAndView;
    }
}
