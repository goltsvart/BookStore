package com.store.BookStore.data.repo;

import com.store.BookStore.data.domain.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    PurchaseHistory findPurchaseHistoryById(long id);
}
