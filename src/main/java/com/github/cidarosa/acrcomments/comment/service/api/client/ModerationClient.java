package com.github.cidarosa.acrcomments.comment.service.api.client;

import com.github.cidarosa.acrcomments.comment.service.api.model.ModerationRequestDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.ModerationResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ModerationClient {

    @PostExchange("/api/moderate")
    ModerationResponseDTO moderateComment(@RequestBody ModerationRequestDTO requestDTO);
}
