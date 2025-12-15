package com.github.cidarosa.acrcomments.comment.service.domain.service;

import com.github.cidarosa.acrcomments.comment.service.api.client.ModerationClient;
import com.github.cidarosa.acrcomments.comment.service.api.model.CommentInputDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.CommentOutputDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.ModerationRequestDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.ModerationResponseDTO;
import com.github.cidarosa.acrcomments.comment.service.domain.exceptions.CommentNotFoundException;
import com.github.cidarosa.acrcomments.comment.service.domain.exceptions.ModerationRejectedException;
import com.github.cidarosa.acrcomments.comment.service.domain.model.Comment;
import com.github.cidarosa.acrcomments.comment.service.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    @Transactional(readOnly = true)
    public Page<CommentOutputDTO> findAllPageable(Pageable pageable){

        Page<Comment> comments = commentRepository.findAll((pageable));
        return comments.map(this::convertToModel);
    }

    @Transactional(readOnly = true)
    public CommentOutputDTO findById(UUID id){

        Comment comment = commentRepository.findById(id )
                .orElseThrow(CommentNotFoundException::new);

        return convertToModel(comment);
    }

    @Transactional
    public CommentOutputDTO saveComment(CommentInputDTO inputDTO){

        Comment comment = Comment.builder()
                .id(UUID.randomUUID())
                .author(inputDTO.getAuthor())
                .text(inputDTO.getText())
                .createdAt(OffsetDateTime.now())
                .build();

        ModerationRequestDTO requestDTO = new ModerationRequestDTO(comment.getId(), inputDTO.getText());
        ModerationResponseDTO responseDTO = moderationClient.moderateComment(requestDTO);

        if (!responseDTO.isApproved()){
            throw new ModerationRejectedException(responseDTO.getReason());
        }

        comment = commentRepository.saveAndFlush(comment);

        return convertToModel(comment);
    }

    private CommentOutputDTO convertToModel(Comment comment) {

        return CommentOutputDTO.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }


}
