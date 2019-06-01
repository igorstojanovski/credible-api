package com.credible.api.services;

import com.credible.api.model.Approval;
import com.credible.api.repositories.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private UserService userService;

    public Approval createApproval(Long userId, Long approvingUserId) {
        Approval approval = new Approval();
        approval.setApprovedUser(userService.get(userId).orElseThrow());
        approval.setApprovingUser(userService.get(approvingUserId).orElseThrow());

        return approvalRepository.save(approval);
    }

    public List<Approval> getAllApprovals(Long approvedUserId) {
        return approvalRepository.getApprovalByApprovedUserId(approvedUserId);
    }
}
