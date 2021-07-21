package com.obpam.repositories;

import com.obpam.models.User;
import com.obpam.views.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserView> findOneById(long id);
    List<UserView> findAllBy();
}
