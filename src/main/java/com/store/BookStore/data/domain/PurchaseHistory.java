package com.store.BookStore.data.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class PurchaseHistory {
    private @Id @GeneratedValue Long id;
    @OneToMany(cascade=CascadeType.ALL)
    private List<Purchase> purchaseList;
    private BigDecimal totalSpent;
    public PurchaseHistory() { }
    public PurchaseHistory(List<Purchase> purchaseList, BigDecimal totalSpent) {
        this.purchaseList = purchaseList;
        this.totalSpent = totalSpent;
    }
}
