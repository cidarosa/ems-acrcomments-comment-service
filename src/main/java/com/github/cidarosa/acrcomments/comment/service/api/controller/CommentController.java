package com.github.cidarosa.acrcomments.comment.service.api.controller;

import com.github.cidarosa.acrcomments.comment.service.api.model.CommentInputDTO;
import com.github.cidarosa.acrcomments.comment.service.api.model.CommentOutputDTO;
import com.github.cidarosa.acrcomments.comment.service.domain.service.CommentService;
import com.github.cidarosa.acrcomments.comment.service.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping
    public ResponseEntity<Page<CommentOutputDTO>> search(@PageableDefault Pageable pageable) {
        Page<CommentOutputDTO> comments = commentService.findAllPageable(pageable);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentOutputDTO> getOne(@PathVariable UUID id) {
        CommentOutputDTO outputDTO = commentService.findById(id);

        return ResponseEntity.ok(outputDTO);
    }

    @PostMapping
    public ResponseEntity<CommentOutputDTO> create(@RequestBody CommentInputDTO inputDTO) {

        CommentOutputDTO outputDTO = commentService.saveComment(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(outputDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(outputDTO);

    }
}
