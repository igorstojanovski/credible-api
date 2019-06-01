package com.credible.api.model.responses;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalResponse {
    private Long approvedUserId;
    private Long approvingUserId;
}
