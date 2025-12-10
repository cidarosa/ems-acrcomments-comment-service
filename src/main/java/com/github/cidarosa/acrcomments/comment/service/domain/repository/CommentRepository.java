package com.github.cidarosa.acrcomments.comment.service.domain.repository;

import com.github.cidarosa.acrcomments.comment.service.domain.model.Comment;
import com.github.cidarosa.acrcomments.comment.service.domain.model.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
