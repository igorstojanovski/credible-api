package com.credible.api.resources;

import com.credible.api.model.Endorsement;
import com.credible.api.model.requests.EndorsementRequest;
import com.credible.api.model.responses.EndorsementResponse;
import com.credible.api.services.EndorsementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/{userId}/endorsement")
public class EndorsementController {

    @Autowired
    private EndorsementService endorsementService;

    @PostMapping
    public ResponseEntity<EndorsementResponse> createEndorsement(@PathVariable Long userId,
                                                                 @RequestBody EndorsementRequest endorsementRequest) {
        endorsementRequest.setUserId(userId);
        Endorsement endorsement = endorsementService.createEndorsement(endorsementRequest);
        EndorsementResponse response = new EndorsementResponse();
        response.setEndorserId(endorsement.getEndorser().getId());
        response.setId(endorsement.getId());
        response.setUserId(endorsement.getUser().getId());
        response.setText(endorsement.getText());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
