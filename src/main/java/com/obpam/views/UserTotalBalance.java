package com.obpam.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserTotalBalance {
    String getName();

    @JsonProperty("total_balance")
    long getTotalBalance();
}
