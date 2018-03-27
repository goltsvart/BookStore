package com.store.BookStore.rest.controller;

import com.store.BookStore.rest.service.BookService;
import com.store.BookStore.rest.service.ShoppingCartService;
import com.store.BookStore.util.NotEnoughProductsInStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final BookService bookService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, BookService bookService) {
        this.shoppingCartService = shoppingCartService;
        this.bookService = bookService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("books", shoppingCartService.getBooksInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addBook/{bookId}")
    public ModelAndView addProductToCart(@PathVariable("bookId") Long bookId) {
        bookService.findById(bookId).ifPresent(shoppingCartService::addBook);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeBook/{bookId}")
    public ModelAndView removeBookFromCart(@PathVariable("bookId") Long bookId) {
        bookService.findById(bookId).ifPresent(shoppingCartService::removeBook);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout/{username}")
    public ModelAndView checkout(@PathVariable("username") String username) {
        try {
            shoppingCartService.checkout(username);
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }
}