package com.github.cidarosa.acrcomments.comment.service.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CommentOutputDTO {

    private TSID id;
    private String text;
    private String author;
    private OffsetDateTime createdAt;
}
