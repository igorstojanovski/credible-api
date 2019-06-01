package com.credible.api.services;

import com.credible.api.model.User;
import com.credible.api.repositories.UserRepository;
import com.credible.configuration.model.BankIdUser;
import com.credible.configuration.services.UserOnboardingService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserOnboardingService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    private User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails onBoard(Long id, BankIdUser bankIdUser) {
        User user = new User();
        user.setId(id);
        user.setName(bankIdUser.getName());
        user.setSurname(bankIdUser.getSurname());
        user.setCode(RandomStringUtils.randomAlphabetic(10));
        return save(user);
    }

    boolean codeIsValid(Long id, String code) {
        User userByCode = userRepository.findUserByCode(code);
        boolean isValid = false;
        if (userByCode != null) {
            if (userByCode.getId().equals(id)) {
                isValid = true;
            }
        }

        return isValid;
    }

    public String getUserCode(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElseThrow().getCode();
    }

    Optional<User> getUserByCode(String code) {
        return Optional.of(userRepository.findUserByCode(code));
    }
}
