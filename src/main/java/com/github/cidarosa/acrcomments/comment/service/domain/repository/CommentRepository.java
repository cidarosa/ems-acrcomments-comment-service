package com.github.cidarosa.acrcomments.comment.service.domain.repository;

import com.github.cidarosa.acrcomments.comment.service.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
