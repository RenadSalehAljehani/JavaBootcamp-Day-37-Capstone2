package com.example.handmadetrail.Controller;

import com.example.handmadetrail.ApiResponse.ApiResponse;
import com.example.handmadetrail.Model.Comment;
import com.example.handmadetrail.Servise.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    // 1. Declare a dependency for CommentService using Dependency Injection
    private final CommentService commentService;

    // 2. CRUD
    // 2.1 Get
    @GetMapping("/get")
    public ResponseEntity getAllComments() {
        return ResponseEntity.status(200).body(commentService.getAllComments());
    }

    // 2.2 Post
    @PostMapping("/add")
    public ResponseEntity addComment(@RequestBody @Valid Comment comment) {
        commentService.addComment(comment);
        return ResponseEntity.status(200).body(new ApiResponse("New Comment Added."));
    }

    // 2.3 Update
    @PutMapping("/update/{commentId}")
    public ResponseEntity updateComment(@PathVariable Integer commentId, @RequestBody @Valid Comment comment) {
        commentService.updateComment(commentId, comment);
        return ResponseEntity.status(200).body(new ApiResponse("Comment Updated."));
    }

    // 2.4 Delete
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(200).body(new ApiResponse("Comment Deleted."));
    }
}
