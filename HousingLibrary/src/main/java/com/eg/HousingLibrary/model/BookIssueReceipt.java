package com.eg.HousingLibrary.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope("prototype")
public class BookIssueReceipt {
    private final String receiptId;

    public BookIssueReceipt() {
        this.receiptId = UUID.randomUUID().toString();
    }

    public String getReceiptId() {
        return receiptId;
    }
}

