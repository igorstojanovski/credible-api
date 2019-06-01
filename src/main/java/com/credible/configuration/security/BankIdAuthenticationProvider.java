package com.credible.configuration.security;

import com.credible.configuration.model.BankId;
import com.credible.configuration.services.bankId.BankIdException;
import com.credible.configuration.services.bankId.BankIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;

@Component
public class BankIdAuthenticationProvider implements AuthenticationProvider {

    private final BankIdService bankIdService;

    @Autowired
    public BankIdAuthenticationProvider(BankIdService bankIdService) {
        this.bankIdService = bankIdService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        authentication.getPrincipal();
        String bankId = ((BankId) authentication.getPrincipal()).getBankId();
        String ip;

        BankIdAuthenticationToken bankIdAuthenticationToken = new BankIdAuthenticationToken((BankId) authentication.getPrincipal(), new ArrayList<>());
        bankIdAuthenticationToken.setDetails(authentication.getDetails());

        try {
            ip = NetworkInterface.getNetworkInterfaces().nextElement().getInetAddresses().nextElement().getHostAddress();
            bankIdService.auth(bankId, ip);

        } catch (SocketException | BankIdException e) {
            bankIdAuthenticationToken.setAuthenticated(false);
        }

        return bankIdAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BankIdAuthenticationToken.class.equals(authentication);
    }
}
