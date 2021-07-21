package com.obpam.services;

import com.obpam.dtos.UserDto;
import com.obpam.models.User;
import com.obpam.views.UserView;
import com.obpam.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.obpam.StringConstants.USER_DOES_NOT_EXIST;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    @Transactional
    @Override
    public UserView findById(long id) {
        return userRepository.findOneById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));
    }

    @Transactional
    @Override
    public List<UserView> findAll() {
        return userRepository.findAllBy();
    }

    @Transactional
    @Override
    public UserDto addUser(UserDto user) {
        User userNew = new User();
        mapper.map(user, userNew);

        userRepository.save(userNew);

        mapper.map(userNew, user);
        return user;
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
