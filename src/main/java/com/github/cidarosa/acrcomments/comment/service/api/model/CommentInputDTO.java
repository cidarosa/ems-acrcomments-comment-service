package com.github.cidarosa.acrcomments.comment.service.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentInputDTO {

    private String text;
    private String author;

}
