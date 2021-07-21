package com.obpam.repositories;

import com.obpam.models.Transaction;
import com.obpam.views.TransactionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t.id as id, t.type as type, t.amount as amount, t.date as date FROM " +
            "Account a JOIN a.transactions t WHERE t.account.id = :accountId")
    List<TransactionView> getAllUserAccountTransactions(@Param("accountId") long accountId);
}
