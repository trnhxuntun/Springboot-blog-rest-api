package com.springboot.blog.Service;

import com.springboot.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long post_id,CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(Long post_id);
    CommentDTO getCommentById(Long post_id,Long cmt_id);
    CommentDTO updateCommentById(CommentDTO commentDTO,Long post_id,Long cmt_id);
}
