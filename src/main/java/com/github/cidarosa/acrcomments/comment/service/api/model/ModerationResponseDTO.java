package com.github.cidarosa.acrcomments.comment.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModerationResponseDTO {

    private UUID commentId;
    private Boolean approved;
    private String reason;

    public boolean isApproved() {

        return Boolean.TRUE.equals(getApproved());

    }
}
