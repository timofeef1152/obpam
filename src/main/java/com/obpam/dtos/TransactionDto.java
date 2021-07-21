package com.obpam.dtos;

import com.obpam.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
public class TransactionDto {
    @Null(message = "Id must be null")
    private Long id;
    @NotNull(message = "Type couldn't be null")
    private TransactionType type;
    @Min(value = 1, message = "Amount couldn't be negative or zero")
    private long amount;
}
