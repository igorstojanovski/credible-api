package com.credible.configuration.services.bankId;

import com.credible.configuration.model.AuthResponse;
import com.credible.configuration.model.CollectResponse;

public interface BankIdAuthenticator {
    AuthResponse auth(String personalNumber, String ipAddress) throws BankIdException;
    CollectResponse collect(String refId) throws BankIdException;
}
