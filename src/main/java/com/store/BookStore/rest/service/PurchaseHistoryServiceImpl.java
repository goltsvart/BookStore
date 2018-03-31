package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.PurchaseHistory;
import com.store.BookStore.data.repo.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService{

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Override
    public PurchaseHistory findHistoryById(long id) {
        return purchaseHistoryRepository.findPurchaseHistoryById(id);
    }
}
