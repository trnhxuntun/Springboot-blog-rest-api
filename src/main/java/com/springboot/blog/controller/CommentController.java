package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentDTO> createComments(@Valid @RequestBody CommentDTO commentDTO, @PathVariable(name = "post_id") Long id) {
        return new ResponseEntity<>(commentService.createComment(id, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{post_id}/comments")
    public List<CommentDTO> getCommentbyPostId(@PathVariable(name = "post_id") Long id) {
        return commentService.getCommentsByPostId(id);
    }

    @GetMapping("/posts/{post_id}/comments/{cmt_id}")
    public CommentDTO getCommentById(@PathVariable(name = "post_id") Long post_id, @PathVariable(name = "cmt_id") Long comment_id) {
        return commentService.getCommentById(post_id, comment_id);
    }

    @PutMapping("/posts/{post_id}/comments/{cmt_id}")
    public CommentDTO updateComment(@Valid @RequestBody CommentDTO commentDTO, @PathVariable Long post_id, @PathVariable Long cmt_id) {
        return commentService.updateCommentById(commentDTO, post_id, cmt_id);
    }
}
