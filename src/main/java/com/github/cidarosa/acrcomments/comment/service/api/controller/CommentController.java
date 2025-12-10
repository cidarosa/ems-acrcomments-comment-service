package com.github.cidarosa.acrcomments.comment.service.api.controller;

import com.github.cidarosa.acrcomments.comment.service.api.model.CommentInputDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.CommentOutputDTO;
import com.github.cidarosa.acrcomments.comment.service.common.IdGenerator;
import com.github.cidarosa.acrcomments.comment.service.domain.model.Comment;
import com.github.cidarosa.acrcomments.comment.service.domain.model.CommentId;
import com.github.cidarosa.acrcomments.comment.service.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentOutputDTO> search(@PageableDefault Pageable pageable){
        Page<Comment> comments = commentRepository.findAll((pageable));
        return comments.map(this::convertToModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutputDTO create(@RequestBody CommentInputDTO inputDTO){

        Comment comment = Comment.builder()
                .id(new CommentId(IdGenerator.generateTSID()))
                .author(inputDTO.getAuthor())
                .text(inputDTO.getText())
                .createdAt(OffsetDateTime.now())
                .build();

        comment = commentRepository.saveAndFlush(comment);

        return convertToModel(comment);

    }

    private CommentOutputDTO convertToModel(Comment comment) {

        return CommentOutputDTO.builder()
                .id(comment.getId().getValue())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
