package com.credible.api.resources;

import com.credible.api.model.Approval;
import com.credible.api.model.responses.ApprovalResponse;
import com.credible.api.services.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/{userId}/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping
    @PreAuthorize("@securityService.isValidCode(#id)")
    public ResponseEntity createApproval(@PathVariable Long userId, @RequestParam Long approvingUserId) {
        approvalService.createApproval(userId, approvingUserId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<List<ApprovalResponse>> getAllApprovals(@PathVariable Long userId) {
        List<Approval> approvals = approvalService.getAllApprovals(userId);

        List<ApprovalResponse> responses = approvals.stream()
                .map(a -> new ApprovalResponse(a.getApprovedUser().getId(), a.getApprovingUser().getId()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
