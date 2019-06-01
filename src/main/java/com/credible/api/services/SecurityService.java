package com.credible.api.services;

import com.credible.configuration.model.BankId;
import com.credible.configuration.security.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

@Component("securityService")
public class SecurityService implements PermissionEvaluator {

    @Autowired
    private UserService userService;

    public boolean isOwner(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BankId principal = (BankId) authentication.getPrincipal();
        return principal.getId().equals(id);
    }

    public boolean isCodeValid(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String code = ((AuthenticationDetails) authentication.getDetails()).getCode();
        return isOwner(id) || code != null && userService.codeIsValid(id, code);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
