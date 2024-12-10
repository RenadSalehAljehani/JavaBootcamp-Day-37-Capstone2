package com.example.handmadetrail.Servise;

import com.example.handmadetrail.ApiResponse.ApiException;
import com.example.handmadetrail.Model.Comment;
import com.example.handmadetrail.Repository.CommentRepository;
import com.example.handmadetrail.Repository.DiyBeginnerRepository;
import com.example.handmadetrail.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    // 1. Declare a dependency for CommentRepository using Dependency Injection
    private final CommentRepository commentRepository;

    // 2. Declare a dependency for DiyBeginnerRepository using Dependency Injection
    private final DiyBeginnerRepository diyBeginnerRepository;

    // 3. Declare a dependency for ProjectRepository using Dependency Injection
    private final ProjectRepository projectRepository;

    // 4. CRUD
    // 4.1 Get
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // 4.2 Post
    public void addComment(Comment comment) {
        // 1. Check the existence of diy-beginner id and project id for the comment
        boolean invalidDiyBeginnerId = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(comment.getDiyBeginnerId()) == null;
        boolean invalidProjectId = projectRepository.findProjectByProjectId(comment.getProjectId()) == null;

        if (invalidDiyBeginnerId && invalidProjectId) {
            throw new ApiException("Diy Beginner and Project Not Found.");
        }
        if (invalidDiyBeginnerId) {
            throw new ApiException("Diy Beginner Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Not Found.");
        }
        // 2. Save changes
        commentRepository.save(comment);
    }

    // 4.3 Update
    public void updateComment(Integer commentId, Comment comment) {
        // 1. check if the comment to be updated exists
        Comment oldComment = commentRepository.findCommentByCommentId(commentId);
        if (oldComment == null) {
            throw new ApiException("Comment Not Found.");
        }

        // 1. Check the existence of diy-beginner id and project id for the comment
        boolean invalidDiyBeginnerId = diyBeginnerRepository.findDiyBeginnerByDiyBeginnerId(comment.getDiyBeginnerId()) == null;
        boolean invalidProjectId = projectRepository.findProjectByProjectId(comment.getProjectId()) == null;

        if (invalidDiyBeginnerId && invalidProjectId) {
            throw new ApiException("Diy Beginner and Project Not Found.");
        }
        if (invalidDiyBeginnerId) {
            throw new ApiException("Diy Beginner Not Found.");
        }
        if (invalidProjectId) {
            throw new ApiException("Project Not Found.");
        }

        // 3. Set new values
        oldComment.setDiyBeginnerId(comment.getDiyBeginnerId());
        oldComment.setProjectId(comment.getProjectId());
        oldComment.setContent(comment.getContent());

        // 4. Save changes
        commentRepository.save(oldComment);
    }

    // 4.4 Delete
    public void deleteComment(Integer commentId) {
        // Check if the comment to be deleted exists
        Comment oldComment = commentRepository.findCommentByCommentId(commentId);
        if (oldComment == null) {
            throw new ApiException("Comment Not Found.");
        }
        commentRepository.delete(oldComment);
    }
}