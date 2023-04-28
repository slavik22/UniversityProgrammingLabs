package com.bozzaccio.twitterclone.service;

import com.bozzaccio.twitterclone.dao.UserRepository;
import com.bozzaccio.twitterclone.entity.User;
import com.bozzaccio.twitterclone.security.UserLogged;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;

import static com.bozzaccio.twitterclone.util.ErrorUtils.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Assert.notNull(username, buildErrorMessage(BASE_PARAMETER_ERROR, USERNAME, NULL_MESSAGE_ERROR));

        User user = repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(buildErrorMessageWithValue(BASE_DB_ERROR,
                        USER_NOT_FOUND_FOR_USERNAME_ERROR,
                        username)));

        return UserLogged.build(user);
    }

    public boolean checkIfUserExistsByUsername(String username) {

        Assert.notNull(username, buildErrorMessage(BASE_PARAMETER_ERROR, USERNAME, NULL_MESSAGE_ERROR));

        return repository.existsByUsername(username);
    }

    public boolean checkIfUserExistsByEmail(String email) {

        Assert.notNull(email, buildErrorMessage(BASE_PARAMETER_ERROR, EMAIL, NULL_MESSAGE_ERROR));

        return repository.existsByEmail(email);
    }

    public User create(User entity) {

        Assert.notNull(entity, buildErrorMessage(BASE_PARAMETER_ERROR, ENTITY, NULL_MESSAGE_ERROR));

        return repository.saveAndFlush(entity);
    }
}
