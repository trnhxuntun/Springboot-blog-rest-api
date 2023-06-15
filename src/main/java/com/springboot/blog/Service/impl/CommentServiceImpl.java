package com.springboot.blog.Service.impl;

import com.springboot.blog.payload.CommentDTO;
import com.springboot.blog.Service.CommentService;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(Long post_id, CommentDTO commentDTO) {
        Comment comment = maptoEntity(commentDTO);
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        comment.setPosts(post);
        Comment newComment = commentRepository.save(comment);
        return maptoDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long post_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        List<Comment> comments = commentRepository.findByPostsId(post_id);
        return comments.stream().map(comment -> maptoDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long post_id, Long cmt_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        Comment comment = commentRepository.findById(cmt_id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", cmt_id));
        if (!comment.getPosts().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bình luận không thuộc về bài viết này");
        }
        return maptoDTO(comment);
    }

    @Override
    public CommentDTO updateCommentById(CommentDTO commentDTO,Long post_id, Long cmt_id) {
        Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        Comment comment = commentRepository.findById(cmt_id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", cmt_id));
        if (!comment.getPosts().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Bình luận không thuộc về bài viết này");
        }
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment newComment=commentRepository.save(comment);
        return maptoDTO(newComment);
    }

    private CommentDTO maptoDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
        return commentDTO;
    }

    private Comment maptoEntity(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO,Comment.class);
        return comment;
    }
}
