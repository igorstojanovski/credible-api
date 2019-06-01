package com.credible.api.repositories;

import com.credible.api.model.Approval;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends CrudRepository<Approval, Long> {
    List<Approval> getApprovalByApprovedUserId(Long approvedUserId);
}
