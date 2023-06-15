package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.ResponsePostsDTO;
import com.springboot.blog.Service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping("list")
    public ResponsePostsDTO GetAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int PageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(PageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> GetById(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.GetbyId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> UpdatePost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(postService.updatePost(postDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable Long id) {
        postService.DeletePost(id);
        return new ResponseEntity<>("Post entity deleted", HttpStatus.OK);
    }
}
