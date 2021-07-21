package com.obpam.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "user_id"}))
@Getter
@Setter
public class Account extends BaseEntity {
    private String name;

    private long balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
