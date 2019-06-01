package com.credible.api.resources;

import com.credible.api.model.User;
import com.credible.api.model.responses.UserResponse;
import com.credible.api.services.UserService;
import com.credible.configuration.model.BankId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isCodeValid(#id)")
    public ResponseEntity<User> getUser(@PathVariable Long id) throws Exception {
        User user = userService.get(id).orElseThrow(Exception::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}/code")
    @PreAuthorize("@securityService.isOwner(#id)")
    public ResponseEntity<String> getUserCode(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserCode(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        BankId bankId = (BankId) auth.getPrincipal();
        User user = userService.get(bankId.getId()).orElseThrow();

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setSurname(user.getSurname());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
