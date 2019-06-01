package com.credible.configuration.security;

import com.credible.configuration.model.BankId;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BankIdAuthenticationToken extends AbstractAuthenticationToken {
    private final BankId principal;

    /**
     * Creates a token with the supplied array of authorities.
     *  @param principal
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     */
    public BankIdAuthenticationToken(BankId principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
