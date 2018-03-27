package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.Book;
import com.store.BookStore.data.domain.Purchase;
import com.store.BookStore.data.domain.PurchaseHistory;
import com.store.BookStore.data.domain.User;
import com.store.BookStore.data.repo.BookRepository;
import com.store.BookStore.data.repo.UserRepository;
import com.store.BookStore.util.NotEnoughProductsInStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private Map<Book, Integer> books = new HashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addBook(Book book) {
        if (books.containsKey(book)) {
            books.replace(book, books.get(book) + 1);
        } else {
            books.put(book, 1);
        }
    }

    @Override
    public void removeBook(Book book) {
        if (books.containsKey(book)) {
            if (books.get(book) > 1)
                books.replace(book, books.get(book) - 1);
            else if (books.get(book) == 1) {
                books.remove(book);
            }
        }
    }

    @Override
    public Map<Book, Integer> getBooksInCart() {
        return Collections.unmodifiableMap(books);
    }

    @Override
    public void checkout(String username) throws NotEnoughProductsInStockException {
        List<Purchase> purchaseList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            Book book = bookRepository.getOne(entry.getKey().getId());
            if (book.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(book);
            entry.getKey().setQuantity(book.getQuantity() - entry.getValue());
            //add to history of customer purchase
            Purchase purchase = new Purchase(entry.getKey(), entry.getValue());
            purchaseList.add(purchase);
            BigDecimal currentValue = entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
            total = total.add(currentValue);
        }
        bookRepository.saveAll(books.keySet());
        bookRepository.flush();
        books.clear();
        //create history of customer purchase
        PurchaseHistory purchaseHistory = new PurchaseHistory(purchaseList, total);
        List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();
        purchaseHistoryList.add(purchaseHistory);
        User user = userRepository.findUserByUsername(username);
        user.setPurchaseHistoryList(purchaseHistoryList);
        userRepository.save(user);
    }

    @Override
    public BigDecimal getTotal() {
        return books.entrySet().stream()
                .map(entry -> {
                    return entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue()));
                })
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}