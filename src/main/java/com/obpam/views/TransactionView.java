package com.obpam.views;

import com.obpam.models.TransactionType;

import java.time.LocalDateTime;

public interface TransactionView {
    long getId();

    TransactionType getType();

    long getAmount();

    LocalDateTime getDate();
}
