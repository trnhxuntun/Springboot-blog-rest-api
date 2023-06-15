package com.springboot.blog.Service;

import com.springboot.blog.payload.PostDTO;
import com.springboot.blog.payload.ResponsePostsDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    ResponsePostsDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO GetbyId(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void DeletePost(Long id);
}
