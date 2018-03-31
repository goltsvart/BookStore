package com.store.BookStore.rest.service;

import com.store.BookStore.data.domain.PurchaseHistory;

public interface PurchaseHistoryService {

    PurchaseHistory findHistoryById(long id);
}
