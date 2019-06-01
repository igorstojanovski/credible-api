package com.credible.configuration.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class AuthorizationDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println("DECIDE!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        System.out.println("SUPPORTS!");
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        System.out.println("SUPPORTS 2");
        return false;
    }
}
