package com.credible.api.services;

import com.credible.api.model.Endorsement;
import com.credible.api.model.requests.EndorsementRequest;
import com.credible.api.repositories.EndorsementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndorsementService {

    @Autowired
    private UserService userService;
    @Autowired
    private EndorsementRepository endorsementRepository;

    public Endorsement createEndorsement(EndorsementRequest endorsementRequest) {

        Endorsement endorsement = new Endorsement();
        endorsement.setEndorser(userService.get(endorsementRequest.getEndorserId()).orElseThrow());
        endorsement.setUser(userService.get(endorsementRequest.getUserId()).orElseThrow());
        endorsement.setText(endorsementRequest.getText());

        return endorsementRepository.save(endorsement);
    }
}
