package com.credible.api.model.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EndorsementRequest {
    private Long userId;
    private Long endorserId;
    private String text;
}
