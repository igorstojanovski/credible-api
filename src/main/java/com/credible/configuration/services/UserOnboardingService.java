package com.credible.configuration.services;

import com.credible.configuration.model.BankIdUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserOnboardingService {
    UserDetails onBoard(Long id, BankIdUser user);
}
