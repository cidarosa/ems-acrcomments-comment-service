package com.github.cidarosa.acrcomments.comment.service.domain.exceptions;

public class ModerationRejectedException extends RuntimeException{

    public ModerationRejectedException(String message) {
        super(message);
    }
}
