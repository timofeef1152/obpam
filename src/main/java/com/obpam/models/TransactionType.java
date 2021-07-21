package com.obpam.models;

import lombok.Getter;

@Getter
public enum TransactionType {
    WITHDRAW(-1),
    DEPOSIT(1);

    private final int sign;

    private TransactionType(int sign){
        this.sign = sign;
    }
}
