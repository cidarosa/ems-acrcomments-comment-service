package com.github.cidarosa.acrcomments.comment.service.api.client.excpetions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ModerationClientBadGatewayException extends RuntimeException {
}
