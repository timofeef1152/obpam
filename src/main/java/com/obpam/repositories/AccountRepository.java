package com.obpam.repositories;

import com.obpam.models.Account;
import com.obpam.views.AccountView;
import com.obpam.views.UserTotalBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<AccountView> findOneById(long id);

    List<AccountView> findByUserId(long userId);

    boolean existsByUserId(long userId);

    boolean existsByIdAndUserId(long id, long userId);

    @Query("SELECT u.name as name, SUM(a.balance) as totalBalance FROM " +
            "User u JOIN u.accounts a WHERE a.user.id = :userId " +
            "GROUP BY u.name")
    UserTotalBalance getTotalBalanceById(@Param("userId") long userId);
}
