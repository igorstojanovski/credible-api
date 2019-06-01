package com.credible.configuration;

import com.credible.configuration.model.AuthResponse;
import com.credible.configuration.model.BankIdUser;
import com.credible.configuration.model.CollectResponse;
import com.credible.configuration.model.CompletitionData;
import com.credible.configuration.services.bankId.BankIdAuthenticator;
import com.credible.configuration.services.bankId.BankIdException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DummyBankIdAuthenticator implements BankIdAuthenticator {

    private final Map<String, String> personalNumbers = new HashMap<>();

    @Override
    public AuthResponse auth(String personalNumber, String ipAddress) {
        AuthResponse authResponse = new AuthResponse();
        if (personalNumbers.containsKey(personalNumber)) {
            authResponse.setOrderRef(personalNumbers.get(personalNumber));
        } else {
            String refId = RandomStringUtils.randomAlphabetic(10);
            authResponse.setOrderRef(refId);
            personalNumbers.put(personalNumber, refId);
        }

        return authResponse;
    }

    @Override
    public CollectResponse collect(String refId) throws BankIdException {
        if(personalNumbers.values().contains(refId)) {
            String personalNumber = "";
            for(Map.Entry<String, String> entry : personalNumbers.entrySet()) {
                if(entry.getValue().equals(refId)) {
                    personalNumber = entry.getKey();
                    break;
                }
            }
            personalNumbers.remove(personalNumber);
            CollectResponse collectResponse = new CollectResponse();
            collectResponse.setStatus("complete");

            CompletitionData completitionData = new CompletitionData();
            BankIdUser bankIdUser = new BankIdUser();
            bankIdUser.setPersonalNumber(personalNumber);
            bankIdUser.setName("Igor Stojanovski");
            bankIdUser.setSurname("Stojanovski");
            bankIdUser.setName("Igor");
            completitionData.setUser(bankIdUser);
            collectResponse.setCompletitionData(completitionData);
            return collectResponse;
        }
        return null;
    }
}
