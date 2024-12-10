package com.example.handmadetrail.Repository;

import com.example.handmadetrail.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // Using find
    Comment findCommentByCommentId(Integer commentId);

    // Using JPQL
    @Query("select c from Comment c where c.rating >= 4")
    List<Comment> getHighRatings();
}