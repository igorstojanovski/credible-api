package com.credible.configuration.services.bankId;

import com.credible.api.repositories.BankIdRepository;
import com.credible.configuration.model.AuthResponse;
import com.credible.configuration.model.BankId;
import com.credible.configuration.model.BankIdUser;
import com.credible.configuration.model.CollectResponse;
import com.credible.configuration.services.UserOnboardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankIdService {
    private final UserOnboardingService userOnboardingService;
    private BankIdRepository bankIdRepository;
    private BankIdAuthenticator bankIdAuthenticator;

    @Autowired
    public BankIdService(BankIdRepository bankIdRepository, BankIdAuthenticator bankIdAuthenticator,
                         UserOnboardingService userOnboardingService) {
        this.bankIdRepository = bankIdRepository;
        this.bankIdAuthenticator =bankIdAuthenticator;
        this.userOnboardingService = userOnboardingService;
    }

    public BankId find(String bankId) {
        return bankIdRepository.findBankIdByBankId(bankId);
    }

    public BankId createBankId(BankId bankId) {
        return bankIdRepository.save(bankId);
    }

    public AuthResponse auth(String personalNumber, String ipAddress) throws BankIdException {
        return bankIdAuthenticator.auth(personalNumber, ipAddress);
    }

    public CollectResponse collect(String refId) throws BankIdException {
        return bankIdAuthenticator.collect(refId);
    }

    public BankId onBoard(BankIdUser bankIdUser) {
        BankId internal = bankIdRepository.findBankIdByBankId(bankIdUser.getPersonalNumber());
        if(internal == null) {
            BankId bankId = new BankId();
            bankId.setBankId(bankIdUser.getPersonalNumber());
            internal = bankIdRepository.save(bankId);
            userOnboardingService.onBoard(internal.getId(), bankIdUser);
        }
        return internal;
    }

    public boolean isValid(String bankId) {
        return bankIdRepository.findBankIdByBankId(bankId) != null;
    }
}
